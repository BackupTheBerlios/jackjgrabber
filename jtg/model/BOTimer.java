package model;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * @author  Alexander Geist
 */
public class BOTimer extends java.lang.Object{
    private String channelId, timerNumber, modifiedId, eventTypeId, eventRepeatId, announceTime, senderName, description;
    private GregorianCalendar unformattedStartTime, unformattedStopTime;

    public String getTimerNumber (){
        return this.timerNumber;
    }

    public void setTimerNumber(String eventId){
        this.timerNumber = eventId;
    }

    public String getEventTypeId(){
    	return eventTypeId;
    }

    public void setEventTypeId(String eventType){
        this.eventTypeId = eventType;
    }

    public String getEventRepeatId (){
        return this.eventRepeatId;
    }

    public void setEventRepeatId(String eventRepeat){
        this.eventRepeatId = eventRepeat;
    }

    public String getAnnounceTime (){
        return this.announceTime;
    }

    public void setAnnounceTime(String announceTime){
        this.announceTime = announceTime;
    }

    public String getStartTime (){
    	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy   HH:mm");
    	return sdf.format(this.getUnformattedStartTime().getTime());
    }
    
    public String getShortStartTime (){
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    	return sdf.format(this.getUnformattedStartTime().getTime());
    }

    public String getStopTime(){
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    	return sdf.format(this.getUnformattedStopTime().getTime());
    }


	public String getInfo() {
		return this.getStartTime()+" "+this.getSenderName()+" "+this.getDescription();
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
	 * @param time in milliseconds
	 * Bei Setzen eines neuen Start-Datum, muss das Stop-Datum angepasst werden
	 */
	public void setUnformattedStartTime(long startMillis) {
		this.getUnformattedStartTime().setTimeInMillis(startMillis);
		
		int startDay = this.getUnformattedStartTime().get(Calendar.DAY_OF_MONTH);
		this.getUnformattedStopTime().set(Calendar.DAY_OF_MONTH, startDay);
		
		long stopMillis = this.getUnformattedStopTime().getTimeInMillis();
		if ((stopMillis-startMillis)<0) {
			this.getUnformattedStopTime().set(Calendar.DAY_OF_MONTH, startDay+1);
		}
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
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
		return sdf.format(this.getUnformattedStartTime().getTime());
	}
	/**
	 * @return Returns the modifiedId.
	 */
	public String getModifiedId() {
		return modifiedId;
	}
	/**
	 * @param modifiedId The modifiedId to set.
	 */
	public void setModifiedId(String timerId) {
		this.modifiedId = timerId;
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
}