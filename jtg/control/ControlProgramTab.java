package control;
/*
ControlProgramTab.java by Geist Alexander 

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

import boxConnection.SerBoxControl;
import boxConnection.SerBoxTelnet;

import model.BOBouquet;
import model.BOBox;
import model.BOEpg;
import model.BOEpgDetails;
import model.BORecordArgs;
import model.BOSender;
import model.BOTimer;
import presentation.GuiEpgTableModel;
import presentation.GuiMainView;
import presentation.GuiSenderTableModel;
import service.SerAlertDialog;
import service.SerFormatter;
import streaming.RecordControl;


/**
 * Controlklasse des Programmtabs.
 */
public class ControlProgramTab extends ControlTab implements ActionListener, MouseListener, ItemListener {
	
	ArrayList bouquetList = new ArrayList();
	BOSender selectedSender;
	BOBox selectedBox;
	BOEpg selectedEpg;
	BOBouquet selectedBouquet;
	Date dateChooserDate;
	GuiMainView mainView;	
	RecordControl recordControl;
	
	public ControlProgramTab(GuiMainView view) {
		this.setMainView(view);		
	}
	
	public void initialize() {
		try {
			this.setBouquetList(this.getBoxAccess().getBouquetList());
			this.selectRunningSender();
		} catch (IOException e) {			
			SerAlertDialog.alertConnectionLost("ControlProgrammTab", this.getMainView());
		}
	}
	
	public void reInitialize() {
		this.setBouquetList(new ArrayList());
		this.setSelectedBouquet(null);
		this.getSenderTableModel().fireTableDataChanged();
		selectedSender=null;
		this.getEpgTableModel().fireTableDataChanged();
		this.getMainView().getTabProgramm().getBoquetsComboModel().setSelectedItem(null);
		this.getMainView().getTabProgramm().getJTextAreaEPG().setText("");
		this.initialize();
	}
	/*
	 * Sender in den Bouquets suchen und selektieren
	 */
	public void selectRunningSender() {
		try {
			String runningChanId = ControlMain.getBoxAccess().getChanIdOfRunningSender();
			for (int i=0; i<getBouquetList().size(); i++) { //Schleife ueber die Bouquets
				BOBouquet bouquet = (BOBouquet)this.getBouquetList().get(i);
				bouquet.readSender();
				for (int i2=0; i2<bouquet.getSender().size(); i2++) { //Schleife ueber die Sender im Bouquet
					BOSender sender = (BOSender)bouquet.getSender().get(i2);
					if (sender.getChanId().equals(runningChanId)) {
						this.setSelectedBouquet(bouquet);
						this.getMainView().getTabProgramm().getJComboBoxBouquets().setSelectedIndex(i);
						this.getMainView().getTabProgramm().getJTableChannels().setRowSelectionInterval(i2, i2);
						this.setSelectedSender(sender);
						break;
					}
				}
			}
			
		} catch (IOException e) {
			this.setSelectedBouquet((BOBouquet)this.getBouquetList().get(0));		
			this.getMainView().getTabProgramm().getJComboBoxBouquets().setSelectedIndex(0);
		}
	}
	
	/**
	 * Klick-Events der Buttons
	 */
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action == "record") {
			this.actionRecord();
		}
		if (action == "Box Reboot"){
			try{
				SerBoxTelnet.runReboot();					
			}catch (Exception ex){}
		}
		if (action == "add to timer"){
			this.actionAddToTimer();
		}
		if (action == "nhttpd reset"){
			try{
				SerBoxTelnet.runNhttpdReset();
			}catch (Exception ex){}
		}
		if (action == "EPG Reset"){
			try{
				SerBoxTelnet.runSectiondReset();
			}catch (Exception ex){}
		}
	}
	/*
	 * Steuerung der 2 Zustaende. 
	 * Aufnahme l�uft bereits ->stop
	 * Aufnahme l�uft nicht->start
	 */
	private void actionRecord() {
		if (recordControl ==  null) {
			this.startRecordModus();                           
		} else {
			recordControl.stopRecord();
			this.stopRecordModus();
		}
	}
	
	public void stopRecordModus() {
		recordControl=null;
		this.getMainView().getTabProgramm().getJButtonAufnahme().setText("Aufnahme");
		this.getMainView().getTabProgramm().getJButtonAufnahme().setToolTipText("Sofortaufnahme starten");
	}
	
	public void startRecordModus() {
		recordControl = new RecordControl(this.buildRecordArgs(), this);
		recordControl.start();
		this.getMainView().getTabProgramm().getJButtonAufnahme().setText("Stop");
		this.getMainView().getTabProgramm().getJButtonAufnahme().setToolTipText("Sofortaufname stoppen");
	}
	
	private BORecordArgs buildRecordArgs() {
		BORecordArgs args = new BORecordArgs();
		args.setBouquetNr(this.getSelectedBouquet().getBouquetNummer());
		args.setChannelId(this.getSelectedSender().getChanId());
		args.setSenderName(this.getSelectedSender().getName());
		if (this.getSelectedEpg() != null) {
		    args.setEpgTitle(" "+this.getSelectedEpg().getTitle());
		}; 
		return args;
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
					if (ControlMain.getBoxAccess().zapTo(this.getSelectedSender().getChanId()).equals("ok")) {
					}
				}
			}
			//Neue Epg-Zeile selektiert
			if (tableName == "Epg") {
				String eventId = (String)this.getMainView().getTabProgramm().sorter.getValueAt(table.getSelectedRow(), 0);
				this.setSelectedEpg(eventId); 
				if (me.getClickCount()==2) {
					BOTimer timer = this.buildTimer(this.getSelectedEpg());
					if (ControlMain.getBoxAccess().writeTimer(timer).equals("ok")) {
						Logger.getLogger("ControlProgramTab").info("Timer �bertragen "+timer.getInfo());
					} else {
						Logger.getLogger("ControlProgramTab").error(timer.getInfo());
					}
				}
			}
		} catch (ConnectException e) {
			SerAlertDialog.alertConnectionLost("ControlProgrammTab", this.getMainView());
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("ControlProgrammTab", this.getMainView());
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
	 * Select-Events der Combobox
	 */
	public void itemStateChanged( ItemEvent e ) {
		JComboBox comboBox = (JComboBox)e.getSource();
		if (comboBox.getName().equals("ipList")) {
			BOBox newSelectedBox = (BOBox)ControlMain.getSettings().getBoxList().get(comboBox.getSelectedIndex());
			if (this.getSelectedBox().isSelected() != newSelectedBox.isSelected() || 
					this.getSelectedBox().getDboxIp() == null) { 
				this.getSelectedBox().setSelected(false); //alte Box zur�cksetzen!	
				this.setSelectedBox(newSelectedBox);
				newSelectedBox.setSelected(true);
				ControlMain.detectImage();
				this.reInitialize();
				this.getMainView().getMainTabPane().reInitTimerPanel(); //Bei IP-Wechsel refreshen, da evtl anderes Box-Image
			}
		}
		if (comboBox.getName().equals("bouquets")) {
			this.reInitBouquetList(comboBox);
		}
	}
	
	//Setzen des aktuellen Bouquets
	public void reInitBouquetList(JComboBox  comboBox) {
		if (this.getBouquetList().size()>0) {
			this.setSelectedBouquet((BOBouquet)this.getBouquetList().get(comboBox.getSelectedIndex()));
			this.reInitSender();
			this.showFirstSender();
		}
	}
	
	private void showFirstSender() {
		if (this.getSelectedBouquet()!=null && this.getSelectedBouquet().getSender().size()> 0) {
			this.getMainView().getTabProgramm().getJTableChannels().setRowSelectionInterval(0,0);
			this.setSelectedSender((BOSender)this.getSelectedBouquet().getSender().get(0));
		}
	}
	/**
	 * Setzen des Epg-Tables in den Ursprungszustand
	 */
	public void reInitEpg() {
		this.getEpgTableModel().fireTableDataChanged();
		this.selectedEpg=null;
		this.reInitEpgDetail();
	}
	/**
	 * Aktualisieren des Tables Sender
	 */
	public void reInitSender() {
		if (this.getMainView().getMainTabPane().tabProgramm != null) { //Beim 1. Start gibt es noch keine Table zum refreshen
			this.getSenderTableModel().fireTableDataChanged();
		}
	}
	/**
	 *Aktualisieren des TextPane Epg-Datails
	 */
	public void reInitEpgDetail() {
		this.getMainView().getTabProgramm().getJTextAreaEPG().setText("");
		if (getSelectedEpg() != null) {
			try {
				BOEpgDetails detail = this.getSelectedEpg().readEpgDetails();
				this.getJTextAreaEPG().setText(detail.getText());
				this.getJTextAreaEPG().setCaretPosition(0);
			} catch (IOException e) {
				SerAlertDialog.alertConnectionLost("ControlProgrammTab", this.getMainView());
			}
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
	 * Setze die Selektion des EPG-Tables an die 1. Zeile
	 */
	public void setSelectedSender(BOSender selectedSender) {
		this.selectedSender = selectedSender;
		if (selectedSender != null) {
			try {
				selectedSender.readEpg();
			} catch (IOException e) {
				SerAlertDialog.alertConnectionLost("ControlProgrammTab", this.getMainView());
			}
		}
		this.reInitEpg();
	}
	/**
	 * @return Returns the box.
	 */
	public SerBoxControl getBoxAccess() {
		return ControlMain.getBoxAccess();
	}
	/**
	 * @return Returns the selectedEpg.
	 */
	public BOEpg getSelectedEpg() {
		return selectedEpg;
	}
	/**
	 * Setzen des aktuellen Epg, refreshen der dazugeh�rigen Epg-Details.
	 * Durch die Sortierung geht die Objektidentit�t verloren
	 * Passendes EPG durch Event-ID finden
	 */
	public void setSelectedEpg(String eventId) {
		if (eventId != null) {
			ArrayList epgList = this.getEpgTableModel().getEpgList();
			for (int i = 0; i<epgList.size(); i++) {
				BOEpg epg = (BOEpg)epgList.get(i);
				if (epg.getEventId().equals(eventId)) {
					this.selectedEpg = epg;
					break;
				}
			}
		}
		this.reInitEpgDetail();
	}
	
	private void actionAddToTimer() {
		ArrayList list = this.getEpgTableModel().getEpgList();
		int[] rows = this.getMainView().getTabProgramm().getJTableEPG().getSelectedRows(); //Selektierter EPG�s
		
//		Schleife �ber die selektierten epg-Zeilen
		for (int i=0; i<rows.length; i++) { 	
			String eventId = (String)this.getMainView().getTabProgramm().sorter.getValueAt(rows[i], 0);
			
//			Schleife �ber die EPG-Liste um das passende EPG zur epg-event-id zu ermitteln
			for (int i2 = 0; i2<list.size(); i2++) {
				BOEpg epg = (BOEpg)list.get(i2);
				if (epg.getEventId().equals(eventId)) {
					BOTimer timer = this.buildTimer(epg);
					try {
						if (ControlMain.getBoxAccess().writeTimer(timer).equals("ok")) {
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
	}
		
	private BOTimer buildTimer(BOEpg epg) {
		BOTimer timer = new BOTimer();
		
		long unformattedStart = Long.parseLong(epg.getUnformattedStart());
		long unformattedDuration = Long.parseLong(epg.getUnformattedDuration());
		long endtime = unformattedStart+unformattedDuration;
		long announce = unformattedStart-(60*1000);
		
		timer.setChannelId(this.getSelectedSender().getChanId());
		timer.setSenderName(this.getSelectedSender().getName());
		timer.setAnnounceTime(Long.toString(announce)); //Vorwarnzeit
		timer.setUnformattedStartTime(SerFormatter.formatUnixDate(unformattedStart));
		timer.setUnformattedStopTime(SerFormatter.formatUnixDate(endtime));
		timer.setModifiedId("new");
		
		timer.setEventRepeatId("0");
		timer.setEventTypeId("5");
		timer.setDescription(epg.getTitle());
		return timer;
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
	
	private JTable getJTableEPG() {
		return this.getMainView().getTabProgramm().getJTableEPG();
	}
	/**
	 * @return Returns the selectedBox.
	 */
	public BOBox getSelectedBox() {
		return selectedBox;
	}
	/**
	 * @param selectedBox The selectedBox to set.
	 */
	public void setSelectedBox(BOBox selectedBox) {
		this.selectedBox = selectedBox;
	}
	/**
	 * @return Returns the recordControl.
	 */
	public RecordControl getRecordControl() {
		return recordControl;
	}
	/**
	 * @param recordControl The recordControl to set.
	 */
	public void setRecordControl(RecordControl recordControl) {
		this.recordControl = recordControl;
	}
}
