package streaming;
/*
UdpReceiver.java by Geist Alexander 

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
import java.net.DatagramSocket;

import org.apache.log4j.Logger;


import service.SerAlertDialog;
import control.ControlMain;

/**
 * @author Geist Alexander
 *
 */
public class UdpReceiver extends Thread {
	
	public boolean isStopped = false;
	public long packetCount = 0;
	DatagramSocket udpSocket;
	Record record; 

	
	public UdpReceiver(Record stream) {
		record = stream;
	}
	
	public void run() {
		try {
			udpSocket = new DatagramSocket(31341);
			UdpPacket udpPacket = new UdpPacket();
			int curStatus = 0;
			
			do {			
				if (isStopped) break;
				udpSocket.receive( udpPacket.packet);
				curStatus = udpPacket.getPacketStatus();					
				record.writeStream[udpPacket.getStream()].write(udpPacket);
				packetCount++;
			} while (curStatus != 2 && !isStopped);		
		} catch (IOException e) {
			if (isStopped) {
				//Do nothing, regulaerer Stop
			} else {
				SerAlertDialog.alertConnectionLost("UdpReceiver", ControlMain.getControl().getView());
			    record.recordControl.stopRecord();
			}
		}
	}
	
	public void closeSocket() {
		isStopped = true;
		if (udpSocket.isBound()) {
			isStopped=true;
			udpSocket.close();
			Logger.getLogger("UdpReceiver").info("UdpReceiver stopped");
		}
	}
}
