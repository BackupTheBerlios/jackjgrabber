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

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import service.SerAlertDialog;
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
	 * R�ckgabe der Stardard-Playbackoption
	 * oder Start eines Abfragedialogs
	 */
	public static BOPlaybackOption detectPlaybackOption() {
		if (getPlaybackSettings().getPlaybackOptions() != null && getPlaybackSettings().getPlaybackOptions().size()>0) {
			BOPlaybackOption option;
	        if (getPlaybackSettings().isAlwaysUseStandardPlayback()) {
	            option = getPlaybackSettings().getStandardPlaybackOption();
	        } else {
	            option = startPlaybackOptionsQuestDialog();
	        }
	        return option;
		}
		SerAlertDialog.alert(ControlMain.getProperty("msg_playbackOptionError"), ControlMain.getControl().getView());
		return null;
	}
	
	private static BOPlaybackOption startPlaybackOptionsQuestDialog() {
	    ArrayList options = getPlaybackSettings().getPlaybackOptions();
	    JList list = new JList(options.toArray());
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    BOPlaybackOption def = getPlaybackSettings().getStandardPlaybackOption();
	    if (def != null) {
	    	list.setSelectedValue(def,true);
	    }
	    int ret = JOptionPane.showConfirmDialog(
	            ControlMain.getControl().getView(),
	            new Object[] {ControlMain.getProperty("msg_choosePlayback2"),new JScrollPane(list)},
                ControlMain.getProperty("msg_choose"),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
	    );
	    if (ret == JOptionPane.OK_OPTION) {
	        BOPlaybackOption opt = (BOPlaybackOption)list.getSelectedValue();
	        return opt;
	    }
	    return null;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getName();
	}
	
	private static BOSettingsPlayback getPlaybackSettings() {
		return ControlMain.getSettings().getPlaybackSettings();
	}
	
}
