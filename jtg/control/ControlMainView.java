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

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import presentation.GuiMainTabPane;
import presentation.GuiMainView;
import presentation.GuiTerms;
import service.SerLogAppender;


/**
 * @author Alexander Geist
 *
 * Control-Klasse der Main-Gui
 */
public class ControlMainView implements ActionListener, ChangeListener {
	
	GuiMainView view;
	GuiTerms guiTerms;
	private Logger mainLogger;
	private SerLogAppender logAppender;

	private static final String _MESSAGE_BUNDLE = "/locale/messages";
	private Locale locale = new Locale("de","DE");
    private static Properties prop = new Properties();    

	
	
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
		this.startLogger();
		this.getLogAppender().setView(this.getView());
		this.initialize();
		this.getView().getMainTabPane().getTabProgramm().getControl().initialize();
		this.getMainLogger().info("Anwendung gestartet");		
	}
	
	private void initialize() {
		this.logSystemInfo();
		ControlMain.readSettings();
		mainLogger.info("Searching Box-Image");
		ControlMain.detectImage();
		mainLogger.info(ControlMain.getBox().getName()+"-Access loaded");
		this.setResourceBundle(locale);
	}
	private void logSystemInfo() {
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
		mainLogger.info("  "+java.text.DateFormat.getTimeInstance(java.text.DateFormat.FULL).format(new Date()));
		mainLogger.info("\njava.version\t"+System.getProperty("java.version"));
		mainLogger.info("\njava.vendor\t"+System.getProperty("java.vendor"));
		mainLogger.info("\njava.home\t"+System.getProperty("java.home"));
		mainLogger.info("\njava.vm.version\t"+System.getProperty("java.vm.version"));
		mainLogger.info("\njava.vm.vendor\t"+System.getProperty("java.vm.vendor"));
		mainLogger.info("\njava.vm.name\t"+System.getProperty("java.vm.name"));
		mainLogger.info("\njava.class.vers\t"+System.getProperty("java.class.version"));
		mainLogger.info("\njava.class.path\t"+System.getProperty("java.class.path"));
		mainLogger.info("\nos.name\t"+System.getProperty("os.name"));
		mainLogger.info("\nos.arch\t"+System.getProperty("os.arch"));
		mainLogger.info("\nos.version\t"+System.getProperty("os.version"));
		mainLogger.info("\nuser.name\t"+System.getProperty("user.name"));
		mainLogger.info("\nuser.home\t"+System.getProperty("user.home"));
	}

	
	public void startLogger() {
		PatternLayout layout = new PatternLayout();
		
		//http://logging.apache.org/log4j/docs/api/org/apache/log4j/PatternLayout.html
		layout.setConversionPattern("%d{HH:mm:ss} %-5p %c - %m%n");
		
		mainLogger = Logger.getLogger("ControlMain");
		SerLogAppender logApp;
		try {
			setLogAppender(new SerLogAppender(layout));
			getLogAppender().setMaxBackupIndex(3); //Number of max Backup-Files
			getLogAppender().setMaxFileSize("500KB");
			BasicConfigurator.configure(getLogAppender());
		} catch (IOException e) {}
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
	 * @return Returns the logAppender.
	 */
	public SerLogAppender getLogAppender() {
		return logAppender;
	}
	/**
	 * @param logAppender The logAppender to set.
	 */
	public void setLogAppender(SerLogAppender logAppender) {
		this.logAppender = logAppender;
	}
	/**
	 * @return Returns the mainLogger.
	 */
	public Logger getMainLogger() {
		return mainLogger;
	}
	/**
	 * @param mainLogger The mainLogger to set.
	 */
	public void setMainLogger(Logger mainLogger) {
		this.mainLogger = mainLogger;
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
	private void setLocale(String sprache, String land){
        locale = new Locale(sprache,land);    	
    }

    private Locale getLocale(){
        return locale;
    }

    public static String getProperty(String key){
    	return prop.getProperty(key);
    }

    private void setResourceBundle(Locale locale){
        this.locale = locale;    	    	  
        try{    	                       
        	InputStream is=getClass().getResourceAsStream(_MESSAGE_BUNDLE+"_"+locale.getLanguage()+".properties");                    	        	
        	prop.load(is);                    	
        }catch (IOException ex){}       	        
    }    
}
