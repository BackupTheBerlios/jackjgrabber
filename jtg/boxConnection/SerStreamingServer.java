package boxConnection;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.BORecordArgs;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import service.SerXMLConverter;
import streaming.StreamThread;

/**
 * @author Geist Alexander
 *
 */
public class SerStreamingServer extends Thread {
	
	int port = 4000;
	ServerSocket server;
	StreamThread recordThread;
	
	public SerStreamingServer(int port) {
		this.setPort(port);
	}
	
	public void run() {
		try {
			server = new ServerSocket(this.getPort());
			Socket socket = server.accept();
			this.record(socket);
		} catch (IOException e) {
			Logger.getLogger("SerStreamingServer").error("StreamingServer start failed");	
		} catch (DocumentException e) {
			Logger.getLogger("SerStreamingServer").error("Recording failed");	
		}
	}
	
	public void record(Socket socket) throws IOException, DocumentException {		
		SAXReader reader = new SAXReader();
		Document document = reader.read(socket.getInputStream());
		BORecordArgs recordArgs = SerXMLConverter.parseRecordDocument(document);
		
		if (recordArgs.getCommand().equals("stop") && this.getRecordThread() != null) {
			this.getRecordThread().setRunFlag(false);
		}
		if (recordArgs.getCommand().equals("record")) {
			this.setRecordThread(new StreamThread(recordArgs));
			this.getRecordThread().start();
		}
		server.close();  //server restart
		this.run();
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
	/**
	 * @return Returns the recordThread.
	 */
	public StreamThread getRecordThread() {
		return recordThread;
	}
	/**
	 * @param recordThread The recordThread to set.
	 */
	public void setRecordThread(StreamThread recordThread) {
		this.recordThread = recordThread;
	}
}
