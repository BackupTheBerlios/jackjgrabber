/*
TcpRecord.java by Geist Alexander 

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
package streaming;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

import service.SerAlertDialog;

import control.ControlMain;

import model.BORecordArgs;

public class TcpRecord extends Record{
    Socket socket;
	int port = 31338;
	byte[] buffer = new byte[0];
	String boxIp;
	String aPid;
	boolean running = true;
	
	RecordControl recordControl;
	BORecordArgs recordArgs;
	DataWriteStream[] writeStream = new DataWriteStream[1];
	
	public TcpRecord(BORecordArgs args, RecordControl control) {
	    recordControl = control;
        recordArgs = args;
        boxIp = ControlMain.getBoxIpOfActiveBox();
        String[] pid = (String[])recordArgs.getAPids().get(0);
        aPid = pid[0];
        Logger.getLogger("TcpRecord").info("Start record Pid "+aPid);
        String baseFileName = recordControl.getFileName();
        writeStream[0] = new DataWriteStream(recordControl);
	}
	
	public void start() {
		PrintWriter out;
		try {
			socket = new Socket(boxIp,port);
			Logger.getLogger("TcpRecord").info("TCP-Connection established");
			out = new PrintWriter(socket.getOutputStream());
			
			String requestString = "GET /0x"+aPid+" HTTP/1.1\r\n\r\n";
			out.write(requestString);
			out.flush();
			
			this.readStream(socket.getInputStream());
		} catch (IOException e) {
			if (!running) {
				//Do nothing, Socket wurde regulaer geschlossen
			} else {
				SerAlertDialog.alertConnectionLost("TcpReceiver", ControlMain.getControl().getView());
				recordControl.stopRecord();
			}
		} 
	}
	
	public void readStream(InputStream inStream) throws IOException {
		BufferedInputStream in = new  BufferedInputStream(inStream);
		Logger.getLogger("TcpRecord").info("Receiving data");
		in.read(new byte[42]);
		int length = 0;
		do {
			byte[] temp = new byte[65535];
			length = in.read(temp, 0, 65535);
			buffer = new byte[length];
			System.arraycopy(temp, 0, buffer, 0, length );
			
			writeStream[0].write(buffer);
		} while (running);
		recordControl.stopRecord();
	}
	
	public void stop() {
	    running = false;
	    try {
			socket.close();
			Logger.getLogger("TcpRecord").info("TcpRecord stopped");
			for (int i=0; i<writeStream.length; i++) {
                writeStream[i].stop();
            }
		} catch (IOException e) {
			Logger.getLogger("TcpRecord").error("Unable to stop Tcp-Socket");
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
