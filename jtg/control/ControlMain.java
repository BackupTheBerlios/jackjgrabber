package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import model.BOBox;
import model.BOSettings;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.dom4j.Document;

import boxConnection.SerBoxControl;
import boxConnection.SerBoxControlDefault;
import boxConnection.SerBoxControlEnigma;
import boxConnection.SerBoxControlNeutrino;
import service.SerAlertDialog;
import service.SerLogAppender;
import service.SerXMLConverter;
import service.SerXMLHandling;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

/**
 * @author Alexander Geist
 *
 * Startklasse, Start der Anwendung
 * Deklaration der globalen Variablen
 */
public class ControlMain {
	
	static BOSettings settings;
	static Document settingsDocument;
	static SerBoxControl box;
	static SerLogAppender logAppender;
	static ControlMainView control;
	static int CurrentBox=0;
	
	static Locale locale = new Locale("de","DE");
    private static Properties prop = new Properties();
    
    public static String filename = "settings.xml";
    public static String _MESSAGE_BUNDLE = "locale/messages_"+locale.getLanguage()+".properties.";
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

	public static void main( String args[] ) {
		control = new ControlMainView();
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
		
	public static void detectImage() {
		Logger.getLogger("ControlMain").info("Searching Box-Image");
		int image=SerBoxControl.ConnectBox(ControlMain.getBoxIp());
		if (image==0) {
			box=new SerBoxControlDefault();
		}
		if (image==3) {
			box=new SerBoxControlDefault();
		}
		if (image==1) {
			box = new SerBoxControlNeutrino();
		}
		else if (image==2) {
			box = new SerBoxControlEnigma();
		}
		Logger.getLogger("ControlMain").info(ControlMain.getBox().getName()+"-Access loaded");
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
				//mainLogger.info("Settings found");
			} else {
				settingsDocument = SerXMLHandling.buildEmptyXMLFile(pathToXMLFile);
				Logger.getLogger("ControlMain").info("Settings not found, created empty document");
			}
			setSettings(SerXMLConverter.buildSettings(getSettingsDocument()));
		} catch (Exception ex) {SerAlertDialog.alert("Fehler beim Zugriff auf die "+filename+" Datei", control.getView());}
	}
	
	public static String getBoxIp() {
		String boxIp = new String();
		ArrayList boxList = getSettings().getBoxList();
		for (int i=0; boxList.size()>i; i++) {
			BOBox box = (BOBox)boxList.get(i);
			if (box.isStandard().booleanValue()) {
				boxIp = box.getDboxIp();
			}
		}
		return boxIp;
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
	 * @return Returns the currentBox.
	 */
	public static int getCurrentBox() {
		return CurrentBox;
	}
	/**
	 * @param currentBox The currentBox to set.
	 */
	public static void setCurrentBox(int currentBox) {
		CurrentBox = currentBox;
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
	public static SerBoxControl getBox() {
		return box;
	}
	/**
	 * @param box The box to set.
	 */
	public static void setBox(SerBoxControl box) {
		ControlMain.box = box;
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
	private void setLocale(String sprache, String land){
        locale = new Locale(sprache,land);    	
    }

    private Locale getLocale(){
        return locale;
    }

    public static String getProperty(String key){
    	return prop.getProperty(key);
    }

    public static void setResourceBundle(Locale loc){
        ControlMain.locale=loc;
        try{    	                 	
        	File f = new File(_MESSAGE_BUNDLE).getAbsoluteFile();        	
        	InputStream is = new FileInputStream(f);
        	prop.load(is);                    	
        }catch (IOException ex){}       	        
    }    
}
