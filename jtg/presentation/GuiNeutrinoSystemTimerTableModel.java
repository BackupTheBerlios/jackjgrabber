package presentation;



import javax.swing.table.AbstractTableModel;
import model.BOTimer;
import control.ControlNeutrinoTimerTab;

/**
 * @author Alexander Geist
 */
public class GuiNeutrinoSystemTimerTableModel extends AbstractTableModel 
{
	ControlNeutrinoTimerTab control;
	
	public GuiNeutrinoSystemTimerTableModel(ControlNeutrinoTimerTab ctrl){
		this.setControl(ctrl);
	}

	public int getColumnCount() {
		return 4;	
	}	

	public int getRowCount() {
		if (this.getControl().getTimerList() != null) {
			return this.getControl().getTimerList().size();
		}
		return 0;
	}

	public Object getValueAt( int rowIndex, int columnIndex ) {
		BOTimer timer = (BOTimer)this.getControl().getTimerList().get(rowIndex);
		if (columnIndex == 0) {
			return timer.getEventType();
		} if (columnIndex == 1) {
			return timer.getStartDate();
		} if (columnIndex == 2) {
			return timer.getStartTime();
		} else {
			return timer.getEventRepeat();
		}
	}
	
	public void setValueAt(Object value, int row, int col) {
		if (col == 0) {
			BOTimer timer = (BOTimer)this.getControl().getTimerList().get(row);
			timer.setEventType((String)value);
			this.fireTableDataChanged();
		}
		if (col == 3) {
			BOTimer timer = (BOTimer)this.getControl().getTimerList().get(row);
			timer.setEventRepeat((String)value);
			this.fireTableDataChanged();
		}
    }

	public String getColumnName( int columnIndex ) {
		if (columnIndex == 0) {
			return "Timer-Typ"; 
		} if (columnIndex == 1) {
			return "Datum";
		} if (columnIndex == 2) {
			return "Zeit";
		} else {
			return "Wiederholung";
		}
	}
	
	public boolean isCellEditable (int row, int col) {
	    Class columnClass = getColumnClass(col);
	    return true;
	}
	
	public ControlNeutrinoTimerTab getControl() {
		return control;
	}
	
	public void setControl(ControlNeutrinoTimerTab control) {
		this.control = control;
	}
	
//	public Class getColumnClass(int c) {
//        return getValueAt(0, c).getClass();
//	}
}
