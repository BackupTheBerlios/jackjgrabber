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
import java.util.ArrayList;
import java.util.Date;

import control.ControlMain;
import control.ControlProgramTab;
import control.ControlProjectXTab;
import model.BORecordArgs;


public class RecordControl extends Thread
{
	Record record;
	public boolean isRunning = true;
	ControlProgramTab controlProgramTab;
	ArrayList recordFiles = new ArrayList();
	BORecordArgs recordArgs;
	public Date stopTime;

	public RecordControl(BORecordArgs args, ControlProgramTab control) {
		recordArgs = args;
		controlProgramTab = control;
		record = new Record(args, this);
	}
	
	/*
	 * Kontrolle der Stopzeit einer Sofortaufnahme
	 */
	public void run() {
		record.start();
		if (recordArgs.getBouquetNr() != null) { //Timer-Record hat keine bouquetNr 
			long millis = new Date().getTime();
			stopTime = new Date(millis+1500000);
			controlProgramTab.setRecordStoptTime(stopTime);
			waitForStop();
		}
	}
	
	private void waitForStop() {
		boolean running = true;
		while (running) {
			if (new Date().getTime()-stopTime.getTime()>0) {
				running=false;
			}
		}
		stopRecord();
	}
	
	public void stopRecord() {
	    record.stop();
    	controlProgramTab.getMainView().getTabProgramm().stopRecordModus();
    	if (ControlMain.getSettings().isStartPX()) {
    		this.startProjectX();
    	}
    	isRunning = false;
	}
	
	public void startProjectX() {
		ControlProjectXTab control = new ControlProjectXTab(controlProgramTab.getMainView(), this.buildPXcommand());
		control.initialize();
	}
	
	private String[] buildPXcommand() {
		String[] args = new String[recordFiles.size()];
		for (int i=0; i<args.length; i++) {
			File file = (File)recordFiles.get(i);
			args[i] = file.getAbsolutePath();
		}
		return args;
	}
}
