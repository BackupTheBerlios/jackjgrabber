package control;

/*
 * ControlAboutTab.java by Geist Alexander
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 675 Mass
 * Ave, Cambridge, MA 02139, USA.
 *  
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//import java.io.BufferedReader;
import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.JTable;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

//import model.BOMovieGuide;

import presentation.GuiMainView;
import service.SerXMLHandling;


public class ControlMovieGuideTab extends ControlTab implements ActionListener,
		MouseListener {

	GuiMainView mainView;
	static Hashtable objectList = new Hashtable();	
	static Hashtable titelList = new Hashtable();

	static ArrayList dateArray = new ArrayList();
    static ArrayList genreArray = new ArrayList();
	
    private static Element root;
	private static Document movieGuideDocument;

	public static String movieGuideFileName = "movieguide.xml";
	

	public ControlMovieGuideTab(GuiMainView view) {
		this.setMainView(view);		
		try{
			setRootElement();
			setTitelMap();
		}catch (Exception ex){}
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
		JTable table = (JTable) me.getSource();
		String tableName = table.getName();
		int selectedRow = table.getSelectedRow();		
		if (tableName == "filmTable") {						
			StringTokenizer st = new StringTokenizer(objectList.get(new Integer(selectedRow)).toString(),"|");															
			this.getMainView().getTabMovieGuide().getTaEpisode().setText("Episode: "+st.nextToken());  			
			this.getMainView().getTabMovieGuide().getTfGenre().setText("Genre: "+st.nextToken());		
			this.getMainView().getTabMovieGuide().getTaLand().setText("Produktion: "+st.nextToken()+" / "+st.nextToken()+" / Regie: "+st.nextToken());
			this.getMainView().getTabMovieGuide().getTaAudioVideo().setText("Audio: "+st.nextToken()+" / Video: "+st.nextToken());											
			this.getMainView().getTabMovieGuide().getTaDarsteller().setText("Darsteller: "+st.nextToken());
			this.getMainView().getTabMovieGuide().getTaBeschreibung().setText("Inhalt: "+st.nextToken());											
		}
		if (tableName == "timerTable") {		
		}
	}

	public void mouseClicked(MouseEvent me) {
	}

	public void mouseReleased(MouseEvent me) {
	}

	public void mouseExited(MouseEvent me) {
	}

	public void mouseEntered(MouseEvent me) {
	}

	public GuiMainView getMainView() {
		return mainView;
	}

	/**
	 * @param mainView
	 *            The mainView to set.
	 */
	public void setMainView(GuiMainView mainView) {
		this.mainView = mainView;
	}

	private void openFileChooser() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setDialogType(JFileChooser.OPEN_DIALOG);

		fc.setApproveButtonText("Auswählen");
		fc.setApproveButtonToolTipText("Datei auswählen");
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
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
				Logger.getLogger("ControlMovieGuide").info("MovieGuide found");
			} else {
				Logger.getLogger("ControlMovieGuide").info(
						"MovieGuide not found");
			}
		} catch (MalformedURLException e) {
			Logger.getLogger("ControlMovieGuide").error(
					"Fehler beim lesen der MovieGuide!");
		} catch (DocumentException e) {
			Logger.getLogger("ControlMovieGuide").error(
					"Fehler beim lesen der MovieGuide!");
		}
	}

	/**
	 * @return Returns the movieGuideDocument.
	 */
	public static Document getMovieGuideDocument() {
		return movieGuideDocument;
	}

	/**
	 * @param movieGuideDocument
	 *            The movieGuideDocument to set.
	 */
	public static void setMovieGuideDocument(Document movieGuideDocument) {
		ControlMovieGuideTab.movieGuideDocument = movieGuideDocument;
	}
	
	private static void setRootElement() throws Exception{
		Document doc = SerXMLHandling.readDocument(new File(ControlMovieGuideTab.movieGuideFileName));
		root = doc.getRootElement();
	}
    private static Element getRootElement() throws Exception{
    	return root;		
    }    
    
    public ArrayList getDateArray(){
    	return dateArray;
    }
    
    public ArrayList getGenreArray(){
    	return genreArray;
    }
    
    private static void setDateArray(String value){
    	if(!dateArray.contains(value)){ 		    	
    		dateArray.add(value);    
		}	
    }    
    private static void setGenreArray(String value){
    	if(!genreArray.contains(value)){ 							
    		genreArray.add(value);
		}	
    }

    public Hashtable getTitelMap(){
    	return titelList;
    }
    
    private static final String[] elem = {"datum", "titel","genre", "episode","land","jahr","regie","ton","bild","darsteller","inhalt"};
	
    public static void setTitelMap() {
		try {			
			int a = 0;			
			for (Iterator i = root.elementIterator("entry"); i.hasNext();) {
				Element entry = (Element) i.next();
				setDateArray(entry.element(elem[0]).getStringValue());				
				setGenreArray(entry.element(elem[1]).getStringValue());
				String value = entry.element(elem[1]).getStringValue();	
				if(!titelList.containsValue(value)){ 
					titelList.put(new Integer(a), (String)value);
					StringBuffer objectValue = new StringBuffer();	
					for(int z=2;z<=10;z++){
						objectValue.append(entry.element(elem[z]).getStringValue());
						objectValue.append(" |");						
					}
					objectList.put(new Integer(a),objectValue);
					a++;
				}			
			}
		} catch (Exception ex) {}			
	}
   
}