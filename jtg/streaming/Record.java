package streaming;
/*
Record.java by Geist Alexander 

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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import service.SerAlertDialog;
import control.ControlMain;

import model.BORecordArgs;

/**
 * @author Alexander Geist
 *
 */
public class Record {

	BORecordArgs recordArgs;
	int TcpPort = 31340;
	int udpPort = 31341;
	int spktBufNum = 16;

	PESWriteStream[] writeStream;
	RecordControl recordControl;
	UdpReceiver udpReceiver;
	TcpReceiver tcpReceiver;
	Socket tcpSocket;
	String boxIp;
	public String avString;
	String[] dboxArgs;	
	
	public Record(BORecordArgs args, RecordControl control) {
		recordControl = control;
	    recordArgs = args;
	    boxIp = ControlMain.getBoxIpOfActiveBox();
	}
	
	public void start() {
		try {
			tcpSocket = new Socket(boxIp,31340);
			
			PrintWriter out = new PrintWriter(tcpSocket.getOutputStream());	
			String requestString = this.getRequestString();
			out.write(requestString);
			out.flush();
			Logger.getLogger("Record").info("to DBox: "+requestString.replace("\n", ""));

			boolean isPid = false;
			InputStream input = tcpSocket.getInputStream();
			do {
				byte[] buffer = new byte[1024];
				
				int length = input.read(buffer);
				String[] replyString = new String(buffer, 0, length).split("\n");
				
				for (int i=0; i<replyString.length; i++) {
				    String s = replyString[i];
					if (s == "") continue;
					if (s == "EXIT") {
						recordControl.stopRecord();
					}
					Logger.getLogger("Record").info("from DBox: "+s);
					if (0 < this.parseDBoxReply(s, spktBufNum)) isPid = true;
				}
			} while(!isPid);
			
			out.write("START\n");
			out.flush();

			udpReceiver = new UdpReceiver(this);
			tcpReceiver = new TcpReceiver(this);
			udpReceiver.start();
			tcpReceiver.start();
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("Record", ControlMain.getControl().getView());
			recordControl.stopRecord();
		}
	}
	
	public String getRequestString() {
		StringBuffer cmd = new StringBuffer();
		cmd.append("VIDEO");
		if (ControlMain.getSettings().getStreamType().equals("TS")) {
			cmd.append("TS");
		}
		cmd.append(" "+udpPort+" ");
		cmd.append(spktBufNum+" ");
		if (recordArgs.getBouquetNr() == null) { //Timer-Record hat keine bouquetNr
		    cmd.append("0 ");
			cmd.append("1 ");
			
			if (recordArgs.getVPid() > 0) {
			    cmd.append("v");
			}
			for (int i=0; i<recordArgs.getAPids().size(); i++){
			    cmd.append("a");
			}
			if (recordArgs.getVPid() > 0) {
			    cmd.append(" "+Integer.toHexString(recordArgs.getVPid()));
			}
			for (int i=0; i<recordArgs.getAPids().size(); i++){
			    Object test = recordArgs.getAPids().get(i);
			    String[] aPid = (String[])recordArgs.getAPids().get(i);
			    String hexPid = Integer.toHexString(Integer.parseInt(aPid[0]));
			    cmd.append(" "+hexPid);
			}
		} else { 
		    cmd.append(recordArgs.getBouquetNr()+" ");
			cmd.append(recordArgs.getEventId()+" ");
		}

		cmd.append("\n");
		return cmd.toString();
	}
	
	public int parseDBoxReply(String dboxReply, int spktBufNum)
	{
		dboxArgs = dboxReply.split(" ");

		if (dboxArgs[0].equals("INFO:")) return 0;
		if (!dboxArgs[0].equals("PID")) return -1;  // "EXIT" ist moeglich
		String baseFileName = this.getFileName();
		if (dboxArgs.length < 4) return -2;
	  	avString = dboxArgs[1];
		int pidNum = Integer.parseInt(dboxArgs[2]);
		if (pidNum + 3 > dboxArgs.length) return -3;

		try {
			if (ControlMain.getSettings().getStreamType().equals("TS")) {
				writeStream = new PESWriteStream[1];
				writeStream[0] = new PESWriteStream('t', 0, baseFileName, recordControl);
			}  else {
				writeStream = new PESWriteStream[pidNum];
	            for (int i = 0; i < pidNum; i++) {
	            	writeStream[i] = new PESWriteStream(avString.charAt(i), i, baseFileName, recordControl);
	            }
			}
        } catch (FileNotFoundException e) {
            Logger.getLogger("Record").error("Unable to create Output-Files");
            recordControl.stopRecord();
        }
		return pidNum;
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
	
	public void stop()
	{
	    udpReceiver.closeSocket();
	    tcpReceiver.closeSocket();
	    for (int i=0; i<writeStream.length; i++) {
	        writeStream[i].stop();
	    }
	}
}
