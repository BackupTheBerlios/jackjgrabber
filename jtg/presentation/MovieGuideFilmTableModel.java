package presentation;

import javax.swing.table.AbstractTableModel;

import model.BOMovieGuide;
import control.ControlMovieGuideTab;


public class MovieGuideFilmTableModel extends AbstractTableModel 
{
	ControlMovieGuideTab control;	    			
	
	public MovieGuideFilmTableModel(ControlMovieGuideTab ctrl){
		this.setControl(ctrl);			
	}

	public int getColumnCount() {
		return 1;	
	}	

	public int getRowCount() {
		if (this.getControl().getTitelMap() == null) {
			return 0;
		} else {
			return this.getControl().getTitelMap().size();
		}
	}

	public Object getValueAt( int rowIndex, int columnIndex ) {
		if (columnIndex == 0) {								
			return ((BOMovieGuide)this.getControl().getTitelMap().get(new Integer(rowIndex))).getTitel();
		}else{
			return null;
		}	
	}
	
	public String getColumnName( int columnIndex ) {				
		if (columnIndex == 0) {	
			return "Titel";				
		}else 		
		return null;
	}
	
	public boolean isCellEditable (int row, int col) {
	    Class columnClass = getColumnClass(col);
	    return false;
	}
	
	public ControlMovieGuideTab getControl() {
		return control;
	}
	
	public void setControl(ControlMovieGuideTab control) {
		this.control = control;
	}		

	public void fireTableDataChanged() {						
		super.fireTableDataChanged();
	}

}
