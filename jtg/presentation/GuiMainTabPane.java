package presentation;
/*
GuiMainTabPane.java by Geist Alexander 

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
import javax.swing.JPanel;
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
			if (ControlMain.getBoxAccess() != null) {
				tabTimer = GuiTimerPanel.getTimerPanel(ControlMain.getBoxAccess().getName(), this.getView());
			} else {
			    tabTimer = GuiTimerPanel.getTimerPanel("Default", this.getView());
			}
		}
		return tabTimer;
	}
	
	public void reInitTimerPanel() {
		this.tabTimer=null;
		JPanel panel = (JPanel)this.getComponent(1);
		panel.removeAll();
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
	 * Keine Parameter, da Start nicht nach einer Aufnahme
	 */    
	public GuiTabProjectX getTabProjectX() {
		if (tabProjectX == null) {
			ControlProjectXTab control = new ControlProjectXTab(this.getView(), null);
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
	/**
	 * Sets the tabProjectX.
	 * @param tabProjectX 
	 * Tab wird nach Aufnahme gesetzt 
	 */
	public void setTabProjectX(GuiTabProjectX tabProjectX) {
		this.tabProjectX = tabProjectX;
	}

}
