package presentation;
/*
GuiPlaybackSettingsTableModel.java by Geist Alexander 

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

import model.BOPlaybackOption;
import service.SerAlertDialog;
import control.ControlMain;
import control.ControlSettingsTabPlayback;

public class GuiPlaybackSettingsTableModel extends AbstractTableModel  {
	
    ControlSettingsTabPlayback control;
	
	public GuiPlaybackSettingsTableModel(ControlSettingsTabPlayback ctrl) {
		control = ctrl;
	}

	public int getColumnCount() {
		return 3;	
	}	

	public int getRowCount() {
		if (ControlMain.getSettings().getPlaybackOptions().size() == 0) {
			return 0;
		}
		return ControlMain.getSettings().getPlaybackOptions().size();
	}

	public Object getValueAt( int rowIndex, int columnIndex ) {
	    BOPlaybackOption playbackOption = (BOPlaybackOption)ControlMain.getSettings().getPlaybackOptions().get(rowIndex);
		if (columnIndex == 0) {
			return playbackOption.getName();
		}
		if (columnIndex == 1) {
			return playbackOption.getExecString();
		}
		else return playbackOption.isStandard();
	}
			
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	    BOPlaybackOption playbackOption = (BOPlaybackOption)ControlMain.getSettings().getPlaybackOptions().get(rowIndex);
		if (columnIndex == 0) {
		    playbackOption.setName((String)aValue);
		}
		if (columnIndex == 1) {
		    playbackOption.setExecString((String)aValue);
		}
		//nur eine Checkbox darf selektiert sein!!
		if (columnIndex == 2) {
			if (((Boolean)aValue).booleanValue()) {
				ArrayList playbackList = ControlMain.getSettings().getPlaybackOptions();
				for (int i=0; i<playbackList.size(); i++) { 
				    BOPlaybackOption boxx = (BOPlaybackOption)playbackList.get(i);
					boxx.setStandard(Boolean.FALSE);
					this.fireTableDataChanged();
				}
			}
			playbackOption.setStandard((Boolean)aValue);
		}
	}

	public String getColumnName( int columnIndex ) {
		if (columnIndex == 0) {
			return "Name"; 
		}
		if (columnIndex == 1) {
			return "Aufruf-Option";
		}
		else {
			return "Standard";
		}
	}
	
	/*
	 * Wenn die 1. Box angelegt wird, diese als Standard deklarieren
	 */
	public void addRow(BOPlaybackOption playbackOption) {
		if (ControlMain.getSettings().getPlaybackOptions().size()==0) {
		    playbackOption.setStandard(Boolean.TRUE);
		}
		ControlMain.getSettings().addPlaybackOption(playbackOption);
		fireTableDataChanged();
	}
	
	public void removeRow(int rowNumber) {
		try {
			ControlMain.getSettings().removePlaybackOption(rowNumber);
			fireTableDataChanged();
		} catch (ArrayIndexOutOfBoundsException ex) {SerAlertDialog.alert("Bitte eine Zeile markieren", control.getMainView());};
	}
	
	public boolean isCellEditable (int row, int col) {
	    Class columnClass = getColumnClass(col);
	    return true;
	}

	public Class getColumnClass (int col) {
       if (col==2) {
          return Boolean.class;
       }
       return String.class;
	}
}
