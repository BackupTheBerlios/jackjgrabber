package presentation;


import javax.swing.DefaultComboBoxModel;
import control.ControlEnigmaTimerTab;


/**
 * @author Alexander Geist, Treito
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
