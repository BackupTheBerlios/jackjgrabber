package streaming;
/*
TcpReceiver.java by Geist Alexander 

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
import java.net.Socket;

import org.apache.log4j.Logger;

import control.ControlMain;

import service.SerAlertDialog;

/**
 * @author Geist Alexander
 *
 */
public class TcpReceiver extends Thread {

	Socket tcpSocket;
	Record record;
	public boolean isStopped = false;
	
	public TcpReceiver(Record stream) {
		record = stream;
	}
	
	public void run() {
		try {
			tcpSocket = record.tcpSocket;
			byte[] reply = new byte[1000];
			int len;
			
			while (!isStopped) {
				len = tcpSocket.getInputStream().read(reply);
				if (len == 0) {
					continue;
				}
				
				String[] replyString = new String(reply, 0, len).split("\n");
				for (int i=0; i<replyString.length; i++) {
					Logger.getLogger("TcpReceiver").info(replyString[i]);
					if (replyString[i].indexOf("EXIT") != -1) {
						closeSocket();
					}
				}
			}
		} catch (IOException e) {
			if (isStopped) {
				//Do nothing, Socket wurde regulaer geschlossen
			} else {
				SerAlertDialog.alertConnectionLost("TcpReceiver", ControlMain.getControl().getView());
				record.recordControl.stopRecord();
			}
		} 
	}
	
	public void closeSocket() {
		isStopped = true;
		if (tcpSocket.isBound()) {
			try {
				tcpSocket.close();
				Logger.getLogger("TcpReceiver").info("TcpReceiver stopped");
			} catch (IOException e) {
				Logger.getLogger("TcpReceiver").error("Unable to stop Tcp-Socket");
			}
		}
	}

}