package control;
/*
ControlMain by Alexander Geist

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.  

*/ 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

import model.BOBox;
import model.BOSettings;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.dom4j.Document;
import org.dom4j.DocumentException;

import presentation.GuiSplashScreen;
import projectX.common.*;
import service.SerLogAppender;
import service.SerSettingsHandler;
import service.SerXMLHandling;
import boxConnection.SerBoxControl;
import boxConnection.SerBoxControlDefault;
import boxConnection.SerBoxControlEnigma;
import boxConnection.SerBoxControlNeutrino;

/**
 * Startklasse, Start der Anwendung
 * Deklaration der globalen Variablen
 */
public class ControlMain {
	
	static BOSettings settings;
	static Document settingsDocument;
	static Document movieGuideDocument;
	static SerBoxControl boxAccess;
	static SerLogAppender logAppender;
	static ControlMainView control;
	static BOBox activeBox;
	
	
    private static Properties properties = new Properties();   
    private static Locale locale = new Locale("");
    public static GuiSplashScreen screen;
    
    public static String settingsFilename = "settings.xml";
    
	public static String version[] = { 
		"Jack the JGrabber 0.1.6d",
		"24.11.2004",
		"TEST PROJECT ONLY",
		"User: "+System.getProperty("user.name")
	};
	
	static String terms[] = { 
		" ",
		"TERMS OF CONDITIONS:",
		"(1) this is a free Java based recording utility.",
		"(2) It is intended for educational purposes only, as a non-commercial test project.",
		"(3) It may not be used otherwise. Most parts are only experimental.",
		"(4) released under the terms of the GNU GPL",
		"(5) there is NO WARRANTY of any kind attached to this software",
		"(6) use it at your own risk and for your own education as it was meant",
		" ",
	};

	public static void main( String args[] ) {
		startLogger();
		readSettings();
		if (ControlMain.getSettings().showLogo) {
			screen = new GuiSplashScreen("ico/grabber1.png", version[0], "Starting Application..." );
		}
		setResourceBundle();
		detectActiveBox();
		control = new ControlMainView();
		if (screen != null) {
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {}
			screen.dispose();
		}
	};
	
	public static void startLogger() {
		PatternLayout layout = new PatternLayout();
		
		//http://logging.apache.org/log4j/docs/api/org/apache/log4j/PatternLayout.html
		layout.setConversionPattern("%d{HH:mm:ss:ms} %-5p %c - %m%n");
		
		try {
			ControlMain.setLogAppender(new SerLogAppender(layout));
			ControlMain.getLogAppender().setMaxBackupIndex(3); //Number of max Backup-Files
			ControlMain.getLogAppender().setMaxFileSize("100KB");
			BasicConfigurator.configure(ControlMain.getLogAppender());
		} catch (IOException e) {}
	}
		
	public static void detectImage() {
		Logger.getLogger("ControlMain").info(ControlMain.getProperty("msg_searchImage"));
		int image=SerBoxControl.ConnectBox(ControlMain.getBoxIpOfActiveBox());
		if (image==0) {
			boxAccess=new SerBoxControlDefault();
		}
		if (image==3) {
			boxAccess=new SerBoxControlDefault();
		}
		if (image==1) {
			boxAccess = new SerBoxControlNeutrino();
		}
		else if (image==2) {
			boxAccess = new SerBoxControlEnigma();
		}
		Logger.getLogger("ControlMain").info(ControlMain.getBoxAccess().getName()+"-"+ControlMain.getProperty("msg_accessLoaded"));
	}
	
	/**
	 * Settings aus dem XML-File lesen. Falls Keines vorhanden ein leeres Dokument erstellen.
	 * Dokument wird immer gebraucht, falls neue Settings gespeichert werden.
	 */
	public static void readSettings() {
			try {
				File pathToXMLFile = new File(settingsFilename).getAbsoluteFile();
				if (pathToXMLFile.exists()) {
					settingsDocument = SerXMLHandling.readDocument(pathToXMLFile);
					Logger.getLogger("ControlMain").info(ControlMain.getProperty("msg_settingsFound"));
				} else {
					settingsDocument = SerXMLHandling.createStandardSettingsFile(pathToXMLFile);
					Logger.getLogger("ControlMain").info(ControlMain.getProperty("msg_settingsNotFound"));
				}
				setSettings(SerSettingsHandler.buildSettings(getSettingsDocument()));
			} catch (MalformedURLException e) {
				Logger.getLogger("ControlMain").error(ControlMain.getProperty("msg_settingsError"));
			} catch (DocumentException e) {
				Logger.getLogger("ControlMain").error(ControlMain.getProperty("msg_settingsError"));
			} catch (IOException e) {
				Logger.getLogger("ControlMain").error(ControlMain.getProperty("msg_settingsError"));
			}
	}	

	public static String getBoxIpOfActiveBox() {
		BOBox box = getActiveBox();
		if (box==null) {
			return "";
		}
		try {
            return (InetAddress.getByName(box.getDboxIp())).getHostAddress();
        } catch (UnknownHostException e) {
            return box.getDboxIp();
        }
	}
	
	/**
	 * Wenn nur eine Box angelegt, diese als Standard benutzen.
	 * Wenn mehrere Boxen angelegt sind, und keine als Standard definiert ist, die 1. Box als Standard benutzen
	 */
	public static boolean  detectActiveBox() {
		ArrayList boxList = getSettings().getBoxList();
		BOBox box;
		if (boxList.size()==1) { //nur eine Box vorhanden, diese als Standard benutzen
			box = (BOBox)boxList.get(0);
			box.setSelected(true);
			setActiveBox(box);
			return true;
		}
		for (int i=0; boxList.size()>i; i++) { 
			box = (BOBox)boxList.get(i);
			if (box.isStandard().booleanValue()) { //mehrer Boxen vorhanden, die Standardbox zurückgeben
				box.setSelected(true);
				setActiveBox(box);
				return true;
			}
		}
		if (boxList.size()>0) {//mehrere Boxen vorhanden, die 1. als Standard benutzen
			box = (BOBox)boxList.get(0);
			box.setSelected(true);
			setActiveBox(box);
			return true;
		}
		return false;
	}
	
	public static int getIndexOfActiveBox() {
		ArrayList boxList = getSettings().getBoxList();
		if (getActiveBox() != null) { 
			for (int i=0; boxList.size()>i; i++) {
				BOBox box = (BOBox)boxList.get(i);
				if (box.getDboxIp().equals(getActiveBox().getDboxIp())) {
					return i;
				}
			}
		}
		return -1;
	}
	/**
	 * @return Returns the settings.
	 */
	public static BOSettings getSettings() {
		return settings;
	}
	/**
	 * @param settings The settings to set.
	 */
	public static void setSettings(BOSettings settings) {
		ControlMain.settings = settings;
	}
	
	/**
	 * @return Returns the settingsDocument.
	 */
	public static Document getSettingsDocument() {
		return settingsDocument;
	}
	/**
	 * @param settingsDocument The settingsDocument to set.
	 */
	public static void setSettingsDocument(Document settingsDocument) {
		ControlMain.settingsDocument = settingsDocument;
	}
	
	/**
	 * @return Returns the box.
	 */
	public static SerBoxControl getBoxAccess() {
		return boxAccess;
	}
	/**
	 * @param box The box to set.
	 */
	public static void setBoxAccess(SerBoxControl box) {
		ControlMain.boxAccess = box;
	}
	/**
	 * @return Returns the terms.
	 */
	public static String[] getTerms() {
		return terms;
	}
	/**
	 * @param terms The terms to set.
	 */
	public static void setTerms(String[] terms) {
		ControlMain.terms = terms;
	}
	/**
	 * @return Returns the logAppender.
	 */
	public static SerLogAppender getLogAppender() {
		return logAppender;
	}
	/**
	 * @param logAppender The logAppender to set.
	 */
	public static void setLogAppender(SerLogAppender logAppender) {
		ControlMain.logAppender = logAppender;
	}

	public static String getProperty(String key){		
		return properties.getProperty(key);
	}

	public static void setResourceBundle(){		
		String _MESSAGE_BUNDLE ="locale/messages_"+ControlMain.getSettings().getShortLocale()+".properties";   
		
        File f = new File(_MESSAGE_BUNDLE.toLowerCase());
        try {
	        InputStream is = new FileInputStream(f);                
	        properties.load(is);                          
		}catch (IOException ex){
			Logger.getLogger("ControlMain").error(ControlMain.getProperty("msg_propertyError")+f.getName());
		}                       
	}    
	/**
	 * @return Returns the control.
	 */
	public static ControlMainView getControl() {
		return control;
	}
	/**
	 * @param control The control to set.
	 */
	public static void setControl(ControlMainView control) {
		ControlMain.control = control;
	}
	/**
	 * @return Returns the activeBox.
	 */
	public static BOBox getActiveBox() {
		return activeBox;
	}
	/**
	 * @param activeBox The activeBox to set.
	 */
	public static void setActiveBox(BOBox activeBox) {
		ControlMain.activeBox = activeBox;
	}

	/**
	 * 
	 */
	public static void endProgram() {
		try {
			if (ControlMain.getSettings().isSettingsChanged()) {
				SerSettingsHandler.saveAllSettings();
				Logger.getLogger("ControlMainView").info("Settings saved");
			}
			if (ControlMain.getSettings().isProjectXSettingsChanged()) {
				X.inisave();
				Logger.getLogger("ControlMainView").info("ProjectX-Settings saved");
			}
		} catch (IOException e1) {
			Logger.getLogger("ControlMainView").error("Error while save Settings");
		}
		System.exit(0); 
		
	}
}
