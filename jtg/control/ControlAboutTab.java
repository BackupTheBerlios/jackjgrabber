package control;
/*
ControlAboutTab.java by Geist Alexander 

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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JLabel;

import presentation.GuiMainView;
import service.SerAlertDialog;
import service.BrowserLauncher;


public class ControlAboutTab extends ControlTab implements MouseListener {
	
	GuiMainView mainView;
	
	public ControlAboutTab(GuiMainView view ) {
		this.setMainView(view);
	}

	public void initialize() {
		this.showVersion();
		this.showAuthors();
	}
	
	private void showVersion() {
		this.getMainView().getTabAbout().getTaVersion().append(ControlMain.version[0]+"\n");
		this.getMainView().getTabAbout().getTaVersion().append(ControlMain.version[1]+"\n");
	}
	private void showOther() {
		
	}
	private void showAuthors() {
		try {
			BufferedReader input = new BufferedReader(new FileReader( new File("Authors")));
			String line;
			while ( (line = input.readLine()) != null) {
				this.getMainView().getTabAbout().getTaAuthors().append(line+"\n");
			}
		} catch (IOException e) {}
	}
	private void showLicense() {
		try {
			BufferedReader input = new BufferedReader(new FileReader( new File("COPYING")));
			String line;
			while ( (line = input.readLine()) != null) {
				this.getMainView().getTabAbout().getTaLicense().append(line+"\n");
			}
		} catch (IOException e) {}
	}
	
	public void mousePressed(MouseEvent me) {
		JLabel label = (JLabel)me.getSource();
		try {
			BrowserLauncher.openURL(label.getName());
		} catch (IOException e) {
			SerAlertDialog.alert(ControlMain.getProperty("msg_browserError"), this.getMainView());
		}
	}
	
	public void mouseClicked(MouseEvent me) 
	{}
	public void mouseReleased(MouseEvent me)
	{}
	public void mouseExited(MouseEvent me)
	{}
	public void mouseEntered(MouseEvent me)
	{}
	
	/**
	 * @return Returns the mainView.
	 */
	public GuiMainView getMainView() {
		return mainView;
	}
	/**
	 * @param mainView The mainView to set.
	 */
	public void setMainView(GuiMainView mainView) {
		this.mainView = mainView;
	}
}
