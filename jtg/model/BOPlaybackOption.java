/*
BOPlaybackOption.java by Geist Alexander 

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
package model;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import control.ControlMain;

public class BOPlaybackOption {
    
    public String name="mplayer";
    public String execString="d://programme/mplayer/mplayer.exe http://$ip:31339/$vPid,$aPid1";
    public Boolean standard = Boolean.FALSE;
    public Boolean logOutput = Boolean.TRUE;

    /**
     * @return Returns the execString.
     */
    public String getExecString() {
        if (execString == null) {
            return "";
        }
        return execString;
    }
    /**
     * @param execString The execString to set.
     */
    public void setExecString(String execString) {
        if (this.execString == null || !this.execString.equals(execString)) {
	        this.execString = execString;
	        this.setSettingsChanged(true);
	    }
    }
    /**
     * @return Returns the name.
     */
    public String getName() {
        if (name == null) {
            return "";
        }
        return name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        if (this.name == null || !this.name.equals(name)) {
	        this.name = name;
	        this.setSettingsChanged(true);
	    }
    }
    /**
	 * @return Returns the standard.
	 */
	public Boolean isStandard() {
		return standard;
	}
	/**
	 * @param standard The standard to set.
	 */
	public void setStandard(Boolean standard) {
	    if (this.standard.booleanValue() != standard.booleanValue()) {
	        this.standard = standard;
	        this.setSettingsChanged(true);
	    }
	}
	
	/**
	 * @param settingsChanged The settingsChanged to set.
	 */
	public void setSettingsChanged(boolean settingsChanged) {
		ControlMain.getSettings().setSettingsChanged(settingsChanged);
	}
	/**
	 * @param printOutput The printOutput to set.
	 */
	public void setLogOutput(Boolean printOutput) {
		if (this.logOutput.booleanValue() != printOutput.booleanValue()) {
	        this.logOutput = printOutput;
	        this.setSettingsChanged(true);
	    }
	}
	/**
	 * @return Returns the printOutput.
	 */
	public Boolean isLogOutput() {
		return logOutput;
	}
	
	/*
	 * Rückgabe der Stardard-Playbackoption
	 * oder Start eines Abfragedialogs
	 */
	public static BOPlaybackOption detectPlaybackOption() {
		if (ControlMain.getSettings().getPlaybackOptions() != null && ControlMain.getSettings().getPlaybackOptions().size()>0) {
			BOPlaybackOption option;
	        if (ControlMain.getSettings().isAlwaysUseStandardPlayback()) {
	            option = ControlMain.getSettings().getStandardPlaybackOption();
	        } else {
	            option = startPlaybackOptionsQuestDialog();
	        }
	        return option;
		}
		return null;
	}
	
	private static BOPlaybackOption startPlaybackOptionsQuestDialog() {
	    ArrayList options = ControlMain.getSettings().getPlaybackOptions();
	    
	    String ret = (String)JOptionPane.showInputDialog(
	    		ControlMain.getControl().getView(),
                ControlMain.getProperty("msg_choosePlayback2"),
                ControlMain.getProperty("msg_choose"),
                JOptionPane.QUESTION_MESSAGE,
                null,
                ControlMain.getSettings().getPlaybackOptionNames(),
                ControlMain.getSettings().getPlaybackOptionNames()[0]
              );
	    if (ret!=null) {
	        return ControlMain.getSettings().getPlaybackOption(ret);
	    }
	    return null;
	}
}
