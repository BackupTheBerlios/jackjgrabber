package presentation;



import javax.swing.table.AbstractTableModel;
import model.BOTimer;
import control.ControlEnigmaTimerTab;

/**
 * @author Alexander Geist, Treito
 */
public class GuiEnigmaRecordTimerTableModel extends AbstractTableModel 
{
	ControlEnigmaTimerTab control;
	
	public GuiEnigmaRecordTimerTableModel(ControlEnigmaTimerTab ctrl){
		this.setControl(ctrl);
	}

	public int getColumnCount() {
		return 8;	
	}	

	public int getRowCount() {
		if (this.getControl().getTimerList() != null) {
			return this.getControl().getTimerList()[0].size();
		}
		return 0;
	}

	public Object getValueAt( int rowIndex, int columnIndex ) {
		BOTimer timer = (BOTimer)this.getControl().getTimerList()[0].get(rowIndex);
		if (columnIndex == 0) {
		    return timer.getEventType();
		} if (columnIndex == 1) {
			return timer.getSenderName();
		} if (columnIndex == 2) {
			return timer.getStartDate();
		} if (columnIndex == 3) {
			return timer.getStartTime();
		//} if (columnIndex == 4)
		//	return timer.getStopTime();
		} if (columnIndex == 5) {
			return control.convertShortEventRepeat(timer.getEventRepeat());
		} if (columnIndex == 6) {
		    return timer.getEventId();
		} else {
			return timer.getDescription(); 
		}
	}
	
	public void setValueAt(Object value, int row, int col) {
		if (col == 1) {
			BOTimer timer = (BOTimer)this.getControl().getTimerList()[0].get(row);
			timer.setSenderName((String)value);
		}
		if (col == 2) {
			BOTimer timer = (BOTimer)this.getControl().getTimerList()[0].get(row);
			System.out.println(value);
		}
		if (col == 3) {
			BOTimer timer = (BOTimer)this.getControl().getTimerList()[0].get(row);
			System.out.println(value);
		}
		if (col == 4) {
			BOTimer timer = (BOTimer)this.getControl().getTimerList()[0].get(row);
			System.out.println(value);
		}
		if (col == 5) {
			BOTimer timer = (BOTimer)this.getControl().getTimerList()[0].get(row);
			timer.setEventRepeat(control.convertLongEventRepeat((String)value));
			control.getTab().selectRepeatDaysForRecordTimer(timer);
		}
    }

	public String getColumnName( int columnIndex ) {
		if (columnIndex == 0) {
		    return "Status";
		}if (columnIndex == 1) {
			return "Sender"; 
		} if (columnIndex == 2) {
			return "Datum";
		} if (columnIndex == 3) {
			return "Start";
		} if (columnIndex == 4) {
			return "Ende";
		} if (columnIndex == 5) {
			return "Wiederholung";
		} if (columnIndex == 6) {
			return "Nach Aufn.";
		} else {
			return "Titel";
		}
	}
	
	public boolean isCellEditable (int row, int col) {
	    Class columnClass = getColumnClass(col);
	    if (col==5) {
	    	return false;
	    }
	    return true;
	}
	
	public ControlEnigmaTimerTab getControl() {
		return control;
	}
	
	public void setControl(ControlEnigmaTimerTab control) {
		this.control = control;
	}
}
