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

import model.BOLocalTimer;
import model.BOSender;
import model.BOTimer;
import model.BOTimerList;
import presentation.GuiMainView;
import presentation.timer.GuiNeutrinoTimerPanel;
import presentation.timer.GuiTimerPanel;
import service.SerAlertDialog;
import service.SerFormatter;
import service.SerTimerHandler;


public class ControlNeutrinoTimerTab extends ControlTabTimer implements ActionListener, MouseListener {
	
	GuiMainView mainView;
	BOTimerList timerList;
	ArrayList senderList;
	GuiNeutrinoTimerPanel tab;
	Hashtable repeatOptionsHashTable;
	Hashtable timerTypeHashTable;
	public static final String[] repeatOptions = {ControlMain.getProperty("once"), ControlMain.getProperty("dayly"), ControlMain.getProperty("weekly"), 
			ControlMain.getProperty("2-weekly"), ControlMain.getProperty("4-weekly"), ControlMain.getProperty("weekdays")  };
	
	public static final String[] timerType = { "SHUTDOWN", "NEXTPROGRAM", "ZAPTO", "STANDBY", "RECORD", "REMIND", "SLEEPTIMER"};
	
	public static final String[] weekdays = {ControlMain.getProperty("monday"), ControlMain.getProperty("tuesday"), ControlMain.getProperty("wednesday"), 
			ControlMain.getProperty("thursday"), ControlMain.getProperty("friday"), ControlMain.getProperty("saturday"), ControlMain.getProperty("sunday")
	};
	public static final int[] weekdays_value = {512, 1024, 2048, 4096, 8192, 16384, 32768};
	
	public ControlNeutrinoTimerTab(GuiMainView view) {
		this.setMainView(view);
	}
	
	public void initialize() {}
	
	public void run() {
	    this.setTab((GuiNeutrinoTimerPanel)this.getMainView().getTabTimer());
			try {
				this.setSenderList(ControlMain.getBoxAccess().getAllSender());
				this.setTimerList(ControlMain.getBoxAccess().reReadTimerList());
				this.refreshTables();
				this.getView().recordTimerSorter.setSortingStatus(1, 1);
				this.getView().systemTimerSorter.setSortingStatus(1, 1);
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
				this.reReadTimerList();
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
	
	public void addRecordTimer(BOTimer timer) {
		this.getTimerList().getRecordTimerList().add(timer);
		this.getView().getRecordTimerTableModel().fireTableDataChanged();
		this.getView().recordTimerSorter.fireTableDataChanged();

	}
	
	private void actionAddRecordTimer() {
	    new ControlTimerEditView(this, this.buildRecordTimer().getLocalTimer());
	}
	
	private void actionAddSystemTimer() {
		this.getTimerList().getSystemTimerList().add(this.buildSystemTimer());
		this.getView().getSystemTimerTableModel().fireTableDataChanged();
		this.getView().systemTimerSorter.fireTableDataChanged();
	}
	
	/*
	 * wird aufgerufen wenn ein Wochentag selektiert wird.
	 * Der neue RepeatId-Wert wird dann aufgrund der selektierten
	 * Wochentage festgestellt und gesetzt
	 */
	public void actionRecordTimerRepeatDaysChanged (ActionEvent event) {
		JRadioButton radioButton = (JRadioButton)event.getSource();
		BOTimer timer = this.getSelectedRecordTimer();
		timer.setEventRepeatId(this.getRepeatOptionValue(this.getView().jRadioButtonWhtage));
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
		timer.setEventRepeatId(this.getRepeatOptionValue(this.getView().jRadioButtonWhtage2));
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
			this.deleteAllTimer(this.getTimerList().getRecordTimerList());
			this.getTimerList().setRecordTimerList(new ArrayList());
			this.deleteAllTimer(this.getTimerList().getSystemTimerList());
			this.getTimerList().setSystemTimerList(new ArrayList());
			this.refreshTables();
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("ControlNeutrinoTimerTab", this.getMainView());
		}
	}
	
	private void actionDeleteAllRecordTimer() {
		try {
			this.deleteAllTimer(this.getTimerList().getRecordTimerList());
			this.getTimerList().setRecordTimerList(new ArrayList());
			this.getView().getRecordTimerTableModel().fireTableDataChanged();
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("ControlNeutrinoTimerTab", this.getMainView());
		}
	}
	
	private void actionDeleteAllSystemTimer() {
		try {
			this.deleteAllTimer(this.getTimerList().getSystemTimerList());
			this.getTimerList().setSystemTimerList(new ArrayList());
			this.getView().getSystemTimerTableModel().fireTableDataChanged();
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("ControlNeutrinoTimerTab", this.getMainView());
		}
	}
	
	private void actionDeleteSelectedRecordTimer() {
		int[] rows = this.getView().getJTableRecordTimer().getSelectedRows();
		ArrayList timerList = this.getTimerList().getRecordTimerList();
		for (int i=rows.length-1; 0<=i; i--) {
		    int modelIndex = this.getView().recordTimerSorter.modelIndex(rows[i]);
			BOTimer timer = (BOTimer)timerList.get(modelIndex);
			try {
				this.deleteTimer(timer);
				timerList.remove(modelIndex);
				this.getView().getRecordTimerTableModel().fireTableDataChanged();
			} catch (IOException e) {
				SerAlertDialog.alertConnectionLost("ControlNeutrinoTimerTab", this.getMainView());
			}
		}
	}
	
	private void actionDeleteSelectedSystemTimer() {
		int[] rows = this.getView().getJTableSystemTimer().getSelectedRows();
		ArrayList timerList = this.getTimerList().getSystemTimerList();
		for (int i=rows.length-1; 0<=i; i--) {
		    int modelIndex = this.getView().systemTimerSorter.modelIndex(rows[i]);
			BOTimer timer = (BOTimer)timerList.get(modelIndex);
			try {
				this.deleteTimer(timer);
				timerList.remove(modelIndex);
				this.getView().getSystemTimerTableModel().fireTableDataChanged();
			} catch (IOException e) {
				SerAlertDialog.alertConnectionLost("ControlNeutrinoTimerTab", this.getMainView());
			}
		}
	}
	
	private void actionSend() {
		this.setChanId(this.getTimerList().getRecordTimerList());
		try {
			this.writeAllTimer(this.getTimerList().getRecordTimerList());
			this.writeAllTimer(this.getTimerList().getSystemTimerList());
			this.reReadTimerList();
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
		timer.setModifiedId("remove");
		SerTimerHandler.deleteLocalTimer(timer.getLocalTimer());
		this.writeTimer(timer);
	}
	
	/**
	 * Reload der Timer und refreshen der Tables
	 */
	public void reReadTimerList() {
		try {
            this.setTimerList(ControlMain.getBoxAccess().reReadTimerList());
            this.refreshTables();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void writeTimer(BOTimer timer) throws IOException {
	    if (timer.getModifiedId()!=null) {
	        ControlMain.getBoxAccess().writeTimer(timer);    
	    }
	}
	
	private void writeAllTimer(ArrayList timerList) throws IOException {
		for (int i=0; i<timerList.size(); i++) {
			this.writeTimer((BOTimer)timerList.get(i));
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
			if (me.getClickCount()==2) {
			    new ControlTimerEditView(this, this.getSelectedRecordTimer().getLocalTimer());
			}
			selectRepeatDaysForRecordTimer(this.getSelectedRecordTimer(), this.getView().jRadioButtonWhtage);
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
		if (longString.equals(ControlMain.getProperty("weekdays"))) {
			return "512";
		}
		return (String)this.getRepeatOptionsHashTable().get(longString);
	}
	
	public String convertShortEventRepeat(String shortString){
		int number = Integer.parseInt(shortString);
    	if (number >5) {
    		return ControlMain.getProperty("weekdays"); 
    	}
		return getRepeatOptions()[number];
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
		timer.unformattedStartTime=SerFormatter.formatTimeInMillisToCal(now);  
		timer.setUnformattedStopTime(SerFormatter.formatTimeInMillisToCal(now)); 				
		timer.setEventRepeatId("0");
		timer.setEventTypeId("5");
		
		BOLocalTimer.getDefaultLocalTimer(timer);
		return timer;
	}
	
	private BOTimer buildSystemTimer() {
		BOTimer timer = new BOTimer();
		
		BOSender defaultSender = (BOSender)this.getSenderList().get(0);
		long now = new Date().getTime();
		
		timer.setAnnounceTime(Long.toString(new Date().getTime()/1000));
		timer.setUnformattedStartTime(SerFormatter.formatTimeInMillisToCal(now));  
		timer.setUnformattedStopTime(SerFormatter.formatTimeInMillisToCal(now)); 
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
	public static void selectRepeatDaysForRecordTimer(BOTimer timer, JRadioButton[] jRadioButtonWhtage) {
		int result = Integer.parseInt((String)timer.getEventRepeatId());		
		if (result>5) {
			for (int i = 0; i<7; i++){
				jRadioButtonWhtage[i].setSelected((result&weekdays_value[i])==weekdays_value[i]);
			}
		} else {
		    for (int i = 0; i<7; i++){
				jRadioButtonWhtage[i].setSelected(false);
			}
		}
	}

	public void selectRepeatDaysForSystemTimer(BOTimer timer) {
		int result = Integer.parseInt((String)timer.getEventRepeatId());
		if (result>5) {
			this.getView().enableSystemTimerWeekdays(true);
			for (int i = 0; i<7; i++){
				this.getView().jRadioButtonWhtage2[i].setSelected((result&weekdays_value[i])==weekdays_value[i]);
			}
		} else {
			this.getView().enableSystemTimerWeekdays(false);
		}
	}
		
	private void refreshTables() {
		this.getView().getRecordTimerTableModel().fireTableDataChanged();
		this.getView().getSystemTimerTableModel().fireTableDataChanged();
	}
	
	public BOTimer getSelectedRecordTimer () {
		JTable table = this.getView().getJTableRecordTimer();
		int selectedRow = table.getSelectedRow();
		int modelIndex = this.getView().recordTimerSorter.modelIndex(selectedRow);
		return (BOTimer)this.getTimerList().getRecordTimerList().get(modelIndex);
	}
	
	public BOTimer getSelectedSystemTimer () {
		JTable table = this.getView().getJTableSystemTimer();
		int selectedRow = table.getSelectedRow();
		int modelIndex = this.getView().systemTimerSorter.modelIndex(selectedRow);
		return (BOTimer)this.getTimerList().getSystemTimerList().get(modelIndex);
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
	public BOTimerList getTimerList() {
		return timerList;
	}
	/**
	 * @param timerList The timerList to set.
	 */
	public void setTimerList(BOTimerList timerList) {
		this.timerList = timerList;
	}
	public GuiNeutrinoTimerPanel getView() {
	    return (GuiNeutrinoTimerPanel)this.getTab();
	}
	
	/**
	 * @return Returns the tab.
	 */
	public GuiTimerPanel getTab() {
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
