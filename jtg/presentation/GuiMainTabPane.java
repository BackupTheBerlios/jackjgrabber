package presentation;

import javax.swing.JTabbedPane;

import control.ControlMain;
import control.ControlProgramTab;
import control.ControlProjectXTab;
import control.ControlSettingsTab;

public class GuiMainTabPane extends JTabbedPane {

	public GuiTabProgramm tabProgramm = null;
	public GuiTabSettings tabSettings = null;
	public GuiTimerPanel tabTimer=null;
	public GuiTabProjectX tabProjectX=null;
	public GuiMainView view;
	int index;

	
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
	 * Image-spezifische Timer-Gui		
	 */    
	public GuiTimerPanel getTabTimer() {
		if (tabTimer == null) {
			tabTimer = GuiTimerPanel.getTimerPanel(ControlMain.getBoxAccess().getName(), this.getView());
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
	/**
	 * @return Returns the index.
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @param index The index to set.
	 */
	public void setIndex(int index) {
		this.index = index;
	}
}
