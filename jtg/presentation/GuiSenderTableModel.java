/*
 * Created on 15.04.2004
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package presentation;


import javax.swing.table.AbstractTableModel;

import model.BOSender;
import control.ControlProgramTab;

/**
 * @author QSE2419
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class GuiSenderTableModel extends AbstractTableModel 
{
	
	ControlProgramTab control;

	
	public GuiSenderTableModel(ControlProgramTab ctrl) {
		control = ctrl;
	}

	public int getColumnCount() {
		return 2;	
	}	

	public int getRowCount() {
		if (control.getSelectedBouquet() == null || control.getSelectedBouquet().getSender() == null ) {
			return 0;
		} else {
			return control.getSelectedBouquet().getSender().size();
		}
	}

	public Object getValueAt( int rowIndex, int columnIndex ) {
		BOSender sender = (BOSender)control.getSelectedBouquet().getSender().get(rowIndex);
		if (columnIndex == 0) {
			return sender.getNummer();
		} else {
			return sender.getName();
		}
	}

	public String getColumnName( int columnIndex ) {
		if (columnIndex == 0) {
			return "Nr."; 
		} else {
			return "Sender";
		}
	}
	
	
	public boolean isCellEditable(int row, int col)  {
		return false;
	}
}
