package presentation;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import service.SerGUIUtils;
import control.ControlMain;
import control.ControlProgramTab;
import control.ControlSettingsTab;

/*
 * Created on 31.08.2004
 * Haupt-Gui, hier werden die einzelnen Tabs verwaltet
 */
public class GuiMainView extends JFrame {

	
	private JTabbedPane mainTabPane = null;
	public GuiTabProgramm tabProgramm = null;
	private GuiTabSettings tabSettings = null;
	private ControlMain control;    
	
	/**
	 * This is the default constructor
	 */
	public GuiMainView(ControlMain ctrl) {
		super();
		control = ctrl;
		initialize();
		SerGUIUtils.center(this);
		this.setVisible(true);
		
	}
	/**
	 * This method initializes this
	 */
	private void initialize() {
		this.setForeground(java.awt.SystemColor.windowText);
		this.setResizable(false);
		this.setSize(785, 555);
		this.setTitle("Jack (the Grabber) Jr. 0.1");
		this.setContentPane(getMainTabPane());
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		
	}
	/**
	 * Haupt-TabPane. Neue Tabs werden hier angemeldet.
	 */    
	private JTabbedPane getMainTabPane() {
		if (mainTabPane == null) {
			mainTabPane = new JTabbedPane();
			mainTabPane.setBackground(new java.awt.Color(204,204,204));
			mainTabPane.setComponentOrientation(java.awt.ComponentOrientation.LEFT_TO_RIGHT);
			mainTabPane.setForeground(java.awt.Color.black);
			mainTabPane.setDoubleBuffered(true);
			
			mainTabPane.addTab("Programm", null, getTabProgramm(), null);
			mainTabPane.addTab("Settings", null, getTabSettings(), null);
		}
		return mainTabPane;
	}
	/**
	 * Aufbau der Tabs "Programm"
	 * 1. Beim Start des Controls werden die ben�tigen Objekte aufgebaut
	 * 2. Anzeige der aufgebauten Objekte	
	 */    
	public GuiTabProgramm getTabProgramm() {
		if (tabProgramm == null) {
			ControlProgramTab control = new ControlProgramTab(this);
			tabProgramm = new GuiTabProgramm(control);
		}
		return tabProgramm;
	}
	
	/**
	 * Aufbau des Tabs Settings		
	 */    
	public GuiTabSettings getTabSettings() {
		if (tabSettings == null) {
			ControlSettingsTab control = new ControlSettingsTab(this);
			tabSettings = new GuiTabSettings(control);
		}
		return tabSettings;
	}
}
