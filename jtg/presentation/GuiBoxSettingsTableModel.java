package presentation;


import javax.swing.table.AbstractTableModel ;
import java.util.*;
import model.BOBox;

import service.SerAlertDialog;

import control.ControlMain;
import control.ControlSettingsTab;

/**
 * @author Alexander Geist
 *
 */
public class GuiBoxSettingsTableModel extends AbstractTableModel  {
	
	ControlSettingsTab control;
	
	public GuiBoxSettingsTableModel(ControlSettingsTab ctrl) {
		control = ctrl;
	}

	public int getColumnCount() {
		return 4;	
	}	

	public int getRowCount() {
		if (control.getBoxList().size() == 0) {
			return 0;
		}
		return control.getBoxList().size();
	}

	public Object getValueAt( int rowIndex, int columnIndex ) {
		BOBox box = (BOBox)control.getBoxList().get(rowIndex);
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
		else return box.isStandard();
	}
			
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		ControlMain.getSettings().setBoxIpChanged(true);
		BOBox box = (BOBox)control.getBoxList().get(rowIndex);
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
				ArrayList boxList = control.getBoxList();
				for (int i=0; i<boxList.size(); i++) { 
					BOBox boxx = (BOBox)boxList.get(i);
					boxx.setStandard(Boolean.FALSE);
					this.fireTableDataChanged();
				}
			}
			box.setStandard((Boolean)aValue);
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
	
	public void addRow(BOBox data) {
		control.getBoxList().add(data);
		fireTableDataChanged();
	}
	
	public void removeRow(int rowNumber) {
		try {
			control.getBoxList().remove(rowNumber);
			fireTableDataChanged();
		} catch (ArrayIndexOutOfBoundsException ex) {SerAlertDialog.alert("Bitte eine Zeile markieren", control.getMainView());};
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
