/*
ControlSettingsTabPlayback.java by Geist Alexander 

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
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

import model.BOPlaybackOption;
import model.BOSettings;
import presentation.GuiMainView;
import presentation.GuiSettingsTabPlayback;
import presentation.GuiTabSettings;

public class ControlSettingsTabPlayback extends ControlTabSettings implements ActionListener, ItemListener{
    
    GuiTabSettings settingsTab;
    
    public ControlSettingsTabPlayback (GuiTabSettings tabSettings) {
		this.setSettingsTab(tabSettings);
	}
    
    /* (non-Javadoc)
     * @see control.ControlTab#initialize()
     */
    public void initialize() {
        this.getTab().getCbUseStandardOption().setSelected(this.getSettings().isAlwaysUseStandardPlayback());
    }

    public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action == "delete") {
			this.actionRemoveOption();
		}
		if (action == "add") {
			this.actionAddOption();
		}
	}
    
//  Change-Events der der Checkbox
	public void itemStateChanged (ItemEvent event) {
	    JCheckBox checkBox = (JCheckBox)event.getSource();
		while (true) {				
			if (checkBox.getName().equals("useStandard")) {
				this.getSettings().setAlwaysUseStandardPlayback(checkBox.isSelected());
				break;
			}
			break;
		}	
	}

    
    private void actionAddOption() {
		BOPlaybackOption option = new BOPlaybackOption();
		this.getSettingsTab().getSettingsTabPlayback().getPlaybackSettingsTableModel().addRow(option);
	}
	private void actionRemoveOption() {
		int selectedRow = this.getSettingsTab().getSettingsTabPlayback().getJTablePlaybackSettings().getSelectedRow();
		this.getSettingsTab().getSettingsTabPlayback().getPlaybackSettingsTableModel().removeRow(selectedRow);
	}
 
    public GuiMainView getMainView() {
        return this.getSettingsTab().getControl().getMainView();
    }
    
    /* (non-Javadoc)
     * @see control.ControlTab#getMainView()
     */
    public GuiTabSettings getSettingsTab() {
        return settingsTab;
    }

    /* (non-Javadoc)
     * @see control.ControlTab#setMainView(presentation.GuiMainView)
     */
    public void setSettingsTab(GuiTabSettings tabSettings) {
        settingsTab = tabSettings;
    }
    private BOSettings getSettings() {
        return ControlMain.getSettings();
    }
    
    private GuiSettingsTabPlayback getTab() {
        return this.getSettingsTab().getSettingsTabPlayback();
    }
}
