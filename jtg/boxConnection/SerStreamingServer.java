package boxConnection;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

/**
 * @author Geist Alexander
 *
 */
public class SerStreamingServer extends Thread {
	
	int port = 4000;
	
	public SerStreamingServer(int port) {
		this.setPort(port);
	}
	
	public void run() {
		try {
			ServerSocket server = new ServerSocket(this.getPort());
			Socket socket = server.accept();
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String line;
			while((line=input.readLine())!=null) {
				System.out.print(line);
			}
			server.close();  //server restart
			this.run();
		} catch (IOException e) {
			Logger.getLogger("ControlMain").error("StreamingServer start failure");	
		}
	}
	/**
	 * @return Returns the port.
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @param port The port to set.
	 */
	public void setPort(int port) {
		this.port = port;
	}
}
