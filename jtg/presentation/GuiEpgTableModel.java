package presentation;


import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import model.BOEpg;
import control.ControlProgramTab;
import service.SerFormatter;
/**
 * @author Geist Alexander
 *
 * TableModel des EPG-Tables
 * es wird eine eigene EPG-ArrayList verwaltet!! 
 * Diese EPG-List enthält nur EPG´s die mit dem Datum des DateChoosers übereinstimmen
 */
public class GuiEpgTableModel extends AbstractTableModel 
{
	ControlProgramTab control;
	ArrayList epgList = new ArrayList();
	
	public GuiEpgTableModel(ControlProgramTab ctrl) {
		this.setControl(ctrl);
		this.createEpgList();
	}

	public int getColumnCount() {
		return 5;	
	}	

	public int getRowCount() {
		return this.getEpgList().size();
	}

	public Object getValueAt( int rowIndex, int columnIndex ) {
		BOEpg epg = (BOEpg)this.getEpgList().get(rowIndex);

		if (columnIndex == 0) {
			return epg.getEventId();
		} if (columnIndex == 1) {
			return epg.getStartTime();
		} if (columnIndex == 2) {
			return epg.getEndTime();
		} if (columnIndex == 3) {
			return epg.getDuration();
		} else {
			return epg.getTitle();
		}
	}
	public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

	public String getColumnName( int columnIndex ) {
		if (columnIndex == 0) {
			return "Event-ID"; 
		} if (columnIndex == 1) {
			return "Start";
		} if (columnIndex == 2) {
			return "Ende";
		} if (columnIndex == 3) {
			return "Dauer";
		} else {
			return "Titel";
		}
	}
	
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	/**
	 * create a new EPG-List which compare to the Date of DateChooser
	 */
	public void createEpgList() {
		this.setEpgList(new ArrayList());
		if (control.getSelectedSender() != null && control.getSelectedSender().getEpg() != null ) {
			ArrayList fullList = control.getSelectedSender().getEpg();
			Date dateChooserDate = control.getDateChooserDate();
			
			for (int i=0; i<fullList.size(); i++) {
				BOEpg epg = (BOEpg)fullList.get(i);                                
                                if (SerFormatter.shortDate(epg.getStartdate().getTime()).equalsIgnoreCase(SerFormatter.shortDate(dateChooserDate.getTime()))){                              
				//if (epg.getStartdate().compareTo(dateChooserDate) == 0) {
					this.getEpgList().add(epg);
				}
			}
		}
	}
	
	/**
	 * update the own EPG-List
	 */
	public void fireTableRowsInserted(int i, int i2) {
		this.createEpgList();
		super.fireTableRowsInserted(i, i2);
	}
	/**
	 * @return ArrayList
	 */
	public ArrayList getEpgList() {
		return epgList;
	}

	/**
	 * Sets the epgList.
	 * @param epgList The epgList to set
	 */
	public void setEpgList(ArrayList epgList) {
		this.epgList = epgList;
	}

	/**
	 * @return Returns the control.
	 */
	public ControlProgramTab getControl() {
		return control;
	}
	/**
	 * @param control The control to set.
	 */
	public void setControl(ControlProgramTab control) {
		this.control = control;
	}
	
}
