package streaming;
/*
LocalTimerRecordDaemon.java by Geist Alexander 

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
import java.io.IOException;
import java.util.GregorianCalendar;

import model.BOLocalTimer;
import model.BOPids;
import model.BORecordArgs;
import model.BOTimer;

import org.apache.log4j.Logger;

import control.ControlMain;
import control.ControlProgramTab;


public class LocalTimerRecordDaemon extends Thread {
    
    boolean running = false;
    
    public void run() {
        while (!running) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {}
            BOTimer timer = ControlMain.getBoxAccess().detectNextLocalRecordTimer(false);
            
            if (timer!=null) {
                long now = new GregorianCalendar().getTimeInMillis();
                if (now>timer.getLocalTimer().getStartTime() && now<timer.getLocalTimer().getStopTime()) {
                    Logger.getLogger("LocalTimerRecordDaemon").info(ControlMain.getProperty("msg_startRecord")+" "+timer.getLocalTimer().getDescription());
                    this.startRecord(timer.getLocalTimer());
                    running = true;
                    this.waitForStop(timer.getLocalTimer());
                }
            }
        }
    }
    
    private void waitForStop(BOLocalTimer timer) {
        while (running) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            long now = new GregorianCalendar().getTimeInMillis();
            if (now>timer.getStopTime()) {
                ControlMain.getControl().getView().getTabProgramm().getControl().stopRecord();
                ControlMain.getBoxAccess().getTimerList(true);
                running=false;
                ControlMain.getBoxAccess().detectNextLocalRecordTimer(true);
                this.run();
            }
        }
    }
    
    private void startRecord(BOLocalTimer timer) {
        try {
            //umschalten auf den gewuenschten Sender
            ControlMain.getBoxAccess().zapTo(timer.getMainTimer().getChannelId());
            //ermitteln der Pids
            BOPids pids = ControlMain.getBoxAccess().getPids();
            //starte Aufnahme
            ControlProgramTab ctrl = ControlMain.getControl().getView().getTabProgramm().getControl();
            BORecordArgs args = new BORecordArgs(pids, timer, false);
            ctrl.startRecord(args);
        } catch (IOException e) {

        }
    }
    
}
