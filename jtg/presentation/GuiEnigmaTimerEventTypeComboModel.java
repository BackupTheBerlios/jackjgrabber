package presentation;


import javax.swing.DefaultComboBoxModel;
import control.ControlEnigmaTimerTab;


/*
GuiEnigmaTimerEventTypeComboModel.java by Geist Alexander, Treito

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

public class GuiEnigmaTimerEventTypeComboModel extends DefaultComboBoxModel { //implements ComboBoxModel {
	
	ControlEnigmaTimerTab control;
	
	public GuiEnigmaTimerEventTypeComboModel(ControlEnigmaTimerTab ctrl) {
		this.setControl(ctrl);
	}
	
	public Object getElementAt(int index) {
		return control.timerType[index];
	}

	public int getSize() {
		return control.timerType.length;
	}
	
	/**
	 * @return Returns the control.
	 */
	public ControlEnigmaTimerTab getControl() {
		return control;
	}
	/**
	 * @param control The control to set.
	 */
	public void setControl(ControlEnigmaTimerTab control) {
		this.control = control;
	}
}
