package control;

import java.io.File;
import java.io.IOException;

import model.BOSettings;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.dom4j.Document;

import presentation.GuiMainView;
import service.SerAlertDialog;
import service.SerBoxControl;
import service.SerBoxControlDefault;
import service.SerBoxControlEnigma;
import service.SerBoxControlNeutrino;
import service.SerLogAppender;
import service.SerXMLConverter;
import service.SerXMLHandling;

/**
 * @author AlexG
 *
 *Startklasse, hier wird die Anwendung initialisiert und gestartet
 */
public class ControlMain {
	
	GuiMainView view;
	ControlMain control;
	static Logger logger;
	static BOSettings settings;
	static Document settingsDocument;
	static SerBoxControl box;
	static int CurrentBox=0;
	
	public static void main( String args[] ) {
		ControlMain control = new ControlMain();
		control.init();
		control.setView(new GuiMainView(control));
		control.startLogger();
		getLogger().info("Anwendung gestartet");
	};
	
	private void init() {
		//lesen und Setzen des Settings-XMLdokuments beim Start der Anwendung
		this.readSettings();
		//Aufbereitung des Settings-XML-Dokuments
		setSettings((SerXMLConverter.buildSettings(getSettingsDocument())));
		this.detectImage();
	}
	
	private void startLogger() {
		PatternLayout layout = new PatternLayout();
		//http://logging.apache.org/log4j/docs/api/org/apache/log4j/PatternLayout.html
		layout.setConversionPattern("%d{HH:mm:ss,SSS} %m%n");
		
		logger = Logger.getLogger("Logging");
		SerLogAppender logApp;
		try {
			logApp = new SerLogAppender(layout);
			logApp.setView(this.getView());
			BasicConfigurator.configure(logApp);
		} catch (IOException e) {}
	}
	
	private void detectImage() {
		int image=SerBoxControl.ConnectBox(ControlMain.getBoxIp());
		if (image==0) {
			box=new SerBoxControlDefault();
		}
		if (image==3) {
			box=new SerBoxControlDefault();
			SerAlertDialog.alert("No supported Image detected!!", this.getView());
		}
		else if (image==1)
			box = new SerBoxControlNeutrino();
		else if (image==2)
			box = new SerBoxControlEnigma();
	}
	
	/**
	 * Settings aus dem XML-File lesen. Falls Keines vorhanden ein leeres Dokument erstellen.
	 * Dokument wird immer gebraucht, falls neue Settings gespeichert werden.
	 */
	private void readSettings() {
		try {
				File pathToXMLFile = new File("settings.xml").getAbsoluteFile();
				if (pathToXMLFile.exists()) {
					settingsDocument = SerXMLHandling.readDocument(pathToXMLFile);
				} else {
					settingsDocument = SerXMLHandling.buildEmptyXMLFile(pathToXMLFile);
				}
		} catch (Exception ex) { SerAlertDialog.alert("Fehler beim Zugriff auf die settings.xml Datei", this.getView());}
	}
	
	public static String getBoxIp() {
		return (String)getSettings().getDboxIp();
	}
	public static String getVlcPath() {
		return (String)getSettings().getVlcPath();
	}
	public static void setBoxIp(String boxIp) {
		getSettings().setDboxIp(boxIp);
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
	 * @return Returns the control.
	 */
	public ControlMain getControl() {
		return control;
	}
	/**
	 * @param control The control to set.
	 */
	public void setControl(ControlMain control) {
		this.control = control;
	}
	/**
	 * @return Returns the settingsDocument.
	 */
	public static Document getSettingsDocument() {
		return settingsDocument;
	}
	/**
	 * @return Returns the view.
	 */
	public GuiMainView getView() {
		return view;
	}
	/**
	 * @param view The view to set.
	 */
	public void setView(GuiMainView view) {
		this.view = view;
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
	 * @return Returns the logger.
	 */
	public static Logger getLogger() {
		return logger;
	}
	/**
	 * @param logger The logger to set.
	 */
	public static void setLogger(Logger logger) {
		ControlMain.logger = logger;
	}
}
