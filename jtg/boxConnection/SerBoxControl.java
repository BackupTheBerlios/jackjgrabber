package boxConnection;
/*
SerBoxControl.java by Geist Alexander 

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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.URL;
import java.util.ArrayList;

import model.BOBouquet;
import model.BOEpg;
import model.BOEpgDetails;
import model.BOPids;
import model.BOSender;
import model.BOTimer;

import org.apache.log4j.Logger;

import control.ControlMain;

public abstract class SerBoxControl {	
	
	/**
	 *	Detect the Image of the Box 
	 *  Rückgabe von 0 (default), 1 (Neutrino), 2 (Enigma), 3 (Sonstige)
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
			Logger.getLogger("SerBoxControl").error(ControlMain.getProperty("err_connect"));
			return(imageType);
		} catch (IllegalArgumentException ex) {
			Logger.getLogger("SerBoxControl").error(ControlMain.getProperty("err_valid_ip"));
		}
		return(imageType);
	}

	public abstract String getName();
	public abstract BufferedReader getConnection(String request) throws IOException;
	public abstract BOPids getPids(boolean tvMode) throws IOException;
	public abstract ArrayList getBouquetList() throws IOException;
	public abstract ArrayList getAllSender() throws IOException;
	public abstract ArrayList getSender(BOBouquet bouquet) throws IOException;
	public abstract String zapTo(String chanId) throws IOException;
	public abstract ArrayList getEpg(BOSender sender) throws IOException;
	public abstract BOEpgDetails getEpgDetail(BOEpg epg) throws IOException;
    public abstract String sendMessage(String message) throws IOException;
    public abstract String standbyBox(String modus) throws IOException;
	public abstract String shutdownBox() throws IOException;
	public abstract ArrayList[] readTimer() throws IOException;
	public abstract String writeTimer(BOTimer timer) throws IOException;		
	public abstract String getChanIdOfRunningSender() throws IOException;
	public abstract boolean isTvMode() throws IOException;
	public abstract String setRadioTvMode(String mode) throws IOException;
}
