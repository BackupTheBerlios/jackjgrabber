/*
 * ControlSettingsTabMain.java by Geist Alexander
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *  
 */
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileFilter;

import model.BOBox;
import model.BOLookAndFeelHolder;
import model.BOSettingsMain;

import org.apache.log4j.Logger;

import presentation.GuiMainView;
import presentation.settings.GuiSettingsTabMain;
import presentation.settings.GuiTabSettings;
import service.SerErrorStreamReadThread;
import service.SerInputStreamReadThread;

import com.jgoodies.plaf.plastic.Plastic3DLookAndFeel;
import com.jgoodies.plaf.plastic.PlasticLookAndFeel;
import com.jgoodies.plaf.plastic.PlasticXPLookAndFeel;

public class ControlSettingsTabMain extends ControlTabSettings implements ActionListener, ItemListener, KeyListener {

	GuiTabSettings			settingsTab;
	public final String[]	localeNames	= {"de,Deutsch", "en,Englisch", "fi,Finisch"};
	public BOLookAndFeelHolder[] lookAndFeels;
	private int currentSelectedLookAndFeel = 0;

	public ControlSettingsTabMain(GuiTabSettings tabSettings) {
		this.setSettingsTab(tabSettings);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see control.ControlTab#initialize()
	 */
	public void run() {
		this.getTab().getJComboBoxTheme().setSelectedItem(this.getSettings().getThemeLayout());
		this.getTab().getJComboBoxLocale().setSelectedItem(this.getSettings().getLocale());
		this.getTab().getCbShowLogo().setSelected(this.getSettings().isShowLogo());
		this.getTab().getCbStartFullscreen().setSelected(this.getSettings().isStartFullscreen());
		this.getTab().getCbStartVlcAtStart().setSelected(this.getSettings().isStartVlcAtStart());
		this.getTab().getCbUseSysTray().setSelected(this.getSettings().isUseSysTray());
		this.getTab().getJTextFieldVlcPath().setText(this.getSettings().getVlcPath());
		this.initLookAndFeels();
		this.getTab().getJComboBoxLookAndFeel().setSelectedIndex(currentSelectedLookAndFeel);
	}
	
	public BOLookAndFeelHolder[] initLookAndFeels() {
	    if (lookAndFeels==null) {
			LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
			lookAndFeels = new BOLookAndFeelHolder[looks.length];
	
			String currentSelLFClassName = this.getSettings().getLookAndFeel();
			for (int i = 0; i < looks.length; i++) {
				lookAndFeels[i] = new BOLookAndFeelHolder(looks[i].getName(),looks[i].getClassName());
				if (lookAndFeels[i].getLookAndFeelClassName().equals(currentSelLFClassName)) {
					currentSelectedLookAndFeel = i;
				}
			}
	    }
	    return lookAndFeels;
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		while (true) {
		    if (action == "delete") {
		  			this.actionRemoveBox();
		  		}
		  		if (action == "add") {
		  			this.actionAddBox();
		  		}
		  		if (action == "launchVlc") {
			  			this.actionStartVlc();
			  		}
		  		if (action == "vlcPath") {
			  			this.openVlcPathFileChooser();
			  		}
		  		if (action.equals("showLogo")) {
		  				this.getSettings().setShowLogo(((JCheckBox)e.getSource()).isSelected());
		  				break;
		  		}
		  		if (action.equals("startFullscreen")) {
		  				this.getSettings().setStartFullscreen(((JCheckBox)e.getSource()).isSelected());
		  				break;
		  		}
		  		if (action.equals("startVlc")) {
		  				this.getSettings().setStartVlcAtStart(((JCheckBox)e.getSource()).isSelected());
		  				break;
		  		}
		  		if (action.equals("useSysTray")) {
		  		    boolean selected = ((JCheckBox)e.getSource()).isSelected();
		  				this.getSettings().setUseSysTray(selected);
		  				this.getMainView().checkTrayMenu(selected); // Damit das Tray sofort geladen, bzw. ausgeblendet wird
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
			if (tf.getName().equals("vlcPath")){
			    this.getSettings().setVlcPath(tf.getText());
			    break;
			}
			break;
		}
	}
	/**
	 * Change-Events der Combos und der Checkbox
	 * 
	 * @version 24.11.2004 12:00
	 */
	public void itemStateChanged(ItemEvent event) {
	    JComboBox comboBox = (JComboBox) event.getSource();
	    while (true) {
	        if (event.getStateChange() == 1) {
	            if (comboBox.getName().equals("theme")) {
	                getSettings().setThemeLayout((String) comboBox.getSelectedItem());
	                if (ControlMain.getControl() != null && ControlMain.getControl().getView() != null) {
	                    this.getMainView().getControl().setLookAndFeel();
	                }
	                break;
	            }
	            if (comboBox.getName().equals("locale")) {
	                getSettings().setLocale((String) comboBox.getSelectedItem());
	                break;
	            }
	            if (comboBox.getName().equals("lookAndFeel")) {
	                String lookAndFeel = ((BOLookAndFeelHolder) this.getTab().getJComboBoxLookAndFeel().getSelectedItem()).getLookAndFeelClassName();
	                boolean enable = this.enableThemeComboBox(lookAndFeel);
	                this.getTab().getJComboBoxTheme().setEnabled(enable);
	                getSettings().setLookAndFeel(lookAndFeel);
	                this.getMainView().getControl().setLookAndFeel();
	                break;
	            }
	        }
	        break;
	    }
	}
	
	/*
	 * Theme-Auswahl nur fuer Plastic-L&F�s erlauben
	 */
	private boolean enableThemeComboBox(String lookAndFeel) {
	    return (lookAndFeel.equals(PlasticLookAndFeel.class.getName()) || 
	            lookAndFeel.equals(PlasticXPLookAndFeel.class.getName()) ||
	            lookAndFeel.equals(Plastic3DLookAndFeel.class.getName()) );
	}
	
	private void openVlcPathFileChooser() {
			JFileChooser chooser = new JFileChooser();
			chooser.setApproveButtonText(ControlMain.getProperty("msg_choose"));
			chooser.setApproveButtonToolTipText(ControlMain.getProperty("msg_pathVlc"));
			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			FileFilter filter = new FileFilter(){
				public boolean accept(File f){
					return (f.getName().endsWith("vlc.exe") || f.isDirectory() );
				}
				public String getDescription(){
					return "vlc.exe";
				}
			};
			chooser.setFileFilter(filter);
			int returnVal = chooser.showSaveDialog( null ) ;
		
			if ( returnVal == JFileChooser.APPROVE_OPTION ) {
				String path = chooser.getSelectedFile().toString();
				this.getTab().getJTextFieldVlcPath().setText(path);
				this.getSettings().setVlcPath(path);	
			}
		}

	private void actionStartVlc() {
	    try {
	        String execString=this.getSettings().getVlcPath()+" -I http";
	        Logger.getLogger("ControlSettingsTabMain").info(execString);
	        Process run = Runtime.getRuntime().exec(execString);
	        new SerInputStreamReadThread(true, run.getInputStream()).start();
	        new SerErrorStreamReadThread(run.getErrorStream(), true).start();
	    } catch (IOException e) {
	        Logger.getLogger("ControlSettingsTabMain").error(e.getMessage());
	    }
	}
	
	private void actionAddBox() {
		BOBox box = new BOBox();
		this.getSettingsTab().getSettingsTabMain().getModelBoxTable().addRow(box);
	}
	private void actionRemoveBox() {
		int selectedRow = this.getSettingsTab().getSettingsTabMain().getJTableBoxSettings().getSelectedRow();
		this.getSettingsTab().getSettingsTabMain().getModelBoxTable().removeRow(selectedRow);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see control.ControlTab#getMainView()
	 */
	public GuiTabSettings getSettingsTab() {
		return settingsTab;
	}
	public GuiMainView getMainView() {
		return this.getSettingsTab().getControl().getMainView();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see control.ControlTab#setMainView(presentation.GuiMainView)
	 */
	public void setSettingsTab(GuiTabSettings tabSettings) {
		settingsTab = tabSettings;

	}
	private BOSettingsMain getSettings() {
		return ControlMain.getSettings().getMainSettings();
	}

	private GuiSettingsTabMain getTab() {
		return this.getSettingsTab().getSettingsTabMain();
	}
}