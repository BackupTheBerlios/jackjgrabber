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
import model.BOMovieGuide;
import control.ControlMovieGuideTab;


public class MovieGuideSenderTableModel extends AbstractTableModel 
{
	ControlMovieGuideTab control;
	
	public MovieGuideSenderTableModel(ControlMovieGuideTab ctrl){
		this.setControl(ctrl);
	}

	public int getColumnCount() {
		return 1;	
	}	

	public int getRowCount() {
		//if (this.getControl().getTimerList() != null) {
		//	return this.getControl().getTimerList()[1].size();
		//}
		return 0;
	}

	public Object getValueAt( int rowIndex, int columnIndex ) {
		
		/*
		  
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
		if (col == 0) {
			BOTimer timer = (BOTimer)this.getControl().getTimerList()[1].get(row);
			timer.setEventTypeId(control.convertLongEventType((String)value));
		}
		if (col == 1) {
			BOTimer timer = (BOTimer)this.getControl().getTimerList()[1].get(row);
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
			BOTimer timer = (BOTimer)this.getControl().getTimerList()[1].get(row);
			timer.setEventRepeatId(control.convertLongEventRepeat((String)value));
			control.selectRepeatDaysForSystemTimer(timer);
		}
		*/
		return new BOMovieGuide();
    }

	public String getColumnName() {		
			return "Datum"; 
	}
	
	public boolean isCellEditable (int row, int col) {
	    Class columnClass = getColumnClass(col);
	    return true;
	}
	
	public ControlMovieGuideTab getControl() {
		return control;
	}
	
	public void setControl(ControlMovieGuideTab control) {
		this.control = control;
	}
	
	public void fireTableDataChanged() {
	//	super.fireTableDataChanged();
	//	this.getControl().getTab().enableSystemTimerWeekdays(false);
	}
}
