package presentation;

import control.ControlDefaultTimerTab;
import control.ControlTab;

/**
 * Aleander Geist
 */
public class GuiDafaultTimerPanel extends GuiTimerPanel {

	ControlDefaultTimerTab control;
	
	public GuiDafaultTimerPanel(ControlDefaultTimerTab ctrl) {
		control = ctrl;
	}
	
	public ControlTab getControl() {
		return control;
	}
}
