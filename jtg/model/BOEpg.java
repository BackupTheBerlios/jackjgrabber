/*
 * Created on 31.08.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package model;

import java.io.IOException;
import control.ControlMain;
import java.util.Date;

/**
 * @author alexg
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BOEpg {
	BOSender sender;
	String eventId, startTime, endTime, duration, title;
	Date startDate, endDate;
	BOEpgDetails epgGetail;
	
	public BOEpg(BOSender sender, String eventId, String startTime, Date startDate, 
			String endTime, Date endDate, String duration, String title) {		
                this.setSender(sender);
		this.setDuration(duration);
		this.setEndTime(endTime);
		this.setEndDate(endDate);
		this.setEventId(eventId);
		this.setStartTime(startTime);
		this.setTitle(title);
                this.setStartDate(startDate);
	}

	/**
	 * @return Returns the sender.
	 */
	public BOSender getSender() {
		return sender;
	}
	/**
	 * @param sender The sender to set.
	 */
	public void setSender(BOSender sender) {
		this.sender = sender;
	}
	/**
	 * @return Returns the duration.
	 */
	public String getDuration() {
		return duration;
	}
	/**
	 * @param duration The duration to set.
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}
	/**
	 * @return Returns the eventId.
	 */
	public String getEventId() {
		return eventId;
	}
	/**
	 * @param eventId The eventId to set.
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	/**
	 * @return Returns the startTime.
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime The startTime to set.
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
        /**
	 * @return Returns the startDate.
	 */
	public Date getStartdate() {
		return startDate;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return Returns the epgGetail.
	 */
	public BOEpgDetails getEpgGetail() {
		return epgGetail;
	}
	/**
	 * @param epgGetail The epgGetail to set.
	 */
	public void setEpgDetail(BOEpgDetails value) {
		epgGetail=value;
	}
	
	public BOEpgDetails readEpgDetails() throws IOException {
		BOEpgDetails detail = ControlMain.getBoxAccess().getEpgDetail(this);
		this.setEpgDetail(detail);
		return detail;
	}
	/**
	 * @return Returns the endTime.
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime The endTime to set.
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return Returns the endDate.
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
