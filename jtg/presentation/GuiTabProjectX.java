/*
 * Created on 17.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package presentation;

import control.ControlProjectXTab;

/**
 * @author Treito
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GuiTabProjectX extends GuiTab {

	private ControlProjectXTab control;

	public GuiTabProjectX(ControlProjectXTab control) {
		this.setControl(control);
		initialize();
	}

	private  void initialize() {

	}

	public ControlProjectXTab getControl() {
		return control;
	}

	public void setControl(ControlProjectXTab control) {
		this.control = control;
	}
 }
