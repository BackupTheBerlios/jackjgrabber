package presentation;
/*
GuiPidsQuestionDialog.java by Geist Alexander 

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import model.BOPid;
import model.BOPids;

import service.SerGUIUtils;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import control.ControlMain;

public class GuiPidsQuestionDialog extends JDialog implements ActionListener{
	
	private JButton jButtonOk;
	private static JRadioButton jRadioButtonVPid;
	private static JRadioButton jRadioButtonVtxtPid;
	private static JRadioButton[] jRadioButtonAPids;
	private static BOPids pidList;
	private JPanel panelPidButtons;
	private int pidsCount;
	
	public GuiPidsQuestionDialog(BOPids pids, GuiMainView view) {
		super(view, true);
		addWindowListener (new WindowAdapter() { 
			public void windowClosing(WindowEvent e) { 
				checkPidList(true);
			}
		});
		pidsCount = pids.getPidCount();
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
			jRadioButtonAPids = new JRadioButton[pidList.getAPids().size()];
			
			PanelBuilder builder = new PanelBuilder(panelPidButtons, this.buildLayout());
			builder.setDefaultDialogBorder();
			CellConstraints cc = new CellConstraints();
			
			builder.addSeparator(ControlMain.getProperty("label_selectPids"), cc.xy(1, 1));
			int counter = 0;
			//Radiobutton for vpid
			jRadioButtonVPid = new JRadioButton();			
			jRadioButtonVPid.setText(pidList.getVPid().getNumber()+" "+pidList.getVPid().getName());
			jRadioButtonVPid.setSelected(true);
			builder.add(jRadioButtonVPid,		cc.xy(1, 2));
			counter=1;
			//Radiobuttons vor audiopids
			for(int i = 0 ; i<pidList.getAPids().size(); i++){
			    BOPid pid = (BOPid)pidList.getAPids().get(i);
			    jRadioButtonAPids[i] = new JRadioButton();			
			    jRadioButtonAPids[i].setText(pid.getNumber()+" "+pid.getName());
			    jRadioButtonAPids[i].setSelected(true);
			    builder.add(jRadioButtonAPids[i],cc.xy(1, counter+2));
			    counter++;
			}
			//Radiobutton for vtxt-pid
			if (pidList.getVtxtPid() != null) {
			    jRadioButtonVtxtPid = new JRadioButton();			
					jRadioButtonVtxtPid.setText(pidList.getVtxtPid().getNumber()+" "+pidList.getVtxtPid().getName());
					jRadioButtonVtxtPid.setSelected(true);
					builder.add(jRadioButtonVtxtPid,		cc.xy(1,  counter+2));  
			}
			
			builder.add(this.getJButtonOk(),cc.xy(1, pidsCount+2));
		}
		return panelPidButtons;
	}
	
	private FormLayout buildLayout() {
		ColumnSpec[] colSpecs = new ColumnSpec[1];
		colSpecs[0]= new ColumnSpec("pref");
		RowSpec[] rowSpecs = new RowSpec[pidsCount+2];
		for (int i=0; i<rowSpecs.length; i++) {
			rowSpecs[i]= new RowSpec("pref");
		}
		FormLayout layout = new FormLayout(colSpecs, rowSpecs);	 
		return layout;
	}
	
	/**
	 * @param clear
	 * remove all Pids when Dialog is closed or
	 * find selected Pids when Dialog button "Ok" was pressed
	 */
	private static void checkPidList(boolean clear){
	    if (!clear) {
	        if (!jRadioButtonVPid.isSelected()) {
	            pidList.setVPid(null);
	        }
	        if (jRadioButtonVtxtPid!=null && !jRadioButtonVtxtPid.isSelected()) {
	            pidList.setVtxtPid(null);
	        }
	        for (int i=pidList.getAPids().size()-1; 0<=i; i--) {
	            if (!jRadioButtonAPids[i].isSelected()) {
	                pidList.getAPids().remove(i);
	            }
	        }
	    } else {
	        pidList.setVPid(null);
	        pidList.setAPids(new ArrayList());
	        pidList.setVtxtPid(null);
        }
	}
	/**
	 * @return Returns the control.
	 */
	public BOPids getPidList() {
		return pidList;
	}
	/**
	 * @param control The control to set.
	 */
	public void setPidList(BOPids list) {
		pidList = list;
	}
	/**
	 * @return Returns the jButtonOk.
	 */
	public JButton getJButtonOk() {
	    if (jButtonOk == null) {
	        jButtonOk = new JButton(ControlMain.getProperty("button_ok"));
	        jButtonOk.addActionListener(this);
	    }
		return jButtonOk;
	}
	public void actionPerformed(ActionEvent e) {
		checkPidList(false);
		this.dispose();
	}
}
