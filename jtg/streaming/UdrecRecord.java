package streaming;
/*
UdrecRecord.java by Geist Alexander 

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
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import model.BORecordArgs;
import service.SerErrorStreamReadThread;
import service.SerInputStreamReadThread;
import control.ControlMain;


public class UdrecRecord  extends Record {

	BORecordArgs recordArgs;
	int TcpPort = 31340;
	int udpPort = 31341;
	int spktBufNum = 16;
	RecordControl recordControl;
	String boxIp;
	boolean running = true;
	Process run;
	
	public UdrecRecord(BORecordArgs args, RecordControl control){
        recordControl = control;
        recordArgs = args;
        boxIp = ControlMain.getBoxIpOfActiveBox();
	}
	
	private String getRequestString() {
	    StringBuffer cmd = new StringBuffer();
	    Object[] args = {
	            ControlMain.getSettings().getUdrecPath(), 
	            boxIp, 
	            Integer.toString(spktBufNum), 
	            ControlMain.getSettings().getShortUdrecStreamType(),
	            new File(recordControl.getDirectory(), recordControl.getFileName()).getAbsolutePath(),
	            ControlMain.getSettings().getUdrecOptions()
	    };
	    
	    MessageFormat mf = new MessageFormat("{0} -host {1} -buf {2} -now -{3} -o {4} {5}");
	    cmd.append(mf.format(args));
	    
	    if (recordArgs.getVPid() != null) {
		    cmd.append(" -vp "+recordArgs.getVPid());
		}
		for (int i=0; i<recordArgs.getAPids().size(); i++){
		    String[] aPid = (String[])recordArgs.getAPids().get(i);
		    cmd.append(" -ap "+aPid[0]);
		}
		return cmd.toString();
	}
	
	public void start() {
	    try {
	    	String requestString = this.getRequestString();
	    	Logger.getLogger("UdrecRecord").info("to DBox: "+requestString);
            run = Runtime.getRuntime().exec(requestString);
            new SerInputStreamReadThread(true, run.getInputStream()).start();
            new SerErrorStreamReadThread(true, run.getErrorStream()).start();
        } catch (IOException e) {
            Logger.getLogger("UdrecRecord").error(ControlMain.getProperty("err_udrec")+e.getLocalizedMessage());
        }
	}	
	
	public void stop() {
		PrintWriter out = new PrintWriter(run.getOutputStream());
		out.write("\n");
		out.flush();
		try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
	/**
     * @return Returns the writeStream.
     */
    public DataWriteStream[] getWriteStream() {
        return null;
    }
	
    public ArrayList getFiles() {
    	File[] files = recordControl.getDirectory().listFiles();
    	ArrayList fullPathFiles = new ArrayList(files.length);
    	for (int i=0; i<files.length; i++) {
    	    fullPathFiles.add(files[i].getAbsolutePath());
    	}
    	return fullPathFiles;
    	//TODO nicht alle Files übergeben!! Nur Files mit identischem Standardnamen
    }
}
