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
/**
 * @author Treito
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GuiTabTimer extends GuiTab {

	private JPanel jPanelTimerListe = null;
	private ControlTimerTab control;
	
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
		java.awt.GridBagConstraints gridBagConstraintsTimerList = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		this.setSize(770,550);
		gridBagConstraintsTimerList.gridx = 0;
		gridBagConstraintsTimerList.gridy = 0;
		this.add(getJPanelTimerListe(), gridBagConstraintsTimerList);
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanelTimerListe() {
		if (jPanelTimerListe == null) {
			jPanelTimerListe = new JPanel();
			jPanelTimerListe.setPreferredSize(new java.awt.Dimension(630,400));
			jPanelTimerListe.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Timerliste, Doppelklick um auf Sender zu Zappen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
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
 }
