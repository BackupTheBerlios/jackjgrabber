package presentation;

import javax.swing.JTabbedPane;

import control.ControlProgramTab;
import control.ControlProjectXTab;
import control.ControlSettingsTab;
import control.ControlTimerTab;

public class GuiMainTabPane extends JTabbedPane {

	public GuiTabProgramm tabProgramm = null;
	private GuiTabSettings tabSettings = null;
	private GuiTabTimer tabTimer=null;
	private GuiTabProjectX tabProjectX=null;
	private GuiMainView view;

	
	public GuiMainTabPane(GuiMainView mainView) {
		super();
		this.setView(mainView);
	}
	
	/**
	 * Aufbau der Tabs "Programm"
	 * Initialisierung des Controls passiert extern, da dieser Tab 
	 * automatisch beim Aufbau der Gui angewählt wird
	 */    
	public GuiTabProgramm getTabProgramm() {
		if (tabProgramm == null) {
			ControlProgramTab control = new ControlProgramTab(this.getView());
			tabProgramm = new GuiTabProgramm(control);
		}
		return tabProgramm;
	}
	/**
	 * Aufbau des Tabs Timerliste		
	 */    
	public GuiTabTimer getTabTimer() {
		if (tabTimer == null) {
			ControlTimerTab control = new ControlTimerTab(this.getView());
			tabTimer = new GuiTabTimer(control);
			control.initialize();
		}
		return tabTimer;
	}
	
	/**
	 * Aufbau des Tabs Settings		
	 */    
	public GuiTabSettings getTabSettings() {
		if (tabSettings == null) {
			ControlSettingsTab control = new ControlSettingsTab(this.getView());
			tabSettings = new GuiTabSettings(control);
			control.initialize();
		}
		return tabSettings;
	}
	
	/**
	 * Aufbau des Tabs ProjectX	
	 */    
	public GuiTabProjectX getTabProjectX() {
		if (tabProjectX == null) {
			ControlProjectXTab control = new ControlProjectXTab(this.getView());
			tabProjectX = new GuiTabProjectX(control);
			control.initialize();
		}
		return tabProjectX;
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
}
