package presentation;

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
import javax.swing.table.TableColumn;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import calendar.JDateChooser;

import control.ControlProgramTab;

/**
 * @author Alexander Geist
 * 
 * Gui des Programm-Tabs
 */
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
	private JComboBox jComboChooseDate = null;
	private JTable jTableEPG = null;
	private JButton jButtonOfflineEpg = null;
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
	private JTable jTableChannels = null;
	private ControlProgramTab control;
	private JComboBox JComboBoxBouquets = null;
	private JDateChooser jDateChooser = null;
	private JScrollPane jScrollPaneEPGDetail = null;
	private JScrollPane jScrollPaneAusgabe = null;
	public GuiEpgTableSorter sorter = null;
	
	public GuiTabProgramm(ControlProgramTab control) {
		this.setControl(control);
		initialize();
	}
	
	private  void initialize() {
		FormLayout layout = new FormLayout(
			      "pref, 8dlu, pref, 8dlu, 340px:grow",  							// columns 
			      "pref, 263px:grow, 8dlu, pref, pref, 3dlu, pref, 100px:grow");	// rows
		PanelBuilder builder = new PanelBuilder(this, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();
	
		builder.addSeparator("Datum",		   					cc.xywh	(1, 1, 1, 1));
		builder.add(this.getJPanelChannels(),  					cc.xywh	(1, 2, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
		builder.addSeparator("EPG",			   					cc.xywh	(3, 1, 3, 1));
		builder.add(this.getJScrollPaneEPG(),					cc.xywh	(3, 2, 3, 1));
		builder.addSeparator("Aktionen",						cc.xywh	(1, 4, 1, 1));
		builder.addSeparator("Infomationen",					cc.xywh	(3, 4, 1, 1));
		builder.addSeparator("EPG-Details",						cc.xywh	(5, 4, 1, 1));
		builder.add(this.getJPanelButtonsAktionen(),  			cc.xywh	(1, 5, 1, 1));
		builder.add(this.getJPanelButtonsInformationen(),     	cc.xywh	(3, 5, 1, 1));
		builder.add(this.getJScrollPaneEPGDetail(),	 			cc.xywh	(5, 5, 1, 4));
		builder.addSeparator("Ausgabe",							cc.xywh	(1, 7, 3, 1));
		builder.add(this.getJScrollPaneAusgabe(), 	 			cc.xywh	(1, 8, 3, 1, CellConstraints.FILL, CellConstraints.FILL));
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
				      "pref, 1dlu, 85px, 20px",	 		//columna 
				      "pref, 1dlu, pref, 1dlu, pref");	//rows
			PanelBuilder builder = new PanelBuilder(jPanelButtonsAktionen, layout);
			CellConstraints cc = new CellConstraints();
			
			builder.add(this.getJButtonAufnahme(),	  					cc.xyw	(1, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJButtonNhttpdReset(),  					cc.xyw	(1, 3, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJButtonVLC(),		  					cc.xyw	(1, 5, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJSpinner(),			  					cc.xyw	(3, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(new JLabel("Min"),			  					cc.xyw	(4, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJButtonReboot(), 	 					cc.xyw	(3, 3, 2, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJButtonEpgReset(),	  					cc.xyw	(3, 5, 2, CellConstraints.FILL, CellConstraints.FILL));
		}
		return jPanelButtonsAktionen;
	}
	
	private JPanel getJPanelButtonsInformationen() {
		if (jPanelButtonsProgrammInfo == null) {
			jPanelButtonsProgrammInfo = new JPanel();
			FormLayout layout = new FormLayout(
				      "pref, 1dlu, pref",				//columna 
				      "pref, 1dlu, pref, 1dlu, pref");	//rows
			PanelBuilder builder = new PanelBuilder(jPanelButtonsProgrammInfo, layout);
			CellConstraints cc = new CellConstraints();
			
			builder.add(this.getJComboBoxBoxIP(),	  					cc.xyw	(1, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJButtonSelectedToTimer(),				cc.xyw	(1, 3, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJButtonStartServer(),					cc.xyw	(1, 5, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJButtonClickfinder(),  					cc.xyw	(3, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJButtonReadEPG(), 	 					cc.xyw	(3, 3, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJButtonOfflineEpg(),  					cc.xyw	(3, 5, 1, CellConstraints.FILL, CellConstraints.FILL));
		}
		return jPanelButtonsProgrammInfo;
	}
	
	private JPanel getJPanelChannels() {
		if (jPanelChannel == null) {
			jPanelChannel = new JPanel();
			FormLayout layout = new FormLayout(
				      "210px",									//column 
				      "pref, 4px, pref, pref, min:grow");		//rows
			PanelBuilder builder = new PanelBuilder(jPanelChannel, layout);
			CellConstraints cc = new CellConstraints();
			
			builder.add(this.getJDateChooser(),		  					cc.xyw	(1, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.addSeparator("Sender, Doppelklick Zapping",			cc.xy	(1, 3));
			builder.add(this.getJComboBoxBouquets(), 					cc.xyw	(1, 4, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJScrollPaneChannels(), 					cc.xyw	(1, 5, 1, CellConstraints.FILL, CellConstraints.FILL));
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
			jComboBoxBoxIP.setModel(new GuiIpListComboModel(control));
			jComboBoxBoxIP.addItemListener(control);
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
			
			//jTableEPG.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			TableColumn eventIdColumnt = jTableEPG.getColumnModel().getColumn(0);
			jTableEPG.getTableHeader().getColumnModel().removeColumn(eventIdColumnt); 

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
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButtonOfflineEpg() {
		if (jButtonOfflineEpg == null) {
			jButtonOfflineEpg = new JButton();
			jButtonOfflineEpg.setPreferredSize(new java.awt.Dimension(105,25));
			jButtonOfflineEpg.setText("Offline EPG");
		}
		return jButtonOfflineEpg;
	}
   }
