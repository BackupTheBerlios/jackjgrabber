package presentation.movieguide;

import java.util.Comparator;
import java.util.GregorianCalendar;

import javax.swing.table.TableModel;

import presentation.GuiTableSorter;
import service.SerFormatter;

/**
 * @author alexg
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GuiMovieGuideTimerTableSorter extends GuiTableSorter {
	
	public GuiMovieGuideTimerTableSorter() {
        this.mouseListener = new MouseHandler();
        this.tableModelListener = new TableModelHandler();
    }

    public GuiMovieGuideTimerTableSorter(TableModel tableModel) {
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
            GregorianCalendar firstDate = SerFormatter.getDateFromString((String)o1, "dd.MM.yy   HH:mm");
            GregorianCalendar secondDate = SerFormatter.getDateFromString((String)o1, "dd.MM.yy   HH:mm");
            return SerFormatter.compareDates(firstDate, secondDate);
        }
    };
}
