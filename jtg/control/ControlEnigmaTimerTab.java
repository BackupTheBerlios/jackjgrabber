package control;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.swing.JTable;

import org.apache.log4j.Logger;

import model.BOEpg;
import model.BOSender;
import model.BOTimer;


import presentation.GuiEnigmaTimerPanel;
import presentation.GuiMainView;
import service.SerAlertDialog;
import service.SerFormatter;


/*
ControlEnigmaTimerTab.java by Geist Alexander, Treito

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
public class ControlEnigmaTimerTab extends ControlTab implements ActionListener, MouseListener {
	
	GuiMainView mainView;
	ArrayList[] timerList;
	ArrayList senderList;
	GuiEnigmaTimerPanel tab;
	public String[] repeatOptions = { "einmal", "Wochentage" };
	public String[] timerType = { "Nichts", "Standby", "Shutdown"};
	
	public ControlEnigmaTimerTab(GuiMainView view) {
		this.setMainView(view);
	}
	
	public void initialize() {
		this.setTab((GuiEnigmaTimerPanel)this.getMainView().getTabTimer());
		try {
			this.setTimerList(ControlMain.getBoxAccess().readTimer());
			this.getTab().getRecordTimerTableModel().fireTableDataChanged();
			this.setSenderList(ControlMain.getBoxAccess().getAllSender());
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("ControlEnigmaTimerTab", this.getMainView());
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		if (action == "deleteAllRecordTimer") {
		    this.actionDeleteAllRecordTimer();
		}		
		if (action == "deleteSelectedRecordTimer") {
		    this.actionDeleteSelectedRecordTimer();
		}
		
		if (action == "addProgramTimer") {
			this.getTimerList()[0].add(this.buildRecordTimer());
			this.getTab().getRecordTimerTableModel().fireTableDataChanged();
		}
		if (action == "cleanup") {
		    this.actionCleanUpRecordTimer();
		}
		if (action == "reload") {
			this.actionReload();
		}
		if (action == "send") {
			this.actionSend();
		}
	}
	
	
	private void actionDeleteAllRecordTimer() {
		try {
			this.deleteAllTimer();
			this.getTimerList()[0] = new ArrayList();
			this.getTab().getRecordTimerTableModel().fireTableDataChanged();
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("ControlEnigmaTimerTab", this.getMainView());
		}
	}
	
	private void deleteAllTimer() throws IOException {
	    try {
	        BOTimer timer= new BOTimer();
	        timer.setModifiedId("deleteall");
	        this.writeTimer(timer);
	    } catch (IOException e) {
			SerAlertDialog.alertConnectionLost("ControlEnigmaTimerTab", this.getMainView());
	    }
	}
	
	private void actionCleanUpRecordTimer()
	{
	        BOTimer timer= new BOTimer();
	        timer.setModifiedId("cleanup");
	        try {
	            this.writeTimer(timer);
	            this.rereadTimerList();
	        } catch (IOException e) {
				SerAlertDialog.alertConnectionLost("ControlEnigmaTimerTab", this.getMainView());
	        }
	}
	
	private void actionDeleteSelectedRecordTimer() {
		int[] rows = this.getTab().getJTableRecordTimer().getSelectedRows();
		ArrayList timerList = this.getTimerList()[0];
		for (int i=rows.length-1; 0<=i; i--) {
			BOTimer timer = (BOTimer)timerList.get(rows[i]);
			try {
				this.deleteTimer(timer);
				timerList.remove(i);
				this.getTab().getRecordTimerTableModel().fireTableDataChanged();
			} catch (IOException e) {
				SerAlertDialog.alertConnectionLost("ControlEnigmaTimerTab", this.getMainView());
			}
		}
	}
	
	private void actionReload() {
		try {
			this.rereadTimerList();
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("ControlEnigmaTimerTab", this.getMainView());
		}
	}
	
	private void deleteTimer(BOTimer timer) throws IOException {
		if (timer.getTimerNumber() != null) {  //Neu angelegte Timer muessen nicht geloescht werden
			timer.setModifiedId("remove");
			this.writeTimer(timer);
			this.rereadTimerList();
		}
	}
	
	private void writeTimer(BOTimer timer) throws IOException {
		if (ControlMain.getBoxAccess().writeTimer(timer) != null) {
			if (timer.getModifiedId().equals("cleanup")) {
			    Logger.getLogger("ControlProgramTab").info("Timer gesäubert");
			} else if (timer.getModifiedId().equals("deleteall")) {
			    Logger.getLogger("ControlProgramTab").info("alle Timer gelöscht");
			} else if (timer.getModifiedId().equals("remove")) {
			    Logger.getLogger("ControlProgramTab").info("Timer wird gelöscht");
	        } else {
			    Logger.getLogger("ControlProgramTab").info("Timer übertragen "+timer.getInfo());
			}
		} else {
		    if (timer.getModifiedId().equals("cleanup")) {
		        Logger.getLogger("ControlProgramTab").error("Timer nicht gesäubert");
			} else if (timer.getModifiedId().equals("deleteall")) {
			    Logger.getLogger("ControlProgramTab").error("alle Timer nicht gelöscht");
			} else if (timer.getModifiedId().equals("remove")) {
			    Logger.getLogger("ControlProgramTab").error("Timer wurde nicht gelöscht");
	        } else {
			Logger.getLogger("ControlProgramTab").error(timer.getInfo());
			throw new IOException();
	        }
		}
	}
	
	private void writeAllTimer(ArrayList timerList) throws IOException {
		for (int i=0; i<timerList.size(); i++) {
			BOTimer timer = (BOTimer)timerList.get(i);
			if (timer.getModifiedId() != null) { //nur neue und modifizierte Timer wegschreiben
				this.writeTimer(timer);
			}
		}
	}
	private void rereadTimerList() throws IOException {
		this.setTimerList(ControlMain.getBoxAccess().readTimer());
		this.getTab().getRecordTimerTableModel().fireTableDataChanged();
	}
	private void actionSend() {
		//this.setChanId(this.getTimerList()[0]);
		try {
			this.writeAllTimer(this.getTimerList()[0]);
			this.rereadTimerList();
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("ControlEnigmaTimerTab", this.getMainView());
		}
	}
	/**
	 * Klick-Events der Tables
	 */
	public void mousePressed(MouseEvent me) {
		JTable table = (JTable)me.getSource();
		String tableName = table.getName();
		int selectedRow = table.getSelectedRow();
		if (tableName == "recordTimerTable") {
			BOTimer timer = (BOTimer)this.getTimerList()[0].get(selectedRow);
			this.getTab().selectRepeatDaysForRecordTimer(timer);
			if (me.getClickCount()==2) { 
				
			}
		}
		
	}
	
	public void mouseClicked(MouseEvent me) 
	{}
	public void mouseReleased(MouseEvent me)
	{}
	public void mouseExited(MouseEvent me)
	{}
	public void mouseEntered(MouseEvent me)
	{}
	
	
	public String convertShortTimerType (String timerTypeString) {
		long timerType=Long.parseLong(timerTypeString);
	    
		if ((timerType&256)==256) {
		    return "erfolgreich";
		} else if ((timerType&76)==76) {
		    return "Aufnahme";
		} else if ((timerType&44)==44) {
		    return "offen";
		} else {
		    return "Fehler";
		}
	
	}
	
	public String convertShortEventRepeat(String shortString){
		int repeatNumber = Integer.parseInt(shortString);
    	switch(repeatNumber) {
			case 0:
			return "einmal";
			
    	}
    	if (repeatNumber >1) {
    		return "Wochentage"; 
    	}
    	return new String();
	}
	
	public String convertLongEventRepeat (String longString) {
		if (longString.equals("einmal")) {
			return "0";
		} else {		
			return "768";
		}
	}
	
	public String convertShortEventType(String shortString) {
	    long timerType=Long.parseLong(shortString);
	    
		if ((timerType&134217728)==134217728) {
		    return "Standby";
		} else if ((timerType&67108864)==67108864) {
		    return "Shutdown";
		} else {
		    return "Nichts";
		}
	}
	
	public String convertLongEventType(String longString) {
	    if (longString.equals("Standby")) {
	        return "134217728";
	    } else if (longString.equals("Shutdown")) {
	        return "67108864";
	    } else {
	        return "0";
	    }
	}
	
	private BOTimer buildRecordTimer() {
	    BOTimer timer = new BOTimer();
		
		BOSender defaultSender = (BOSender)this.getSenderList().get(0);
		long now = new Date().getTime();
		
		timer.setSenderName( defaultSender.getName() );
		timer.setChannelId(defaultSender.getChanId());
		timer.setAnnounceTime(Long.toString(new Date().getTime()/1000));
		timer.setUnformattedStartTime(SerFormatter.formatDate(now));  
		timer.setUnformattedStopTime(SerFormatter.formatDate(now)); 
		timer.setModifiedId("new");
				
		timer.setEventRepeatId("0");
		timer.setEventTypeId("44");
		return timer;
	}
	
	/**
	 * @return Returns the mainView.
	 */
	public GuiMainView getMainView() {
		return mainView;
	}
	/**
	 * @param mainView The mainView to set.
	 */
	public void setMainView(GuiMainView mainView) {
		this.mainView = mainView;
	}

	/**
	 * @return Returns the timerList.
	 */
	public ArrayList[] getTimerList() {
		return timerList;
	}
	/**
	 * @param timerList The timerList to set.
	 */
	public void setTimerList(ArrayList[] timerList) {
		this.timerList = timerList;
	}
	/**
	 * @return Returns the tab.
	 */
	public GuiEnigmaTimerPanel getTab() {
		return tab;
	}
	/**
	 * @param tab The tab to set.
	 */
	public void setTab(GuiEnigmaTimerPanel tab) {
		this.tab = tab;
	}
	/**
	 * @return Returns the senderList.
	 */
	public ArrayList getSenderList() {
		return senderList;
	}
	/**
	 * @param senderList The senderList to set.
	 */
	public void setSenderList(ArrayList senderList) {
		this.senderList = senderList;
	}
}