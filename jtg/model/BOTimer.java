package model;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import service.SerFormatter;

/**
 * @author  Alexander Geist
 */
public class BOTimer extends java.lang.Object{
    private String eventId, eventType, eventRepeat, announceTime, senderName, description;;
    private GregorianCalendar unformattedStartTime, unformattedStopTime;
    
    public String getEventId (){
        return this.eventId;
    }
    
    public void setEventId(String eventId){
        this.eventId = eventId;
    }
    
    public String getEventType(){	
    	return eventType;
    }
    
    public void setEventType(String eventType){
        this.eventType = eventType;
    }
    
    public String getEventRepeat (){
        return this.eventRepeat;
    }
    
    public void setEventRepeat(String eventRepeat){
        this.eventRepeat = eventRepeat;
    }
    
    public String getAnnounceTime (){
        return this.announceTime;
    }
    
    public void setAnnounceTime(String announceTime){
        this.announceTime = announceTime;
    }
    
    public String getStartTime (){
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    	return sdf.format(this.getUnformattedStartTime().getTime());
    }
    
    public String getDateWithoutYear(GregorianCalendar cal) {
		String day = Integer.toString(this.getUnformattedStartTime().get(Calendar.DAY_OF_MONTH));
		String month = Integer.toString(this.getUnformattedStartTime().get(Calendar.MONTH));
		return day+"."+month;
    }
    
    public String getStopTime(){
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    	return sdf.format(this.getUnformattedStopTime().getTime());
    }
    
    public String getSenderName (){
        return this.senderName;
    }
    
    public void setSenderName(String sender){
        this.senderName = sender;
    }
    /**
	 * @return Returns the unformattedStartTime.
	 */
	public GregorianCalendar getUnformattedStartTime() {
		return unformattedStartTime;
	}
	/**
	 * @param unformattedStartTime The unformattedStartTime to set.
	 */
	public void setUnformattedStartTime(GregorianCalendar startDate) {
		this.unformattedStartTime = startDate;
	}
     
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the unformattedStopTime.
	 */
	public GregorianCalendar getUnformattedStopTime() {
		return unformattedStopTime;
	}
	/**
	 * @param unformattedStopTime The unformattedStopTime to set.
	 */
	public void setUnformattedStopTime(GregorianCalendar endDate) {
		this.unformattedStopTime = endDate;
	}
	/**
	 * @return Returns the startDate.
	 */
	public String getStartDate() {
		String day = Integer.toString(this.getUnformattedStartTime().get(Calendar.DAY_OF_MONTH));
		String month = Integer.toString(this.getUnformattedStartTime().get(Calendar.MONTH));
		String year = Integer.toString(this.getUnformattedStartTime().get(Calendar.YEAR));
		return day+"."+month+"."+year;
	}
}
