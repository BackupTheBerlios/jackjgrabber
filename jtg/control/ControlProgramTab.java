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

import boxConnection.SerBoxControl;
import boxConnection.SerBoxTelnet;

import model.BOBouquet;
import model.BOBox;
import model.BOEpg;
import model.BOEpgDetails;
import model.BOSender;
import model.BOTimer;
import presentation.GuiEpgTableModel;
import presentation.GuiMainView;
import presentation.GuiSenderTableModel;
import service.SerAlertDialog;
import service.SerFormatter;

/**
 * @author Alexander Geist
 * Verwaltung des Programmtabs.
 */
public class ControlProgramTab extends ControlTab implements ActionListener, MouseListener, ItemListener {
	
	ArrayList bouquetList = new ArrayList();
	ArrayList pids;
	BOSender selectedSender;
	BOBox selectedBox;
	BOEpg selectedEpg;
	BOBouquet selectedBouquet;
	Date dateChooserDate;
	GuiMainView mainView;	
	
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
	
	public void selectRunningSender() {
		try {
			String runningChanId = ControlMain.getBoxAccess().getChanIdOfRunningSender();
			for (int i=0; i<getBouquetList().size(); i++) {
				BOBouquet bouquet = (BOBouquet)this.getBouquetList().get(i);
				bouquet.readSender();
				for (int i2=0; i2<bouquet.getSender().size(); i2++) {
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
		if (action == "Aufnahme") {
			
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
						this.setPids(ControlMain.getBoxAccess().getPids());
					}
				}
			}
			//Neue Epg-Zeile selektiert
			if (tableName == "Epg") {
				String eventId = (String)this.getMainView().getTabProgramm().sorter.getValueAt(table.getSelectedRow(), 0);
				this.setSelectedEpg(eventId); 
				if (me.getClickCount()==2) {
					BOTimer timer = this.buildTimer(this.getSelectedEpg());
					if (ControlMain.getBoxAccess().setTimer("new", timer).equals("ok")) {
						Logger.getLogger("ControlProgramTab").info("Timer übertragen "+this.getSelectedEpg().getInfo());
					} else {
						Logger.getLogger("ControlProgramTab").error(this.getSelectedEpg().getInfo());
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
			if (this.getSelectedBox().isSelected() != newSelectedBox.isSelected()) {
				this.getSelectedBox().setSelected(false); //alte Box zurücksetzen!	
				this.setSelectedBox(newSelectedBox);
				newSelectedBox.setSelected(true);
				ControlMain.detectImage();
				this.reInitialize();
			}
		}
		if (comboBox.getName().equals("bouquets")) {
			this.reInitBouquetList(comboBox);
		}
	}
	
	//Setzen des aktuellen Bouquets, Selektion des 1. Senders
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
	 * Setzen des aktuellen Epg, refreshen der dazugehörigen Epg-Details.
	 * Durch die Sortierung geht die Objektidentität verloren
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
		int[] rows = this.getMainView().getTabProgramm().getJTableEPG().getSelectedRows(); //Selektierter EPG´s
		
//		Schleife über die selektierten epg-Zeilen
		for (int i=0; i<rows.length; i++) { 	
			String eventId = (String)this.getMainView().getTabProgramm().sorter.getValueAt(rows[i], 0);
			
//			Schleife über die EPG-Liste um das passende EPG zur epg-event-id zu ermitteln
			for (int i2 = 0; i2<list.size(); i2++) {
				BOEpg epg = (BOEpg)list.get(i2);
				if (epg.getEventId().equals(eventId)) {
					BOTimer timer = this.buildTimer(epg);
					try {
						if (ControlMain.getBoxAccess().setTimer("new", timer).equals("ok")) {
							Logger.getLogger("ControlProgramTab").info("Timer übertragen "+epg.getInfo());
						} else {
							Logger.getLogger("ControlProgramTab").error(epg.getInfo());
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
		
		int unformattedStart = Integer.parseInt(epg.getUnformattedStart());
		int unformattedDuration = Integer.parseInt(epg.getUnformattedDuration());
		int endtime = unformattedStart+unformattedDuration;
		int announce = unformattedStart-(60*1000);
		
		timer.setSenderName(this.getSelectedSender().getChanId());
		timer.setAnnounceTime(Integer.toString(announce)); //Vorwarnzeit
		timer.setUnformattedStartTime(SerFormatter.formatUnixDate(unformattedStart));
		timer.setUnformattedStopTime(SerFormatter.formatUnixDate(endtime));
		
		timer.setEventRepeat("0");
		timer.setEventType("5");
		timer.setDescription(epg.getTitle());
		return timer;
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
}
