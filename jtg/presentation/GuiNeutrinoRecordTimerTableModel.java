package presentation;


import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;

import model.BOSender;
import model.BOTimer;
import control.ControlNeutrinoTimerTab;

/**
 * @author Alexander Geist
 */
public class GuiNeutrinoRecordTimerTableModel extends AbstractTableModel 
{
	ControlNeutrinoTimerTab control;
	
	public GuiNeutrinoRecordTimerTableModel(ControlNeutrinoTimerTab ctrl){
		this.setControl(ctrl);
	}

	public int getColumnCount() {
		return 6;	
	}	

	public int getRowCount() {
		if (this.getControl().getTimerList() != null) {
			return this.getControl().getTimerList().size();
		}
		return 0;
	}

	public Object getValueAt( int rowIndex, int columnIndex ) {
		BOTimer timer = (BOTimer)this.getControl().getTimerList().get(rowIndex);
		if (columnIndex == 0) {
			return timer.getSenderName();
		} if (columnIndex == 1) {
			return timer.getStartDate();
		} if (columnIndex == 2) {
			return timer.getStartTime();
		} if (columnIndex == 3) {
			return timer.getStopTime();
		} else {
			return timer.getDescription(); 
		}
	}
	
	public void setValueAt(Object value, int row, int col) {
		if (col == 0) {
			BOTimer timer = (BOTimer)this.getControl().getTimerList().get(row);
			timer.setSenderName((String)value);
			this.fireTableDataChanged();
		}
		
        //data[row][col] = value;
        //fireTableCellUpdated(row, col);
    }

	public String getColumnName( int columnIndex ) {
		if (columnIndex == 0) {
			return "Sender"; 
		} if (columnIndex == 1) {
			return "Datum";
		} if (columnIndex == 2) {
			return "Start";
		} if (columnIndex == 3) {
			return "Ende";
		} if (columnIndex == 4) {
			return "Wiederholung";
		} else {
			return "Titel";
		}
	}
	
	public boolean isCellEditable (int row, int col) {
	    Class columnClass = getColumnClass(col);
	    return true;
	}
	
	public ControlNeutrinoTimerTab getControl() {
		return control;
	}
	
	public void setControl(ControlNeutrinoTimerTab control) {
		this.control = control;
	}
	
	public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
	}
}