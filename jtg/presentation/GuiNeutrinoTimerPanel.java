package presentation;
/*
GuiNeutrinoTimerPanel.java by Geist Alexander 

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


import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import control.ControlMain;
import control.ControlNeutrinoTimerTab;
import control.ControlTab;

public class GuiNeutrinoTimerPanel extends GuiTimerPanel {
	
	public JRadioButton[] jRadioButtonWhtage = new JRadioButton[7];
	public JRadioButton[] jRadioButtonWhtage2 = new JRadioButton[7];
	
	private JPanel jPanelDauerTimer = null;
	private JPanel jPanelDauerTimer2 = null;
	private JPanel jPanelTimerListe = null;
	private JPanel jPanelButtonsRecordTimer = null;
	private JPanel jPanelButtonsSystemTimer = null;
	private JPanel jPanelButtonsGui = null;
	private JButton jButtonReload = null;
	private JButton jButtonDeleteAllRecordTimer = null;
	private JButton jButtonDeleteSelectedRecordTimer = null;
	private JButton jButtonDeleteAllSystemTimer = null;
	private JButton jButtonDeleteSelectedSystemTimer = null;
	private JButton jButtonSenden = null;
	private JButton jButtonNewRecordTimer = null;
	private JButton jButtonNewSystemtimer = null;
	private JButton jButtonDeleteAll = null;
	private JComboBox comboBoxSender = null;
	private JComboBox comboBoxRepeatRecordTimer = null;
	private JComboBox comboBoxEventType = null;
	private JComboBox comboBoxRepeatSystemTimer = null;
	private ImageIcon imageIconNeutrino = null;
	private JTable jTableRecordTimer = null;
	private JTable jTableSystemTimer = null;
	private JScrollPane jScrollPaneRecordTimerTable = null;
	private JScrollPane jScrollPaneSystemTimerTable = null;

	private JFormattedTextField tfRecordTimerStartTime = null;
	private JFormattedTextField tfRecordTimerEndTime = null;
	private JFormattedTextField tfSystemTimerStartTime = null;

	private ControlNeutrinoTimerTab control;
	public GuiNeutrinoRecordTimerTableModel recordTimerTableModel;
	public GuiNeutrinoSystemTimerTableModel systemTimerTableModel;
	private GuiTimerSenderComboModel senderComboModel = null;
	public GuiTimerTableSorter recordTimerSorter = null;
	public GuiTimerTableSorter systemTimerSorter = null;


	public GuiNeutrinoTimerPanel(ControlNeutrinoTimerTab control) {
		this.setControl(control);
		initialize();
	}

	public GuiNeutrinoTimerPanel() {
		initialize();
	}

	private  void initialize() {
		FormLayout layout = new FormLayout(
			      "f:320:grow, 10, 100:grow, 160:grow, 7, pref",							// columns
			      "pref, t:220:grow, pref, 10, pref, t:100, b:100:grow, pref");				// rows
		PanelBuilder builder = new PanelBuilder(this, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();

		builder.addSeparator(ControlMain.getProperty("label_recordTimer"),						cc.xyw  (1, 1, 4));
		builder.add(this.getJScrollPaneRecordTimerTable(),   											cc.xyw  (1, 2, 4));
		builder.add(this.getJPanelDauerTimer(),	 														cc.xyw  (1, 3, 4, CellConstraints.CENTER, CellConstraints.TOP));
		builder.addSeparator(ControlMain.getProperty("label_systemTimer"),						cc.xyw  (1, 5, 3));
		builder.add(this.getJScrollPaneSystemTimerTable(),  											cc.xywh (1, 6, 1, 2));
		builder.add(this.getJPanelDauerTimer2(), 															cc.xywh (3, 6, 1, 2, CellConstraints.LEFT, CellConstraints.TOP));
		builder.addTitle(ControlMain.getProperty("label_actRecTimer"),								cc.xy     (6, 1));
		builder.add(this.getJPanelButtonsRecordTimer(), 												cc.xywh (6, 2, 1, 1,  CellConstraints.CENTER, CellConstraints.TOP));
		builder.addTitle(ControlMain.getProperty("label_actSysTimer"),								cc.xy   (6, 5, CellConstraints.CENTER, CellConstraints.DEFAULT));
		builder.add(this.getJPanelButtonsSystemTimer(),												cc.xywh (6, 6, 1, 1, CellConstraints.CENTER, CellConstraints.TOP));
		builder.add(this.getJPanelButtonsGui(),																cc.xywh (6, 7, 1, 2, CellConstraints.CENTER, CellConstraints.BOTTOM));
	}

	public ControlTab getControl() {
		return control;
	}

	public void setControl(ControlNeutrinoTimerTab control) {
		this.control = control;
	}

	private ImageIcon getImageIconNeutrino() {
		if (imageIconNeutrino == null) {
			//imageIconNeutrino = new ImageIcon(ClassLoader.getSystemResource("ico/neutrino.gif"));
			imageIconNeutrino = new ImageIcon(ClassLoader.getSystemResource("ico/neutrino-logo.jpg"));
		}
		return imageIconNeutrino;
	}

	public JTable getJTableRecordTimer() {
		if (jTableRecordTimer == null) {
			recordTimerTableModel = new GuiNeutrinoRecordTimerTableModel(control);			
			recordTimerSorter = new GuiTimerTableSorter(recordTimerTableModel);
			jTableRecordTimer = new JTable(recordTimerSorter);
			recordTimerSorter.setTableHeader(jTableRecordTimer.getTableHeader());

			jTableRecordTimer.setName("recordTimerTable");
			jTableRecordTimer.addMouseListener(control);
			jTableRecordTimer.setRowHeight(20);
			jTableRecordTimer.getColumnModel().getColumn(0).setMaxWidth(100);
			jTableRecordTimer.getColumnModel().getColumn(0).setPreferredWidth(100);
			jTableRecordTimer.getColumnModel().getColumn(1).setPreferredWidth(100);
			jTableRecordTimer.getColumnModel().getColumn(1).setMaxWidth(100);
			jTableRecordTimer.getColumnModel().getColumn(2).setMaxWidth(50);
			jTableRecordTimer.getColumnModel().getColumn(3).setPreferredWidth(80);
			jTableRecordTimer.getColumnModel().getColumn(3).setMaxWidth(80);


			TableColumn columnSender = jTableRecordTimer.getColumnModel().getColumn(0);
			TableColumn columnStartTime = jTableRecordTimer.getColumnModel().getColumn(1);
			TableColumn columnEndTime = jTableRecordTimer.getColumnModel().getColumn(2);
			TableColumn columnRepeat = jTableRecordTimer.getColumnModel().getColumn(3);

			columnSender.setCellEditor(new DefaultCellEditor(this.getComboBoxSender()));
			columnStartTime.setCellEditor(new DefaultCellEditor(this.getTfRecordTimerStartTime()));
			columnEndTime.setCellEditor(new DefaultCellEditor(this.getTfRecordTimerEndTime()));
			columnRepeat.setCellEditor(new DefaultCellEditor(this.getComboBoxRepeatRecordTimer()));
		}
		return jTableRecordTimer;
	}

	public JTable getJTableSystemTimer() {
		if (jTableSystemTimer == null) {
			systemTimerTableModel = new GuiNeutrinoSystemTimerTableModel(control);			
			systemTimerSorter = new GuiTimerTableSorter(systemTimerTableModel);
			jTableSystemTimer = new JTable(systemTimerSorter);
			systemTimerSorter.setTableHeader(jTableSystemTimer.getTableHeader());
			
			jTableSystemTimer.setName("systemTimerTable");
			jTableSystemTimer.addMouseListener(control);
			jTableSystemTimer.setRowHeight(20);
			jTableSystemTimer.getColumnModel().getColumn(1).setPreferredWidth(120);
			jTableSystemTimer.getColumnModel().getColumn(1).setMaxWidth(120);

			TableColumn columnEventType = jTableSystemTimer.getColumnModel().getColumn(0);
			TableColumn columnStartTime = jTableSystemTimer.getColumnModel().getColumn(1);
			TableColumn columnRepeat = jTableSystemTimer.getColumnModel().getColumn(2);

			columnEventType.setCellEditor(new DefaultCellEditor(this.getComboBoxEventType()));
			columnStartTime.setCellEditor(new DefaultCellEditor(this.getTfSystemTimerStartTime()));
			columnRepeat.setCellEditor(new DefaultCellEditor(this.getComboBoxRepeatSystemTimer()));
		}
		return jTableSystemTimer;
	}

	private JScrollPane getJScrollPaneRecordTimerTable() {
		if (jScrollPaneRecordTimerTable == null) {
			jScrollPaneRecordTimerTable = new JScrollPane();
			jScrollPaneRecordTimerTable.setViewportView(getJTableRecordTimer());
		}
		return jScrollPaneRecordTimerTable;
	}

	private JScrollPane getJScrollPaneSystemTimerTable() {
		if (jScrollPaneSystemTimerTable == null) {
			jScrollPaneSystemTimerTable = new JScrollPane();
			jScrollPaneSystemTimerTable.setViewportView(getJTableSystemTimer());
		}
		return jScrollPaneSystemTimerTable;
	}

	public GuiNeutrinoRecordTimerTableModel getRecordTimerTableModel() {
		return recordTimerTableModel;
	}
	/**
	 * @param senderTableModel The senderTableModel to set.
	 */
	public void setRecordTimerTableModel(GuiNeutrinoRecordTimerTableModel TimerTableModel) {
		this.recordTimerTableModel = TimerTableModel;
	}

	public JPanel getJPanelButtonsRecordTimer() {
		if (jPanelButtonsRecordTimer == null) {
			jPanelButtonsRecordTimer = new JPanel();
			FormLayout layout = new FormLayout(
				      "pref",	 		//columna
		      "pref, pref, pref, 20, pref");	//rows
			PanelBuilder builder = new PanelBuilder(jPanelButtonsRecordTimer, layout);
			CellConstraints cc = new CellConstraints();

			builder.add(this.getJButtonNewRecordTimer(),  				cc.xy	(1, 1));
			builder.add(this.getJButtonDeleteSelectedRecordTimer(),	cc.xy	(1, 2));
			builder.add(this.getJButtonDeleteAllRecordTimer(),			cc.xy	(1, 3));
			builder.add(new JLabel(this.getImageIconNeutrino()),		cc.xy	(1, 5));
		}
		return jPanelButtonsRecordTimer;
	}

	public JPanel getJPanelButtonsSystemTimer() {
		if (jPanelButtonsSystemTimer == null) {
			jPanelButtonsSystemTimer = new JPanel();
			FormLayout layout = new FormLayout(
				      "f:pref",	 		//columna
				      "pref, pref, pref, ");	//rows
			PanelBuilder builder = new PanelBuilder(jPanelButtonsSystemTimer, layout);
			CellConstraints cc = new CellConstraints();

			builder.add(this.getJButtonNewSystemtimer(),  				cc.xyw	(1, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJButtonDeleteSelectedSystemTimer(),		cc.xy	(1, 2));
			builder.add(this.getJButtonDeleteAllSystemTimer(),			cc.xy	(1, 3));
		}
		return jPanelButtonsSystemTimer;
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
					jRadioButtonWhtage[i].addActionListener(control);
					jRadioButtonWhtage[i].setName(Integer.toString(control.weekdays_value[i]));
					jRadioButtonWhtage[i].setActionCommand("recordTimer");
					jRadioButtonWhtage[i].setEnabled(false);					
					jRadioButtonWhtage[i].setText(control.weekdays[i]);
				}
				builder.add(jRadioButtonWhtage[i],cc.xy(a, 1));
				a = a+2;
			}
		}
		return jPanelDauerTimer;
	}
	
	public JPanel getJPanelDauerTimer2() {
		if (jPanelDauerTimer2 == null) {
			jPanelDauerTimer2 = new JPanel();
			FormLayout layout = new FormLayout(
					 "pref",	 		//columna
			  "pref, 10, pref, 10, pref, 10, pref, 10, pref, 10, pref, 10, pref");	//rows
			PanelBuilder builder = new PanelBuilder(jPanelDauerTimer2, layout);
			CellConstraints cc = new CellConstraints();
			int a= 1;
			for(int i = 0 ; i< 7; i++){
				if (jRadioButtonWhtage2[i]== null) {
					jRadioButtonWhtage2[i] = new JRadioButton();
					jRadioButtonWhtage2[i].setEnabled(false);
					jRadioButtonWhtage2[i].setActionCommand("systemTimer");
					jRadioButtonWhtage2[i].setName(Integer.toString(control.weekdays_value[i]));
					jRadioButtonWhtage2[i].addActionListener(control);
					jRadioButtonWhtage2[i].setText(control.weekdays[i]);
				}
				builder.add(jRadioButtonWhtage2[i],cc.xy(1, a));
				a = a+2;
				
			}
		}
		return jPanelDauerTimer2;
	}

	public JButton getJButtonDeleteAllRecordTimer() {
		if (jButtonDeleteAllRecordTimer == null) {
			jButtonDeleteAllRecordTimer = new JButton(ControlMain.getProperty("button_delete"));
			jButtonDeleteAllRecordTimer.setActionCommand("deleteAllRecordTimer");
			jButtonDeleteAllRecordTimer.setPreferredSize(new Dimension(150,25));
			jButtonDeleteAllRecordTimer.addActionListener(control);
		}
		return jButtonDeleteAllRecordTimer;
	}

	public JButton getJButtonDeleteAllSystemTimer() {
		if (jButtonDeleteAllSystemTimer == null) {
			jButtonDeleteAllSystemTimer = new JButton(ControlMain.getProperty("button_delete"));
			jButtonDeleteAllSystemTimer.setActionCommand("deleteAllSystemTimer");
			jButtonDeleteAllSystemTimer.setPreferredSize(new Dimension(150,25));
			jButtonDeleteAllSystemTimer.addActionListener(control);
		}
		return jButtonDeleteAllSystemTimer;
	}

	public JButton getJButtonDeleteSelectedRecordTimer() {
		if (jButtonDeleteSelectedRecordTimer == null) {
			jButtonDeleteSelectedRecordTimer = new JButton(ControlMain.getProperty("button_deleteSelected"));
			jButtonDeleteSelectedRecordTimer.setActionCommand("deleteSelectedRecordTimer");
			jButtonDeleteSelectedRecordTimer.setPreferredSize(new Dimension(150,25));
			jButtonDeleteSelectedRecordTimer.addActionListener(control);
		}
		return jButtonDeleteSelectedRecordTimer;
	}

	public JButton getJButtonDeleteSelectedSystemTimer() {
		if (jButtonDeleteSelectedSystemTimer == null) {
			jButtonDeleteSelectedSystemTimer = new JButton(ControlMain.getProperty("button_deleteSelected"));
			jButtonDeleteSelectedSystemTimer.setActionCommand("deleteSelectedSystemTimer");
			jButtonDeleteSelectedSystemTimer.setPreferredSize(new Dimension(150,25));
			jButtonDeleteSelectedSystemTimer.addActionListener(control);
		}
		return jButtonDeleteSelectedSystemTimer;
	}


	public JButton getJButtonNewRecordTimer() {
		if (jButtonNewRecordTimer == null) {
			jButtonNewRecordTimer = new JButton(ControlMain.getProperty("button_create"));
			jButtonNewRecordTimer.setActionCommand("addRecordTimer");
			jButtonNewRecordTimer.setPreferredSize(new Dimension(150,25));
			jButtonNewRecordTimer.addActionListener(control);
		}
		return jButtonNewRecordTimer;
	}

	public JButton getJButtonSenden() {
		if (jButtonSenden == null) {
			jButtonSenden = new JButton("<HTML><font color=red>"+ControlMain.getProperty("button_send")+"</font></HTML>");
			jButtonSenden.setActionCommand("send");
			jButtonSenden.addActionListener(control);
			jButtonSenden.setPreferredSize(new Dimension(150,25));
		}
		return jButtonSenden;
	}

	public JButton getJButtonDeleteAl() {
		if (jButtonDeleteAll == null) {
			jButtonDeleteAll =new JButton(ControlMain.getProperty("button_deleteAll"));
			jButtonDeleteAll.setActionCommand("deleteAll");
			jButtonDeleteAll.setPreferredSize(new Dimension(150,25));
			jButtonDeleteAll.addActionListener(control);
		}
		return jButtonDeleteAll;
	}

	public JButton getJButtonReload() {
		if (jButtonReload == null) {
			jButtonReload = new JButton(ControlMain.getProperty("button_reload"));
			jButtonReload.setActionCommand("reload");
			jButtonReload.addActionListener(control);
		}
		return jButtonReload;
	}

	public JButton getJButtonNewSystemtimer() {
		if (jButtonNewSystemtimer == null) {
			jButtonNewSystemtimer = new JButton(ControlMain.getProperty("button_create"));
			jButtonNewSystemtimer.setActionCommand("addSystemTimer");
			jButtonNewSystemtimer.setPreferredSize(new Dimension(150,25));
			jButtonNewSystemtimer.addActionListener(control);
		}
		return jButtonNewSystemtimer;
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
	 * @return Returns the systemTimerTableModel.
	 */
	public GuiNeutrinoSystemTimerTableModel getSystemTimerTableModel() {
		return systemTimerTableModel;
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
	 * @return Returns the comboBoxRepeatRecordTimer.
	 */
	public JComboBox getComboBoxRepeatRecordTimer() {
		if (comboBoxRepeatRecordTimer == null) {
			comboBoxRepeatRecordTimer = new JComboBox(new GuiTimerRepeatComboModel(control));
		}
		return comboBoxRepeatRecordTimer;
	}
	/**
	 * @return Returns the comboBoxRepeatSystemTimer.
	 */
	public JComboBox getComboBoxRepeatSystemTimer() {
		if (comboBoxRepeatSystemTimer == null) {
			comboBoxRepeatSystemTimer = new JComboBox(new GuiTimerRepeatComboModel(control));
		}
		return comboBoxRepeatSystemTimer;
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
	
	public void enableRecordTimerWeekdays() {		
		enableRecordTimerWeekdays(true);
	}
	public void enableRecordTimerWeekdays(boolean enabled) {		
		for (int i = 0; i<7; i++){
			jRadioButtonWhtage[i].setEnabled(enabled);
		}
	}
	
	public void enableSystemTimerWeekdays() {
		enableSystemTimerWeekdays(true);
	}
	public void enableSystemTimerWeekdays(boolean enabled) {
		for (int i = 0; i<7; i++){
			jRadioButtonWhtage2[i].setEnabled(enabled);
		}
	}

	public void disableRecordTimerWeekdays() {
		enableRecordTimerWeekdays(false);
	}

	public void disableSystemTimerWeekdays() {
		enableSystemTimerWeekdays(false);
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

	/**
	 * @return Returns the tfSystemTimerStartTime.
	 */
	public JFormattedTextField getTfSystemTimerStartTime() {
		if (tfSystemTimerStartTime == null) {
			tfSystemTimerStartTime = new JFormattedTextField(new SimpleDateFormat("dd.MM.yy   HH:mm"));
			((DateFormatter)tfSystemTimerStartTime.getFormatter()).setAllowsInvalid(false);
			((DateFormatter)tfSystemTimerStartTime.getFormatter()).setOverwriteMode(true);
		}
		return tfSystemTimerStartTime;
	}
}

