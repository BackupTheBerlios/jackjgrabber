package presentation;


import javax.swing.DefaultComboBoxModel;
import model.BOSender;
import control.ControlEnigmaTimerTab;


/**
 * @author Alexander Geist, Treito
 */
public class GuiEnigmaTimerSenderComboModel extends DefaultComboBoxModel { //implements ComboBoxModel {
	
	ControlEnigmaTimerTab control;
	
	public GuiEnigmaTimerSenderComboModel(ControlEnigmaTimerTab ctrl) {
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
