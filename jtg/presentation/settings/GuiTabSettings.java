package presentation.settings;
/*
GuiTabSettings.java by Geist Alexander 

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
import presentation.GuiTab;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import control.ControlMain;
import control.ControlSettingsTab;

public class GuiTabSettings extends GuiTab {

	private ControlSettingsTab control;
	private GuiSettingsTabPane settingsTabPane;

	public GuiTabSettings(ControlSettingsTab ctrl) {
		super();
		this.setControl(ctrl);
		initialize();
	}

	protected void initialize() {
		FormLayout layout = new FormLayout( "f:pref:grow", "f:pref:grow");
		PanelBuilder builder = new PanelBuilder(this, layout);
		CellConstraints cc = new CellConstraints();
		
		builder.add(this.getSettingsTabPane(),		   		cc.xy(1,1));
	}
	
	/**
	 * Haupt-TabPane. Neue Tabs werden hier angemeldet.
	 */    
	public GuiSettingsTabPane getSettingsTabPane() {
		if (settingsTabPane == null) {
		    settingsTabPane = new GuiSettingsTabPane(this);
		    settingsTabPane.addChangeListener(control);
			
		    settingsTabPane.addTab(ControlMain.getProperty("label_general"), settingsTabPane.getTabSettingsMain());
		    settingsTabPane.addTab(ControlMain.getProperty("label_record"), settingsTabPane.getTabSettingsRecord());
		    settingsTabPane.addTab(ControlMain.getProperty("label_playback"), settingsTabPane.getTabSettingsPlayback());
		    settingsTabPane.addTab(ControlMain.getProperty("tab_movieGuide"), settingsTabPane.getTabSettingsMovieguide());
		    settingsTabPane.addTab(ControlMain.getProperty("tab_path"), settingsTabPane.getTabSettingsPath());
		    
		}
		return settingsTabPane;
	}
	
	public GuiSettingsTabMain getSettingsTabMain() {
	    return this.getSettingsTabPane().getTabSettingsMain();
	}
	public GuiSettingsTabRecord getSettingsTabRecord() {
	    return this.getSettingsTabPane().getTabSettingsRecord();
	}
	public GuiSettingsTabPlayback getSettingsTabPlayback() {
	    return this.getSettingsTabPane().getTabSettingsPlayback();
	}
	public GuiSettingsTabMovieGuide getSettingsTabMovieGuide() {
	    return this.getSettingsTabPane().getTabSettingsMovieguide();
	}
	public GuiSettingsTabPath getSettingsTabPath() {
	    return this.getSettingsTabPane().getTabSettingsPath();
	}
	
	/**
	 * @return Returns the control.
	 */
	public ControlSettingsTab getControl() {
		return control;
	}
	/**
	 * @param control The control to set.
	 */
	public void setControl(ControlSettingsTab control) {
		this.control = control;
	}
}
