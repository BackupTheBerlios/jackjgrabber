/*
GuiSettingsTabPath.java by Geist Alexander 

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
import javax.swing.JLabel;
import javax.swing.JTextField;

import presentation.GuiTab;
import service.SerIconManager;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import control.ControlMain;
import control.ControlSettingsTabPath;

public class GuiSettingsTabPath extends GuiTab {
    
	private ControlSettingsTabPath control;
	private JTextField jTextFieldRecordSavePath;
	private JTextField jTextFieldUdrecPath;
	private JTextField jTextFieldProjectXPath;
	private JTextField jTextFieldVlcPath;
	private JTextField jTextFieldShutdonwToolPath;
	private JButton jButtonRecordPathFileChooser = null;
	private JButton jButtonUdrecPathFileChooser = null;
	private JButton jButtonProjectXPathFileChooser = null;
	private JButton jButtonVlcPathFileChooser = null;
	private JButton jButtonShutdownToolPathFileChooser = null;
	private SerIconManager iconManager = SerIconManager.getInstance();
       
    public GuiSettingsTabPath(ControlSettingsTabPath ctrl) {
		super();
		this.setControl(ctrl);
		initialize();
	}
    
    protected void initialize() {
        FormLayout layout = new FormLayout(
				  "pref, 10, f:pref:grow, 5, pref",  		// columns 
				  "pref, pref, pref, pref, pref"); 			// rows
				PanelBuilder builder = new PanelBuilder(this, layout);
				builder.setDefaultDialogBorder();
				CellConstraints cc = new CellConstraints();
				
				builder.add(new JLabel(ControlMain.getProperty("label_recordPath")),		cc.xy	(1, 1));
				builder.add(this.getJTextFieldRecordSavePath(),								cc.xy	(3, 1));
				builder.add(this.getJButtonRecordPathFileChooser(),							cc.xy	(5, 1));
				builder.add(new JLabel(ControlMain.getProperty("label_projectXPath")),		cc.xy	(1, 2));
				builder.add(this.getJTextFieldProjectXPath(),								cc.xy	(3, 2));
				builder.add(this.getJButtonProjectXPathFileChooser(),						cc.xy	(5, 2));
				builder.add(new JLabel(ControlMain.getProperty("label_udrecPath")),			cc.xy	(1, 3));
				builder.add(this.getJTextFieldUdrecPath(),									cc.xy	(3, 3));
				builder.add(this.getJButtonUdrecPathFileChooser(),							cc.xy	(5, 3));
				builder.add(new JLabel(ControlMain.getProperty("label_vlcPath")),			cc.xy	(1, 4));
				builder.add(this.getJTextFieldVlcPath(),									cc.xy	(3, 4));
				builder.add(this.getJButtonVlcPathFileChooser(),							cc.xy	(5, 4));
				builder.add(new JLabel(ControlMain.getProperty("label_shutdownToolPath")),	cc.xy	(1, 5));
				builder.add(this.getJTextFieldShutdonwToolPath(),							cc.xy	(3, 5));
				builder.add(this.getJButtonShutdownToolPathFileChooser(),					cc.xy	(5, 5));
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
    public ControlSettingsTabPath getControl() {
        return control;
    }
    /**
     * @param control The control to set.
     */
    public void setControl(ControlSettingsTabPath control) {
        this.control = control;
    }
    /**
     * @return Returns the jButtonShutdownToolPathFileChooser.
     */
    public JButton getJButtonShutdownToolPathFileChooser() {
        if (jButtonShutdownToolPathFileChooser == null) {
            jButtonShutdownToolPathFileChooser = new JButton(iconManager.getIcon("Open16.gif"));
            jButtonShutdownToolPathFileChooser.setActionCommand("shutdownToolPath");
            jButtonShutdownToolPathFileChooser.addActionListener(control);
		}
        return jButtonShutdownToolPathFileChooser;
    }
    /**
     * @return Returns the jTextFieldShutdonwToolPath.
     */
    public JTextField getJTextFieldShutdonwToolPath() {
        if (jTextFieldShutdonwToolPath == null) {
            jTextFieldShutdonwToolPath = new JTextField();
            jTextFieldShutdonwToolPath.addKeyListener(control);
            jTextFieldShutdonwToolPath.setName("shutdownToolPath");
            jTextFieldShutdonwToolPath.setPreferredSize(new Dimension(340, 19));
		}
        return jTextFieldShutdonwToolPath;
    }
}
