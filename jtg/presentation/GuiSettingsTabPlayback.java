/*
GuiSettingsTabPlayback.java by Geist Alexander 

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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import control.ControlMain;
import control.ControlSettingsTabPlayback;

public class GuiSettingsTabPlayback extends GuiTab{
    
    private ControlSettingsTabPlayback control;
    private JPanel panelPlaybackSettings = null;
	private JButton jButtonAnlegen = null;
	private JButton jButtonLoeschen = null;
	private JCheckBox cbUseStandardOption = null;
	
	private JScrollPane jScrollPanePlaybackSettings = null;
	private JTable jTablePlaybackSettings = null;
	private GuiPlaybackSettingsTableModel playbackSettingsTableModel;
    
    public GuiSettingsTabPlayback(ControlSettingsTabPlayback ctrl) {
		super();
		this.setControl(ctrl);
		initialize();
	}
    
    protected void initialize() {
        FormLayout layout = new FormLayout(
				  "f:pref:grow",  		// columns 
				  "f:pref"); 			// rows
		PanelBuilder builder = new PanelBuilder(this, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();

		builder.add(this.getPanelPlaybackSettings(),		   		cc.xy(1,1));        
    }

    private JPanel getPanelPlaybackSettings() {
		if (panelPlaybackSettings == null) {
			panelPlaybackSettings = new JPanel();
			FormLayout layout = new FormLayout(
			        "pref:grow, 5, pref",	 		//columna 
			  "pref, 10, pref, pref, 15, pref, pref, 80, 10, pref");	//rows
			PanelBuilder builder = new PanelBuilder(panelPlaybackSettings, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator(ControlMain.getProperty("label_playbackOptions"),										cc.xywh	(1, 1, 3, 1));
			builder.add(new JLabel("z.B. xine http://$ip:31339/$vPid,$aPid1"),				cc.xywh	(1, 3, 3, 1));
			builder.add(new JLabel("z.B. d://programme/mplayer/mplayer.exe http://$ip:31339/$vPid,$aPid2"),	cc.xywh	(1, 4, 3, 1));
			builder.add(this.getJScrollPanePlaybackSettings(),								cc.xywh	(1, 6, 1, 3));
			builder.add(this.getJButtonAnlegen(),											cc.xy	(3, 6));
			builder.add(this.getJButtonLoeschen(),											cc.xy	(3, 7));
			builder.add(this.getCbUseStandardOption(),										cc.xy	(1, 10));
		}
		return panelPlaybackSettings;
	}
    
    /**
	 * This method initializes jScrollPanePlaybackSettings	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPanePlaybackSettings() {
		if (jScrollPanePlaybackSettings == null) {
			jScrollPanePlaybackSettings = new JScrollPane();
			jScrollPanePlaybackSettings.setPreferredSize(new Dimension(350, 150));
			jScrollPanePlaybackSettings.setViewportView(getJTablePlaybackSettings());
		}
		return jScrollPanePlaybackSettings;
	}
	
	/**
	 * This method initializes jTablePlaybackSettings	
	 * 	
	 * @return javax.swing.JTable	
	 */    
	public JTable getJTablePlaybackSettings() {
		if (jTablePlaybackSettings == null) {
			playbackSettingsTableModel = new GuiPlaybackSettingsTableModel(control);
			jTablePlaybackSettings = new JTable(playbackSettingsTableModel);
			jTablePlaybackSettings.setName("playbackSettings");
			jTablePlaybackSettings.getColumnModel().getColumn(0).setPreferredWidth(100);
			jTablePlaybackSettings.getColumnModel().getColumn(0).setMaxWidth(100);
			jTablePlaybackSettings.getColumnModel().getColumn(1).setPreferredWidth(120);
			jTablePlaybackSettings.getColumnModel().getColumn(2).setMaxWidth(80);
			jTablePlaybackSettings.getColumnModel().getColumn(2).setCellRenderer( new GuiPlaybackSettingsTableCellRenderer());
			jTablePlaybackSettings.getColumnModel().getColumn(3).setMaxWidth(80);
			jTablePlaybackSettings.getColumnModel().getColumn(3).setCellRenderer( new GuiPlaybackSettingsTableCellRenderer());
		}
		return jTablePlaybackSettings;
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
			jButtonLoeschen.setText(ControlMain.getProperty("button_delete"));
			jButtonLoeschen.setActionCommand("delete");
			jButtonLoeschen.addActionListener(control);
			jButtonLoeschen.setPreferredSize(new java.awt.Dimension(90,25));
		}
		return jButtonLoeschen;
	}
	
    /**
     * @return Returns the control.
     */
    public ControlSettingsTabPlayback getControl() {
        return control;
    }
    /**
     * @param control The control to set.
     */
    public void setControl(ControlSettingsTabPlayback control) {
        this.control = control;
    }
    /**
     * @return Returns the playbackSettingsTableModel.
     */
    public GuiPlaybackSettingsTableModel getPlaybackSettingsTableModel() {
        return playbackSettingsTableModel;
    }
    /**
     * @return Returns the cbUseStandardOption.
     */
    public JCheckBox getCbUseStandardOption() {
        if (cbUseStandardOption == null) {
            cbUseStandardOption = new JCheckBox(ControlMain.getProperty("cbUseStandard"));
            cbUseStandardOption.setName("useStandard");
            cbUseStandardOption.addItemListener(control);
		}
        return cbUseStandardOption;
    }
}
