package presentation.timer;

import java.awt.Dimension;
import java.text.SimpleDateFormat;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.text.DateFormatter;

import model.BOTimer;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import control.ControlEnigmaTimerTab;
import control.ControlMain;
import control.ControlTabTimer;

/*
GuiEnigmaTimerPanel.java by Geist Alexander, Treito

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

public class GuiEnigmaTimerPanel extends GuiTimerPanel {
    private static String[] WOCHENTAGE = {ControlMain.getProperty("monday"), ControlMain.getProperty("tuesday"), ControlMain.getProperty("wednesday"), 
			ControlMain.getProperty("thursday"), ControlMain.getProperty("friday"), ControlMain.getProperty("saturday"), ControlMain.getProperty("sunday")};
	public static final int[] WOCHENTAGE_VALUE = {256, 512, 1024, 2048, 4096, 8192, 16384};
	public JRadioButton[] jRadioButtonWhtage = new JRadioButton[7];
	private JPanel jPanelDauerTimer = null;
	private JPanel jPanelTimerListe = null;
	private JPanel jPanelButtonsProgramTimer = null;
	private JPanel jPanelButtonsGui = null;
	private JButton jButtonReload = null;
	private JButton jButtonDeleteAllProgramTimer = null;
	private JButton jButtonDeleteSelectedProgramTimer = null;
	private JButton jButtonSenden = null;
	private JButton jButtonNewProgramtimer = null;
	private JButton jButtonDeleteAll = null;
	private JComboBox comboBoxSender = null;
	private JComboBox comboBoxRepeatProgramTimer = null;
	private JComboBox comboBoxEventType = null;
	private ImageIcon imageIconEnigma = null;
	private ControlEnigmaTimerTab control;
	public GuiEnigmaRecordTimerTableModel recordTimerTableModel;
	private JTable jTableRecordTimer = null;
	private JTable jTableSystemTimer = null;
	private JScrollPane jScrollPaneRecordTimerTable = null;
	private JFormattedTextField tfRecordTimerStartTime = null;
	private JFormattedTextField tfRecordTimerEndTime = null;
	private GuiTimerSenderComboModel senderComboModel = null;
	public GuiTimerTableSorter recordTimerSorter = null;

	public GuiEnigmaTimerPanel(ControlEnigmaTimerTab control) {
		this.setControl(control);
		initialize();
	}
	
	public GuiEnigmaTimerPanel() {
		initialize();
	}

	private  void initialize() {
		FormLayout layout = new FormLayout(
			      "f:330:grow, 110:grow, 160:grow, 4dlu, pref",  							// columns 
			      "pref, f:440:grow, pref, 8dlu, pref"); 			// rows
		PanelBuilder builder = new PanelBuilder(this, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();
		
		builder.addSeparator(ControlMain.getProperty("label_recordTimer"),					cc.xyw  (1, 1, 3));
		builder.add(this.getJScrollPaneRecordTimerTable(),   	cc.xyw  (1, 2, 3));
		builder.add(this.getJPanelDauerTimer(),	 				cc.xyw  (1, 3, 3));
		builder.addTitle(ControlMain.getProperty("label_actRecTimer"),				cc.xy    (5, 1));
		builder.add(this.getJPanelButtonsProgramTimer(), 		cc.xywh(5, 2, 1, 1,  CellConstraints.CENTER, CellConstraints.TOP));
		builder.add(this.getJPanelButtonsGui(),					cc.xywh(5, 3, 1, 2, CellConstraints.CENTER, CellConstraints.BOTTOM));
	}

	public ControlTabTimer getControl() {
		return control;
	}

	public void setControl(ControlEnigmaTimerTab control) {
		this.control = control;
	}
	
	private ImageIcon getImageIconEnigma() {
		if (imageIconEnigma == null) {
			//imageIconEnigma = new ImageIcon(ClassLoader.getSystemResource("ico/Enigma.gif"));
			imageIconEnigma = new ImageIcon(ClassLoader.getSystemResource("ico/enigma-logo.jpg"));
		}
		return imageIconEnigma;
	}
 
	public JTable getJTableRecordTimer() {
		if (jTableRecordTimer == null) {		
			recordTimerTableModel = new GuiEnigmaRecordTimerTableModel(control);	
			recordTimerSorter = new GuiTimerTableSorter(recordTimerTableModel);
			jTableRecordTimer = new JTable(recordTimerSorter);
			recordTimerSorter.setTableHeader(jTableRecordTimer.getTableHeader());
			jTableRecordTimer.setName("recordTimerTable");
			jTableRecordTimer.addMouseListener(control);
			jTableRecordTimer.setRowHeight(20);
			jTableRecordTimer.getColumnModel().getColumn(0).setMaxWidth(70);
			jTableRecordTimer.getColumnModel().getColumn(1).setMaxWidth(110);
			jTableRecordTimer.getColumnModel().getColumn(1).setPreferredWidth(110);
			jTableRecordTimer.getColumnModel().getColumn(2).setMaxWidth(110);
			jTableRecordTimer.getColumnModel().getColumn(2).setPreferredWidth(110);
			jTableRecordTimer.getColumnModel().getColumn(3).setMaxWidth(45);
			jTableRecordTimer.getColumnModel().getColumn(4).setMaxWidth(80);
			jTableRecordTimer.getColumnModel().getColumn(5).setMaxWidth(80);
			jTableRecordTimer.getColumnModel().getColumn(6).setPreferredWidth(80);
			TableColumn columnSender = jTableRecordTimer.getColumnModel().getColumn(1);			
			TableColumn columnStartTime = jTableRecordTimer.getColumnModel().getColumn(2);
			TableColumn columnEndTime = jTableRecordTimer.getColumnModel().getColumn(3);
			TableColumn columnRepeat = jTableRecordTimer.getColumnModel().getColumn(4);
			TableColumn columnEvent = jTableRecordTimer.getColumnModel().getColumn(5);
			columnSender.setCellEditor(new DefaultCellEditor(this.getComboBoxSender()));
			columnStartTime.setCellEditor(new DefaultCellEditor(this.getTfRecordTimerStartTime()));
			columnEndTime.setCellEditor(new DefaultCellEditor(this.getTfRecordTimerEndTime()));
			columnRepeat.setCellEditor(new DefaultCellEditor(this.getComboBoxRepeatProgramTimer()));
			columnEvent.setCellEditor(new DefaultCellEditor(this.getComboBoxEventType()));
		}
		return jTableRecordTimer;
	}
	
	

	private JScrollPane getJScrollPaneRecordTimerTable() {
		if (jScrollPaneRecordTimerTable == null) {
			jScrollPaneRecordTimerTable = new JScrollPane();
			jScrollPaneRecordTimerTable.setViewportView(getJTableRecordTimer());
		}
		return jScrollPaneRecordTimerTable;
	}
	
  
	public GuiEnigmaRecordTimerTableModel getRecordTimerTableModel() {
		return recordTimerTableModel;
	}
	/**
	 * @param senderTableModel The senderTableModel to set.
	 */
	public void setRecordTimerTableModel(GuiEnigmaRecordTimerTableModel TimerTableModel) {
		this.recordTimerTableModel = TimerTableModel;
	}
	
	
	public JPanel getJPanelButtonsProgramTimer() {
		if (jPanelButtonsProgramTimer == null) {
			jPanelButtonsProgramTimer = new JPanel();
			FormLayout layout = new FormLayout(
				      "pref",	 		//columna 
		      "pref, pref, pref, 20, pref");	//rows
			PanelBuilder builder = new PanelBuilder(jPanelButtonsProgramTimer, layout);
			CellConstraints cc = new CellConstraints();
			builder.add(this.getJButtonNewProgramtimer(),  				cc.xy	(1, 1));
			builder.add(this.getJButtonDeleteSelectedProgramTimer(),	cc.xy	(1, 2));
			builder.add(this.getJButtonDeleteAllProgramTimer(),			cc.xy	(1, 3));
			builder.add(new JLabel(this.getImageIconEnigma()),		cc.xy	(1, 5));
		}
		return jPanelButtonsProgramTimer;
	}
	
	public JPanel getJPanelButtonsGui() {
		if (jPanelButtonsGui == null) {
			jPanelButtonsGui = new JPanel();
			FormLayout layout = new FormLayout(
				      "Fill:pref",	 		//columna 
		      "pref, pref, pref, ");	//rows
			PanelBuilder builder = new PanelBuilder(jPanelButtonsGui, layout);
			CellConstraints cc = new CellConstraints();
			
			builder.add(this.getJButtonDeleteAl(),					cc.xy(1, 1));
			builder.add(this.getJButtonReload(),					cc.xy(1, 2));
			builder.add(this.getJButtonSenden(),					cc.xy(1, 3));
		}
		return jPanelButtonsGui;
	}
	
	public JPanel getJPanelDauerTimer() {
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
					jRadioButtonWhtage[i].addItemListener(control);
					jRadioButtonWhtage[i].setName("recordTimer");
					jRadioButtonWhtage[i].setActionCommand(Integer.toString(control.WOCHENTAGE_VALUE[i]));
					jRadioButtonWhtage[i].setEnabled(false);					
					jRadioButtonWhtage[i].setText(control.WOCHENTAGE[i]);
				}
				builder.add(jRadioButtonWhtage[i],cc.xy(a, 1));
				a = a+2;
			}
		}
		return jPanelDauerTimer;
	}	
  
	
	public JButton getJButtonDeleteAllProgramTimer() {
		if (jButtonDeleteAllProgramTimer == null) {
			jButtonDeleteAllProgramTimer = new JButton(ControlMain.getProperty("button_deleteAll"));
			jButtonDeleteAllProgramTimer.setActionCommand("deleteAllRecordTimer");
			jButtonDeleteAllProgramTimer.setPreferredSize(new Dimension(150,25));
			jButtonDeleteAllProgramTimer.addActionListener(control);
		}
		return jButtonDeleteAllProgramTimer;
	}
	
	

	public JButton getJButtonDeleteSelectedProgramTimer() {
		if (jButtonDeleteSelectedProgramTimer == null) {
			jButtonDeleteSelectedProgramTimer = new JButton(ControlMain.getProperty("button_deleteSelected"));
			jButtonDeleteSelectedProgramTimer.setActionCommand("deleteSelectedRecordTimer");
			jButtonDeleteSelectedProgramTimer.setPreferredSize(new Dimension(150,25));
			jButtonDeleteSelectedProgramTimer.addActionListener(control);
		}
		return jButtonDeleteSelectedProgramTimer;
	}
	
	

	public JButton getJButtonNewProgramtimer() {
		if (jButtonNewProgramtimer == null) {
			jButtonNewProgramtimer = new JButton(ControlMain.getProperty("button_create"));
			jButtonNewProgramtimer.setActionCommand("addProgramTimer");
			jButtonNewProgramtimer.setPreferredSize(new Dimension(150,25));
			jButtonNewProgramtimer.addActionListener(control);
		}
		return jButtonNewProgramtimer;
	}

	public JButton getJButtonSenden() {
		if (jButtonSenden == null) {
			jButtonSenden = new JButton(ControlMain.getProperty("button_send"));
			jButtonSenden.setActionCommand("send");
			jButtonSenden.addActionListener(control);
			jButtonSenden.setPreferredSize(new Dimension(150,25));
		}
		return jButtonSenden;
	}
	
	public JButton getJButtonDeleteAl() {
		if (jButtonDeleteAll == null) {
			jButtonDeleteAll = new JButton(ControlMain.getProperty("button_cleanup"));
			jButtonDeleteAll.setActionCommand("cleanup");
			jButtonDeleteAll.setPreferredSize(new Dimension(150,25));
			jButtonDeleteAll.addActionListener(control);
		}
		return jButtonDeleteAll;
	}
	
	public JButton getJButtonReload() {
		if (jButtonReload == null) {
			jButtonReload = new JButton(ControlMain.getProperty("button_reload"));
			jButtonReload.setActionCommand("reload");
			jButtonReload.setPreferredSize(new Dimension(150,25));
			jButtonReload.addActionListener(control);
		}
		return jButtonReload;
	}

	
	/**
	 * @return Returns the senderComboModel.
	 */
	public GuiTimerSenderComboModel getSenderComboModel() {
		return senderComboModel;
	}
	/**
	 * @param senderComboModel The senderComboModel to set.
	 */
	public void setSenderComboModel(
			GuiTimerSenderComboModel senderComboModel) {
		this.senderComboModel = senderComboModel;
	}
	/**
	 * @return Returns the comboBoxEventType.
	 */
	public JComboBox getComboBoxEventType() {
		if (comboBoxEventType == null) {
			comboBoxEventType = new JComboBox(new GuiTimerEventTypeComboModel(control));
		}
		return comboBoxEventType;
	}
	
	
	/**
	 * @return Returns the comboBoxRepeatProgramTimer.
	 */
	public JComboBox getComboBoxRepeatProgramTimer() {
		if (comboBoxRepeatProgramTimer == null) {
			comboBoxRepeatProgramTimer = new JComboBox(new GuiTimerRepeatComboModel(control));
		}
		return comboBoxRepeatProgramTimer;
	}
	/**
	 * @return Returns the comboBoxSender.
	 */
	public JComboBox getComboBoxSender() {
		if (comboBoxSender == null) {
			comboBoxSender = new JComboBox(new GuiTimerSenderComboModel(control));
		}
		return comboBoxSender;
	}
	/**
	 * 512 = Montags
	 * 1024 = Dienstags
	 * 2048 = Mittwochs
	 * 4096 = Donnerstags
	 * 8192 = Freitags
	 * 16384 = Samstags
	 * 32768 = Sonntags
	 */
	public void selectRepeatDaysForRecordTimer(BOTimer timer) {
		int result;		
		if (Integer.parseInt((String)timer.getEventRepeatId())>5) {
			result = Integer.parseInt((String)timer.getEventRepeatId());
			System.out.println(result);
			this.enableRecordTimerWeekdays();
		} else {
			result = Integer.parseInt((String)timer.getEventRepeatId());
			this.disableRecordTimerWeekdays();
		}
		for (int i = 0; i<7; i++){
			jRadioButtonWhtage[i].setSelected((result&WOCHENTAGE_VALUE[i])==WOCHENTAGE_VALUE[i]);
		}
	}
	
	public void enableRecordTimerWeekdays(boolean enabled) {		
		for (int i = 0; i<7; i++){
			jRadioButtonWhtage[i].setEnabled(enabled);
		}
	}
	public void enableRecordTimerWeekdays() {		
		enableRecordTimerWeekdays(true);
	}
	
	
	

	public void disableRecordTimerWeekdays() {
		enableRecordTimerWeekdays(false);
	}

	/**
	 * @return Returns the tfRecordTimerEndTime.
	 */
	public JFormattedTextField getTfRecordTimerEndTime() {
		if (tfRecordTimerEndTime == null) {
			tfRecordTimerEndTime = new JFormattedTextField(new SimpleDateFormat("HH:mm"));
			((DateFormatter)tfRecordTimerEndTime.getFormatter()).setAllowsInvalid(false);
			((DateFormatter)tfRecordTimerEndTime.getFormatter()).setOverwriteMode(true);
		}
		return tfRecordTimerEndTime;
	}

	/**
	 * @return Returns the tfRecordTimerStartTime.
	 */
	public JFormattedTextField getTfRecordTimerStartTime() {
		if (tfRecordTimerStartTime == null) {
			tfRecordTimerStartTime = new JFormattedTextField(new SimpleDateFormat("dd.MM.yy   HH:mm"));
			((DateFormatter)tfRecordTimerStartTime.getFormatter()).setAllowsInvalid(false);
			((DateFormatter)tfRecordTimerStartTime.getFormatter()).setOverwriteMode(true);
		}
		return tfRecordTimerStartTime;
	}

	
	
}
