package model;

import java.util.ArrayList;

/**
 * @author Geist Alexander
 *
 */
public class BORecordArgs {

	String senderName;
	String epgTitle;
	String channelId;
	String epgInfo1;
	String epgInfo2;
	String epgId;
	String vPid;
	String mode;
	ArrayList aPids;
	String VideotextPid;
	String command;
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
	public String getChannelId() {
		return channelId;
	}
	/**
	 * @param channelId The channelId to set.
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
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
}
