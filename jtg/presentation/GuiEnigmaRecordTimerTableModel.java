package presentation;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.table.AbstractTableModel;

import model.BOSender;
import model.BOTimer;
import control.ControlEnigmaTimerTab;

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
		if (col == 1) {
		    BOTimer timer = (BOTimer)this.getControl().getTimerList()[0].get(row);
			int senderIndex = this.getControl().getTab().getComboBoxSender().getSelectedIndex();
			BOSender sender = (BOSender)this.getControl().getSenderList().get(senderIndex);
			timer.setChannelId(sender.getChanId());
			timer.setSenderName((String)value);
			if (timer.getModifiedId() == null) {
				timer.setModifiedId("modify");
			}
		}
		if (col == 2) {
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
		
		if (col == 3) {
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
		if (col == 4) {
		    BOTimer timer = (BOTimer)this.getControl().getTimerList()[0].get(row);
		    timer.setEventRepeatId(control.convertLongEventRepeat((String)value));
			System.out.println (timer.getEventRepeatId());
			control.getTab().selectRepeatDaysForRecordTimer(timer);
			if (timer.getModifiedId() == null) {
				timer.setModifiedId("modify");
			}
		}
		if (col == 5) {
		    BOTimer timer = (BOTimer)this.getControl().getTimerList()[0].get(row);
			timer.setEventTypeId(control.convertLongEventType((String)value));
			if (timer.getModifiedId() == null) {
				timer.setModifiedId("modify");
			}
		}
		if (col == 6) {
		    BOTimer timer = (BOTimer)this.getControl().getTimerList()[0].get(row);
			timer.setDescription((String)value);
			if (timer.getModifiedId() == null) {
				timer.setModifiedId("modify");
			}
		}
    }

	public String getColumnName( int columnIndex ) {
		if (columnIndex == 0) {
		    return "Status";
		}if (columnIndex == 1) {
			return "Sender"; 
		//} if (columnIndex == 2) {
		//	return "Datum";
		} if (columnIndex == 2) {
			return "Start";
		} if (columnIndex == 3) {
			return "Ende";
		} if (columnIndex == 4) {
			return "Wiederholung";
		} if (columnIndex == 5) {
			return "Nach Aufn.";
		} else {
			return "Titel";
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
