package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTable;
import model.BOTimer;


import presentation.GuiMainView;
import presentation.GuiNeutrinoTimerPanel;
import service.SerAlertDialog;

/**
 * @author Alexander Geist
 */
public class ControlNeutrinoTimerTab extends ControlTab implements ActionListener, MouseListener {
	
	GuiMainView mainView;
	ArrayList[] timerList;
	ArrayList senderList;
	GuiNeutrinoTimerPanel tab;
	public String[] repeatOptions = { "einmal", "täglich", "wöchentlich", "2-wöchentlich", "4-wöchentlich", "monatlich", "Wochentage" };
	public String[] timerType = { "Shutdown", "Umschalten", "Standby", "Erinnerung", "Sleep-Timer"};
	
	public ControlNeutrinoTimerTab(GuiMainView view) {
		this.setMainView(view);
	}
	
	public void initialize() {
		this.setTab((GuiNeutrinoTimerPanel)this.getMainView().getTabTimer());
		try {
			this.setTimerList(ControlMain.getBoxAccess().getTimer());
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
			
		}
		if (action == "addSystemTimer") {
			
		}
		if (action == "reload") {
			
		}
		if (action == "send") {
			
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
	
	public String convertEventType(String eventType) {
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
