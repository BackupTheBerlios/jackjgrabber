package presentation;
/*
GuiEpgTableModel.java by Geist Alexander 

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

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import model.BOMovieGuide;
import control.ControlMovieGuideTab;

/**
 * TableModel des EPG-Tables
 * es wird eine eigene EPG-ArrayList verwaltet!! 
 * Diese EPG-list enthält nur EPG´s die mit dem Datum des DateChoosers übereinstimmen
 */
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
			//return this.getControl().getTitelMap().size();
			return 2;
			//return ((BOMovieGuide)this.getControl().getTitelMap().get(this.getControl().getSelectRowFilmTable())).getDatum().size();
		}
	}

	public Object getValueAt( int rowIndex, int columnIndex ) {	
		Integer selectedRow = getControl().getSelectRowFilmTable();
		if (columnIndex == 0) {
			return ((BOMovieGuide)this.getControl().getTitelMap().get(selectedRow)).getDatum();
		} if (columnIndex == 1) {
			return ((BOMovieGuide)this.getControl().getTitelMap().get(selectedRow)).getStart();
		} if (columnIndex == 2) {
			return ((BOMovieGuide)this.getControl().getTitelMap().get(selectedRow)).getStart();
		} if (columnIndex == 3) {
			return ((BOMovieGuide)this.getControl().getTitelMap().get(selectedRow)).getDauer();
		} if (columnIndex == 4) {
			return ((BOMovieGuide)this.getControl().getTitelMap().get(selectedRow)).getSender();
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
