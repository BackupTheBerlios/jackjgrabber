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
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.swing.table.AbstractTableModel;

import model.BOSender;
import model.BOTimer;
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
		if (col == 0) {
			BOTimer timer = (BOTimer)this.getControl().getTimerList()[0].get(row);
			int senderIndex = this.getControl().getTab().getComboBoxSender().getSelectedIndex();
			BOSender sender = (BOSender)this.getControl().getSenderList().get(senderIndex);
			timer.setChannelId(sender.getChanId());
			timer.setSenderName((String)value);
			if (timer.getModifiedId() == null) {
				timer.setModifiedId("modify");
			}
		}
		if (col == 1) {
			BOTimer timer = (BOTimer)this.getControl().getTimerList()[0].get(row);
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy   HH:mm");
			try {
				Date newDate = sdf.parse((String)value);
				timer.setUnformattedStartTime(newDate.getTime());
				
				if (timer.getModifiedId() == null) {
					timer.setModifiedId("modify");
				}
			} catch (ParseException e) {}
		}
		if (col == 2) {
			BOTimer timer = (BOTimer)this.getControl().getTimerList()[0].get(row);
			GregorianCalendar oldcal = timer.getUnformattedStopTime();
			Calendar newcal = new GregorianCalendar();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			try {
				Date newDate = sdf.parse((String)value);
				newcal.setTime(newDate);
				oldcal.set(Calendar.HOUR_OF_DAY, newcal.get(Calendar.HOUR_OF_DAY));
				oldcal.set(Calendar.MINUTE, newcal.get(Calendar.MINUTE));
				if (timer.getModifiedId() == null) {
					timer.setModifiedId("modify");
				}
			} catch (ParseException e) {}
		}
		if (col == 3) {
			BOTimer timer = (BOTimer)this.getControl().getTimerList()[0].get(row);
			timer.setEventRepeatId(control.convertLongEventRepeat((String)value));
			control.getTab().selectRepeatDaysForRecordTimer(timer);
			if (timer.getModifiedId() == null) {
				timer.setModifiedId("modify");
			}
		}
    }

	public String getColumnName( int columnIndex ) {
		if (columnIndex == 0) {
			return "Sender";
		} if (columnIndex == 1) {
			return "Start";
		} if (columnIndex == 2) {
			return "Ende";
		} if (columnIndex == 3) {
			return "Wiederholung";
		} else {
			return "Titel";
		}
	}

	public boolean isCellEditable (int row, int col) {
	    Class columnClass = getColumnClass(col);
	    if (col==4) {
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
}
