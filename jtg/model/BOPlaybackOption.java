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

import control.ControlMain;

public class BOPlaybackOption {
    
    public String name="mplayer";
    public String execString="d://programme/mplayer/mplayer.exe http://$ip:31339/$vPid,$aPid";
    public Boolean standard = Boolean.FALSE;

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
	    if (this.standard.compareTo(standard) != 0) {
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
}
