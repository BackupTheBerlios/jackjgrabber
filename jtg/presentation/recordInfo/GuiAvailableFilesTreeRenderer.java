package presentation.recordInfo;

import java.awt.*;

import javax.swing.*;
import javax.swing.tree.*;

import model.*;

import service.*;

/**
 * @author Reinhard Achleitner
 * @version 02.12.2004
 *  
 */
public class GuiAvailableFilesTreeRenderer extends DefaultTreeCellRenderer {

	/**
	 *  
	 */
	public GuiAvailableFilesTreeRenderer() {
		super();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean,
	 *      boolean, int, boolean)
	 */
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		JLabel lab = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		if (row == 0) {
			lab.setIcon(SerIconManager.getInstance().getIcon("grabber1.gif"));

		} else {
			if (value instanceof FileTypeTreeNode) {
				Icon icon = ((FileTypeTreeNode) value).getIcon();
				if (icon != null) {
					lab.setIcon(icon);
				}

			}
			
			if (leaf)
			{
				lab.setIcon(null);
			}
		}
		return lab;

	}

}