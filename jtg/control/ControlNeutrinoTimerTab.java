package control;
/*
ControlNeutrinoTimerTab.java by Geist Alexander 

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.swing.JRadioButton;
import javax.swing.JTable;

import model.BOSender;
import model.BOTimer;


import presentation.GuiMainView;
import presentation.GuiNeutrinoTimerPanel;
import service.SerAlertDialog;
import service.SerFormatter;


public class ControlNeutrinoTimerTab extends ControlTimerTab implements ActionListener, MouseListener {
	
	GuiMainView mainView;
	ArrayList[] timerList;
	ArrayList senderList;
	GuiNeutrinoTimerPanel tab;
	Hashtable repeatOptionsHashTable;
	Hashtable timerTypeHashTable;
	public final String[] repeatOptions = { "einmal", "täglich", "wöchentlich", "2-wöchentlich", "4-wöchentlich", "monatlich", "Wochentage" };
	public static final String[] timerType = { "SHUTDOWN", "NEXTPROGRAM", "ZAPTO", "STANDBY", "RECORD", "REMIND", "SLEEPTIMER"};
	public final String[] WOCHENTAGE = {"Montag","Dienstag","Mittwoch","Donnerstag","Freitag","Samstag","Sonntag"};
	public final int[] WOCHENTAGE_VALUE = {512, 1024, 2048, 4096, 8192, 16384, 32768};
	
	public ControlNeutrinoTimerTab(GuiMainView view) {
		this.setMainView(view);
	}
	
	public void initialize() {
		this.setTab((GuiNeutrinoTimerPanel)this.getMainView().getTabTimer());
		try {
			this.setSenderList(ControlMain.getBoxAccess().getAllSender());
			this.setTimerList(ControlMain.getBoxAccess().readTimer());
			this.refreshTables();
			this.getTab().recordTimerSorter.setSortingStatus(1, 1);
			this.getTab().systemTimerSorter.setSortingStatus(1, 1);
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("ControlNeutrinoTimerTab", this.getMainView());
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		while (true) {
			if (action == "deleteAll") {
				this.actionDeleteAll();
				break;
			}
			if (action == "deleteAllRecordTimer") {
				this.actionDeleteAllRecordTimer();
				break;
			}
			if (action == "deleteAllSystemTimer") {
				this.actionDeleteAllSystemTimer();
				break;
			}
			if (action == "deleteSelectedRecordTimer") {
				this.actionDeleteSelectedRecordTimer();
				break;
			}
			if (action == "deleteSelectedSystemTimer") {
				this.actionDeleteSelectedSystemTimer();
				break;
			}
			if (action == "addRecordTimer") {
				this.actionAddRecordTimer();
				break;
			}
			if (action == "addSystemTimer") {
				this.actionAddSystemTimer();
				break;
			}
			if (action == "reload") {
				this.actionReload();
				break;
			}
			if (action == "send") {
				this.actionSend();
				break;
			}
			if (action == "recordTimer") {
				this.actionRecordTimerRepeatDaysChanged(e);
				break;
			}
			if (action == "systemTimer") {
				this.actionSystemTimerRepeatDaysChanged(e);
				break;
			}
			break;
		}
	}
	
	private void actionAddRecordTimer() {
		this.getTimerList()[0].add(this.buildRecordTimer());
		this.getTab().getRecordTimerTableModel().fireTableDataChanged();
		this.getTab().recordTimerSorter.fireTableDataChanged();
	}
	
	private void actionAddSystemTimer() {
		this.getTimerList()[1].add(this.buildSystemTimer());
		this.getTab().getSystemTimerTableModel().fireTableDataChanged();
		this.getTab().systemTimerSorter.fireTableDataChanged();
	}
	
	/*
	 * wird aufgerufen wenn ein Wochentag selektiert wird.
	 * Der neue RepeatId-Wert wird dann aufgrund der selektierten
	 * Wochentage festgestellt und gesetzt
	 */
	public void actionRecordTimerRepeatDaysChanged (ActionEvent event) {
		JRadioButton radioButton = (JRadioButton)event.getSource();
		BOTimer timer = this.getSelectedRecordTimer();
		timer.setEventRepeatId(this.getRepeatOptionValue(this.getTab().jRadioButtonWhtage));
		timer.setModifiedId("modify");
	}
	/*
	 * wird aufgerufen wenn ein Wochentag selektiert wird.
	 * Der neue RepeatId-Wert wird dann aufgrund der selektierten
	 * Wochentage festgestellt und gesetzt
	 */
	public void actionSystemTimerRepeatDaysChanged (ActionEvent event) {
		JRadioButton radioButton = (JRadioButton)event.getSource();
		BOTimer timer = this.getSelectedSystemTimer();
		timer.setEventRepeatId(this.getRepeatOptionValue(this.getTab().jRadioButtonWhtage2));
		timer.setModifiedId("modify");
	}
	/*
	 * Beim jeweiligen RadioButton ist als Name die RepeatId eingestellt
	 */
	private String getRepeatOptionValue(JRadioButton[] buttons) {
		int result=0;
		for (int i=0; i<buttons.length; i++) {
			if (buttons[i].isSelected()) {
				result+=Integer.parseInt(buttons[i].getName());
			}
		}
		return Integer.toString(result);
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
		    int modelIndex = this.getTab().recordTimerSorter.modelIndex(rows[i]);
			BOTimer timer = (BOTimer)timerList.get(modelIndex);
			try {
				this.deleteTimer(timer);
				timerList.remove(modelIndex);
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
		    int modelIndex = this.getTab().systemTimerSorter.modelIndex(rows[i]);
			BOTimer timer = (BOTimer)timerList.get(modelIndex);
			try {
				this.deleteTimer(timer);
				timerList.remove(modelIndex);
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
		this.refreshTables();
	}
	
	private void writeTimer(BOTimer timer) throws IOException {
		ControlMain.getBoxAccess().writeTimer(timer);
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
		if (tableName == "recordTimerTable") {
			this.selectRepeatDaysForRecordTimer(this.getSelectedRecordTimer());
		}
		if (tableName == "systemTimerTable") {
			this.selectRepeatDaysForSystemTimer(this.getSelectedSystemTimer());
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
	
	/*
	 * Methode wird aufgerufen, wenn in der Repeat-Combo-Box ein Eintrag geaendert wurde
	 * Der Wert wird in den Timer geschrieben.
	 * Bei Wochentagen wird der Montag vorselektiert. 
	 */
	public String convertLongEventRepeat (String longString) {
		if (longString.equals("Wochentage")) {
			return "512";
		}
		return (String)this.getRepeatOptionsHashTable().get(longString);
	}
	
	public String convertShortEventRepeat(String shortString){
		int number = Integer.parseInt(shortString);
    	if (number >5) {
    		return "Wochentage"; 
    	}
		return this.getRepeatOptions()[number];
	}
	
	public String convertLongEventType(String longString) {	
		return (String)this.getTimerTypeHashTable().get(longString);
	}
	
	public String convertShortEventType(String timerType) {
		return this.getTimerType()[Integer.parseInt(timerType)-1];
	}
	
	private Hashtable getRepeatOptionsHashTable() {
		if (repeatOptionsHashTable == null) {
			repeatOptionsHashTable = new Hashtable();
			for (int i=0; i<this.getRepeatOptions().length; i++) {
				String repeatOption = this.getRepeatOptions()[i];
				repeatOptionsHashTable.put(repeatOption, Integer.toString(i));
			}
		}
		return repeatOptionsHashTable;
	}
	
	private Hashtable getTimerTypeHashTable() {
		if (timerTypeHashTable == null) {
			timerTypeHashTable = new Hashtable();
			for (int i=0; i<this.getTimerType().length; i++) {
				String timerType = this.getTimerType()[i];
				timerTypeHashTable.put(timerType, Integer.toString(i+1));
			}
		}
		return timerTypeHashTable;
	}
	
	private BOTimer buildRecordTimer() {
		BOTimer timer = new BOTimer();
		
		BOSender defaultSender = (BOSender)this.getSenderList().get(0);
		long now = new Date().getTime();
		
		timer.setModifiedId("new");
		timer.setSenderName( defaultSender.getName() );
		timer.setChannelId(defaultSender.getChanId());
		timer.setAnnounceTime(Long.toString(new Date().getTime()/1000));
		timer.setUnformattedStartTime(SerFormatter.formatDate(now));  
		timer.setUnformattedStopTime(SerFormatter.formatDate(now)); 
		
				
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
	 * 512 = Montags
	 * 1024 = Dienstags
	 * 2048 = Mittwochs
	 * 4096 = Donnerstags
	 * 8192 = Freitags
	 * 16384 = Samstags
	 * 32768 = Sonntags
	 */
	public void selectRepeatDaysForRecordTimer(BOTimer timer) {
		int result = Integer.parseInt((String)timer.getEventRepeatId());		
		if (result>5) {
			this.getTab().enableRecordTimerWeekdays();
			for (int i = 0; i<7; i++){
				this.getTab().jRadioButtonWhtage[i].setSelected((result&WOCHENTAGE_VALUE[i])==WOCHENTAGE_VALUE[i]);
			}
		} else {
			this.getTab().disableRecordTimerWeekdays();
		}
	}

	public void selectRepeatDaysForSystemTimer(BOTimer timer) {
		int result = Integer.parseInt((String)timer.getEventRepeatId());
		if (result>5) {
			this.getTab().enableSystemTimerWeekdays();
			for (int i = 0; i<7; i++){
				this.getTab().jRadioButtonWhtage2[i].setSelected((result&WOCHENTAGE_VALUE[i])==WOCHENTAGE_VALUE[i]);
			}
		} else {
			this.getTab().disableSystemTimerWeekdays();
		}
	}
		
	private void refreshTables() {
		this.getTab().getRecordTimerTableModel().fireTableDataChanged();
		this.getTab().getSystemTimerTableModel().fireTableDataChanged();
	}
	
	public BOTimer getSelectedRecordTimer () {
		JTable table = this.getTab().getJTableRecordTimer();
		int selectedRow = table.getSelectedRow();
		int modelIndex = this.getTab().recordTimerSorter.modelIndex(selectedRow);
		return (BOTimer)this.getTimerList()[0].get(modelIndex);
	}
	
	public BOTimer getSelectedSystemTimer () {
		JTable table = this.getTab().getJTableSystemTimer();
		int selectedRow = table.getSelectedRow();
		int modelIndex = this.getTab().systemTimerSorter.modelIndex(selectedRow);
		return (BOTimer)this.getTimerList()[1].get(modelIndex);
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
	/**
	 * @return Returns the repeatOptions.
	 */
	public String[] getRepeatOptions() {
		return repeatOptions;
	}
	/**
	 * @return Returns the timerType.
	 */
	public String[] getTimerType() {
		return timerType;
	}
}
