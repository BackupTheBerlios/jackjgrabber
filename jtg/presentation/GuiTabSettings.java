package presentation;
/*
GuiTabSettings.java by Geist Alexander 

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
import java.awt.Dimension;
import java.text.ParseException;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;


import model.BOLocale;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import control.ControlMain;
import control.ControlSettingsTab;

public class GuiTabSettings extends JPanel {

	private JPanel panelBoxSettings = null;
	private JPanel panelPlaybackSettings = null;
	private JPanel panelLayoutSettings = null;
	private JPanel panelRecordSettings = null;
	private JComboBox jComboBoxTheme = null;
	private JComboBox jComboBoxStreamType = null;
	private JComboBox jComboBoxLocale = null;
	private JTextField jTextFieldRecordSavePath;
	private JTextField jTextFieldUdrecPath;
	private JTextField jTextFieldPlaybackString = null;
	private JButton jButtonAnlegen = null;
	private JButton jButtonLoeschen = null;
	private JButton jButtonRecordPathFileChooser;
	private JButton jButtonUdrecPathFileChooser;
	private JRadioButton jRadioButtonUdrec;
	private JRadioButton jRadioButtonJGrabber;
	private ButtonGroup buttonGroupStreamingEngine = new ButtonGroup();
	private JScrollPane jScrollPaneBoxSettings = null;
	private JTable jTableBoxSettings = null;
	private JFormattedTextField tfServerPort = null;
	private JFormattedTextField tfBoxIp = null;
	private JCheckBox cbStartStreamingServer;
	private JCheckBox cbRecordAllPids;
	private JCheckBox cbStartPX;
	private ControlSettingsTab control;
	private GuiBoxSettingsTableModel modelBoxTable;

	public GuiTabSettings(ControlSettingsTab ctrl) {
		super();
		this.setControl(ctrl);
		initialize();
	}

	private  void initialize() {
		FormLayout layout = new FormLayout(
						  "f:pref:grow, 20, f:200",  		// columns 
						  "f:pref, 20, pref, 20, pref"); 			// rows
				PanelBuilder builder = new PanelBuilder(this, layout);
				builder.setDefaultDialogBorder();
				CellConstraints cc = new CellConstraints();
		
				builder.add(this.getPanelBoxSettings(),		   		cc.xy(1,1));
				builder.add(this.getPanelLayoutSettings(),			cc.xywh(3, 1, 1, 1, CellConstraints.LEFT, CellConstraints.TOP));				
				builder.add(this.getPanelRecordSettings(),			cc.xywh(1, 3, 1, 1, CellConstraints.LEFT, CellConstraints.TOP));
				builder.add(this.getPanelPlaybackSettings(),		cc.xywh(1, 5, 1, 1, CellConstraints.LEFT, CellConstraints.TOP));
	}
	    
	private JPanel getPanelBoxSettings() {
		if (panelBoxSettings == null) {
			panelBoxSettings = new JPanel();
			FormLayout layout = new FormLayout(
					  "pref:grow, 5, pref",	 		//columna 
			  "pref:grow, pref:grow, pref:grow, 40:grow");				//rows
			PanelBuilder builder = new PanelBuilder(panelBoxSettings, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator("Box-Settings",						cc.xywh	(1, 1, 3, 1));
			builder.add(this.getJScrollPaneBoxSettings(),	  			cc.xywh	(1, 2, 1, 3));
			builder.add(this.getJButtonAnlegen(),						cc.xy	(3, 2));
			builder.add(this.getJButtonLoeschen(),						cc.xy	(3, 3));
		}
		return panelBoxSettings;
	}
	
	private JPanel getPanelRecordSettings() {
		if (panelRecordSettings == null) {
			panelRecordSettings = new JPanel();
			FormLayout layout = new FormLayout(
					  "pref, 5, pref, 10, pref:grow,  5, pref",	 		//columns 
			  "pref:grow, pref:grow, pref:grow, pref:grow, pref:grow, pref:grow, pref:grow, pref, 4, pref:grow");		//rows
			PanelBuilder builder = new PanelBuilder(panelRecordSettings, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator("Aufname-Settings",							cc.xywh	(1, 1, 7, 1));
			builder.add(this.getCbStartStreamingServer(),						cc.xywh	(1, 2, 4, 1));
			builder.add(new JLabel("Streamingserver-Port"),	  					cc.xywh	(5, 2, 1, 1, CellConstraints.RIGHT, CellConstraints.FILL));
			builder.add(this.getTfServerPort(),									cc.xy	(7, 2));
			builder.add(this.getCbRecordAllPids(),								cc.xy	(1, 3));
			builder.add(this.getCbStartPX(),									cc.xywh	(1, 5, 5, 1));
			builder.add(new JLabel("Aufnahme-Zielverzeichniss"),				cc.xy	(1, 6));
			builder.add(this.getJTextFieldRecordSavePath(),						cc.xywh	(4, 6, 2, 1));
			builder.add(this.getJButtonRecordPathFileChooser(),					cc.xy	(7, 6));
			builder.addSeparator("TV-Aufname-Engine",							cc.xywh	(1, 7, 7, 1));	
			builder.add(this.getJRadioButtonJGrabber(),							cc.xy	(1, 8));
			builder.add(this.getJRadioButtonUdrec(),							cc.xy	(3, 8));
			builder.add(this.getJComboBoxStreamType(),							cc.xywh	(5, 8, 2, 1));
			builder.add(new JLabel("Pfad zur udrec.exe"),						cc.xy	(1, 10));
			builder.add(this.getJTextFieldUdrecPath(),							cc.xywh	(4, 10, 2, 1));
			builder.add(this.getJButtonUdrecPathFileChooser(),					cc.xy	(7, 10));
		}
		return panelRecordSettings;
	}
  
	private JPanel getPanelLayoutSettings() {
		if (panelLayoutSettings == null) {
			panelLayoutSettings = new JPanel();
			FormLayout layout = new FormLayout(
					  "pref, 10, pref",	 		//columna 
			  "pref, pref, pref, pref");	//rows
			PanelBuilder builder = new PanelBuilder(panelLayoutSettings, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator("Layout-Settings",						cc.xywh	(1, 1, 3, 1));
			builder.add(new JLabel("Theme"),				  			cc.xy	(1, 2));
			builder.add(this.getJComboBoxTheme(),						cc.xy	(3, 2));
			builder.add(new JLabel("Locale"),				  			cc.xy	(1, 4));
			builder.add(this.getJComboBoxLocale(),						cc.xy	(3, 4));
		}
		return panelLayoutSettings;
	}
	
	private JPanel getPanelPlaybackSettings() {
		if (panelPlaybackSettings == null) {
			panelPlaybackSettings = new JPanel();
			FormLayout layout = new FormLayout(
					  "pref, 10, pref",	 		//columna 
			  "pref, pref, pref, pref, pref");	//rows
			PanelBuilder builder = new PanelBuilder(panelPlaybackSettings, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator("Playback-Settings",													cc.xywh	(1, 1, 3, 1));
			builder.add(new JLabel("z.B. xine http://$ip:31339/$vPid,$aPid"),				cc.xywh	(1, 2, 3, 1));
			builder.add(new JLabel("Execute"),					  											cc.xy	(1, 3));
			builder.add(this.getJTextFieldPlaybackString(),											cc.xy	(3, 3));
		}
		return panelPlaybackSettings;
	}
	
	/**
	 * This method initializes jComboBoxLocale	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	public JComboBox getJComboBoxTheme() {
		if (jComboBoxTheme == null) {
			jComboBoxTheme = new JComboBox(ControlMain.themes);
			jComboBoxTheme.addItemListener(control);
			jComboBoxTheme.setName("theme");
			jComboBoxTheme.setPreferredSize(new java.awt.Dimension(105,19));
		}
		return jComboBoxTheme;
	}
	
	/**
	 * This method initializes jComboBoxStreamType	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	public JComboBox getJComboBoxStreamType() {
		if (jComboBoxStreamType == null) {
			jComboBoxStreamType = new JComboBox();
			jComboBoxStreamType.addItemListener(control);
			jComboBoxStreamType.setName("streamType");
			jComboBoxStreamType.setPreferredSize(new java.awt.Dimension(50,19));
		}
		return jComboBoxStreamType;
	}
	/**
	 * This method initializes jButtonAnlegen	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButtonAnlegen() {
		if (jButtonAnlegen == null) {
			jButtonAnlegen = new JButton();
			jButtonAnlegen.setText("Anlegen");
			jButtonAnlegen.setActionCommand("add");
			jButtonAnlegen.addActionListener(control);
			jButtonAnlegen.setPreferredSize(new java.awt.Dimension(90,25));
		}
		return jButtonAnlegen;
	}
	/**
	 * This method initializes getJButtonLoeschen	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButtonLoeschen() {
		if (jButtonLoeschen == null) {
			jButtonLoeschen = new JButton();
			jButtonLoeschen.setText("Löschen");
			jButtonLoeschen.setActionCommand("delete");
			jButtonLoeschen.addActionListener(control);
			jButtonLoeschen.setPreferredSize(new java.awt.Dimension(90,25));
		}
		return jButtonLoeschen;
	}
	/**
	 * This method initializes jScrollPaneBoxSettings	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPaneBoxSettings() {
		if (jScrollPaneBoxSettings == null) {
			jScrollPaneBoxSettings = new JScrollPane();
			jScrollPaneBoxSettings.setPreferredSize(new Dimension(450, 100));
			jScrollPaneBoxSettings.setViewportView(getJTableBoxSettings());
		}
		return jScrollPaneBoxSettings;
	}
	/**
	 * This method initializes jTableBoxSettings	
	 * 	
	 * @return javax.swing.JTable	
	 */    
	public JTable getJTableBoxSettings() {
		if (jTableBoxSettings == null) {
			modelBoxTable = new GuiBoxSettingsTableModel(control);
			jTableBoxSettings = new JTable(modelBoxTable);
			jTableBoxSettings.setName("BoxSettings");
			jTableBoxSettings.getColumnModel().getColumn(0).setPreferredWidth(80);
			jTableBoxSettings.getColumnModel().getColumn(1).setPreferredWidth(100);
			jTableBoxSettings.getColumnModel().getColumn(2).setPreferredWidth(80);
			jTableBoxSettings.getColumnModel().getColumn(3).setPreferredWidth(40);
			jTableBoxSettings.getColumnModel().getColumn(3).setCellRenderer( new GuiBoxSettingsTableCellRenderer(control));
			
			TableColumn columnIp = jTableBoxSettings.getColumnModel().getColumn(0);
			columnIp.setCellEditor(new DefaultCellEditor(this.getTfBoxIp()));
		}
		return jTableBoxSettings;
	}
	/**
	 * @return Returns the control.
	 */
	public ControlSettingsTab getControl() {
		return control;
	}
	/**
	 * @param control The control to set.
	 */
	public void setControl(ControlSettingsTab control) {
		this.control = control;
	}
	/**
	 * @return Returns the modelBoxTable.
	 */
	public GuiBoxSettingsTableModel getModelBoxTable() {
		return modelBoxTable;
	}
	/**
	 * @param modelBoxTable The modelBoxTable to set.
	 */
	public void setModelBoxTable(GuiBoxSettingsTableModel modelBoxTable) {
		this.modelBoxTable = modelBoxTable;
	}
	/**
	 * This method initializes jComboBoxLocale	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	public JComboBox getJComboBoxLocale() {
		if (jComboBoxLocale == null) {
			jComboBoxLocale = new JComboBox(new BOLocale().getLocaleList().toArray());
			jComboBoxLocale.addItemListener(control);
			jComboBoxLocale.setName("locale");
			jComboBoxLocale.setPreferredSize(new java.awt.Dimension(105,19));
		}
		return jComboBoxLocale;
	}
	/**
	 * @return Returns the tfServerPort.
	 */
	public JFormattedTextField getTfServerPort() {
		if (tfServerPort == null) {
			try {
				tfServerPort = new JFormattedTextField(new MaskFormatter("####"));
				tfServerPort.setName("serverPort");
				tfServerPort.addKeyListener(control);
				((MaskFormatter)tfServerPort.getFormatter()).setAllowsInvalid(false);
				((MaskFormatter)tfServerPort.getFormatter()).setOverwriteMode(true);
				tfServerPort.setPreferredSize(new java.awt.Dimension(40,19));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tfServerPort;
	}
	
	public JFormattedTextField getTfBoxIp() {
		if (tfBoxIp == null) {
			try {
				tfBoxIp = new JFormattedTextField(new MaskFormatter("###.###.###.###"));
				((MaskFormatter)tfBoxIp.getFormatter()).setAllowsInvalid(false);
				((MaskFormatter)tfBoxIp.getFormatter()).setOverwriteMode(true);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tfBoxIp;
	}
	/**
	 * @return Returns the cbStartStreamingServer.
	 */
	public JCheckBox getCbStartStreamingServer() {
		if (cbStartStreamingServer == null) {
			cbStartStreamingServer = new JCheckBox("Streamingserver beim Start starten");
			cbStartStreamingServer.setName("startStreamingServer");
			cbStartStreamingServer.addItemListener(control);
		}
		return cbStartStreamingServer;
	}
	/**
	 * @return Returns the jButtonRecordPathFileChooser.
	 */
	public JButton getJButtonRecordPathFileChooser() {
		if (jButtonRecordPathFileChooser == null) {
			jButtonRecordPathFileChooser = new JButton("...");
			jButtonRecordPathFileChooser.setActionCommand("recordPath");
			jButtonRecordPathFileChooser.addActionListener(control);
		}
		return jButtonRecordPathFileChooser;
	}
	/**
	 * @return Returns the jButtonUdrecPathFileChooser.
	 */
	public JButton getJButtonUdrecPathFileChooser() {
		if (jButtonUdrecPathFileChooser == null) {
			jButtonUdrecPathFileChooser = new JButton("...");
			jButtonUdrecPathFileChooser.setActionCommand("udrecPath");
			jButtonUdrecPathFileChooser.addActionListener(control);
		}
		return jButtonUdrecPathFileChooser;
	}
	/**
	 * @return Returns the jTextFieldRecordSavePath.
	 */
	public JTextField getJTextFieldRecordSavePath() {
		if (jTextFieldRecordSavePath == null) {
			jTextFieldRecordSavePath = new JTextField();
			jTextFieldRecordSavePath.setPreferredSize(new Dimension(340, 19));
			jTextFieldRecordSavePath.setEditable(false);
		}
		return jTextFieldRecordSavePath;
	}
	/**
	 * @return Returns the jTextFieldUdrecPath.
	 */
	public JTextField getJTextFieldUdrecPath() {
		if (jTextFieldUdrecPath == null) {
			jTextFieldUdrecPath = new JTextField();
			jTextFieldUdrecPath.addKeyListener(control);
			jTextFieldUdrecPath.setName("udrecPath");
			jTextFieldUdrecPath.setPreferredSize(new Dimension(340, 19));
		}
		return jTextFieldUdrecPath;
	}
	/**
	 * @return Returns the jTextFieldPlaybackString.
	 */
	public JTextField getJTextFieldPlaybackString() {
		if (jTextFieldPlaybackString == null) {
			jTextFieldPlaybackString = new JTextField();
			jTextFieldPlaybackString.addKeyListener(control);
			jTextFieldPlaybackString.setName("playbackString");
			jTextFieldPlaybackString.setPreferredSize(new Dimension(340, 19));
		}
		return jTextFieldPlaybackString;
	}
	/**
	 * @return Returns the cbStartPX.
	 */
	public JCheckBox getCbStartPX() {
		if (cbStartPX == null) {
			cbStartPX = new JCheckBox("Nach Aufnahmeende mit ProjectX demuxen");
			cbStartPX.setName("startPX");
			cbStartPX.addItemListener(control);
		}
		return cbStartPX;
	}
	/**
	 * @return Returns the cbRecordAllPids.
	 */
	public JCheckBox getCbRecordAllPids() {
		if (cbRecordAllPids == null) {
			cbRecordAllPids = new JCheckBox("Immer alle Pids aufnehmen");
			cbRecordAllPids.setName("recordAllPids");
			cbRecordAllPids.addItemListener(control);
		}
		return cbRecordAllPids;
	}
	/**
	 * @return Returns the jRadioButtonJGrabber.
	 */
	public JRadioButton getJRadioButtonJGrabber() {
		if (jRadioButtonJGrabber == null) {
			jRadioButtonJGrabber = new JRadioButton("JGrabber");
			jRadioButtonJGrabber.addActionListener(control);
			jRadioButtonJGrabber.setActionCommand("jgrabber");
			buttonGroupStreamingEngine.add(jRadioButtonJGrabber);
		}
		return jRadioButtonJGrabber;
	}
	/**
	 * @return Returns the jRadioButtonUdrec.
	 */
	public JRadioButton getJRadioButtonUdrec() {
		if (jRadioButtonUdrec == null) {
			jRadioButtonUdrec = new JRadioButton("udrec");
			jRadioButtonUdrec.addActionListener(control);
			jRadioButtonUdrec.setActionCommand("udrec");
			buttonGroupStreamingEngine.add(jRadioButtonUdrec);
		}
		return jRadioButtonUdrec;
	}
	/**
	 * @return Returns the streamTypeComboModel.
	 */
	public GuiStreamTypeComboModel getStreamTypeComboModel() {
		GuiStreamTypeComboModel model = (GuiStreamTypeComboModel)this.getJComboBoxStreamType().getModel();
		return model;
	}
}
