/*
GuiSettingsTabTools.java by Geist Alexander 

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
package presentation.settings;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JTextField;

import presentation.GuiTab;
import service.SerIconManager;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import control.ControlSettingsTabTools;

public class GuiSettingsTabTools extends GuiTab {
    
	private ControlSettingsTabTools control;
	private JTextField jTextFieldRecordSavePath;
	private JTextField jTextFieldUdrecPath;
	private JTextField jTextFieldProjectXPath;
	private JTextField jTextFieldVlcPath =null;
	private JButton jButtonRecordPathFileChooser = null;
	private JButton jButtonUdrecPathFileChooser = null;
	private JButton jButtonProjectXPathFileChooser = null;
	private JButton jButtonVlcPathFileChooser = null;
	private SerIconManager iconManager = SerIconManager.getInstance();
       
    public GuiSettingsTabTools(ControlSettingsTabTools ctrl) {
		super();
		this.setControl(ctrl);
		initialize();
	}
    
    protected void initialize() {
        FormLayout layout = new FormLayout(
				  "f:pref:grow",  		// columns 
				  "f:pref, 15, pref, 15,  pref, pref, 15, f:pref"); 			// rows
		PanelBuilder builder = new PanelBuilder(this, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();
    }
	    	
	
	/**
	 * @return Returns the jButtonRecordPathFileChooser.
	 */
	public JButton getJButtonRecordPathFileChooser() {
		if (jButtonRecordPathFileChooser == null) {
			jButtonRecordPathFileChooser = new JButton(iconManager.getIcon("Open16.gif"));
			jButtonRecordPathFileChooser.setActionCommand("recordPath");
			jButtonRecordPathFileChooser.addActionListener(control);
		}
		return jButtonRecordPathFileChooser;
	}
	/**
	 * @return Returns the jButtonUdrecPathFileChooser.
	 */
	public JButton getJButtonUdrecPathFileChooser() {
		if (jButtonUdrecPathFileChooser == null) {
			jButtonUdrecPathFileChooser = new JButton(iconManager.getIcon("Open16.gif"));
			jButtonUdrecPathFileChooser.setActionCommand("udrecPath");
			jButtonUdrecPathFileChooser.addActionListener(control);
		}
		return jButtonUdrecPathFileChooser;
	}
	/**
	 * @return Returns the jButtonProjectXPathFileChooser.
	 */
	public JButton getJButtonProjectXPathFileChooser() {
		if (jButtonProjectXPathFileChooser == null) {
			jButtonProjectXPathFileChooser = new JButton(iconManager.getIcon("Open16.gif"));
			jButtonProjectXPathFileChooser.setActionCommand("projectxPath");
			jButtonProjectXPathFileChooser.addActionListener(control);
		}
		return jButtonProjectXPathFileChooser;
	}
	/**
	 * @return Returns the jTextFieldRecordSavePath.
	 */
	public JTextField getJTextFieldRecordSavePath() {
		if (jTextFieldRecordSavePath == null) {
			jTextFieldRecordSavePath = new JTextField();
			jTextFieldRecordSavePath.setPreferredSize(new Dimension(340, 19));
			jTextFieldRecordSavePath.setEditable(false);
		}
		return jTextFieldRecordSavePath;
	}
	/**
	 * @return Returns the jTextFieldUdrecPath.
	 */
	public JTextField getJTextFieldUdrecPath() {
		if (jTextFieldUdrecPath == null) {
			jTextFieldUdrecPath = new JTextField();
			jTextFieldUdrecPath.addKeyListener(control);
			jTextFieldUdrecPath.setName("udrecPath");
			jTextFieldUdrecPath.setPreferredSize(new Dimension(340, 19));
		}
		return jTextFieldUdrecPath;
	}
	
	/**
	 * @return Returns the jTextFieldProjectXPath.
	 */
	public JTextField getJTextFieldProjectXPath() {
		if (jTextFieldProjectXPath == null) {
			jTextFieldProjectXPath = new JTextField();
			jTextFieldProjectXPath.setPreferredSize(new Dimension(340, 19));
			jTextFieldProjectXPath.setEditable(false);
		}
		return jTextFieldProjectXPath;
	}
	
	/**
	 * @return Returns the jTextFieldVlcPath.
	 */
	public JTextField getJTextFieldVlcPath() {
		if (jTextFieldVlcPath == null) {
		    jTextFieldVlcPath = new JTextField();
		    jTextFieldVlcPath.addKeyListener(control);
		    jTextFieldVlcPath.setName("vlcPath");
		    jTextFieldVlcPath.setPreferredSize(new Dimension(340, 19));
		}
		return jTextFieldVlcPath;
	}
	/**
	 * @return Returns the jButtonVlcPathFileChooser.
	 */
	public JButton getJButtonVlcPathFileChooser() {
		if (jButtonVlcPathFileChooser == null) {
		    jButtonVlcPathFileChooser = new JButton(iconManager.getIcon("Open16.gif"));
		    jButtonVlcPathFileChooser.setActionCommand("vlcPath");
		    jButtonVlcPathFileChooser.addActionListener(control);
		}
		return jButtonVlcPathFileChooser;
	}
    /**
     * @return Returns the control.
     */
    public ControlSettingsTabTools getControl() {
        return control;
    }
    /**
     * @param control The control to set.
     */
    public void setControl(ControlSettingsTabTools control) {
        this.control = control;
    }
}
