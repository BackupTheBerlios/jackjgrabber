package presentation;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.table.AbstractTableModel;
import model.BOTimer;
import control.ControlNeutrinoTimerTab;

/**
 * @author Alexander Geist
 */
public class GuiNeutrinoSystemTimerTableModel extends AbstractTableModel 
{
	ControlNeutrinoTimerTab control;
	
	public GuiNeutrinoSystemTimerTableModel(ControlNeutrinoTimerTab ctrl){
		this.setControl(ctrl);
	}

	public int getColumnCount() {
		return 4;	
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
			return timer.getStartDate();
		} if (columnIndex == 2) {
			return timer.getStartTime();
		} else {
			return control.convertShortEventRepeat(timer.getEventRepeatId());
		}
	}
	
	public void setValueAt(Object value, int row, int col) {
		if (col == 0) {
			BOTimer timer = (BOTimer)this.getControl().getTimerList()[1].get(row);
			timer.setEventTypeId(control.convertLongEventType((String)value));
		}
		if (col == 1) {
			BOTimer timer = (BOTimer)this.getControl().getTimerList()[1].get(row);
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
			BOTimer timer = (BOTimer)this.getControl().getTimerList()[1].get(row);
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
			BOTimer timer = (BOTimer)this.getControl().getTimerList()[1].get(row);
			timer.setEventRepeatId(control.convertLongEventRepeat((String)value));
			control.getTab().selectRepeatDaysForSystemTimer(timer);
		}
    }

	public String getColumnName( int columnIndex ) {
		if (columnIndex == 0) {
			return "Timer-Typ"; 
		} if (columnIndex == 1) {
			return "Datum";
		} if (columnIndex == 2) {
			return "Zeit";
		} else {
			return "Wiederholung";
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
}
