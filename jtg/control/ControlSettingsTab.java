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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;

import model.BOBox;

import presentation.GuiMainView;


public class ControlSettingsTab extends ControlTab implements ActionListener, MouseListener, ItemListener {

	GuiMainView mainView;
	
	public ControlSettingsTab(GuiMainView view) {
		this.setMainView(view);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see control.ControlTab#initialize()
	 */
	public void initialize() {
		this.getMainView().getTabSettings().getJComboBoxTheme().setSelectedItem(ControlMain.getSettings().getThemeLayout());
		this.getMainView().getTabSettings().getJComboBoxLocale().setSelectedItem(ControlMain.bolocale.getLocale());
		this.getMainView().getTabSettings().getTfServerPort().setText(ControlMain.getSettings().getStreamingServerPort());
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

	//Change-Events der Combos
	public void itemStateChanged (ItemEvent event) {
		JComboBox comboBox = (JComboBox)event.getSource();
		if (comboBox.getName().equals("theme")) {
			ControlMain.getSettings().setThemeLayout((String)comboBox.getSelectedItem());
		}
		if (comboBox.getName().equals("locale")) {
			ControlMain.getSettings().setLocale((String)comboBox.getSelectedItem());
		}
	}
		
	public void mousePressed(MouseEvent me) {
		JTable table = (JTable)me.getSource();
		String tableName = table.getName();
		if (tableName == "BoxSettings") {
			
		}
	}
	
	public void mouseClicked(MouseEvent me)
	{}
	public void mouseReleased(MouseEvent me)
	{}
	public void mouseExited(MouseEvent me)
	{}
	public void mouseEntered(MouseEvent me)
	{}
	
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
		JFileChooser chooser = new JFileChooser();
		FileFilter filter = new FileFilter(){
			public boolean accept(File f){
				return (f.getName().endsWith("vlc.exe") || f.isDirectory() );
			}
			public String getDescription(){
				return "vlc.exe";
			}
		};
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog( null ) ;
	
		if ( returnVal == JFileChooser.APPROVE_OPTION ) {
			String path = chooser.getSelectedFile().toString();

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
	 * @return Returns the boxList.
	 */
	public ArrayList getBoxList() {
		return ControlMain.getSettings().getBoxList();
	}
}
