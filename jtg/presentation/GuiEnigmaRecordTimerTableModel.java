package presentation;



import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.table.AbstractTableModel;

import model.BOSender;
import model.BOTimer;
import service.SerFormatter;
import control.ControlEnigmaTimerTab;
import control.ControlMain;

/*
GuiEnigmaRecordTimerTableModel.java by Geist Alexander, Treito

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
public class GuiEnigmaRecordTimerTableModel extends AbstractTableModel 
{
	ControlEnigmaTimerTab control;
	
	public GuiEnigmaRecordTimerTableModel(ControlEnigmaTimerTab ctrl){
		this.setControl(ctrl);
	}

	public int getColumnCount() {
		return 7;	
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
		    return control.convertShortTimerType(timer.getEventTypeId());
		} if (columnIndex == 1) {
			return timer.getSenderName();
		//} if (columnIndex == 2) {
		//	return timer.getStartDate();
		} if (columnIndex == 2) {
			return timer.getStartTime();
		} if (columnIndex == 3) {
			return timer.getStopTime();
		} if (columnIndex == 4) {
			return control.convertShortEventRepeat(timer.getEventRepeatId());
		} if (columnIndex == 5) {
		    return control.convertShortEventType(timer.getEventTypeId());
		} else {
			return timer.getDescription(); 
		}
	}
	
	public void setValueAt(Object value, int row, int col) {
	    BOTimer timer = (BOTimer)this.getControl().getTimerList()[0].get(row);
		if (col == 1) {
			int senderIndex = this.getControl().getTab().getComboBoxSender().getSelectedIndex();
			BOSender sender = (BOSender)this.getControl().getSenderList().get(senderIndex);
			timer.setChannelId(sender.getChanId());
			timer.setSenderName((String)value);
			if (timer.getModifiedId() == null) {
				timer.setModifiedId("modify");
			}
		}
		if (col == 2) {
		    GregorianCalendar newDate = SerFormatter.getDateFromString((String)value, "dd.MM.yy   HH:mm");
			timer.setUnformattedStartTime(newDate.getTimeInMillis());
		}	
		if (col == 3) {
		    GregorianCalendar oldcal = timer.getUnformattedStopTime();
			GregorianCalendar newDate = SerFormatter.getDateFromString((String)value, "HH:mm");
			oldcal.set(Calendar.HOUR_OF_DAY, newDate.get(Calendar.HOUR_OF_DAY));
			oldcal.set(Calendar.MINUTE, newDate.get(Calendar.MINUTE));
		}
		if (col == 4) {
		    timer.setEventRepeatId(control.convertLongEventRepeat((String)value));
			System.out.println (timer.getEventRepeatId());
			control.getTab().selectRepeatDaysForRecordTimer(timer);
			if (timer.getModifiedId() == null) {
				timer.setModifiedId("modify");
			}
		}
		if (col == 5) {
			timer.setEventTypeId(control.convertLongEventType((String)value));
			if (timer.getModifiedId() == null) {
				timer.setModifiedId("modify");
			}
		}
		if (col == 6) {
			timer.setDescription((String)value);
			if (timer.getModifiedId() == null) {
				timer.setModifiedId("modify");
			}
		}
    }

	public String getColumnName( int columnIndex ) {
		if (columnIndex == 0) {
		    return ControlMain.getProperty("state");
		}if (columnIndex == 1) {
			return ControlMain.getProperty("sender"); 
		//} if (columnIndex == 2) {
		//	return "Datum";
		} if (columnIndex == 2) {
			return ControlMain.getProperty("start");
		} if (columnIndex == 3) {
			return ControlMain.getProperty("end");
		} if (columnIndex == 4) {
			return ControlMain.getProperty("repeat");
		} if (columnIndex == 5) {
			return ControlMain.getProperty("afterRecord");
		} else {
			return ControlMain.getProperty("title");
		}
	}
	
	public boolean isCellEditable (int row, int col) {
	    Class columnClass = getColumnClass(col);
	    if (col==0) {
	    	return false;
	    } else {
	        return true;
	    }
	}
	
	public ControlEnigmaTimerTab getControl() {
		return control;
	}
	
	public void setControl(ControlEnigmaTimerTab control) {
		this.control = control;
	}
}
