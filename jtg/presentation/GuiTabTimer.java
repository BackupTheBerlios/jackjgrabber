/*
 * Created on 17.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package presentation;

import javax.swing.JPanel;

import control.ControlProgramTab;
import control.ControlTimerTab;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
/**
 * @author Treito
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GuiTabTimer extends GuiTab {

	private JPanel jPanelTimerListe = null;
	private ControlTimerTab control;
	public GuiTimerTableModel TimerTableModel;
	private JPanel jPanelAktionen = null;
	private JTable jTableTimer = null;
	private JScrollPane jScrollPaneTimerTable = null;
	private JPanel jPanelDauerTimer = null;
	private JButton jButtonDeleteSelectedTimer = null;
	private JButton jButtonDeleteAllTimer = null;
	private JPanel jPanelManuell = null;
	private JPanel jPanelPids = null;
	/**
	 * This is the default constructor
	 */
	public GuiTabTimer(ControlTimerTab control) {
		this.setControl(control);
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		java.awt.GridBagConstraints gridBagConstraintsAktionen = new GridBagConstraints();
		java.awt.GridBagConstraints gridBagConstraintsTimerList = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		this.setSize(770,550);
		gridBagConstraintsTimerList.gridx = 0;
		gridBagConstraintsTimerList.gridy = 0;
		gridBagConstraintsAktionen.gridx = 1;
		gridBagConstraintsAktionen.gridy = 0;
		this.add(getJPanelTimerListe(), gridBagConstraintsTimerList);
		this.add(getJPanelAktionen(), gridBagConstraintsAktionen);
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel			jPanelTimerListe.add(getJScrollPane(), gridBagConstraints4);
	
	 */    
	private JPanel getJPanelTimerListe() {
		if (jPanelTimerListe == null) {
			java.awt.GridBagConstraints gridBagConstraintsTimerTable = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraintsDauerTimer = new GridBagConstraints();
			jPanelTimerListe = new JPanel();
			jPanelTimerListe.setLayout(new GridBagLayout());
			jPanelTimerListe.setPreferredSize(new java.awt.Dimension(400,400));
			jPanelTimerListe.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Timerliste, Doppelklick um auf Sender zu Zappen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			gridBagConstraintsTimerTable.gridx = 0;
			gridBagConstraintsTimerTable.gridy = 0;
			gridBagConstraintsTimerTable.weightx = 1.0;
			gridBagConstraintsTimerTable.weighty = 1.0;
			gridBagConstraintsTimerTable.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraintsDauerTimer.gridx = 0;
			gridBagConstraintsDauerTimer.gridy = 1;
			jPanelTimerListe.add(getJPanelDauerTimer(), gridBagConstraintsDauerTimer);
			jPanelTimerListe.add(getJScrollPaneTimerTable(), gridBagConstraintsTimerTable);
		}
		return jPanelTimerListe;
	}
	/**
	 * @return ControlTimerTab
	 */
	public ControlTimerTab getControl() {
		return control;
	}

	/**
	 * Sets the control.
	 * @param control The control to set
	 */
	public void setControl(ControlTimerTab control) {
		this.control = control;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanelAktionen() {
		if (jPanelAktionen == null) {
			java.awt.GridBagConstraints gridBagConstraintsManuell = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			jPanelAktionen = new JPanel();
			jPanelAktionen.setLayout(new GridBagLayout());
			jPanelAktionen.setPreferredSize(new java.awt.Dimension(350,400));
			jPanelAktionen.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Aktionen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 0;
			gridBagConstraints8.gridx = 1;
			gridBagConstraints8.gridy = 0;
			gridBagConstraintsManuell.gridx = 0;
			gridBagConstraintsManuell.gridy = 1;
			gridBagConstraintsManuell.gridwidth = 2;
			jPanelAktionen.add(getJButtonDeleteSelectedTimer(), gridBagConstraints7);
			jPanelAktionen.add(getJButtonDeleteAllTimer(), gridBagConstraints8);
			jPanelAktionen.add(getJPanelManuell(), gridBagConstraintsManuell);
		}
		return jPanelAktionen;
	}
	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */    
	private JTable getJTableTimer() {
		if (jTableTimer == null) {
			jTableTimer = new JTable(TimerTableModel);
		}
		return jTableTimer;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPaneTimerTable() {
		if (jScrollPaneTimerTable == null) {
			jScrollPaneTimerTable = new JScrollPane();
			jScrollPaneTimerTable.setViewportView(getJTableTimer());
		}
		return jScrollPaneTimerTable;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanelDauerTimer() {
		if (jPanelDauerTimer == null) {
			jPanelDauerTimer = new JPanel();
			jPanelDauerTimer.setLayout(new GridBagLayout());
			jPanelDauerTimer.setMinimumSize(new java.awt.Dimension(350,50));
			jPanelDauerTimer.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Dauertimer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jPanelDauerTimer.setPreferredSize(new java.awt.Dimension(380,50));
		}
		return jPanelDauerTimer;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButtonDeleteSelectedTimer() {
		if (jButtonDeleteSelectedTimer == null) {
			jButtonDeleteSelectedTimer = new JButton();
			jButtonDeleteSelectedTimer.setText("Selektierte l�schen");
			jButtonDeleteSelectedTimer.setToolTipText("Selektierte Timerprogramme l�schen");
		}
		return jButtonDeleteSelectedTimer;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButtonDeleteAllTimer() {
		if (jButtonDeleteAllTimer == null) {
			jButtonDeleteAllTimer = new JButton();
			jButtonDeleteAllTimer.setText("Alle l�schen");
			jButtonDeleteAllTimer.setToolTipText("Alle Timer l�schen");
		}
		return jButtonDeleteAllTimer;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanelManuell() {
		if (jPanelManuell == null) {
			java.awt.GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			jPanelManuell = new JPanel();
			jPanelManuell.setLayout(new GridBagLayout());
			jPanelManuell.setPreferredSize(new java.awt.Dimension(330,200));
			jPanelManuell.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Manuell", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			jPanelManuell.add(getJPanelPids(), gridBagConstraints1);
		}
		return jPanelManuell;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanelPids() {
		if (jPanelPids == null) {
			jPanelPids = new JPanel();
			jPanelPids.setPreferredSize(new java.awt.Dimension(290,100));
			jPanelPids.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Pid's", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
		}
		return jPanelPids;
	}
	public GuiTimerTableModel getTimerTableModel() {
		return TimerTableModel;
	}
	/**
	 * @param senderTableModel The senderTableModel to set.
	 */
	public void setTimerTableModel(GuiTimerTableModel TimerTableModel) {
		this.TimerTableModel = TimerTableModel;
	}
       }
