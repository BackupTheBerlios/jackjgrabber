package control;

import java.io.File;

import model.BOBox;
import model.BOSettings;

import org.apache.log4j.Logger;
import org.dom4j.Document;

import boxConnection.SerBoxControl;
import boxConnection.SerBoxControlDefault;
import boxConnection.SerBoxControlEnigma;
import boxConnection.SerBoxControlNeutrino;
import service.SerAlertDialog;
import service.SerXMLConverter;
import service.SerXMLHandling;

import java.util.ArrayList;

/**
 * @author Alexander Geist
 *
 * Startklasse, Start der Anwendung
 * Deklaration der globalen Variablen
 */
public class ControlMain {
	
	static BOSettings settings;
	public static String filename = "settings.xml";
	static Document settingsDocument;
	static SerBoxControl box;
	static ControlMainView control;
	static int CurrentBox=0;
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
	
	public static void detectImage() {
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
}
