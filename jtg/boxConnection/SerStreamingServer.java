package boxConnection;
/*
SerStreamingServer.java by Geist Alexander 

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
import java.net.ServerSocket;
import java.net.Socket;

import model.BORecordArgs;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import control.ControlMain;

import service.SerXMLConverter;
import streaming.RecordControl;

public class SerStreamingServer extends Thread {
	
	int port = 4000;
	ServerSocket server;
	RecordControl recordControl;
	
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
		
		if (recordArgs.getCommand().equals("stop") && this.recordControl != null) {
			recordControl.stopRecord();
			ControlMain.getControl().getView().getTabProgramm().getControl().stopRecordModus();
		}
		if (recordArgs.getCommand().equals("record")) {
			recordControl = ControlMain.getControl().getView().getTabProgramm().getControl().startRecordModus(recordArgs);
			recordControl.start();
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
}
