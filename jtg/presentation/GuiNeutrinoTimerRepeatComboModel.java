package presentation;


import javax.swing.DefaultComboBoxModel;
import control.ControlNeutrinoTimerTab;


/**
 * @author Alexander Geist
 */
public class GuiNeutrinoTimerRepeatComboModel extends DefaultComboBoxModel { //implements ComboBoxModel {
	
	ControlNeutrinoTimerTab control;
	
	public GuiNeutrinoTimerRepeatComboModel(ControlNeutrinoTimerTab ctrl) {
		this.setControl(ctrl);
	}
	
	public Object getElementAt(int index) {
		return control.repeatOptions[index];
	}

	public int getSize() {
		return control.repeatOptions.length;
	}
	
	/**
	 * @return Returns the control.
	 */
	public ControlNeutrinoTimerTab getControl() {
		return control;
	}
	/**
	 * @param control The control to set.
	 */
	public void setControl(ControlNeutrinoTimerTab control) {
		this.control = control;
	}
}
