package model;
/*
BOSettings.java by Geist Alexander 

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
import java.util.ArrayList;

public class BOSettings {
    
	private String locale;
	private String themeLayout;
    private String vlcPath;
    private ArrayList boxList;
    private boolean boxIpChanged = false;
    private boolean settingsChanged = false;
    private String streamingServerPort;
    
    public BOSettings() {
    }
    
    public BOSettings (String dboxIp, String login, String password, String vlcPath){
        this.vlcPath  = vlcPath;
    }
    
    public String getVlcPath() {
        return vlcPath;
    }
	
    public void setVlcPath(String vlcPath) {
        this.vlcPath = vlcPath;
    }
    public void setAll(BOSettings bosettings){       
        this.vlcPath  = bosettings.getVlcPath();
    }
	/**
	 * @return Returns the boxList.
	 */
	public ArrayList getBoxList() {
		return boxList;
	}
	/**
	 * @param boxList The boxList to set.
	 */
	public void setBoxList(ArrayList box) {
		this.boxList = box;
	}
	/**
	 * @return Returns the themeLayout.
	 */
	public String getThemeLayout() {
		return themeLayout;
	}
	/**
	 * @param themeLayout The themeLayout to set.
	 */
	public void setThemeLayout(String themeLayout) {
		this.themeLayout = themeLayout;
	}
	/**
	 * @return Returns the boxIpChanged.
	 */
	public boolean isBoxIpChanged() {
		return boxIpChanged;
	}
	/**
	 * @param boxIpChanged The boxIpChanged to set.
	 */
	public void setBoxIpChanged(boolean changed) {
		if (changed) {
			this.setSettingsChanged(true);
		}
		this.boxIpChanged = changed;
	}
	/**
	 * @return Returns the settingsChanged.
	 */
	public boolean isSettingsChanged() {
		return settingsChanged;
	}
	/**
	 * @param settingsChanged The settingsChanged to set.
	 */
	public void setSettingsChanged(boolean settingsChanged) {
		this.settingsChanged = settingsChanged;
	}
	/**
	 * @return Returns the locale.
	 */
	public String getLocale() {
		return locale;
	}
	/**
	 * @param locale The locale to set.
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}
	/**
	 * @return Returns the streamingServerPort.
	 */
	public String getStreamingServerPort() {
		return streamingServerPort;
	}
	/**
	 * @param streamingServerPort The streamingServerPort to set.
	 */
	public void setStreamingServerPort(String streamingServerPort) {
		this.streamingServerPort = streamingServerPort;
	}
}
