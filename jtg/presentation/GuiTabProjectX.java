package presentation;
/*
GuiTabProjectX.java by Geist Alexander 

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
import control.ControlProjectXTab;

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
