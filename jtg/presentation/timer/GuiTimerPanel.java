package presentation.timer;
/*
GuiTimerPanel.java by Geist Alexander 

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.  

*/ 
import javax.swing.JPanel;

import presentation.GuiMainView;

import control.ControlDefaultTimerTab;
import control.ControlEnigmaTimerTab;
import control.ControlNeutrinoTimerTab;
import control.ControlTabTimer;

/**
 * Imagespezifische Timer-Gui's und Controls werden hier angelegt
 */
public abstract class GuiTimerPanel extends JPanel {
	
	public static GuiTimerPanel getTimerPanel(String boxName, GuiMainView view) {
		if (boxName.equals("Enigma")) {
			ControlEnigmaTimerTab control = new ControlEnigmaTimerTab(view);
			GuiEnigmaTimerPanel panel = new GuiEnigmaTimerPanel(control);
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
	
	public abstract ControlTabTimer getControl();
}
