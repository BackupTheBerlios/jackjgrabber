package presentation.muxxer;
/*
GuiMuxxerView.java by Geist Alexander 

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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import service.SerGUIUtils;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import control.ControlMain;
import control.ControlMuxxerView;


public class GuiMuxxerView extends JDialog{
	
	ControlMuxxerView control;
	JList jListFiles;
    JCheckBox cbStartPX;
    JCheckBox cbStartMplex;
	JRadioButton rbSVCD;
	JRadioButton rbDVD;
	JRadioButton rbMPEG;
	JButton pbOk;
	JPanel mainPanel;
	ButtonGroup buttonGroupMuxxType = new ButtonGroup();

	public GuiMuxxerView(ControlMuxxerView control) {
		super(ControlMain.getControl().getView());
		this.setControl(control);
        this.setTitle("Demultiplex/Multiplex Options");
		initialize();
        pack();    
        SerGUIUtils.center(this);
	}
	
	private void initialize() {
		this.getContentPane().add(this.getMainPanel());
	}
	    
	    
    private JPanel getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new JPanel();
			FormLayout layout = new FormLayout("pref:grow, 25, pref", //columns
					"pref, 15, pref, 5, pref, pref, pref, 10, pref"); //rows
			PanelBuilder builder = new PanelBuilder(mainPanel, layout);
			builder.setDefaultDialogBorder();
			CellConstraints cc = new CellConstraints();
			
            if (control.getFiles()!=null && control.getFiles().size()>0) {
                builder.add(new JScrollPane(this.getJListFiles()),                  cc.xywh(1,1,1, 9));   
            }
            builder.add(this.getCbStartPX()                 ,                   cc.xy(3,1));
            builder.addSeparator("",                                            cc.xy(3,2));
            builder.add(this.getCbStartMplex(),                                 cc.xy(3,3));
            builder.add(this.getRbMPEG(),                                       cc.xy(3,5));
            builder.add(this.getRbSVCD(),                                       cc.xy(3,6));
            builder.add(this.getRbDVD(),                                        cc.xy(3,7));
            
			builder.add(this.getPbOk(),  cc.xy(3, 9));
		}
		return mainPanel;
	}

	/**
	 * @return Returns the control.
	 */
	public ControlMuxxerView getControl() {
		return control;
	}
	/**
	 * @param control The control to set.
	 */
	public void setControl(ControlMuxxerView control) {
		this.control = control;
	}
	/**
	 * @return Returns the jListFiles.
	 */
	public JList getJListFiles() {
		if (jListFiles==null) {
			jListFiles=new JList(control.getFiles().toArray());
		}
		return jListFiles;
	}
	/**
	 * @return Returns the pbOk.
	 */
	public JButton getPbOk() {
		if (pbOk == null) {
			pbOk = new JButton(ControlMain.getProperty("button_ok"));
			pbOk.setActionCommand("ok");
			pbOk.addActionListener(control);
		}
		return pbOk;
	}
	/**
	 * @return Returns the rbDVD.
	 */
	public JRadioButton getRbDVD() {
		if (rbDVD == null) {
			rbDVD = new JRadioButton("DVD");
            rbDVD.addActionListener(control);
			buttonGroupMuxxType.add(rbDVD);
            if (!control.getOptions().isUseMplex()) {
                rbDVD.setEnabled(false);
            }
		}
		return rbDVD;
	}
	/**
	 * @return Returns the rbMPEG.
	 */
	public JRadioButton getRbMPEG() {
		if (rbMPEG == null) {
			rbMPEG = new JRadioButton("Mpeg");
            rbMPEG.addActionListener(control);
			buttonGroupMuxxType.add(rbMPEG);
            if (!control.getOptions().isUseMplex()) {
                rbMPEG.setEnabled(false);
            }
		}
		return rbMPEG;
	}
	/**
	 * @return Returns the rbSVCD.
	 */
	public JRadioButton getRbSVCD() {
		if (rbSVCD == null) {
			rbSVCD = new JRadioButton("SVCD");
            rbSVCD.addActionListener(control);
			buttonGroupMuxxType.add(rbSVCD);
            if (!control.getOptions().isUseMplex()) {
                rbSVCD.setEnabled(false);
            }
		}
		return rbSVCD;
	}
    
    /**
     * @return Returns the cbStartPX.
     */
    public JCheckBox getCbStartPX() {
        if (cbStartPX == null) {
            cbStartPX = new JCheckBox(ControlMain.getProperty("cbStartPX"));
            cbStartPX.setActionCommand("cbStartPX");
            cbStartPX.addActionListener(control);
        }
        return cbStartPX;
    }
    /**
     * @return Returns the cbStartMplex.
     */
    public JCheckBox getCbStartMplex() {
        if (cbStartMplex == null) {
            cbStartMplex = new JCheckBox(ControlMain.getProperty("cbStartMplex"));
            cbStartMplex.setActionCommand("cbStartMplex");
            cbStartMplex.addActionListener(control);
        }
        return cbStartMplex;
    }
    
    public void checkMplexButtons(boolean enable) {
        this.getRbDVD().setEnabled(enable);
        this.getRbMPEG().setEnabled(enable);
        this.getRbSVCD().setEnabled(enable);
    }
}
