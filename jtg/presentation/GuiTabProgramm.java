package presentation;
/*
GuiTabProgramm.java by Geist Alexander 

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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.table.TableColumn;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import calendar.JDateChooser;

import control.ControlProgramTab;

public class GuiTabProgramm extends GuiTab {
	
	private JTabbedPane jControlTab = null;
	private JPanel tabProgramm = null;
	private JPanel jPanelProgramm = null;
	private JPanel jPanelButtonsAktionen = null;
	private JPanel jPanelButtonsProgrammInfo = null;
	private JPanel jPanelProgrammInfo = null;
	private JPanel jPanelAusgabe = null;
	private JPanel jPanelChannel = null;
	private JPanel jPanelEPGTable = null;
	private JPanel jPanelRecordInfo = null;
	private JComboBox jComboChooseDate = null;
	private JTable jTableEPG = null;
	private JScrollPane jScrollPaneEPG = null;
	private JButton jButtonQuickRecord = null;
	private JButton jButtonReboot = null;
	private JButton jButtonPlayback = null;
	private JButton jButtonNhttpdReset = null;
	private JButton jButtonEPGReset = null;
	private JButton jButtonToTimer = null;
	private JButton jButtonStartServer = null;
	private JTextArea jTextAreaEPG = null;
	private JSpinner jSpinnerRecordStopTime;
	private JComboBox jComboBoxBoxIP = null;
	public JTextArea jTextPaneAusgabe = null;
	public GuiSenderTableModel senderTableModel;
	public GuiBoquetsComboModel boquetsComboModel;
	public GuiEpgTableModel epgTableModel;
	private JScrollPane jScrollPane = null;
	private JTable jTableChannels = null;
	private ControlProgramTab control;
	private JComboBox JComboBoxBouquets = null;
	private JDateChooser jDateChooser = null;
	private JScrollPane jScrollPaneEPGDetail = null;
	private JScrollPane jScrollPaneAusgabe = null;
	public GuiEpgTableSorter sorter = null;
	private SpinnerDateModel dateModelSpinnerStopTime;
	
	public GuiTabProgramm(ControlProgramTab control) {
		this.setControl(control);
		initialize();
		this.setDisconnectModus();
	}
	
	private  void initialize() {
		FormLayout layout = new FormLayout(
			      "pref, 10, 150, 10, pref, 10, pref, 10, 250:grow",  							// columns 
			      "pref, 263px:grow, 10, pref, pref, 3dlu, pref, 100px:grow");	// rows
		PanelBuilder builder = new PanelBuilder(this, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();
	
		builder.addSeparator("Datum",		   							cc.xywh	(1, 1, 1, 1));
		builder.add(this.getJPanelChannels(),  						cc.xywh	(1, 2, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
		builder.addSeparator("EPG",			   							cc.xywh	(3, 1, 7, 1));
		builder.add(this.getJScrollPaneEPG(),							cc.xywh	(3, 2, 7, 1));
		builder.addSeparator("Aktionen",								cc.xywh	(1, 4, 5, 1, CellConstraints.FILL, CellConstraints.FILL));
		builder.add(this.getJPanelButtonsAktionen(),  			cc.xywh	(1, 5, 5, 1, CellConstraints.FILL, CellConstraints.FILL));
		builder.addSeparator("Record-Info",							cc.xywh	(7, 4, 1, 1));
		builder.add(this.getJPanelRecordInfo(), 				    	cc.xywh	(7, 5, 1, 1));
		builder.addSeparator("EPG-Details",							cc.xywh	(9, 4, 1, 1));
		builder.add(this.getJScrollPaneEPGDetail(),	 				cc.xywh	(9, 5, 1, 4));
		builder.addSeparator("Ausgabe",								cc.xywh	(1, 7, 7, 1));
		builder.add(this.getJScrollPaneAusgabe(), 	 				cc.xywh	(1, 8, 7, 1, CellConstraints.FILL, CellConstraints.FILL));
	}
	
	/**
	 * This method initializes jPanelButtonsAktionen	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanelButtonsAktionen() {
		if (jPanelButtonsAktionen == null) {
			jPanelButtonsAktionen = new JPanel();
			FormLayout layout = new FormLayout(
				      "pref, 1dlu, pref, 1dlu, pref",	 		//columna 
				      "pref, 1dlu, pref");	//rows
			PanelBuilder builder = new PanelBuilder(jPanelButtonsAktionen, layout);
			CellConstraints cc = new CellConstraints();
			
			builder.add(this.getJButtonStartServer(),					cc.xyw	(1, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJButtonPlayback(),		  					cc.xyw	(3, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJButtonAufnahme(),	  					cc.xyw	(5, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJButtonReboot(), 	 						cc.xyw	(1, 3, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJButtonNhttpdReset(),  					cc.xyw	(3, 3, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJButtonEpgReset(),	  					cc.xyw	(5, 3, 1, CellConstraints.FILL, CellConstraints.FILL));
		}
		return jPanelButtonsAktionen;
	}
	
	private JPanel getJPanelRecordInfo() {
		if (jPanelRecordInfo == null) {
			jPanelRecordInfo = new JPanel();
			FormLayout layout = new FormLayout(
				      "pref",	 		//columna 
				      "pref, 1dlu, 25");	//rows
			PanelBuilder builder = new PanelBuilder(jPanelRecordInfo, layout);
			CellConstraints cc = new CellConstraints();
			
			builder.add(new JLabel("Sofortaufnahme Stop-Zeit"),	  	cc.xy(1, 1));		
			builder.add(this.getJSpinnerRecordStopTime(),	 			cc.xy(1, 3));
		}
		return jPanelRecordInfo;
	}
	
	private JPanel getJPanelChannels() {
		if (jPanelChannel == null) {
			jPanelChannel = new JPanel();
			FormLayout layout = new FormLayout(
				      "180",									//column 
				      "pref, 4px, pref, pref, min:grow, pref, pref");		//rows
			PanelBuilder builder = new PanelBuilder(jPanelChannel, layout);
			CellConstraints cc = new CellConstraints();
			
			builder.add(this.getJDateChooser(),		  						cc.xyw	(1, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.addSeparator("Sender, Doppelklick Zapping",		cc.xyw	(1, 3, 1));
			builder.add(this.getJComboBoxBouquets(), 					cc.xyw	(1, 4, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJScrollPaneChannels(), 						cc.xyw	(1, 5, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJComboBoxBoxIP(), 							cc.xyw	(1, 6, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJButtonSelectedToTimer(), 				cc.xyw	(1, 7, 1, CellConstraints.FILL, CellConstraints.FILL));
		}
		return jPanelChannel;
	}
	
	/**
	 * This method initializes jComboBoxBoxIP	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	public JComboBox getJComboBoxBoxIP() {
		if (jComboBoxBoxIP == null) {
			jComboBoxBoxIP = new JComboBox();
			jComboBoxBoxIP.setEditable(true);
			jComboBoxBoxIP.setModel(new GuiIpListComboModel());
			jComboBoxBoxIP.addItemListener(control);
			jComboBoxBoxIP.setEditable(false);
			jComboBoxBoxIP.setName("ipList");
		}
		return jComboBoxBoxIP;
	}
	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */    
	public JTable getJTableEPG() {
		if (jTableEPG == null) {
			epgTableModel = new GuiEpgTableModel(control);
			sorter = new GuiEpgTableSorter(epgTableModel);
			jTableEPG = new JTable(sorter);
			sorter.setTableHeader(jTableEPG.getTableHeader());
			
			TableColumn eventIdColumnt = jTableEPG.getColumnModel().getColumn(0);
			jTableEPG.getTableHeader().getColumnModel().removeColumn(eventIdColumnt); //eventId ausblenden 

			jTableEPG.getColumnModel().getColumn(0).setMaxWidth(50);
			jTableEPG.getColumnModel().getColumn(1).setMaxWidth(50);
			jTableEPG.getColumnModel().getColumn(2).setMaxWidth(60);
			jTableEPG.getColumnModel().getColumn(3).setPreferredWidth(280);
			jTableEPG.addMouseListener(control);
			jTableEPG.setName("Epg");
		}
		return jTableEPG;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPaneEPG() {
		if (jScrollPaneEPG == null) {
			jScrollPaneEPG = new JScrollPane();
			jScrollPaneEPG.setViewportView(getJTableEPG());
		}
		return jScrollPaneEPG;
	}
	
	private JScrollPane getJScrollPaneEPGDetail() {
		if (jScrollPaneEPGDetail == null) {
			jScrollPaneEPGDetail = new JScrollPane();
			jScrollPaneEPGDetail.setViewportView(getJTextAreaEPG());
		}
		return jScrollPaneEPGDetail;
	}
	
	/**
	 * This method initializes jButtonReboot	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	public JButton getJButtonAufnahme() {
		if (jButtonQuickRecord == null) {
			jButtonQuickRecord = new JButton();
			jButtonQuickRecord.setPreferredSize(new java.awt.Dimension(115,25));
			jButtonQuickRecord.setText("Record");
			jButtonQuickRecord.setActionCommand("record");
			jButtonQuickRecord.setToolTipText("Sofortaufnahme starten");
			jButtonQuickRecord.addActionListener(this.getControl());
		}
		return jButtonQuickRecord;
	}
	/**
	 * This method initializes jButtonReboot	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	public JButton getJButtonReboot() {
		if (jButtonReboot == null) {
			jButtonReboot = new JButton();
			jButtonReboot.setPreferredSize(new java.awt.Dimension(115,25));
			jButtonReboot.setText("Box Reboot");
			jButtonReboot.setToolTipText("Box neu starten");
			jButtonReboot.addActionListener(this.getControl());
		}
		return jButtonReboot;
	}
	/**
	 * This method initializes jButtonPlayback	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	public JButton getJButtonPlayback() {
		if (jButtonPlayback == null) {
			jButtonPlayback = new JButton();
			jButtonPlayback.setPreferredSize(new java.awt.Dimension(115,25));
			jButtonPlayback.setText("Playback");
			jButtonPlayback.setActionCommand("playback");
			jButtonPlayback.setToolTipText("AKtuelles Programm abspielen.");
			jButtonPlayback.addActionListener(this.getControl());
		}
		return jButtonPlayback;
	}
	/**
	 * This method initializes jButtonNhttpdReset	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	public JButton getJButtonNhttpdReset() {
		if (jButtonNhttpdReset == null) {
			jButtonNhttpdReset = new JButton();
			jButtonNhttpdReset.setPreferredSize(new java.awt.Dimension(115,25));
			jButtonNhttpdReset.setText("nhttpd reset");
			jButtonNhttpdReset.setToolTipText("nhttpd resetten");
			jButtonNhttpdReset.addActionListener(this.getControl());
		}
		return jButtonNhttpdReset;
	}
	/**
	 * This method initializes jButtonToTimer	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	public JButton getJButtonEpgReset() {
		if (jButtonEPGReset == null) {
			jButtonEPGReset = new JButton();
			jButtonEPGReset.setPreferredSize(new java.awt.Dimension(115,25));
			jButtonEPGReset.setText("EPG Reset");
			jButtonEPGReset.setToolTipText("EPG resetten");
			jButtonEPGReset.addActionListener(this.getControl());
		}
		return jButtonEPGReset;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	public JSpinner getJSpinnerRecordStopTime() {
		if (jSpinnerRecordStopTime == null) {
			dateModelSpinnerStopTime    = new SpinnerDateModel();
			jSpinnerRecordStopTime = new JSpinner(dateModelSpinnerStopTime);
			JSpinner.DateEditor dateEditor   = new JSpinner.DateEditor(jSpinnerRecordStopTime, "HH:mm - dd.MM.yyyy");			
			jSpinnerRecordStopTime.setEditor(dateEditor);
			jSpinnerRecordStopTime.setPreferredSize(new java.awt.Dimension(105,25));
			jSpinnerRecordStopTime.setToolTipText("Sofortaufnahme beenden um...");		
			jSpinnerRecordStopTime.addChangeListener(control);
		}
		return jSpinnerRecordStopTime;
	}
	/**
	 * This method initializes jButtonToTimer	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	public JButton getJButtonSelectedToTimer() {
		if (jButtonToTimer == null) {
			jButtonToTimer = new JButton();
			jButtonToTimer.setText("Selected to Timer");
			jButtonToTimer.setToolTipText("ausgewählte Programme zum Timer hinzufügen.");
			jButtonToTimer.addActionListener(this.getControl());
		}
		return jButtonToTimer;
	}
	/**
	 * This method initializes jButtonStartServer	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	public JButton getJButtonStartServer() {
		if (jButtonStartServer == null) {
			jButtonStartServer = new JButton();
			jButtonStartServer.setPreferredSize(new java.awt.Dimension(115,25));
			jButtonStartServer.setActionCommand("startServer");
			jButtonStartServer.setText("Start Server");
			jButtonStartServer.setToolTipText("Streamingserver starten");
			jButtonStartServer.addActionListener(this.getControl());
		}
		return jButtonStartServer;
	}	
	/**
	 * This method initializes jTextAreaEPG	
	 * 	
	 * @return javax.swing.JTextPane	
	 */    
	public JTextArea getJTextAreaEPG() {
		if (jTextAreaEPG == null) {
			jTextAreaEPG = new JTextArea();
			jTextAreaEPG.setEditable(false);
			jTextAreaEPG.setLineWrap(true);
			jTextAreaEPG.setWrapStyleWord(true);
			jTextAreaEPG.setAutoscrolls(true);
		}
		return jTextAreaEPG;
	}
	
	private JScrollPane getJScrollPaneAusgabe() {
		if (jScrollPaneAusgabe == null) {
			jScrollPaneAusgabe = new JScrollPane();
			jScrollPaneAusgabe.setViewportView(getJTextPaneAusgabe());
		}
		return jScrollPaneAusgabe;
	}
	/**
	 * This method initializes jTextPaneAusgabe	
	 * 	
	 * @return javax.swing.JTextPane	
	 */    
	public JTextArea getJTextPaneAusgabe() {
		if (jTextPaneAusgabe == null) {
			jTextPaneAusgabe = new JTextArea();
		}
		return jTextPaneAusgabe;
	}

	private JScrollPane getJScrollPaneChannels() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTableChannels());
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jTableChannels	
	 * 	
	 * @return javax.swing.JList	
	 */    
	public JTable getJTableChannels() {
		if (jTableChannels == null) {
			senderTableModel = new GuiSenderTableModel(control);
			jTableChannels = new JTable(senderTableModel);
			jTableChannels.getColumnModel().getColumn(0).setPreferredWidth(40);
			jTableChannels.getColumnModel().getColumn(1).setPreferredWidth(185);
			jTableChannels.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTableChannels.addMouseListener(control);
			jTableChannels.setName("Sender");
		}
		return jTableChannels;
	}
	   
	public JComboBox getJComboBoxBouquets() {
	      if (JComboBoxBouquets == null) {
	         boquetsComboModel = new GuiBoquetsComboModel(control);
	         JComboBoxBouquets = new JComboBox(boquetsComboModel);
	         JComboBoxBouquets.addItemListener(control);
	         JComboBoxBouquets.setName("bouquets");
	      }
	      return JComboBoxBouquets;
	   }
	/**
	 * @return Returns the boquetsComboModel.
	 */
	public GuiBoquetsComboModel getBoquetsComboModel() {
		return boquetsComboModel;
	}
	/**
	 * @param boquetsComboModel The boquetsComboModel to set.
	 */
	public void setBoquetsComboModel(
			GuiBoquetsComboModel guiBoquetsComboModel) {
		this.boquetsComboModel = guiBoquetsComboModel;
	}
	/**
	 * @return Returns the senderTableModel.
	 */
	public GuiSenderTableModel getSenderTableModel() {
		return senderTableModel;
	}
	/**
	 * @param senderTableModel The senderTableModel to set.
	 */
	public void setSenderTableModel(GuiSenderTableModel senderTableModel) {
		this.senderTableModel = senderTableModel;
	}
	/**
	 * This method initializes jDateChooser	
	 * 
	 * Achtung modifizierter DateChooser!!!	
	 * Es wird das Control ControlProgramTab als "Listener" uebergeben
	 * wird das Datum geaendert wird das aktuelle Datum automatisch
	 * ueber setDateChooserDate(Date) gesetzt
	 */    
	public JDateChooser getJDateChooser() {
	      if (jDateChooser == null) {
	         jDateChooser = new JDateChooser("d MMMMM, yyyy", false, control);
	      }
	      return jDateChooser;
	}
	
	/**
	 * @return Returns the epgTableModel.
	 */
	public GuiEpgTableModel getEpgTableModel() {
		return epgTableModel;
	}
	/**
	 * @param epgTableModel The epgTableModel to set.
	 */
	public void setEpgTableModel(GuiEpgTableModel epgTableModel) {
		this.epgTableModel = epgTableModel;
	}
	/**
	 * @return ControlProgramTab
	 */
	public ControlProgramTab getControl() {
		return control;
	}

	/**
	 * Sets the control.
	 * @param control The control to set
	 */
	public void setControl(ControlProgramTab control) {
		this.control = control;
	}
	public void setDisconnectModus() {
		this.getJButtonAufnahme().setEnabled(false);
		this.getJButtonEpgReset().setEnabled(false);
		this.getJButtonNhttpdReset().setEnabled(false);
		this.getJButtonReboot().setEnabled(false);
		this.getJButtonSelectedToTimer().setEnabled(false);
		this.getJButtonStartServer().setEnabled(false);
		this.getJButtonPlayback().setEnabled(false);
	}
	
	public void setConnectModus() {
		this.getJButtonAufnahme().setEnabled(true);
		this.getJButtonEpgReset().setEnabled(true);
		this.getJButtonNhttpdReset().setEnabled(true);
		this.getJButtonReboot().setEnabled(true);
		this.getJButtonSelectedToTimer().setEnabled(true);
		this.getJButtonStartServer().setEnabled(true);
		this.getJButtonPlayback().setEnabled(true);
	}
	
	public void stopStreamingServerModus() {
		this.getJButtonStartServer().setText("Start Server");
		this.getJButtonStartServer().setToolTipText("Streamingserver starten");
	}

	public void startStreamingServerModus() {
		this.getJButtonStartServer().setText("Stop Server");
		this.getJButtonStartServer().setToolTipText("Streamingserver stoppen");
	}
	/**
	 * Versetzen des Aufnahme-Buttons in den Aufnahme-Warte-Modus
	 */
	public void stopRecordModus() {
		this.getJButtonAufnahme().setText("Aufnahme");
		this.getJButtonAufnahme().setToolTipText("Sofortaufnahme starten");
	}
	
	/**
	 * Versetzen des Aufnahme-Buttons in den in den Aufnahme-Modus
	 */
	public void startRecordModus() {
		this.getJButtonAufnahme().setText("Stop");
		this.getJButtonAufnahme().setToolTipText("Sofortaufname stoppen");
	}
	/**
	 * @return Returns the dateModelSpinnerStopTime.
	 */
	public SpinnerDateModel getDateModelSpinnerStopTime() {
		return dateModelSpinnerStopTime;
	}
}
