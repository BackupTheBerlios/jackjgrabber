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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import presentation.GuiMainView;


public class ControlAboutTab extends ControlTab {
	
	GuiMainView mainView;
	
	public ControlAboutTab(GuiMainView view ) {
		this.setMainView(view);
	}

	public void initialize() {
		this.showVersion();
		this.showAuthors();
	}
	
	private void showVersion() {
		for (int i=0; i<ControlMain.version.length; i++) {
			this.getMainView().getTabAbout().getTaVersion().append(ControlMain.version[i]+"\n");
		}
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
