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
import java.awt.event.*;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager.*;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.plaf.plastic.*;

import control.ControlMain;
import control.ControlSettingsTabMain;

public class GuiSettingsTabMain extends GuiTab {

	private ControlSettingsTabMain		control;
	private JPanel						panelBoxSettings		= null;
	private JButton						jButtonAnlegen			= null;
	private JButton						jButtonLoeschen			= null;
	private JFormattedTextField			tfBoxIp					= null;
	private JComboBox					jComboBoxTheme			= null;
	private JComboBox					jComboBoxLookAndFeel	= null;
	private JComboBox					jComboBoxLocale			= null;
	private JScrollPane					jScrollPaneBoxSettings	= null;
	private JTable						jTableBoxSettings		= null;
	private GuiBoxSettingsTableModel	modelBoxTable;
	private JPanel						panelLayoutSettings		= null;
	private JCheckBox					cbStartFullscreen;
	private JCheckBox					cbShowLogo;
	private JCheckBox					cbUseSysTray;

	public final String[]				themes					= {"Silver", "BrownSugar", "DarkStar", "DesertBlue",
			"ExperienceBlue", "SkyBluerTahoma"					};

	public LookAndFeelHolder[]			lookAndFeels;

	public GuiSettingsTabMain(ControlSettingsTabMain ctrl) {
		super();
		this.setControl(ctrl);
		initialize();
	}

	protected void initialize() {

		// Lade verfügbaren Look & Feels

		LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
		lookAndFeels = new LookAndFeelHolder[looks.length];

		int currentSelectedLookAndFeel = 0;
		String currentSelLFClassName = ControlMain.getSettings().getLookAndFeel();
		for (int i = 0; i < looks.length; i++) {
			lookAndFeels[i] = new LookAndFeelHolder(looks[i].getName(), looks[i].getClassName());
			;
			if (lookAndFeels[i].getLookAndFeelClassName().equals(currentSelLFClassName)) {
				currentSelectedLookAndFeel = i;
			}
		}

		FormLayout layout = new FormLayout("f:pref:grow, 100", // columns
				"f:pref, 10, pref"); // rows
		PanelBuilder builder = new PanelBuilder(this, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();

		builder.add(this.getPanelBoxSettings(), cc.xy(1, 1));
		builder.add(this.getPanelLayoutSettings(), cc.xy(1, 3));

		jComboBoxLookAndFeel.setSelectedIndex(currentSelectedLookAndFeel);
	}

	private JPanel getPanelBoxSettings() {
		if (panelBoxSettings == null) {
			panelBoxSettings = new JPanel();
			FormLayout layout = new FormLayout("pref:grow, 5, pref", //columna
					"pref:grow, pref:grow, pref:grow, 40:grow"); //rows
			PanelBuilder builder = new PanelBuilder(panelBoxSettings, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator(ControlMain.getProperty("label_networkSettings"), cc.xywh(1, 1, 3, 1));
			builder.add(this.getJScrollPaneBoxSettings(), cc.xywh(1, 2, 1, 3));
			builder.add(this.getJButtonAnlegen(), cc.xy(3, 2));
			builder.add(this.getJButtonLoeschen(), cc.xy(3, 3));
		}
		return panelBoxSettings;
	}

	private JPanel getPanelLayoutSettings() {
		if (panelLayoutSettings == null) {
			panelLayoutSettings = new JPanel();
			FormLayout layout = new FormLayout("f:300, 10, pref", //columna
					"pref, pref, pref, pref, 5, pref, pref, pref,pref"); //rows
			PanelBuilder builder = new PanelBuilder(panelLayoutSettings, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator(ControlMain.getProperty("label_guiSettings"), cc.xywh(1, 1, 3, 1));

			builder.add(new JLabel(ControlMain.getProperty("label_lookandfeel")), cc.xy(1, 2));
			builder.add(this.getJComboBoxLookAndFeel(), cc.xy(3, 2));

			builder.add(new JLabel(ControlMain.getProperty("label_theme")), cc.xy(1, 4));
			builder.add(this.getJComboBoxTheme(), cc.xy(3, 4));
			builder.add(new JLabel(ControlMain.getProperty("label_lang")), cc.xy(1, 6));
			builder.add(this.getJComboBoxLocale(), cc.xy(3, 6));

			builder.add(this.getCbStartFullscreen(), cc.xyw(1, 7, 3));
			builder.add(this.getCbShowLogo(), cc.xyw(1, 8, 3));
			builder.add(this.getCbUseSysTray(), cc.xyw(1, 9, 3));
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
			jComboBoxLocale = new JComboBox(control.localeNames);
			jComboBoxLocale.addItemListener(control);
			jComboBoxLocale.setName("locale");
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
			jTableBoxSettings.getColumnModel().getColumn(3).setCellRenderer(new GuiBoxSettingsTableCellRenderer());

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
			jComboBoxTheme = new JComboBox(themes);
			jComboBoxTheme.addItemListener(control);
			jComboBoxTheme.setName("theme");
		}
		return jComboBoxTheme;
	}

	/**
	 * This method initializes jComboBoxLocale
	 * 
	 * @return javax.swing.JComboBox
	 */
	public JComboBox getJComboBoxLookAndFeel() {
		if (jComboBoxLookAndFeel == null) {
			jComboBoxLookAndFeel = new JComboBox(lookAndFeels);
			jComboBoxLookAndFeel.addItemListener(control);
			jComboBoxLookAndFeel.setName("lookAndFeel");
			jComboBoxLookAndFeel.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					String lookAndFeel = ((LookAndFeelHolder) jComboBoxLookAndFeel.getSelectedItem()).getLookAndFeelClassName();
					boolean enable = lookAndFeel.equals(PlasticLookAndFeel.class.getName());
					jComboBoxTheme.setEnabled(enable);
				}

			});
		}
		return jComboBoxLookAndFeel;
	}

	public JFormattedTextField getTfBoxIp() {
		if (tfBoxIp == null) {
			try {
				tfBoxIp = new JFormattedTextField(new MaskFormatter("###.###.###.###"));
				((MaskFormatter) tfBoxIp.getFormatter()).setAllowsInvalid(false);
				((MaskFormatter) tfBoxIp.getFormatter()).setOverwriteMode(true);
			}
			catch (ParseException e) {
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
			jButtonAnlegen.setText(ControlMain.getProperty("button_create"));
			jButtonAnlegen.setActionCommand("add");
			jButtonAnlegen.addActionListener(control);
			jButtonAnlegen.setPreferredSize(new java.awt.Dimension(90, 25));
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
			jButtonLoeschen.setText(ControlMain.getProperty("button_delete"));
			jButtonLoeschen.setActionCommand("delete");
			jButtonLoeschen.addActionListener(control);
			jButtonLoeschen.setPreferredSize(new java.awt.Dimension(90, 25));
		}
		return jButtonLoeschen;
	}

	/**
	 * @return Returns the cbStartFullscreen.
	 */
	public JCheckBox getCbStartFullscreen() {
		if (cbStartFullscreen == null) {
			cbStartFullscreen = new JCheckBox(ControlMain.getProperty("cbFullscreen"));
			cbStartFullscreen.setName("startFullscreen");
			cbStartFullscreen.addItemListener(control);
		}
		return cbStartFullscreen;
	}

	/**
	 * @return Returns the cbShowLogo.
	 */
	public JCheckBox getCbShowLogo() {
		if (cbShowLogo == null) {
			cbShowLogo = new JCheckBox(ControlMain.getProperty("cbShowLogo"));
			cbShowLogo.setName("showLogo");
			cbShowLogo.addItemListener(control);
		}
		return cbShowLogo;
	}

	/**
	 * @return Returns the cbUseSysTray.
	 */
	public JCheckBox getCbUseSysTray() {
		if (cbUseSysTray == null) {
			cbUseSysTray = new JCheckBox(ControlMain.getProperty("cbUseSystray"));
			cbUseSysTray.setName("useSysTray");
			cbUseSysTray.addItemListener(control);
		}
		return cbUseSysTray;
	}

	/**
	 * @return Returns the control.
	 */
	public ControlSettingsTabMain getControl() {
		return control;
	}
	/**
	 * @param control
	 *            The control to set.
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
	 * @param modelBoxTable
	 *            The modelBoxTable to set.
	 */
	public void setModelBoxTable(GuiBoxSettingsTableModel modelBoxTable) {
		this.modelBoxTable = modelBoxTable;
	}
}