package presentation;

import java.util.GregorianCalendar;

import javax.swing.table.AbstractTableModel;
import model.BOMovieGuide;
import control.ControlMovieGuideTab;
import service.SerFormatter;

public class MovieGuideTimerTableModel extends AbstractTableModel 
{
	ControlMovieGuideTab control;
	private static final String[] COLUMN_NAME = {"Datum","Start","Ende","Dauer","Programm"};
	
	public MovieGuideTimerTableModel(ControlMovieGuideTab ctrl) {
		this.setControl(ctrl);
	}

	public int getColumnCount() {
		return 5;	
	}	

	public int getRowCount() {
		if (this.getControl().getTimerTableSize() <= 0) {
			return 0;
		} else {						
			return this.getControl().getTimerTableSize();			
		}
	}

	public Object getValueAt( int rowIndex, int columnIndex ) {
		Object value = null;
		try{
		Integer selectRow = this.getControl().getSelectRowFilmTable();				
		if (columnIndex == 0) {							
			value = (SerFormatter.getShortDate(Long.parseLong(((BOMovieGuide)this.getControl().getTitelMap().get(selectRow)).getDatum().get(rowIndex).toString())));
		}else if (columnIndex == 1) {			
			value = SerFormatter.getGC2String((GregorianCalendar)((BOMovieGuide)this.getControl().getTitelMap().get(selectRow)).getStart().get(rowIndex) );
		}else if (columnIndex == 2) {	
			value = SerFormatter.getGC2String((GregorianCalendar)((BOMovieGuide)this.getControl().getTitelMap().get(selectRow)).getEnde().get(rowIndex) );
		}else if (columnIndex == 3) {
			value = ((BOMovieGuide)this.getControl().getTitelMap().get(selectRow)).getDauer().get(rowIndex);
		}else if (columnIndex == 4) {
			value = ((BOMovieGuide)this.getControl().getTitelMap().get(selectRow)).getSender().get(rowIndex);
		} 
		}catch(Exception ex){}
		return value;
	}
		
	public String getColumnName( int columnIndex ) {
		String value = null;
		try{
			value = COLUMN_NAME[columnIndex];
		}catch(Exception ex){}
		return value;		
	}
	
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	public void fireTableDataChanged() {	
		super.fireTableDataChanged();
	}
	/**
	 * @return Returns the control.
	 */
	public ControlMovieGuideTab getControl() {
		return control;
	}
	/**
	 * @param control The control to set.
	 */
	public void setControl(ControlMovieGuideTab control) {
		this.control = control;
	}

}
