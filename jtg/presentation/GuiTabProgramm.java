/*
 * Created on 11.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package presentation;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import calendar.JDateChooser;
import control.ControlProgramTab;

/**
 * @author AlexG
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GuiTabProgramm extends GuiTab {
	
	private JTabbedPane jControlTab = null;
	private JPanel tabProgramm = null;
	private JPanel jPanelProgramm = null;
	private JPanel jPanelEpgInfoButtons = null;
	private JPanel jPanelEpgInfo = null;
	private JPanel jPanelAusgabe = null;  //  @jve:decl-index=0:visual-constraint="10,11"
	private JPanel jPanelChannel = null;
	private JPanel jPanelEPGTime = null;
	private JComboBox jComboChooseDate = null;
	private JTable jTableEPG = null;
	private JScrollPane jScrollPaneEPG = null;
	private DefaultTableModel defaultTableModel = null;   //  @jve:decl-index=0:parse
	private JButton jButtonQuickRecord = null;
	private JButton jButtonReboot = null;
	private JButton jButton1 = null;
	private JButton jButtonNhttpdReset = null;
	private JButton jButtonEPGReset = null;
	private JSpinner jSpinnerRecMin = null;
	private JButton jButtonToTimer = null;
	private JButton jButtonStartServer = null;
	private JButton jButtonReadEPG = null;
	private JButton jButtonClickfinder = null;
	private JTextArea jTextAreaEPG = null;
	private JComboBox jComboBoxBoxIP = null;
	public JTextPane jTextPaneAusgabe = null;
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
	private JPanel jPanelAktionen = null; 
	
	public GuiTabProgramm(ControlProgramTab control) {
		this.setControl(control);
		initialize();
	}
	
	private  void initialize() {
		
		java.awt.GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
		java.awt.GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		java.awt.GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
		java.awt.GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
		java.awt.GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		
		this.setLayout(new GridBagLayout());
		
		gridBagConstraints7.gridx = 0;
		gridBagConstraints7.gridy = 0;
		gridBagConstraints7.anchor = java.awt.GridBagConstraints.CENTER;
		gridBagConstraints7.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints7.gridwidth = 4;
		gridBagConstraints9.gridx = 1;
		gridBagConstraints9.gridy = 4;
		gridBagConstraints9.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints9.gridwidth = 3;
		gridBagConstraints10.gridx = 0;
		gridBagConstraints10.gridy = 4;
		gridBagConstraints12.gridx = 2;
		gridBagConstraints12.gridy = 5;
		gridBagConstraints12.gridwidth = 2;
		gridBagConstraints12.fill = java.awt.GridBagConstraints.BOTH;
		//this.setPreferredSize(new java.awt.Dimension(630,400));
		this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		gridBagConstraints6.gridx = 0;
		gridBagConstraints6.gridy = 5;
		gridBagConstraints6.gridwidth = 2;
		this.add(getJPanelProgramm(), gridBagConstraints7);
		this.add(getJPanelEpgInfo(), gridBagConstraints9);
		this.add(getJPanelEpgInfoButtons(), gridBagConstraints10);
		this.add(getJPanelAusgabe(), gridBagConstraints12);
		this.add(getJPanelAktionen(), gridBagConstraints6);
	}
	
	private JPanel getJPanelProgramm() {
		if (jPanelProgramm == null) {
			java.awt.GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			jPanelProgramm = new JPanel();
			jPanelProgramm.setLayout(new GridBagLayout());
			jPanelProgramm.setPreferredSize(new java.awt.Dimension(770,275));
			jPanelProgramm.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Programm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
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
	private JPanel getJPanelEpgInfoButtons() {
		if (jPanelEpgInfoButtons == null) {
			jPanelEpgInfoButtons = new JPanel();
			GridLayout thisLayout = new GridLayout(4,1);
			jPanelEpgInfoButtons.setLayout(thisLayout);
			thisLayout.setHgap(0);
			thisLayout.setVgap(0);
			thisLayout.setColumns(1);
			thisLayout.setRows(4);
			jPanelEpgInfoButtons.setPreferredSize(new java.awt.Dimension(110,110));
			jPanelEpgInfoButtons.setBorder(new EtchedBorder(BevelBorder.RAISED, null, null));
			jPanelEpgInfoButtons.add(getJComboBoxBoxIP());
			jPanelEpgInfoButtons.add(getJButtonReadEPG());
			jPanelEpgInfoButtons.add(getJButtonClickfinder());
			jPanelEpgInfoButtons.add(getJButtonStartServer());
		}
		return jPanelEpgInfoButtons;
	}
	
	private JPanel getJPanelEpgInfo() {
		if (jPanelEpgInfo == null) {
			jPanelEpgInfo = new JPanel();
			jPanelEpgInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Programm-Informationen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			BorderLayout thisLayout = new BorderLayout();
			jPanelEpgInfo.setLayout(thisLayout);
			thisLayout.setHgap(0);
			thisLayout.setVgap(0);
			jPanelEpgInfo.setPreferredSize(new java.awt.Dimension(355,110));

			jPanelEpgInfo.add(getJScrollPaneEPGDetail(), BorderLayout.CENTER);
		}
		return jPanelEpgInfo;
	}

		/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanelAusgabe() {
		if (jPanelAusgabe == null) {
			java.awt.GridBagConstraints gridBagConstraints92 = new GridBagConstraints();
			jPanelAusgabe = new JPanel();
			jPanelAusgabe.setLayout(new GridBagLayout());
			jPanelAusgabe.setPreferredSize(new java.awt.Dimension(100,110));
			jPanelAusgabe.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Ausgabe", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jPanelAusgabe.setSize(100, 150);
			gridBagConstraints92.gridx = 0;
			gridBagConstraints92.gridy = 0;
			gridBagConstraints92.weightx = 1.0;
			gridBagConstraints92.weighty = 1.0;
			gridBagConstraints92.fill = java.awt.GridBagConstraints.BOTH;
			jPanelAusgabe.add(getJScrollPaneAusgabe(), gridBagConstraints92);
		}
		
		/*if (jPanelAusgabe == null) {
			jPanelAusgabe = new JPanel();		
			JPanel jPanel1 = new JPanel();
			JPanel jPanel2 = new JPanel();
				
			GridBagLayout thisLayout = new GridBagLayout();
			jPanelAusgabe.setLayout(thisLayout);
			thisLayout.columnWidths = new int[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
			thisLayout.rowHeights = new int[] {1};
			thisLayout.columnWeights = new double[] {0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1};
			thisLayout.rowWeights = new double[] {0.1};
			jPanelAusgabe.setPreferredSize(new java.awt.Dimension(630,100));
				
			GridBagLayout jPanel1Layout = new GridBagLayout();
			jPanel1.setLayout(jPanel1Layout);
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Aktionen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jPanel1Layout.columnWidths = new int[] {1,1,1,1,1,1};
			jPanel1Layout.rowHeights = new int[] {1,1,1};
			jPanel1Layout.columnWeights = new double[] {0.1,0.1,0.1,0.1,0.1,0.1};
			jPanel1Layout.rowWeights = new double[] {0.1,0.1,0.1};
				
			jPanel1.add(this.getJButtonAufnahme(), new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));				
			jPanel1.add(this.getJButtonReboot(), new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));	
			jPanel1.add(this.getJButtonVLC(), new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
			jPanel1.add(this.getJButtonNhttpdReset(), new GridBagConstraints(3, 2, 3, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
			jPanel1.add(this.getJButtonEpgReset(), new GridBagConstraints(3, 1, 3, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
			jPanel1.add(this.getJSpinner(), new GridBagConstraints(3, 0, 2, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
			jPanel1.add(new JLabel("min"), new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
				
			GridLayout jPanel2Layout = new GridLayout(1,1);
			jPanel2.setLayout(jPanel2Layout);
			jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Ausgabe", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jPanel2Layout.setHgap(0);
			jPanel2Layout.setVgap(0);
			jPanel2Layout.setColumns(1);
			jPanel2Layout.setRows(1);
			jPanel2.add(this.getJScrollPaneAusgabe());
			jPanelAusgabe.add(jPanel2, new GridBagConstraints(2, 0, 18, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
			*/
		return jPanelAusgabe;
	}
	private JPanel getJPanelAktionen() {
		if (jPanelAktionen == null) {
			java.awt.GridBagConstraints gridBagConstraints72 = new GridBagConstraints();
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
			gridBagConstraints91.gridx = 1;
			gridBagConstraints91.gridy = 2;
			gridBagConstraints91.insets = new java.awt.Insets(0,0,0,0);
			gridBagConstraints91.gridwidth = 3;
			gridBagConstraints101.gridx = 0;
			gridBagConstraints101.gridy = 2;
			gridBagConstraints131.gridx = 0;
			gridBagConstraints131.gridy = 1;
			gridBagConstraints131.gridwidth = 1;
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
			gridBagConstraints72.gridx = 1;
			gridBagConstraints72.gridy = 1;
			gridBagConstraints72.gridwidth = 3;
			jPanelAktionen.add(getJButtonAufnahme(), gridBagConstraints8);
			jPanelAktionen.add(getJButtonReboot(), gridBagConstraints131);
			jPanelAktionen.add(getJButtonNhttpdReset(), gridBagConstraints91);
			jPanelAktionen.add(getJSpinner(), gridBagConstraints15);
			jPanelAktionen.add(jLabel, gridBagConstraints17);
			jPanelAktionen.add(getJButtonVLC(), gridBagConstraints101);
			jPanelAktionen.add(getJButtonEpgReset(), gridBagConstraints72);
		}
		return jPanelAktionen;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
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
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanelEPG() {
		if (jPanelEPGTime == null) {
			java.awt.GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
			jPanelEPGTime = new JPanel();
			jPanelEPGTime.setLayout(new GridBagLayout());
			jPanelEPGTime.setPreferredSize(new java.awt.Dimension(500,245));
			jPanelEPGTime.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Programm, Doppelklick zu Timer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			gridBagConstraints71.gridx = 0;
			gridBagConstraints71.gridy = 0;
			gridBagConstraints71.weightx = 1.0;
			gridBagConstraints71.weighty = 1.0;
			gridBagConstraints71.fill = java.awt.GridBagConstraints.BOTH;
			jPanelEPGTime.add(getJScrollPaneEPG(), gridBagConstraints71);
		}
		return jPanelEPGTime;
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
	/**
	 * This method initializes defaultTableModel	
	 * 	
	 * @return javax.swing.table.DefaultTableModel	
	 */    
	private DefaultTableModel getDefaultTableModel() {
		if (defaultTableModel == null) {
			defaultTableModel = new DefaultTableModel();
			defaultTableModel.setColumnCount(4);
			defaultTableModel.setRowCount(18);
		}
		return defaultTableModel;
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
		}
		return jButtonReboot;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButtonVLC() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setPreferredSize(new java.awt.Dimension(105,25));
			jButton1.setText("VLC-Play");
			jButton1.setToolTipText("AKtuelles Programm im VLC abspielen.");
		}
		return jButton1;
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
			jButtonNhttpdReset.setText("nhttpd Res.");
			jButtonNhttpdReset.setToolTipText("nhttpd resetten");
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
			jButtonToTimer.setText("To Timer");
			jButtonToTimer.setToolTipText("ausgew�hlte Dateien zum Timer hinzuf�gen.");
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
		}
		return jButtonClickfinder;
	}
	
	private JScrollPane getJScrollPaneEPGDetail() {
		if (jScrollPaneEPGDetail == null) {
			jScrollPaneEPGDetail = new JScrollPane();
			jScrollPaneEPGDetail.setViewportView(getJTextAreaEPG());
			jScrollPaneEPGDetail.setPreferredSize(new java.awt.Dimension(305,70));
		}
		return jScrollPaneEPGDetail;
	}
	/**
	 * This method initializes jTextAreaEPG	
	 * 	
	 * @return javax.swing.JTextPane	
	 */    
	public JTextArea getJTextAreaEPG() {
		if (jTextAreaEPG == null) {
			jTextAreaEPG = new JTextArea();
			//jTextAreaEPG.setPreferredSize(new java.awt.Dimension(305,20));
			jTextAreaEPG.setMargin(new java.awt.Insets(3,3,3,3));
			jTextAreaEPG.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
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
	public JTextPane getJTextPaneAusgabe() {
		if (jTextPaneAusgabe == null) {
			jTextPaneAusgabe = new JTextPane();
			jTextPaneAusgabe.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
					
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
	private JComboBox getJComboBoxBouquets() {
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
	 * Es wird das Control ControlProgramTab als "Listener" �bergeben
	 * wird das Datum ge�ndert wird das aktuelle Datum automatisch
	 * �ber setDateChooserDate(Date) gesetzt
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
