package boxConnection;
/*
SerBoxControlDefault.java by Geist Alexander 

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
import java.util.ArrayList;
import java.util.GregorianCalendar;

import presentation.GuiMainView;
import presentation.timer.GuiEnigmaRecordTimerTableModel;
import presentation.timer.GuiEnigmaSystemTimerTableModel;
import presentation.timer.GuiRecordTimerTableModel;
import presentation.timer.GuiSystemTimerTableModel;

import control.ControlMain;
import control.ControlTimerTab;

import model.BOBouquet;
import model.BOEpg;
import model.BOEpgDetails;
import model.BOPids;
import model.BOSender;
import model.BOTimer;
import model.BOTimerList;

/**
 * Default-Schnittstellen-Definition
 * 
 */
public class SerBoxControlDefault extends SerBoxControl {
    
    public GregorianCalendar getBoxTime() throws IOException {
        throw new IOException();
	}
	
    public BOSender getRunningSender() throws IOException {
        throw new IOException();
	}
    
    public BOTimerList getTimerList() throws IOException{
        throw new IOException();
    }
    public BOTimerList reReadTimerList() throws IOException{
        throw new IOException();
    }
    public ArrayList getBoxVersion() throws IOException {
        throw new IOException();
    }
    
	public String getName() {
		return "Default";
	}
	
	public boolean isTvMode()  throws IOException {
		throw new IOException();
	}
	
	public String getChanIdOfRunningSender() throws IOException {
		throw new IOException();
	}
	
	public BufferedReader getConnection(String request) throws IOException {
		throw new IOException();
	}
		
	public BOPids getPids() throws IOException {
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
	public BOTimerList readTimer() throws IOException {
		throw new IOException();
	}
	public String writeTimer(BOTimer timer) throws IOException {
		throw new IOException();
	}
	public String setRadioTvMode(String mode) throws IOException {
		throw new IOException();
	}

	/* (non-Javadoc)
	 * @see boxConnection.SerBoxControl#setRecordModusWithPlayback()
	 */
	public String setRecordModusWithPlayback() throws IOException {
		throw new IOException();
	}

	/* (non-Javadoc)
	 * @see boxConnection.SerBoxControl#setRecordModus()
	 */
	public String setRecordModus() throws IOException {
		throw new IOException();
	}

	/* (non-Javadoc)
	 * @see boxConnection.SerBoxControl#stopRecordModus()
	 */
	public String stopRecordModus() throws IOException {
		throw new IOException();
	}
	public String [] getRepeatOptions() throws IOException {
		return new String [] {};
	}
	
	public String [] getTimerType() throws IOException {
		return new String [] {};
	}
	
	public GuiRecordTimerTableModel getRecordTimerTabelModel(GuiMainView view) {
		ControlTimerTab control = new ControlTimerTab(view);
		return new GuiEnigmaRecordTimerTableModel(control);
	}
	public GuiSystemTimerTableModel getSystemTimerTabelModel(GuiMainView view) {
		ControlTimerTab control = new ControlTimerTab(view);
		return new GuiEnigmaSystemTimerTableModel(control);
	}
	public String getIcon() {
		return ("");	
	}

}
