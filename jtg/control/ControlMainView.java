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

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import presentation.GuiMainTabPane;
import presentation.GuiMainView;
import presentation.GuiTerms;
import service.SerAlertDialog;
import snoozesoft.systray4j.SysTrayMenuEvent;
import snoozesoft.systray4j.SysTrayMenuListener;


/**
 * Control-Klasse des Haupt-Fensters, beinhaltet und verwaltet das MainTabPane
 * Klasse wird beim Start der Anwendung initialisiert und ist immer verfügbar
 */
public class ControlMainView implements ActionListener, ChangeListener, SysTrayMenuListener {
	
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
		this.log(ControlMain.getProperty("msg_app_starting"));		
	}
	/*
	 * erster Tab wird automatisch gestartet, darum muss die Initialisierung des Controls
	 * manuell erfolgen
	 */
	private void startProgramControl() {
		this.getView().getMainTabPane().getTabProgramm().getControl().initialize();	
		
		int index = ControlMain.getIndexOfActiveBox();
		if (index ==-1) {
			SerAlertDialog.alert(ControlMain.getProperty("msg_ipError"), this.getView());
		} 
		this.getView().getTabProgramm().getJComboBoxBoxIP().setSelectedIndex(index);
	}
	
	private void initialize() {
		this.logSystemInfo();
		ControlMain.detectImage();
	}
	
	private void logSystemInfo() {
		for (int i=0; i<ControlMain.version.length; i++) {
			this.log(ControlMain.version[i]);
		}
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
	 * Change-Events of the MainTabPane
	 */
	public void stateChanged(ChangeEvent event) {
		GuiMainTabPane pane = (GuiMainTabPane)event.getSource();
		int count = pane.getSelectedIndex(); //number of selected Tab
				
		//Change-Events bei betreten neuer Tabs
		if (count == 0) { //ProgrammTab
			pane.setComponentAt(count, pane.getTabProgramm());
		}
		if (count == 1) { //TimerTab
			pane.setComponentAt(count, pane.getTabTimer());
			pane.getTabTimer().getControl().initialize();
		}
		if (count == 2) { //ProjectXTab
			ControlMain.getSettings().setProjectXSettingsChanged(true);
			pane.setComponentAt(count, pane.getTabProjectX());
		}
		if (count == 3) { //SettingsTab
			pane.setComponentAt(count, pane.getTabSettings());
		}
		if (count == 4) { //AboutTab
			pane.setComponentAt(count, pane.getTabAbout());
		}
		//if (count == 5) { //MovieGuideTab
		//	pane.setComponentAt(count, pane.getTabMovieGuide());
		//}
		pane.setIndex(count);
	}
	public void iconLeftDoubleClicked( SysTrayMenuEvent e ) {}
	
	public void iconLeftClicked( SysTrayMenuEvent e ){
	    if( this.getView().isVisible() ) {
	        this.getView().setVisible(false);
	    } else {
	        this.getView().setVisible(true);    
	    }
	}
	
	public void menuItemSelected( SysTrayMenuEvent e ) {
        if( e.getActionCommand().equals( "exit" ) ) System.exit( 0 );
        else if( e.getActionCommand().equals( "about" ) ) {
            JOptionPane.showMessageDialog( this.getView(), ControlMain.version[0] );
        }
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
	 * @return Returns the tabSettings.
	 */
	public GuiMainView getView() {
		return view;
	}
	/**
	 * @param tabSettings The tabSettings to set.
	 */
	public void setView(GuiMainView view) {
		this.view = view;
	}	
}
