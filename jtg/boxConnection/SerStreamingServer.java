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

import service.SerXMLConverter;
import control.ControlMain;


public class SerStreamingServer extends Thread {
	
	private static ServerSocket server;
	public static boolean isRunning = false;

	public void run() {
	    int port = Integer.parseInt(ControlMain.getSettingsRecord().getStreamingServerPort());
		try {
			server = new ServerSocket(port);
			isRunning=true;
			Logger.getLogger("SerStreamingServer").info("Start Streaming-Server");
			Socket socket = server.accept();
			this.record(socket);
		} catch (IOException e) {
			if (!isRunning) {
			    isRunning=false;
			}	
		} catch (DocumentException e) {
		    isRunning=false;
			Logger.getLogger("SerStreamingServer").error("Recording failed");	
		}
	}
	
	public void record(Socket socket) throws IOException, DocumentException {		
		SAXReader reader = new SAXReader();
		Document document = reader.read(socket.getInputStream());

		//Pretty print the document to System.out
//		OutputFormat format = OutputFormat.createPrettyPrint();
//		XMLWriter writer = new XMLWriter( System.out, format );
//		writer.write( document );
		
		BORecordArgs recordArgs = SerXMLConverter.parseRecordDocument(document);
		if (recordArgs.getCommand().equals("stop") ) {
		    ControlMain.getControl().getView().getTabProgramm().stopStreamingServerModus();
		    ControlMain.getControl().getView().getTabProgramm().getControl().stopRecord();
		}
		if (recordArgs.getCommand().equals("record")) {
		    recordArgs.setQuickRecord(false);
			recordArgs.checkRecordPids();
			recordArgs.checkTitle();
			ControlMain.getControl().getView().getTabProgramm().startStreamingServerModus();
			ControlMain.getControl().getView().getTabProgramm().getControl().startRecord(recordArgs);
		}
		server.close();  //server restart
		this.run();
	}

	public static boolean stopServer() {
		try {
			if (server != null && server.isBound()) {
			    isRunning=false;
				server.close();
				Logger.getLogger("SerStreamingServer").info("StreamingServer stopped");	
				return true;
			}
		} catch (IOException e) {
			Logger.getLogger("SerStreamingServer").error("StreamingServer stop failed");	
		}
		return false;
	}
	
}
