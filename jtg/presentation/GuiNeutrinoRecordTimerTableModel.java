package presentation;


import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

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
			return this.getControl().getTimerList()[0].size();
		}
		return 0;
	}

	public Object getValueAt( int rowIndex, int columnIndex ) {
		BOTimer timer = (BOTimer)this.getControl().getTimerList()[0].get(rowIndex);
		if (columnIndex == 0) {
			return timer.getSenderName();
		} if (columnIndex == 1) {
			return timer.getStartDate();
		} if (columnIndex == 2) {
			return timer.getStartTime();
		} if (columnIndex == 3) {
			return timer.getStopTime();
		} if (columnIndex == 4) {
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
		}
		if (col == 1) {
			BOTimer timer = (BOTimer)this.getControl().getTimerList()[0].get(row);
			GregorianCalendar oldcal = timer.getUnformattedStartTime();
			Calendar newcal = new GregorianCalendar();
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
			try {
				Date newDate = sdf.parse((String)value);
				newcal.setTime(newDate);
				oldcal.set(newcal.get(Calendar.YEAR), newcal.get(Calendar.MONTH), newcal.get(Calendar.DATE));
			} catch (ParseException e) {}
		}
		if (col == 2) {
			BOTimer timer = (BOTimer)this.getControl().getTimerList()[0].get(row);
			GregorianCalendar oldcal = timer.getUnformattedStartTime();
			Calendar newcal = new GregorianCalendar();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			try {
				Date newDate = sdf.parse((String)value);
				newcal.setTime(newDate);
				oldcal.set(Calendar.HOUR_OF_DAY, newcal.get(Calendar.HOUR_OF_DAY));
				oldcal.set(Calendar.MINUTE, newcal.get(Calendar.MINUTE));
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
			} catch (ParseException e) {}
		}
		if (col == 4) {
			BOTimer timer = (BOTimer)this.getControl().getTimerList()[0].get(row);
			timer.setEventRepeatId(control.convertLongEventRepeat((String)value));
			control.getTab().selectRepeatDaysForRecordTimer(timer);
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
		} if (columnIndex == 4) {
			return "Wiederholung";
		} else {
			return "Titel";
		}
	}
	
	public boolean isCellEditable (int row, int col) {
	    Class columnClass = getColumnClass(col);
	    if (col==5) {
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
