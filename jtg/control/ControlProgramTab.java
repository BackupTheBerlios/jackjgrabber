/*
 * Created on 11.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
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
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import model.BOBouquet;
import model.BOEpg;
import model.BOEpgDetails;
import model.BOSender;
import presentation.GuiEpgTableModel;
import presentation.GuiMainView;
import presentation.GuiSenderTableModel;
import service.SerAlertDialog;
import service.SerBoxControl;



/**
 * @author Alexander Geist
 * Verwaltung des Programmtabs.
 */
public class ControlProgramTab extends ControlTab implements ActionListener, MouseListener, ItemListener {
	
	ArrayList bouquetList = new ArrayList();
	ArrayList pids;
	SerBoxControl box;
	BOSender selectedSender;
	BOEpg selectedEpg;
	BOBouquet selectedBouquet;
	Date dateChooserDate;
	GuiMainView mainView;
	
	public ControlProgramTab(GuiMainView view) {
		this.setMainView(view);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see control.ControlTab#initialize()
	 */
	public void initialize() {
		try {
			this.setBox(ControlMain.getBox());
			this.setBouquetList(this.getBox().getBouquetList());
			this.setSelectedBouquet((BOBouquet)this.getBouquetList().get(0));
			this.getSelectedBouquet().readSender();		
			this.getMainView().getTabProgramm().getJComboBoxBouquets().setSelectedIndex(0);
		} catch (IOException e) {
			SerAlertDialog.alert("Not connected to Box", this.getMainView());
		}
	}
	
	/**
	 * Klick-Events der Buttons
	 */
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action == "Aufnahme") {
			Logger.getLogger("ControlProgrammTab").info("bla bla");
		}
	}
	/**
	 * Klick-Events der Tables
	 */
	public void mousePressed(MouseEvent me) {
		try {
			JTable table = (JTable)me.getSource();
			String tableName = table.getName();
			//Neuer Sender selektiert
			if (tableName == "Sender") {  
				this.setSelectedSender((BOSender)this.getSelectedBouquet().getSender().get(table.getSelectedRow()));
				if (me.getClickCount()==2) { //Zapping
					if (ControlMain.getBox().zapTo(this.getSelectedSender().getChanId()).equals("ok")) {
						this.setPids(ControlMain.getBox().getPids());
					}
				}
			}
			//Neue Epg-Zeile selektiert
			if (tableName == "Epg") {
				this.setSelectedEpg((BOEpg)this.getEpgTableModel().getEpgList().get(table.getSelectedRow()));
				if (me.getClickCount()==2) {
					//TODO add to TimerList
				}
			}
		} catch (ConnectException e) {
			SerAlertDialog.alert("Not connected to box", this.getMainView());
		} catch (IOException e) {
			SerAlertDialog.alert("Not connected to box", this.getMainView());
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
	 * Select-Event der Bouquet-Combobox
	 * Setzen des aktuellen Bouquets 
	 */
	public void itemStateChanged( ItemEvent e ) {
		JComboBox selectedChoice = (JComboBox)e.getSource();
		this.setSelectedBouquet((BOBouquet)this.getBouquetList().get(selectedChoice.getSelectedIndex()));
		this.reInitSender();
      }
	
	/**
	 * Aktualisieren des Tables EPG
	 */
	public void reInitEpg() {
		try {
			this.getSelectedSender().readEpg();
			this.getEpgTableModel().fireTableRowsInserted(1,1);
			this.getMainView().getTabProgramm().getJTextAreaEPG().setText("");
		} catch (IOException e) {
			SerAlertDialog.alert("Fehler beim Lesen des EPG", this.getMainView());
		}
	}
	/**
	 * Aktualisieren des Tables Sender
	 */
	public void reInitSender() {
		try {
			this.getSelectedBouquet().readSender();
			if (this.getMainView().tabProgramm != null) { //Beim 1. Start gibt es noch keine Table zum refreshen
				this.getSenderTableModel().fireTableRowsInserted(1,1);
			}
		} catch (IOException e) {
			SerAlertDialog.alert("Fehler beim Lesen des EPG", this.getMainView());
		}
	}
	/**
	 *Aktualisieren des TextPane Epg-Datails
	 */
	public void reInitEpgDetail() {
		this.getMainView().getTabProgramm().getJTextAreaEPG().setText("");
		try {
			BOEpgDetails detail = this.getSelectedEpg().readEpgDetails();
			this.getJTextAreaEPG().setText(detail.getText());
			this.getJTextAreaEPG().setCaretPosition(0);
		} catch (IOException e) {
			SerAlertDialog.alert("Fehler beim Leser der Epg-Details", this.getMainView());
		}
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
	public void setMainView(GuiMainView view) {
		this.mainView = view;
	}
	/**
	 * @return Returns the bouquetList.
	 */
	public ArrayList getBouquetList() {
		return bouquetList;
	}
	/**
	 * @param bouquetList The bouquetList to set.
	 */
	public void setBouquetList(ArrayList bouquetList) {
		this.bouquetList = bouquetList;
	}
	/**
	 * @return Returns the selectedSender.
	 */
	public BOSender getSelectedSender() {
		return selectedSender;
	}
	/**
	 * Setzen des aktuellen Senders, und zeigen des richtigen EPG
	 */
	public void setSelectedSender(BOSender selectedSender) {
		this.selectedSender = selectedSender;
		this.reInitEpg();
	}
	/**
	 * @return Returns the box.
	 */
	public SerBoxControl getBox() {
		return box;
	}
	/**
	 * @param box The box to set.
	 */
	public void setBox(SerBoxControl box) {
		this.box = box;
	}
	/**
	 * @return Returns the selectedEpg.
	 */
	public BOEpg getSelectedEpg() {
		return selectedEpg;
	}
	/**
	 * Setzen des aktuellen Epg�s, refreshen der Dazugeh�rigen Epg-Details
	 */
	public void setSelectedEpg(BOEpg selectedEpg) {
		this.selectedEpg = selectedEpg;
		this.reInitEpgDetail();
	}
	/**
	 * @return Returns the pids.
	 */
	public ArrayList getPids() {
		return pids;
	}
	/**
	 * @param pids The pids to set.
	 */
	public void setPids(ArrayList pids) {
		this.pids = pids;
	}
	/**
	 * @return BOBouquet
	 */
	public BOBouquet getSelectedBouquet() {
		return selectedBouquet;
	}

	/**
	 * Sets the selectedBouquet.
	 * @param selectedBouquet The selectedBouquet to set
	 */
	public void setSelectedBouquet(BOBouquet selectedBouquet) {
		this.selectedBouquet = selectedBouquet;
	}

	/**
	 * @return Returns the dateChooserDate.
	 */
	public Date getDateChooserDate() {
		if (dateChooserDate == null) {
			Date date = this.getMainView().getTabProgramm().getJDateChooser().getDate();
			dateChooserDate=date;
		}
		return dateChooserDate;
	}
	/**
	 * Methode wird aufgerufen wenn Datum geaendert wurde
	 */
	public void setDateChooserDate(Date dateChooserDate) {
		this.dateChooserDate = dateChooserDate;
		//falls ein Sender selektiert ist, muss dessen EPG-Anzeige dem Datum angepasst werden
		if (this.getSelectedSender() != null) {
			this.reInitEpg();
		}
	}
	
	private GuiEpgTableModel getEpgTableModel() {
		return this.getMainView().getTabProgramm().getEpgTableModel();
	}
	
	private GuiSenderTableModel getSenderTableModel() {
		return this.getMainView().getTabProgramm().getSenderTableModel();
	}
	
	private JTextArea getJTextAreaEPG() {
		return this.getMainView().getTabProgramm().getJTextAreaEPG();
	}
}
