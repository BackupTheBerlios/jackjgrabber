/*
 * Created on 20.09.2004
 */
package presentation;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import model.BOLocale;

import control.ControlMain;
import control.ControlSettingsTab;
/**
 * @author Geist Alexander
 *
 */
public class GuiTabSettings extends JPanel {

	private JPanel jPanel = null;
	private JPanel panelLayoutSettings = null;
	private JPanel panelBoxSettings = null;
	private JPanel jPanel3 = null;
	private JPanel jPanel4 = null;
	private JPanel jPanel5 = null;
	private JComboBox jComboBoxTheme = null;
	private JButton jButtonAnlegen = null;
	private JButton jButtonLoeschen = null;
	private JScrollPane jScrollPaneBoxSettings = null;
	private JTable jTableBoxSettings = null;
	private JLabel jLabel = null;
	private ControlSettingsTab control;
	private GuiBoxSettingsTableModel modelBoxTable;
	private JLabel jLabel1 = null;
	private JComboBox jComboBoxLocale = null;
	private JPanel jPanel1 = null;
	private JLabel jLabel2 = null;
	/**
	 * This is the default constructor
	 */
	public GuiTabSettings(ControlSettingsTab ctrl) {
		super();
		this.setControl(ctrl);
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		GridBagConstraints constraintsBoxSettings = new GridBagConstraints();
		GridBagConstraints constraintsLayoutSettings = new GridBagConstraints();
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		//this.setSize(770, 400);		
		this.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 1;
		gridBagConstraints1.gridheight = 1;
		gridBagConstraints1.gridwidth = 1;
		gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
		constraintsLayoutSettings.gridx = 1;
		constraintsLayoutSettings.gridy = 0;
		constraintsLayoutSettings.gridheight = 1;
		constraintsLayoutSettings.gridwidth = 1;
		constraintsLayoutSettings.fill = java.awt.GridBagConstraints.BOTH;
		constraintsBoxSettings.gridx = 0;
		constraintsBoxSettings.gridy = 0;
		constraintsBoxSettings.gridheight = 1;
		constraintsBoxSettings.fill = java.awt.GridBagConstraints.BOTH;
		constraintsBoxSettings.anchor = java.awt.GridBagConstraints.CENTER;
		constraintsBoxSettings.insets = new java.awt.Insets(0,0,0,0);
		gridBagConstraints4.gridx = 1;
		gridBagConstraints4.gridy = 2;
		gridBagConstraints4.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints5.gridx = 0;
		gridBagConstraints5.gridy = 2;
		gridBagConstraints5.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints6.gridx = 1;
		gridBagConstraints6.gridy = 1;
		gridBagConstraints6.gridwidth = 1;
		gridBagConstraints6.fill = java.awt.GridBagConstraints.BOTH;
		this.add(getJPanel(), gridBagConstraints1);
		this.add(getPanelLayoutSettings(), constraintsLayoutSettings);
		this.add(getPanelBoxSettings(), constraintsBoxSettings);
		this.add(getJPanel3(), gridBagConstraints4);
		this.add(getJPanel4(), gridBagConstraints5);
		this.add(getJPanel5(), gridBagConstraints6);
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setPreferredSize(new java.awt.Dimension(100,160));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Layout-Settings", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
		}
		return jPanel;
	}
	/**
	 * This method initializes panelLayoutSettings	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getPanelLayoutSettings() {
		if (panelLayoutSettings == null) {
			jLabel2 = new JLabel();
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			jLabel1 = new JLabel();
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			jLabel = new JLabel();
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			panelLayoutSettings = new JPanel();
			panelLayoutSettings.setLayout(new GridBagLayout());
			panelLayoutSettings.setPreferredSize(new java.awt.Dimension(370,100));
			panelLayoutSettings.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Layout-Settings", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			gridBagConstraints7.gridx = 1;
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.weightx = 2.0;
			gridBagConstraints7.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints7.anchor = java.awt.GridBagConstraints.NORTH;
			gridBagConstraints7.gridwidth = 2;
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.gridy = 0;
			gridBagConstraints8.weightx = 1.0;
			gridBagConstraints8.anchor = java.awt.GridBagConstraints.NORTH;
			jLabel.setText("Theme");
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 2;
			jLabel1.setText("locale");
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 2;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints2.anchor = java.awt.GridBagConstraints.NORTH;
			gridBagConstraints2.gridwidth = 2;
			gridBagConstraints31.gridx = 1;
			gridBagConstraints31.gridy = 3;
			gridBagConstraints31.weightx = 3.0D;
			gridBagConstraints31.weighty = 3.0D;
			gridBagConstraints31.gridwidth = 3;
			gridBagConstraints12.gridx = 1;
			gridBagConstraints12.gridy = 4;
			gridBagConstraints12.gridwidth = 3;
			jLabel2.setText("(Anwendungs-Neustart erforderlich)");
			jLabel2.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD | java.awt.Font.ITALIC, 10));
			panelLayoutSettings.add(getJComboBoxTheme(), gridBagConstraints7);
			panelLayoutSettings.add(jLabel, gridBagConstraints8);
			panelLayoutSettings.add(jLabel1, gridBagConstraints11);
			panelLayoutSettings.add(getJComboBoxLocale(), gridBagConstraints2);
			panelLayoutSettings.add(getJPanel1(), gridBagConstraints31);
			panelLayoutSettings.add(jLabel2, gridBagConstraints12);
		}
		return panelLayoutSettings;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getPanelBoxSettings() {
		if (panelBoxSettings == null) {
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints42 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			panelBoxSettings = new JPanel();
			panelBoxSettings.setLayout(new GridBagLayout());
			panelBoxSettings.setPreferredSize(new java.awt.Dimension(390,140));
			panelBoxSettings.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Box-Settings", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints42.gridx = 0;
			gridBagConstraints42.gridy = 1;
			gridBagConstraints42.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints51.gridx = 1;
			gridBagConstraints51.gridy = 0;
			gridBagConstraints51.weightx = 1.0;
			gridBagConstraints51.weighty = 1.0;
			gridBagConstraints51.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints51.gridheight = 5;
			panelBoxSettings.add(getJButtonAnlegen(), gridBagConstraints3);
			panelBoxSettings.add(getJButtonLoeschen(), gridBagConstraints42);
			panelBoxSettings.add(getJScrollPaneBoxSettings(), gridBagConstraints51);
		}
		return panelBoxSettings;
	}
	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setMinimumSize(new java.awt.Dimension(100,100));
			jPanel3.setPreferredSize(new java.awt.Dimension(100,100));
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Layout-Settings", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
		}
		return jPanel3;
	}
	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setPreferredSize(new java.awt.Dimension(100,160));
			jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Layout-Settings", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
		}
		return jPanel4;
	}
	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setPreferredSize(new java.awt.Dimension(100,100));
			jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Layout-Settings", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
		}
		return jPanel5;
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
			jComboBoxTheme.setPreferredSize(new java.awt.Dimension(105,25));
		}
		return jComboBoxTheme;
	}
	/**
	 * This method initializes jButton	
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
	 * This method initializes jButton1	
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
			jScrollPaneBoxSettings.setViewportView(getJTableBoxSettings());
		}
		return jScrollPaneBoxSettings;
	}
	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */    
	public JTable getJTableBoxSettings() {
		if (jTableBoxSettings == null) {
			modelBoxTable = new GuiBoxSettingsTableModel(control);
			jTableBoxSettings = new JTable(modelBoxTable);
			jTableBoxSettings.setName("BoxSettings");
			jTableBoxSettings.getColumnModel().getColumn(0).setPreferredWidth(140);
			jTableBoxSettings.getColumnModel().getColumn(1).setPreferredWidth(100);
			jTableBoxSettings.getColumnModel().getColumn(2).setPreferredWidth(80);
			jTableBoxSettings.getColumnModel().getColumn(3).setCellRenderer( new GuiBoxTableCellRenderer(control));
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
			jComboBoxLocale.setPreferredSize(new java.awt.Dimension(105,25));
		}
		return jComboBoxLocale;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
		}
		return jPanel1;
	}
        }
