package control;

/*
 * ControlAboutTab.java by Ralph Henneberger
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
import java.util.Map;
import java.util.Iterator;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.BOMovieGuide;
import model.BOTimer;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import presentation.GuiMainView;
import presentation.MovieGuideFilmTableModel;
import presentation.MovieGuideTimerTableModel;
import service.SerFormatter;
import service.SerMovieGuide2Xml;
import service.SerXMLHandling;


public class ControlMovieGuideTab extends ControlTab implements ActionListener,ItemListener, MouseListener,ChangeListener  {
	
	GuiMainView mainView;
	boolean initialized = false;

	static Hashtable titelList;
	static Hashtable controlMap;
	static Hashtable titelListAktuell;
	
	static ArrayList genreList = new ArrayList();
	static ArrayList datumList = new ArrayList();
	static ArrayList senderList = new ArrayList();
	
	private static Element root;
	private static Document movieGuideDocument;

	public static File movieGuideFileName = new File("movieguide.xml");
	private static String SelectedItemJComboBox;
	private static int SelectedItemJComboBoxSucheNach;
    public static int timerTableSize;
    private static BOMovieGuide boMovieGuide4Timer; 
    
	public ControlMovieGuideTab(GuiMainView view) {
		this.setMainView(view);		
	}

	public void initialize() {
		try{
			initialized = true;
			setRootElement();
			if(this.getTitelMap()==null){				
				setTitelMap();
			}
		}catch (Exception ex){}
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action == "download") {
			try{
				SerMovieGuide2Xml.readGuide("",2);
			}catch (Exception ex){}			
		}
		if (action == "neuEinlesen") {
			//
		}
		if (action == "select2Timer") {
			getTimerTableSelectToTimer();
		}
		if (action == "suchen") {					
			setSelectedItemJComboBox(this.getMainView().getTabMovieGuide().getTfSuche().getText());					
			reInitFilmTable(getSelectedItemJComboBoxSucheNach());						
		}
		if (action == "allDates") {
			setSelectedItemJComboBox("all");
			reInitFilmTable(1);
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
			setSelectedItemJComboBox(comboBox.getSelectedItem().toString());
			reInitFilmTable(1);				
		}	
		if (comboBox.getName().equals("jComboBoxGenre")) {		
			setSelectedItemJComboBox(comboBox.getSelectedItem().toString());
			reInitFilmTable(2);		
		}
		if (comboBox.getName().equals("jComboBoxSucheNach")) {		
			SelectedItemJComboBoxSucheNach = (comboBox.getSelectedIndex()+2);			
		}
		if (comboBox.getName().equals("jComboBoxSender")) {		
			setSelectedItemJComboBox(comboBox.getSelectedItem().toString());
			reInitFilmTable(11);		
		}
	}
	
	public int getSelectedItemJComboBoxSucheNach(){
		return SelectedItemJComboBoxSucheNach;
	}
	
	private static void setSelectedItemJComboBox(String value){
		SelectedItemJComboBox = value;
	}
	
	public String getSelectedItemJComboBox(){
		return SelectedItemJComboBox;
	}
	
	public Integer getSelectRowFilmTable(){		
		if (this.getJTableFilm().getSelectedRow()<=0){
			return new Integer(0);
		}else{			
			return new Integer(this.getMainView().getTabMovieGuide().mgFilmTableSorter.modelIndex(this.getJTableFilm().getSelectedRow()));
		}
	}
	public int getSelectRowTimerTable(){		
		if (this.getJTableTimer().getSelectedRow()<=0){
			return 0;
		}else{			
			return this.getMainView().getTabMovieGuide().mgTimerTableSorter.modelIndex(this.getJTableTimer().getSelectedRow());
		}
	}
	
	public void setBOMovieGuide4Timer(BOMovieGuide bomovieguide){
		boMovieGuide4Timer = bomovieguide;
	}
	public BOMovieGuide getBOMovieGuide4Timer(){
		return boMovieGuide4Timer;
	}
	
	public void mousePressed(MouseEvent me) {
		JTable table = (JTable) me.getSource();
		String tableName = table.getName();				
		Integer selectedRow = new Integer(table.getSelectedRow());		
		if (tableName == "filmTable") {	
			int modelIndex = this.getMainView().getTabMovieGuide().mgFilmTableSorter.modelIndex(table.getSelectedRow());
			setBOMovieGuide4Timer((BOMovieGuide)getTitelMap().get(new Integer(modelIndex)));			
			this.getMainView().getTabMovieGuide().getTaEpisode().setText("Episode: "+getBOMovieGuide4Timer().getEpisode());			  		
			this.getMainView().getTabMovieGuide().getTfGenre().setText("Genre: "+getBOMovieGuide4Timer().getGenre());		
			this.getMainView().getTabMovieGuide().getTaLand().setText("Produktion: "+getBOMovieGuide4Timer().getLand()+" / "+getBOMovieGuide4Timer().getJahr()+" / Regie: "+getBOMovieGuide4Timer().getRegie());
			this.getMainView().getTabMovieGuide().getTaAudioVideo().setText("Audio: "+getBOMovieGuide4Timer().getTon()+" / Video: "+getBOMovieGuide4Timer().getBild());											
			this.getMainView().getTabMovieGuide().getTaDarsteller().setText("Darsteller: "+getBOMovieGuide4Timer().getDarsteller());
			this.getMainView().getTabMovieGuide().getTaDarsteller().setCaretPosition(0);
			this.getMainView().getTabMovieGuide().getTaBeschreibung().setText("Inhalt: "+getBOMovieGuide4Timer().getInhalt());
			this.getMainView().getTabMovieGuide().getTaBeschreibung().setCaretPosition(0);
			setTimerTableSize(getBOMovieGuide4Timer().getDatum().size());
			reInitTimerTable();
		}
		if (tableName == "timerTable") {		
			if(me.getClickCount()>=2){ 						
				getTimerTableSelectToTimer();
		 	}
		}
	}
	private void getTimerTableSelectToTimer(){
		BOTimer botimer = new BOTimer();  //FIXME timer aufbauen
		int modelIndexTimer=getSelectRowTimerTable();
		System.out.println(getBOMovieGuide4Timer().getTitel());
		System.out.println(getBOMovieGuide4Timer().getDatum().toArray()[modelIndexTimer]);
		System.out.println(getBOMovieGuide4Timer().getStart().toArray()[modelIndexTimer]);
		System.out.println(getBOMovieGuide4Timer().getEnde().toArray()[modelIndexTimer]);
		System.out.println(getBOMovieGuide4Timer().getDauer().toArray()[modelIndexTimer]);
		System.out.println(getBOMovieGuide4Timer().getSender().toArray()[modelIndexTimer]); //FIXME senderid holen		
	}
	
    private void setTimerTableSize(int size){
    	timerTableSize = size;
    }
    
    public int getTimerTableSize(){
    	return timerTableSize;
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
		JFileChooser fc = new JFileChooser(new File("movieguide.xml"));
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setApproveButtonText("Auswählen");
		fc.setApproveButtonToolTipText("Datei auswählen");
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String path = fc.getSelectedFile().toString();		
			try{
				movieGuideFileName=new File(path);
				this.initialize();
			}catch(Exception ex){}			
		}
	}

	public static void checkMovieGuide() {
		try {
			File pathToXMLFile = movieGuideFileName.getAbsoluteFile();
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
	
	private static File getMovieGuideFileName(){
		return movieGuideFileName;
	}
	
	private static void setMovieGuideFileName(File filename){
		movieGuideFileName = filename;		
	}
	
	public static Document getMovieGuideDocument() {
		return movieGuideDocument;
	}

	public static void setMovieGuideDocument(Document movieGuideDocument) {
		ControlMovieGuideTab.movieGuideDocument = movieGuideDocument;
	}
	
	private static void setRootElement() throws Exception{
		Document doc = SerXMLHandling.readDocument(getMovieGuideFileName());
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
    
    public ArrayList getSenderList(){
    	return senderList;	
    }
    
    private static void setSenderList(String value){
    	if(!senderList.contains(value) && value.length()>0){
    		senderList.add(value);
    	}
    }
    
    public Hashtable getTitelMap(){
    	return titelListAktuell;		
    }    	           
    
    public Hashtable getControlMap(){
    	return controlMap;
    }
    
    private static void setControlMap(String key, Integer value){
    	if(!controlMap.containsKey(key)){
    		controlMap.put(key,value);
    	}
    }
  
    public void setTitelMapSelected(String search,int value){
    	titelListAktuell = new Hashtable();    	
    	Iterator i = titelList.entrySet().iterator();
    	int a = 0;
    	while (i.hasNext()){
    		Map.Entry entry = (Map.Entry)i.next();
    		BOMovieGuide bomovieguide = (BOMovieGuide)entry.getValue();
    		switch (value){
    			case 1: //datum
    				if(bomovieguide.getDatum().contains(search)){
    	    			titelListAktuell.put(new Integer(a++),bomovieguide);
    	    		}
    				break;
    			case 2: //genre
    				if(bomovieguide.getGenre().contains(search)){
    	    			titelListAktuell.put(new Integer(a++),bomovieguide);
    	    		}
    				break;
    			case 3: //titel
    				if(bomovieguide.getTitel().toLowerCase().indexOf(search.toLowerCase())!=-1){
    	    			titelListAktuell.put(new Integer(a++),bomovieguide);
    	    		}
    				break;
    			case 4: //darsteller
    				if(bomovieguide.getDarsteller().toLowerCase().indexOf(search.toLowerCase())!=-1){
    	    			titelListAktuell.put(new Integer(a++),bomovieguide);
    	    		}
    				break;
    			case 5: // episode
    				if(bomovieguide.getEpisode().toLowerCase().indexOf(search.toLowerCase())!=-1){
    	    			titelListAktuell.put(new Integer(a++),bomovieguide);
    	    		}
    				break;
    			case 6: // bild 
    				if(bomovieguide.getBild().indexOf(search)!=-1){
    	    			titelListAktuell.put(new Integer(a++),bomovieguide);
    	    		}
    				break; 
    			case 7: // ton
    				if(bomovieguide.getTon().toLowerCase().indexOf(search.toLowerCase())!=-1){
    	    			titelListAktuell.put(new Integer(a++),bomovieguide);
    	    		}
    				break;
    			case 8: // Prodland
    				if(bomovieguide.getLand().toLowerCase().indexOf(search.toLowerCase())!=-1){
    	    			titelListAktuell.put(new Integer(a++),bomovieguide);
    	    		}
    				break;
    			case 9: //Prodjahr
    				if(bomovieguide.getJahr().indexOf(search)!=-1){
    	    			titelListAktuell.put(new Integer(a++),bomovieguide);
    	    		}
    				break;
    			case 10: //regie
    				if(bomovieguide.getRegie().toLowerCase().indexOf(search)!=-1){
    	    			titelListAktuell.put(new Integer(a++),bomovieguide);
    	    		}
    				break;
    			case 11: //sender
    				if(bomovieguide.getSender().contains(search)){
    	    			titelListAktuell.put(new Integer(a++),bomovieguide);
    	    		}
    				break;    				
    		};    		
    		if(search.equals("all")){
    			titelListAktuell.put(new Integer(a++),bomovieguide);
    		}
    	}    
    }

    public static void setTitelMap() {				    	    
    	titelList = new Hashtable();
    	controlMap = new Hashtable();
    	setGenreList("Genre...");
    	setSenderList("Sender...");
    	try {
			int a = 0;			
			for (Iterator i = root.elementIterator("entry"); i.hasNext();) {
				Element entry = (Element) i.next();		
				setDatumList(entry.element("datum").getStringValue());	
				setSenderList(entry.element("sender").getStringValue());
				if(!controlMap.containsKey(entry.element("titel").getStringValue())){
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
					 BOMovieGuide bomovieguide = (BOMovieGuide)titelList.get(controlMap.get(entry.element("titel").getStringValue()));
                     bomovieguide.setDatum(entry.element("datum").getStringValue());
                     bomovieguide.setStart(entry.element("start").getStringValue());
                     bomovieguide.setDauer(entry.element("dauer").getStringValue());
                     bomovieguide.setSender(entry.element("sender").getStringValue());                             
                     bomovieguide.setEnde(SerFormatter.getCorrectEndTime(entry.element("start").getStringValue(),entry.element("dauer").getStringValue()));
                     titelList.put(controlMap.get(bomovieguide.getTitel()),bomovieguide);
				}
			}
		} catch (Exception ex) {System.out.println(ex);}				
		titelListAktuell = new Hashtable();   // Table aufbauen für den Heutigen Tag
    	Iterator i = titelList.entrySet().iterator();
    	int a = 0;
    	while (i.hasNext()){
    		Map.Entry entry = (Map.Entry)i.next();
    		BOMovieGuide bomovieguide = (BOMovieGuide)entry.getValue();
    		if(bomovieguide.getDatum().contains(SerFormatter.getDatumToday())){
    			titelListAktuell.put(new Integer(a++),bomovieguide);
    		}
    	}       			
	}
    
    
    public JTable getJTableFilm() {
		return this.getMainView().getTabMovieGuide().getJTableFilm();
	}
    
    public JTable getJTableTimer() {
		return this.getMainView().getTabMovieGuide().getJTableTimer();
	}
    
    private MovieGuideTimerTableModel getMovieGuideTimerTableModel() {
		return this.getMainView().getTabMovieGuide().getGuiMovieGuideTimerTableModel();
	}
    
    public void reInitTimerTable() {
		if (this.getMainView().getMainTabPane().tabMovieGuide != null) { 
			this.getMovieGuideTimerTableModel().fireTableDataChanged();
		}
	}
    
	private MovieGuideFilmTableModel getMovieGuideFilmTableModel() {
		return this.getMainView().getTabMovieGuide().getMovieGuideFilmTableModel();
	}
	
	public void reInitFilmTable(int value) {
		if (this.getMainView().getMainTabPane().tabMovieGuide != null) { 
			this.getMovieGuideFilmTableModel().fireTableDataChanged(value);
		}
	}
}