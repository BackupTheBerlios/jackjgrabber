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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.BOMovieGuide;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import presentation.GuiMainView;
import presentation.MovieGuideFilmTableModel;
import service.SerFormatter;
import service.SerMovieGuide2Xml;
import service.SerXMLHandling;


public class ControlMovieGuideTab extends ControlTab implements ActionListener,ItemListener, MouseListener,ChangeListener  {

	GuiMainView mainView;

	static Hashtable titelList;
	static Hashtable controlMap;
	
	static ArrayList genreList = new ArrayList();
	static ArrayList datumList = new ArrayList();
	
	private static Element root;
	private static Document movieGuideDocument;

	public static String movieGuideFileName = "movieguide.all";
	

	public ControlMovieGuideTab(GuiMainView view) {
		this.setMainView(view);
		
		try{
			if(getRootElement() == null){							
				setRootElement();
			}
			if(this.getTitelMap()==null){				
				setTitelMap();
			}
		}catch (Exception ex){}
				
		//this.initialize();		
	}

	public void initialize() {							
		/*
		try{
			if(getRootElement() == null){							
				setRootElement();
			}
			if(this.getTitelMap()==null){				
				setTitelMap();
			}			
		}catch (Exception ex){}
		*/
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action == "download") {
			try{
				SerMovieGuide2Xml.readGuide("",2);
			}catch (Exception ex){}			
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
	public void stateChanged(ChangeEvent event) {
	
	}
	public void itemStateChanged (ItemEvent event) {
		String comp = event.getSource().getClass().getName();		
		JComboBox comboBox = (JComboBox)event.getSource();
		if (comboBox.getName().equals("jComboBoxDatum")) {			
			
		}			
	}
	public Integer getSelectRowFilmTable(){		
		if (this.getJTableFilm().getSelectedRow()<=0){
			return new Integer(0);
		}else{
			return new Integer(this.getJTableFilm().getSelectedRow());
		}
	}
	public void mousePressed(MouseEvent me) {
		JTable table = (JTable) me.getSource();
		String tableName = table.getName();		
		Integer selectedRow = new Integer(table.getSelectedRow());
		if (tableName == "filmTable") {	
			BOMovieGuide bomovieguide = (BOMovieGuide)getTitelMap().get(selectedRow);
			this.getMainView().getTabMovieGuide().getTaEpisode().setText("Episode:"+bomovieguide.getEpisode());			  		
			this.getMainView().getTabMovieGuide().getTfGenre().setText("Genre: "+bomovieguide.getGenre());		
			this.getMainView().getTabMovieGuide().getTaLand().setText("Produktion: "+bomovieguide.getLand()+" / "+bomovieguide.getJahr()+" / Regie: "+bomovieguide.getRegie());
			this.getMainView().getTabMovieGuide().getTaAudioVideo().setText("Audio: "+bomovieguide.getTon()+" / Video: "+bomovieguide.getBild());											
			this.getMainView().getTabMovieGuide().getTaDarsteller().setText("Darsteller: "+bomovieguide.getDarsteller());
			this.getMainView().getTabMovieGuide().getTaBeschreibung().setText("Inhalt: "+bomovieguide.getInhalt());
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
			String path = fc.getSelectedFile().toString();		
			try{
				SerMovieGuide2Xml.readGuide(path,1);
			}catch(Exception ex){}			
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
					"Fehler beim lesen des MovieGuide!");
		} catch (DocumentException e) {
			Logger.getLogger("ControlMovieGuide").error(
					"Fehler beim lesen des MovieGuide!");
		}
	}
	
	private static String getMovieGuideFileName(){
		return movieGuideFileName;
	}
	
	private static void setMovieGuideFileName(String filename){
		movieGuideFileName = filename;		
	}
	
	public static Document getMovieGuideDocument() {
		return movieGuideDocument;
	}

	public static void setMovieGuideDocument(Document movieGuideDocument) {
		ControlMovieGuideTab.movieGuideDocument = movieGuideDocument;
	}
	
	private static void setRootElement() throws Exception{
		Document doc = SerXMLHandling.readDocument(new File(getMovieGuideFileName()));
		root = doc.getRootElement();
	}
    private static Element getRootElement() throws Exception{
    	return root;		
    }    
	 
    public ArrayList getDatumList(){    	
    	return datumList;
    }
    
    private static void setDatumList(String value){    	
    	if(!datumList.contains((String)value)){
    		datumList.add(value);    		
    	}    	
    }
    
    public ArrayList getGenreList(){
    	return genreList;	
    }
    
    private static void setGenreList(String value){
    	if(!genreList.contains(value) && value.length()>0){
    		genreList.add(value);
    	}
    }
    
    public Hashtable getTitelMap(){
    	return titelList;		
    }    	           
    
    private static Hashtable getControlMap(){
    	return controlMap;
    }
    
    private static void setControlMap(String key, Integer value){
    	if(!controlMap.containsKey(key)){
    		controlMap.put(key,value);
    	}
    }
    
    public static void setTitelMap() {				    	    
    	titelList = new Hashtable();
    	controlMap = new Hashtable();
    	try {
			int a = 0;			
			for (Iterator i = root.elementIterator("entry"); i.hasNext();) {
				Element entry = (Element) i.next();		
				setDatumList(entry.element("datum").getStringValue());
				if(!getControlMap().containsKey(entry.element("titel").getStringValue())){
				BOMovieGuide bomovieguide = new BOMovieGuide(
						entry.element("sender").getStringValue(), 
						entry.element("datum").getStringValue(),
						entry.element("start").getStringValue(),
						SerFormatter.getCorrectEndTime(entry.element("start").getStringValue(),entry.element("dauer").getStringValue()),
						entry.element("titel").getStringValue(),
						entry.element("episode").getStringValue(),
						entry.element("genre").getStringValue(),
						entry.element("dauer").getStringValue(),
						entry.element("land").getStringValue(),
						entry.element("jahr").getStringValue(),
						entry.element("regie").getStringValue(),
						entry.element("bild").getStringValue(),
						entry.element("ton").getStringValue(),
						entry.element("darsteller").getStringValue(),
						entry.element("inhalt").getStringValue()
				);				
				setGenreList(bomovieguide.getGenre());
				titelList.put(new Integer(a),bomovieguide);		
				setControlMap(bomovieguide.getTitel(),new Integer(a));
                a++;               
				}else{					 
					 BOMovieGuide bomovieguide = (BOMovieGuide)titelList.get(getControlMap().get(entry.element("titel").getStringValue()));
                     bomovieguide.setDatum(entry.element("datum").getStringValue());
                     bomovieguide.setStart(entry.element("start").getStringValue());
                     bomovieguide.setDauer(entry.element("dauer").getStringValue());
                     bomovieguide.setSender(entry.element("sender").getStringValue());                             
                     bomovieguide.setEnde(SerFormatter.getCorrectEndTime(entry.element("start").getStringValue(),entry.element("dauer").getStringValue()));
                     titelList.put(getControlMap().get(bomovieguide.getTitel()),bomovieguide);
				}
			}
		} catch (Exception ex) {System.out.println(ex);}				
	}

    private JTable getJTableFilm() {
		return this.getMainView().getTabMovieGuide().getJTableFilm();
	}
	
	private MovieGuideFilmTableModel getMovieGuideFilmTableModel() {
		return this.getMainView().getTabMovieGuide().getMovieGuideFilmTableModel();
	}
}