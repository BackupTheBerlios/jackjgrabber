/*
 * Created on 24.05.2004
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package presentation;


import javax.swing.table.*;
import javax.swing.*;

import model.BOBox;

import control.ControlSettingsTab;

import java.awt.*;
/**
 * @author QSE2419
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class GuiBoxTableCellRenderer extends DefaultTableCellRenderer {
	
	ControlSettingsTab control;
	
	public GuiBoxTableCellRenderer(ControlSettingsTab ctrl) {
		super();
		this.setControl(ctrl);
	}
	
	public Component getTableCellRendererComponent(
					JTable table,
					Object value,
					boolean isSelected,
					boolean hasFocus,
					int row,
					int column) 
		{
			BOBox box = (BOBox)control.getBoxList().get(row);
			JCheckBox checkbox = new JCheckBox();
			checkbox.setHorizontalAlignment(SwingConstants.CENTER);
			checkbox.setSelected(box.isStandard().booleanValue());
			return checkbox;
		}
	/**
	 * @return Returns the control.
	 */
	public ControlSettingsTab getControl() {
		return control;
	}
	/**
	 * @param control The control to set.
	 */
	public void setControl(ControlSettingsTab control) {
		this.control = control;
	}
}
