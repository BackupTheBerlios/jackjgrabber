package control;
/*
ControlStartTab.java by Geist Alexander 

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
import java.io.File;
import java.io.IOException;

import model.BOSender;
import model.BOTimer;
import model.BOTimerList;
import presentation.GuiMainView;


public class ControlStartTab extends ControlTab {
	
	GuiMainView mainView;
	
	public ControlStartTab(GuiMainView view ) {
		this.setMainView(view);
	}

	public void run() {
	}
	
	public String checkWarns() {
	    File udrec;
	    String warnText=new String();
	    File save = new File(ControlMain.getSettingsPath().getSavePath());
	    if (ControlMain.getSettingsPath().getUdrecPath()==null &&
	            ControlMain.getSettingsPath().getUdrecPath().substring(0,4).equalsIgnoreCase("mono")) {
		    udrec = new File(ControlMain.getSettingsPath().getUdrecPath().substring(5));
	    } else {
		    udrec = new File(ControlMain.getSettingsPath().getUdrecPath());
	    }
	    File px = new File(ControlMain.getSettingsPath().getProjectXPath());
	    File shutdown = new File(ControlMain.getSettingsPath().getShutdownToolPath());
	    int boxCount = ControlMain.getSettingsMain().getBoxList().size();
	    int playerCount = ControlMain.getSettingsPlayback().getPlaybackOptions().size();
	    
	    if (!save.exists()) {
	        warnText=warnText+this.getHtmlString(ControlMain.getProperty("warn_save"));
	    }

        if (!udrec.exists()) {
            warnText=warnText+this.getHtmlString(ControlMain.getProperty("warn_udrec"));
	    }
	    
	    if (!px.exists()) {
	        warnText=warnText+this.getHtmlString(ControlMain.getProperty("warn_px"));
	    }
	    
	    if (!shutdown.exists()) {
	        warnText=warnText+this.getHtmlString(ControlMain.getProperty("warn_shutdown"));
	    }
	    
	    if (boxCount==0) {
	        warnText=warnText+this.getHtmlString(ControlMain.getProperty("warn_box"));
	    }
	    
	    if (playerCount==0) {
	        warnText=warnText+this.getHtmlString(ControlMain.getProperty("warn_player"));
	    }
  
	    return warnText;
	}
	
	public String getHtmlString(String string) {
	    return "<br><font color=red>"+string+"<font></br>";
	}
	
	public String getRunningSender() {
	    try {
	        BOSender sender = ControlMain.getBoxAccess().getRunningSender();
	        if (sender !=null) {
	            return sender.getName();    
	        }
        } catch (IOException e) {
            return new String();
        }
        return new String();
	}
	
	public String getNextTimerInfo() {
	    try {
            BOTimerList list = ControlMain.getBoxAccess().getTimerList();
            BOTimer timer = list.getFirstBoxRecordTimer();
            if (timer!=null) {
                return timer.getStartTime()+"    Sender:"+timer.getSenderName();    
            }
        } catch (IOException e) {
            return new String();
        }
        return new String();
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
}
