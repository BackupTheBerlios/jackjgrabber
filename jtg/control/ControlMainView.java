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
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import presentation.GuiLogWindow;
import presentation.GuiMainTabPane;
import presentation.GuiMainView;
import snoozesoft.systray4j.SysTrayMenuEvent;
import snoozesoft.systray4j.SysTrayMenuListener;

import com.jgoodies.plaf.plastic.Plastic3DLookAndFeel;
import com.jgoodies.plaf.plastic.PlasticLookAndFeel;
import com.jgoodies.plaf.plastic.PlasticTheme;
import com.jgoodies.plaf.plastic.PlasticXPLookAndFeel;
import com.jgoodies.plaf.windows.ExtWindowsLookAndFeel;
import com.l2fprod.gui.plaf.skin.Skin;
import com.l2fprod.gui.plaf.skin.SkinLookAndFeel;

/**
 * Control-Klasse des Haupt-Fensters, beinhaltet und verwaltet das MainTabPane
 * Klasse wird beim Start der Anwendung initialisiert und ist immer verfügbar
 */
public class ControlMainView implements ChangeListener, SysTrayMenuListener, ActionListener {
	
	GuiMainView view;
	public static final String[] themes = {"Silver", "BrownSugar", "DarkStar", "DesertBlue",
	        "ExperienceBlue", "SkyBluerTahoma", "SkyRed"};
	public static String[] skinLFThemes;
	public static HashMap skinLFThemesMap;
	
	public void initialize() {
	    this.initSkinLookAndFeel();
	    this.initLookAndFeel();
	    this.setLookAndFeel();
	    this.setView(new GuiMainView(this));		
	    if (ControlMain.getSettings().standardSettings==true) { //First start, go to Settings-Tab
	        this.getView().getMainTabPane().setSelectedIndex(5);
	    }
	    Logger.getLogger("ControlMainView").info(ControlMain.getProperty("msg_app_starting"));
	}
	
	public void actionPerformed(ActionEvent e) {
	    GuiLogWindow.switchLogVisiblity();
	}
	/*
	 * Installiere das jgoodies l&f
	 */
	private void initLookAndFeel() {
		try {
		    PlasticLookAndFeel l2 = new PlasticLookAndFeel();
			UIManager.LookAndFeelInfo info2 = new UIManager.LookAndFeelInfo(l2.getName(), PlasticLookAndFeel.class.getName());
			UIManager.installLookAndFeel(info2);
			
			Plastic3DLookAndFeel l3 = new Plastic3DLookAndFeel();
			UIManager.LookAndFeelInfo info3 = new UIManager.LookAndFeelInfo(l3.getName(), Plastic3DLookAndFeel.class.getName());
			UIManager.installLookAndFeel(info3);
			
			PlasticXPLookAndFeel l = new PlasticXPLookAndFeel();
			UIManager.LookAndFeelInfo info = new UIManager.LookAndFeelInfo(l.getName(), PlasticXPLookAndFeel.class.getName());
			UIManager.installLookAndFeel(info);
			
			ExtWindowsLookAndFeel l4 = new ExtWindowsLookAndFeel();
			UIManager.LookAndFeelInfo info4 = new UIManager.LookAndFeelInfo(l4.getName(), ExtWindowsLookAndFeel.class.getName());
			UIManager.installLookAndFeel(info4);
		} catch (Exception e1) {
		    Logger.getLogger("ControlMainView").error(e1.getMessage());
		}
	}

	public void setLookAndFeel() {
	    String lookAndFeel = ControlMain.getSettings().getMainSettings().getLookAndFeel();
	    String current = UIManager.getLookAndFeel().getClass().getName();
		boolean lfChanged = !current.equals(lookAndFeel);
		
	    if (lookAndFeel.indexOf("SkinLookAndFeel") >-1) {
	        this.setSkinLookAndFeel(lfChanged);
	    } else {
	        try {
	            boolean themeChanged = this.isPlasticThemeChanged();
				if (themeChanged) {
					PlasticTheme inst = (PlasticTheme) (Class.forName("com.jgoodies.plaf.plastic.theme."
							+ ControlMain.getSettings().getMainSettings().getPlasticTheme())).newInstance();
					PlasticLookAndFeel.setMyCurrentTheme(inst);
				}

				if (lfChanged || themeChanged) {
					UIManager.setLookAndFeel(lookAndFeel);
					if (lookAndFeel.indexOf("WindowsLookAndFeel") > -1 || lookAndFeel.indexOf("WindowsClassicLookAndFeel") > -1) {
						UIManager.put("TextArea.font", new Font("Tahoma", Font.PLAIN, 11));
					}
					if (this.getView()!=null) {
					    this.getView().repaintGui();    
					}
				}
		    } catch (Exception e) {
		        Logger.getLogger("ControlMainView").error(e.getMessage());
		    }    
	    }
	}
	
	public void setSkinLookAndFeel(boolean lfChanged) {
	    try {
            Skin aSkin = (Skin) skinLFThemesMap.get(ControlMain.getSettingsMain().getSkinLFTheme());
            Skin old = SkinLookAndFeel.getSkin();
            if (aSkin != null) {
            	if(lfChanged || aSkin!=old) { //Zeichne GUI neu, wenn L&F oder Theme geändert
            	    SkinLookAndFeel.setSkin(aSkin);
            	    UIManager.setLookAndFeel(ControlMain.getSettings().getMainSettings().getLookAndFeel());    
            	
	            	if (this.getView()!=null) {
					    this.getView().repaintGui();    
					}
            	}
            }
        } catch (Exception e) {
            Logger.getLogger("ControlMainView").error(e.getMessage());
        } 
	}
	
	/*
	 * installiere das Skin-L&F
	 */
	private void initSkinLookAndFeel() {
	    try {
	        SkinLookAndFeel.loadThemePack(ClassLoader.getSystemResource("themepack.zip"));
            ArrayList themesDateien = getSkinLFFiles();        
			
			Skin aSkin = null;
			String temp;
			String shortName = "";
			skinLFThemesMap = new HashMap();
			skinLFThemes = new String[themesDateien.size()];

			for (int i=0; i<themesDateien.size(); i++) {
				URL skinUrl = (URL) themesDateien.get(i);
				temp = skinUrl.getFile();
				int index1 = temp.lastIndexOf('/');
				int index2 = temp.lastIndexOf(".zip");
				if (index1 == -1 || index2 == -1) {
					continue;
				}
				shortName = temp.substring(index1 + 1, index2);
				skinLFThemes[i]=shortName;
				aSkin = SkinLookAndFeel.loadThemePack(skinUrl);
				skinLFThemesMap.put(shortName, aSkin);				
			}

			//Skin-L&F installieren, wenn Themes-Dateien vorhanden
            if(skinLFThemesMap.size()>0) {
                SkinLookAndFeel lf = new SkinLookAndFeel();
    			UIManager.LookAndFeelInfo info = new UIManager.LookAndFeelInfo(lf.getName(), SkinLookAndFeel.class.getName());
    			UIManager.installLookAndFeel(info);    
            }
        } catch (Exception e) {
          //Bei Fehlern des Skin L&F auf das Plastic-L&F ausweichen
            if (ControlMain.getSettingsMain().isSkinLookAndFeel()) { 
	            ControlMain.getSettingsMain().setLookAndFeel(PlasticLookAndFeel.class.getName());
	        }
            Logger.getLogger("ControlMainView").error(e.getMessage());
        }
	}
	
	private ArrayList getSkinLFFiles() {
	    ArrayList themesDateien = new ArrayList();
        try {
            File themesPath = new File(ControlMain.getSettingsPath().getWorkDirectory()+File.separator+"themes");
            if (!themesPath.exists()) {
                themesPath.mkdir();
            }
            File[] themeFiles = themesPath.listFiles();
            //checken ob gültige Theme-Datei
            for (int i = 0; i < themeFiles.length; i++) {
            	if (themeFiles[i].isFile() && themeFiles[i].getName().indexOf(".zip") != -1) {
            	    themesDateien.add(themeFiles[i].toURL());
            	}
            }
        } catch (MalformedURLException e) {
            Logger.getLogger("ControlMainView").error(e.getMessage());
        }
        return themesDateien;
	}
	
	private boolean isPlasticThemeChanged() {
	    String currentTheme = PlasticLookAndFeel.getMyCurrentTheme().getClass().getName();
		currentTheme = currentTheme.substring(currentTheme.lastIndexOf(".") + 1);
		return !currentTheme.equals(ControlMain.getSettings().getMainSettings().getPlasticTheme());    
	}
	
	/**
	 * Change-Events of the MainTabPane
	 */
	public void stateChanged(ChangeEvent event) {
		GuiMainTabPane pane = (GuiMainTabPane)event.getSource();
		int count = pane.getSelectedIndex(); //number of selected Tab
				
		while (true) {
			//Change-Events bei betreten neuer Tabs
		    if (count == 0) { //StartTab
				pane.setComponentAt(count, pane.getTabStart());
				new Thread(pane.getTabStart().getControl()).start();
				break;
			}
			if (count == 1) { //ProgrammTab
				pane.setComponentAt(count, pane.getTabProgramm());
				break;
			}
			if (count == 2) { //TimerTab
				pane.setComponentAt(count, pane.getTabTimer());
				new Thread(pane.getTabTimer().getControl()).start();
				break;
			}
			if (count == 3) { //MovieGuideTab
				pane.setComponentAt(count, pane.getTabMovieGuide());
				pane.getTabMovieGuide().getControl().askToDownloadMG();
				break;
			}
			if (count == 4) { //Record Info
				pane.setComponentAt(count, pane.getTabRecordInfo());
			}
			if (count == 5) { //SettingsTab
				pane.setComponentAt(count, pane.getTabSettings());
				break;
			}
			if (count == 6) { //AboutTab
				pane.setComponentAt(count, pane.getTabAbout());
				break;
			}
			break;
		}


		pane.setIndex(count);
	}
	public void iconLeftDoubleClicked( SysTrayMenuEvent e ) {}
	
	public void iconLeftClicked( SysTrayMenuEvent e ){
	    if( this.getView().isVisible() ) {
	        this.getView().setVisible(false);
	    } else {
	    	this.getView().setState(Frame.NORMAL);
	        this.getView().setVisible(true);    
	        this.getView().toFront();
	    }
	}
	
	public void menuItemSelected( SysTrayMenuEvent e ) {
		while (true) {
			if( e.getActionCommand().equals( "exit" ) ) {
				ControlMain.endProgram();
	        	break;
	        }
	        if( e.getActionCommand().equals( "about" ) ) {
	            JOptionPane.showMessageDialog( this.getView(), ControlMain.version[0] );
	            break;
	        }
	        if( e.getActionCommand().equals( "open" ) ) {
	        	this.getView().setState(Frame.NORMAL);
		        this.getView().setVisible(true);    
		        this.getView().toFront();
		        break;
	        }	
	        break;
		}
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
