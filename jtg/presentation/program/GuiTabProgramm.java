package presentation.program;
/*
 * GuiTabProgramm.java by Geist Alexander
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation,
 * Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *  
 */

import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.event.*;
import javax.swing.table.TableColumn;

import presentation.GuiTab;
import presentation.GuiTableSorter;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import calendar.JDateChooser;

import control.ControlMain;
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
	private JPanel jPanelEpg = null;
	private JPanel jPanelEpgDetails = null;
	private JPanel jPanelRecordInfo = null;
	private JPanel jPanelOutput = null;
	private JComboBox jComboChooseDate = null;
	private JTable jTableEPG = null;
	private JScrollPane jScrollPaneEPG = null;
	private JButton jButtonQuickRecord = null;
	private JButton jButtonReboot = null;
	private JButton jButtonPlayback = null;
	private JButton jButtonToTimer = null;
	private JButton jButtonStartServer = null;
	private JButton jButtonRefresh = null;
	private JButton jButtonShutdown = null;
	private JRadioButton jRadioButtonTVMode = null;
	private JRadioButton jRadioButtonRadioMode = null;
	private ButtonGroup tvRadioButtonGroup = new ButtonGroup();
	private JTextArea jTextAreaEPG = null;
	private JSpinner jSpinnerRecordStopTime;
	private JComboBox jComboBoxBoxIP = null;
	public JTextArea jTextPaneAusgabe = null;
	public GuiSenderTableModel senderTableModel;
	public GuiBoquetsComboModel boquetsComboModel;
	public GuiEpgTableModel epgTableModel;
	private JScrollPane jScrollPaneChannels = null;
	private JTable jTableChannels = null;
	private ControlProgramTab control;
	private JComboBox JComboBoxBouquets = null;
	private JDateChooser jDateChooser = null;
	private JScrollPane jScrollPaneEPGDetail = null;
	private JScrollPane jScrollPaneAusgabe = null;
	public GuiTableSorter sorter = null;
	private SpinnerDateModel dateModelSpinnerStopTime;

	public GuiTabProgramm(ControlProgramTab control) {
		this.setControl(control);
		initialize();
		this.setDisconnectModus();
	}

	protected void initialize() {
		FormLayout layout = new FormLayout("f:pref, 10, f:170, 10, f:pref, 10, f:250:grow", // columns
				"f:290:grow, 10, f:pref, 5, f:120:grow"); // rows
		PanelBuilder builder = new PanelBuilder(this, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();

		builder.add(this.getJPanelChannels(), cc.xy(1, 1));
		builder.add(this.getJPanelEpg(), cc.xywh(3, 1, 5, 1));
		builder.add(this.getJPanelButtonsAktionen(), cc.xywh(1, 3, 3, 1));
		builder.add(this.getJPanelRecordInfo(), cc.xywh(5, 3, 1, 1));
		builder.add(this.getJPanelEpgDetails(), cc.xywh(7, 3, 1, 3));
		builder.add(this.getJPanelOutput(), cc.xywh(1, 5, 5, 1));
	}

	/**
	 * This method initializes jPanelOutput
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelOutput() {
		if (jPanelOutput == null) {
			jPanelOutput = new JPanel();
			FormLayout layout = new FormLayout("f:d:grow", //columna
					"f:pref, f:d:grow"); //rows
			PanelBuilder builder = new PanelBuilder(jPanelOutput, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator(ControlMain.getProperty("label_output"), cc.xy(1, 1));
			builder.add(this.getJScrollPaneAusgabe(), cc.xy(1, 2));
		}
		return jPanelOutput;
	}

	/**
	 * This method initializes jPanelEpgDetails
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelEpgDetails() {
		if (jPanelEpgDetails == null) {
			jPanelEpgDetails = new JPanel();
			FormLayout layout = new FormLayout("f:d:grow", //columns
					"f:pref, f:d:grow"); //rows
			PanelBuilder builder = new PanelBuilder(jPanelEpgDetails, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator(ControlMain.getProperty("label_epgDetails"), cc.xy(1, 1));
			builder.add(this.getJScrollPaneEPGDetail(), cc.xy(1, 2));
		}
		return jPanelEpgDetails;
	}

	/**
	 * This method initializes jPanelEpg
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelEpg() {
		if (jPanelEpg == null) {
			jPanelEpg = new JPanel();
			FormLayout layout = new FormLayout("f:d:grow", //columns
					"f:pref, f:d:grow"); //rows
			PanelBuilder builder = new PanelBuilder(jPanelEpg, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator(ControlMain.getProperty("label_epg"), cc.xy(1, 1));
			builder.add(this.getJScrollPaneEPG(), cc.xy(1, 2));
		}
		return jPanelEpg;
	}

	/**
	 * This method initializes jPanelButtonsAktionen
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelButtonsAktionen() {
		if (jPanelButtonsAktionen == null) {
			jPanelButtonsAktionen = new JPanel();
			FormLayout layout = new FormLayout("f:pref, 2, f:105, 2, f:110, 2, f:85", //columna
					"pref, f:22, 2, f:22"); //rows
			PanelBuilder builder = new PanelBuilder(jPanelButtonsAktionen, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator(ControlMain.getProperty("label_actions"), cc.xywh(1, 1, 7, 1));
			builder.add(this.getJRadioButtonTVMode(), cc.xy(1, 2));
			builder.add(this.getJButtonStartServer(), cc.xy(3, 2));
			builder.add(this.getJButtonPlayback(), cc.xy(5, 2));
			builder.add(this.getJButtonAufnahme(), cc.xy(7, 2));
			builder.add(this.getJRadioButtonRadioMode(), cc.xy(1, 4));
			builder.add(this.getJButtonReboot(), cc.xy(3, 4));
			builder.add(this.getJButtonShutdown(), cc.xy(5, 4));
		}
		return jPanelButtonsAktionen;
	}

	private JPanel getJPanelRecordInfo() {
		if (jPanelRecordInfo == null) {
			jPanelRecordInfo = new JPanel();
			FormLayout layout = new FormLayout("pref", //columna
					"pref, pref, 2, 25"); //rows
			PanelBuilder builder = new PanelBuilder(jPanelRecordInfo, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator(ControlMain.getProperty("label_recordInfo"), cc.xy(1, 1));
			builder.add(new JLabel(ControlMain.getProperty("label_stopRecord")), cc.xy(1, 2));
			builder.add(this.getJSpinnerRecordStopTime(), cc.xy(1, 4));
		}
		return jPanelRecordInfo;
	}

	private JPanel getJPanelChannels() {
		if (jPanelChannel == null) {
			jPanelChannel = new JPanel();
			FormLayout layout = new FormLayout("30, 150", //column
					"pref, pref, 4, pref, pref, min:grow, pref, pref"); //rows
			PanelBuilder builder = new PanelBuilder(jPanelChannel, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator(ControlMain.getProperty("label_date"), cc.xywh(1, 1, 2, 1));
			builder.add(this.getJDateChooser(), cc.xyw(1, 2, 2, CellConstraints.FILL, CellConstraints.FILL));
			builder.addSeparator(ControlMain.getProperty("label_zapping"), cc.xyw(1, 4, 2));
			builder.add(this.getJComboBoxBouquets(), cc.xyw(1, 5, 2, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJScrollPaneChannels(), cc.xyw(1, 6, 2, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJButtonRefresh(), cc.xyw(1, 7, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJComboBoxBoxIP(), cc.xyw(2, 7, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJButtonSelectedToTimer(), cc.xyw(1, 8, 2, CellConstraints.FILL, CellConstraints.FILL));
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
			jComboBoxBoxIP.setRenderer(new GuiIpComboCellRenderer());
			jComboBoxBoxIP.setPreferredSize(new Dimension(100, 22));
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
			sorter = new GuiTableSorter(epgTableModel);
			jTableEPG = new JTable(sorter);
			sorter.setTableHeader(jTableEPG.getTableHeader());

			TableColumn eventIdColumnt = jTableEPG.getColumnModel().getColumn(0);
			jTableEPG.getTableHeader().getColumnModel().removeColumn(eventIdColumnt); //eventId ausblenden

			jTableEPG.getColumnModel().getColumn(0).setMaxWidth(50);
			jTableEPG.getColumnModel().getColumn(1).setMaxWidth(50);
			jTableEPG.getColumnModel().getColumn(2).setMaxWidth(60);
			jTableEPG.getColumnModel().getColumn(3).setPreferredWidth(280);
			jTableEPG.addMouseListener(control);
			jTableEPG.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (!e.getValueIsAdjusting()) {

						control.epgChanged(jTableEPG);
					}
				}

			});
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
			jButtonQuickRecord.setText(ControlMain.getProperty("button_record"));
			jButtonQuickRecord.setActionCommand("record");
			jButtonQuickRecord.setToolTipText(ControlMain.getProperty("buttontt_record"));
			jButtonQuickRecord.addActionListener(this.getControl());
		}
		return jButtonQuickRecord;
	}
	/**
	 * This method initializes jButtonRefresh
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getJButtonRefresh() {
		if (jButtonRefresh == null) {
			ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("ico/Refresh16.gif"));
			jButtonRefresh = new JButton(icon);
			jButtonRefresh.setActionCommand("refresh");
			jButtonRefresh.setToolTipText(ControlMain.getProperty("buttontt_refresh"));
			jButtonRefresh.addActionListener(this.getControl());
		}
		return jButtonRefresh;
	}
	/**
	 * This method initializes jButtonShutdown
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getJButtonShutdown() {
		if (jButtonShutdown == null) {
			jButtonShutdown = new JButton();
			jButtonShutdown.setText(ControlMain.getProperty("button_shutdown"));
			jButtonShutdown.setActionCommand("shutdown");
			jButtonShutdown.setToolTipText(ControlMain.getProperty("buttontt_shutdown"));
			jButtonShutdown.addActionListener(this.getControl());
		}
		return jButtonShutdown;
	}
	/**
	 * This method initializes jButtonReboot
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getJButtonReboot() {
		if (jButtonReboot == null) {
			jButtonReboot = new JButton();
			jButtonReboot.setText(ControlMain.getProperty("button_reboot"));
			jButtonReboot.setActionCommand("reboot");
			jButtonReboot.setToolTipText(ControlMain.getProperty("buttontt_reboot"));
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
			jButtonPlayback.setText(ControlMain.getProperty("button_playback"));
			jButtonPlayback.setActionCommand("playback");
			jButtonPlayback.setToolTipText(ControlMain.getProperty("buttontt_playback"));
			jButtonPlayback.addActionListener(this.getControl());
		}
		return jButtonPlayback;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	public JSpinner getJSpinnerRecordStopTime() {
		if (jSpinnerRecordStopTime == null) {
			dateModelSpinnerStopTime = new SpinnerDateModel();
			jSpinnerRecordStopTime = new JSpinner(dateModelSpinnerStopTime);
			JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(jSpinnerRecordStopTime, "HH:mm - dd.MM.yyyy");
			jSpinnerRecordStopTime.setEditor(dateEditor);
			jSpinnerRecordStopTime.setPreferredSize(new java.awt.Dimension(105, 25));
			jSpinnerRecordStopTime.setToolTipText(ControlMain.getProperty("buttontt_quickRecord"));
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
			jButtonToTimer.setText(ControlMain.getProperty("button_toTimer"));
			jButtonToTimer.setActionCommand("toTimer");
			jButtonToTimer.setToolTipText(ControlMain.getProperty("buttontt_toTimer"));
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
			jButtonStartServer.setText(ControlMain.getProperty("button_startServer"));
			jButtonStartServer.setActionCommand("startServer");
			jButtonStartServer.setToolTipText(ControlMain.getProperty("buttontt_startServer"));
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
		if (jScrollPaneChannels == null) {
			jScrollPaneChannels = new JScrollPane();
			jScrollPaneChannels.setViewportView(getJTableChannels());
		}
		return jScrollPaneChannels;
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
			jTableChannels.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent e) {
					if (!e.getValueIsAdjusting()) {
						control.channelChanged(jTableChannels);
					}
				}
			});

			jTableChannels.setName("Sender");
			jTableChannels.setShowHorizontalLines(false);
			jTableChannels.setShowVerticalLines(false);

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
	 * @param boquetsComboModel
	 *            The boquetsComboModel to set.
	 */
	public void setBoquetsComboModel(GuiBoquetsComboModel guiBoquetsComboModel) {
		this.boquetsComboModel = guiBoquetsComboModel;
	}
	/**
	 * @return Returns the senderTableModel.
	 */
	public GuiSenderTableModel getSenderTableModel() {
		return senderTableModel;
	}
	/**
	 * @param senderTableModel
	 *            The senderTableModel to set.
	 */
	public void setSenderTableModel(GuiSenderTableModel senderTableModel) {
		this.senderTableModel = senderTableModel;
	}
	/**
	 * This method initializes jDateChooser
	 * 
	 * Achtung modifizierter DateChooser!!! Es wird das Control ControlProgramTab als "Listener" uebergeben wird das Datum geaendert wird
	 * das aktuelle Datum automatisch ueber setDateChooserDate(Date) gesetzt
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
	 * @param epgTableModel
	 *            The epgTableModel to set.
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
	 * 
	 * @param control
	 *            The control to set
	 */
	public void setControl(ControlProgramTab control) {
		this.control = control;
	}
	public void setDisconnectModus() {
		this.getJButtonAufnahme().setEnabled(false);
		this.getJButtonReboot().setEnabled(false);
		this.getJButtonSelectedToTimer().setEnabled(false);
		this.getJButtonStartServer().setEnabled(false);
		this.getJButtonPlayback().setEnabled(false);
		this.getJButtonShutdown().setEnabled(false);
	}

	public void setConnectModus() {
		this.getJButtonAufnahme().setEnabled(true);
		this.getJButtonReboot().setEnabled(true);
		this.getJButtonSelectedToTimer().setEnabled(true);
		this.getJButtonStartServer().setEnabled(true);
		this.getJButtonPlayback().setEnabled(true);
		this.getJButtonShutdown().setEnabled(true);
	}

	public void stopStreamingServerModus() {
		this.getJButtonStartServer().setText(ControlMain.getProperty("button_startServer"));
		this.getJButtonStartServer().setToolTipText(ControlMain.getProperty("buttontt_startServer"));
	}

	public void startStreamingServerModus() {
		this.getJButtonStartServer().setText(ControlMain.getProperty("button_stopServer"));
		this.getJButtonStartServer().setToolTipText(ControlMain.getProperty("buttontt_stopServer"));
	}
	/**
	 * Versetzen des Aufnahme-Buttons in den Aufnahme-Warte-Modus
	 */
	public void stopRecordModus() {
		this.getJButtonAufnahme().setText(ControlMain.getProperty("button_record"));
		this.getJButtonAufnahme().setToolTipText(ControlMain.getProperty("buttontt_record"));
		this.getJButtonAufnahme().setIcon(null);
	}

	/**
	 * Versetzen des Aufnahme-Buttons in den in den Aufnahme-Modus
	 */
	public void startRecordModus() {
		this.getJButtonAufnahme().setText(ControlMain.getProperty("button_stopRecord"));
		this.getJButtonAufnahme().setToolTipText(ControlMain.getProperty("buttontt_stopRecord"));
		ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("ico/stop.png"));
		this.getJButtonAufnahme().setIcon(icon);
	}
	/**
	 * @return Returns the dateModelSpinnerStopTime.
	 */
	public SpinnerDateModel getDateModelSpinnerStopTime() {
		return dateModelSpinnerStopTime;
	}
	/**
	 * @return Returns the jRadioButtonRadioMode.
	 */
	public JRadioButton getJRadioButtonRadioMode() {
		if (jRadioButtonRadioMode == null) {
			jRadioButtonRadioMode = new JRadioButton(ControlMain.getProperty("button_radio"));
			jRadioButtonRadioMode.addActionListener(control);
			jRadioButtonRadioMode.setActionCommand("radioMode");
			tvRadioButtonGroup.add(jRadioButtonRadioMode);
		}
		return jRadioButtonRadioMode;
	}
	/**
	 * @return Returns the jRadioButtonTVMode.
	 */
	public JRadioButton getJRadioButtonTVMode() {
		if (jRadioButtonTVMode == null) {
			jRadioButtonTVMode = new JRadioButton(ControlMain.getProperty("button_tv"));
			jRadioButtonTVMode.addActionListener(control);
			jRadioButtonTVMode.setActionCommand("tvMode");
			tvRadioButtonGroup.add(jRadioButtonTVMode);
		}
		return jRadioButtonTVMode;
	}
}