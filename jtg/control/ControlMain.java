package control;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.BOSettings;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.dom4j.Document;

import boxConnection.SerBoxControl;
import boxConnection.SerBoxControlDefault;
import boxConnection.SerBoxControlEnigma;
import boxConnection.SerBoxControlNeutrino;

import presentation.GuiMainTabPane;
import presentation.GuiMainView;
import presentation.GuiTerms;
import service.SerAlertDialog;
import service.SerLogAppender;
import service.SerXMLConverter;
import service.SerXMLHandling;

/**
 * @author Alexander Geist
 *
 *Startklasse, hier wird die Anwendung initialisiert und gestartet
 */
public class ControlMain implements ActionListener, ChangeListener {
	
	GuiMainView view;
	ControlMain control;
	GuiTerms guiTerms;
	static BOSettings settings;
	static Document settingsDocument;
	static SerBoxControl box;
	static int CurrentBox=0;
	private Logger mainLogger;
	private SerLogAppender logAppender;
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
		ControlMain control = new ControlMain();
		control.showTerms();
	};
	
	private void showTerms() {
		guiTerms = new GuiTerms(this);
		guiTerms.setVisible(true);
	}
	
	private void runAfterTerms() {
		this.setView(new GuiMainView(this));
		this.startLogger();
		this.getLogAppender().setView(this.getView());
		this.init();
		this.getView().getMainTabPane().getTabProgramm().getControl().initialize();
		this.getMainLogger().info("Anwendung gestartet");
	}
	
	private void init() {
		this.writeSystemInfo();
		//lesen und Setzen des Settings-XMLdokuments beim Start der Anwendung
		this.readSettings();
		//Aufbereitung des Settings-XML-Dokuments
		setSettings((SerXMLConverter.buildSettings(getSettingsDocument())));
//		detect the type of the box-image
		mainLogger.info("Searching Box-Image");
		this.detectImage();
		mainLogger.info(getBox().getName()+"-Access loaded");
	}
	
	private void writeSystemInfo() {
		mainLogger.info(version[0]+"/"+version[1]+" "+version[2]+" "+version[3]);
		mainLogger.info("java.version\t"+System.getProperty("java.version"));
		mainLogger.info("java.vendor\t"+System.getProperty("java.vendor"));
		mainLogger.info("java.home\t"+System.getProperty("java.home"));
		mainLogger.info("java.vm.version\t"+System.getProperty("java.vm.version"));
		mainLogger.info("java.vm.vendor\t"+System.getProperty("java.vm.vendor"));
		mainLogger.info("java.vm.name\t"+System.getProperty("java.vm.name"));
		mainLogger.info("java.class.vers\t"+System.getProperty("java.class.version"));
		mainLogger.info("java.class.path\t"+System.getProperty("java.class.path"));
	}
	
	public void javaEV() {
		mainLogger.info("  "+java.text.DateFormat.getTimeInstance(java.text.DateFormat.FULL).format(new Date()));
		mainLogger.info("\njava.version\t"+System.getProperty("java.version"));
		mainLogger.info("\njava.vendor\t"+System.getProperty("java.vendor"));
		mainLogger.info("\njava.home\t"+System.getProperty("java.home"));
		mainLogger.info("\njava.vm.version\t"+System.getProperty("java.vm.version"));
		mainLogger.info("\njava.vm.vendor\t"+System.getProperty("java.vm.vendor"));
		mainLogger.info("\njava.vm.name\t"+System.getProperty("java.vm.name"));
		mainLogger.info("\njava.class.vers\t"+System.getProperty("java.class.version"));
		mainLogger.info("\njava.class.path\t"+System.getProperty("java.class.path"));
		mainLogger.info("\nos.name\t"+System.getProperty("os.name"));
		mainLogger.info("\nos.arch\t"+System.getProperty("os.arch"));
		mainLogger.info("\nos.version\t"+System.getProperty("os.version"));
		mainLogger.info("\nuser.name\t"+System.getProperty("user.name"));
		mainLogger.info("\nuser.home\t"+System.getProperty("user.home"));
	}

	
	public void startLogger() {
		PatternLayout layout = new PatternLayout();
		
		//http://logging.apache.org/log4j/docs/api/org/apache/log4j/PatternLayout.html
		layout.setConversionPattern("%d{HH:mm:ss} %-5p %c - %m%n");
		
		mainLogger = Logger.getLogger("ControlMain");
		SerLogAppender logApp;
		try {
			setLogAppender(new SerLogAppender(layout));
			getLogAppender().setMaxBackupIndex(3); //Number of max Backup-Files
			getLogAppender().setMaxFileSize("500KB");
			BasicConfigurator.configure(getLogAppender());
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
					mainLogger.info("Settings found");
				} else {
					settingsDocument = SerXMLHandling.buildEmptyXMLFile(pathToXMLFile);
					mainLogger.info("Settings not found, created empty document");
				}
		} catch (Exception ex) { SerAlertDialog.alert("Fehler beim Zugriff auf die settings.xml Datei", this.getView());}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String actName = e.getActionCommand();

		if (actName.equals("I agree"))  {
			guiTerms.close();
			this.runAfterTerms();
		} else if (actName.equals("I disagree (closing)")) {
			System.exit(0);
		}
	}
	
	/**
	 * Change-Events des TabPane
	 */
	public void stateChanged(ChangeEvent event) {
		GuiMainTabPane pane = (GuiMainTabPane)event.getSource();
		int count = pane.getSelectedIndex(); //number of selected Tab
		JPanel comp = (JPanel)pane.getComponent(count);
		
		if (count == 0) { //ProgrammTab
			comp.add(pane.getTabProgramm());
		}
		if (count == 1) { //TimerTab
			comp.add(pane.getTabTimer());
		}
		if (count == 2) { //ProjectXTab
			comp.add(pane.getTabProjectX());
		}
		if (count == 3) { //SettingsTab
			comp.add(pane.getTabSettings());
		}
	}
	
	public static String[] getTerms()
	{
		return terms;
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
	private Logger getMainLogger() {
		return mainLogger;
	}
	/**
	 * @param logger The logger to set.
	 */
	private void setMainLogger(Logger logger) {
		mainLogger = logger;
	}
	/**
	 * @return SerLogAppender
	 */
	public SerLogAppender getLogAppender() {
		return logAppender;
	}

	/**
	 * Sets the logAppender.
	 * @param logAppender The logAppender to set
	 */
	public void setLogAppender(SerLogAppender appender) {
		logAppender = appender;
	}

}
