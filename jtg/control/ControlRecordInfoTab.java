package control;
/*

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

/**
 * Kontrollklasse für die Aufnahme Infos
 * @author Reinhard Achleitner
 */
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.event.*;

import org.apache.log4j.*;

import presentation.*;

/**
 * Controlklasse des Programmtabs.
 */
public class ControlRecordInfoTab extends ControlTab
		implements
			ActionListener,
			MouseListener,
			ItemListener,
			ChangeListener {

	GuiMainView mainView;

	private GuiTabRecordInfo guiTabRecordInfo;

	private File directory;

	private static final String LOGFILENAME = "log.txt";

	public ControlRecordInfoTab(GuiMainView view) {
		this.setMainView(view);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see control.ControlTab#getMainView()
	 */
	public GuiMainView getMainView() {
		return mainView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see control.ControlTab#setMainView(presentation.GuiMainView)
	 */
	public void setMainView(GuiMainView view) {
		mainView = view;

	}

	public void actionPerformed(ActionEvent e) {
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void itemStateChanged(ItemEvent e) {

	}

	public void stateChanged(ChangeEvent e) {

	}

	public void setRecordView(GuiTabRecordInfo tabRecordInfo) {
		guiTabRecordInfo = tabRecordInfo;

	}

	/** Setzt die Aufnahmeinfos
	 * 
	 * @param title
	 * @param engine
	 * @param directory
	 * @param timer
	 */
	public void startRecord(String title, String engine, File directory,
			boolean timer) {
		
		this.directory = directory;
		ControlMain.getLogAppender().addTextArea(guiTabRecordInfo.getLogArea());
		guiTabRecordInfo.startRecord(title, engine, directory, timer);
	}

	/** stoppt die Erstellung der Aufnahmeinfos
	 * 
	 *
	 */
	public void stopRecord() {
		ControlMain.getLogAppender().removeTextArea(
				guiTabRecordInfo.getLogArea());
		guiTabRecordInfo.stopRecord();
		
		saveLog();
	}

	/** speichert das Log der Aufnahme
	 * 
	 */
	private void saveLog() {
		PrintStream print = null;
		try {

			String file = directory.getAbsolutePath() + File.separatorChar
					+ LOGFILENAME;

			File f = new File(file);
			
			print = new PrintStream(file);
			
			StringBuffer log = new StringBuffer();
			log.append(guiTabRecordInfo.getTitle() + "\n");
			log.append(guiTabRecordInfo.getVideo() + "\n");
			log.append(guiTabRecordInfo.getAudio() + "\n");
			log.append(guiTabRecordInfo.getLog());
			
			
			StringTokenizer tok = new StringTokenizer(log.toString(), "\n");
			while (tok.hasMoreTokens()) {
				print.println(tok.nextToken());
			}

		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger("RecordControl").error(e);
		} finally {
			if (print != null) {
				print.close();
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see control.ControlTab#initialize()
	 */
	public void initialize() {
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// TODO Auto-generated method stub
		
	}

}