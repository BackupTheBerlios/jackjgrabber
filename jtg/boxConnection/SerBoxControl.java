package boxConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.URL;
import java.util.ArrayList;

import model.BOBouquet;
import model.BOEpg;
import model.BOEpgDetails;
import model.BOSender;
import model.BOTimer;

import org.apache.log4j.Logger;


/**
 * @author Treito
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class SerBoxControl {	
	
	/**
	 *	Detect the Image of the Box 
	 *  Rï¿½ckgabe von 0 (default), 1 (Neutrino), 2 (Enigma), 3 (Sonstige)
	 */
	public static int ConnectBox(String ConnectBoxIP) {
		int imageType=0; //Defaultwert!!!
		URL url;
		Authenticator.setDefault(new SerBoxAuthenticator());
		
		try {		
			url= new URL ("http://"+ConnectBoxIP);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));														
			String inputLine;
		
			while ((inputLine = in.readLine()) !=null) {		
				if (inputLine.toLowerCase().indexOf("neutrino") > 0) {
					imageType=1;
					return(imageType);
				}
				if (inputLine.toLowerCase().indexOf("enigma") > 0) {
					imageType=2;
					return(imageType);
				}
				else {
					imageType=3;
				}
			}
			in.close();
		} catch (IOException e) { //Box ist aus, Rückgabe des Defaultwertes
			Logger.getLogger("SerBoxControl").error("Unable to connect");
			return(imageType);
		} catch (IllegalArgumentException ex) {
			Logger.getLogger("SerBoxControl").error("No valid BoxIp");
		}
		return(imageType);
	}

	public abstract String getName();
	public abstract BufferedReader getConnection(String request) throws IOException;
	public abstract ArrayList getPids() throws IOException;
	public abstract ArrayList getBouquetList() throws IOException;
	public abstract ArrayList getAllSender() throws IOException;
	public abstract ArrayList getSender(BOBouquet bouquet) throws IOException;
	public abstract String zapTo(String chanId) throws IOException;
	public abstract ArrayList getEpg(BOSender sender) throws IOException;
	public abstract BOEpgDetails getEpgDetail(BOEpg epg) throws IOException;
    public abstract String sendMessage(String message) throws IOException;
    public abstract String standbyBox(String modus) throws IOException;
	public abstract String shutdownBox() throws IOException;
	public abstract ArrayList getTimer() throws IOException;
	public abstract String setTimer(BOTimer timer) throws IOException;		

}
