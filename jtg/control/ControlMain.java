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

import model.BOBox;
import model.BOLocale;
import model.BOSettings;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.dom4j.Document;

import boxConnection.SerBoxControl;
import boxConnection.SerBoxControlDefault;
import boxConnection.SerBoxControlEnigma;
import boxConnection.SerBoxControlNeutrino;
import boxConnection.SerStreamingServer;
import service.SerLogAppender;
import service.SerXMLConverter;
import service.SerXMLHandling;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

/**
 * Startklasse, Start der Anwendung
 * Deklaration der globalen Variablen
 */
public class ControlMain {
	
	static BOSettings settings;
	static Document settingsDocument;
	static SerBoxControl boxAccess;
	static SerLogAppender logAppender;
	static ControlMainView control;
	static BOLocale bolocale = new BOLocale();
	static SerStreamingServer streamingServerThread;
	
	
    private static Properties prop = new Properties();   
    private static Locale locale = new Locale("");    
    
    public static String filename = "settings.xml";
	public static String version[] = { 
		"Jack the JGrabber 0.1",
		"18.09.2004",
		"TEST PROJECT ONLY",
		", User: "+System.getProperty("user.name")
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
	
	public static final String[] themes = {
		"Silver", "BrownSugar", "DarkStar", "DesertBlue", "ExperienceBlue", "SkyBluerTahoma"
	};

	public static void main( String args[] ) {
		startLogger();
		readSettings();
		control = new ControlMainView();
		startStreamingSever();
	};
	
	public static void startLogger() {
		PatternLayout layout = new PatternLayout();
		
		//http://logging.apache.org/log4j/docs/api/org/apache/log4j/PatternLayout.html
		layout.setConversionPattern("%d{HH:mm:ss} %-5p %c - %m%n");
		
		try {
			ControlMain.setLogAppender(new SerLogAppender(layout));
			ControlMain.getLogAppender().setMaxBackupIndex(3); //Number of max Backup-Files
			ControlMain.getLogAppender().setMaxFileSize("100KB");
			BasicConfigurator.configure(ControlMain.getLogAppender());
		} catch (IOException e) {}
	}
	
	public static void startStreamingSever() {
		int port = Integer.parseInt(getSettings().getStreamingServerPort());
		Logger.getLogger("ControlMain").info("Start Streaming-Server");
		setStreamingServerThread(new SerStreamingServer(port));
		getStreamingServerThread().start();	
	}
		
	public static void detectImage() {
		Logger.getLogger("ControlMain").info("Searching Box-Image");
		int image=SerBoxControl.ConnectBox(ControlMain.getBoxIpOfSelectedBox());
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
		Logger.getLogger("ControlMain").info(ControlMain.getBoxAccess().getName()+"-Access loaded");
	}
	
	/**
	 * Settings aus dem XML-File lesen. Falls Keines vorhanden ein leeres Dokument erstellen.
	 * Dokument wird immer gebraucht, falls neue Settings gespeichert werden.
	 */
	public static void readSettings() {
		try {
			File pathToXMLFile = new File(filename).getAbsoluteFile();
			if (pathToXMLFile.exists()) {
				settingsDocument = SerXMLHandling.readDocument(pathToXMLFile);
				Logger.getLogger("ControlMain").info("Settings found");
			} else {
				settingsDocument = SerXMLHandling.buildEmptyXMLFile(pathToXMLFile);
				Logger.getLogger("ControlMain").info("Settings not found, created empty document");
			}
			setSettings(SerXMLConverter.buildSettings(getSettingsDocument()));
		} catch (Exception ex) {Logger.getLogger("ControlMain").error("Fehler beim lesen der Settings!");}
	}
	/**
	 * Wenn nur ein Box angelegt, diese als Standard benutzen
	 */
	public static String getBoxIpOfSelectedBox() {
		ArrayList boxList = getSettings().getBoxList();
		if (boxList.size()==1) {
			BOBox box = (BOBox)boxList.get(0);
			return box.getDboxIp();
		}
		for (int i=0; boxList.size()>i; i++) {
			BOBox box = (BOBox)boxList.get(i);
			if (box.isSelected()) {
				return box.getDboxIp();
			}
		}
		return new String();
	}
	
	public static BOBox getStandardBox() {
		ArrayList boxList = getSettings().getBoxList();
		for (int i=0; boxList.size()>i; i++) {
			BOBox box = (BOBox)boxList.get(i);
			if (box.isStandard().booleanValue()) {
				return box;
			}
		}
		return new BOBox();
	}
	/**
	 * Wenn nur ein Box angelegt, diese als Standard benutzen
	 */
	public static BOBox getSelectedBox() {
		ArrayList boxList = getSettings().getBoxList();
		if (boxList.size()==1) {
			return (BOBox)boxList.get(0);
		
		}
		for (int i=0; boxList.size()>i; i++) {
			BOBox box = (BOBox)boxList.get(i);
			if (box.isSelected()) {
				return box;
			}
		}
		return new BOBox();
	}
	
	public static int getIndexOfStandardBox() {
		ArrayList boxList = getSettings().getBoxList();
		for (int i=0; boxList.size()>i; i++) {
			BOBox box = (BOBox)boxList.get(i);
			if (box.isStandard().booleanValue()) {
				return i;
			}
		}
		return 0;
	}
	
	public static int getIndexOfSelecteddBox() {
		ArrayList boxList = getSettings().getBoxList();
		for (int i=0; boxList.size()>i; i++) {
			BOBox box = (BOBox)boxList.get(i);
			if (box.isSelected()) {
				return i;
			}
		}
		return 0;
	}
	
	public static String getVlcPath() {
		return (String)getSettings().getVlcPath();
	}	
	public static void setVlcPath(String boxIp) {
		getSettings().setVlcPath(boxIp);
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
	
	private static void setLocale(String sprache){       		              
	     if (!bolocale.getLocale().equals("de")){        	
                locale = new Locale(bolocale.getLocale().substring(0,2));
         }
	}

	public static Locale getLocale(){           			
		bolocale.setLocale(settings.getLocale().toString());
		setLocale(bolocale.getLocale().substring(0,2));		
        return locale;
	}

	public static String getProperty(String key){		
		return prop.getProperty(key);
	}

	public static void setResourceBundle(Locale loc){		
		String _MESSAGE_BUNDLE ="locale/messages_"+locale.getLanguage()+".properties";
		ControlMain.locale=loc;        
		try{                            
			if (locale.getLanguage().equalsIgnoreCase("de") || locale.getLanguage().length() < 1   ){      
                	_MESSAGE_BUNDLE="locale/messages.properties";
			}                               
        File f = new File(_MESSAGE_BUNDLE).getAbsoluteFile();       
        InputStream is = new FileInputStream(f);                
        prop.load(is);                          
		}catch (IOException ex){}                       
	}    
	/**
	 * @return Returns the streamingServerThread.
	 */
	public static SerStreamingServer getStreamingServerThread() {
		return streamingServerThread;
	}
	/**
	 * @param streamingServerThread The streamingServerThread to set.
	 */
	public static void setStreamingServerThread(
			SerStreamingServer streamingServerThread) {
		ControlMain.streamingServerThread = streamingServerThread;
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
}
