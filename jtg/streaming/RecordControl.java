package streaming;
/*
RecordControl.java by Geist Alexander 

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
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import control.ControlMain;
import control.ControlProgramTab;
import control.ControlProjectXTab;
import model.BORecordArgs;


public class RecordControl extends Thread
{
	Record record;
	public boolean isRunning = true;
	ControlProgramTab controlProgramTab;
	BORecordArgs recordArgs;
	String fileName;
	File directory;
	public Date stopTime;

	public RecordControl(BORecordArgs args, ControlProgramTab control) {
		recordArgs = args;
		controlProgramTab = control;
		this.detectRecord();
	}
	
	private boolean detectRecord() {
	    if (controlProgramTab.isTvMode() && ControlMain.getSettings().getStreamingEngine()==0) {
		    record = new UdpRecord(recordArgs, this);
		    return true;
		}
		if (controlProgramTab.isTvMode() && ControlMain.getSettings().getStreamingEngine()==1) {
		    record = new UdrecRecord(recordArgs, this);
		    return true;
		}
		else {
		    record = new TcpRecord(recordArgs, this);
		    return true;
		}
	}
	/*
	 * Kontrolle der Stopzeit einer Sofortaufnahme
	 */
	public void run() {
		record.start();
		if (recordArgs.isQuickRecord()) { 
			long millis = new Date().getTime();
			stopTime = new Date(millis+1500000);
			controlProgramTab.setRecordStoptTime(stopTime);
			waitForStop();
		}
	}
	
	private void waitForStop() {
		boolean running = true;
		while (running) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {}
			if (new Date().getTime()-stopTime.getTime()>0) {
				running=false;
			}
		}
		stopRecord();
	}
	
	public void stopRecord() {
	    record.stop();
    	controlProgramTab.getMainView().getTabProgramm().stopRecordModus();
    	controlProgramTab.getMainView().setSystrayDefaultIcon();
    	if (ControlMain.getSettings().isStartPX() && record.getFiles()!=null && record.getFiles().length>0) {
    		this.startProjectX();
    		Logger.getLogger("RecordControl").info(ControlMain.getProperty("msg_startPX"));
    	}
    	isRunning = false;
	}
	
	public void startProjectX() {
		ControlProjectXTab control = new ControlProjectXTab(controlProgramTab.getMainView(), record.getFiles());
		control.initialize();
	}
	
	public String getFileName() {
		if (this.fileName==null) {
		    SimpleDateFormat f = new SimpleDateFormat("dd-MM-yy_HH-mm");
		    Date now = new Date();
		    String date = f.format(now);
		    
			BORecordArgs args = this.recordArgs;
			if (args.getEpgTitle() != null) {
				fileName = date+" "+args.getSenderName()+" "+args.getEpgTitle();   
			} else {
				fileName = date+" "+args.getSenderName();
			}
		}
		return fileName.replace(' ', '_');
	}
	
	public File getDirectory() {
	    if (directory == null) {
	        directory = new File(ControlMain.getSettings().getSavePath(), this.getFileName().replace(' ', '_'));
            directory.mkdir();
	    }
	    return directory;
	}
}
