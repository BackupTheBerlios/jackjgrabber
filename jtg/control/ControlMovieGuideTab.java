package control;
/*
 * ControlMovieGuideTab by Ralph Henneberger
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
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.BOMovieGuide;
import model.BOTimer;

import org.dom4j.Document;
import org.dom4j.Element;

import presentation.GuiMainView;

import presentation.GuiTabMovieGuide;
import presentation.MovieGuideFilmTableModel;
import presentation.MovieGuideTimerTableModel;
import service.SerFormatter;
import service.SerMovieGuide2Xml;
import service.SerXMLHandling;


public class ControlMovieGuideTab extends ControlTab implements ActionListener,ItemListener, MouseListener,ChangeListener  {
	
	GuiMainView mainView;
	GuiTabMovieGuide tab;
	BOMovieGuide boMovieGuide4Timer;
	boolean initialized = false;
	boolean searchAbHeute = true;

	Hashtable titelList;
	Hashtable controlMap;
	Hashtable titelListAktuell;
	
	ArrayList genreList = new ArrayList();
	ArrayList datumList = new ArrayList();
	ArrayList senderList = new ArrayList();
	
	Element root;

	public static File movieGuideFileName = new File("movieguide.xml");
	String SelectedItemJComboBox;
	int SelectedItemJComboBoxSucheNach;
    int timerTableSize; 
    
	public ControlMovieGuideTab(GuiMainView view) {
		this.setMainView(view);		
	}

	public void initialize() {
		try{			
			initialized = true;
			this.setTab((GuiTabMovieGuide)this.getMainView().getTabMovieGuide());
			setRootElement();
			if(this.getTitelMap()==null){				
				setTitelMap();
			}
			this.getTab().getComboBoxGenre().setSelectedIndex(0);
			this.getTab().getComboBoxSender().setSelectedIndex(0);
			this.getTab().getComboBoxDatum().setSelectedItem(SerFormatter.getDatumToday());			
			this.getTab().mgFilmTableSorter.setSortingStatus(0,2);		//alphabetisch geordnet
		}catch (Exception ex){System.out.println(ex);}			
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action == "download") {
			try{
				new SerMovieGuide2Xml(null, this.getMainView()).start();
			}catch (Exception ex){System.out.println(ex);}			
		}
		if (action == "neuEinlesen") {
			//
		}
		if (action == "select2Timer") {
			getTimerTableSelectToTimer();
		}
		if (action == "suchen") {					
			setSelectedItemJComboBox(this.getTab().getTfSuche().getText());	
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
		if (comp.equals("javax.swing.JCheckBox")) {
			JCheckBox checkBox = (JCheckBox)event.getSource();
			if (checkBox.getName().equals("showAbHeute")) {
				searchAbHeute = checkBox.isSelected();		
			}
		}else{	
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
				reInitFilmTable(12);		
			}
		}
	}
	
	public int getSelectedItemJComboBoxSucheNach(){
		return SelectedItemJComboBoxSucheNach;
	}
	
	private void setSelectedItemJComboBox(String value){
		this.SelectedItemJComboBox = value;
	}
	
	public String getSelectedItemJComboBox(){
		return SelectedItemJComboBox;
	}
	
	public Integer getSelectRowFilmTable(){				
		if (this.getTab().mgFilmTableSorter.modelIndex(this.getJTableFilm().getSelectedRow())<=0){
			return new Integer(0);
		}else{			
			return new Integer(this.getTab().mgFilmTableSorter.modelIndex(this.getJTableFilm().getSelectedRow()));
		}
	}
	public int getSelectRowTimerTable(){				
		if (this.getTab().mgTimerTableSorter.modelIndex(this.getJTableTimer().getSelectedRow())<=0){
			return 0;
		}else{			
			return this.getTab().mgTimerTableSorter.modelIndex(this.getJTableTimer().getSelectedRow());
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
		if (tableName == "filmTable") {				
			reInitTable(new Integer(this.getTab().mgFilmTableSorter.modelIndex(table.getSelectedRow())));			
		}
		if (tableName == "timerTable") {				
			this.getTab().getTaAudioVideo().setText("Audio: "+getBOMovieGuide4Timer().getTon().toArray()[getSelectRowTimerTable()]+" / Video: "+getBOMovieGuide4Timer().getBild().toArray()[getSelectRowTimerTable()]);
			if(me.getClickCount()>=2){ 						
				getTimerTableSelectToTimer();
		 	}
		}
	}
	
	private void reInitTable(Integer modelIndex){
		setBOMovieGuide4Timer((BOMovieGuide)getTitelMap().get(modelIndex));			
		this.getTab().getTaEpisode().setText("Episode: "+getBOMovieGuide4Timer().getEpisode());			  		
		this.getTab().getTaGenre().setText("Genre: "+getBOMovieGuide4Timer().getGenre());		
		this.getTab().getTaAudioVideo().setText("Audio: / Video: ");
		this.getTab().getTaLand().setText("Produktion: "+getBOMovieGuide4Timer().getLand()+" / "+getBOMovieGuide4Timer().getJahr()+" / Regie: "+getBOMovieGuide4Timer().getRegie());													
		this.getTab().getTaDarsteller().setText("Darsteller: "+getBOMovieGuide4Timer().getDarsteller());
		this.getTab().getTaDarsteller().setCaretPosition(0);
		this.getTab().getTaBeschreibung().setText("Inhalt: "+getBOMovieGuide4Timer().getInhalt());
		this.getTab().getTaBeschreibung().setCaretPosition(0);
		setTimerTableSize(getBOMovieGuide4Timer().getDatum().size());
		reInitTimerTable();
	}
	/*
	private BOTimer buildTimer(BOEpg epg) {
		BOTimer timer = new BOTimer();		
		int timeBefore = Integer.parseInt(ControlMain.getSettings().getRecordTimeBefore())*60;
		int timeAfter = Integer.parseInt(ControlMain.getSettings().getRecordTimeAfter())*60;
		long unformattedStart = Long.parseLong(epg.getUnformattedStart());
		long unformattedDuration = Long.parseLong(epg.getUnformattedDuration());
		long endtime = unformattedStart+unformattedDuration;
		long announce = unformattedStart-(120);
		
		timer.setModifiedId("new");
		timer.setChannelId(this.getSelectedSender().getChanId());
		timer.setSenderName(this.getSelectedSender().getName());
		timer.setAnnounceTime(Long.toString(announce)); //Vorwarnzeit
		timer.setUnformattedStartTime(SerFormatter.formatUnixDate(unformattedStart-timeBefore));
		timer.setUnformattedStopTime(SerFormatter.formatUnixDate(endtime+timeAfter));
		
		timer.setEventRepeatId("0");
		timer.setEventTypeId("5");
		timer.setDescription(epg.getTitle());
		return timer;
	}
	*/
	
	private void getTimerTableSelectToTimer(){
		int modelIndexTimer=getSelectRowTimerTable();
		
		BOTimer botimer = new BOTimer();  //FIXME timer aufbauen			
		int timeBefore = Integer.parseInt(ControlMain.getSettings().getRecordTimeBefore())*-1;
		int timeAfter = Integer.parseInt(ControlMain.getSettings().getRecordTimeAfter());
		int timeAnnounce = (Integer.parseInt(ControlMain.getSettings().getRecordTimeBefore())+2)*-1;
		// timer
			botimer.setModifiedId("new");
		//	botimer.setChannelId(this.getSelectedSender().getChanId());
		//	botimer.setSenderName(this.getSelectedSender().getName());		
			botimer.setUnformattedStartTime(SerFormatter.getGC((GregorianCalendar)getBOMovieGuide4Timer().getStart().toArray()[modelIndexTimer],timeBefore));
			botimer.setUnformattedStopTime(SerFormatter.getGC((GregorianCalendar)getBOMovieGuide4Timer().getEnde().toArray()[modelIndexTimer],timeAfter));			
			botimer.setAnnounceTime( String.valueOf((SerFormatter.getGC(botimer.getUnformattedStartTime(),2)).getTimeInMillis()) );
			botimer.setEventRepeatId("0");
			botimer.setEventTypeId("5");
			botimer.setDescription(getBOMovieGuide4Timer().getTitel());
		//

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
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setApproveButtonText("Ausw�hlen");
		fc.setApproveButtonToolTipText("Datei ausw�hlen");
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String path = fc.getSelectedFile().toString();		
			try{
				new SerMovieGuide2Xml(path, this.getMainView()).start();
			}catch(Exception ex){System.out.println(ex);}			
		}
	}
	
	private File getMovieGuideFileName(){
		return movieGuideFileName;
	}
	
	private void setMovieGuideFileName(File filename){
		movieGuideFileName = filename;		
	}
	
	private void setRootElement() throws Exception{
		Document doc = SerXMLHandling.readDocument(getMovieGuideFileName());
		root = doc.getRootElement();
	}
    private Element getRootElement() throws Exception{
    	return root;		
    }    
	 
    public ArrayList getDatumList(){    	
    	return datumList;
    }
    
    private void setDatumList(String value){    	
    	if(!datumList.contains((String)value)){
    		datumList.add(value);    		
    	}    	
    }
    
    public ArrayList getGenreList(){
    	return genreList;	
    }
    
    private void setGenreList(String value){
    	if(!genreList.contains(value) && value.length()>0){
    		genreList.add(value);
    	}
    }
    
    public ArrayList getSenderList(){
    	return senderList;	
    }
    
    private void setSenderList(String value){
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
    
    private void setControlMap(String key, Integer value){
    	if(!controlMap.containsKey(key)){
    		controlMap.put(key,value);
    	}
    }
    private boolean checkSearchToday(BOMovieGuide bomovieguide){ //pr�fen ob Datum > als Heute in BOMovieGuide-Object
    	return (bomovieguide.booleanArrayDate(bomovieguide.getDatum(),String.valueOf(SerFormatter.getStringToLong(SerFormatter.getDatumToday()))));
    }
    
    private int doItSearch(BOMovieGuide bomovieguide ,String value, String search, int a){
    	if(value.indexOf(search)!=-1){    	
    		//a = searchValue(bomovieguide,a);  
    		titelListAktuell.put(new Integer(a++),bomovieguide);
		}		
    	return (a);
    }
    private int doItSearchArray(BOMovieGuide bomovieguide ,ArrayList value, String search, int a){
    	if(bomovieguide.booleanArrayTest(value,search.toLowerCase())){
    		//a = searchValue(bomovieguide,a);    
    		titelListAktuell.put(new Integer(a++),bomovieguide);
		}
    	return (a);
    }
    private int searchValue(BOMovieGuide bomovieguide,int a){
    	/*if(searchAbHeute){
			if(checkSearchToday(bomovieguide)){
				titelListAktuell.put(new Integer(a++),bomovieguide);
			}
		}else{*/
			titelListAktuell.put(new Integer(a++),bomovieguide);
	//	}
    	return (a++);
    }
    
    public void setTitelMapSelected(String search,int value){  //bauen der AnzeigeMap nach Suchkriterien
    	titelListAktuell = new Hashtable();    	
    	Iterator i = titelList.entrySet().iterator();
    	int a = 0;
    	while (i.hasNext()){
    		Map.Entry entry = (Map.Entry)i.next();
    		BOMovieGuide bomovieguide = (BOMovieGuide)entry.getValue();    		
    		switch (value){
    			case 1: //datum    				
    				if(bomovieguide.getDatum().contains(String.valueOf(SerFormatter.getStringToLong(search)))){ 
    	    			titelListAktuell.put(new Integer(a++),bomovieguide);
    	    		}
    				break;
    			case 2: //genre
    				a = doItSearch(bomovieguide,bomovieguide.getGenre(),search,a);    				
    				break;
    			case 3: //titel
    				a = doItSearch(bomovieguide,bomovieguide.getTitel(),search,a);    				
    				break;
    			case 4: //darsteller
    				a = doItSearch(bomovieguide,bomovieguide.getDarsteller(),search,a);    				
    				break;
    			case 5: // episode
    				a = doItSearch(bomovieguide,bomovieguide.getEpisode(),search,a);    				
    				break;
    			case 6: // bild    			
    				a = doItSearchArray(bomovieguide, bomovieguide.getBild(),search,a);    				
    				break; 
    			case 7: // ton
    				a = doItSearchArray(bomovieguide, bomovieguide.getTon(),search,a);    				
    				break;
    			case 8: // Prodland
    				a = doItSearch(bomovieguide,bomovieguide.getLand(),search,a);    				
    				break;
    			case 9: //Prodjahr
    				a = doItSearch(bomovieguide,bomovieguide.getJahr(),search,a);    				
    				break;
    			case 10: //regie
    				a = doItSearch(bomovieguide,bomovieguide.getRegie(),search,a);    				
    				break;
    			case 11:
					if(bomovieguide.getIfStringInObject(search.toLowerCase())){
						a = searchValue(bomovieguide,a);						
					}
					break;
    			case 12: //sender    			
    				if(bomovieguide.getSender().contains(search)){
    					a = searchValue(bomovieguide,a);    					
    	    		}    	    		
    				break;     								
    		};    		
    		if(search.equals("all")){
    			titelListAktuell.put(new Integer(a++),bomovieguide);
    		}    		
    	}    
    }
    
    public void setTitelMap() {				    	    // einlesen der xml datei in Hashtable (key,BOMovieGuide Object)
    	titelList = new Hashtable();
    	controlMap = new Hashtable();
    	setGenreList("Genre...");
    	setSenderList("Sender...");
    	try {
			int a = 0;			
			for (Iterator i = root.elementIterator("entry"); i.hasNext();) {
				Element entry = (Element) i.next();						
				if ( (SerFormatter.getStringToLong(entry.element("datum").getStringValue())) >= (SerFormatter.getStringToLong(SerFormatter.getDatumToday())) ){		//nur lesen was ab heutigen datum													
				setDatumList(entry.element("datum").getStringValue());	
				setSenderList(entry.element("sender").getStringValue());
				//}
				if(!controlMap.containsKey(entry.element("titel").getStringValue())){ //pr�fen ob titel schon vorhanden ist, wenn ja nur neue Daten hinzug�gen
				String datum = entry.element("datum").getStringValue();
				String start = entry.element("start").getStringValue();
					BOMovieGuide bomovieguide = new BOMovieGuide(
						entry.element("sender").getStringValue(), 
						String.valueOf(SerFormatter.getStringToLong(datum)),
						SerFormatter.getString2Cal(datum,start),
						SerFormatter.getString2Cal(datum,SerFormatter.getCorrectEndTime(start,entry.element("dauer").getStringValue())),					
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
					 String datum = entry.element("datum").getStringValue();
				     String start = entry.element("start").getStringValue();
					 BOMovieGuide bomovieguide = (BOMovieGuide)titelList.get(controlMap.get(entry.element("titel").getStringValue()));
                     bomovieguide.setDatum(String.valueOf(SerFormatter.getStringToLong(datum)));
                     bomovieguide.setStart(SerFormatter.getString2Cal(datum,start));
                     bomovieguide.setDauer(entry.element("dauer").getStringValue());
                     bomovieguide.setSender(entry.element("sender").getStringValue());                             
                     bomovieguide.setBild(entry.element("bild").getStringValue());
                     bomovieguide.setTon(entry.element("ton").getStringValue());
                     bomovieguide.setEnde(SerFormatter.getString2Cal(datum,SerFormatter.getCorrectEndTime(start,entry.element("dauer").getStringValue())));
                     titelList.put(controlMap.get(bomovieguide.getTitel()),bomovieguide);
				}
			}
			}
		} catch (Exception ex) {System.out.println(ex);}				
		setTitelMapSelected(SerFormatter.getDatumToday(),1);  // TitelMap f�r den heutigen Tag		    	
	}
    
    
    public JTable getJTableFilm() {
		return this.getTab().getJTableFilm();
	}
    
    public JTable getJTableTimer() {
		return this.getTab().getJTableTimer();
	}
    
    private MovieGuideTimerTableModel getMovieGuideTimerTableModel() {
		return this.getTab().getGuiMovieGuideTimerTableModel();
	}
    
    public void reInitTimerTable() {
		if (this.getMainView().getMainTabPane().tabMovieGuide != null) { 
			this.getMovieGuideTimerTableModel().fireTableDataChanged();
		}
	}
    
	private MovieGuideFilmTableModel getMovieGuideFilmTableModel() {
		return this.getTab().getMovieGuideFilmTableModel();
	}
	
	public void reInitFilmTable(int value) {
		if (this.getMainView().getMainTabPane().tabMovieGuide != null) { 
			this.getMovieGuideFilmTableModel().fireTableDataChanged(value);
		}
	}
	public GuiTabMovieGuide getTab() {
		return tab;
	}
	/**
	 * @param tab The tab to set.
	 */
	public void setTab(GuiTabMovieGuide tab) {
		this.tab = tab;
	}
	public boolean checkNewMovieGuide(){
		boolean value = false;
		//SerMovieGuide2Xml.op
		return value;
	}
}