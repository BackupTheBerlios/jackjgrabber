package presentation;
/*
GuiNeutrinoRecordTimerTableModel.java by Geist Alexander 

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

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.table.AbstractTableModel;

import model.BOSender;
import model.BOTimer;
import service.SerFormatter;
import control.ControlMain;
import control.ControlNeutrinoTimerTab;

public class GuiNeutrinoRecordTimerTableModel extends AbstractTableModel
{
	ControlNeutrinoTimerTab control;

	public GuiNeutrinoRecordTimerTableModel(ControlNeutrinoTimerTab ctrl){
		this.setControl(ctrl);
	}

	public int getColumnCount() {
		return 5;
	}

	public int getRowCount() {
		if (this.getControl().getTimerList() != null) {
			return this.getControl().getTimerList()[0].size();
		}
		return 0;
	}

	public Object getValueAt( int rowIndex, int columnIndex ) {
		BOTimer timer = (BOTimer)this.getControl().getTimerList()[0].get(rowIndex);
		if (columnIndex == 0) {
			return timer.getSenderName();
		} if (columnIndex == 1) {
			return timer.getStartTime();
		} if (columnIndex == 2) {
			return timer.getStopTime();
		} if (columnIndex == 3) {
			return control.convertShortEventRepeat(timer.getEventRepeatId());
		} else {
			return timer.getDescription();
		}
	}

	public void setValueAt(Object value, int row, int col) {
		BOTimer timer = (BOTimer)control.getTimerList()[0].get(row);
		if (col == 0) {
			int senderIndex = this.getControl().getTab().getComboBoxSender().getSelectedIndex();
			BOSender sender = (BOSender)this.getControl().getSenderList().get(senderIndex);
			timer.setChannelId(sender.getChanId());
			timer.setSenderName((String)value);
		}
		if (col == 1) {
			GregorianCalendar newDate = SerFormatter.getDateFromString((String)value, "dd.MM.yy   HH:mm");
			timer.setUnformattedStartTime(newDate.getTimeInMillis());
		}
		if (col == 2) {
			GregorianCalendar oldcal = timer.getUnformattedStopTime();
			GregorianCalendar newDate = SerFormatter.getDateFromString((String)value, "HH:mm");
			oldcal.set(Calendar.HOUR_OF_DAY, newDate.get(Calendar.HOUR_OF_DAY));
			oldcal.set(Calendar.MINUTE, newDate.get(Calendar.MINUTE));
		}
		if (col == 3) {
			timer.setEventRepeatId(control.convertLongEventRepeat((String)value));
			control.selectRepeatDaysForRecordTimer(timer);
		}
		timer.setModifiedId("modify");
    }

	public String getColumnName( int columnIndex ) {
		if (columnIndex == 0) {
			return ControlMain.getProperty("sender");
		} if (columnIndex == 1) {
			return ControlMain.getProperty("start");
		} if (columnIndex == 2) {
			return ControlMain.getProperty("end");
		} if (columnIndex == 3) {
			return ControlMain.getProperty("repeat");
		} else {
			return ControlMain.getProperty("title");
		}
	}

	public boolean isCellEditable (int row, int col) {
	    Class columnClass = getColumnClass(col);
	    if (col==4) {
	    	return false;
	    }
	    BOTimer timer = (BOTimer)control.getTimerList()[0].get(row);
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
		this.getControl().getTab().enableRecordTimerWeekdays(false);
	}
}
