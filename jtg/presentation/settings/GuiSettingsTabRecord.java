/*
 * GuiSettingsTabRecord.java by Geist Alexander
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
package presentation.settings;

import java.awt.Dimension;
import java.text.ParseException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.MaskFormatter;

import presentation.GuiTab;
import service.SerIconManager;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import control.ControlMain;
import control.ControlSettingsTabRecord;

public class GuiSettingsTabRecord extends GuiTab {

	private ControlSettingsTabRecord control;
	private JPanel panelEngineSettings = null;
	private JPanel panelRecordSettings = null;
	private JPanel panelServerRecordSettings = null;
	private JPanel panelQuickRecordSettings = null;
	private JPanel panelRecordtimeSettings = null;
	private JPanel panelNorth = null;
	private JComboBox jComboBoxStreamType = null;
	private JTextField jTextFieldUdrecOptions = null;
	private JRadioButton jRadioButtonUdrec;
	private JRadioButton jRadioButtonJGrabber;
	private JRadioButton jRadioButtonRecordAllPids;
	private JRadioButton jRadioButtonAC3ReplaceStereo;
	private JRadioButton jRadioButtonStereoReplaceAc3;
	private ButtonGroup buttonGroupStreamingEngine = new ButtonGroup();
	private ButtonGroup buttonGroupAudioOptions = new ButtonGroup();
	private JFormattedTextField tfServerPort = null;
	private JCheckBox cbStartStreamingServer;
	private JCheckBox cbStartPX;
	private JCheckBox cbRecordVtxt;
	private JCheckBox cbShutdownAfterRecord;
	private JCheckBox cbStopPlaybackAtRecord;
	private JSpinner recordMinsBefore, recordMinsAfter;

	private JCheckBox cbStoreEPG;
	private JCheckBox cbStoreLogAfterRecord;
	private SerIconManager iconManager = SerIconManager.getInstance();
	private JButton testButton;
	private JButton tagButton;
	private JButton tagButtonFile;
	
	private JTextField dirPattern;
	private JTextField filePattern;
	private JPanel panelFileNameSettings;

	public GuiSettingsTabRecord(ControlSettingsTabRecord ctrl) {
		super();
		this.setControl(ctrl);
		initialize();
	}

	protected void initialize() {
		FormLayout layout = new FormLayout("f:pref:grow, 10 f:pref:grow", // columns
				"pref, 25, t:pref, 25, pref,25,pref"); // rows
		PanelBuilder builder = new PanelBuilder(this, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();

		builder.add(this.getPanelNorth(), cc.xyw(1, 1, 3));
		builder.add(this.getPanelServerRecordSettings(), cc.xy(1, 3));
		builder.add(this.getPanelRecordtimeSettings(), cc.xy(1, 5));
		builder.add(this.getPanelFileNameSettings(), cc.xy(1, 7));
	}

	private JPanel getPanelNorth() {
		if (panelNorth == null) {
			panelNorth = new JPanel();
			FormLayout layout = new FormLayout("pref, 25, pref:grow", //columns
					"t:pref"); //rows
			PanelBuilder builder = new PanelBuilder(panelNorth, layout);
			CellConstraints cc = new CellConstraints();

			builder.add(this.getPanelRecordSettings(), cc.xy(1, 1));
			builder.add(this.getPanelEngineSettings(), cc.xy(3, 1));
		}
		return panelNorth;
	}

	private JPanel getPanelRecordSettings() {
		if (panelRecordSettings == null) {
			panelRecordSettings = new JPanel();
			FormLayout layout = new FormLayout("pref:grow", //columns
					"pref, pref, pref, pref, pref, pref, 10, pref, pref, pref"); //rows
			PanelBuilder builder = new PanelBuilder(panelRecordSettings, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator(ControlMain.getProperty("label_recordSettings"), cc.xy(1, 1));
			builder.add(this.getCbStartPX(), cc.xy(1, 2));
			builder.add(this.getCbStoreEPG(), cc.xy(1, 3));
			builder.add(this.getCbStoreLogAfterRecord(), cc.xy(1, 4));
			builder.add(this.getCbStopPlaybackAtRecord(), cc.xy(1, 5));

			builder.add(this.getCbRecordVtxt(), cc.xy(1, 6));
			builder.add(this.getJRadioButtonRecordAllPids(), cc.xy(1, 8));
			builder.add(this.getJRadioButtonAC3ReplaceStereo(), cc.xy(1, 9));
			builder.add(this.getJRadioButtonStereoReplaceAc3(), cc.xy(1, 10));
		}
		return panelRecordSettings;
	}

	private JPanel getPanelServerRecordSettings() {
		if (panelServerRecordSettings == null) {
			panelServerRecordSettings = new JPanel();
			FormLayout layout = new FormLayout("pref, 15, pref, 5, pref", //columns
					"pref, pref, pref"); //rows
			PanelBuilder builder = new PanelBuilder(panelServerRecordSettings, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator(ControlMain.getProperty("label_serverRecordSettings"), cc.xyw(1, 1, 5));
			builder.add(this.getCbStartStreamingServer(), cc.xy(1, 2));
			builder.add(new JLabel(ControlMain.getProperty("label_serverPort")), cc.xy(3, 2));
			builder.add(this.getTfServerPort(), cc.xy(5, 2));
			builder.add(this.getCbShutdownAfterRecord(), cc.xyw(1, 3, 5));
		}
		return panelServerRecordSettings;
	}

	private JPanel getPanelQuickRecordSettings() {
		if (panelQuickRecordSettings == null) {
			panelQuickRecordSettings = new JPanel();
			FormLayout layout = new FormLayout("pref", //columns
					"pref, pref"); //rows
			PanelBuilder builder = new PanelBuilder(panelQuickRecordSettings, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator(ControlMain.getProperty("label_quickRecordSettings"), cc.xywh(1, 1, 1, 1));
			builder.add(this.getJRadioButtonRecordAllPids(), cc.xy(1, 2));
		}
		return panelQuickRecordSettings;
	}

	private JPanel getPanelEngineSettings() {
		if (panelEngineSettings == null) {
			panelEngineSettings = new JPanel();
			FormLayout layout = new FormLayout("pref, 5, pref, 30, 250:grow", //columns
					"pref, pref, 10, pref"); //rows
			PanelBuilder builder = new PanelBuilder(panelEngineSettings, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator(ControlMain.getProperty("label_engine"), cc.xywh(1, 1, 5, 1));
			builder.add(this.getJRadioButtonJGrabber(), cc.xy(1, 2));
			builder.add(this.getJRadioButtonUdrec(), cc.xy(3, 2));
			builder.add(this.getJComboBoxStreamType(), cc.xy(5, 2));
			builder.add(new JLabel(ControlMain.getProperty("label_udrecOptions")), cc.xyw(1, 4, 4));
			builder.add(this.getJTextFieldUdrecOptions(), cc.xy(5, 4));
		}
		return panelEngineSettings;
	}

	private JPanel getPanelRecordtimeSettings() {
		if (panelRecordtimeSettings == null) {
			panelRecordtimeSettings = new JPanel();
			FormLayout layout = new FormLayout("pref, 5, pref", //columns
					"pref, pref, pref"); //rows
			PanelBuilder builder = new PanelBuilder(panelRecordtimeSettings, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator(ControlMain.getProperty("label_timerSettings"), cc.xyw(1, 1, 3));
			builder.add(this.getJSpinnerRecordMinsBefore(), cc.xy(1, 2));
			builder.add(new JLabel(ControlMain.getProperty("label_RecordBefore")), cc.xy(3, 2));
			builder.add(this.getJSpinnerRecordMinsAfter(), cc.xy(1, 3));
			builder.add(new JLabel(ControlMain.getProperty("label_RecordAfter")), cc.xy(3, 3));
		}
		return panelRecordtimeSettings;
	}

	private JPanel getPanelFileNameSettings() {
		if (panelFileNameSettings == null) {
			panelFileNameSettings = new JPanel();
			FormLayout layout = new FormLayout("pref,10,350:grow,10,pref,5,pref", //columns
					"pref, 10, pref,pref,pref,pref"); //rows
			PanelBuilder builder = new PanelBuilder(panelFileNameSettings, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator(ControlMain.getProperty("filep_filepattern"), cc.xyw(1, 1, 7));
			builder.add(new JLabel(ControlMain.getProperty("filep_directory")), cc.xy(1, 3));
			builder.add(getDirPattern(), cc.xy(3, 3));
			builder.add(getTagButton(), cc.xy(5, 3));
			builder.add(getTestButton(), cc.xy(7, 3));

			builder.add(new JLabel(ControlMain.getProperty("filep_file")), cc.xy(1, 4));
			builder.add(getFilePattern(), cc.xy(3, 4));
			builder.add(getTagButtonFile(), cc.xy(5, 4));

		}
		return panelFileNameSettings;
	}

	/**
	 * @return
	 */
	public JButton getTestButton() {
		if (testButton == null) {
			testButton = new JButton("Test");
			testButton.setName("TestPattern");
			testButton.addActionListener(control);
		}
		return testButton;
	}

	/**
	 * @return
	 */
	public JButton getTagButton() {
		if (tagButton == null) {
			tagButton = new JButton(ControlMain.getProperty("filep_tagName"));
			tagButton.setActionCommand("Tags");
			tagButton.addActionListener(control);
		}
		return tagButton;
	}

	/**
	 * @return
	 */
	public JButton getTagButtonFile() {
		if (tagButtonFile == null) {
			tagButtonFile = new JButton(ControlMain.getProperty("filep_tagName"));
			tagButtonFile.setActionCommand("TagsFile");
			tagButtonFile.addActionListener(control);
		}
		return tagButtonFile;
	}
	
	/**
	 * @return
	 */
	public JTextField getDirPattern() {

		if (dirPattern == null) {
			dirPattern = new JTextField();
			dirPattern.setName("dirPattern");
			dirPattern.addKeyListener(control);
		}
		return dirPattern;
	}

	/**
	 * @return
	 */
	public JTextField getFilePattern() {

		if (filePattern == null) {
			filePattern = new JTextField();
			filePattern.setName("filePattern");
			filePattern.addKeyListener(control);
		}
		return filePattern;
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
			jComboBoxStreamType.setPreferredSize(new java.awt.Dimension(50, 19));
		}
		return jComboBoxStreamType;
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
				((MaskFormatter) tfServerPort.getFormatter()).setAllowsInvalid(false);
				((MaskFormatter) tfServerPort.getFormatter()).setOverwriteMode(true);
				tfServerPort.setPreferredSize(new java.awt.Dimension(40, 19));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return tfServerPort;
	}

	/**
	 * @return Returns the cbStartStreamingServer.
	 */
	public JCheckBox getCbStartStreamingServer() {
		if (cbStartStreamingServer == null) {
			cbStartStreamingServer = new JCheckBox(ControlMain.getProperty("cbStartServer"));
			cbStartStreamingServer.setActionCommand("startStreamingServer");
			cbStartStreamingServer.addActionListener(control);
		}
		return cbStartStreamingServer;
	}
	/**
	 * @return Returns the checkbox for storing epg.
	 */
	public JCheckBox getCbStoreEPG() {
		if (cbStoreEPG == null) {
			cbStoreEPG = new JCheckBox(ControlMain.getProperty("cbStoreEPG"));
			cbStoreEPG.setActionCommand("storeEPG");
			cbStoreEPG.addActionListener(control);
		}
		return cbStoreEPG;
	}

	/**
	 * @return Returns the checkbox for storing epg.
	 */
	public JCheckBox getCbStoreLogAfterRecord() {
		if (cbStoreLogAfterRecord == null) {
			cbStoreLogAfterRecord = new JCheckBox(ControlMain.getProperty("cbStoreLogAfterRecord"));
			cbStoreLogAfterRecord.setActionCommand("storeLogAfterRecord");
			cbStoreLogAfterRecord.addActionListener(control);
		}
		return cbStoreLogAfterRecord;
	}

	/**
	 * @return Returns the checkbox for storing epg.
	 */
	public JCheckBox getCbStopPlaybackAtRecord() {
		if (cbStopPlaybackAtRecord == null) {
			cbStopPlaybackAtRecord = new JCheckBox(ControlMain.getProperty("cbStopPlaybackAtRecord"));
			cbStopPlaybackAtRecord.setActionCommand("cbStopPlaybackAtRecord");
			cbStopPlaybackAtRecord.addActionListener(control);
		}
		return cbStopPlaybackAtRecord;
	}

	/**
	 * @return Returns the cbShutdownAfterRecord.
	 */
	public JCheckBox getCbShutdownAfterRecord() {
		if (cbShutdownAfterRecord == null) {
			cbShutdownAfterRecord = new JCheckBox(ControlMain.getProperty("cbShutdownAfterRecord"));
			cbShutdownAfterRecord.setActionCommand("shutdownAfterRecord");
			cbShutdownAfterRecord.addActionListener(control);
		}
		return cbShutdownAfterRecord;
	}
	/**
	 * @return Returns the jTextFieldUdrecOptions.
	 */
	public JTextField getJTextFieldUdrecOptions() {
		if (jTextFieldUdrecOptions == null) {
			jTextFieldUdrecOptions = new JTextField();
			jTextFieldUdrecOptions.addKeyListener(control);
			jTextFieldUdrecOptions.setName("udrecOptions");
			jTextFieldUdrecOptions.setPreferredSize(new Dimension(340, 19));
		}
		return jTextFieldUdrecOptions;
	}

	public JSpinner getJSpinnerRecordMinsBefore() {
		if (recordMinsBefore == null) {
			SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 60, 1);
			recordMinsBefore = new JSpinner(model);
			recordMinsBefore.setToolTipText(ControlMain.getProperty("buttonRecordBefore"));
			recordMinsBefore.setName("recordBefore");
			recordMinsBefore.addChangeListener(control);
		}
		return recordMinsBefore;
	}
	public JSpinner getJSpinnerRecordMinsAfter() {
		if (recordMinsAfter == null) {
			SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 60, 1);
			recordMinsAfter = new JSpinner(model);
			recordMinsAfter.setToolTipText(ControlMain.getProperty("buttonRecordAfter"));
			recordMinsAfter.setName("recordAfter");
			recordMinsAfter.addChangeListener(control);
		}
		return recordMinsAfter;
	}
	/**
	 * @return Returns the cbStartPX.
	 */
	public JCheckBox getCbStartPX() {
		if (cbStartPX == null) {
			cbStartPX = new JCheckBox(ControlMain.getProperty("cbStartPX"));
			cbStartPX.setActionCommand("startPX");
			cbStartPX.addActionListener(control);
		}
		return cbStartPX;
	}
	/**
	 * @return Returns the jRadioButtonAC3ReplaceStereo.
	 */
	public JRadioButton getJRadioButtonAC3ReplaceStereo() {
		if (jRadioButtonAC3ReplaceStereo == null) {
			jRadioButtonAC3ReplaceStereo = new JRadioButton(ControlMain.getProperty("rbAC3ReplaceStereo"));
			jRadioButtonAC3ReplaceStereo.setActionCommand("rbAC3ReplaceStereo");
			jRadioButtonAC3ReplaceStereo.addActionListener(control);
			buttonGroupAudioOptions.add(jRadioButtonAC3ReplaceStereo);
		}
		return jRadioButtonAC3ReplaceStereo;
	}
	/**
	 * @return Returns the jRadioButtonStereoReplaceAc3.
	 */
	public JRadioButton getJRadioButtonStereoReplaceAc3() {
		if (jRadioButtonStereoReplaceAc3 == null) {
			jRadioButtonStereoReplaceAc3 = new JRadioButton(ControlMain.getProperty("rbStereoReplaceAc3"));
			jRadioButtonStereoReplaceAc3.setActionCommand("rbStereoReplaceAc3");
			jRadioButtonStereoReplaceAc3.addActionListener(control);
			buttonGroupAudioOptions.add(jRadioButtonStereoReplaceAc3);
		}
		return jRadioButtonStereoReplaceAc3;
	}
	/**
	 * @return Returns the jRadioButtonRecordAllPids.
	 */
	public JRadioButton getJRadioButtonRecordAllPids() {
		if (jRadioButtonRecordAllPids == null) {
			jRadioButtonRecordAllPids = new JRadioButton(ControlMain.getProperty("rbRecordAllPids"));
			jRadioButtonRecordAllPids.setActionCommand("rbRecordAllPids");
			jRadioButtonRecordAllPids.addActionListener(control);
			buttonGroupAudioOptions.add(jRadioButtonRecordAllPids);
		}
		return jRadioButtonRecordAllPids;
	}

	/**
	 * @return Returns the jRadioButtonRecordAllPids.
	 */
	public JCheckBox getCbRecordVtxt() {
		if (cbRecordVtxt == null) {
			cbRecordVtxt = new JCheckBox(ControlMain.getProperty("cbRecordVtxt"));
			cbRecordVtxt.setActionCommand("recordVtxt");
			cbRecordVtxt.addActionListener(control);
		}
		return cbRecordVtxt;
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
		GuiStreamTypeComboModel model = (GuiStreamTypeComboModel) this.getJComboBoxStreamType().getModel();
		return model;
	}
	/**
	 * @return Returns the control.
	 */
	public ControlSettingsTabRecord getControl() {
		return control;
	}
	/**
	 * @param control
	 *            The control to set.
	 */
	public void setControl(ControlSettingsTabRecord control) {
		this.control = control;
	}
}