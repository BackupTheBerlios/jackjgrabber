package boxConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import model.BOBouquet;
import model.BOEpg;
import model.BOEpgDetails;
import model.BOSender;
import model.BOTimer;

/**
 * Default-Schnittstellen-Definition
 * 
 */
public class SerBoxControlDefault extends SerBoxControl {
	
	public String getName() {
		return "Default";
	}
	
	public String getChanIdOfRunningSender() throws IOException {
		throw new IOException();
	}
	
	public BufferedReader getConnection(String request) throws IOException {
		throw new IOException();
	}
		
	public ArrayList getPids() throws IOException {
		throw new IOException();
	}	 
	
	public ArrayList getBouquetList() throws IOException {
		throw new IOException();
	}
	
	public ArrayList getAllSender() throws IOException {
		throw new IOException();
	}
	
	public ArrayList getSender(BOBouquet bouquet) throws IOException {
		throw new IOException();
	}	 
	
	public String zapTo(String channelId) throws IOException {
		throw new IOException();
	}
	
	public ArrayList getEpg(BOSender sender) throws IOException {
		throw new IOException();
	}
	
	public BOEpgDetails getEpgDetail(BOEpg epg) throws IOException {
		throw new IOException();
	}
	public String sendMessage(String message) throws IOException {
        throw new IOException();
}

	public String shutdownBox() throws IOException {
        throw new IOException();
	}

	public String standbyBox(String modus) throws IOException {
        throw new IOException();
	}  
	public ArrayList getTimer() throws IOException {
		throw new IOException();
	}
	public String setTimer(String action, BOTimer timer) throws IOException {
		throw new IOException();
	}


}
