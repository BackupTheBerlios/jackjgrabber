/*
 * Created on 20.09.2004
 */
package presentation;

import java.awt.Dimension;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import model.BOLocale;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import control.ControlMain;
import control.ControlSettingsTab;
/**
 * @author Geist Alexander
 *
 */
public class GuiTabSettings extends JPanel {

	private JPanel panelBoxSettings = null;
	private JPanel panelLayoutSettings = null;
	private JPanel panelRecordSettings = null;
	private JComboBox jComboBoxTheme = null;
	private JButton jButtonAnlegen = null;
	private JButton jButtonLoeschen = null;
	private JScrollPane jScrollPaneBoxSettings = null;
	private JTable jTableBoxSettings = null;
	private ControlSettingsTab control;
	private GuiBoxSettingsTableModel modelBoxTable;
	private JLabel jLabel1 = null;
	private JComboBox jComboBoxLocale = null;
	private JFormattedTextField tfServerPort = null;

	public GuiTabSettings(ControlSettingsTab ctrl) {
		super();
		this.setControl(ctrl);
		initialize();
	}

	private  void initialize() {
		FormLayout layout = new FormLayout(
						  "f:pref:grow, 20, f:200:grow",  		// columns 
						  "pref, 20, pref"); 			// rows
				PanelBuilder builder = new PanelBuilder(this, layout);
				builder.setDefaultDialogBorder();
				CellConstraints cc = new CellConstraints();
		
				builder.add(this.getPanelBoxSettings(),		   		cc.xywh(1, 1, 1, 1, CellConstraints.LEFT, CellConstraints.TOP));
				builder.add(this.getPanelLayoutSettings(),			cc.xywh(3, 1, 1, 1, CellConstraints.LEFT, CellConstraints.TOP));				
				builder.add(this.getPanelRecordSettings(),			cc.xywh(1, 3, 1, 1, CellConstraints.LEFT, CellConstraints.TOP));
	}
	    
	private JPanel getPanelBoxSettings() {
		if (panelBoxSettings == null) {
			panelBoxSettings = new JPanel();
			FormLayout layout = new FormLayout(
					  "pref:grow, 5, pref",	 		//columna 
			  "pref:grow, pref:grow, pref:grow, 40:grow");				//rows
			PanelBuilder builder = new PanelBuilder(panelBoxSettings, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator("Box-Settings",						cc.xywh	(1, 1, 3, 1));
			builder.add(this.getJScrollPaneBoxSettings(),	  			cc.xywh	(1, 2, 1, 3));
			builder.add(this.getJButtonAnlegen(),						cc.xy	(3, 2));
			builder.add(this.getJButtonLoeschen(),						cc.xy	(3, 3));
		}
		return panelBoxSettings;
	}
	
	private JPanel getPanelRecordSettings() {
		if (panelRecordSettings == null) {
			panelRecordSettings = new JPanel();
			FormLayout layout = new FormLayout(
					  "pref:grow, 5, pref",	 		//columna 
			  "pref:grow, pref:grow, pref:grow, 40:grow");				//rows
			PanelBuilder builder = new PanelBuilder(panelRecordSettings, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator("Aufname-Settings",					cc.xywh	(1, 1, 3, 1));
			builder.add(new JLabel("Streamingserver-Port"),	  			cc.xy	(1, 2));
			builder.add(this.getTfServerPort(),							cc.xy	(3, 2));
		}
		return panelRecordSettings;
	}
  
	private JPanel getPanelLayoutSettings() {
		if (panelLayoutSettings == null) {
			panelLayoutSettings = new JPanel();
			FormLayout layout = new FormLayout(
					  "pref, 10, pref",	 		//columna 
			  "pref, pref, pref, pref");	//rows
			PanelBuilder builder = new PanelBuilder(panelLayoutSettings, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator("Layout-Settings",						cc.xywh	(1, 1, 3, 1));
			builder.add(new JLabel("Theme"),				  			cc.xy	(1, 2));
			builder.add(this.getJComboBoxTheme(),						cc.xy	(3, 2));
			builder.add(new JLabel("Locale"),				  			cc.xy	(1, 4));
			builder.add(this.getJComboBoxLocale(),						cc.xy	(3, 4));
		}
		return panelLayoutSettings;
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
	 * This method initializes jButtonAnlegen	
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
	 * This method initializes getJButtonLoeschen	
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
			jScrollPaneBoxSettings.setPreferredSize(new Dimension(350, 100));
			jScrollPaneBoxSettings.setViewportView(getJTableBoxSettings());
		}
		return jScrollPaneBoxSettings;
	}
	/**
	 * This method initializes jTableBoxSettings	
	 * 	
	 * @return javax.swing.JTable	
	 */    
	public JTable getJTableBoxSettings() {
		if (jTableBoxSettings == null) {
			modelBoxTable = new GuiBoxSettingsTableModel(control);
			jTableBoxSettings = new JTable(modelBoxTable);
			jTableBoxSettings.setName("BoxSettings");
			jTableBoxSettings.getColumnModel().getColumn(0).setPreferredWidth(80);
			jTableBoxSettings.getColumnModel().getColumn(1).setPreferredWidth(100);
			jTableBoxSettings.getColumnModel().getColumn(2).setPreferredWidth(80);
			jTableBoxSettings.getColumnModel().getColumn(3).setPreferredWidth(40);
			jTableBoxSettings.getColumnModel().getColumn(3).setCellRenderer( new GuiBoxSettingsTableCellRenderer(control));
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
	 * @return Returns the tfServerPort.
	 */
	public JFormattedTextField getTfServerPort() {
		if (tfServerPort == null) {
			try {
				tfServerPort = new JFormattedTextField(new MaskFormatter("####"));
				((MaskFormatter)tfServerPort.getFormatter()).setAllowsInvalid(false);
				((MaskFormatter)tfServerPort.getFormatter()).setOverwriteMode(true);
				tfServerPort.setPreferredSize(new java.awt.Dimension(40,22));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tfServerPort;
	}
}
