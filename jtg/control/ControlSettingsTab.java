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
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import model.BOBox;
import model.BOSettings;

import presentation.GuiMainView;
import presentation.GuiStreamTypeComboModel;


public class ControlSettingsTab extends ControlTab implements KeyListener, ActionListener, ItemListener {

	GuiMainView mainView;
	BOSettings settings;
	
	public final String[] streamTypesJGrabber = { "PES MPEG-Packetized Elementary", "TS MPEG-Transport"	};
	public final String[] streamTypesUdrec = { "PES MPEG-Packetized Elementary", "TS MPEG-Transport", "ES MPEG-Elementary"	};
	
	public ControlSettingsTab(GuiMainView view) {
		this.setMainView(view);
		this.setSettings(ControlMain.getSettings());
	}
	
	/*
	 *  (non-Javadoc)
	 * @see control.ControlTab#initialize()
	 */
	public void initialize() {
		this.getMainView().getTabSettings().getJComboBoxTheme().setSelectedItem(settings.getThemeLayout());
		this.getMainView().getTabSettings().getJComboBoxLocale().setSelectedItem(settings.getLocale());
		this.getMainView().getTabSettings().getTfServerPort().setText(settings.getStreamingServerPort());
		this.getMainView().getTabSettings().getJTextFieldRecordSavePath().setText(settings.getSavePath());
		this.getMainView().getTabSettings().getCbStartStreamingServer().setSelected(settings.isStartStreamingServer());
		this.getMainView().getTabSettings().getJTextFieldPlaybackString().setText(settings.getPlaybackString());
		this.getMainView().getTabSettings().getJComboBoxStreamType().setSelectedItem(settings.getJgrabberStreamType());
		this.getMainView().getTabSettings().getCbStartPX().setSelected(settings.isStartPX());
		this.getMainView().getTabSettings().getCbRecordAllPids().setSelected(settings.isRecordAllPids());
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
		this.getMainView().getTabSettings().getJRadioButtonJGrabber().setSelected(true);
		GuiStreamTypeComboModel streamTypeComboModel = new GuiStreamTypeComboModel(streamTypesJGrabber);
		String streamType = this.getSettings().getJgrabberStreamType();
		this.getMainView().getTabSettings().getJComboBoxStreamType().setModel(streamTypeComboModel);
		this.getMainView().getTabSettings().getStreamTypeComboModel().setSelectedItem(streamType);
		this.getMainView().getTabSettings().getJTextFieldUdrecPath().setText(settings.getUdrecPath());
	}
	
	private void initializeUdrecEngine() {
		this.getMainView().getTabSettings().getJRadioButtonUdrec().setSelected(true);
		GuiStreamTypeComboModel streamTypeComboModel = new GuiStreamTypeComboModel(streamTypesUdrec);
		String streamType = this.getSettings().getUdrecStreamType();
		this.getMainView().getTabSettings().getJComboBoxStreamType().setModel(streamTypeComboModel);
		this.getMainView().getTabSettings().getStreamTypeComboModel().setSelectedItem(streamType);
		this.getMainView().getTabSettings().getJTextFieldUdrecPath().setText(settings.getUdrecPath());
	}
	
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action == "delete") {
			this.actionRemoveBox();
		}
		if (action == "add") {
			this.actionAddBox();
		}
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
	}
	public void keyTyped(KeyEvent event) {}
	
	public void keyPressed(KeyEvent event) {}
	
	public void keyReleased(KeyEvent event) {
		JTextField tf = (JTextField)event.getSource();
		if (tf.getName().equals("playbackString")){
			settings.setPlaybackString(tf.getText());
		}
		if (tf.getName().equals("serverPort")){
			settings.setStreamingServerPort(tf.getText());
		}
		if (tf.getName().equals("udrecPath")){
			settings.setUdrecPath(tf.getText());
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

	//Change-Events der Combos und der Checkbox
	public void itemStateChanged (ItemEvent event) {
		String comp = event.getSource().getClass().getName();
		while (true) {
			if (comp.equals("javax.swing.JCheckBox")) {
				JCheckBox checkBox = (JCheckBox)event.getSource();
				if (checkBox.getName().equals("startStreamingServer")) {
					settings.setStartStreamingServer(checkBox.isSelected());
					break;
				}
				if (checkBox.getName().equals("startPX")) {
					settings.setStartPX(checkBox.isSelected());
					break;
				}
				if (checkBox.getName().equals("recordAllPids")) {
					settings.setRecordAllPids(checkBox.isSelected());
					break;
				}
				break;
			} else {
				JComboBox comboBox = (JComboBox)event.getSource();
				if (event.getStateChange()==1) {
					if (comboBox.getName().equals("theme")) {
						settings.setThemeLayout((String)comboBox.getSelectedItem());
						break;
					}
					if (comboBox.getName().equals("locale")) {
						settings.setLocale((String)comboBox.getSelectedItem());
						break;
					}
					if (comboBox.getName().equals("playbackDevice")) {
						settings.setPlaybackString((String)comboBox.getSelectedItem());
						break;
					}
					if (comboBox.getName().equals("streamType")) {
						settings.setStreamType((String)comboBox.getSelectedItem());
						break;
					}
				}	
				break;
			}	
		}
		
	}
	
	private void actionAddBox() {
		BOBox box = new BOBox();
		box.setDboxIp("192.168.001.110"); //defaultwert
		this.getMainView().getTabSettings().getModelBoxTable().addRow(box);
	}
	private void actionRemoveBox() {
		int selectedRow = this.getMainView().getTabSettings().getJTableBoxSettings().getSelectedRow();
		this.getMainView().getTabSettings().getModelBoxTable().removeRow(selectedRow);
	}

	private void openRecordPathFileChooser() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setDialogType(JFileChooser.SAVE_DIALOG);

		fc.setApproveButtonText( "Auswählen");
		fc.setApproveButtonToolTipText( "Verzeichnis auswählen");
		int returnVal = fc.showSaveDialog( null ) ;

		if ( returnVal == JFileChooser.APPROVE_OPTION )
			{
				String path = fc.getSelectedFile().toString();
				this.getMainView().getTabSettings().getJTextFieldRecordSavePath().setText(path);
				ControlMain.getSettings().setSavePath(path);
			}
	}
	
	private void openUdrecPathFileChooser() {
		JFileChooser chooser = new JFileChooser();
		chooser.setApproveButtonText( "Auswählen");
		chooser.setApproveButtonToolTipText( "Pfad zur udrec.exe auswählen");
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
			this.getMainView().getTabSettings().getJTextFieldUdrecPath().setText(path);
			ControlMain.getSettings().setUdrecPath(path);	
		}
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
	/**
	 * @return Returns the settings.
	 */
	public BOSettings getSettings() {
		return settings;
	}
	/**
	 * @param settings The settings to set.
	 */
	public void setSettings(BOSettings settings) {
		this.settings = settings;
	}
}
