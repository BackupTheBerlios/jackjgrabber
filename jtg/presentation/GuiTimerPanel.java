package presentation;

import javax.swing.JPanel;

import control.ControlDefaultTimerTab;
import control.ControlNeutrinoTimerTab;
import control.ControlTab;

/**
 * @author Alexander Geist
 * 
 * Imagespezifische Timer-Gui's und Controls werden hier angelegt
 */
public abstract class GuiTimerPanel extends JPanel {
	
	public static GuiTimerPanel getTimerPanel(String boxName, GuiMainView view) {
		if (boxName.equals("Enigma")) {
			ControlNeutrinoTimerTab control = new ControlNeutrinoTimerTab(view);
			GuiNeutrinoTimerPanel panel = new GuiNeutrinoTimerPanel(control);
			return panel;
		}
		if (boxName.equals("Neutrino")) {
			ControlNeutrinoTimerTab control = new ControlNeutrinoTimerTab(view);
			GuiNeutrinoTimerPanel panel = new GuiNeutrinoTimerPanel(control);
			return panel;
		} else {
			ControlDefaultTimerTab control = new ControlDefaultTimerTab(view);
			return new GuiDafaultTimerPanel(control);
		}
		
			
	}
	
	public abstract ControlTab getControl();
}
