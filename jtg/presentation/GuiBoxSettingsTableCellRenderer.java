package presentation;


import javax.swing.table.*;
import javax.swing.*;

import model.BOBox;

import control.ControlSettingsTab;

import java.awt.*;
/**
 * @author Alexander Geist
 */
public class GuiBoxSettingsTableCellRenderer extends DefaultTableCellRenderer {
	
	ControlSettingsTab control;
	
	public GuiBoxSettingsTableCellRenderer(ControlSettingsTab ctrl) {
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
