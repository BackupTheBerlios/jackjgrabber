/*
 * Created on 12.09.2004
 *
 */
package presentation;


import javax.swing.DefaultComboBoxModel;

import model.BOBouquet;
import control.ControlProgramTab;

/**
 * @author AlexG
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GuiBoquetsComboModel extends DefaultComboBoxModel { //implements ComboBoxModel {
	
	ControlProgramTab control;
	
	public GuiBoquetsComboModel(ControlProgramTab ctrl) {
		this.setControl(ctrl);
	}
	
	public Object getElementAt(int index) {
		BOBouquet bouquet = (BOBouquet)this.getControl().getBouquetList().get(index);
		return bouquet.getBouquetName();
	}

	public int getSize() {
		return this.getControl().getBouquetList().size();
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
