package presentation;


import javax.swing.DefaultComboBoxModel;

import model.BOBox;
import control.ControlMain;
import control.ControlProgramTab;

/**
 * @author Alexander Geist
 * ComboBoxModel der JCombobox IP-Auswahl im Programm-Tab
 */
public class GuiIpListComboModel extends DefaultComboBoxModel { //implements ComboBoxModel {
	
	ControlProgramTab control;
	
	public GuiIpListComboModel(ControlProgramTab ctrl) {
		this.setControl(ctrl);
	}
	
	public Object getElementAt(int index) {
		BOBox box = (BOBox)ControlMain.getSettings().getBoxList().get(index);
		return box.getDboxIp();
	}

	public int getSize() {
		return ControlMain.getSettings().getBoxList().size();
	}
	
	/**
	 * @return Returns the control.
	 */
	public ControlProgramTab getControl() {
		return control;
	}
	/**
	 * @param control The control to set.
	 */
	public void setControl(ControlProgramTab control) {
		this.control = control;
	}
}
