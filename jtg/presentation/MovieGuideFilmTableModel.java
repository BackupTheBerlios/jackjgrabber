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
import javax.swing.table.AbstractTableModel;

import control.ControlMovieGuideTab;


public class MovieGuideFilmTableModel extends AbstractTableModel{
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
			return this.getControl().getTitelMap().get(new Integer(rowIndex));						
		}else{
			return null;
		}	
	}
	
	public String getColumnName( int columnIndex ) {		
		String value = "";
		if (columnIndex == 0) {	
			value = "Titel";				
		}		
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
}
