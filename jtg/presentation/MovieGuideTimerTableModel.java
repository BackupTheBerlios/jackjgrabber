package presentation;

import javax.swing.table.AbstractTableModel;
import model.BOMovieGuide;
import control.ControlMovieGuideTab;

public class MovieGuideTimerTableModel extends AbstractTableModel 
{
	ControlMovieGuideTab control;
	
	public MovieGuideTimerTableModel(ControlMovieGuideTab ctrl) {
		this.setControl(ctrl);
	}

	public int getColumnCount() {
		return 5;	
	}	

	public int getRowCount() {
		if (this.getControl().getTitelMap() == null) {
			return 0;
		} else {			
			return 10;
			//return this.getControl().getTitelMap().size();
			//return ((BOMovieGuide)this.getControl().getTitelMap().get(this.getControl().getSelectRowFilmTable())).getDatum().toArray().length;			
			//Integer selectRow = this.getControl().getSelectRowFilmTable();
			//return ((BOMovieGuide)this.getControl().getTitelMap().get(selectRow) ).getDatum().toArray().length;
		}
	}

	public Object getValueAt( int rowIndex, int columnIndex ) {
		Integer selectRow = this.getControl().getSelectRowFilmTable();		
		if (columnIndex == 0) {		
			return ((BOMovieGuide)this.getControl().getTitelMap().get(selectRow)).getDatum().toArray()[rowIndex];
		} if (columnIndex == 1) {
			return ((BOMovieGuide)this.getControl().getTitelMap().get(selectRow)).getStart().toArray()[rowIndex];
		} if (columnIndex == 2) {			
			return ((BOMovieGuide)this.getControl().getTitelMap().get(selectRow)).getEnde().toArray()[rowIndex];
		} if (columnIndex == 3) {
			return ((BOMovieGuide)this.getControl().getTitelMap().get(selectRow)).getDauer().toArray()[rowIndex];
		} if (columnIndex == 4) {
			return ((BOMovieGuide)this.getControl().getTitelMap().get(selectRow)).getSender().toArray()[rowIndex];
		} else {
			return null;
		}
	}
	
	public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

	public String getColumnName( int columnIndex ) {
		if (columnIndex == 0) {
			return "Datum"; 
		} if (columnIndex == 1) {
			return "Start";
		} if (columnIndex == 2) {
			return "Ende";
		} if (columnIndex == 3) {
			return "Dauer";
		} if (columnIndex == 4) {
			return "Programm";
		} else {
			return null;
		}
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
