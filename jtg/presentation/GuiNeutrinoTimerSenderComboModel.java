package presentation;


import javax.swing.DefaultComboBoxModel;
import model.BOSender;
import control.ControlNeutrinoTimerTab;


/**
 * @author Alexander Geist
 */
public class GuiNeutrinoTimerSenderComboModel extends DefaultComboBoxModel { //implements ComboBoxModel {
	
	ControlNeutrinoTimerTab control;
	
	public GuiNeutrinoTimerSenderComboModel(ControlNeutrinoTimerTab ctrl) {
		this.setControl(ctrl);
	}
	
	public Object getElementAt(int index) {
		BOSender sender = (BOSender)this.getControl().getSenderList().get(index);
		return sender.getName();
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
