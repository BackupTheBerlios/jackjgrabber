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
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumn;

import org.apache.log4j.Logger;

import boxConnection.SerBoxControl;
import boxConnection.SerBoxTelnet;
import boxConnection.SerStreamingServer;

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
import presentation.GuiTabProgramm;
import service.SerAlertDialog;
import service.SerFormatter;
import streaming.RecordControl;


/**
 * Controlklasse des Programmtabs.
 */
public class ControlProgramTab extends ControlTab implements ActionListener, MouseListener, ItemListener, ChangeListener {
	
	ArrayList bouquetList = new ArrayList();
	ArrayList pids;
	BOSender selectedSender;
	BOEpg selectedEpg;
	BORecordArgs recordArgs;
	BOBouquet selectedBouquet;
	Date dateChooserDate;
	GuiMainView mainView;	
	RecordControl recordControl;
	SerStreamingServer streamingServerThread;
	boolean tvMode;
	
	public ControlProgramTab(GuiMainView view) {
		this.setMainView(view);		
	}
	
	public void initialize() {
		try {
			if (this.getBoxAccess().isTvMode()) {
				this.initializeTVMode();
			} else {
				this.initializeRadioMode();
			}
			this.selectRunningSender();
			this.getMainView().getTabProgramm().setConnectModus();
			this.reInitStreamingServer();
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("ControlProgrammTab", this.getMainView());
		}
	}
	
	public void initializeTVMode() {
		try {
			this.setTvMode(true);
			this.setBouquetList(this.getBoxAccess().getBouquetList());
		} catch (IOException e) {			
			SerAlertDialog.alertConnectionLost("ControlProgrammTab", this.getMainView());
		}
	}
	
	public void initializeRadioMode() {
		try {
			this.setTvMode(false);
			TableColumn nummerColumn = this.getJTableSender().getColumnModel().getColumn(0);
			 this.getJTableSender().getTableHeader().getColumnModel().removeColumn(nummerColumn); //eventId ausblenden
			 
			BOBouquet bouquet = new BOBouquet("0", "Radio-Sender");
			bouquet.setSender(this.getBoxAccess().getAllSender());
			ArrayList bouquetList = new ArrayList();
			bouquetList.add(bouquet);
			this.setBouquetList(bouquetList); 
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("ControlProgrammTab", this.getMainView());
		}
	}
	
	/*
	 * Versetzen des Programm-Tabs in den Ausgangszustand
	 * und initialisiere diesen neu
	 */
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
	 * Laufenden Sender in den Bouquets suchen und selektieren
	 * Wird beim Start der Anwendung benötigt.
	 */
	public void selectRunningSender() {
		try {
			String runningChanId = ControlMain.getBoxAccess().getChanIdOfRunningSender();
			for (int i=0; i<getBouquetList().size(); i++) { //Schleife ueber die Bouquets
				BOBouquet bouquet = (BOBouquet)this.getBouquetList().get(i);
				bouquet.readSender();
				for (int i2=0; i2<bouquet.getSender().size(); i2++) { //Schleife ueber die Sender im Bouquet
					BOSender sender = (BOSender)bouquet.getSender().get(i2);
					if (sender.getChanId().equals(runningChanId) && this.getSelectedSender() == null) {
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
		if (action == "playback") {
			this.actionPlayback();
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
		if (action == "startServer"){
			this.actionStreamingServer();
		}
	}
		
	/*
	 * Steuerung der 2 Zustaende. 
	 * Aufnahme läuft bereits ->stop
	 * Aufnahme läuft nicht->start
	 */
	private void actionRecord() {
		try {
			if (recordControl ==  null || !recordControl.isRunning) {
				BORecordArgs args;
				if (this.isTvMode()) {
					args = this.buildTVRecordArgs();
				} else {
					args = this.buildRadioRecordArgs();
				}
				this.startRecord(args);                          
			} else {
				this.stopRecord();
			}
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("ControlProgrammTab", this.getMainView());
		}
	}
	/*
	 * TV-Wiedergabe des laufenden Senders
	 */
	private void actionPlayback() {
		try {
			this.zapToSelectedSender();
			String vPid = "0x"+(String)this.getPids().get(0);
			String aPid = "0x"+(String)this.getPids().get(1);
			String ip = ControlMain.getBoxIpOfActiveBox();
			String execString =  ControlMain.getSettings().getPlaybackString();
			
			execString = SerFormatter.replace(execString, "$ip",  ip);
			execString = SerFormatter.replace(execString, "$vPid", vPid);
			execString = SerFormatter.replace(execString, "$aPid", aPid);
			
			Process p = Runtime.getRuntime().exec(execString);
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("ControlProgrammTab", this.getMainView());
		}
	}
	
	/**
	 * Stop der Aufnahme und Versetzung der GUI in den Aufnahme-Warte-Modus
	 */
	public void stopRecord() {
		if (recordControl != null) {
			recordControl.stopRecord();
		}
		this.getMainView().getTabProgramm().stopRecordModus();
	}
	
	/**
	 * @param recordArgsl
	 * Start der Aufnahme und Versetzung der GUI in den Aufnahme-Modus
	 */
	public void startRecord(BORecordArgs recordArgs) {
		this.setRecordArgs(recordArgs);
		recordControl = new RecordControl(recordArgs, this);
		this.getMainView().getTabProgramm().startRecordModus();
		recordControl.start();
	}
	

	/**
	 * @return BORecordArgs
	 * Erstellen des Objektes BORecordArgs aus den Sender und EPG-Informationen
	 */
	private BORecordArgs buildRadioRecordArgs() throws IOException {
		BORecordArgs args = new BORecordArgs();
		this.zapToSelectedSender();
		
		ArrayList pidList = new ArrayList();
			
		String[] aPids = new String[this.getPids().size()-1];
		for (int i=0; i<this.getPids().size()-1; i++) {
			aPids[i]=(String)this.getPids().get(i+1);
		}
		pidList.add(aPids);
		args.setAPids(pidList);
			
		this.fillRecordArgsWithEpgData(args);
		return args;
	}
	
	private BORecordArgs buildTVRecordArgs() throws IOException {
		this.zapToSelectedSender();
		BORecordArgs args = new BORecordArgs();
		args.setVPid((String)this.getPids().get(0));
		
		ArrayList pidList = new ArrayList();
		
		String[] aPids = new String[this.getPids().size()-1];
		for (int i=0; i<this.getPids().size()-1; i++) {
			aPids[i]=(String)this.getPids().get(i+1);
		}
		pidList.add(aPids);
		args.setAPids(pidList);

		this.fillRecordArgsWithEpgData(args);
		return args;
	}
	
	private void fillRecordArgsWithEpgData(BORecordArgs args) throws IOException {
		args.setSenderName(this.getSelectedSender().getName());
		BOEpg epg = this.getSelectedSender().getRunnigEpgWithUpdate();
		if (epg != null) {
			String title = epg.getTitle();
			args.setEpgTitle(title.substring(0, title.length()-1));
		}
	}
	
	/*
	 * Zapping zum selektierten Sender und Ermittlung der Pids
	 */
	private void zapToSelectedSender() throws IOException{
		if (ControlMain.getBoxAccess().zapTo(this.getSelectedSender().getChanId()).equals("ok")) {
			this.setPids(ControlMain.getBoxAccess().getPids());
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
					this.zapToSelectedSender();
				}
			}
			//Neue Epg-Zeile selektiert
			if (tableName == "Epg") {
				int selectedRow = table.getSelectedRow();		
				int modelIndex = this.getMainView().getTabProgramm().sorter.modelIndex(selectedRow);
				this.setSelectedEpg((BOEpg)this.getEpgTableModel().getEpgList().get(modelIndex)); 
				if (me.getClickCount()==2) {
					BOTimer timer = this.buildTimer(this.getSelectedEpg());
					if (ControlMain.getBoxAccess().writeTimer(timer).equals("ok")) {
						Logger.getLogger("ControlProgramTab").info("Timer übertragen "+timer.getInfo());
					} else {
						Logger.getLogger("ControlProgramTab").error(timer.getInfo());
					}
				}
			}
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
		if (e.getStateChange()==1) {
			JComboBox comboBox = (JComboBox)e.getSource();
			if (comboBox.getName().equals("ipList")) {
				this.newBoxSelected(comboBox);		}
			if (comboBox.getName().equals("bouquets")) {
				this.reInitBouquetList(comboBox);
			}
		}
	}
	
	public void stateChanged(ChangeEvent event) {
		JSpinner stopTimeSpinner = (JSpinner)event.getSource();
		Date stopTime = (Date)stopTimeSpinner.getModel().getValue();
		if (this.getRecordControl()!=null) {
			this.getRecordControl().stopTime = stopTime;
		}
	}
	
	/**
	 * @param boxIpComboBox
	 * Setzen der neuen aktiven Box-IP
	 * Ermitteln des laufenden Images der neuen Box
	 * Reinitialisierung der Programm-GUI
	 * Reinitialisierung der Timer-GUI
	 */
	private void newBoxSelected(JComboBox boxIpComboBox) {
		BOBox newSelectedBox = (BOBox)ControlMain.getSettings().getBoxList().get(boxIpComboBox.getSelectedIndex());
		BOBox oldSelectedBox = ControlMain.getActiveBox();
		if (oldSelectedBox==null || //Konstellation möglich, wenn erste Box angelegt wird 
				oldSelectedBox.isSelected() != newSelectedBox.isSelected())  { 
			if (oldSelectedBox!=null) {
				oldSelectedBox.setSelected(false); //alte Box zurücksetzen!
			}	
			ControlMain.setActiveBox(newSelectedBox);
			newSelectedBox.setSelected(true);
			ControlMain.detectImage();
			this.reInitialize();
			this.getMainView().getMainTabPane().reInitTimerPanel(); //Bei IP-Wechsel refreshen, da evtl anderes Box-Image
		}
	}
	
	/**
	 * @param bouquetsComboBox
	 * Setzen des aktuellen Bouquets, refresh der Senderlist,
	 * Selektion des 1. Senders
	 */
	public void reInitBouquetList(JComboBox  bouquetsComboBox) {
		if (this.getBouquetList().size()>0) {
			this.setSelectedBouquet((BOBouquet)this.getBouquetList().get(bouquetsComboBox.getSelectedIndex()));
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
		int indexRunningEpg = this.getEpgTableModel().getIndexRunningEpg();
		if (indexRunningEpg >= 0) {
			this.getMainView().getTabProgramm().sorter.setSortingStatus(2, 0);
			this.getMainView().getTabProgramm().sorter.setSortingStatus(3, 0);
			this.getMainView().getTabProgramm().sorter.setSortingStatus(4, 0);
			this.getMainView().getTabProgramm().sorter.setSortingStatus(1, 1); //Sortierung zuruecksetzen
			this.getMainView().getTabProgramm().getJTableEPG().setRowSelectionInterval(indexRunningEpg, indexRunningEpg);
			BOEpg selEpg = (BOEpg)this.getSelectedSender().getEpg().get(indexRunningEpg);
			this.setSelectedEpg(selEpg);
		}
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
	 */
	public void setSelectedEpg(BOEpg epg) {
		this.selectedEpg = epg;
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
						if (ControlMain.getBoxAccess().writeTimer(timer).equals("ok")) {
							Logger.getLogger("ControlProgramTab").info("Timer übertragen "+timer.getInfo());
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
	
	/**
	 * @param epg
	 * @return BOTimer
	 * Erstellein eines BOTimer-Objekts aus den EPG-Informationen
	 */
	private BOTimer buildTimer(BOEpg epg) {
		BOTimer timer = new BOTimer();
		
		long unformattedStart = Long.parseLong(epg.getUnformattedStart());
		long unformattedDuration = Long.parseLong(epg.getUnformattedDuration());
		long endtime = unformattedStart+unformattedDuration;
		long announce = unformattedStart-(60*1000);
		
		timer.setModifiedId("new");
		timer.setChannelId(this.getSelectedSender().getChanId());
		timer.setSenderName(this.getSelectedSender().getName());
		timer.setAnnounceTime(Long.toString(announce)); //Vorwarnzeit
		timer.setUnformattedStartTime(SerFormatter.formatUnixDate(unformattedStart));
		timer.setUnformattedStopTime(SerFormatter.formatUnixDate(endtime));
		
		timer.setEventRepeatId("0");
		timer.setEventTypeId("5");
		timer.setDescription(epg.getTitle());
		return timer;
	}
	
	private void startStreamingSever() {
		int port = Integer.parseInt(ControlMain.getSettings().getStreamingServerPort());
		setStreamingServerThread(new SerStreamingServer(port, this));
		getStreamingServerThread().start();
		this.getMainView().getTabProgramm().startStreamingServerModus();
	}
	
	private void stopStreamingServer() {
		if (streamingServerThread!=null) {
			streamingServerThread.stopServer();
			this.getMainView().getTabProgramm().stopStreamingServerModus();
			streamingServerThread=null;
		}
	}
	
	private void reInitStreamingServer() {
		if (ControlMain.getSettings().isStartStreamingServer()) {
			startStreamingSever();
		}
	}
	/*
	 * Kontroller der 2 Zustaende
	 * Streamingserver on>off, off>on
	 */
	private void actionStreamingServer() {
		if (streamingServerThread!=null) {
			this.stopStreamingServer();
		} else {
			this.startStreamingSever();
		}
	}
	
	public void setRecordStoptTime(Date time) {
		GuiTabProgramm tabProg = this.getMainView().getTabProgramm();
		tabProg.getDateModelSpinnerStopTime().setValue(time);
	}
	
	public Date getRecordStopTime() {
		return this.getMainView().getTabProgramm().getDateModelSpinnerStopTime().getDate();	
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
	 * @param dateChooserDate
	 * Methode wird aufgerufen wenn Datum im DateChooser geaendert wurde
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
	
	private JTable getJTableSender() {
		return this.getMainView().getTabProgramm().getJTableChannels();
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
	 * @return Returns the streamingServerThread.
	 */
	public SerStreamingServer getStreamingServerThread() {
		return streamingServerThread;
	}
	/**
	 * @param streamingServerThread The streamingServerThread to set.
	 */
	public void setStreamingServerThread(
			SerStreamingServer streamingServerThread) {
		this.streamingServerThread = streamingServerThread;
	}
	/**
	 * @return Returns the recordArgs.
	 */
	public BORecordArgs getRecordArgs() {
		return recordArgs;
	}
	/**
	 * @param recordArgs The recordArgs to set.
	 */
	public void setRecordArgs(BORecordArgs recordArgs) {
		this.recordArgs = recordArgs;
	}
	/**
	 * @return Returns the tvMode.
	 */
	public boolean isTvMode() {
		return tvMode;
	}
	/**
	 * @param tvMode The tvMode to set.
	 */
	public void setTvMode(boolean tvMode) {
		this.tvMode = tvMode;
	}
}
