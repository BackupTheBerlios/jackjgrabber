package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;
import presentation.GuiMainTabPane;
import presentation.GuiMainView;
import presentation.GuiTerms;

/**
 * @author Alexander Geist
 *
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
	
	private void runAfterTerms() {
		this.setView(new GuiMainView(this));
		ControlMain.startLogger();
		ControlMain.getLogAppender().setView(this.getView());
		this.initialize();
		this.getView().getMainTabPane().getTabProgramm().getControl().initialize();
		this.log("Anwendung gestartet");		
	}
	
	private void initialize() {
		this.logSystemInfo();
		ControlMain.readSettings();
		ControlMain.detectImage();
		ControlMain.setResourceBundle(ControlMain.locale);
	}
	private void logSystemInfo() {
		Logger mainLogger = Logger.getLogger("ControlMainView");
		mainLogger.info(ControlMain.version[0]+"/"+ControlMain.version[1]+" "
				+ControlMain.version[2]+" "+ControlMain.version[3]);
		mainLogger.info("java.version\t"+System.getProperty("java.version"));
		mainLogger.info("java.vendor\t"+System.getProperty("java.vendor"));
		mainLogger.info("java.home\t"+System.getProperty("java.home"));
		mainLogger.info("java.vm.version\t"+System.getProperty("java.vm.version"));
		mainLogger.info("java.vm.vendor\t"+System.getProperty("java.vm.vendor"));
		mainLogger.info("java.vm.name\t"+System.getProperty("java.vm.name"));
		mainLogger.info("java.class.vers\t"+System.getProperty("java.class.version"));
		mainLogger.info("java.class.path\t"+System.getProperty("java.class.path"));
	}
	
	public void javaEV() {
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
		
		//Change-Event beim Verlassen des Setting-Tabs
		if (pane.getIndex()==3 && ControlMain.getSettings().isBoxIpChanged()) {
			ControlMain.detectImage();
			pane.getTabProgramm().getControl().reInitialize();
			ControlMain.getSettings().setBoxIpChanged(false);
		}
		
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
		}
		if (count == 2) { //ProjectXTab
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
