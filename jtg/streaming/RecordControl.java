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
import control.ControlProgramTab;
import model.BORecordArgs;


/**
 * @author Geist Alexander
 *
 */
public class RecordControl extends Thread
{
	Record record;
	public boolean isRunning = true;
	ControlProgramTab controlProgramTab;

	public RecordControl(BORecordArgs args, ControlProgramTab control) {
		controlProgramTab = control;
		record = new Record(args, this);
	}
	
	public void run() {
	   record.start();
	}
	
	public void stopRecord() {
	    record.stop();
    	controlProgramTab.getMainView().getTabProgramm().stopRecordModus();
    	isRunning = false;
	}
}
