package model;
/*
BOTimerList.java by Geist Alexander 

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

import service.SerFormatter;


public class BOTimerList {
	
    private ArrayList recordTimerList = new ArrayList();
    private ArrayList systemTimerList = new ArrayList(); 
    
    public BOLocalTimer getFirstLocalBoxTimer() {
        if (this.getRecordTimerList().size()>0) {
            BOTimer timer=null;
            
            for (int i=1; i<this.getRecordTimerList().size(); i++) {
                if (timer==null) {
                    if (!((BOTimer)this.getRecordTimerList().get(i)).getLocalTimer().isLocal())
                    timer = (BOTimer)this.getRecordTimerList().get(i);
                } else {
                    BOTimer compareTimer = (BOTimer)this.getRecordTimerList().get(i);
                    if (!compareTimer.getLocalTimer().isLocal()) {
                        if (SerFormatter.compareDates(compareTimer.getUnformattedStartTime(), timer.getUnformattedStartTime())==-1) {
                            timer=compareTimer;
                        }   
                    }
                }
            }
            if (timer!=null) {
                return timer.getLocalTimer();   
            }
        }
        return null;
    }
    
    public BOTimer getFirstRecordTimer() {
        if (this.getRecordTimerList().size()>0) {
            BOTimer timer = (BOTimer)this.getRecordTimerList().get(0);
            for (int i=1; i<this.getRecordTimerList().size(); i++) {
                BOTimer compareTimer = (BOTimer)this.getRecordTimerList().get(i);
                if (SerFormatter.compareDates(compareTimer.getUnformattedStartTime(), timer.getUnformattedStartTime())==-1) {
                    timer=compareTimer;
                }
            }
            return timer;
        }
        return null;
    }
    /**
     * @return Returns the recordTimerList.
     */
    public ArrayList getRecordTimerList() {
        return recordTimerList;
    }
    /**
     * @param recordTimerList The recordTimerList to set.
     */
    public void setRecordTimerList(ArrayList localTimerList) {
        this.recordTimerList = localTimerList;
    }
    /**
     * @return Returns the systemTimerList.
     */
    public ArrayList getSystemTimerList() {
        return systemTimerList;
    }
    /**
     * @param systemTimerList The systemTimerList to set.
     */
    public void setSystemTimerList(ArrayList systemTimerList) {
        this.systemTimerList = systemTimerList;
    }
}
