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
import java.util.ArrayList;
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
	public Date stopTime;

	public RecordControl(BORecordArgs args, ControlProgramTab control) {
		recordArgs = args;
		controlProgramTab = control;
		if (control.isTvMode()) {
		    record = new UdpRecord(args, this);
		} else {
		    record = new TcpRecord(args, this);
		}
		
	}
	
	/*
	 * Kontrolle der Stopzeit einer Sofortaufnahme
	 */
	public void run() {
		record.start();
		if (recordArgs.getBouquetNr() != null) { //Timer-UdpRecord hat keine bouquetNr 
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
    	if (ControlMain.getSettings().isStartPX() && record.getWriteStream()!=null) {
    		this.startProjectX();
    		Logger.getLogger("RecordControl").info("Start ProjectX for demuxing");
    	}
    	isRunning = false;
	}
	
	public void startProjectX() {
		ControlProjectXTab control = new ControlProjectXTab(controlProgramTab.getMainView(), this.buildPXcommand());
		control.initialize();
	}
	
	private String[] buildPXcommand() {
        ArrayList allFiles = new ArrayList();
        int streamCount = record.getWriteStream().length;
        for (int i=0; i<streamCount; i++) {
            ArrayList fileList = record.getWriteStream()[i].fileList;
            for (int i2=0; i2<fileList.size(); i2++) {
                File file = (File)fileList.get(i2);
                allFiles.add(file);
            }
        }
        String[] args = new String[allFiles.size()];
        for (int i=0; i<args.length; i++) {
            File file = (File)allFiles.get(i);
            args[i] = file.getAbsolutePath();
        }
        return args;
	}
	
	public String getFileName() {
	    SimpleDateFormat f = new SimpleDateFormat("dd-MM-yy_HH-mm");
	    Date now = new Date();
	    String date = f.format(now);
	    String name;
	    
		BORecordArgs args = this.recordArgs;
		if (args.getEpgTitle() != null) {
		    name = date+" "+args.getSenderName()+" "+args.getEpgTitle();   
		} else {
		    name = date+" "+args.getSenderName();
		}	
		return name;
	}
}
