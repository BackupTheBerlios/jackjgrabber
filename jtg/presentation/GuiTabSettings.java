/*
 * Created on 08.08.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package presentation;


import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import service.SerGUIUtils;
import control.ControlMain;
import control.ControlSettingsTab;

/**
 * @author AlexG
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GuiTabSettings extends GuiTab {
	
	public JTextField tfBoxIp, tfVlcPath;
	public JButton pbVlcFileChooser, pbSave;

	private ControlSettingsTab control;
	
	public GuiTabSettings(ControlSettingsTab control) {
		super();
		this.setControl(control);
		this.createItems();

		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
//																		x  y  w  h   wx   wy
		SerGUIUtils.addComponent( this, gbl, new JLabel("Box-Ip  "),		0, 0, 1, 1,  0.0, 0.0 );
		SerGUIUtils.addComponent( this, gbl, tfBoxIp,						1, 0, 1, 1,  0.0, 0.0 );
		SerGUIUtils.addComponent( this, gbl, new JLabel("VLC_Path  "),		0, 1, 1, 1,  0.0, 0.0 );
		SerGUIUtils.addComponent( this, gbl, tfVlcPath,					1, 1, 4, 1,  1.0, 0.0 );
		SerGUIUtils.addComponent( this, gbl, pbVlcFileChooser,				5, 1, 1, 1,  0.0, 0.0 );
		SerGUIUtils.addComponent( this, gbl, pbSave,						1, 2, 1, 1,  0.0, 0.0 );
		SerGUIUtils.addComponent( this, gbl, new JPanel(),					0, 0, 8, 15, 1.0, 1.0 );
	}
	
	
	private void createItems()
	{
		tfBoxIp = new JTextField();
		tfBoxIp.setText(ControlMain.getBoxIp());
		tfBoxIp.setPreferredSize(new Dimension(120,20));
		
		tfVlcPath = new JTextField();
		tfVlcPath.setText(ControlMain.getSettings().getVlcPath());
		tfVlcPath.setPreferredSize(new Dimension(200,20));
		
		pbVlcFileChooser = new JButton("...");
		pbVlcFileChooser.setPreferredSize(new Dimension(30,20));
		pbVlcFileChooser.setActionCommand("VlcFileChooser");
		pbVlcFileChooser.addActionListener(this.getControl());
		
		pbSave = new JButton("Speichern");
		pbSave.setActionCommand("save");
		pbSave.addActionListener(this.getControl());
	}
	public JTextField getTfBoxIp() {
		return tfBoxIp;
	}
	/**
	 * @param tfBoxIp The tfBoxIp to set.
	 */
	public void setTfBoxIp(JTextField tfBoxIp) {
		this.tfBoxIp = tfBoxIp;
	}
	/**
	 * @return Returns the pbVlcFileChooser.
	 */
	public JButton getPbVlcFileChooser() {
		return pbVlcFileChooser;
	}
	/**
	 * @param pbVlcFileChooser The pbVlcFileChooser to set.
	 */
	public void setPbVlcFileChooser(JButton pbVlcFileChooser) {
		this.pbVlcFileChooser = pbVlcFileChooser;
	}
	/**
	 * @return Returns the tfVlcPath.
	 */
	public JTextField getTfVlcPath() {
		return tfVlcPath;
	}
	/**
	 * @param tfVlcPath The tfVlcPath to set.
	 */
	public void setTfVlcPath(JTextField tfVlcPath) {
		this.tfVlcPath = tfVlcPath;
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
	 * @return Returns the pbSave.
	 */
	public JButton getPbSave() {
		return pbSave;
	}
	/**
	 * @param pbSave The pbSave to set.
	 */
	public void setPbSave(JButton pbSave) {
		this.pbSave = pbSave;
	}
}
