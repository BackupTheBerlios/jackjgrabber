/*
ControlSettingsTabMain.java by Geist Alexander 

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

import javax.swing.JComboBox;

import model.BOBox;
import model.BOSettings;
import presentation.GuiMainView;
import presentation.GuiSettingsTabMain;
import presentation.GuiTabSettings;

public class ControlSettingsTabMain extends ControlTabSettings implements ActionListener, ItemListener{
    
    GuiTabSettings settingsTab;
    
    public ControlSettingsTabMain (GuiTabSettings tabSettings) {
		this.setSettingsTab(tabSettings);
	}
    
    /* (non-Javadoc)
     * @see control.ControlTab#initialize()
     */
    public void initialize() {
		this.getTab().getJComboBoxTheme().setSelectedItem(this.getSettings().getThemeLayout());
		this.getTab().getJComboBoxLocale().setSelectedItem(this.getSettings().getLocale());

    }
    
    public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action == "delete") {
			this.actionRemoveBox();
		}
		if (action == "add") {
			this.actionAddBox();
		}
	}
    
//  Change-Events der Combos und der Checkbox
	public void itemStateChanged (ItemEvent event) {
	    JComboBox comboBox = (JComboBox)event.getSource();
		if (event.getStateChange()==1) {
		    while (true) {
				if (comboBox.getName().equals("theme")) {
				    getSettings().setThemeLayout((String)comboBox.getSelectedItem());
					break;
				}
				if (comboBox.getName().equals("locale")) {
				    getSettings().setLocale((String)comboBox.getSelectedItem());
					break;
				}
				break;		
			}				
		}
	}
    
    private void actionAddBox() {
		BOBox box = new BOBox();
		box.setDboxIp("192.168.001.110"); //defaultwert
		this.getSettingsTab().getSettingsTabMain().getModelBoxTable().addRow(box);
	}
	private void actionRemoveBox() {
		int selectedRow = this.getSettingsTab().getSettingsTabMain().getJTableBoxSettings().getSelectedRow();
		this.getSettingsTab().getSettingsTabMain().getModelBoxTable().removeRow(selectedRow);
	}

    /* (non-Javadoc)
     * @see control.ControlTab#getMainView()
     */
    public GuiTabSettings getSettingsTab() {
        return settingsTab;
    }
    public GuiMainView getMainView() {
        return this.getSettingsTab().getControl().getMainView();
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
    
    private GuiSettingsTabMain getTab() {
        return this.getSettingsTab().getSettingsTabMain();
    }
}
