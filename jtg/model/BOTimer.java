package model;

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
    private String eventId, eventType, eventRepeat, announceTime, startTime, stopTime, channelId;
    Date startDate, endDate;
    /** Creates a new instance of BOTimer */
    public BOTimer() {
    }
   
    public BOTimer(String eventId, String eventType, String eventRepeat, String announceTime, 
                    String startTime, String stopTime, String sender,Date startDate ){
        this.setEventId(eventId);
        this.setEventType(eventType);
        this.setEventRepeat(eventRepeat);
        this.setAnnounceTime(announceTime);
        this.setStartTime(startTime);
        this.setStopTime(stopTime);
        this.setChannelId(sender);         
        this.setStartDate(startDate);
    }
    
    public String getEventId (){
        return this.eventId;
    }
    
    public void setEventId(String eventId){
        this.eventId = eventId;
    }
    
    public String getEventType(){
        return this.eventType;
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
    
    public String getChannelId (){
        return this.channelId;
    }
    
    public void setChannelId(String sender){
        this.channelId = sender;
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
     
}
