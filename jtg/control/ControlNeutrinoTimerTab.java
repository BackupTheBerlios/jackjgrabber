package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTable;

import org.apache.log4j.Logger;

import model.BOSender;
import model.BOTimer;


import presentation.GuiMainView;
import presentation.GuiNeutrinoTimerPanel;
import service.SerAlertDialog;
import service.SerFormatter;

/**
 * @author Alexander Geist
 */
public class ControlNeutrinoTimerTab extends ControlTab implements ActionListener, MouseListener {
	
	GuiMainView mainView;
	ArrayList[] timerList;
	ArrayList senderList;
	GuiNeutrinoTimerPanel tab;
	public String[] repeatOptions = { "einmal", "t�glich", "w�chentlich", "2-w�chentlich", "4-w�chentlich", "monatlich", "Wochentage" };
	public String[] timerType = { "SHUTDOWN", "NEXTPROGRAM", "ZAPTO", "STANDBY", "REMIND", "SLEEPTIMER"};
	
	public ControlNeutrinoTimerTab(GuiMainView view) {
		this.setMainView(view);
	}
	
	public void initialize() {
		this.setTab((GuiNeutrinoTimerPanel)this.getMainView().getTabTimer());
		try {
			this.setTimerList(ControlMain.getBoxAccess().readTimer());
			this.getTab().getRecordTimerTableModel().fireTableDataChanged();
			this.setSenderList(ControlMain.getBoxAccess().getAllSender());
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("ControlNeutrinoTimerTab", this.getMainView());
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action == "deleteAll") {
			
		}
		if (action == "deleteAllProgramTimer") {
			
		}
		if (action == "deleteAllSystemTimer") {
			
		}
		if (action == "deleteSelectedProgramTimer") {
			
		}
		if (action == "deleteSelectedSystemTimer") {
			
		}
		if (action == "addProgramTimer") {
			this.getTimerList()[0].add(this.buildRecordTimer());
			this.getTab().getRecordTimerTableModel().fireTableDataChanged();
		}
		if (action == "addSystemTimer") {
			this.getTimerList()[1].add(this.buildSystemTimer());
			this.getTab().getSystemTimerTableModel().fireTableDataChanged();
		}
		if (action == "reload") {
			
		}
		if (action == "send") {
			this.actionSend();
		}
	}
	
	private void actionSend() {
		this.sendTimer(this.getTimerList()[0]);
		this.sendTimer(this.getTimerList()[1]);
	}
	
	private void sendTimer(ArrayList timerList) {
		for (int i=0; i<timerList.size(); i++) {
			BOTimer timer = (BOTimer)timerList.get(i);
			if (timer.getModifiedId() != null) { //nur neue und modifizierte Timer wegschreiben
				try {
					if (ControlMain.getBoxAccess().writeTimer(timer) != null) {
						Logger.getLogger("ControlProgramTab").info("Timer �bertragen "+timer.getInfo());
					} else {
						Logger.getLogger("ControlProgramTab").error(timer.getInfo());
					}
				} catch (IOException e) {
					SerAlertDialog.alertConnectionLost("ControlProgramTab", this.getMainView());
				}
			}
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
		if (tableName == "systemTimerTable") {
			BOTimer timer = (BOTimer)this.getTimerList()[1].get(selectedRow);
			this.getTab().selectRepeatDaysForSystemTimer(timer);
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
	
	
	public String convertLongEventRepeat (String longString) {
		if (longString.equals("einmal")) {
			return "0";
		}
		if (longString.equals("t�glich")){
			return "1";
		}
		if (longString.equals("w�chentlich")){
			return "2";
		}
		if (longString.equals("2-w�chentlich")){
			return "3";
		}
		if (longString.equals("4-w�chentlich")){
			return "4";
		}
		if (longString.equals("monatlich")) {
			return  "5";
		} else {
			return "768";
		}
	}
	
	public String convertShortEventRepeat(String shortString){
		int repeatNumber = Integer.parseInt(shortString);
    	switch(repeatNumber) {
			case 0:
			return "einmal";
			case 1:
			return "t�glich";
			case 2:
			return "w�chentlich";
			case 3:
			return "2-w�chentlich";
			case 4:
			return "4-w�chentlich";
			case 5:
			return "monatlich";
    	}
    	if (repeatNumber >5) {
    		return "Wochentage"; 
    	}
    	return new String();
	}
	
	public String convertShortEventType(String eventType) {
		switch(Integer.parseInt(eventType)) {
			case 1: return "SHUTDOWN";
			case 2: return "NEXTPROGRAM";
			case 3: return "ZAPTO";
			case 4: return "STANDBY";
			case 5: return "RECORD";
			case 6: return "REMIND";
			case 7: return "SLEEPTIMER";										
		}
		return new String();
	}
	
	public String convertLongEventType(String longString) {
		if (longString.equals("SHUTDOWN")){
			return "1";
		}
		if (longString.equals("NEXTPROGRAM")){
			return "2";
		}
		if (longString.equals("ZAPTO")){
			return "3";
		}
		if (longString.equals("STANDBY")){
			return "4";
		}
		if (longString.equals("RECORD")){
			return "5";
		}
		if (longString.equals("REMIND")) {
			return  "6";
		} else {
			return "7";
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
		timer.setEventTypeId("5");
		return timer;
	}
	private BOTimer buildSystemTimer() {
		BOTimer timer = new BOTimer();
		
		BOSender defaultSender = (BOSender)this.getSenderList().get(0);
		long now = new Date().getTime();
		
		timer.setAnnounceTime(Long.toString(new Date().getTime()/1000));
		timer.setUnformattedStartTime(SerFormatter.formatDate(now));  
		timer.setUnformattedStopTime(SerFormatter.formatDate(now)); 
		timer.setModifiedId("new");
				
		timer.setEventRepeatId("0");
		timer.setEventTypeId("1");
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
	public GuiNeutrinoTimerPanel getTab() {
		return tab;
	}
	/**
	 * @param tab The tab to set.
	 */
	public void setTab(GuiNeutrinoTimerPanel tab) {
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
