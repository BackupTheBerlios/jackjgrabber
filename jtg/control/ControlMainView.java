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


/**
 * Control-Klasse des Haupt-Fensters, beinhaltet und verwaltet das MainTabPane
 * Klasse wird beim Start der Anwendung initialisiert und ist immer verfügbar
 */
public class ControlMainView implements ChangeListener, SysTrayMenuListener, ActionListener {
	
	GuiMainView view;
	
	public void initialize() {
	    this.initPlasticLookAndFeel();
	    this.setLookAndFeel();
	    this.setView(new GuiMainView(this));		
	    if (ControlMain.getSettings().standardSettings==true) { //First start, go to Settings-Tab
	        this.getView().getMainTabPane().setSelectedIndex(4);
	    }
	    Logger.getLogger("ControlMainView").info(ControlMain.getProperty("msg_app_starting"));
	}
	
	public void actionPerformed(ActionEvent e) {
	    GuiLogWindow.switchLogVisiblity();
	}
	
	private void initPlasticLookAndFeel() {
		try {
			// Installiere das Plastic Look And Feel
		    PlasticLookAndFeel l2 = new PlasticLookAndFeel();
			UIManager.LookAndFeelInfo info2 = new UIManager.LookAndFeelInfo(l2.getName(), PlasticLookAndFeel.class.getName());
			UIManager.installLookAndFeel(info2);
			
			Plastic3DLookAndFeel l3 = new Plastic3DLookAndFeel();
			UIManager.LookAndFeelInfo info3 = new UIManager.LookAndFeelInfo(l3.getName(), Plastic3DLookAndFeel.class.getName());
			UIManager.installLookAndFeel(info3);
			
			PlasticXPLookAndFeel l = new PlasticXPLookAndFeel();
			UIManager.LookAndFeelInfo info = new UIManager.LookAndFeelInfo(l.getName(), PlasticXPLookAndFeel.class.getName());
			UIManager.installLookAndFeel(info);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void setLookAndFeel() {
		try {
			String lookAndFeel = ControlMain.getSettings().getMainSettings().getLookAndFeel();
			String current = UIManager.getLookAndFeel().getClass().getName();
			boolean lfChanged = !current.equals(lookAndFeel);
			boolean themeChanged = this.isThemeChanged();
			
			if (themeChanged) {
				PlasticTheme inst = (PlasticTheme) (Class.forName("com.jgoodies.plaf.plastic.theme."
						+ ControlMain.getSettings().getMainSettings().getThemeLayout())).newInstance();
				PlasticLookAndFeel.setMyCurrentTheme(inst);
			}

			if (lfChanged || themeChanged) {
				UIManager.setLookAndFeel(lookAndFeel);
				if (lookAndFeel.indexOf("WindowsLookAndFeel") > -1 || lookAndFeel.indexOf("WindowsClassicLookAndFeel") > -1) {
					Font f = (Font) UIManager.get("TextArea.font");
					UIManager.put("TextArea.font", new Font("Tahoma", Font.PLAIN, 11));
				}
				if (this.getView()!=null) {
				    this.getView().repaintGui();    
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean isThemeChanged() {
	    String currentTheme = PlasticLookAndFeel.getMyCurrentTheme().getClass().getName();
		currentTheme = currentTheme.substring(currentTheme.lastIndexOf(".") + 1);
		return !currentTheme.equals(ControlMain.getSettings().getMainSettings().getThemeLayout());
	}
	
	/**
	 * Change-Events of the MainTabPane
	 */
	public void stateChanged(ChangeEvent event) {
		GuiMainTabPane pane = (GuiMainTabPane)event.getSource();
		int count = pane.getSelectedIndex(); //number of selected Tab
				
		while (true) {
			//Change-Events bei betreten neuer Tabs
			if (count == 0) { //ProgrammTab
				pane.setComponentAt(count, pane.getTabProgramm());
				break;
			}
			if (count == 1) { //TimerTab
				pane.setComponentAt(count, pane.getTabTimer());
				new Thread(pane.getTabTimer().getControl()).start();
				break;
			}
			if (count == 2) { //MovieGuideTab
				pane.setComponentAt(count, pane.getTabMovieGuide());
				break;
			}
			if (count == 3) { //Record Info
				pane.setComponentAt(count, pane.getTabRecordInfo());
			}
			if (count == 4) { //SettingsTab
				pane.setComponentAt(count, pane.getTabSettings());
				break;
			}
			if (count == 5) { //AboutTab
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
