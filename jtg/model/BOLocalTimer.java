package model;
/*
BOLocalTimer.java by Geist Alexander 

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
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import org.dom4j.Node;

import service.SerHelper;
import control.ControlMain;

public class BOLocalTimer {
	
	private boolean startPX;
	private boolean recordAllPids;
	private boolean ac3ReplaceStereo;
	private boolean stereoReplaceAc3;
	private boolean shutdownAfterRecord;
	private String description;
	public BOUdrecOptions udrecOptions;
	private String savePath;
	private String jgrabberStreamType; //PES, TS, ES
	private String udrecStreamType; //PES, TS
	private int streamingEngine; //0=JGrabber, 1=udrec
	private boolean storeEPG;
	private boolean storeLogAfterRecord;
	private boolean recordVtxt;
	private boolean stopPlaybackAtRecord;
	private String dirPattern;
	private String filePattern;
	private long startTime;
	private Node timerNode;
	
	private BOTimer mainTimer;
	
	public BOLocalTimer() {
	    
	}
	
	public BOLocalTimer(BOTimer mainTimer) {
		this.setMainTimer(mainTimer);
		mainTimer.setLocalTimer(this);
	}

	public static BOLocalTimer getDefaultLocalTimer(BOTimer mainTimer) {
		BOLocalTimer timer = new BOLocalTimer(mainTimer);
		timer.setAc3ReplaceStereo(ControlMain.getSettingsRecord().isAc3ReplaceStereo());
		timer.setDirPattern(ControlMain.getSettingsRecord().getDirPattern());
		timer.setFilePattern(ControlMain.getSettingsRecord().getFilePattern());
		timer.setJgrabberStreamType(ControlMain.getSettingsRecord().getJgrabberStreamType());
		timer.setRecordAllPids(ControlMain.getSettingsRecord().isRecordAllPids());
		timer.setRecordVtxt(ControlMain.getSettingsRecord().isRecordVtxt());
		timer.setShutdownAfterRecord(ControlMain.getSettingsRecord().isShutdownAfterRecord());
		timer.setStartPX(ControlMain.getSettingsRecord().isStartPX());
		timer.setStereoReplaceAc3(ControlMain.getSettingsRecord().isStereoReplaceAc3());
		timer.setStopPlaybackAtRecord(ControlMain.getSettingsRecord().isStopPlaybackAtRecord());
		timer.setStoreEPG(ControlMain.getSettingsRecord().isStoreEPG());
		timer.setStoreLogAfterRecord(ControlMain.getSettingsRecord().isStoreLogAfterRecord());
		timer.setStreamingEngine(ControlMain.getSettingsRecord().getStreamingEngine());
		timer.setUdrecOptions((BOUdrecOptions)SerHelper.serialClone(ControlMain.getSettingsRecord().getUdrecOptions()));
		timer.setUdrecStreamType(ControlMain.getSettingsRecord().getUdrecStreamType());
		timer.setSavePath(ControlMain.getSettingsPath().getSavePath());
		timer.setStartTime(mainTimer.getUnformattedStartTime().getTimeInMillis());
		timer.setDescription(mainTimer.getDescription());
		return timer;
	}

	public static BOLocalTimer getDefaultLocalTimer() {
		BOLocalTimer timer = new BOLocalTimer();
		timer.setAc3ReplaceStereo(ControlMain.getSettingsRecord().isAc3ReplaceStereo());
		timer.setDirPattern(ControlMain.getSettingsRecord().getDirPattern());
		timer.setFilePattern(ControlMain.getSettingsRecord().getFilePattern());
		timer.setJgrabberStreamType(ControlMain.getSettingsRecord().getJgrabberStreamType());
		timer.setRecordAllPids(ControlMain.getSettingsRecord().isRecordAllPids());
		timer.setRecordVtxt(ControlMain.getSettingsRecord().isRecordVtxt());
		timer.setShutdownAfterRecord(ControlMain.getSettingsRecord().isShutdownAfterRecord());
		timer.setStartPX(ControlMain.getSettingsRecord().isStartPX());
		timer.setStereoReplaceAc3(ControlMain.getSettingsRecord().isStereoReplaceAc3());
		timer.setStopPlaybackAtRecord(ControlMain.getSettingsRecord().isStopPlaybackAtRecord());
		timer.setStoreEPG(ControlMain.getSettingsRecord().isStoreEPG());
		timer.setStoreLogAfterRecord(ControlMain.getSettingsRecord().isStoreLogAfterRecord());
		timer.setStreamingEngine(ControlMain.getSettingsRecord().getStreamingEngine());
		timer.setUdrecOptions((BOUdrecOptions)SerHelper.serialClone(ControlMain.getSettingsRecord().getUdrecOptions()));
		timer.setUdrecStreamType(ControlMain.getSettingsRecord().getUdrecStreamType());
		timer.setSavePath(ControlMain.getSettingsPath().getSavePath());
		return timer;
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
	 * @return Returns the ac3ReplaceStereo.
	 */
	public boolean isAc3ReplaceStereo() {
		return ac3ReplaceStereo;
	}
	/**
	 * @param ac3ReplaceStereo The ac3ReplaceStereo to set.
	 */
	public void setAc3ReplaceStereo(boolean ac3ReplaceStereo) {
		this.ac3ReplaceStereo = ac3ReplaceStereo;
	}
	/**
	 * @return Returns the dirPattern.
	 */
	public String getDirPattern() {
		return dirPattern;
	}
	/**
	 * @param dirPattern The dirPattern to set.
	 */
	public void setDirPattern(String dirPattern) {
		this.dirPattern = dirPattern;
	}
	/**
	 * @return Returns the filePattern.
	 */
	public String getFilePattern() {
		return filePattern;
	}
	/**
	 * @param filePattern The filePattern to set.
	 */
	public void setFilePattern(String filePattern) {
		this.filePattern = filePattern;
	}
	/**
	 * @return Returns the jgrabberStreamType.
	 */
	public String getJgrabberStreamType() {
		return jgrabberStreamType;
	}
	/**
	 * @param jgrabberStreamType The jgrabberStreamType to set.
	 */
	public void setJgrabberStreamType(String jgrabberStreamType) {
		this.jgrabberStreamType = jgrabberStreamType;
	}
	/**
	 * @return Returns the recordAllPids.
	 */
	public boolean isRecordAllPids() {
		return recordAllPids;
	}
	/**
	 * @param recordAllPids The recordAllPids to set.
	 */
	public void setRecordAllPids(boolean recordAllPids) {
		this.recordAllPids = recordAllPids;
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
		this.recordVtxt = recordVtxt;
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
		this.savePath = savePath;
	}
	/**
	 * @return Returns the shutdownAfterRecord.
	 */
	public boolean isShutdownAfterRecord() {
		return shutdownAfterRecord;
	}
	/**
	 * @param shutdownAfterRecord The shutdownAfterRecord to set.
	 */
	public void setShutdownAfterRecord(boolean shutdownAfterRecord) {
		this.shutdownAfterRecord = shutdownAfterRecord;
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
		this.startPX = startPX;
	}
	/**
	 * @return Returns the stereoReplaceAc3.
	 */
	public boolean isStereoReplaceAc3() {
		return stereoReplaceAc3;
	}
	/**
	 * @param stereoReplaceAc3 The stereoReplaceAc3 to set.
	 */
	public void setStereoReplaceAc3(boolean stereoReplaceAc3) {
		this.stereoReplaceAc3 = stereoReplaceAc3;
	}
	/**
	 * @return Returns the stopPlaybackAtRecord.
	 */
	public boolean isStopPlaybackAtRecord() {
		return stopPlaybackAtRecord;
	}
	/**
	 * @param stopPlaybackAtRecord The stopPlaybackAtRecord to set.
	 */
	public void setStopPlaybackAtRecord(boolean stopPlaybackAtRecord) {
		this.stopPlaybackAtRecord = stopPlaybackAtRecord;
	}
	/**
	 * @return Returns the storeEPG.
	 */
	public boolean isStoreEPG() {
		return storeEPG;
	}
	/**
	 * @param storeEPG The storeEPG to set.
	 */
	public void setStoreEPG(boolean storeEPG) {
		this.storeEPG = storeEPG;
	}
	/**
	 * @return Returns the storeLogAfterRecord.
	 */
	public boolean isStoreLogAfterRecord() {
		return storeLogAfterRecord;
	}
	/**
	 * @param storeLogAfterRecord The storeLogAfterRecord to set.
	 */
	public void setStoreLogAfterRecord(boolean storeLogAfterRecord) {
		this.storeLogAfterRecord = storeLogAfterRecord;
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
	public void setStreamingEngine(int streamingEngine) {
		this.streamingEngine = streamingEngine;
	}
	/**
	 * @return Returns the udrecOptions.
	 */
	public BOUdrecOptions getUdrecOptions() {
		return udrecOptions;
	}
	/**
	 * @param udrecOptions The udrecOptions to set.
	 */
	public void setUdrecOptions(BOUdrecOptions udrecOptions) {
		this.udrecOptions = udrecOptions;
	}
	/**
	 * @return Returns the udrecStreamType.
	 */
	public String getUdrecStreamType() {
		return udrecStreamType;
	}
	/**
	 * @param udrecStreamType The udrecStreamType to set.
	 */
	public void setUdrecStreamType(String udrecStreamType) {
		this.udrecStreamType = udrecStreamType;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
	    if (description==null) {
	        description="";
	    }
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the mainTimer.
	 */
	public BOTimer getMainTimer() {
		return mainTimer;
	}
	/**
	 * @param mainTimer The mainTimer to set.
	 */
	public void setMainTimer(BOTimer mainTimer) {
		this.mainTimer = mainTimer;
	}
    /**
     * @return Returns the startTime.
     */
    public long getStartTime() {
        return startTime;
    }
    /**
     * @param startTime The startTime to set.
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public void setStartTime(GregorianCalendar startTime) {
        this.startTime = startTime.getTimeInMillis();
    }
    /**
     * @return Returns the timerNode.
     */
    public Node getTimerNode() {
        return timerNode;
    }
    /**
     * @param timerNode The timerNode to set.
     */
    public void setTimerNode(Node timerNode) {
        this.timerNode = timerNode;
    }
}
