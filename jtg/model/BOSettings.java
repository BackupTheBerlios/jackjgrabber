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

/**
 * Klasse referenziert die Settings
 * Settings werden beim Start gelesen und beim Beenden gespeichert
 * Sind keine Änderungen an den Settings vorgenommen worden, werden diese nicht gespeichert
 * Werden settings geaendert muss die Variable "settingsChanged" auf true gesetzt werden.
 * Dies geschieht in den Setter-Methoden der einzelnen Settings-Optionen
 */
public class BOSettings {
    
	public String locale;
	public String themeLayout;
	public ArrayList boxList;
	public boolean settingsChanged = false;
	public boolean projectXSettingsChanged = false;
	public String streamingServerPort;
	public boolean startStreamingServer;
	public boolean startPX;
	public String savePath;
	public String udrecPath;
	public String playbackString;
    public String jgrabberStreamType;	//PES, TS, ES
    public String udrecStreamType;		//PES, TS
    public int streamingEngine; //0=JGrabber, 1=udrec
    
    public void removeBox(int number) {
    	setSettingsChanged(true);
    	getBoxList().remove(number);
    }
    
    public void addBox(BOBox box) {
    	setSettingsChanged(true);
    	getBoxList().add(box);
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
	public void setThemeLayout(String layout) {
		if(!this.themeLayout.equals(layout)) {
			setSettingsChanged(true);
			this.themeLayout = layout;
		}
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
		if(!this.locale.equals(locale)) {
			setSettingsChanged(true);
			this.locale = locale;
		}	
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
		if (!this.streamingServerPort.equals(streamingServerPort)) {
			setSettingsChanged(true);
			this.streamingServerPort = streamingServerPort;
		}
	}
	/**
	 * @return Returns the startStreamingServer.
	 */
	public boolean isStartStreamingServer() {
		return startStreamingServer;
	}
	/**
	 * @param startStreamingServer The startStreamingServer to set.
	 */
	public void setStartStreamingServer(boolean startServer) {
		if (this.startStreamingServer != startServer) {
			setSettingsChanged(true);
			this.startStreamingServer = startServer;
		}
	}
	/**
	 * @return Returns the savePath.
	 */
	public String getSavePath() {
		return savePath;
	}
	/**
	 * @param savePath The savePath to set.
	 */
	public void setSavePath(String savePath) {
		if(!this.savePath.equals(savePath)) {
			setSettingsChanged(true);
			this.savePath = savePath;
		}	
	}
	/**
	 * @return Returns the projectXSettingsChanged.
	 */
	public boolean isProjectXSettingsChanged() {
		return projectXSettingsChanged;
	}
	/**
	 * @param projectXSettingsChanged The projectXSettingsChanged to set.
	 */
	public void setProjectXSettingsChanged(boolean projectXSettingsChanged) {
		this.projectXSettingsChanged = projectXSettingsChanged;
	}
	/**
	 * @return Returns the playbackPlayer.
	 */
	public String getPlaybackString() {
		if (playbackString!=null) {
			return playbackString;
		}
		return"";
	}
	/**
	 * @param playbackPlayer The playbackPlayer to set.
	 */
	public void setPlaybackString(String playbackString) {
		if (!this.playbackString.equals(playbackString)) {
			setSettingsChanged(true);
			this.playbackString = playbackString;
		}
	}
	/**
	 * @return int
	 */
	public String getJgrabberStreamType() {
		return jgrabberStreamType;
	}

	/**
	 * Sets the streamType.
	 * @param streamType The streamType to set
	 */
	public void setJgrabberStreamType(String streamType) {
		if(this.jgrabberStreamType==null || !this.jgrabberStreamType.equals(streamType)) {
			setSettingsChanged(true);
			this.jgrabberStreamType = streamType;
		}
	}
	public void setStreamType(String streamType) {
		if(this.getStreamingEngine()==0) {
			this.setJgrabberStreamType(streamType);
		} else {
			this.setUdrecStreamType(streamType);
		}
	}
	/**
	 * @return Returns the startPX.
	 */
	public boolean isStartPX() {
		return startPX;
	}
	/**
	 * @param startPX The startPX to set.
	 */
	public void setStartPX(boolean startPX) {
		if (this.startPX != startPX) {
			setSettingsChanged(true);
			this.startPX = startPX;
		}
	}
	/**
	 * @return Returns the streamingEngine.
	 */
	public int getStreamingEngine() {
		return streamingEngine;
	}
	/**
	 * @param streamingEngine The streamingEngine to set.
	 */
	public void setStreamingEngine(int engine) {
		if(this.streamingEngine!=engine) {
			setSettingsChanged(true);
			this.streamingEngine = engine;
		}	
		
	}
	/**
	 * @return Returns the udrecPath.
	 */
	public String getUdrecPath() {
		return udrecPath;
	}
	/**
	 * @param udrecPath The udrecPath to set.
	 */
	public void setUdrecPath(String path) {
		if(this.udrecPath==null || !this.udrecPath.equals(path)) {
			setSettingsChanged(true);
			this.udrecPath = path;
		}	
	}
	/**
	 * @return Returns the jUdrecStreamType.
	 */
	public String getUdrecStreamType() {
		return udrecStreamType;
	}
	/**
	 * @param udrecStreamType The jUdrecStreamType to set.
	 */
	public void setUdrecStreamType(String streamType) {
		if(this.udrecStreamType==null ||!this.udrecStreamType.equals(streamType)) {
			setSettingsChanged(true);
			this.udrecStreamType = streamType;
		}
	}
}
