package presentation;




import javax.swing.table.AbstractTableModel;

import boxConnection.SerBoxControl;

import model.BOTimer;
import control.ControlNeutrinoTimerTab;

public class GuiNeutrinoTimerTableModel extends AbstractTableModel 
{
	ControlNeutrinoTimerTab control;
	SerBoxControl box;
	
	public GuiNeutrinoTimerTableModel(ControlNeutrinoTimerTab ctrl){
		this.setControl(ctrl);
	}

	public int getColumnCount() {
		return 5;	
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

	public String getColumnName( int columnIndex ) {
		if (columnIndex == 0) {
			return "Sender"; 
		} if (columnIndex == 1) {
			return "Datum";
		} if (columnIndex == 2) {
			return "Start";
		} if (columnIndex == 3) {
			return "Ende";
		} else {
			return "Titel";
		}
	}
	
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	public ControlNeutrinoTimerTab getControl() {
		return control;
	}
	
	public void setControl(ControlNeutrinoTimerTab control) {
		this.control = control;
	}
	
}
