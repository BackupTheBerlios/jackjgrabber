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

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import model.BOBox;
import model.BOLookAndFeelHolder;
import model.BOSettings;
import presentation.GuiMainView;
import presentation.settings.GuiSettingsTabMain;
import presentation.settings.GuiTabSettings;

import com.jgoodies.plaf.plastic.Plastic3DLookAndFeel;
import com.jgoodies.plaf.plastic.PlasticLookAndFeel;
import com.jgoodies.plaf.plastic.PlasticXPLookAndFeel;

public class ControlSettingsTabMain extends ControlTabSettings implements ActionListener, ItemListener {

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
		this.getTab().getCbUseSysTray().setSelected(this.getSettings().isUseSysTray());
		this.initLookAndFeels();
		this.getTab().getJComboBoxLookAndFeel().setSelectedIndex(currentSelectedLookAndFeel);
	}
	
	public BOLookAndFeelHolder[] initLookAndFeels() {
	    if (lookAndFeels==null) {
			LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
			lookAndFeels = new BOLookAndFeelHolder[looks.length];
	
			String currentSelLFClassName = ControlMain.getSettings().getLookAndFeel();
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
		if (action == "delete") {
			this.actionRemoveBox();
		}
		if (action == "add") {
			this.actionAddBox();
		}
	}

	/**
	 * Change-Events der Combos und der Checkbox
	 * 
	 * @version 24.11.2004 12:00
	 */
	public void itemStateChanged(ItemEvent event) {
		String comp = event.getSource().getClass().getName();
		while (true) {
			if (comp.equals("javax.swing.JCheckBox")) {
				JCheckBox checkBox = (JCheckBox) event.getSource();
				if (checkBox.getName().equals("showLogo")) {
					this.getSettings().setShowLogo(checkBox.isSelected());
					break;
				}
				if (checkBox.getName().equals("startFullscreen")) {
					this.getSettings().setStartFullscreen(checkBox.isSelected());
					break;
				}
				if (checkBox.getName().equals("useSysTray")) {
					boolean useTray = checkBox.isSelected();
					this.getSettings().setUseSysTray(useTray);

					this.getMainView().checkTrayMenu(useTray); // Damit das Tray sofort geladen, bzw. ausgeblendet wird

					break;
				}
				break;
			}
			else {
				JComboBox comboBox = (JComboBox) event.getSource();
				if (event.getStateChange() == 1) {
					if (comboBox.getName().equals("theme")) {
						getSettings().setThemeLayout((String) comboBox.getSelectedItem());
						if (ControlMain.getControl() != null && ControlMain.getControl().getView() != null)
						{
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
	}
	
	/*
	 * Theme-Auswahl nur fuer Plastic-L&F´s erlauben
	 */
	private boolean enableThemeComboBox(String lookAndFeel) {
	    return (lookAndFeel.equals(PlasticLookAndFeel.class.getName()) || 
	            lookAndFeel.equals(PlasticXPLookAndFeel.class.getName()) ||
	            lookAndFeel.equals(Plastic3DLookAndFeel.class.getName()) );
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
	private BOSettings getSettings() {
		return ControlMain.getSettings();
	}

	private GuiSettingsTabMain getTab() {
		return this.getSettingsTab().getSettingsTabMain();
	}
}