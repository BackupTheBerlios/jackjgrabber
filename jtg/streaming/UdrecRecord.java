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
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;

import model.BOPid;
import model.BOPids;
import model.BORecordArgs;
import service.SerExternalProcessHandler;
import service.SerHelper;
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
	    BOPids pids = recordArgs.getPids();
	    Object[] args = {
	            ControlMain.getSettingsPath().getUdrecPath(), 
	            boxIp, 
	            Integer.toString(spktBufNum), 
	            ControlMain.getSettingsRecord().getShortUdrecStreamType(),
	            new File(recordControl.getDirectory(), recordControl.getFileName()).getAbsolutePath(),
	            ControlMain.getSettingsRecord().getUdrecOptions()
	    };
	    
	    MessageFormat mf = new MessageFormat("{0} -host {1} -buf {2} -now -{3} -o {4} {5}");
	    cmd.append(mf.format(args));
	    
	    if (recordArgs.getPids().getVPid() != null) {
		    cmd.append(" -vp "+pids.getVPid().getNumber());
		}
		for (int i=0; i<recordArgs.getPids().getAPids().size(); i++){
		    cmd.append(" -ap "+((BOPid)pids.getAPids().get(i)).getNumber());
		}
		return cmd.toString();
	}
	
	public void start() {
	    SerExternalProcessHandler.startProcess("vlc",  this.getRequestString(), true);
	}	
	
	public void stop() {
	    if (run != null) {
	        PrintWriter out = new PrintWriter(run.getOutputStream());
	    		out.write("\n");
	    		out.flush();  
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
	    ArrayList udrecFiles = new ArrayList(files.length);
	    udrecFiles.add(SerHelper.getVideoFile(files));
	    SerHelper.fillArrayWithAudioFiles(files, udrecFiles);
	    return udrecFiles;
	}
}
