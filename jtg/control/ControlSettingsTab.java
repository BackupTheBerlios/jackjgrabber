package control;
/*
ControlSettingsTab.java by Geist Alexander 

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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.BOSettings;
import presentation.GuiMainView;
import presentation.GuiSettingsTabPane;


public class ControlSettingsTab extends ControlTab implements ChangeListener {

	GuiMainView mainView;
	BOSettings settings;

	public ControlSettingsTab(GuiMainView view) {
		this.setMainView(view);
	}
	
	public void stateChanged(ChangeEvent event) {
		GuiSettingsTabPane pane = (GuiSettingsTabPane)event.getSource();
		int count = pane.getSelectedIndex(); //number of selected Tab
				
		//Change-Events bei betreten neuer Tabs
		if (count == 0) {
			pane.setComponentAt(count, pane.getTabSettingsMain());
		}
		if (count == 1) {
			pane.setComponentAt(count, pane.getTabSettingsRecord());
		}
		if (count == 2) {
			pane.setComponentAt(count, pane.getTabSettingsPlayback());
		}
		pane.setIndex(count);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see control.ControlTab#initialize()
	 */
	public void initialize() {
	}
	/**
	 * @return Returns the mainView.
	 */
	public GuiMainView getMainView() {
		return mainView;
	}
	/**
	 * @param mainView The mainView to set.
	 */
	public void setMainView(GuiMainView mainView) {
		this.mainView = mainView;
	}
}
