package control;
/*
ControlProjectXTab.java by Geist Alexander 

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
import projectX.common.X;

public class ControlProjectXTab extends ControlTab {
	
	GuiMainView mainView;
	Object[] pxArgs;
	
	public ControlProjectXTab(GuiMainView view,Object[] args ) {
		this.setMainView(view);
		this.setPxArgs(args);
	}

	/**
	 * Start ProjectX 
	 */
	public void initialize() {
		JPanel pxPanel = X.main(this.getPxArgs());
		this.getMainView().getMainTabPane().setTabProjectX(pxPanel);
	}
	/**
	 * @return Returns the mainView.
	 */
	public GuiMainView getMainView() {
		return mainView;
	}
	/**
	 * @param mainView The mainView to set.
	 */
	public void setMainView(GuiMainView mainView) {
		this.mainView = mainView;
	}

	/**
	 * @return String[]
	 */
	public String[] getPxArgs() {
		if (pxArgs==null) {
			return new String[0];
		}
		String[] args = new String[pxArgs.length];
		for (int i=0; i<args.length; i++) {
			args[i] = (String)pxArgs[i];
		}
		return args;
	}

	/**
	 * Sets the pxArgs.
	 * @param pxArgs The pxArgs to set
	 */
	public void setPxArgs(Object[] pxArgs) {
		this.pxArgs = pxArgs;
	}

}
