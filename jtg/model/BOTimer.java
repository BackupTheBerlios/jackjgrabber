package model;
/*
BOTimer.java by Geist Alexander 

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

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import control.ControlNeutrinoTimerTab;

public class BOTimer extends java.lang.Object{
    public String channelId, timerNumber, modifiedId, eventTypeId, eventRepeatId, announceTime, senderName, description;
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
		this.setModifiedId("modify");
        this.eventTypeId = eventType;
    }

    public String getEventRepeatId (){
        return this.eventRepeatId;
    }

    public void setEventRepeatId(String eventRepeat){
    	this.setModifiedId("modify");
        this.eventRepeatId = eventRepeat;
    }

    public String getAnnounceTime (){
        return this.announceTime;
    }

    public void setAnnounceTime(String announceTime){
        this.announceTime = announceTime;
    }

    public String getStartTime (){
    	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy  HH:mm");
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
		String outputString;
		if (this.getSenderName() == null) {
			int type = Integer.parseInt(this.getEventTypeId())-1;
			outputString = ControlNeutrinoTimerTab.timerType[type];
		} else {
			outputString = this.getSenderName();
		}
		Object[] args = {this.getStartTime(), this.getModifiedId(), outputString, this.getDescription()};
		MessageFormat form = new MessageFormat("-{1}-  {0} -{2}- {3}");
		return form.format(args);
	}

    public String getSenderName (){
        return this.senderName;
    }

    public void setSenderName(String sender){
    	this.setModifiedId("modify");
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
		this.setModifiedId("modify");
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
		if (description==null) {
			return "";
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
	 * Beu neuen Timern keine modified-Id setzen!!
	 */
	public void setModifiedId(String id) {
		if (modifiedId == null || modifiedId.equals("modify")) {
			this.modifiedId = id;
		}
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