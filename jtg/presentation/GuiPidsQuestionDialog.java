package presentation;
/*
GuiMainView.java by Geist Alexander 

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


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import service.SerGUIUtils;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class GuiPidsQuestionDialog extends JDialog {
	
	private JButton jButtonOk;
	private static JRadioButton[] jRadioButtonPids; 
	private static ArrayList pidList;
	private JPanel panelPidButtons;
	
	public GuiPidsQuestionDialog(ArrayList pids, GuiMainView view) {
		super(view, "Aufnahme-Pids auswählen", true);
		addWindowListener (new WindowAdapter() { 
			public void windowClosing(WindowEvent e) { 
				checkPidList();
			}
		});
		this.setPidList(pids);
		this.initialize();
		this.pack();
		SerGUIUtils.center(this);
		setVisible(true);	
	}
	private void initialize() {
		this.getContentPane().add(this.getQuestPanel());
	}
	private JPanel getQuestPanel() {
		if (panelPidButtons == null) {
			panelPidButtons = new JPanel();
			jRadioButtonPids = new JRadioButton[pidList.size()];
			PanelBuilder builder = new PanelBuilder(panelPidButtons, this.buildLayout());
			CellConstraints cc = new CellConstraints();
			
			builder.addSeparator("Aufnahme-Pids auswählen", cc.xy(1, 1));
			for(int i = 0 ; i<pidList.size(); i++){
				if (jRadioButtonPids[i]== null) {
					jRadioButtonPids[i] = new JRadioButton();			
					jRadioButtonPids[i].setText((String)pidList.get(i));
					jRadioButtonPids[i].setSelected(true);
				}
				builder.add(jRadioButtonPids[i],cc.xy(1, i+2));
			}
		}
		return panelPidButtons;
	}
	
	private FormLayout buildLayout() {
		ColumnSpec[] colSpecs = new ColumnSpec[1];
		colSpecs[0]= new ColumnSpec("pref");
		RowSpec[] rowSpecs = new RowSpec[pidList.size()+1];
		for (int i=0; i<rowSpecs.length; i++) {
			rowSpecs[i]= new RowSpec("pref");
		}
		FormLayout layout = new FormLayout(colSpecs, rowSpecs);	 
		return layout;
	}
	
	private static void checkPidList() {
		for (int i=pidList.size()-1; 0<=i; i--) {
			if (!jRadioButtonPids[i].isSelected()) {
				pidList.remove(i);
			}
		}
	}
	/**
	 * @return Returns the control.
	 */
	public ArrayList getPidList() {
		return pidList;
	}
	/**
	 * @param control The control to set.
	 */
	public void setPidList(ArrayList list) {
		pidList = list;
	}
	/**
	 * @return Returns the jButtonOk.
	 */
	public JButton getJButtonOk() {
		return jButtonOk;
	}
	/**
	 * @return Returns the jRadioButtonPids.
	 */
	public JRadioButton[] getJRadioButtonPids() {
		return jRadioButtonPids;
	}
}
