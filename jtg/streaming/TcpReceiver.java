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
			while (!isStopped) {
				int len;
				len = tcpSocket.getInputStream().read(reply);
				if (len == 0) {
					continue;
				}
				String replyString = new String(reply, 0, len);
				Logger.getLogger("TcpReceiver").info(new String(replyString).replace("\n", ""));
				if (replyString.indexOf("EXIT") != -1) {
				    isStopped = true;
				}
			}
			tcpSocket.close();
			Logger.getLogger("Streaming").info("TcpReceiver stopped\n");
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("TcpReceiver", ControlMain.getControl().getView());
		} 
	}

}
