package presentation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import javax.swing.table.TableModel;

/**
 * @author alexg
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GuiTimerTableSorter extends GuiTableSorter {
	
	public GuiTimerTableSorter() {
        this.mouseListener = new MouseHandler();
        this.tableModelListener = new TableModelHandler();
    }

    public GuiTimerTableSorter(TableModel tableModel) {
        this();
        setTableModel(tableModel);
    }
	
	 protected Comparator getComparator(int column) {
    	if (column == 1) {
    		return DATE_COMPARATOR;
    	}
        Class columnType = tableModel.getColumnClass(column);
        Comparator comparator = (Comparator) columnComparators.get(columnType);
        if (comparator != null) {
            return comparator;
        }
        if (Comparable.class.isAssignableFrom(columnType)) {
            return COMPARABLE_COMAPRATOR;
        }
        return LEXICAL_COMPARATOR;
    }
	
	public static final Comparator DATE_COMPARATOR = new Comparator() {
        public int compare(Object o1, Object o2) {
        	try {
				String firstString = (String)o1;
				String secondString = (String)o2;
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy   HH:mm");
				Date firstDate = sdf.parse(firstString);
				Date secondDate = sdf.parse(secondString);
				return firstDate.compareTo(secondDate);
			} catch (ParseException e) {
				return 0;
			}
        }
    };
}
