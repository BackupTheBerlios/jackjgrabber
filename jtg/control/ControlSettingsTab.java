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

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import model.BOBox;
import model.BOSettings;

import presentation.GuiMainView;


public class ControlSettingsTab extends ControlTab implements ActionListener,  ItemListener {

	GuiMainView mainView;
	BOSettings settings;
	
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
			this.openFileChooser();
		}
		if (action == "playbackString") {
			this.actionSetPlaybackString(e);
		}
	}
	
	private void actionSetPlaybackString(ActionEvent event) {
		JTextField tf = (JTextField)event.getSource();
		this.getSettings().setPlaybackString(tf.getText());
	}

	//Change-Events der Combos und der Checkbox
	public void itemStateChanged (ItemEvent event) {
		String comp = event.getSource().getClass().getName();
		if (comp.equals("javax.swing.JCheckBox")) {
			JCheckBox checkBox = (JCheckBox)event.getSource();
			settings.setStartStreamingServer(checkBox.isSelected());
		} else {
			JComboBox comboBox = (JComboBox)event.getSource();
			if (comboBox.getName().equals("theme")) {
				settings.setThemeLayout((String)comboBox.getSelectedItem());
			}
			if (comboBox.getName().equals("locale")) {
				settings.setLocale((String)comboBox.getSelectedItem());
			}
			if (comboBox.getName().equals("playbackDevice")) {
				settings.setPlaybackString((String)comboBox.getSelectedItem());
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

	private void openFileChooser() {
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
