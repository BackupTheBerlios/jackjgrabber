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
import java.io.File;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import model.BOSettings;
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
        this.getTab().getJTextFieldRecordSavePath().setText(this.getSettings().getSavePath());
        this.getTab().getJTextFieldProjectXPath().setText(this.getSettings().getProjectXPath());
        this.getTab().getCbStartStreamingServer().setSelected(this.getSettings().isStartStreamingServer());
        this.getTab().getJComboBoxStreamType().setSelectedItem(this.getSettings().getJgrabberStreamType());
        this.getTab().getCbStartPX().setSelected(this.getSettings().isStartPX());
        this.getTab().getCbRecordAllPids().setSelected(this.getSettings().isRecordAllPids());
        this.getTab().getJSpinnerRecordMinsBefore().setValue(Integer.valueOf(this.getSettings().getRecordTimeBefore()));
        this.getTab().getJSpinnerRecordMinsAfter().setValue(Integer.valueOf(this.getSettings().getRecordTimeAfter()));
        this.getTab().getJTextFieldUdrecOptions().setText(this.getSettings().getUdrecOptions());
        this.getTab().getCbAC3ReplaceStereo().setSelected(this.getSettings().isAc3ReplaceStereo());
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
		this.getTab().getJTextFieldUdrecPath().setText(this.getSettings().getUdrecPath());
	}
	
	private void initializeUdrecEngine() {
		this.getTab().getJRadioButtonUdrec().setSelected(true);
		GuiStreamTypeComboModel streamTypeComboModel = new GuiStreamTypeComboModel(streamTypesUdrec);
		String streamType = this.getSettings().getUdrecStreamType();
		this.getTab().getJComboBoxStreamType().setModel(streamTypeComboModel);
		this.getTab().getStreamTypeComboModel().setSelectedItem(streamType);
		this.getTab().getJTextFieldUdrecPath().setText(this.getSettings().getUdrecPath());
	}
	
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action == "recordPath") {
			this.openRecordPathFileChooser();
		}
		if (action == "udrecPath") {
			this.openUdrecPathFileChooser();
		}
		if (action == "jgrabber") {
			this.getSettings().setStreamingEngine(0);
			this.initializeJGrabberEngine();
		}
		if (action == "udrec") {
			this.getSettings().setStreamingEngine(1);
			this.initializeUdrecEngine();
		}
		if (action == "projectxPath") {
			this.openProjectXPathFileChooser();
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
			    this.getSettings().setUdrecPath(tf.getText());
			    break;
			}
			if (tf.getName().equals("udrecOptions")){
			    this.getSettings().setUdrecOptions(tf.getText());
			    break;
			}
			break;
		}
	}
	
	private void actionSetPlaybackString(ActionEvent event) {
		JTextField tf = (JTextField)event.getSource();
		this.getSettings().setPlaybackString(tf.getText());
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
		String comp = event.getSource().getClass().getName();
		while (true) {
			if (comp.equals("javax.swing.JCheckBox")) {
				JCheckBox checkBox = (JCheckBox)event.getSource();
				if (checkBox.getName().equals("startStreamingServer")) {
					this.getSettings().setStartStreamingServer(checkBox.isSelected());
					break;
				}
				if (checkBox.getName().equals("startPX")) {
				    this.getSettings().setStartPX(checkBox.isSelected());
					break;
				}
				if (checkBox.getName().equals("recordAllPids")) {
				    this.getSettings().setRecordAllPids(checkBox.isSelected());
					break;
				}
				if (checkBox.getName().equals("cbAC3ReplaceStereo")) {
				    this.getSettings().setAc3ReplaceStereo(checkBox.isSelected());
					break;
				}
				break;
			} else {
				JComboBox comboBox = (JComboBox)event.getSource();
				if (event.getStateChange()==1) {
					if (comboBox.getName().equals("streamType")) {
					    this.getSettings().setStreamType((String)comboBox.getSelectedItem());
						break;
					}
				}	
				break;
			}	
		}
		
	}

	private void openRecordPathFileChooser() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setDialogType(JFileChooser.SAVE_DIALOG);

		fc.setApproveButtonText(ControlMain.getProperty("msg_choose"));
		fc.setApproveButtonToolTipText( ControlMain.getProperty("msg_chooseDirectory"));
		int returnVal = fc.showSaveDialog( null ) ;

		if ( returnVal == JFileChooser.APPROVE_OPTION )
			{
				String path = fc.getSelectedFile().toString();
				this.getTab().getJTextFieldRecordSavePath().setText(path);
				ControlMain.getSettings().setSavePath(path);
			}
	}
	
	private void openProjectXPathFileChooser() {
		JFileChooser chooser = new JFileChooser();
		chooser.setApproveButtonText(ControlMain.getProperty("msg_choose"));
		chooser.setApproveButtonToolTipText(ControlMain.getProperty("msg_pathProjectX"));
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		FileFilter filter = new FileFilter(){
			public boolean accept(File f){
				return (f.getName().endsWith("ProjectX.jar") || f.isDirectory() );
			}
			public String getDescription(){
				return "ProjectX.jar";
			}
		};
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog( null ) ;
	
		if ( returnVal == JFileChooser.APPROVE_OPTION ) {
			String path = chooser.getSelectedFile().toString();
			this.getTab().getJTextFieldProjectXPath().setText(path);
			ControlMain.getSettings().setProjectXPath(path);	
		}
	}
	
	
	private void openUdrecPathFileChooser() {
		JFileChooser chooser = new JFileChooser();
		chooser.setApproveButtonText(ControlMain.getProperty("msg_choose"));
		chooser.setApproveButtonToolTipText(ControlMain.getProperty("msg_pathUdrec"));
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		FileFilter filter = new FileFilter(){
			public boolean accept(File f){
				return (f.getName().endsWith("udrec.exe") || f.isDirectory() );
			}
			public String getDescription(){
				return "udrec.exe";
			}
		};
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog( null ) ;
	
		if ( returnVal == JFileChooser.APPROVE_OPTION ) {
			String path = chooser.getSelectedFile().toString();
			this.getTab().getJTextFieldUdrecPath().setText(path);
			ControlMain.getSettings().setUdrecPath(path);	
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
    
    private BOSettings getSettings() {
        return ControlMain.getSettings();
    }
    
    private GuiSettingsTabRecord getTab() {
        return this.getSettingsTab().getSettingsTabRecord();
    }
}
