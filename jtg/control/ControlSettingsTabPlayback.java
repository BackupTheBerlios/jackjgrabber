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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import model.BOSettings;
import presentation.GuiMainView;
import presentation.GuiSettingsTabPlayback;
import presentation.GuiTabSettings;

public class ControlSettingsTabPlayback extends ControlTabSettings implements KeyListener{
    
    GuiTabSettings settingsTab;
    
    public ControlSettingsTabPlayback (GuiTabSettings tabSettings) {
		this.setSettingsTab(tabSettings);
	}
    
    /* (non-Javadoc)
     * @see control.ControlTab#initialize()
     */
    public void initialize() {
        this.getTab().getJTextFieldPlaybackString().setText(this.getSettings().getPlaybackString());

    }

    public void keyTyped(KeyEvent event) {}
	
	public void keyPressed(KeyEvent event) {}
	
	public void keyReleased(KeyEvent event) {
		JTextField tf = (JTextField)event.getSource();
		if (tf.getName().equals("playbackString")){
			this.getSettings().setPlaybackString(tf.getText());
		}
	}
    
    private void actionSetPlaybackString(ActionEvent event) {
		JTextField tf = (JTextField)event.getSource();
		this.getSettings().setPlaybackString(tf.getText());
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
