/*
 * Created on 11.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

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

import calendar.JDateChooser;

import control.ControlProgramTab;

/**
 * @author Alexander Geist
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GuiTabProgramm extends GuiTab {
	
	private JTabbedPane jControlTab = null;
	private JPanel tabProgramm = null;
	private JPanel jPanelProgramm = null;
	private JPanel jPanelAktionen = null;
	private JPanel jPanelButtonsProgrammInfo = null;
	private JPanel jPanelProgrammInfo = null;
	private JPanel jPanelAusgabe = null;
	private JPanel jPanelChannel = null;
	private JPanel jPanelEPGTable = null;
	private JComboBox jComboChooseDate = null;
	private JTable jTableEPG = null;
	private JScrollPane jScrollPaneEPG = null;
	private JButton jButtonQuickRecord = null;
	private JButton jButtonReboot = null;
	private JButton jButtonVLC = null;
	private JButton jButtonNhttpdReset = null;
	private JButton jButtonEPGReset = null;
	private JSpinner jSpinnerRecMin = null;
	private JButton jButtonToTimer = null;
	private JButton jButtonStartServer = null;
	private JButton jButtonReadEPG = null;
	private JButton jButtonClickfinder = null;
	private JTextArea jTextAreaEPG = null;
	private JComboBox jComboBoxBoxIP = null;
	public JTextArea jTextPaneAusgabe = null;
	public GuiSenderTableModel senderTableModel;
	public GuiBoquetsComboModel boquetsComboModel;
	public GuiEpgTableModel epgTableModel;
	private JScrollPane jScrollPane = null;
	public JTable jTableChannels = null;
	private ControlProgramTab control;
	private JComboBox JComboBoxBouquets = null;
	private JDateChooser jDateChooser = null;
	private JScrollPane jScrollPaneEPGDetail = null;
	private JScrollPane jScrollPaneAusgabe = null;
	
	public GuiTabProgramm(ControlProgramTab control) {
		this.setControl(control);
		initialize();
	}
	
	private void initialize(){
		this.setLayout(new GridBagLayout());
		
		java.awt.GridBagConstraints gridBagConstraintsAusgabe = new GridBagConstraints();
		java.awt.GridBagConstraints gridBagConstraintsButtonsAktionen = new GridBagConstraints();
		java.awt.GridBagConstraints gridBagConstraintsButtonsInfo = new GridBagConstraints();
		java.awt.GridBagConstraints gridBagConstraintsProgram = new GridBagConstraints();
		java.awt.GridBagConstraints gridBagConstraintsProgramInfo = new GridBagConstraints();
		gridBagConstraintsProgram.gridx = 0;
		gridBagConstraintsProgram.gridy = 0;
		gridBagConstraintsProgram.anchor = java.awt.GridBagConstraints.CENTER;
		gridBagConstraintsProgram.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraintsProgram.gridwidth = 6;
		gridBagConstraintsButtonsAktionen.gridx = 0;
		gridBagConstraintsButtonsAktionen.gridy = 3;
		gridBagConstraintsButtonsInfo.gridx = 1;
		gridBagConstraintsButtonsInfo.gridy = 3;
		gridBagConstraintsProgramInfo.gridx = 2;
		gridBagConstraintsProgramInfo.gridy = 3;
		gridBagConstraintsProgramInfo.gridheight = 2;
		gridBagConstraintsProgramInfo.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraintsAusgabe.gridx = 0;
		gridBagConstraintsAusgabe.gridy = 4;
		gridBagConstraintsAusgabe.gridwidth = 2;
		gridBagConstraintsAusgabe.fill = java.awt.GridBagConstraints.BOTH;
		//this.setPreferredSize(new java.awt.Dimension(630,400));
		this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		this.add(getJPanelProgramm(), gridBagConstraintsProgram);
		this.add(getJPanelAktionen(), gridBagConstraintsButtonsAktionen);
		this.add(getJPanelButtonsInformationen(), gridBagConstraintsButtonsInfo);
		this.add(getJPanelProgramInfo(), gridBagConstraintsProgramInfo);
		this.add(getJPanelAusgabe(), gridBagConstraintsAusgabe);
	}
	
	private JPanel getJPanelProgramm() {
		if (jPanelProgramm == null) {
			java.awt.GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			jPanelProgramm = new JPanel();
			jPanelProgramm.setLayout(new GridBagLayout());
			jPanelProgramm.setPreferredSize(new java.awt.Dimension(770,275));
			jPanelProgramm.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED)));
			gridBagConstraints13.gridx = 0;
			gridBagConstraints13.gridy = 0;
			gridBagConstraints13.fill = java.awt.GridBagConstraints.VERTICAL;
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints1.anchor = java.awt.GridBagConstraints.CENTER;
			jPanelProgramm.add(getJPanelChannels(), gridBagConstraints13);
			jPanelProgramm.add(getJPanelEPG(), gridBagConstraints1);
			
		}
		return jPanelProgramm;
	}
	/**
	 * This method initializes jPanelAktionen	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanelAktionen() {
		if (jPanelAktionen == null) {
			java.awt.GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			javax.swing.JLabel jLabel = new JLabel();
			java.awt.GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints131 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints101 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints91 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			jPanelAktionen = new JPanel();
			jPanelAktionen.setLayout(new GridBagLayout());
			jPanelAktionen.setPreferredSize(new java.awt.Dimension(225,110));
			jPanelAktionen.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Aktionen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.gridy = 0;
			gridBagConstraints8.insets = new java.awt.Insets(0,0,0,0);
			gridBagConstraints91.gridx = 0;
			gridBagConstraints91.gridy = 1;
			gridBagConstraints91.insets = new java.awt.Insets(0,0,0,0);
			gridBagConstraints101.gridx = 0;
			gridBagConstraints101.gridy = 2;
			gridBagConstraints131.gridx = 1;
			gridBagConstraints131.gridy = 1;
			gridBagConstraints131.gridwidth = 3;
			gridBagConstraints14.gridx = 1;
			gridBagConstraints14.gridy = 2;
			gridBagConstraints14.gridwidth = 3;
			gridBagConstraints15.gridx = 1;
			gridBagConstraints15.gridy = 0;
			gridBagConstraints15.weightx = 1.0;
			gridBagConstraints15.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints15.insets = new java.awt.Insets(0,5,0,5);
			gridBagConstraints17.gridx = 3;
			gridBagConstraints17.gridy = 0;
			gridBagConstraints17.insets = new java.awt.Insets(0,0,0,5);
			jLabel.setText("Min");
			jPanelAktionen.add(getJButtonAufnahme(), gridBagConstraints8);
			jPanelAktionen.add(getJButtonReboot(), gridBagConstraints131);
			jPanelAktionen.add(getJButtonNhttpdReset(), gridBagConstraints91);
			jPanelAktionen.add(getJButtonEpgReset(), gridBagConstraints14);
			jPanelAktionen.add(getJSpinner(), gridBagConstraints15);
			jPanelAktionen.add(jLabel, gridBagConstraints17);
			jPanelAktionen.add(getJButtonVLC(), gridBagConstraints101);
		}
		return jPanelAktionen;
	}
	
	private JPanel getJPanelButtonsInformationen() {
		if (jPanelButtonsProgrammInfo == null) {
			java.awt.GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints131 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints101 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints91 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			jPanelButtonsProgrammInfo = new JPanel();
			jPanelButtonsProgrammInfo.setLayout(new GridBagLayout());
			jPanelButtonsProgrammInfo.setPreferredSize(new java.awt.Dimension(225,110));
			jPanelButtonsProgrammInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Informationen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.gridy = 0;
			gridBagConstraints91.gridx = 0;
			gridBagConstraints91.gridy = 1;
			gridBagConstraints101.gridx = 0;
			gridBagConstraints101.gridy = 2;
			gridBagConstraints15.gridx = 1;
			gridBagConstraints15.gridy = 0;
			gridBagConstraints131.gridx = 1;
			gridBagConstraints131.gridy = 1;
			gridBagConstraints14.gridx = 1;
			gridBagConstraints14.gridy = 2;
			jPanelButtonsProgrammInfo.add(new JLabel("IP der Box:"), gridBagConstraints8);
			jPanelButtonsProgrammInfo.add(this.getJButtonReadEPG(), gridBagConstraints131);
			jPanelButtonsProgrammInfo.add(this.getJButtonClickfinder(), gridBagConstraints91);
			jPanelButtonsProgrammInfo.add(this.getJButtonSelectedToTimer(), gridBagConstraints14);
			jPanelButtonsProgrammInfo.add(this.getJComboBoxBoxIP(), gridBagConstraints15);
			jPanelButtonsProgrammInfo.add(this.getJButtonStartServer(), gridBagConstraints101);
		}
		return jPanelButtonsProgrammInfo;
	}
	
	private JPanel getJPanelAusgabe() {
		if (jPanelAusgabe == null) {
			jPanelAusgabe = new JPanel();
			jPanelAusgabe.setLayout(new GridLayout(1,1));
			jPanelAusgabe.setPreferredSize(new java.awt.Dimension(200,110));
			jPanelAusgabe.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Ausgabe", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jPanelAusgabe.add(getJScrollPaneAusgabe());
		}
		return jPanelAusgabe;
	}

	private JPanel getJPanelChannels() {
		if (jPanelChannel == null) {
			java.awt.GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			jPanelChannel = new JPanel();
			jPanelChannel.setLayout(new GridBagLayout());
			jPanelChannel.setPreferredSize(new java.awt.Dimension(250,245));
			jPanelChannel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Sender, Doppelklick Zapping", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			gridBagConstraints16.gridx = 0;
			gridBagConstraints16.gridy = 3;
			gridBagConstraints16.weightx = 1.0;
			gridBagConstraints16.weighty = 1.0;
			gridBagConstraints16.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints16.gridwidth = 4;
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 1;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints7.gridwidth = 4;
			gridBagConstraints12.gridx = 3;
			gridBagConstraints12.gridy = 0;
			gridBagConstraints12.fill = java.awt.GridBagConstraints.BOTH;
			jPanelChannel.add(getJScrollPaneChannels(), gridBagConstraints16);
			jPanelChannel.add(getJComboBoxBouquets(), gridBagConstraints7);
			jPanelChannel.add(getJDateChooser(), gridBagConstraints12);
		}
		return jPanelChannel;
	}
	
	private JPanel getJPanelEPG() {
		if (jPanelEPGTable == null) {
			java.awt.GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
			jPanelEPGTable = new JPanel();
			jPanelEPGTable.setLayout(new GridBagLayout());
			jPanelEPGTable.setPreferredSize(new java.awt.Dimension(500,245));
			jPanelEPGTable.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Programm, Doppelklick zu Timer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			gridBagConstraints71.gridx = 0;
			gridBagConstraints71.gridy = 0;
			gridBagConstraints71.weightx = 1.0;
			gridBagConstraints71.weighty = 1.0;
			gridBagConstraints71.fill = java.awt.GridBagConstraints.BOTH;
			jPanelEPGTable.add(getJScrollPaneEPG(), gridBagConstraints71);
		}
		return jPanelEPGTable;
	}
	
	private JPanel getJPanelProgramInfo() {
		if (jPanelProgrammInfo == null) {
			java.awt.GridBagConstraints gridBagConstraints92 = new GridBagConstraints();	
			jPanelProgrammInfo = new JPanel();		
			jPanelProgrammInfo.setLayout(new GridBagLayout());
			jPanelProgrammInfo.setPreferredSize(new java.awt.Dimension(320,100));
			jPanelProgrammInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Programm-Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			gridBagConstraints92.gridx = 0;
			gridBagConstraints92.gridy = 0;
			gridBagConstraints92.weightx = 1.0;
			gridBagConstraints92.weighty = 1.0;
			gridBagConstraints92.fill = java.awt.GridBagConstraints.BOTH;
			jPanelProgrammInfo.add(this.getJScrollPaneEPGDetail(), gridBagConstraints92);
		}
		return jPanelProgrammInfo;
	}
	/**
	 * This method initializes jComboBoxBoxIP	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getJComboBoxBoxIP() {
		if (jComboBoxBoxIP == null) {
			jComboBoxBoxIP = new JComboBox();
			jComboBoxBoxIP.setMaximumSize(new java.awt.Dimension(105,24));
			jComboBoxBoxIP.setEditable(true);
			jComboBoxBoxIP.setPreferredSize(new java.awt.Dimension(105,24));
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
			jTableEPG = new JTable(epgTableModel);
			jTableEPG.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
			jScrollPaneEPG.setAutoscrolls(true);
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
	private JButton getJButtonAufnahme() {
		if (jButtonQuickRecord == null) {
			jButtonQuickRecord = new JButton();
			jButtonQuickRecord.setPreferredSize(new java.awt.Dimension(105,25));
			jButtonQuickRecord.setText("Aufnahme");
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
	private JButton getJButtonReboot() {
		if (jButtonReboot == null) {
			jButtonReboot = new JButton();
			jButtonReboot.setPreferredSize(new java.awt.Dimension(105,25));
			jButtonReboot.setText("Box Reboot");
			jButtonReboot.setToolTipText("Box neu starten");
			jButtonReboot.addActionListener(this.getControl());
		}
		return jButtonReboot;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButtonVLC() {
		if (jButtonVLC == null) {
			jButtonVLC = new JButton();
			jButtonVLC.setPreferredSize(new java.awt.Dimension(105,25));
			jButtonVLC.setText("VLC-Play");
			jButtonVLC.setToolTipText("AKtuelles Programm im VLC abspielen.");
			jButtonVLC.addActionListener(this.getControl());
		}
		return jButtonVLC;
	}
	/**
	 * This method initializes jButtonNhttpdReset	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButtonNhttpdReset() {
		if (jButtonNhttpdReset == null) {
			jButtonNhttpdReset = new JButton();
			jButtonNhttpdReset.setPreferredSize(new java.awt.Dimension(105,25));
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
	private JButton getJButtonEpgReset() {
		if (jButtonEPGReset == null) {
			jButtonEPGReset = new JButton();
			jButtonEPGReset.setPreferredSize(new java.awt.Dimension(105,25));
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
	private JSpinner getJSpinner() {
		if (jSpinnerRecMin == null) {
			jSpinnerRecMin = new JSpinner();
			jSpinnerRecMin.setToolTipText("Sofortaufnahme beenden nach...");
		}
		return jSpinnerRecMin;
	}
	/**
	 * This method initializes jButtonToTimer	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButtonSelectedToTimer() {
		if (jButtonToTimer == null) {
			jButtonToTimer = new JButton();
			jButtonToTimer.setPreferredSize(new java.awt.Dimension(105,25));
			jButtonToTimer.setText("add to timer");
			jButtonToTimer.setToolTipText("ausgewählte Dateien zum Timer hinzufügen.");
			jButtonToTimer.addActionListener(this.getControl());
		}
		return jButtonToTimer;
	}
	/**
	 * This method initializes jButtonStartServer	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButtonStartServer() {
		if (jButtonStartServer == null) {
			jButtonStartServer = new JButton();
			jButtonStartServer.setPreferredSize(new java.awt.Dimension(105,25));
			jButtonStartServer.setText("Start Server");
			jButtonStartServer.setToolTipText("Streamingserver starten");
			jButtonStartServer.addActionListener(this.getControl());
		}
		return jButtonStartServer;
	}
	/**
	 * This method initializes jButtonReadEPG	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButtonReadEPG() {
		if (jButtonReadEPG == null) {
			jButtonReadEPG = new JButton();
			jButtonReadEPG.setPreferredSize(new java.awt.Dimension(105,25));
			jButtonReadEPG.setText("EPG lesen");
			jButtonReadEPG.addActionListener(this.getControl());
		}
		return jButtonReadEPG;
	}
	/**
	 * This method initializes jButtonClickfinder	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButtonClickfinder() {
		if (jButtonClickfinder == null) {
			jButtonClickfinder = new JButton();
			jButtonClickfinder.setPreferredSize(new java.awt.Dimension(105,25));
			jButtonClickfinder.setText("Clickfinder");
			jButtonClickfinder.setToolTipText("");
			jButtonClickfinder.addActionListener(this.getControl());
		}
		return jButtonClickfinder;
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
			jScrollPaneAusgabe.setAutoscrolls(false);
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
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
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
	private JTable getJTableChannels() {
		if (jTableChannels == null) {
			senderTableModel = new GuiSenderTableModel(control);
			jTableChannels = new JTable(senderTableModel);
			jTableChannels.getColumnModel().getColumn(0).setPreferredWidth(40);
			jTableChannels.getColumnModel().getColumn(1).setPreferredWidth(200);
			jTableChannels.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTableChannels.addMouseListener(control);
			jTableChannels.setName("Sender");
		}
		return jTableChannels;
	}
	
	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	public JComboBox getJComboBoxBouquets() {
	      if (JComboBoxBouquets == null) {
	         boquetsComboModel = new GuiBoquetsComboModel(control);
	         JComboBoxBouquets = new JComboBox(boquetsComboModel);
	         JComboBoxBouquets.addItemListener(control);
	         if (this.getControl().getBouquetList().size()>0) {
	            JComboBoxBouquets.setSelectedIndex(0);
	         }
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
	 * Es wird das Control ControlProgramTab als "Listener" ï¿½bergeben
	 * wird das Datum geï¿½ndert wird das aktuelle Datum automatisch
	 * ï¿½ber setDateChooserDate(Date) gesetzt
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
  }
