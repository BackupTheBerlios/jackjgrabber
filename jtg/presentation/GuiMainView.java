package presentation;
/*
GuiMainView.java by Geist Alexander 

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
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import projectX.common.X;
import service.SerGUIUtils;
import service.SerSettingsHandler;
import snoozesoft.systray4j.SysTrayMenu;
import snoozesoft.systray4j.SysTrayMenuIcon;
import snoozesoft.systray4j.SysTrayMenuItem;

import com.jgoodies.plaf.plastic.Plastic3DLookAndFeel;
import com.jgoodies.plaf.plastic.PlasticLookAndFeel;
import com.jgoodies.plaf.plastic.PlasticTheme;
import com.sun.org.apache.xerces.internal.impl.*;

import control.ControlMain;
import control.ControlMainView;
/*
 * Haupt-Gui, hier werden die einzelnen Tabs verwaltet
 */
public class GuiMainView extends JFrame {

	
	private GuiMainTabPane mainTabPane = null;
	private ControlMainView control;
	private SysTrayMenuIcon[] sysTrayIcon = { 
	        new SysTrayMenuIcon( "ico/grabber1" ),
	        new SysTrayMenuIcon( "ico/grabber2" )
	};
	private SysTrayMenu menu;

	
	public GuiMainView(ControlMainView ctrl) {
		super("FormLayout");
		addWindowListener (new WindowAdapter() { 
			public void windowClosing(WindowEvent e) { 
				try {
					if (ControlMain.getSettings().isSettingsChanged()) {
						SerSettingsHandler.saveAllSettings();
						Logger.getLogger("ControlMainView").info("Settings saved");
					}
					if (ControlMain.getSettings().isProjectXSettingsChanged()) {
						X.inisave();
						Logger.getLogger("ControlMainView").info("ProjectX-Settings saved");
					}
				} catch (IOException e1) {
					Logger.getLogger("ControlMainView").error("Error while save Settings");
				}
				System.exit(0); 
			}
			public void windowIconified(WindowEvent e) {
			    if (ControlMain.getSettings().useSysTray) {
			        ControlMain.getControl().getView().setVisible(false);
			    }
			}
		});
		
		initPlasticLookAndFeel();
		
		setLookAndFeel();
		control = ctrl;
		initialize();
		setTitle(ControlMain.version[0]+"/"+ControlMain.version[1]+
				" "+ControlMain.version[2]+", "+ControlMain.version[3]);
		pack();
		SerGUIUtils.center(this);
		if (ControlMain.getSettings().isStartFullscreen()) {
		    GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			device.setFullScreenWindow(this);
		}
		setVisible(true);	
	}
	
	/**
	 * 
	 */
	private void initPlasticLookAndFeel() {
		try {
			// Installiere das Plastic Look And Feel
			PlasticTheme inst = (PlasticTheme) (Class
					.forName("com.jgoodies.plaf.plastic.theme."
							+ ControlMain.getSettings().getThemeLayout()))
					.newInstance();
			PlasticLookAndFeel.setMyCurrentTheme(inst);
			PlasticLookAndFeel l = new PlasticLookAndFeel();
			UIManager.LookAndFeelInfo info = new UIManager.LookAndFeelInfo(l
					.getName(), PlasticLookAndFeel.class.getName());
			UIManager.installLookAndFeel(info);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void setLookAndFeel() {
		try {
			String lookAndFeel = ControlMain.getSettings().getLookAndFeel();
			PlasticTheme inst = (PlasticTheme) (Class
					.forName("com.jgoodies.plaf.plastic.theme."
							+ ControlMain.getSettings().getThemeLayout()))
					.newInstance();
			PlasticLookAndFeel.setMyCurrentTheme(inst);
			UIManager.setLookAndFeel(lookAndFeel);
			dispose();
			SwingUtilities.updateComponentTreeUI(this);
			setVisible(true);
			validate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method initializes this
	 */
	private void initialize() {
		this.getContentPane().add(this.getMainTabPane());
		setIconImage( new ImageIcon(ClassLoader.getSystemResource("ico/grabber1.gif")).getImage());
		if (ControlMain.getSettings().isUseSysTray()) {
		    sysTrayIcon[0].addSysTrayMenuListener( control );
		    sysTrayIcon[1].addSysTrayMenuListener( control );
			createMenu();   
		}
	}
	/**
	 * Haupt-TabPane. Neue Tabs werden hier angemeldet.
	 */    
	public GuiMainTabPane getMainTabPane() {
		if (mainTabPane == null) {
			mainTabPane = new GuiMainTabPane(this);
			mainTabPane.addChangeListener(control);
			
			mainTabPane.addTab(ControlMain.getProperty("tab_program"), mainTabPane.getTabProgramm());
			mainTabPane.addTab(ControlMain.getProperty("tab_timerlist"), new JPanel());
			mainTabPane.addTab("MovieGuide", new JPanel());
			mainTabPane.addTab(ControlMain.getProperty("tab_projectx"), new JPanel());
			mainTabPane.addTab(ControlMain.getProperty("tab_settings"), new JPanel());
			mainTabPane.addTab(ControlMain.getProperty("tab_about"), new JPanel());
		}
		return mainTabPane;
	}
	
	void createMenu()
    {
//	  create an exit item
        SysTrayMenuItem itemOpen = new SysTrayMenuItem( ControlMain.getProperty("open"), "open" );
        itemOpen.addSysTrayMenuListener( control );
	    
        // create an exit item
        SysTrayMenuItem itemExit = new SysTrayMenuItem( ControlMain.getProperty("exit"), "exit" );
        itemExit.addSysTrayMenuListener( control );

        // create an about item
        SysTrayMenuItem itemAbout = new SysTrayMenuItem( ControlMain.getProperty("about"), "about" );
        itemAbout.addSysTrayMenuListener( control );

        // create the main menu
        menu = new SysTrayMenu( sysTrayIcon[0], ControlMain.version[0] );

        // insert items
        menu.addItem( itemExit );
        menu.addSeparator();
        menu.addItem( itemAbout );
        menu.addSeparator();
        menu.addItem( itemOpen );
    }
	
	/**
	 * Setzt das Tray Men� in Abh�ngigkeit von useTray Wird vom Einstellungstab aufgerufen wenn die CheckBox ihren Status �ndert.
	 * 
	 * @param useTray
	 * @author Reinhard Achleitner (crazyreini)
	 * @version 24.11.2004 - 12:08
	 */
	public void checkTrayMenu(boolean useTray) {
		if (useTray) {
			if (menu == null) {

				// Falls es noch kein Men� gibt, erzeuge es
				createMenu();
			}
			else {
				// Das Men� existiert schon, zeige das Icon
				menu.showIcon();
			}
		}
		else {

			if (menu != null) {
				// Verstecke Icon
				menu.hideIcon();
			}
		}
	}
	    
	public GuiTabProgramm getTabProgramm() {
		return this.getMainTabPane().getTabProgramm();
	}
	    
	public JPanel getTabTimer() {
		return this.getMainTabPane().getTabTimer();
	}
	    
	public GuiTabSettings getTabSettings() {
		return this.getMainTabPane().getTabSettings();
	}
	    
	public JPanel getTabProjectX() {
		return this.getMainTabPane().getTabProjectX();
	}
	public GuiTabAbout getTabAbout() {
		return this.getMainTabPane().getTabAbout();
	}
	public GuiTabMovieGuide getTabMovieGuide() {
		return this.getMainTabPane().getTabMovieGuide();
	}
	public void setSystrayRecordIcon() {
		if (menu != null) {
			menu.setIcon( sysTrayIcon[1] );	
		}
	}
	public void setSystrayDefaultIcon() {
		if (menu != null) {
			menu.setIcon( sysTrayIcon[0] );
		}
	}
	/**
	 * @return Returns the control.
	 */
	public ControlMainView getControl() {
		return control;
	}
	/**
	 * @param control The control to set.
	 */
	public void setControl(ControlMainView control) {
		this.control = control;
	}
}
