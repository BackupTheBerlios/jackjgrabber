package presentation.settings;
/*
GuiBoxSettingsTableModel.java by Geist Alexander 

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

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import model.BOBox;
import presentation.program.GuiIpListComboModel;
import service.SerAlertDialog;
import control.ControlMain;
import control.ControlSettingsTabMain;

public class GuiBoxSettingsTableModel extends AbstractTableModel  {
	
    ControlSettingsTabMain control;
	
	public GuiBoxSettingsTableModel(ControlSettingsTabMain ctrl) {
		control = ctrl;
	}

	public int getColumnCount() {
		return 4;	
	}	

	public int getRowCount() {
		if (ControlMain.getSettingsMain().getBoxList().size() == 0) {
			return 0;
		}
		return ControlMain.getSettingsMain().getBoxList().size();
	}

	public Object getValueAt( int rowIndex, int columnIndex ) {
		BOBox box = (BOBox)ControlMain.getSettingsMain().getBoxList().get(rowIndex);
		if (columnIndex == 0) {
			return box.getDboxIp();
		}
		if (columnIndex == 1) {
			return box.getLogin();
		}
		if (columnIndex == 2) {
			return box.getPassword();
		}
		//
		else return new Boolean(box.isStandard());
	}
			
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		BOBox box = (BOBox)ControlMain.getSettingsMain().getBoxList().get(rowIndex);
		if (columnIndex == 0) {
			box.setDboxIp((String)aValue);
		}
		if (columnIndex == 1) {
			box.setLogin((String)aValue);
		}
		if (columnIndex == 2) {
			box.setPassword((String)aValue);
		}
		//nur eine Checkbox darf selektiert sein!!
		if (columnIndex == 3) {
			if (((Boolean)aValue).booleanValue()) {
				ArrayList boxList = ControlMain.getSettingsMain().getBoxList();
				for (int i=0; i<boxList.size(); i++) { 
					BOBox boxx = (BOBox)boxList.get(i);
					boxx.setStandard(false);
					this.fireTableDataChanged();
				}
			}
			box.setStandard(((Boolean)aValue).booleanValue());
		}
	}

	public String getColumnName( int columnIndex ) {
		if (columnIndex == 0) {
			return "Box-IP"; 
		}
		if (columnIndex == 1) {
			return "Login";
		}
		if (columnIndex == 2) {
			return "Passwort";
		}
		else {
			return "Standard";
		}
	}
	
	/*
	 * Wenn die 1. Box angelegt wird, diese als Standard deklarieren
	 */
	public void addRow(BOBox box) {
		if (ControlMain.getSettingsMain().getBoxList().size()==0) {
			box.setStandard(true);
		}
		ControlMain.getSettingsMain().addBox(box);
		fireTableDataChanged();
		
		if (ControlMain.getSettingsMain().getBoxList().size()==1) {
		    ControlMain.getControl().getView().getMainTabPane().tabProgramm=null;
		    ControlMain.getControl().getView().getMainTabPane().firstIpSetted=true;
		} else {
		    this.refreshIpComboBox();
		}
	}
	
	private void refreshIpComboBox() {
		control.getMainView().getTabProgramm().getJComboBoxBoxIP().setModel(new GuiIpListComboModel());
		int index = ControlMain.getIndexOfActiveBox();
		control.getMainView().getTabProgramm().getJComboBoxBoxIP().setSelectedIndex(index);
	}
	
	public void removeRow(int rowNumber) {
		try {
			ControlMain.getSettingsMain().removeBox(rowNumber);
			fireTableDataChanged();
			this.refreshIpComboBox();
		} catch (ArrayIndexOutOfBoundsException ex) {SerAlertDialog.alert(ControlMain.getProperty("msg_selectRow"), control.getMainView());};
	}
	
	public boolean isCellEditable (int row, int col) {
	    Class columnClass = getColumnClass(col);
	    return true;
	}

	public Class getColumnClass (int col) {
       if (col==3) {
          return Boolean.class;
       }
       return String.class;
	}
}
