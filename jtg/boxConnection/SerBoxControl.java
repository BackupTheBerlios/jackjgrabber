package boxConnection;
/*
 * SerBoxControl.java by Geist Alexander
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation,
 * Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *  
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import model.BOBouquet;
import model.BOEpg;
import model.BOEpgDetails;
import model.BOPids;
import model.BOSender;
import model.BOTimer;
import model.BOTimerList;

import org.apache.log4j.Logger;

import presentation.GuiMainView;
import presentation.timer.GuiRecordTimerTableModel;
import presentation.timer.GuiSystemTimerTableModel;
import service.SerTimerHandler;
import control.ControlMain;
import control.ControlTimerTab;

public abstract class SerBoxControl {
        
    public boolean newTimerAdded=true;
    public BOTimerList timerList;
    private BOTimer nextLocalRecordTimer;
    private boolean locked = false;
    
    public BOTimer detectNextLocalRecordTimer(boolean newRead) {
        if (timerList != null && (newRead || nextLocalRecordTimer==null)) {
            nextLocalRecordTimer = timerList.getFirstLocalRecordTimer();
        }
        return nextLocalRecordTimer;
    }
    
    public BOTimerList getTimerList(boolean newRead) {
        if ((newRead || timerList==null || newTimerAdded) && !locked ) {
            locked=true;
            SerTimerHandler.deleteOldTimer();
            timerList=SerTimerHandler.readLocalTimer();
            try {
                reReadTimerList();
            } catch (IOException e) {
                Logger.getLogger("SerBoxControl").error(ControlMain.getProperty("err_read_timer"));
            }
            newTimerAdded=false;
            locked=false;
        }
        return timerList;
    }

	/**
	 * Detect the Image of the Box R�ckgabe von 0 (default), 1 (Neutrino), 2 (Enigma), 3 (Sonstige)
	 */
	public static int ConnectBox(String ConnectBoxIP) {
		int imageType = 3; //Defaultwert!!!
		URL url;
		BufferedReader in;
		String inputLine;
		Authenticator.setDefault(new SerBoxAuthenticator());

	    if (isNeutrino(ConnectBoxIP)){
	        imageType=1;
	    } else if (isEnigma(ConnectBoxIP)) {
	        imageType = 2;
	    }
	    return imageType;

	}
    
    private static boolean isEnigma(String ConnectBoxIP) {
        try {
            URL url = new URL("http://" + ConnectBoxIP);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if  (inputLine.toLowerCase().indexOf("enigma") > 0) {
                    return true;
                } 
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
    
    private static boolean isNeutrino(String ConnectBoxIP) {
        try {
            URL url=new URL("http://"+ConnectBoxIP+"/control/info");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            if (in.readLine().equals("Neutrino")){
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
    
    public ArrayList senderList;
    public abstract ArrayList getAllSender() throws IOException;

	public abstract String getName();
	protected abstract BOTimerList reReadTimerList() throws IOException;
	public abstract GregorianCalendar getBoxTime() throws IOException;
	public abstract BufferedReader getConnection(String request) throws IOException;
	public abstract BOPids getPids() throws IOException;
	public abstract ArrayList getBouquetList() throws IOException;
	public abstract ArrayList getSender(BOBouquet bouquet) throws IOException;
	public abstract String zapTo(String chanId) throws IOException;
	public abstract ArrayList getEpg(BOSender sender) throws IOException;
	public abstract BOEpgDetails getEpgDetail(BOEpg epg) throws IOException;
	public abstract String sendMessage(String message) throws IOException;
	public abstract String standbyBox(String modus) throws IOException;
	public abstract String shutdownBox() throws IOException;
	protected abstract BOTimerList readTimer() throws IOException;
	public abstract String writeTimer(BOTimer timer) throws IOException;
	public abstract String getChanIdOfRunningSender() throws IOException;
	public abstract boolean isTvMode() throws IOException;
	public abstract String setRadioTvMode(String mode) throws IOException;
	public abstract ArrayList getBoxVersion() throws IOException;
	public abstract BOSender getRunningSender() throws IOException;

	public abstract String setRecordModusWithPlayback() throws IOException;
	public abstract String setRecordModus() throws IOException;
	public abstract String stopRecordModus() throws IOException;
	public abstract String [] getRepeatOptions() throws IOException;
	public abstract String [] getTimerType() throws IOException;
	public abstract GuiRecordTimerTableModel getRecordTimerTabelModel(ControlTimerTab ctrl);
	public abstract GuiSystemTimerTableModel getSystemTimerTabelModel(ControlTimerTab ctrl);
	public abstract String getIcon();

}