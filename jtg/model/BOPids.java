/*
BOPids.java by Geist Alexander 

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
package model;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import control.ControlMain;

public class BOPids {

    private BOPid vPid; //erste Position Pid, 2. Position Beschreibung
    private ArrayList aPids = new ArrayList(); //ArrayList von String[] Objekten, erste Position Pid, 2. Position Beschreibung
    private BOPid vtxtPid;
   
    public int getPidCount() {
        int count = 0;
        if (this.getVPid()!= null) {
            count++;
        }
        if (this.getVtxtPid()!= null) {
            count++;
        }
        return this.getAPids().size()+count;
    }
    public String getAPidNumber(int index) {
        String[] aPid = (String[])this.getAPids().get(index);
        return aPid[0];
    }
    
    public String getAPidDescription(int index) {
        String[] aPid = (String[])this.getAPids().get(index);
        return aPid[1];
    }
    
    public static BOPids startPidsQuestDialog(BOPids pids) {
		DefaultListModel m = new DefaultListModel();
		JList list = new JList(m);
		list.setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		m.addElement(pids.getVPid());
		for (int i = 0; i < pids.getAPids().size(); i++) {
			BOPid pid = (BOPid) pids.getAPids().get(i);
			m.addElement(pid);
		}
		m.addElement(pids.getVtxtPid());

		int res = JOptionPane.showOptionDialog(
				ControlMain.getControl().getView(), 
				new Object[]{ControlMain.getProperty("label_selectPids"), new JScrollPane(list)}, 
				"Pids", 
				0,
				JOptionPane.QUESTION_MESSAGE, 
				null, 
				new String[]{ControlMain.getProperty("button_ok"), ControlMain.getProperty("button_cancel")},
				ControlMain.getProperty("button_ok"));
		
		if (res == 0) {
		    Object[] test = list.getSelectedValues();
			BOPid[] pidArray = (BOPid[]) list.getSelectedValues();
			BOPids newPidList = new BOPids();
			for (int i=0; i<pidArray.length; i++) {
				switch (pidArray[i].getId()) {
					case 0: newPidList.setVPid(pidArray[i]);
					break;
					case 1: newPidList.getAPids().add(pidArray[i]);
					break;
					case 2: newPidList.setVtxtPid(pidArray[i]);
					break;
				}
			}
			return newPidList;
		}
		return null;
	}
    
    /**
     * @return Returns the vtxtPid.
     */
    public BOPid getVtxtPid() {
        return vtxtPid;
    }
    /**
     * @param vtxtPid The vtxtPid to set.
     */
    public void setVtxtPid(BOPid vtxtPid) {
        this.vtxtPid = vtxtPid;
    }
    /**
     * @return Returns the aPids.
     */
    public ArrayList getAPids() {
        return aPids;
    }
    /**
     * @param pids The aPids to set.
     */
    public void setAPids(ArrayList pids) {
        aPids = pids;
    }
    /**
     * @return Returns the vPid.
     */
    public BOPid getVPid() {
        return vPid;
    }
    /**
     * @param pid The vPid to set.
     */
    public void setVPid(BOPid pid) {
        vPid = pid;
    }
}
