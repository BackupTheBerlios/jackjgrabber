package presentation;


import javax.swing.DefaultComboBoxModel;

import model.BOBouquet;
import model.BOSender;
import control.ControlNeutrinoTimerTab;
import control.ControlProgramTab;

/**
 * @author Alexander Geist
 */
public class GuiNeutrinoTimerSenderComboModel extends DefaultComboBoxModel { //implements ComboBoxModel {
	
	ControlNeutrinoTimerTab control;
	
	public GuiNeutrinoTimerSenderComboModel(ControlNeutrinoTimerTab ctrl) {
		this.setControl(ctrl);
	}
	
	public Object getElementAt(int index) {
		BOSender bouquet = (BOSender)this.getControl().getSenderList().get(index);
		return bouquet.getName();
	}

	public int getSize() {
		if (this.getControl().getSenderList()!=null){
			return this.getControl().getSenderList().size();
		}
		return 0;
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
