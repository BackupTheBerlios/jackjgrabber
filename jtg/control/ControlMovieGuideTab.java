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
import java.util.ArrayList;
import java.net.MalformedURLException;

import javax.swing.JFileChooser;
import javax.swing.JTable;

import model.BOMovieGuide;
import org.dom4j.Document;
import org.dom4j.DocumentException;

import org.apache.log4j.Logger;
import service.SerXMLHandling;

import presentation.GuiMainView;


public class ControlMovieGuideTab extends ControlTab implements ActionListener, MouseListener {
	
	GuiMainView mainView;
	ArrayList filmeList = new ArrayList();
	static Document movieGuideDocument;
	public static String movieGuideFileName ="movieguide.xml";
	
	public ControlMovieGuideTab(GuiMainView view ) {
		this.setMainView(view);
	}

	public void initialize() {
		
	}
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action == "download") {
			//this.actionRemoveBox();
		}
		if (action == "neuEinlesen") {
			//this.actionAddBox();
		}
		if (action == "select2Timer") {
			//this.openFileChooser();
		}
		if (action == "suchen") {
			//this.openFileChooser();
		}
		if (action == "allDates") {
			//this.openFileChooser();
		}		
		if (action == "movieGuidePath") {
			this.openFileChooser();
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
	public ArrayList getFilmeList() {
		return filmeList;
	}
	/**
	 * @param bouquetList The bouquetList to set.
	 */
	public void setFilmeList(ArrayList filmeList) {
		this.filmeList = filmeList;
	}
	
	
	public GuiMainView getMainView() {
		return mainView;
	}
	/**
	 * @param mainView The mainView to set.
	 */
	public void setMainView(GuiMainView mainView) {
		this.mainView = mainView;
	}
	private void openFileChooser() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setDialogType(JFileChooser.OPEN_DIALOG);

		fc.setApproveButtonText( "Auswählen");
		fc.setApproveButtonToolTipText( "Datei auswählen");
		int returnVal = fc.showOpenDialog( null ) ;

		if ( returnVal == JFileChooser.APPROVE_OPTION )
			{
				//String path = fc.getSelectedFile().toString();
				//this.getMainView().getTabSettings().getJTextFieldRecordSavePath().setText(path);
				//ControlMain.getSettings().setSavePath(path);
			}
	}
	public static void checkMovieGuide() {	
		try {
			File pathToXMLFile = new File(movieGuideFileName).getAbsoluteFile();
			if (pathToXMLFile.exists()) {
				movieGuideDocument = SerXMLHandling.readDocument(pathToXMLFile);				
				Logger.getLogger("ControlMain").info("MovieGuide found");
			} else {								
				Logger.getLogger("ControlMain").info("MovieGuide not found");
			}			
		} catch (MalformedURLException e) {
			Logger.getLogger("ControlMain").error("Fehler beim lesen der MovieGuide!");
		} catch (DocumentException e) {
			Logger.getLogger("ControlMain").error("Fehler beim lesen der MovieGuide!");
		} catch (IOException e) {
			Logger.getLogger("ControlMain").error("Fehler beim lesen der MovieGuide!");
		}
}
	/**
	 * @return Returns the movieGuideDocument.
	 */
	public static Document getMovieGuideDocument() {
		return movieGuideDocument;
	}
	/**
	 * @param movieGuideDocument The movieGuideDocument to set.
	 */
	public static void setMovieGuideDocument(Document movieGuideDocument) {
		ControlMain.movieGuideDocument = movieGuideDocument;
	}
}
