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

import control.ControlEnigmaTimerTab;
import control.ControlTab;

/**
 * Alexander Geist, Treito
 */
public class GuiEnigmaTimerPanel extends GuiTimerPanel {

	private JRadioButton jRadioButtonSon = null;
	private JRadioButton jRadioButtonSam = null;
	private JRadioButton jRadioButtonFri = null;
	private JRadioButton jRadioButtonDon = null;
	private JRadioButton jRadioButtonMit = null;
	private JRadioButton jRadioButtonDie = null;
	private JRadioButton jRadioButtonMon = null;
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
	private GuiEnigmaTimerSenderComboModel senderComboModel = null;
	

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
			      "pref, t:440:grow, pref, 8dlu, pref, pref"); 			// rows
		PanelBuilder builder = new PanelBuilder(this, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();
		
		builder.addSeparator("Aufnahme-Timer",					cc.xyw  (1, 1, 3));
		builder.add(this.getJScrollPaneRecordTimerTable(),   	cc.xyw  (1, 2, 3));
		builder.add(this.getJPanelDauerTimer(),	 				cc.xyw  (1, 3, 3));
		builder.addTitle("Aktionen Aufnahme-Timer",				cc.xy    (5, 1));
		builder.add(this.getJPanelButtonsProgramTimer(), 		cc.xywh(5, 2, 1, 1,  CellConstraints.CENTER, CellConstraints.TOP));
		builder.add(this.getJPanelButtonsGui(),					cc.xywh(5, 5, 1, 2, CellConstraints.CENTER, CellConstraints.BOTTOM));
	}

	public ControlTab getControl() {
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
  
	
	public JButton getJButtonDeleteAllProgramTimer() {
		if (jButtonDeleteAllProgramTimer == null) {
			jButtonDeleteAllProgramTimer = new JButton("Alle löschen");
			jButtonDeleteAllProgramTimer.setActionCommand("deleteAllProgramTimer");
			jButtonDeleteAllProgramTimer.setPreferredSize(new Dimension(150,25));
			jButtonDeleteAllProgramTimer.addActionListener(control);
		}
		return jButtonDeleteAllProgramTimer;
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
			jButtonSenden.setActionCommand("send");
			jButtonSenden.addActionListener(control);
			jButtonSenden.setPreferredSize(new Dimension(150,25));
		}
		return jButtonSenden;
	}
	
	public JButton getJButtonDeleteAl() {
		if (jButtonDeleteAll == null) {
			jButtonDeleteAll = new JButton("Timer aufräumen");
			jButtonDeleteAll.setActionCommand("cleanup");
			jButtonDeleteAll.setPreferredSize(new Dimension(150,25));
			jButtonDeleteAll.addActionListener(control);
		}
		return jButtonDeleteAll;
	}
	
	public JButton getJButtonReload() {
		if (jButtonReload == null) {
			jButtonReload = new JButton("Neu laden");
			jButtonReload.setActionCommand("reload");
			jButtonReload.addActionListener(control);
		}
		return jButtonReload;
	}

	
	/**
	 * @return Returns the senderComboModel.
	 */
	public GuiEnigmaTimerSenderComboModel getSenderComboModel() {
		return senderComboModel;
	}
	/**
	 * @param senderComboModel The senderComboModel to set.
	 */
	public void setSenderComboModel(
			GuiEnigmaTimerSenderComboModel senderComboModel) {
		this.senderComboModel = senderComboModel;
	}
	/**
	 * @return Returns the comboBoxEventType.
	 */
	public JComboBox getComboBoxEventType() {
		if (comboBoxEventType == null) {
			comboBoxEventType = new JComboBox(new GuiEnigmaTimerEventTypeComboModel(control));
		}
		return comboBoxEventType;
	}
	/**
	 * @return Returns the comboBoxRepeatProgramTimer.
	 */
	public JComboBox getComboBoxRepeatProgramTimer() {
		if (comboBoxRepeatProgramTimer == null) {
			comboBoxRepeatProgramTimer = new JComboBox(new GuiEnigmaTimerRepeatComboModel(control));
		}
		return comboBoxRepeatProgramTimer;
	}
	/**
	 * @return Returns the comboBoxSender.
	 */
	public JComboBox getComboBoxSender() {
		if (comboBoxSender == null) {
			comboBoxSender = new JComboBox(new GuiEnigmaTimerSenderComboModel(control));
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
	
	
	public void enableRecordTimerWeekdays() {
		this.getJRadioButtonSon().setEnabled(true);
		this.getJRadioButtonSam().setEnabled(true);
		this.getJRadioButtonFri().setEnabled(true);
		this.getJRadioButtonDon().setEnabled(true);
		this.getJRadioButtonMit().setEnabled(true);
		this.getJRadioButtonDie().setEnabled(true);
		this.getJRadioButtonMon().setEnabled(true);
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

}
