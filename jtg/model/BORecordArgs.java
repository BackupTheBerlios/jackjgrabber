package model;
/*
BORecordArgs.java by Geist Alexander 

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

import control.ControlMain;

public class BORecordArgs {

	String senderName;
	String epgTitle;
	String bouquetNr;
	String eventId;
	String epgInfo1;
	String epgInfo2;
	String epgId;
	String vPid;
	String mode;
	ArrayList aPids;
	String VideotextPid;
	String command;
	boolean quickRecord=true;
	/**
	 * @return Returns the aPids.
	 */
	public ArrayList getAPids() {
		return aPids;
	}
	/**
	 * @param pids The aPids to set.
	 */
	public void setAPids(ArrayList pids) {
		aPids = pids;
	}
	/**
	 * @return Returns the channelId.
	 */
	public String getEventId() {
		return eventId;
	}
	/**
	 * @param channelId The channelId to set.
	 */
	public void setEventId(String channelId) {
		this.eventId = channelId;
	}
	/**
	 * @return Returns the epgId.
	 */
	public String getEpgId() {
		return epgId;
	}
	/**
	 * @param epgId The epgId to set.
	 */
	public void setEpgId(String epgId) {
		this.epgId = epgId;
	}
	/**
	 * @return Returns the epgInfo1.
	 */
	public String getEpgInfo1() {
		return epgInfo1;
	}
	/**
	 * @param epgInfo1 The epgInfo1 to set.
	 */
	public void setEpgInfo1(String epgInfo1) {
		this.epgInfo1 = epgInfo1;
	}
	/**
	 * @return Returns the epgInfo2.
	 */
	public String getEpgInfo2() {
		return epgInfo2;
	}
	/**
	 * @param epgInfo2 The epgInfo2 to set.
	 */
	public void setEpgInfo2(String epgInfo2) {
		this.epgInfo2 = epgInfo2;
	}
	/**
	 * @return Returns the epgTitle.
	 */
	public String getEpgTitle() {
		return epgTitle;
	}
	/**
	 * @param epgTitle The epgTitle to set.
	 */
	public void setEpgTitle(String epgTitle) {
		this.epgTitle = epgTitle;
	}
	/**
	 * @return Returns the mode.
	 */
	public String getMode() {
		return mode;
	}
	/**
	 * @param mode The mode to set.
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	/**
	 * @return Returns the senderName.
	 */
	public String getSenderName() {
		return senderName;
	}
	/**
	 * @param senderName The senderName to set.
	 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	/**
	 * @return Returns the videotextPid.
	 */
	public String getVideotextPid() {
		return VideotextPid;
	}
	/**
	 * @param videotextPid The videotextPid to set.
	 */
	public void setVideotextPid(String videotextPid) {
		VideotextPid = videotextPid;
	}
	/**
	 * @return Returns the vPid.
	 */
	public String getVPid() {
		return vPid;
	}
	/**
	 * @param pid The vPid to set.
	 */
	public void setVPid(String pid) {
		vPid = pid;
	}
	/**
	 * @return Returns the command.
	 */
	public String getCommand() {
		return command;
	}
	/**
	 * @param command The command to set.
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	/**
	 * @return Returns the bouquetNr.
	 */
	public String getBouquetNr() {
		return bouquetNr;
	}
	/**
	 * @param bouquetNr The bouquetNr to set.
	 */
	public void setBouquetNr(String bouquetNr) {
		this.bouquetNr = bouquetNr;
	}
    /**
     * @return Returns the quickRecord.
     */
    public boolean isQuickRecord() {
        return quickRecord;
    }
    /**
     * @param quickRecord The quickRecord to set.
     */
    public void setQuickRecord(boolean quickRecord) {
        this.quickRecord = quickRecord;
    }
    /*
     * Checken ob ein AC3-Stream den Streostrem ersetzen muss
     */
    public void checkSettings() {
        if (ControlMain.getSettings().isAc3ReplaceStereo()) {
            for (int i=this.getAPids().size()-1; 0<=i; i--) {
                String[] aPid = (String[])this.getAPids().get(i);
                String desc = aPid[1];
                if (desc.indexOf("AC3")>-1) {
                    ArrayList newList = new ArrayList();
                    newList.add(aPid);
                    this.setAPids(newList);
                    break;
                }
            }    
        }
    }
}
