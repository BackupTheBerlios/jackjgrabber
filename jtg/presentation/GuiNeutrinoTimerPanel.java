package presentation;

import java.awt.Dimension;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import model.BOTimer;


import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import control.ControlNeutrinoTimerTab;
import control.ControlTab;

/**
 * Aleander Geist
 */
public class GuiNeutrinoTimerPanel extends GuiTimerPanel {

	private JRadioButton jRadioButtonSon = null;
	private JRadioButton jRadioButtonSam = null;
	private JRadioButton jRadioButtonFri = null;
	private JRadioButton jRadioButtonDon = null;
	private JRadioButton jRadioButtonMit = null;
	private JRadioButton jRadioButtonDie = null;
	private JRadioButton jRadioButtonMon = null;
	private JRadioButton jRadioButtonSon2 = null;
	private JRadioButton jRadioButtonSam2 = null;
	private JRadioButton jRadioButtonFri2 = null;
	private JRadioButton jRadioButtonDon2 = null;
	private JRadioButton jRadioButtonMit2 = null;
	private JRadioButton jRadioButtonDie2 = null;
	private JRadioButton jRadioButtonMon2 = null;
	private JPanel jPanelDauerTimer = null;
	private JPanel jPanelDauerTimer2 = null;
	private JPanel jPanelTimerListe = null;
	private JPanel jPanelButtonsProgramTimer = null;
	private JPanel jPanelButtonsSystemTimer = null;
	private JPanel jPanelButtonsGui = null;
	private JButton jButtonReload = null;
	private JButton jButtonDeleteAllProgramTimer = null;
	private JButton jButtonDeleteSelectedProgramTimer = null;
	private JButton jButtonDeleteAllSystemTimer = null;
	private JButton jButtonDeleteSelectedSystemTimer = null;
	private JButton jButtonSenden = null;
	private JButton jButtonNewProgramtimer = null;
	private JButton jButtonNewSystemtimer = null;
	private JButton jButtonDeleteAll = null;
	private JComboBox comboBoxSender = null;
	private JComboBox comboBoxRepeatProgramTimer = null;
	private JComboBox comboBoxEventType = null;
	private JComboBox comboBoxRepeatSystemTimer = null;
	private ImageIcon imageIconNeutrino = null;
	private ControlNeutrinoTimerTab control;
	public GuiNeutrinoRecordTimerTableModel recordTimerTableModel;
	public GuiNeutrinoSystemTimerTableModel systemTimerTableModel;
	private JTable jTableRecordTimer = null;
	private JTable jTableSystemTimer = null;
	private JScrollPane jScrollPaneRecordTimerTable = null;
	private JScrollPane jScrollPaneSystemTimerTable = null;
	private GuiNeutrinoTimerSenderComboModel senderComboModel = null;
	

	public GuiNeutrinoTimerPanel(ControlNeutrinoTimerTab control) {
		this.setControl(control);
		initialize();
	}
	
	public GuiNeutrinoTimerPanel() {
		initialize();
	}

	private  void initialize() {
		FormLayout layout = new FormLayout(
			      "f:330:grow, 110:grow, 160:grow, 4dlu, pref",  							// columns 
			      "pref, t:220:grow, pref, 8dlu, pref, t:100, b:100, pref"); 			// rows
		PanelBuilder builder = new PanelBuilder(this, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();
		
		builder.addSeparator("Aufnahme-Timer",					cc.xyw  (1, 1, 3));
		builder.add(this.getJScrollPaneRecordTimerTable(),   	cc.xyw  (1, 2, 3));
		builder.add(this.getJPanelDauerTimer(),	 				cc.xyw  (1, 3, 3));
		builder.addSeparator("Sytem-Timer",						cc.xyw  (1, 5, 1));
		builder.add(this.getJScrollPaneSystemTimerTable(),  	cc.xywh(1, 6, 1, 2));
		builder.add(this.getJPanelDauerTimer2(), 				cc.xywh(2, 6, 1, 2, CellConstraints.CENTER, CellConstraints.TOP));
		builder.addTitle("Aktionen Aufnahme-Timer",				cc.xy    (5, 1));
		builder.add(this.getJPanelButtonsProgramTimer(), 		cc.xywh(5, 2, 1, 1,  CellConstraints.CENTER, CellConstraints.TOP));
		builder.addTitle("Aktionen System-Timer",				cc.xy    (3, 5));
		builder.add(this.getJPanelButtonsSystemTimer(),			cc.xy    (3, 6));
		builder.add(this.getJPanelButtonsGui(),					cc.xywh(5, 7, 1, 2, CellConstraints.CENTER, CellConstraints.BOTTOM));
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
			
			jTableRecordTimer = new JTable(recordTimerTableModel);
			jTableRecordTimer.setName("recordTimerTable");
			jTableRecordTimer.addMouseListener(control);
			jTableRecordTimer.setRowHeight(20);
			jTableRecordTimer.getColumnModel().getColumn(0).setMaxWidth(100);
			jTableRecordTimer.getColumnModel().getColumn(1).setMaxWidth(70);
			jTableRecordTimer.getColumnModel().getColumn(2).setMaxWidth(50);
			jTableRecordTimer.getColumnModel().getColumn(3).setMaxWidth(50);
			jTableRecordTimer.getColumnModel().getColumn(4).setPreferredWidth(80);
			jTableRecordTimer.getColumnModel().getColumn(4).setMaxWidth(80);

			
			TableColumn columnSender = jTableRecordTimer.getColumnModel().getColumn(0);			
			TableColumn columnRepeat = jTableRecordTimer.getColumnModel().getColumn(4);
			columnSender.setCellEditor(new DefaultCellEditor(this.getComboBoxSender()));
			columnRepeat.setCellEditor(new DefaultCellEditor(this.getComboBoxRepeatProgramTimer()));
		}
		return jTableRecordTimer;
	}
	
	public JTable getJTableSystemTimer() {
		if (jTableSystemTimer == null) {		
			systemTimerTableModel = new GuiNeutrinoSystemTimerTableModel(control);
			jTableSystemTimer = new JTable(systemTimerTableModel);
			jTableSystemTimer.setName("systemTimerTable");
			jTableSystemTimer.addMouseListener(control);
			jTableSystemTimer.setRowHeight(20);
			jTableSystemTimer.getColumnModel().getColumn(0).setMaxWidth(100);
			jTableSystemTimer.getColumnModel().getColumn(1).setMaxWidth(100);
			jTableSystemTimer.getColumnModel().getColumn(2).setMaxWidth(40);
			
			TableColumn columnEventType = jTableSystemTimer.getColumnModel().getColumn(0);
			TableColumn columnRepeat = jTableSystemTimer.getColumnModel().getColumn(3);
			
			columnEventType.setCellEditor(new DefaultCellEditor(this.getComboBoxEventType()));
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
	
	private JRadioButton getJRadioButtonSon() {
		if (jRadioButtonSon == null) {
			jRadioButtonSon = new JRadioButton();
			jRadioButtonSon.setEnabled(false);
			jRadioButtonSon.setText("Sonntag");
		}
		return jRadioButtonSon;
	}
  
	private JRadioButton getJRadioButtonSam() {
		if (jRadioButtonSam == null) {
			jRadioButtonSam = new JRadioButton();
			jRadioButtonSam.setEnabled(false);
			jRadioButtonSam.setText("Samstag");
		}
		return jRadioButtonSam;
	}
   
	private JRadioButton getJRadioButtonFri() {
		if (jRadioButtonFri == null) {
			jRadioButtonFri = new JRadioButton();
			jRadioButtonFri.setEnabled(false);
			jRadioButtonFri.setText("Freitag");
		}
		return jRadioButtonFri;
	}
  
	private JRadioButton getJRadioButtonDon() {
		if (jRadioButtonDon == null) {
			jRadioButtonDon = new JRadioButton();
			jRadioButtonDon.setEnabled(false);
			jRadioButtonDon.setText("Donnerstag");
		}
		return jRadioButtonDon;
	}
 
	private JRadioButton getJRadioButtonMit() {
		if (jRadioButtonMit == null) {
			jRadioButtonMit = new JRadioButton();
			jRadioButtonMit.setEnabled(false);
			jRadioButtonMit.setText("Mittwoch");
		}
		return jRadioButtonMit;
	}
  
	private JRadioButton getJRadioButtonDie() {
		if (jRadioButtonDie == null) {
			jRadioButtonDie = new JRadioButton();
			jRadioButtonDie.setEnabled(false);
			jRadioButtonDie.setText("Dienstag");
		}
		return jRadioButtonDie;
	}
 
	private JRadioButton getJRadioButtonMon() {
		if (jRadioButtonMon == null) {
			jRadioButtonMon = new JRadioButton();
			jRadioButtonMon.setEnabled(false);
			jRadioButtonMon.setText("Montag");
		}
		return jRadioButtonMon;
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
			builder.add(new JLabel(this.getImageIconNeutrino()),		cc.xy	(1, 5));
		}
		return jPanelButtonsProgramTimer;
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
			
			builder.add(this.getJRadioButtonMon(),  					cc.xy	(1, 1));
			builder.add(this.getJRadioButtonDie(),						cc.xy	(3, 1));
			builder.add(this.getJRadioButtonMit(),						cc.xy	(5, 1));
			builder.add(this.getJRadioButtonDon(),	 					cc.xy	(7, 1));
			builder.add(this.getJRadioButtonFri(),  						cc.xy	(9, 1));
			builder.add(this.getJRadioButtonSam(),	  				cc.xy	(11, 1));
			builder.add(this.getJRadioButtonSon(),	  					cc.xy	(13, 1));
		}
		return jPanelDauerTimer;
	}	
  
	private JRadioButton getJRadioButtonSon2() {
		if (jRadioButtonSon2 == null) {
			jRadioButtonSon2 = new JRadioButton();
			jRadioButtonSon2.setEnabled(false);
			jRadioButtonSon2.setText("Sonntag");
		}
		return jRadioButtonSon2;
	}

	private JRadioButton getJRadioButtonSam2() {
		if (jRadioButtonSam2 == null) {
			jRadioButtonSam2 = new JRadioButton();
			jRadioButtonSam2.setEnabled(false);
			jRadioButtonSam2.setText("Samstag");
		}
		return jRadioButtonSam2;
	}
  
	private JRadioButton getJRadioButtonFri2() {
		if (jRadioButtonFri2 == null) {
			jRadioButtonFri2 = new JRadioButton();
			jRadioButtonFri2.setEnabled(false);
			jRadioButtonFri2.setText("Freitag");
		}
		return jRadioButtonFri2;
	}

	private JRadioButton getJRadioButtonDon2() {
		if (jRadioButtonDon2 == null) {
			jRadioButtonDon2 = new JRadioButton();
			jRadioButtonDon2.setEnabled(false);
			jRadioButtonDon2.setText("Donnerstag");
		}
		return jRadioButtonDon2;
	}
   
	private JRadioButton getJRadioButtonMit2() {
		if (jRadioButtonMit2 == null) {
			jRadioButtonMit2 = new JRadioButton();
			jRadioButtonMit2.setEnabled(false);
			jRadioButtonMit2.setText("Mittwoch");
		}
		return jRadioButtonMit2;
	}
  
	private JRadioButton getJRadioButtonDie2() {
		if (jRadioButtonDie2 == null) {
			jRadioButtonDie2 = new JRadioButton();
			jRadioButtonDie2.setEnabled(false);
			jRadioButtonDie2.setText("Dienstag");
		}
		return jRadioButtonDie2;
	}
  
	private JRadioButton getJRadioButtonMon2() {
		if (jRadioButtonMon2 == null) {
			jRadioButtonMon2 = new JRadioButton();
			jRadioButtonMon2.setEnabled(false);
			jRadioButtonMon2.setText("Montag");
		}
		return jRadioButtonMon2;
	}

	public JPanel getJPanelDauerTimer2() {
		if (jPanelDauerTimer2 == null) {
			jPanelDauerTimer2 = new JPanel();
			FormLayout layout = new FormLayout(
					 "pref",	 		//columna 
			  "pref, 10, pref, 10, pref, 10, pref, 10, pref, 10, pref, 10, pref");	//rows
			PanelBuilder builder = new PanelBuilder(jPanelDauerTimer2, layout);
			CellConstraints cc = new CellConstraints();
			
			builder.add(this.getJRadioButtonMon2(),  					cc.xy	(1, 1));
			builder.add(this.getJRadioButtonDie2(),					cc.xy	(1, 3));
			builder.add(this.getJRadioButtonMit2(),					cc.xy	(1, 5));
			builder.add(this.getJRadioButtonDon2(),					cc.xy	(1, 7));
			builder.add(this.getJRadioButtonFri2(),  					cc.xy	(1, 9));
			builder.add(this.getJRadioButtonSam2(),	  				cc.xy	(1, 11));
			builder.add(this.getJRadioButtonSon2(),					cc.xy	(1, 13));
		}
		return jPanelDauerTimer2;
	}	

	public JButton getJButtonDeleteAllProgramTimer() {
		if (jButtonDeleteAllProgramTimer == null) {
			jButtonDeleteAllProgramTimer = new JButton("Aufnahmetimer löschen");
			jButtonDeleteAllProgramTimer.setPreferredSize(new Dimension(150,25));
			jButtonDeleteAllProgramTimer.addActionListener(control);
		}
		return jButtonDeleteAllProgramTimer;
	}
	
	public JButton getJButtonDeleteAllSystemTimer() {
		if (jButtonDeleteAllSystemTimer == null) {
			jButtonDeleteAllSystemTimer = new JButton("Systemtimer löschen");
			jButtonDeleteAllSystemTimer.setPreferredSize(new Dimension(150,25));
			jButtonDeleteAllSystemTimer.addActionListener(control);
		}
		return jButtonDeleteAllSystemTimer;
	}

	public JButton getJButtonDeleteSelectedProgramTimer() {
		if (jButtonDeleteSelectedProgramTimer == null) {
			jButtonDeleteSelectedProgramTimer = new JButton("Selektierte löschen");
			jButtonDeleteSelectedProgramTimer.setActionCommand("deleteSelectedProgramTimer");
			jButtonDeleteSelectedProgramTimer.setPreferredSize(new Dimension(150,25));
			jButtonDeleteSelectedProgramTimer.addActionListener(control);
		}
		return jButtonDeleteSelectedProgramTimer;
	}
	
	public JButton getJButtonDeleteSelectedSystemTimer() {
		if (jButtonDeleteSelectedSystemTimer == null) {
			jButtonDeleteSelectedSystemTimer = new JButton("Selektierte löschen");
			jButtonDeleteSelectedSystemTimer.setActionCommand("deleteSelectedSystemTimer");
			jButtonDeleteSelectedSystemTimer.setPreferredSize(new Dimension(150,25));
			jButtonDeleteSelectedSystemTimer.addActionListener(control);
		}
		return jButtonDeleteSelectedSystemTimer;
	}
	

	public JButton getJButtonNewProgramtimer() {
		if (jButtonNewProgramtimer == null) {
			jButtonNewProgramtimer = new JButton("Anlegen");
			jButtonNewProgramtimer.setActionCommand("addProgramTimer");
			jButtonNewProgramtimer.setPreferredSize(new Dimension(150,25));
			jButtonNewProgramtimer.addActionListener(control);
		}
		return jButtonNewProgramtimer;
	}

	public JButton getJButtonSenden() {
		if (jButtonSenden == null) {
			jButtonSenden = new JButton("Senden");
			jButtonSenden.addActionListener(control);
			jButtonSenden.setPreferredSize(new Dimension(150,25));
		}
		return jButtonSenden;
	}
	
	public JButton getJButtonDeleteAl() {
		if (jButtonDeleteAll == null) {
			jButtonDeleteAll = new JButton("Alle Löschen");
			jButtonDeleteAll.setPreferredSize(new Dimension(150,25));
			jButtonDeleteAll.addActionListener(control);
		}
		return jButtonDeleteAll;
	}
	
	public JButton getJButtonReload() {
		if (jButtonReload == null) {
			jButtonReload = new JButton("Neu laden");
			jButtonReload.addActionListener(control);
		}
		return jButtonReload;
	}

	public JButton getJButtonNewSystemtimer() {
		if (jButtonNewSystemtimer == null) {
			jButtonNewSystemtimer = new JButton("Anlegen");
			jButtonNewSystemtimer.setActionCommand("addSystemTimer");
			jButtonNewSystemtimer.setPreferredSize(new Dimension(150,25));
			jButtonNewSystemtimer.addActionListener(control);
		}
		return jButtonNewSystemtimer;
	}
	/**
	 * @return Returns the senderComboModel.
	 */
	public GuiNeutrinoTimerSenderComboModel getSenderComboModel() {
		return senderComboModel;
	}
	/**
	 * @param senderComboModel The senderComboModel to set.
	 */
	public void setSenderComboModel(
			GuiNeutrinoTimerSenderComboModel senderComboModel) {
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
			comboBoxEventType = new JComboBox(new GuiNeutrinoTimerEventTypeComboModel(control));
		}
		return comboBoxEventType;
	}
	/**
	 * @return Returns the comboBoxRepeatProgramTimer.
	 */
	public JComboBox getComboBoxRepeatProgramTimer() {
		if (comboBoxRepeatProgramTimer == null) {
			comboBoxRepeatProgramTimer = new JComboBox(new GuiNeutrinoTimerRepeatComboModel(control));
		}
		return comboBoxRepeatProgramTimer;
	}
	/**
	 * @return Returns the comboBoxRepeatSystemTimer.
	 */
	public JComboBox getComboBoxRepeatSystemTimer() {
		if (comboBoxRepeatSystemTimer == null) {
			comboBoxRepeatSystemTimer = new JComboBox(new GuiNeutrinoTimerRepeatComboModel(control));
		}
		return comboBoxRepeatSystemTimer;
	}
	/**
	 * @return Returns the comboBoxSender.
	 */
	public JComboBox getComboBoxSender() {
		if (comboBoxSender == null) {
			comboBoxSender = new JComboBox(new GuiNeutrinoTimerSenderComboModel(control));
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
		if (Integer.parseInt((String)timer.getEventRepeat())>5) {
			result = Integer.parseInt((String)timer.getEventRepeat())-256;
			this.enableRecordTimerWeekdays();
		} else {
			result = Integer.parseInt((String)timer.getEventRepeat());
			this.disableRecordTimerWeekdays();
		}
		while (result>=0) {
			if (result>=32768) {
				this.getJRadioButtonSon().setSelected(true);
				result = result-32768;
			} else {
				this.getJRadioButtonSon().setSelected(false);
			}
			if (result>=16384) {
				this.getJRadioButtonSam().setSelected(true);
				result = result-16384;
			} else {
				this.getJRadioButtonSam().setSelected(false);
			}
			if (result>=8192) {
				this.getJRadioButtonFri().setSelected(true);
				result = result-8192;
			} else {
				this.getJRadioButtonFri().setSelected(false);
			}
			if (result>=4096) {
				this.getJRadioButtonDon().setSelected(true);
				result = result-4096;
			} else {
				this.getJRadioButtonDon().setSelected(false);
			}
			if (result>=2048) {
				this.getJRadioButtonMit().setSelected(true);
				result = result-2048;
			} else {
				this.getJRadioButtonMit().setSelected(false);
			}
			if (result>=1024) {
				this.getJRadioButtonDie().setSelected(true);
				result = result-1024;
			} else {
				this.getJRadioButtonDie().setSelected(false);
			}
			if (result>=512) {
				this.getJRadioButtonMon().setSelected(true);
				result = result-512;
			} else {
				this.getJRadioButtonMon().setSelected(false);
			}
			result = result-5;
		}
	}
	
	public void selectRepeatDaysForSystemTimer(BOTimer timer) {
		int result;
		if (Integer.parseInt((String)timer.getEventRepeat())>5) {
			result = Integer.parseInt((String)timer.getEventRepeat())-256;
			this.enableSystemTimerWeekdays();
		} else {
			result = Integer.parseInt((String)timer.getEventRepeat());
			this.disableSystemTimerWeekdays();
		}
		while (result>0) {
			if (result>=32768) {
				this.getJRadioButtonSon2().setSelected(true);
				result = result-32768;
			} else {
				this.getJRadioButtonSon2().setSelected(false);
			}
			if (result>=16384) {
				this.getJRadioButtonSam2().setSelected(true);
				result = result-16384;
			} else {
				this.getJRadioButtonSam2().setSelected(false);
			}
			if (result>=8192) {
				this.getJRadioButtonFri2().setSelected(true);
				result = result-8192;
			} else {
				this.getJRadioButtonFri2().setSelected(false);
			}
			if (result>=4096) {
				this.getJRadioButtonDon2().setSelected(true);
				result = result-4096;
			} else {
				this.getJRadioButtonDon2().setSelected(false);
			}
			if (result>=2048) {
				this.getJRadioButtonMit2().setSelected(true);
				result = result-2048;
			} else {
				this.getJRadioButtonMit2().setSelected(false);
			}
			if (result>=1024) {
				this.getJRadioButtonDie2().setSelected(true);
				result = result-1024;
			} else {
				this.getJRadioButtonDie2().setSelected(false);
			}
			if (result>=512) {
				this.getJRadioButtonMon2().setSelected(true);
				result = result-512;
			} else {
				this.getJRadioButtonMon2().setSelected(false);
			}
			result = result-5;
		}
	}
	
	public void enableRecordTimerWeekdays() {
		this.getJRadioButtonSon().setEnabled(true);
		this.getJRadioButtonSam().setEnabled(true);
		this.getJRadioButtonFri().setEnabled(true);
		this.getJRadioButtonDon().setEnabled(true);
		this.getJRadioButtonMit().setEnabled(true);
		this.getJRadioButtonDie().setEnabled(true);
		this.getJRadioButtonMon().setEnabled(true);
	}
	
	public void enableSystemTimerWeekdays() {
		this.getJRadioButtonSon2().setEnabled(true);
		this.getJRadioButtonSam2().setEnabled(true);
		this.getJRadioButtonFri2().setEnabled(true);
		this.getJRadioButtonDon2().setEnabled(true);
		this.getJRadioButtonMit2().setEnabled(true);
		this.getJRadioButtonDie2().setEnabled(true);
		this.getJRadioButtonMon2().setEnabled(true);
	}
	
	public void disableRecordTimerWeekdays() {
		this.getJRadioButtonSon().setEnabled(false);
		this.getJRadioButtonSam().setEnabled(false);
		this.getJRadioButtonFri().setEnabled(false);
		this.getJRadioButtonDon().setEnabled(false);
		this.getJRadioButtonMit().setEnabled(false);
		this.getJRadioButtonDie().setEnabled(false);
		this.getJRadioButtonMon().setEnabled(false);
	}
	
	public void disableSystemTimerWeekdays() {
		this.getJRadioButtonSon2().setEnabled(false);
		this.getJRadioButtonSam2().setEnabled(false);
		this.getJRadioButtonFri2().setEnabled(false);
		this.getJRadioButtonDon2().setEnabled(false);
		this.getJRadioButtonMit2().setEnabled(false);
		this.getJRadioButtonDie2().setEnabled(false);
		this.getJRadioButtonMon2().setEnabled(false);
	}
}

