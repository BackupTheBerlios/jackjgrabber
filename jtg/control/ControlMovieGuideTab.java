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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JTable;

import model.BOMovieGuide;

import presentation.GuiMainView;


public class ControlMovieGuideTab extends ControlTab implements ActionListener, MouseListener {
	
	GuiMainView mainView;
	
	public ControlMovieGuideTab(GuiMainView view ) {
		this.setMainView(view);
	}

	public void initialize() {
		
	}
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action == "pmgDownload") {
			//this.actionRemoveBox();
		}
		if (action == "neuEinlesen") {
			//this.actionAddBox();
		}
		if (action == "select2Timer") {
			//this.openFileChooser();
		}
	}	
	public void mousePressed(MouseEvent me) {
		JTable table = (JTable)me.getSource();
		String tableName = table.getName();
		int selectedRow = table.getSelectedRow();
		if (tableName == "senderTable") {
			//BOTimer timer = (BOTimer)this.getTimerList()[0].get(selectedRow);
			//this.selectRepeatDaysForRecordTimer(timer);
		}
		if (tableName == "filmTable") {
			//BOTimer timer = (BOTimer)this.getTimerList()[1].get(selectedRow);
			//this.selectRepeatDaysForSystemTimer(timer);
		}
		if (tableName == "timerTable") {
			//BOTimer timer = (BOTimer)this.getTimerList()[1].get(selectedRow);
			//this.selectRepeatDaysForSystemTimer(timer);
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
