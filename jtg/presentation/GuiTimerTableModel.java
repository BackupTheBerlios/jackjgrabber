package presentation;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import boxConnection.SerBoxControl;

import model.BOTimer;
import control.ControlTimerTab;

public class GuiTimerTableModel extends AbstractTableModel 
{
	ControlTimerTab control;
	ArrayList timerList = new ArrayList();
	SerBoxControl box;
	
	public GuiTimerTableModel(ControlTimerTab ctrl){
		this.setControl(ctrl);
		this.createTimerList();
	}

	public int getColumnCount() {
		return 5;	
	}	

	public int getRowCount() {
		return this.getTimerList().size();
	}

	public Object getValueAt( int rowIndex, int columnIndex ) {
		BOTimer timer = (BOTimer)this.getTimerList().get(rowIndex);
		if (columnIndex == 0) {
			return timer.getSender();
		} if (columnIndex == 1) {
			return timer.getStartDate();
		} if (columnIndex == 2) {
			return timer.getStartTime();
		} if (columnIndex == 3) {
			return timer.getStopTime();
		} else {
			return timer.getEventType(); //FIXME: Sendungsnamen auslesen , noch Dummywert
		}
	}
	public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
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
	
	public void createTimerList(){
		try{
			this.setTimerList(box.getTimer());
		}catch(Exception ex){}		
	}
	
	public void fireTableRowsInserted(int i, int i2) {
		this.createTimerList();
		super.fireTableRowsInserted(i, i2);
	}
	
	public ArrayList getTimerList() {
		return timerList;
	}

	public void setTimerList(ArrayList timerList) {
		this.timerList = timerList;
	}

	public ControlTimerTab getControl() {
		return control;
	}
	
	public void setControl(ControlTimerTab control) {
		this.control = control;
	}
	
}
