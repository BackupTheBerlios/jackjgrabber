package presentation;
/*
GuiBoxSettingsTableCellRenderer.java by Geist Alexander 

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

import javax.swing.table.*;
import javax.swing.*;

import model.BOBox;

import control.ControlSettingsTab;

import java.awt.*;

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
