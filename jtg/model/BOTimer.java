package model;

import java.util.ArrayList;
import java.util.Date;

/*
 * BOTimer.java
 *
 * Created on 11. September 2004, 11:33
 */

/**
 *
 * @author  ralix
 */
public class BOTimer extends java.lang.Object{
    private String eventId, eventType, eventRepeat, announceTime, startTime, stopTime, senderName, description;
    Date startDate, endDate;
    /** Creates a new instance of BOTimer */
    public BOTimer() {
    }
   
    public BOTimer(String eventId, String eventType, String eventRepeat, String announceTime, 
                    String startTime, String stopTime, String sender,Date startDate, String description ){
        this.setEventId(eventId);
        this.setEventType(eventType);
        this.setEventRepeat(eventRepeat);
        this.setAnnounceTime(announceTime);
        this.setStartTime(startTime);
        this.setStopTime(stopTime);
        this.setSenderName(sender);         
        this.setStartDate(startDate);
    }
    
    public String getEventId (){
        return this.eventId;
    }
    
    public void setEventId(String eventId){
        this.eventId = eventId;
    }
    
    public String getEventType(){	
    	return eventType;
    }
    
    public String getFormattedEventType() {
    	switch(Integer.parseInt(eventType)) {
			case 1: return "SHUTDOWN";
			case 2: return "NEXTPROGRAM";
			case 3: return "ZAPTO";
			case 4: return "STANDBY";
			case 5: return "RECORD";
			case 6: return "REMIND";
			case 7: return "SLEEPTIMER";										
    	}
    	return new String();
    }
    
    public void setEventType(String eventType){
        this.eventType = eventType;
    }
    
    public String getEventRepeat (){
        return this.eventRepeat;
    }
    
    public String getFormattedEventRepeat(){
    	int repeatNumber = Integer.parseInt(eventRepeat);
    	switch(repeatNumber) {
			case 0:
			return "einmal";
			case 1:
			return "täglich";
			case 2:
			return "wöchentlich";
			case 3:
			return "2-wöchentlich";
			case 4:
			return "4-wöchentlich";
			case 5:
			return "monatlich";
    	}
    	if (repeatNumber >5) {
    		return "Wochentage"; 
    	}
    	return new String();
    }
    
    public void setFormattedEventRepeat(String repeatString){
		if (repeatString.equals("einmal")) {
			eventRepeat = "0";
		}
		if (repeatString.equals("täglich")){
			eventRepeat = "1";
		}
		if (repeatString.equals("wöchentlich")){
			eventRepeat = "2";
		}
		if (repeatString.equals("2-wöchentlich")){
			eventRepeat = "3";
		}
		if (repeatString.equals("4-wöchentlich")){
			eventRepeat = "4";
		}
		if (repeatString.equals("monatlich")){
			eventRepeat = "5";
		}
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
        return this.startTime;
    }
    
    public void setStartTime(String startTime){
        this.startTime = startTime;
    }
    
    public String getStopTime(){
        return this.stopTime;
    }
    
    public void setStopTime(String stopTime){
        this.stopTime = stopTime;
    }
    
    public String getSenderName (){
        return this.senderName;
    }
    
    public void setSenderName(String sender){
        this.senderName = sender;
    }
    /**
	 * @return Returns the startDate.
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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
