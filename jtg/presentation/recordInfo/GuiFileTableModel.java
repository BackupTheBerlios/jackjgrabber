package presentation.recordInfo;

import java.io.*;
import java.util.*;

import javax.swing.event.*;
import javax.swing.table.*;

import service.*;

import model.*;

/**
 * @author Reinhard Achleitner
 * @version 06.12.2004
 *  
 */
public class GuiFileTableModel extends DefaultTableModel implements TreeSelectionListener {

	private ArrayList files = new ArrayList();
	
	private String[] columns = new String[]{"Name"};

	/**
	 *  
	 */
	public GuiFileTableModel() {
		super();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.DefaultTableModel#getRowCount()
	 */
	public int getRowCount() {
		if (files != null) {
			return files.size();
		} else {
			return 0;
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return columns.length;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getColumnName(int)
	 */
	public String getColumnName(int column) {
		return columns[column];
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#setValueAt(java.lang.Object, int, int)
	 */
	public void setValueAt(Object aValue, int row, int column) {
		try
		{
			if (aValue != null && aValue.toString().length() > 0)
			{
				Object[] oneFile = (Object[]) files.get(row);
				File f = (File) oneFile[2];
				File newF = new File(f.getParent(),aValue.toString());
				if (f.renameTo(newF))
				{
					oneFile[0] = newF.getName();
					oneFile[2] = newF;
				}
				else
				{
					fireTableDataChanged();
				}
			}
		}
		catch(Exception e)
		{
			fireTableDataChanged();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int column) {
		Object[] oneFile = (Object[]) files.get(row);
		return oneFile[column];
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	public void valueChanged(TreeSelectionEvent e) {
		ArrayList list = new ArrayList();
		if (e.getPath() != null) {
			Object o = e.getPath().getLastPathComponent();
			if (o instanceof BaseTreeNode && ((BaseTreeNode)o).getUserObject() instanceof BOFileWrapper) {
				BOFileWrapper fileWr = (BOFileWrapper) ((BaseTreeNode) o).getUserObject();
				File[] files = fileWr.listFiles();
				if (files != null)
				{
					for (int i = 0; i < files.length; i++) {
						if (!files[i].isDirectory()) {
							Object[] oneFile = new Object[3];
							oneFile[0] = files[i].getName();
							oneFile[1] = SerHelper.calcSize(files[i].length(),"MB");
							oneFile[2] = files[i].getAbsoluteFile();
							list.add(oneFile);
						}
					}
				}
			}
		}
		files = list;
		fireTableDataChanged();

	}

}