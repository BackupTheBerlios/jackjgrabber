package control;
/*
ControlDefaultTimerTab.java by Geist Alexander 

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
import java.io.IOException;
import java.util.ArrayList;

import model.BOTimer;
import model.BOTimerList;
import presentation.GuiMainView;
import presentation.timer.GuiTimerPanel;

public class ControlDefaultTimerTab extends ControlTabTimer {
	
	GuiMainView mainView;

	public GuiTimerPanel getTab() {
	    return null;
	}
	
	public void reReadTimerList() {}
	
	public ControlDefaultTimerTab(GuiMainView view) {
		this.setMainView(view);
	}
	
	public void run() {}
	
	public void initialize() {}
	
	public ArrayList getSenderList() {
	  return null;  
	}
	public String[] getRepeatOptions() {
		  return null;  
	}
	public String[] getTimerType() {
		  return null;  
	}
	public String convertShortEventRepeat(String shortString) {
		return null;	
	};
	
	public String convertLongEventRepeat(String longString) {
		return null;	
	};
	
	public void writeTimer(BOTimer timer) throws IOException {}

	public void addRecordTimer(BOTimer timer) {}
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
	
	public BOTimerList getTimerList() {
	    return null;
	}
}
