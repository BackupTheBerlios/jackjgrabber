package presentation.timer;
/*
GuiTimerEditView.java by Geist Alexander 

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
import java.text.SimpleDateFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import presentation.settings.GuiStreamTypeComboModel;
import service.SerIconManager;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import control.ControlMain;
import control.ControlNeutrinoTimerTab;
import control.ControlTimerEditView;

public class GuiTimerEditView extends JFrame{
    
    ControlTimerEditView control;
	private JPanel panelEngineSettings;
	private JPanel panelRecordSettings;
	private JPanel panelRecordPath;
	private JPanel panelFileNameSettings;
	private JPanel panelMainOptions;
	private JPanel mainPanel;
	private JPanel jPanelDauerTimer;
	
	private JComboBox jComboBoxStreamType;
	private JComboBox jComboBoxBoxSender;
	private JComboBox jComboBoxRepeatRecordTimer;
	
	private JTextField jTextFieldUdrecOptions;
	private JTextField jTextFieldRecordSavePath;
	private JTextField jTextFieldDirPattern;
	private JTextField jTextFieldFilePattern;
	private JTextField jTextFieldDescription;
	
	private JFormattedTextField tfRecordTimerStartTime;
	private JFormattedTextField tfRecordTimerEndTime;
	
	private JRadioButton jRadioButtonUdrec;
	private JRadioButton jRadioButtonJGrabber;
	private JRadioButton jRadioButtonRecordAllPids;
	private JRadioButton jRadioButtonAC3ReplaceStereo;
	private JRadioButton jRadioButtonStereoReplaceAc3;
	
	private ButtonGroup buttonGroupStreamingEngine = new ButtonGroup();
	private ButtonGroup buttonGroupAudioOptions = new ButtonGroup();
	
	private JCheckBox cbStartPX;
	private JCheckBox cbRecordVtxt;
	private JCheckBox cbShutdownAfterRecord;
	private JCheckBox cbStopPlaybackAtRecord;
	private JCheckBox cbStoreEPG;
	private JCheckBox cbStoreLogAfterRecord;
	
	private JButton jButtonTest;
	private JButton jButtonDirTag;
	private JButton jButtonFileTag;
	private JButton jButtonOk;
	private JButton jButtonCancel;
	private JButton jButtonRecordPathFileChooser;
	private JButton jButtonUdrecOptions;
	
	public JRadioButton[] jRadioButtonWhtage = new JRadioButton[7];

	private SerIconManager iconManager = SerIconManager.getInstance();
	
	
    
    public GuiTimerEditView(ControlTimerEditView control) {
		this.setControl(control);
		initialize();
		this.setResizable(false);
		this.setTitle(control.getTimer().getMainTimer().getSenderName()+" "+control.getTimer().getMainTimer().getStartDate());
		pack();
	}
    
    public void initialize() {
        this.getContentPane().add(this.getMainPanel());
    }
    
    
    private JPanel getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new JPanel();
			FormLayout layout = new FormLayout("pref, 25, pref:grow", //columns
					"pref, 10, pref, 15, t:pref, 15, pref, 15, pref, 15, pref"); //rows
			PanelBuilder builder = new PanelBuilder(mainPanel, layout);
			builder.setDefaultDialogBorder();
			CellConstraints cc = new CellConstraints();

			builder.add(this.getPanelMainOptions(),			cc.xyw(1, 1, 3));
			builder.add(this.getPanelDauerTimer(),				cc.xyw(1, 3, 3));
			builder.add(this.getPanelRecordSettings(), 		cc.xy(1, 5));
			builder.add(this.getPanelEngineSettings(),		cc.xy(3, 5));
			builder.add(this.getPanelRecordPath(), 			cc.xyw(1, 7, 3));
			builder.add(this.getPanelFileNameSettings(), 	cc.xyw(1, 9, 3));
			builder.add(ButtonBarFactory.buildOKCancelBar(this.getJButtonCancel(), this.getJButtonOk()),  cc.xyw(1, 11, 3));
		}
		return mainPanel;
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

	private JPanel getPanelEngineSettings() {
		if (panelEngineSettings == null) {
			panelEngineSettings = new JPanel();
			FormLayout layout = new FormLayout("pref, 5, pref, 5, 150:grow", //columns
			"pref, pref, 10, pref, 20, pref"); //rows
			PanelBuilder builder = new PanelBuilder(panelEngineSettings, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator(ControlMain.getProperty("label_engine"), cc.xywh(1, 1, 5, 1));
			builder.add(this.getJRadioButtonJGrabber(), cc.xy(1, 2));
			builder.add(this.getJRadioButtonUdrec(), cc.xy(3, 2));
			builder.add(this.getJComboBoxStreamType(), cc.xy(5, 2));
			builder.add(this.getJButtonUdrecOptions(), cc.xyw(1, 4, 1));
			builder.add(this.getJTextFieldUdrecOptions(), cc.xyw(3, 4, 3));
			builder.add(this.getCbShutdownAfterRecord(), cc.xyw(1, 6, 5));
		}
		return panelEngineSettings;
	}

	private JPanel getPanelFileNameSettings() {
		if (panelFileNameSettings == null) {
			panelFileNameSettings = new JPanel();
			FormLayout layout = new FormLayout("pref,10,350:grow,10,pref,5,pref", //columns
					"pref, pref,pref,pref,pref"); //rows
			PanelBuilder builder = new PanelBuilder(panelFileNameSettings, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator(ControlMain.getProperty("filep_filepattern"), cc.xyw(1, 1, 7));
			builder.add(new JLabel(ControlMain.getProperty("filep_directory")), cc.xy(1, 2));
			builder.add(getJTextFieldDirPattern(), cc.xy(3, 2));
			builder.add(getJButtonDirTag(), cc.xy(5, 2));
			builder.add(getJButtonTest(), cc.xy(7, 2));

			builder.add(new JLabel(ControlMain.getProperty("filep_file")), cc.xy(1, 3));
			builder.add(this.getJTextFieldFilePattern(), cc.xy(3, 3));
			builder.add(getJButtonFileTag(), cc.xy(5, 3));

		}
		return panelFileNameSettings;
	}
	
	private JPanel getPanelRecordPath() {
	    if (panelRecordPath==null) {
	        panelRecordPath = new JPanel();
	        FormLayout layout = new FormLayout(
					  "pref, 10, f:pref:grow, 5, pref",  		// columns 
					  "pref"); 			// rows
	        PanelBuilder builder = new PanelBuilder(panelRecordPath, layout);
	        builder.setDefaultDialogBorder();
	        CellConstraints cc = new CellConstraints();
					
	        builder.add(new JLabel(ControlMain.getProperty("label_recordPath")),		cc.xy	(1, 1));
	        builder.add(this.getJTextFieldRecordSavePath(),								cc.xy	(3, 1));
	        builder.add(this.getJButtonRecordPathFileChooser(),							cc.xy	(5, 1));    
	    }
	    return panelRecordPath;
	}
	
	private JPanel getPanelMainOptions() {
	    if (panelMainOptions==null) {
	    	panelMainOptions = new JPanel();
	        FormLayout layout = new FormLayout(
					  "100, 5, 100, 5, 60, 5, 90, 5, pref:grow",  		// columns 
					  "pref, 5, pref"); 			// rows
	        PanelBuilder builder = new PanelBuilder(panelMainOptions, layout);
	        CellConstraints cc = new CellConstraints();
					
	        builder.add(new JLabel(ControlMain.getProperty("sender")),		cc.xy	(1, 1));
	        builder.add(this.getJComboBoxBoxSender(),							cc.xy	(1, 3));
	        builder.add(new JLabel(ControlMain.getProperty("start")),		cc.xy	(3, 1));
	        builder.add(this.getTfRecordTimerStartTime(),					cc.xy	(3, 3));
	        builder.add(new JLabel(ControlMain.getProperty("end")),			cc.xy	(5, 1));
	        builder.add(this.getTfRecordTimerEndTime(),						cc.xy	(5, 3));
	        builder.add(new JLabel(ControlMain.getProperty("repeat")),		cc.xy	(7, 1));
	        builder.add(this.getJComboBoxRepeatRecordTimer(),				cc.xy	(7, 3));
	        builder.add(new JLabel(ControlMain.getProperty("title")),		cc.xy	(9, 1));
	        builder.add(this.getJTextFieldDescription(),							cc.xy	(9, 3));
	    }
	    return panelMainOptions;
	}
	
	public JPanel getPanelDauerTimer() {
		if (jPanelDauerTimer == null) {
			jPanelDauerTimer = new JPanel();
			FormLayout layout = new FormLayout(
				      "pref, 20, pref, 20, pref, 20, pref, 20, pref, 20, pref, 20, pref",	 		//columna
				      "pref");	//rows
			PanelBuilder builder = new PanelBuilder(jPanelDauerTimer, layout);
			CellConstraints cc = new CellConstraints();
			
			int a= 1;
			for(int i = 0 ; i< 7; i++){
				if (jRadioButtonWhtage[i]== null) {
					jRadioButtonWhtage[i] = new JRadioButton();
					jRadioButtonWhtage[i].addActionListener(control);
					jRadioButtonWhtage[i].setName(Integer.toString(ControlNeutrinoTimerTab.weekdays_value[i]));
					jRadioButtonWhtage[i].setActionCommand("recordTimer");
					jRadioButtonWhtage[i].setEnabled(false);					
					jRadioButtonWhtage[i].setText(ControlNeutrinoTimerTab.weekdays[i]);
				}
				builder.add(jRadioButtonWhtage[i],cc.xy(a, 1));
				a = a+2;
			}
		}
		return jPanelDauerTimer;
	}
	
	public void enableRecordTimerWeekdays(boolean enabled) {		
		for (int i = 0; i<7; i++){
			jRadioButtonWhtage[i].setEnabled(enabled);
		}
	}
	
	/**
	 * @return jButtonFileTag
	 */
	public JButton getJButtonUdrecOptions() {
		if (jButtonUdrecOptions == null) {
			jButtonUdrecOptions = new JButton(ControlMain.getProperty("label_udrecOptions"));
			jButtonUdrecOptions.setActionCommand("udrecOptions");
			jButtonUdrecOptions.addActionListener(control);
		}
		return jButtonUdrecOptions;
	}
	
	/**
	 * @return jButtonTest
	 */
	public JButton getJButtonTest() {
		if (jButtonTest == null) {
			jButtonTest = new JButton("Test");
			jButtonTest.setName("TestPattern");
			jButtonTest.addActionListener(control);
		}
		return jButtonTest;
	}

	/**
	 * @return jButtonDirTag
	 */
	public JButton getJButtonDirTag() {
		if (jButtonDirTag == null) {
			jButtonDirTag = new JButton(ControlMain.getProperty("filep_tagName"));
			jButtonDirTag.setActionCommand("Tags");
			jButtonDirTag.addActionListener(control);
		}
		return jButtonDirTag;
	}
	
	public JButton getJButtonFileTag() {
		if (jButtonFileTag == null) {
			jButtonFileTag = new JButton(ControlMain.getProperty("filep_tagName"));
			jButtonFileTag.setActionCommand("TagsFile");
			jButtonFileTag.addActionListener(control);
		}
		return jButtonFileTag;
	}
	
	/**
	 * @return jButtonOk
	 */
	public JButton getJButtonOk() {
		if (jButtonOk == null) {
			jButtonOk = new JButton(ControlMain.getProperty("button_ok"));
			jButtonOk.setActionCommand("ok");
			jButtonOk.addActionListener(control);
		}
		return jButtonOk;
	}
	
	/**
	 * @return jButtonCancel
	 */
	public JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton(ControlMain.getProperty("button_cancel"));
			jButtonCancel.setActionCommand("cancel");
			jButtonCancel.addActionListener(control);
		}
		return jButtonCancel;
	}

	/**
	 * @return jTextFieldDirPattern
	 */
	public JTextField getJTextFieldDirPattern() {

		if (jTextFieldDirPattern == null) {
			jTextFieldDirPattern = new JTextField();
			jTextFieldDirPattern.setName("jTextFieldDirPattern");
			jTextFieldDirPattern.addKeyListener(control);
		}
		return jTextFieldDirPattern;
	}

	/**
	 * @return jTextFieldFilePattern
	 */
	public JTextField getJTextFieldFilePattern() {

		if (jTextFieldFilePattern == null) {
			jTextFieldFilePattern = new JTextField();
			jTextFieldFilePattern.setName("jTextFieldFilePattern");
			jTextFieldFilePattern.addKeyListener(control);
		}
		return jTextFieldFilePattern;
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
			jTextFieldUdrecOptions.setEditable(false);
			jTextFieldUdrecOptions.setName("udrecOptions");
			jTextFieldUdrecOptions.setPreferredSize(new Dimension(340, 19));
		}
		return jTextFieldUdrecOptions;
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
	 * @return Returns the jButtonRecordPathFileChooser.
	 */
	public JButton getJButtonRecordPathFileChooser() {
		if (jButtonRecordPathFileChooser == null) {
			jButtonRecordPathFileChooser = new JButton(iconManager.getIcon("Open16.gif"));
			jButtonRecordPathFileChooser.setActionCommand("recordPath");
			jButtonRecordPathFileChooser.addActionListener(control);
		}
		return jButtonRecordPathFileChooser;
	}
	/**
	 * @return Returns the jTextFieldRecordSavePath.
	 */
	public JTextField getJTextFieldRecordSavePath() {
		if (jTextFieldRecordSavePath == null) {
			jTextFieldRecordSavePath = new JTextField();
			jTextFieldRecordSavePath.setName("tfRecordPath");
			jTextFieldRecordSavePath.setPreferredSize(new Dimension(340, 19));
			jTextFieldRecordSavePath.setEditable(false);
		}
		return jTextFieldRecordSavePath;
	}
	/**
	 * @return Returns the streamTypeComboModel.
	 */
	public GuiStreamTypeComboModel getStreamTypeComboModel() {
		GuiStreamTypeComboModel model = (GuiStreamTypeComboModel) this.getJComboBoxStreamType().getModel();
		return model;
	}
	/**
	 * @return Returns the jComboBoxRepeatRecordTimer.
	 */
	public JComboBox getJComboBoxRepeatRecordTimer() {
		if (jComboBoxRepeatRecordTimer == null) {
			jComboBoxRepeatRecordTimer = new JComboBox(new GuiTimerRepeatComboModel(control.getControlTimer()));
			jComboBoxRepeatRecordTimer.addItemListener(control);
			jComboBoxRepeatRecordTimer.setName("repeat");
		}
		return jComboBoxRepeatRecordTimer;
	}
	/**
	 * @return Returns the jComboBoxBoxSender.
	 */
	public JComboBox getJComboBoxBoxSender() {
		if (jComboBoxBoxSender == null) {
			jComboBoxBoxSender = new JComboBox(new GuiTimerSenderComboModel(control.getControlTimer()));
			jComboBoxBoxSender.addItemListener(control);
			jComboBoxBoxSender.setName("sender");
			if (control.getTimer().getMainTimer().getModifiedId()==null) {
			    jComboBoxBoxSender.setEnabled(false);
			}
		}
		return jComboBoxBoxSender;
	}
	/**
	 * @return Returns the tfRecordTimerStartTime.
	 */
	public JFormattedTextField getTfRecordTimerStartTime() {
		if (tfRecordTimerStartTime == null) {
			tfRecordTimerStartTime = new JFormattedTextField(new SimpleDateFormat("dd.MM.yy   HH:mm"));
			tfRecordTimerStartTime.addKeyListener(control);
			tfRecordTimerStartTime.setName("startTime");
		}
		return tfRecordTimerStartTime;
	}
	/**
	 * @return Returns the tfRecordTimerEndTime.
	 */
	public JFormattedTextField getTfRecordTimerEndTime() {
		if (tfRecordTimerEndTime == null) {
			tfRecordTimerEndTime = new JFormattedTextField(new SimpleDateFormat("HH:mm"));
			tfRecordTimerEndTime.addKeyListener(control);
			tfRecordTimerEndTime.setName("stopTime");
		}
		return tfRecordTimerEndTime;
	}
	/**
	 * @return Returns the tfRecordTimerEndTime.
	 */
	public JTextField getJTextFieldDescription() {
		if (jTextFieldDescription == null) {
			jTextFieldDescription = new JTextField();
			jTextFieldDescription.setName("description");
			jTextFieldDescription.addKeyListener(control);
		}
		return jTextFieldDescription;
	}
    /**
     * @return Returns the control.
     */
    public ControlTimerEditView getControl() {
        return control;
    }
    /**
     * @param control The control to set.
     */
    public void setControl(ControlTimerEditView control) {
        this.control = control;
    }
}
