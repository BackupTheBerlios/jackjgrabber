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
	private ImageIcon imageIconNeutrino = null;
	private ControlNeutrinoTimerTab control;
	public GuiNeutrinoRecordTimerTableModel timerTableModel;
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
			      "620:grow, 4dlu, 160",  							// columns 
			      "pref, t:220:grow, pref, 8dlu, pref, t:100, b:100, pref"); 			// rows
		PanelBuilder builder = new PanelBuilder(this, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();
		
		builder.addSeparator("Aufnahme-Timer",					cc.xy (1, 1));
		builder.add(this.getJScrollPaneRecordTimerTable(),   	cc.xy (1, 2));
		builder.add(this.getJPanelDauerTimer(),	 				cc.xy (1, 3));
		builder.addSeparator("Sytem-Timer",						cc.xy (1, 5));
		builder.add(this.getJScrollPaneSystemTimerTable(),  	cc.xywh(1, 6, 1, 2));
		builder.add(this.getJPanelDauerTimer2(), 				cc.xy (1, 8));
		builder.addTitle("Aktionen Aufnahme-Timer",				cc.xy (3, 1));
		builder.add(this.getJPanelButtonsProgramTimer(), 		cc.xy (3, 2));
		builder.addTitle("Aktionen System-Timer",				cc.xy (3, 5));
		builder.add(this.getJPanelButtonsSystemTimer(),			cc.xy (3, 6));
		builder.add(this.getJPanelButtonsGui(),					cc.xywh (3, 7, 1, 2, CellConstraints.DEFAULT, CellConstraints.BOTTOM));
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
			JComboBox comboBoxSender = new JComboBox(new GuiNeutrinoTimerSenderComboModel(control));
			JComboBox comboBoxRepeat = new JComboBox(new GuiNeutrinoTimerRepeatComboModel(control));
			
			timerTableModel = new GuiNeutrinoRecordTimerTableModel(control);			
			jTableRecordTimer = new JTable(timerTableModel);
			jTableRecordTimer.setRowHeight(20);
			jTableRecordTimer.getColumnModel().getColumn(0).setMaxWidth(100);
			jTableRecordTimer.getColumnModel().getColumn(1).setMaxWidth(70);
			jTableRecordTimer.getColumnModel().getColumn(2).setMaxWidth(50);
			jTableRecordTimer.getColumnModel().getColumn(3).setMaxWidth(50);
			jTableRecordTimer.getColumnModel().getColumn(4).setMaxWidth(80);
			
			TableColumn columnSender = jTableRecordTimer.getColumnModel().getColumn(0);			
			TableColumn columnRepeat = jTableRecordTimer.getColumnModel().getColumn(4);
			columnSender.setCellEditor(new DefaultCellEditor(comboBoxSender));
			columnRepeat.setCellEditor(new DefaultCellEditor(comboBoxRepeat));
		}
		return jTableRecordTimer;
	}
	
	public JTable getJTableSystemTimer() {
		if (jTableSystemTimer == null) {
			return new JTable();
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
  
	public GuiNeutrinoRecordTimerTableModel getTimerTableModel() {
		return timerTableModel;
	}
	/**
	 * @param senderTableModel The senderTableModel to set.
	 */
	public void setTimerTableModel(GuiNeutrinoRecordTimerTableModel TimerTableModel) {
		this.timerTableModel = TimerTableModel;
	}
	
	private JRadioButton getJRadioButtonSon() {
		if (jRadioButtonSon == null) {
			jRadioButtonSon = new JRadioButton();
			jRadioButtonSon.setText("Sonntag");
		}
		return jRadioButtonSon;
	}
  
	private JRadioButton getJRadioButtonSam() {
		if (jRadioButtonSam == null) {
			jRadioButtonSam = new JRadioButton();
			jRadioButtonSam.setText("Samstag");
		}
		return jRadioButtonSam;
	}
   
	private JRadioButton getJRadioButtonFri() {
		if (jRadioButtonFri == null) {
			jRadioButtonFri = new JRadioButton();
			jRadioButtonFri.setText("Freitag");
		}
		return jRadioButtonFri;
	}
  
	private JRadioButton getJRadioButtonDon() {
		if (jRadioButtonDon == null) {
			jRadioButtonDon = new JRadioButton();
			jRadioButtonDon.setText("Donnerstag");
		}
		return jRadioButtonDon;
	}
 
	private JRadioButton getJRadioButtonMit() {
		if (jRadioButtonMit == null) {
			jRadioButtonMit = new JRadioButton();
			jRadioButtonMit.setText("Mittwoch");
		}
		return jRadioButtonMit;
	}
  
	private JRadioButton getJRadioButtonDie() {
		if (jRadioButtonDie == null) {
			jRadioButtonDie = new JRadioButton();
			jRadioButtonDie.setText("Dienstag");
		}
		return jRadioButtonDie;
	}
 
	private JRadioButton getJRadioButtonMon() {
		if (jRadioButtonMon == null) {
			jRadioButtonMon = new JRadioButton();
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
				      "pref, 30, pref, 30, pref, 30, pref, 30, pref, 30, pref, 30, pref",	 		//columna 
				      "pref");	//rows
			PanelBuilder builder = new PanelBuilder(jPanelDauerTimer, layout);
			CellConstraints cc = new CellConstraints();
			
			builder.add(this.getJRadioButtonMon(),  					cc.xy	(1, 1));
			builder.add(this.getJRadioButtonDie(),						cc.xy	(3, 1));
			builder.add(this.getJRadioButtonMit(),						cc.xy	(5, 1));
			builder.add(this.getJRadioButtonDon(),	 					cc.xy	(7, 1));
			builder.add(this.getJRadioButtonFri(),  					cc.xy	(9, 1));
			builder.add(this.getJRadioButtonSam(),	  					cc.xy	(11, 1));
			builder.add(this.getJRadioButtonSon(),	  					cc.xy	(13, 1));
		}
		return jPanelDauerTimer;
	}	
  
	private JRadioButton getJRadioButtonSon2() {
		if (jRadioButtonSon2 == null) {
			jRadioButtonSon2 = new JRadioButton();
			jRadioButtonSon2.setText("Sonntag");
		}
		return jRadioButtonSon2;
	}

	private JRadioButton getJRadioButtonSam2() {
		if (jRadioButtonSam2 == null) {
			jRadioButtonSam2 = new JRadioButton();
			jRadioButtonSam2.setText("Samstag");
		}
		return jRadioButtonSam2;
	}
  
	private JRadioButton getJRadioButtonFri2() {
		if (jRadioButtonFri2 == null) {
			jRadioButtonFri2 = new JRadioButton();
			jRadioButtonFri2.setText("Freitag");
		}
		return jRadioButtonFri2;
	}

	private JRadioButton getJRadioButtonDon2() {
		if (jRadioButtonDon2 == null) {
			jRadioButtonDon2 = new JRadioButton();
			jRadioButtonDon2.setText("Donnerstag");
		}
		return jRadioButtonDon2;
	}
   
	private JRadioButton getJRadioButtonMit2() {
		if (jRadioButtonMit2 == null) {
			jRadioButtonMit2 = new JRadioButton();
			jRadioButtonMit2.setText("Mittwoch");
		}
		return jRadioButtonMit2;
	}
  
	private JRadioButton getJRadioButtonDie2() {
		if (jRadioButtonDie2 == null) {
			jRadioButtonDie2 = new JRadioButton();
			jRadioButtonDie2.setText("Dienstag");
		}
		return jRadioButtonDie2;
	}
  
	private JRadioButton getJRadioButtonMon2() {
		if (jRadioButtonMon2 == null) {
			jRadioButtonMon2 = new JRadioButton();
			jRadioButtonMon2.setText("Montag");
		}
		return jRadioButtonMon2;
	}

	public JPanel getJPanelDauerTimer2() {
		if (jPanelDauerTimer2 == null) {
			jPanelDauerTimer2 = new JPanel();
			FormLayout layout = new FormLayout(
				      "pref, 1dlu, pref, 1dlu, pref, 1dlu, pref, 1dlu, pref, 1dlu, pref, 1dlu, pref",	 		//columna 
				      "pref");	//rows
			PanelBuilder builder = new PanelBuilder(jPanelDauerTimer2, layout);
			CellConstraints cc = new CellConstraints();
			
			builder.add(this.getJRadioButtonMon2(),  					cc.xy	(1, 1));
			builder.add(this.getJRadioButtonDie2(),						cc.xy	(3, 1));
			builder.add(this.getJRadioButtonMit2(),						cc.xy	(5, 1));
			builder.add(this.getJRadioButtonDon2(), 					cc.xy	(7, 1));
			builder.add(this.getJRadioButtonFri2(),  					cc.xy	(9, 1));
			builder.add(this.getJRadioButtonSam2(),	  					cc.xy	(11, 1));
			builder.add(this.getJRadioButtonSon2(),	  					cc.xy	(13, 1));
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
}

