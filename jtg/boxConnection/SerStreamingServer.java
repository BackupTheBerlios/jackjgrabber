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

/**
 * @author Geist Alexander
 *
 */
public class SerStreamingServer extends Thread {
	
	int port = 4000;
	ServerSocket server;
	
	public SerStreamingServer(int port) {
		this.setPort(port);
	}
	
	public void run() {
		try {
			server = new ServerSocket(this.getPort());
			Socket socket = server.accept();
			this.record(socket);
		} catch (IOException e) {
			Logger.getLogger("ControlMain").error("StreamingServer start failed");	
		} catch (DocumentException e) {
			Logger.getLogger("ControlMain").error("Recording failed");	
		}
	}
	
	public void record(Socket socket) throws IOException, DocumentException {		
		SAXReader reader = new SAXReader();
		Document document = reader.read(socket.getInputStream());
		this.write(document);
		BORecordArgs recordArgs = SerXMLConverter.parseRecordDocument(document);
		
		server.close();  //server restart
		this.run();
	}
	
	public void write(Document document) throws IOException {	
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter( System.out, format );
        writer.write( document );
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
