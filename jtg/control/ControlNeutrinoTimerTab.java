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
	public String[] repeatOptions = { "einmal", "täglich", "wöchentlich", "2-wöchentlich", "4-wöchentlich", "monatlich", "Wochentage" };
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
			this.actionDeleteAll();
		}
		if (action == "deleteAllRecordTimer") {
			this.actionDeleteAllRecordTimer();
		}
		if (action == "deleteAllSystemTimer") {
			this.actionDeleteAllSystemTimer();
		}
		if (action == "deleteSelectedRecordTimer") {
			this.actionDeleteSelectedRecordTimer();
		}
		if (action == "deleteSelectedSystemTimer") {
			this.actionDeleteSelectedSystemTimer();
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
			this.actionReload();
		}
		if (action == "send") {
			this.actionSend();
		}
	}
	
	private void actionDeleteAll() {
		try {
			this.deleteAllTimer(this.getTimerList()[0]);
			this.deleteAllTimer(this.getTimerList()[1]);
			this.rereadTimerList();
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("ControlProgramTab", this.getMainView());
		}
	}
	
	private void actionDeleteAllRecordTimer() {
		try {
			this.deleteAllTimer(this.getTimerList()[0]);
			this.getTimerList()[0] = new ArrayList();
			this.getTab().getRecordTimerTableModel().fireTableDataChanged();
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("ControlNeutrinoTimerTab", this.getMainView());
		}
	}
	
	private void actionDeleteAllSystemTimer() {
		try {
			this.deleteAllTimer(this.getTimerList()[1]);
			this.getTimerList()[1] = new ArrayList();
			this.getTab().getSystemTimerTableModel().fireTableDataChanged();
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("ControlNeutrinoTimerTab", this.getMainView());
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
				SerAlertDialog.alertConnectionLost("ControlNeutrinoTimerTab", this.getMainView());
			}
		}
	}
	
	private void actionDeleteSelectedSystemTimer() {
		int[] rows = this.getTab().getJTableSystemTimer().getSelectedRows();
		ArrayList timerList = this.getTimerList()[1];
		for (int i=rows.length-1; 0<=i; i--) {
			BOTimer timer = (BOTimer)timerList.get(rows[i]);
			try {
				this.deleteTimer(timer);
				timerList.remove(i);
				this.getTab().getSystemTimerTableModel().fireTableDataChanged();
			} catch (IOException e) {
				SerAlertDialog.alertConnectionLost("ControlNeutrinoTimerTab", this.getMainView());
			}
		}
	}
	
	private void actionReload() {
		try {
			this.rereadTimerList();
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("ControlNeutrinoTimerTab", this.getMainView());
		}
	}
	
	private void actionSend() {
		this.setChanId(this.getTimerList()[0]);
		try {
			this.writeAllTimer(this.getTimerList()[0]);
			this.writeAllTimer(this.getTimerList()[1]);
			this.rereadTimerList();
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("ControlNeutrinoTimerTab", this.getMainView());
		}
	}
	
	private void deleteAllTimer(ArrayList timerList) throws IOException {
		for (int i=0; i<timerList.size(); i++) {
			BOTimer timer = (BOTimer)timerList.get(i);
			this.deleteTimer(timer);
		}
	}
	
	private void deleteTimer(BOTimer timer) throws IOException {
		if (timer.getTimerNumber() != null) {  //Neu angelegte Timer muessen nicht geloescht werden
			timer.setModifiedId("remove");
			this.writeTimer(timer);
		}
	}
	
	/**
	 * Reload der Timer und refreshen der Tables
	 */
	private void rereadTimerList() throws IOException {
		this.setTimerList(ControlMain.getBoxAccess().readTimer());
		this.getTab().getRecordTimerTableModel().fireTableDataChanged();
		this.getTab().getSystemTimerTableModel().fireTableDataChanged();
	}
	
	private void writeTimer(BOTimer timer) throws IOException {
		if (ControlMain.getBoxAccess().writeTimer(timer) != null) {
			Logger.getLogger("ControlProgramTab").info("Timer übertragen "+timer.getInfo());
		} else {
			Logger.getLogger("ControlProgramTab").error(timer.getInfo());
			throw new IOException();
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
	
	/**
	 * Ermitteln der Channel-Id, da neu gelesene Timer nur den Sendernamen enthalten
	 * channel-id notwendig um modifizierte Timer wegzuschreiben
	 * channel-id nur für Record-Timer relevant
	 */
	private void setChanId(ArrayList timerList) {
		for (int i=0; i<timerList.size(); i++) {
			BOTimer timer = (BOTimer)timerList.get(i);
			if (timer.getEventTypeId().equals("5")) { //Sendernamen nur für Record-Timer suchen
				for (int i2=0; i2<this.getSenderList().size(); i2++) {
					BOSender sender = (BOSender)this.getSenderList().get(i2);
					if (sender.getName().equals(timer.getSenderName())) {
						timer.setChannelId(sender.getChanId());
						break;
					}
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
		if (longString.equals("täglich")){
			return "1";
		}
		if (longString.equals("wöchentlich")){
			return "2";
		}
		if (longString.equals("2-wöchentlich")){
			return "3";
		}
		if (longString.equals("4-wöchentlich")){
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
			return "täglich";
			case 2:
			return "wöchentlich";
			case 3:
			return "2-wöchentlich";
			case 4:
			return "4-wöchentlich";
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
