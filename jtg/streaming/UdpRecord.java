package streaming;
/*
UdpRecord.java by Geist Alexander 

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
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.MessageFormat;

import org.apache.log4j.Logger;

import service.SerAlertDialog;
import control.ControlMain;

import model.BORecordArgs;


public class UdpRecord  extends Record {

	BORecordArgs recordArgs;
	int tcpPort = 31340;
	int udpPort = 31341;
	int spktBufNum = 16;

	DataWriteStream[] writeStream;
	RecordControl recordControl;
	UdpReceiver udpReceiver;
	TcpReceiver tcpReceiver;
	Socket tcpSocket;
	PrintWriter outputStream;
	InputStream inputStream;
	String boxIp;
	public String avString;
	boolean running = true; 
	String[] dboxArgs;	
	
	public UdpRecord(BORecordArgs args, RecordControl control){
		try {
            recordControl = control;
            recordArgs = args;
            boxIp = ControlMain.getBoxIpOfActiveBox();
            tcpSocket = new Socket(boxIp,31340);
            udpReceiver = new UdpReceiver(this);
            tcpReceiver = new TcpReceiver(this);
            Logger.getLogger("UdpRecord").info(ControlMain.getProperty("msg_con"));
        } catch (IOException e) {
            SerAlertDialog.alertConnectionLost("UdpRecord", ControlMain.getControl().getView());
            control.controlProgramTab.stopRecord();
        }
	}
	
	public void start() {
	    if (sendRequest()) {
	        startRecord();
	    }
	}
	
	private boolean sendRequest() {
		try {
            outputStream = new PrintWriter(tcpSocket.getOutputStream());
            
            String requestString = this.getRequestString();
            if (running) { //vorzeitiger Stop m�glich
                outputStream.write(requestString+"\n");
                outputStream.flush();
            	Logger.getLogger("UdpRecord").info("to DBox: "+requestString);
            	return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            SerAlertDialog.alertConnectionLost("UdpRecord", ControlMain.getControl().getView());
            return false;
        }
	}
	
	private void startRecord() {
		try {
            boolean isPid = false;
            inputStream = tcpSocket.getInputStream();
            do {
            	byte[] buffer = new byte[1024];
            	
            	int length = inputStream.read(buffer);
            	String[] replyString = new String(buffer, 0, length).split("\n");
            	
            	for (int i=0; i<replyString.length; i++) {
            	    String s = replyString[i];
            		if (s == "") continue;
            		if (s == "EXIT") {
            			recordControl.controlProgramTab.stopRecord();
            		}
            		Logger.getLogger("UdpRecord").info("from DBox: "+s);
            		if (0 < this.parseDBoxReply(s, spktBufNum)) isPid = true;
            	}
            } while(!isPid);
            if (running) {
                outputStream.write("START\n");
                outputStream.flush();
            }
            udpReceiver.start();
            tcpReceiver.start();
        } catch (IOException e) {
            SerAlertDialog.alertConnectionLost("UdpRecord", ControlMain.getControl().getView());
        }
	}
	
	public String getRequestString() {
		StringBuffer cmd = new StringBuffer();
		String avString = new String();
		String pidString= new String();
		if (recordControl.controlProgramTab.isTvMode()) {
			cmd.append("VIDEO");
		} else {
			cmd.append("AUDIO");
		}
		if (ControlMain.getSettingsRecord().getShortJGrabberStreamType().equals("TS")) {
			cmd.append("TS");
		}
		Object[] args = {Integer.toString(udpPort), Integer.toString(spktBufNum)};
		MessageFormat form = new MessageFormat(" {0} {1} 0 1 ");
		cmd.append(form.format(args));
		
		if (recordArgs.getVPid() != null) {
		    avString+="v";
		    pidString+=recordArgs.getVPid();
		}
		for (int i=0; i<recordArgs.getAPids().size(); i++){
		    avString+="a";
		    pidString+= " "+((String[])recordArgs.getAPids().get(i))[0];
		}
		if (recordArgs.getVideotextPid() != null) {
		    avString+="a";
		    pidString+=" "+recordArgs.getVideotextPid();
		} 
		cmd.append(avString);
		cmd.append(" "+pidString);
//		if (recordArgs.getVPid() != null) {
//			cmd.append(" "+recordArgs.getVPid());
//		}
//		for (int i=0; i<recordArgs.getAPids().size(); i++){
//		    String[] aPid = (String[])recordArgs.getAPids().get(i);
//		    cmd.append(" "+aPid[0]);
//		}
		return cmd.toString();
	}
	
	public int parseDBoxReply(String dboxReply, int spktBufNum)
	{
		dboxArgs = dboxReply.split(" ");

		if (dboxArgs[0].equals("INFO:")) return 0;
		if (!dboxArgs[0].equals("PID")) return -1;  // "EXIT" ist moeglich
		String baseFileName = recordControl.getFileName();
		if (dboxArgs.length < 4) return -2;
	  	avString = dboxArgs[1];
		int pidNum = Integer.parseInt(dboxArgs[2]);
		if (pidNum + 3 > dboxArgs.length) return -3;

		if (ControlMain.getSettingsRecord().getShortJGrabberStreamType().equals("TS")) {
			writeStream = new DataWriteStream[1];
			writeStream[0] = new DataWriteStream('t', 0, recordControl);
		}  else {
			writeStream = new DataWriteStream[pidNum];
            for (int i = 0; i < pidNum; i++) {
            	writeStream[i] = new DataWriteStream(avString.charAt(i), i, recordControl);
            }
		}
		return pidNum;
	}
	
	public void stop() {
	    running = false;
	    try {
	        udpReceiver.closeSocket();
	        tcpReceiver.closeSocket();
            for (int i=0; i<writeStream.length; i++) {
                writeStream[i].stop();
            }
        } catch (NullPointerException e) {
            //doNothing Aufnahmeabbruch vor dem Start
        }   
	}
    /**
     * @return Returns the writeStream.
     */
    public DataWriteStream[] getWriteStream() {
        return writeStream;
    }
}
