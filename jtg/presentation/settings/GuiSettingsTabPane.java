package presentation.settings;
/*
GuiSettingsTabPane.java by Geist Alexander 

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
import javax.swing.JTabbedPane;

import control.*;
import control.ControlSettingsTabMain;
import control.ControlSettingsTabPlayback;
import control.ControlSettingsTabRecord;


public class GuiSettingsTabPane extends JTabbedPane {

	public GuiSettingsTabMain tabSettingsMain = null;
	public GuiSettingsTabRecord tabSettingsRecord = null;
	public GuiSettingsTabPlayback tabSettingsPlayback = null;
	public GuiSettingsTabMovieGuide tabSettingsMovieguide = null;
	
	public GuiTabSettings tabSettings;
	int index;

	
	public GuiSettingsTabPane(GuiTabSettings tabSettings) {
		super();
		this.setTabPlacement(JTabbedPane.LEFT);
		this.setTabSettings(tabSettings);
	}
 
	/**
	 * Aufbau des Tabs Settings		
	 */    
	public GuiSettingsTabMain getTabSettingsMain() {
		if (tabSettingsMain == null) {
		    ControlSettingsTabMain control = new ControlSettingsTabMain(this.getTabSettings());
			tabSettingsMain = new GuiSettingsTabMain(control);
			control.run();
		}
		return tabSettingsMain;
	}
	
	public GuiSettingsTabRecord getTabSettingsRecord() {
		if (tabSettingsRecord == null) {
		    ControlSettingsTabRecord control = new ControlSettingsTabRecord(this.getTabSettings());
		    tabSettingsRecord = new GuiSettingsTabRecord(control);
			control.run();
		}
		return tabSettingsRecord;
	}
	
	public GuiSettingsTabPlayback getTabSettingsPlayback() {
		if (tabSettingsPlayback == null) {
		    ControlSettingsTabPlayback control = new ControlSettingsTabPlayback(this.getTabSettings());
		    tabSettingsPlayback = new GuiSettingsTabPlayback(control);
			control.run();
		}
		return tabSettingsPlayback;
	}
	
	public GuiSettingsTabMovieGuide getTabSettingsMovieguide() {
		if (tabSettingsMovieguide == null) {
		    ControlSettingsTabMovieGuide control = new ControlSettingsTabMovieGuide(this.getTabSettings());
		    tabSettingsMovieguide = new GuiSettingsTabMovieGuide(control);
			control.run();
		}
		return tabSettingsMovieguide;
	}	
	
	/**
	 * @return Returns the tabSettings.
	 */
	public GuiTabSettings getTabSettings() {
		return tabSettings;
	}
	/**
	 * @param tabSettings The tabSettings to set.
	 */
	public void setTabSettings(GuiTabSettings view) {
		this.tabSettings = view;
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
