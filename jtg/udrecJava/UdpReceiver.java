package udrecJava;

import java.io.IOException;
import java.net.DatagramSocket;


import service.SerAlertDialog;
import control.ControlMain;

/**
 * @author Geist Alexander
 *
 */
public class UdpReceiver extends Thread {
	
	public boolean isStopped = false;
	public long packetCount = 0;
	Record record;
	
	public UdpReceiver(Record stream) {
		record = stream;
	}
	
	public void run() {
		try {
			DatagramSocket fromSocket = new DatagramSocket(31341);
			UdpPacket udpPacket = new UdpPacket();
			int curStatus = 0;
			
			do {			
				if (isStopped) break;
				fromSocket.receive( udpPacket.packet);
				curStatus = udpPacket.getPacketStatus();	
				
				udpPacket = record.streams.write(udpPacket);
				packetCount++;
			} while (curStatus != 2);		
		} catch (IOException e) {
		    SerAlertDialog.alertConnectionLost("TcpReceiver", ControlMain.getControl().getView());
		}
	}

}
