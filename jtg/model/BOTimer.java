package model;
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
    private String eventId, eventType, eventRepeat, announceTime, alarmTime, stopTime, sender;
    /** Creates a new instance of BOTimer */
    public BOTimer() {
    }
   
    public BOTimer(String eventId, String eventType, String eventRepeat, String announceTime, 
                    String alarmTime, String stopTime, String sender){
        this.eventId      = eventId;
        this.eventType    = eventType;
        this.eventRepeat  = eventRepeat;
        this.announceTime = announceTime;
        this.alarmTime    = alarmTime;
        this.stopTime     = stopTime;
        this.sender         = sender;               
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
    
    public String getAlarmTime (){
        return this.alarmTime;
    }
    
    public void setAlarmTime(String alarmTime){
        this.alarmTime = alarmTime;
    }
    
    public String getStopTime(){
        return this.stopTime;
    }
    
    public void setStopTime(String stopTime){
        this.stopTime = stopTime;
    }
    
    public String getSender (){
        return this.sender;
    }
    
    public void setSender(String sender){
        this.sender = sender;
    }
 
    public void setAll(BOTimer botimer){       
        this.eventId   = botimer.getEventId();
        this.eventType   = botimer.getEventType();
        this.eventRepeat   = botimer.getEventRepeat();
        this.announceTime   = botimer.getAnnounceTime();
        this.alarmTime   = botimer.getAlarmTime();
        this.stopTime   = botimer.getStopTime();
        this.sender   = botimer.getSender();
    }
}
