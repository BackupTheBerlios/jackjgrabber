package presentation;
/*
GuiNeutrinoSystemTimerTableModel.java by Geist Alexander 

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.table.AbstractTableModel;
import model.BOTimer;
import control.ControlNeutrinoTimerTab;


public class GuiNeutrinoSystemTimerTableModel extends AbstractTableModel 
{
	ControlNeutrinoTimerTab control;
	
	public GuiNeutrinoSystemTimerTableModel(ControlNeutrinoTimerTab ctrl){
		this.setControl(ctrl);
	}

	public int getColumnCount() {
		return 3;	
	}	

	public int getRowCount() {
		if (this.getControl().getTimerList() != null) {
			return this.getControl().getTimerList()[1].size();
		}
		return 0;
	}

	public Object getValueAt( int rowIndex, int columnIndex ) {
		BOTimer timer = (BOTimer)this.getControl().getTimerList()[1].get(rowIndex);
		if (columnIndex == 0) {
			return control.convertShortEventType(timer.getEventTypeId());
		} if (columnIndex == 1) {
			return timer.getStartTime();
		} else {
			return control.convertShortEventRepeat(timer.getEventRepeatId());
		}
	}
	
	public void setValueAt(Object value, int row, int col) {
		BOTimer timer = (BOTimer)control.getTimerList()[1].get(row);
		if (col == 0) {
			timer.setEventTypeId(control.convertLongEventType((String)value));
		}
		if (col == 1) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy   HH:mm");
			try {
				Date newDate = sdf.parse((String)value);
				timer.setUnformattedStartTime(newDate.getTime());
			} catch (ParseException e) {}
		}
		if (col == 2) {
			timer.setEventRepeatId(control.convertLongEventRepeat((String)value));
			control.selectRepeatDaysForSystemTimer(timer);
		}
		timer.setModifiedId("modify");
    }

	public String getColumnName( int columnIndex ) {
		if (columnIndex == 0) {
			return "Timer-Typ"; 
		} if (columnIndex == 1) {
			return "Nächster Zeitpunkt";
		} else {
			return "Wiederholung";
		}
	}
	
	public boolean isCellEditable (int row, int col) {
	    Class columnClass = getColumnClass(col);
	    BOTimer timer = (BOTimer)control.getTimerList()[1].get(row);
	    if (col==0 && timer.getModifiedId()==null) {
	    	return false;
	    }
	    return true;
	}
	
	public ControlNeutrinoTimerTab getControl() {
		return control;
	}
	
	public void setControl(ControlNeutrinoTimerTab control) {
		this.control = control;
	}
	
	public void fireTableDataChanged() {
		super.fireTableDataChanged();
		this.getControl().getTab().enableSystemTimerWeekdays(false);
	}
}
