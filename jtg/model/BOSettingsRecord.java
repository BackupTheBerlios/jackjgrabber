package model;

import java.util.StringTokenizer;

/*
BOSettingsRecords.java by Geist Alexander 

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
public class BOSettingsRecord {
	
	private BOSettings settings;
	public String streamingServerPort;
	public boolean startStreamingServer;
	public boolean startPX;
	public boolean recordAllPids;
	public boolean ac3ReplaceStereo;
	public String savePath;
	public String udrecPath;
	
	public String projectXPath;
	public String udrecOptions;
	
	public String jgrabberStreamType; //PES, TS, ES
	public String udrecStreamType; //PES, TS
	public int streamingEngine; //0=JGrabber, 1=udrec
	public String recordTimeBefore;
	public String recordTimeAfter;
	
	public boolean storeEPG;
	public boolean storeLogAfterRecord;
	public boolean recordVtxt;

	public BOSettingsRecord(BOSettings settings) {
		this.setSettings(settings);
	}
	
	private void setSettingsChanged(boolean value) {
		this.getSettings().setSettingsChanged(value);
	}
	
	/**
	 * @return Returns the settings.
	 */
	public BOSettings getSettings() {
		return settings;
	}
	/**
	 * @param settings The settings to set.
	 */
	public void setSettings(BOSettings settings) {
		this.settings = settings;
	}
	
	/**
	 * @return Returns the streamingServerPort.
	 */
	public String getStreamingServerPort() {
		return streamingServerPort;
	}
	/**
	 * @param streamingServerPort
	 *            The streamingServerPort to set.
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
	 * @param startStreamingServer
	 *            The startStreamingServer to set.
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
	 * @param savePath
	 *            The savePath to set.
	 */
	public void setSavePath(String savePath) {
		if (!this.savePath.equals(savePath)) {
			setSettingsChanged(true);
			this.savePath = savePath;
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
	 * 
	 * @param streamType
	 *            The streamType to set
	 */
	public void setJgrabberStreamType(String streamType) {
		if (this.jgrabberStreamType == null
				|| !this.jgrabberStreamType.equals(streamType)) {
			setSettingsChanged(true);
			this.jgrabberStreamType = streamType;
		}
	}
	public void setStreamType(String streamType) {
		if (this.getStreamingEngine() == 0) {
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
	 * @param startPX
	 *            The startPX to set.
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
	 * @param streamingEngine
	 *            The streamingEngine to set.
	 */
	public void setStreamingEngine(int engine) {
		if (this.streamingEngine != engine) {
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
	 * @param udrecPath
	 *            The udrecPath to set.
	 */
	public void setUdrecPath(String path) {
		if (this.udrecPath == null || !this.udrecPath.equals(path)) {
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
	 * @param udrecStreamType
	 *            The jUdrecStreamType to set.
	 */
	public void setUdrecStreamType(String streamType) {
		if (this.udrecStreamType == null
				|| !this.udrecStreamType.equals(streamType)) {
			setSettingsChanged(true);
			this.udrecStreamType = streamType;
		}
	}

	public String getShortJGrabberStreamType() {
		StringTokenizer st = new StringTokenizer(this.getJgrabberStreamType());
		return st.nextToken();
	}

	public String getShortUdrecStreamType() {
		StringTokenizer st = new StringTokenizer(this.getUdrecStreamType());
		return st.nextToken().toLowerCase();
	}
	/**
	 * @return Returns the recordAllPids.
	 */
	public boolean isRecordAllPids() {
		return recordAllPids;
	}
	/**
	 * @param recordAllPids
	 *            The recordAllPids to set.
	 */
	public void setRecordAllPids(boolean recordPids) {
		if (this.recordAllPids != recordPids) {
			setSettingsChanged(true);
			this.recordAllPids = recordPids;
		}
	}
	

	
	

	/**
	 * @return Returns the recordTimeAfter.
	 */
	public String getRecordTimeAfter() {
		return recordTimeAfter;
	}
	/**
	 * @param recordTimeAfter
	 *            The recordTimeAfter to set.
	 */
	public void setRecordTimeAfter(String recordTimeAfter) {
		if (this.recordTimeAfter == null
				|| !this.recordTimeAfter.equals(recordTimeAfter)) {
			setSettingsChanged(true);
			this.recordTimeAfter = recordTimeAfter;
		}
	}
	/**
	 * @return Returns the recordTimeBefore.
	 */
	public String getRecordTimeBefore() {
		return recordTimeBefore;
	}
	/**
	 * @param recordTimeBefore
	 *            The recordTimeBefore to set.
	 */
	public void setRecordTimeBefore(String recordTimeBefore) {
		if (this.recordTimeBefore == null
				|| !this.recordTimeBefore.equals(recordTimeBefore)) {
			setSettingsChanged(true);
			this.recordTimeBefore = recordTimeBefore;
		}
	}
	/**
	 * @return Returns the ac3ReplaceStereo.
	 */
	public boolean isAc3ReplaceStereo() {
		return ac3ReplaceStereo;
	}
	/**
	 * @param ac3ReplaceStereo
	 *            The ac3ReplaceStereo to set.
	 */
	public void setAc3ReplaceStereo(boolean ac3ReplaceStereo) {
		if (this.ac3ReplaceStereo != ac3ReplaceStereo) {
			setSettingsChanged(true);
			this.ac3ReplaceStereo = ac3ReplaceStereo;
		}
	}
	/**
	 * @return Returns the udrecOptions.
	 */
	public String getUdrecOptions() {
		if (udrecOptions == null) {
			return "";
		}
		return udrecOptions;
	}
	/**
	 * @param udrecOptions
	 *            The udrecOptions to set.
	 */
	public void setUdrecOptions(String udrecOptions) {
		if (this.udrecOptions == null
				|| !this.udrecOptions.equals(udrecOptions)) {
			setSettingsChanged(true);
			this.udrecOptions = udrecOptions;
		}
	}
	
	/**
	 * @return Returns the projectXPath.
	 */
	public String getProjectXPath() {
		return projectXPath;
	}
	/**
	 * @param projectXPath The projectXPath to set.
	 */
	public void setProjectXPath(String projectXPath) {
		if (this.projectXPath == null || !this.projectXPath.equals(projectXPath)) {
			setSettingsChanged(true);
			this.projectXPath = projectXPath;
		}
	}
	public boolean isStoreEPG() {
		return storeEPG;
	}
	public void setStoreEPG(boolean storeEPG) {
		this.storeEPG = storeEPG;
	}
	public boolean isStoreLogAfterRecord() {
		return storeLogAfterRecord;
	}
	public void setStoreLogAfterRecord(boolean storeLogAfterRecord) {
		this.storeLogAfterRecord = storeLogAfterRecord;
	}
	
    /**
     * @return Returns the recordVtxt.
     */
    public boolean isRecordVtxt() {
        return recordVtxt;
    }
    /**
     * @param recordVtxt The recordVtxt to set.
     */
    public void setRecordVtxt(boolean recordVtxt) {
        if (this.recordVtxt != recordVtxt) {
      			setSettingsChanged(true);
      			this.recordVtxt = recordVtxt;
      		}
    }

}
