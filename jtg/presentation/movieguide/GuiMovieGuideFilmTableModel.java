package presentation.movieguide;

import javax.swing.table.AbstractTableModel;

import model.BOMovieGuide;
import control.ControlMovieGuideTab;


public class GuiMovieGuideFilmTableModel extends AbstractTableModel 
{
	ControlMovieGuideTab control;	    			
	private static final String[] COLUMN_NAME = {"Titel"};
	
	public GuiMovieGuideFilmTableModel(ControlMovieGuideTab ctrl){
		this.setControl(ctrl);			
	}

	public int getColumnCount() {
		return 1;	
	}	

	public int getRowCount() {
		int value = 0;
		try{
		if (this.getControl().getTitelMap() == null) {
			value = 0;
		}else {
			value =  this.getControl().getTitelMap().size();
		}
		}catch (Exception ex){}
		return value;
	}

	public Object getValueAt( int rowIndex, int columnIndex ) {
		Object value = null;
		try{
		if (columnIndex == 0) {				
			value = ((BOMovieGuide)this.getControl().getTitelMap().get(new Integer(rowIndex))).getTitel();
		}
		}catch (Exception ex){}
		return value;	
	}
	
	public String getColumnName( int columnIndex ) {				
		String value = null;
		try{
			value = COLUMN_NAME[columnIndex];
		}catch(Exception ex){}
		return value;	
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

	public void fireTableDataChanged(int value) {								
		this.getControl().setTitelMapSelected(this.getControl().getSelectedItemJComboBox(),value);
		super.fireTableDataChanged();
	}

}
