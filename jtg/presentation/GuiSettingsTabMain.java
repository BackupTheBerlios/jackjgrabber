/*
GuiSettingsTabMain.java by Geist Alexander 

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
package presentation;

import java.awt.Dimension;
import java.text.ParseException;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;

import model.BOLocale;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import control.ControlMain;
import control.ControlSettingsTabMain;

public class GuiSettingsTabMain extends GuiTab {
    
    private ControlSettingsTabMain control;
	private JPanel panelBoxSettings = null;
	private JButton jButtonAnlegen = null;
	private JButton jButtonLoeschen = null;
	private JFormattedTextField tfBoxIp = null;
	private JComboBox jComboBoxTheme = null;
	private JComboBox jComboBoxLocale = null;
	private JScrollPane jScrollPaneBoxSettings = null;
	private JTable jTableBoxSettings = null;
	private GuiBoxSettingsTableModel modelBoxTable;
	private JPanel panelLayoutSettings = null;
	
    public GuiSettingsTabMain(ControlSettingsTabMain ctrl) {
		super();
		this.setControl(ctrl);
		initialize();
	}
    
    protected void initialize() {
        FormLayout layout = new FormLayout(
				  "f:pref:grow, 10, f:pref",  		// columns 
				  "f:pref"); 			// rows
		PanelBuilder builder = new PanelBuilder(this, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();

		builder.add(this.getPanelBoxSettings(),		   		cc.xy(1,1));
		builder.add(this.getPanelLayoutSettings(),	   		cc.xy(3,1));
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
	public JComboBox getJComboBoxLocale() {
		if (jComboBoxLocale == null) {
			jComboBoxLocale = new JComboBox(new BOLocale().getLocaleList().toArray());
			jComboBoxLocale.addItemListener(control);
			jComboBoxLocale.setName("locale");
			jComboBoxLocale.setPreferredSize(new java.awt.Dimension(105,19));
		}
		return jComboBoxLocale;
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
			jTableBoxSettings.getColumnModel().getColumn(1).setPreferredWidth(80);
			jTableBoxSettings.getColumnModel().getColumn(2).setPreferredWidth(80);
			jTableBoxSettings.getColumnModel().getColumn(3).setPreferredWidth(40);
			jTableBoxSettings.getColumnModel().getColumn(3).setCellRenderer( new GuiBoxSettingsTableCellRenderer());
			
			TableColumn columnIp = jTableBoxSettings.getColumnModel().getColumn(0);
			columnIp.setCellEditor(new DefaultCellEditor(this.getTfBoxIp()));
		}
		return jTableBoxSettings;
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
			jComboBoxTheme.setPreferredSize(new java.awt.Dimension(105,19));
		}
		return jComboBoxTheme;
	}
	
	public JFormattedTextField getTfBoxIp() {
		if (tfBoxIp == null) {
			try {
				tfBoxIp = new JFormattedTextField(new MaskFormatter("###.###.###.###"));
				((MaskFormatter)tfBoxIp.getFormatter()).setAllowsInvalid(false);
				((MaskFormatter)tfBoxIp.getFormatter()).setOverwriteMode(true);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tfBoxIp;
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
     * @return Returns the control.
     */
    public ControlSettingsTabMain getControl() {
        return control;
    }
    /**
     * @param control The control to set.
     */
    public void setControl(ControlSettingsTabMain control) {
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
}
