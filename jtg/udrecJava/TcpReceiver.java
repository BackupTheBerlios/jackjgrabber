package udrecJava;

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
	Record readStream;
	
	public TcpReceiver(Record stream) {
		readStream = stream;
	}
	
	public void run() {
		try {
			tcpSocket = readStream.tcpSocket;
			byte[] reply = new byte[1000];
			boolean isExit = false;
			while (!isExit) {
				int len;
				len = tcpSocket.getInputStream().read(reply);
				if (len == 0) {
					continue;
				}
				String replyString = new String(reply, 0, len);
				Logger.getLogger("TcpReceiver").info(new String(replyString).replace("\n", ""));
				if (replyString.indexOf("EXIT") != -1) {
					isExit = true;
				}
			}
			tcpSocket.close();
			Logger.getLogger("Streaming").info("TcpReceiver stopped\n");
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("TcpReceiver", ControlMain.getControl().getView());
		} 
	}

}
