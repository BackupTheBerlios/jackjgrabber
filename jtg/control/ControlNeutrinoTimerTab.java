package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JTable;

import org.apache.log4j.Logger;

import model.BOSender;
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
		if (action == "Aufnahme") {
			
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
