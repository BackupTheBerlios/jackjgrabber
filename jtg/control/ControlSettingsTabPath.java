/*
ControlSettingsTabPath.java by Geist Alexander 

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
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import model.BOSettingsPath;
import presentation.settings.GuiSettingsTabPath;
import presentation.settings.GuiTabSettings;

public class ControlSettingsTabPath extends ControlTabSettings implements ActionListener, KeyListener  {

    GuiTabSettings settingsTab;
    
    public ControlSettingsTabPath (GuiTabSettings tabSettings) {
		this.setSettingsTab(tabSettings);
	}
    
    /* (non-Javadoc)
     * @see control.ControlTab#initialize()
     */
    public void run() {
        this.getTab().getJTextFieldRecordSavePath().setText(this.getSettings().getSavePath());
        this.getTab().getJTextFieldProjectXPath().setText(this.getSettings().getProjectXPath());
        this.getTab().getJTextFieldUdrecPath().setText(this.getSettings().getUdrecPath());
        this.getTab().getJTextFieldVlcPath().setText(this.getSettings().getVlcPath());
    }
		
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		while (true) {
		    if (action.equals("recordPath")) {
		  			this.openRecordPathFileChooser();
		  			break;
		  		}
		  		if (action.equals("udrecPath")) {
		  			this.openUdrecPathFileChooser();
		  			break;
		  		}
		  		if (action == "vlcPath") {
		  			this.openVlcPathFileChooser();
		  		}
		  		if (action.equals("projectxPath")) {
		  			this.openProjectXPathFileChooser();
		  			break;
		  		}
		  		break;
		}
	}
	public void keyTyped(KeyEvent event) {}
	
	public void keyPressed(KeyEvent event) {}
	
	public void keyReleased(KeyEvent event) {
		JTextField tf = (JTextField)event.getSource();
		while (true) {
			if (tf.getName().equals("udrecPath")){
			    this.getSettings().setUdrecPath(tf.getText());
			    break;
			}
			if (tf.getName().equals("vlcPath")){
			    this.getSettings().setVlcPath(tf.getText());
			    break;
			}
			break;
		}
	}
	
	private void openVlcPathFileChooser() {
		JFileChooser fc = new JFileChooser();
		FileFilter filter = new FileFilter(){
			public boolean accept(File f){
				return (f.getName().endsWith("vlc.exe") || f.isDirectory() );
			}
			public String getDescription(){
				return "vlc.exe";
			}
		};
		fc.setFileFilter(filter);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setDialogType(JFileChooser.SAVE_DIALOG);

		fc.setApproveButtonText(ControlMain.getProperty("msg_choose"));
		fc.setApproveButtonToolTipText( ControlMain.getProperty("msg_pathVlc"));
		int returnVal = fc.showSaveDialog( null ) ;

		if ( returnVal == JFileChooser.APPROVE_OPTION ) {
			String path = fc.getSelectedFile().toString();
			this.getTab().getJTextFieldVlcPath().setText(path);
			this.getSettings().setVlcPath(path);
		}	
	}

	private void openRecordPathFileChooser() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setDialogType(JFileChooser.SAVE_DIALOG);

		fc.setApproveButtonText(ControlMain.getProperty("msg_choose"));
		fc.setApproveButtonToolTipText( ControlMain.getProperty("msg_chooseDirectory"));
		int returnVal = fc.showSaveDialog( null ) ;

		if ( returnVal == JFileChooser.APPROVE_OPTION ) {
			String path = fc.getSelectedFile().toString();
			this.getTab().getJTextFieldRecordSavePath().setText(path);
			this.getSettings().setSavePath(path);
		}
	}
	
	private void openProjectXPathFileChooser() {
	    JFileChooser fc = new JFileChooser();
		FileFilter filter = new FileFilter(){
			public boolean accept(File f){
				return (f.getName().endsWith("ProjectX.jar") || f.isDirectory() );
			}
			public String getDescription(){
				return "ProjectX.jar";
			}
		};
		fc.setFileFilter(filter);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setDialogType(JFileChooser.SAVE_DIALOG);

		fc.setApproveButtonText(ControlMain.getProperty("msg_choose"));
		fc.setApproveButtonToolTipText( ControlMain.getProperty("msg_pathProjectX"));
		int returnVal = fc.showSaveDialog( null ) ;

		if ( returnVal == JFileChooser.APPROVE_OPTION ) {
		    String path = fc.getSelectedFile().toString();
			this.getTab().getJTextFieldProjectXPath().setText(path);
			this.getSettings().setProjectXPath(path);	
		}
	}
		
	private void openUdrecPathFileChooser() {
	    JFileChooser fc = new JFileChooser();
	    FileFilter filter = new FileFilter(){
			public boolean accept(File f){
				return (f.getName().endsWith("udrec.exe") || f.isDirectory() );
			}
			public String getDescription(){
				return "udrec.exe";
			}
		};
		fc.setFileFilter(filter);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setDialogType(JFileChooser.SAVE_DIALOG);

		fc.setApproveButtonText(ControlMain.getProperty("msg_choose"));
		fc.setApproveButtonToolTipText( ControlMain.getProperty("msg_pathUdrec"));
		int returnVal = fc.showSaveDialog( null ) ;

		if ( returnVal == JFileChooser.APPROVE_OPTION ) {
		    String path = fc.getSelectedFile().toString();
			this.getTab().getJTextFieldUdrecPath().setText(path);
			this.getSettings().setUdrecPath(path);	
		}
	}
    
    /* (non-Javadoc)
     * @see control.ControlTab#getMainView()
     */
    public GuiTabSettings getSettingsTab() {
        return settingsTab;
    }

    /* (non-Javadoc)
     * @see control.ControlTab#setMainView(presentation.GuiMainView)
     */
    public void setSettingsTab(GuiTabSettings tabSettings) {
        settingsTab = tabSettings;

    }
    
    private BOSettingsPath getSettings() {
        return ControlMain.getSettingsPath();
    }
    
    private GuiSettingsTabPath getTab() {
        return this.getSettingsTab().getSettingsTabPath();
    }
}
