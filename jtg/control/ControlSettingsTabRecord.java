/*
ControlSettingsTabRecord.java by Geist Alexander 

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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.BOSettingsRecord;
import presentation.settings.GuiSettingsTabRecord;
import presentation.settings.GuiStreamTypeComboModel;
import presentation.settings.GuiTabSettings;

public class ControlSettingsTabRecord extends ControlTabSettings implements KeyListener, ActionListener, ItemListener, ChangeListener {

    GuiTabSettings settingsTab;
    public final String[] streamTypesJGrabber = { "PES MPEG-Packetized Elementary", "TS MPEG-Transport"	};
	public final String[] streamTypesUdrec = { "PES MPEG-Packetized Elementary", "TS MPEG-Transport", "ES MPEG-Elementary"	};
    
    public ControlSettingsTabRecord (GuiTabSettings tabSettings) {
		this.setSettingsTab(tabSettings);
	}
    
    /* (non-Javadoc)
     * @see control.ControlTab#initialize()
     */
    public void run() {
        this.getTab().getTfServerPort().setText(this.getSettings().getStreamingServerPort());
        this.getTab().getCbStartStreamingServer().setSelected(this.getSettings().isStartStreamingServer());
        this.getTab().getJComboBoxStreamType().setSelectedItem(this.getSettings().getJgrabberStreamType());
        this.getTab().getCbStartPX().setSelected(this.getSettings().isStartPX());
        this.getTab().getCbRecordAllPids().setSelected(this.getSettings().isRecordAllPids());
        this.getTab().getCbRecordVtxt().setSelected(this.getSettings().isRecordVtxt());
        this.getTab().getJSpinnerRecordMinsBefore().setValue(Integer.valueOf(this.getSettings().getRecordTimeBefore()));
        this.getTab().getJSpinnerRecordMinsAfter().setValue(Integer.valueOf(this.getSettings().getRecordTimeAfter()));
        this.getTab().getJTextFieldUdrecOptions().setText(this.getSettings().getUdrecOptions());
        this.getTab().getCbAC3ReplaceStereo().setSelected(this.getSettings().isAc3ReplaceStereo());
        this.getTab().getCbStoreEPG().setSelected(this.getSettings().isStoreEPG());
        this.getTab().getCbStoreLogAfterRecord().setSelected(this.getSettings().isStoreLogAfterRecord());
		this.initializeStreamingEngine();
    }

    private void initializeStreamingEngine() {
		String streamType;
		if (this.getSettings().getStreamingEngine()==0) {
			this.initializeJGrabberEngine();
		} else {
			this.initializeUdrecEngine();
		}
	}
	
	private void initializeJGrabberEngine() {
		this.getTab().getJRadioButtonJGrabber().setSelected(true);
		GuiStreamTypeComboModel streamTypeComboModel = new GuiStreamTypeComboModel(streamTypesJGrabber);
		String streamType = this.getSettings().getJgrabberStreamType();
		this.getTab().getJComboBoxStreamType().setModel(streamTypeComboModel);
		this.getTab().getStreamTypeComboModel().setSelectedItem(streamType);
	}
	
	private void initializeUdrecEngine() {
		this.getTab().getJRadioButtonUdrec().setSelected(true);
		GuiStreamTypeComboModel streamTypeComboModel = new GuiStreamTypeComboModel(streamTypesUdrec);
		String streamType = this.getSettings().getUdrecStreamType();
		this.getTab().getJComboBoxStreamType().setModel(streamTypeComboModel);
		this.getTab().getStreamTypeComboModel().setSelectedItem(streamType);
	}
	
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		while (true) {
		  		if (action.equals("jgrabber")) {
		  			this.getSettings().setStreamingEngine(0);
		  			this.initializeJGrabberEngine();
		  			break;
		  		}
		  		if (action.equals("udrec")) {
		  			this.getSettings().setStreamingEngine(1);
		  			this.initializeUdrecEngine();
		  			break;
		  		}
		  		if (action.equals("storeEPG")) {
		  			this.getSettings().setStoreEPG(((JCheckBox)e.getSource()).isSelected());
		  			break;
		  		}
		  		if (action.equals("storeLogAfterRecord")) {
		  			this.getSettings().setStoreLogAfterRecord(((JCheckBox)e.getSource()).isSelected());
		  			break;
		  		}
		  		if (action.equals("cbAC3ReplaceStereo")) {
		  				this.getSettings().setAc3ReplaceStereo(((JCheckBox)e.getSource()).isSelected());
		  				break;
		  		}
		  		if (action.equals("recordAllPids")) {
		  				this.getSettings().setRecordAllPids(((JCheckBox)e.getSource()).isSelected());
		  				break;
		  		}
		  		if (action.equals("recordVtxt")) {
		  				this.getSettings().setRecordVtxt(((JCheckBox)e.getSource()).isSelected());
		  				break;
		  		}
		  		if (action.equals("startPX")) {
		  				this.getSettings().setStartPX(((JCheckBox)e.getSource()).isSelected());
		  				break;
		  		}
		  		if (action.equals("startStreamingServer")) {
		  				this.getSettings().setStartStreamingServer(((JCheckBox)e.getSource()).isSelected());
		  				break;
		  		}  
		  		break;
		}
	}
	public void keyTyped(KeyEvent event) {}
	
	public void keyPressed(KeyEvent event) {}
	
	public void keyReleased(KeyEvent event) {
		JTextField tf = (JTextField)event.getSource();
		while (true) {
			if (tf.getName().equals("serverPort")){
				this.getSettings().setStreamingServerPort(tf.getText());
				break;
			}
			if (tf.getName().equals("udrecPath")){
			    ControlMain.getSettingsPath().setUdrecPath(tf.getText());
			    break;
			}
			if (tf.getName().equals("udrecOptions")){
			    this.getSettings().setUdrecOptions(tf.getText());
			    break;
			}
			break;
		}
	}
	
	private void actionSetServerPort(ActionEvent event) {
		JTextField tf = (JTextField)event.getSource();
		this.getSettings().setStreamingServerPort(tf.getText());
	}
	
	/*
	 * ChangeEvent der Spinner
	 */
	public void stateChanged(ChangeEvent event) {
		JSpinner spinner = (JSpinner)event.getSource();
		if (spinner.getName().equals("recordBefore")) {
			this.getSettings().setRecordTimeBefore(spinner.getValue().toString());
		}
		if (spinner.getName().equals("recordAfter")) {
			this.getSettings().setRecordTimeAfter(spinner.getValue().toString());
		}
	}

	//Change-Events der Combos und der Checkbox
	public void itemStateChanged (ItemEvent event) {
				JComboBox comboBox = (JComboBox)event.getSource();
				if (event.getStateChange()==1) {
					if (comboBox.getName().equals("streamType")) {
					    this.getSettings().setStreamType((String)comboBox.getSelectedItem());
					}
				}	
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
    
    private BOSettingsRecord getSettings() {
        return ControlMain.getSettingsRecord();
    }
    
    private GuiSettingsTabRecord getTab() {
        return this.getSettingsTab().getSettingsTabRecord();
    }
}
