package control;
/*
ControlMainView by Alexander Geist

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;


import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.BOBox;

import org.apache.log4j.Logger;
import presentation.GuiMainTabPane;
import presentation.GuiMainView;
import presentation.GuiTerms;
import service.SerAlertDialog;


/**
 * Control-Klasse der Main-Gui
 */
public class ControlMainView implements ActionListener, ChangeListener {
	
	GuiMainView view;
	GuiTerms guiTerms;
	
	public ControlMainView() {
		//this.showTerms();
		this.runAfterTerms();
				
	}
	private void showTerms() {
		guiTerms = new GuiTerms(this);
		guiTerms.setVisible(true);
	}
	
	private void runAfterTerms() {;
		this.setView(new GuiMainView(this));
		ControlMain.getLogAppender().setView(this.getView());
		this.initialize();
		
		this.startProgramControl();		
		this.log("Anwendung gestartet");		
	}
	
	private void startProgramControl() {
		//erster Tab wird automatisch gestartet, darum muss die Initialisierung des Controls
		//manuell erfolgen
		this.getView().getMainTabPane().getTabProgramm().getControl().initialize();
		BOBox box = ControlMain.getSelectedBox();
		this.getView().getMainTabPane().getTabProgramm().getControl().setSelectedBox(box);
		
		try {
			int index = ControlMain.getIndexOfSelecteddBox();
			this.getView().getTabProgramm().getJComboBoxBoxIP().setSelectedIndex(index);
		} catch (IllegalArgumentException e) {
			SerAlertDialog.alert("No Box-IP in the Settings found", this.getView());
		}
	}
	
	private void initialize() {
		this.logSystemInfo();

		ControlMain.detectImage();
  	    //ControlMain.loadHashTable();
        ControlMain.setResourceBundle(ControlMain.getLocale());
	}
	private void logSystemInfo() {
		this.log(ControlMain.version[0]+"/"+ControlMain.version[1]+" "
				+ControlMain.version[2]+" "+ControlMain.version[3]);
		this.log("java.version\t"+System.getProperty("java.version"));
		this.log("java.vendor\t"+System.getProperty("java.vendor"));
		this.log("java.home\t"+System.getProperty("java.home"));
		this.log("java.vm.version\t"+System.getProperty("java.vm.version"));
		this.log("java.vm.vendor\t"+System.getProperty("java.vm.vendor"));
		this.log("java.vm.name\t"+System.getProperty("java.vm.name"));
		this.log("java.class.vers\t"+System.getProperty("java.class.version"));
		this.log("java.class.path\t"+System.getProperty("java.class.path"));
	}
		
	public void logSysteminfo2() {
		this.log("  "+java.text.DateFormat.getTimeInstance(java.text.DateFormat.FULL).format(new Date()));
		this.log("\njava.version\t"+System.getProperty("java.version"));
		this.log("\njava.vendor\t"+System.getProperty("java.vendor"));
		this.log("\njava.home\t"+System.getProperty("java.home"));
		this.log("\njava.vm.version\t"+System.getProperty("java.vm.version"));
		this.log("\njava.vm.vendor\t"+System.getProperty("java.vm.vendor"));
		this.log("\njava.vm.name\t"+System.getProperty("java.vm.name"));
		this.log("\njava.class.vers\t"+System.getProperty("java.class.version"));
		this.log("\njava.class.path\t"+System.getProperty("java.class.path"));
		this.log("\nos.name\t"+System.getProperty("os.name"));
		this.log("\nos.arch\t"+System.getProperty("os.arch"));
		this.log("\nos.version\t"+System.getProperty("os.version"));
		this.log("\nuser.name\t"+System.getProperty("user.name"));
		this.log("\nuser.home\t"+System.getProperty("user.home"));
	}
	
	private void log(String logtext) {
		Logger.getLogger("ControlMainView").info(logtext);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String actName = e.getActionCommand();

		if (actName.equals("I agree"))  {
			guiTerms.close();
			this.runAfterTerms();
		} else if (actName.equals("I disagree (closing)")) {
			System.exit(0);
		}
	}
	
	/**
	 * Change-Events des TabPane
	 */
	public void stateChanged(ChangeEvent event) {
		GuiMainTabPane pane = (GuiMainTabPane)event.getSource();
		int count = pane.getSelectedIndex(); //number of selected Tab
		JPanel comp = (JPanel)pane.getComponent(count);
				
		//Change-Events bei betreten neuer Tabs
		if (count == 0) { //ProgrammTab
			try {
				comp.getComponent(0);
			} catch (ArrayIndexOutOfBoundsException e) {
				comp.add(pane.getTabProgramm());
			}
		}
		if (count == 1) { //TimerTab
			try {
				comp.getComponent(0);
			} catch (ArrayIndexOutOfBoundsException e) {
				comp.add(pane.getTabTimer());
			}
			pane.getTabTimer().getControl().initialize();
		}
		if (count == 2) { //ProjectXTab
			ControlMain.getSettings().setProjectXSettingsChanged(true);
			try {
				comp.getComponent(0);
			} catch (ArrayIndexOutOfBoundsException e) {
				comp.add(pane.getTabProjectX());
			}
		}
		if (count == 3) { //SettingsTab
			try {
				comp.getComponent(0);
			} catch (ArrayIndexOutOfBoundsException e) {
				comp.add(pane.getTabSettings());
			}
		}
		pane.setIndex(count);
	}
		
	/**
	 * @return Returns the guiTerms.
	 */
	public GuiTerms getGuiTerms() {
		return guiTerms;
	}
	/**
	 * @param guiTerms The guiTerms to set.
	 */
	public void setGuiTerms(GuiTerms guiTerms) {
		this.guiTerms = guiTerms;
	}
	/**
	 * @return Returns the view.
	 */
	public GuiMainView getView() {
		return view;
	}
	/**
	 * @param view The view to set.
	 */
	public void setView(GuiMainView view) {
		this.view = view;
	}	
}
