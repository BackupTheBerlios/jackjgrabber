package presentation;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import service.SerGUIUtils;

import com.jgoodies.plaf.plastic.Plastic3DLookAndFeel;
import com.jgoodies.plaf.plastic.PlasticLookAndFeel;
import com.jgoodies.plaf.plastic.theme.Silver;

import control.ControlMain;
import control.ControlProgramTab;
import control.ControlSettingsTab;
import control.ControlTimerTab;

/*
 * Created on 31.08.2004
 * Haupt-Gui, hier werden die einzelnen Tabs verwaltet
 */
public class GuiMainView extends JFrame {

	
	private JTabbedPane mainTabPane = null;
	public GuiTabProgramm tabProgramm = null;
	private GuiTabSettings tabSettings = null;
	private GuiTabTimer tabTimer=null;
	private ControlMain control;    
	
	public GuiMainView(ControlMain ctrl) {
		super();
		PlasticLookAndFeel.setMyCurrentTheme(new Silver());
		try {
			  UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
		} catch (Exception e) {}
		control = ctrl;
		initialize();
		SerGUIUtils.center(this);
		setVisible(true);
		
	}
	/**
	 * This method initializes this
	 */
	private void initialize() {
		this.setForeground(java.awt.SystemColor.windowText);
		this.setResizable(false);
		this.setSize(785, 555);
		this.setTitle(ControlMain.version[0]+"/"+ControlMain.version[1]+
				" "+ControlMain.version[2]+" "+ControlMain.version[3]);
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
			mainTabPane.addTab("Timerliste", null, getTabTimer(), null);
			mainTabPane.addTab("Settings", null, getTabSettings(), null);
		}
		return mainTabPane;
	}
	/**
	 * Aufbau der Tabs "Programm"
	 * Aufbau der Gui, Start des Controls
	 */    
	public GuiTabProgramm getTabProgramm() {
		if (tabProgramm == null) {
			ControlProgramTab control = new ControlProgramTab(this);
			tabProgramm = new GuiTabProgramm(control);
		}
		return tabProgramm;
	}
	
	/**
	 * Aufbau des Tabs Timerliste		
	 */    
	public GuiTabTimer getTabTimer() {
		if (tabTimer == null) {
			ControlTimerTab control = new ControlTimerTab(this);
			tabTimer = new GuiTabTimer(control);
		}
		return tabTimer;
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
