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
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Collections;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.BOMovieGuide;
import model.BOSender;
import model.BOSettings;
import model.BOTimer;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import presentation.GuiMainView;
import presentation.movieguide.GuiTabMovieGuide;
import presentation.movieguide.GuiMovieGuideFilmTableModel;
import presentation.movieguide.GuiMovieGuideTimerTableModel;
import service.SerAlertDialog;
import service.SerFormatter;
import service.SerMovieGuide2Xml;
import service.SerXMLHandling;


/**
 * @author ralph
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ControlMovieGuideTab extends ControlTab implements ActionListener,ItemListener, MouseListener,ChangeListener, Runnable, KeyListener {
	
	GuiMainView mainView;
	GuiTabMovieGuide tab;
	BOMovieGuide boMovieGuide4Timer;
	boolean searchAbHeute = true;

	HashMap titelList;
	HashMap controlMap;
	HashMap titelListAktuell;
	
	ArrayList genreList = new ArrayList();
	ArrayList datumList = new ArrayList();
	ArrayList senderList = new ArrayList();
	ArrayList boxSenderList;
	
	Element root;

	public static File movieGuideFile = new File("movieguide_"+SerFormatter.getAktuellDateString(0,"MM_yy")+".xml");
	public static File movieGuideFileNext = new File("movieguide_"+SerFormatter.getAktuellDateString(1,"MM_yy")+".xml");
	ArrayList aboList = getSettings().getMgSelectedChannels();
	
	private static final String DATE_FULL = "EEEE, dd. MMMM yyyy";
	private static final String DATE_FULL_TIME = "EEEE, dd. MMMM yyyy,HH:mm";	
	private static final String GENRE  = ControlMain.getProperty("txt_genre2");
	private static final String SENDER = ControlMain.getProperty("txt_sender2");
	//private static String HTML_ON  = "<HTML><font size=3>";
	//private static String HTML_OFF = "</font></HTML>";
	private static String HTML_ON  = "<HTML><font face='System' size='3'>";
	private static String HTML_OFF = "</font></HTML>";
	String SelectedItemJComboBox;
	int SelectedItemJComboBoxSucheNach;
    int timerTableSize; 
    int zaehler = 0;
    
	public ControlMovieGuideTab(GuiMainView view) {
		this.setMainView(view);		
	}
	
	public void run() {
		try {
          this.setTab((GuiTabMovieGuide)this.getMainView().getTabMovieGuide());          
          if(getSettings().getMgLoadType()==1){
          	if(SerMovieGuide2Xml.checkNewMovieGuide()){
          		SerAlertDialog.alert(ControlMain.getProperty("txt_mg_info1")+SerFormatter.getAktuellDateString(1,"MMMM")+ControlMain.getProperty("txt_mg_info2"),this.getMainView()); 					
          	}
          }
          setRootElement();
          if(this.getTitelMap()==null){				
          	setTitelMap();
          }
          beautifyGui();          
          
      } catch (MalformedURLException e) {
          Logger.getLogger("ControlMovieGuideTab").error(movieGuideFile.getName()+" not found");
      } catch (DocumentException e) {
          e.printStackTrace();
      }		
	}
	
	
	/** 
	 * @param keine
	 * Es wird mit setTitelMapSelected die titelListAktuell für den heutigen Tag gebaut,
	 * es werden die Sender/Genre-ArrayListen mittel Collections alphabetisch sortiert.
	 * Alle ComboBoxen auf das 1 Element gesetz, weiter hin wird bei der FilmTable die 
	 * erste Row selectiert.
	 */
	private void beautifyGui(){
		if(getSettings().getMgDefault()==0){
			setTitelMapSelected(SerFormatter.getFormatGreCal(),13);   // TitelMap Alles      
		}else{
			setTitelMapSelected(SerFormatter.getFormatGreCal(),1);  // TitelMap für den heutigen Tag   
			this.getTab().getComboBoxDatum().setSelectedItem(SerFormatter.getFormatGreCal());	 
		}      
        Collections.sort(getSenderList());		//alphabetisch geordnet 
        Collections.sort(getGenreList());		//alphabetisch geordnet
        this.getTab().getComboBoxGenre().setSelectedIndex(0);          
        this.getTab().getComboBoxSender().setSelectedIndex(0);          
        this.getTab().mgFilmTableSorter.setSortingStatus(0,2); //alphabetisch geordnet
        getJTableFilm().getSelectionModel().setSelectionInterval(0,0); //1 Row selected
    }
	
	
	private void downloadMovieGuide(){
		if(getMovieGuideFile().exists()){
			if(SerMovieGuide2Xml.checkNewMovieGuide()){
				SerAlertDialog.alert(ControlMain.getProperty("txt_mg_info1")+SerFormatter.getAktuellDateString(1,"MMMM")+ControlMain.getProperty("txt_mg_info2"),this.getMainView()); 					
          	}else{
          		SerAlertDialog.alert(ControlMain.getProperty("txt_mg_info3")+SerFormatter.getAktuellDateString(0,"MMMM")+".\n"+ControlMain.getProperty("txt_mg_info4")+SerFormatter.getAktuellDateString(1,"MMMM")+" "+ControlMain.getProperty("txt_mg_info5"),this.getMainView());
          	}
		}else{				
			try{
				new SerMovieGuide2Xml(null, this.getMainView()).start();
			}catch (Exception ex){
				Logger.getLogger("ControlMovieGuideTab").error(ControlMain.getProperty("error_not_download"));
			}		
		}
	}
	public void actionPerformed(ActionEvent e) {	
		String action = e.getActionCommand();
		if (action == "download") {
			downloadMovieGuide();
		}
		if (action == "neuEinlesen") {
			//
		}
		if (action == "select2Timer") {
		    if (this.getJTableTimer().getSelectedRow()>=0) {
				getTimerTableSelectToTimer();   
		    } else {
		        SerAlertDialog.alert(ControlMain.getProperty("error_no_timer_sel"), this.getMainView());
		    }
		}
		if (action == "suchen") {		
			if(this.getTitelMap()!=null){
			setSelectedItemJComboBox(this.getTab().getTfSuche().getText());
			if(getSelectedItemJComboBoxSucheNach()==0) {								
				reInitFilmTable(2);
			}else{					
				reInitFilmTable(getSelectedItemJComboBoxSucheNach());
			}		
			getJTableFilm().getSelectionModel().setSelectionInterval(0,0);		
			findAndReplaceGui(getSelectedItemJComboBox());
			}
		}
		if (action == "allDates") {	
			if(this.getTitelMap()!=null){
				this.getTab().getTfSuche().setText("");
				this.getTab().getComboBoxGenre().setSelectedIndex(0);          
				this.getTab().getComboBoxSender().setSelectedIndex(0); 
				reInitFilmTable(13);
			}
		}
		if (action == "movieGuidePath") {
			this.openFileChooser();
		}
		if (action == "clickONDatumComboBox"){
			JComboBox comboBox = this.getTab().getComboBoxDatum();
			if(comboBox.getItemCount()>=1){
				this.getTab().getTfSuche().setText("");
				this.getTab().getComboBoxGenre().setSelectedIndex(0);          
				this.getTab().getComboBoxSender().setSelectedIndex(0); 
				setSelectedItemJComboBox(comboBox.getSelectedItem().toString());
				reInitFilmTable(1);						
				getJTableFilm().getSelectionModel().setSelectionInterval(0,0);	
			}
		}
		if (action == "clickONGenreComboBox"){
			JComboBox comboBox = this.getTab().getComboBoxGenre();
			if(comboBox.getItemCount()>=1){
				this.getTab().getTfSuche().setText("");
				if(!comboBox.getSelectedItem().toString().equals(GENRE)){
					setSelectedItemJComboBox(comboBox.getSelectedItem().toString());
					reInitFilmTable(11);		
					getJTableFilm().getSelectionModel().setSelectionInterval(0,0);
				}
			}
		}
		if (action == "clickONSenderComboBox"){
			JComboBox comboBox = this.getTab().getComboBoxSender();
			if(comboBox.getItemCount()>=1){
				this.getTab().getTfSuche().setText("");
				if(!comboBox.getSelectedItem().toString().equals(SENDER)){
					setSelectedItemJComboBox(comboBox.getSelectedItem().toString());
					reInitFilmTable(12);
					getJTableFilm().getSelectionModel().setSelectionInterval(0,0);
				}
			}
		}
	}
	
	private void findAndReplaceGui(String search){			
		this.getTab().getTaEpisode().setText( SerFormatter.replaceFind(this.getTab().getTaEpisode().getText(),search));					
		this.getTab().getTaGenre().setText( SerFormatter.replaceFind(this.getTab().getTaGenre().getText(),search));		
		this.getTab().getTaAudioVideo().setText( SerFormatter.replaceFind(this.getTab().getTaAudioVideo().getText(),search));
		this.getTab().getTaLand().setText( SerFormatter.replaceFind(this.getTab().getTaLand().getText(),search));													
		this.getTab().getTaDarsteller().setText( SerFormatter.replaceFind(this.getTab().getTaDarsteller().getText(),search));
		this.getTab().getTaDarsteller().setCaretPosition(0);		
		this.getTab().getTaBeschreibung().setText( SerFormatter.replaceFind(this.getTab().getTaBeschreibung().getText(),search));		
		this.getTab().getTaBeschreibung().setCaretPosition(0);		
	}
	
	public void stateChanged(ChangeEvent event) {	
	}
	
	/**
	 * Change-Events of the GuiTabMovieGuide
	 */
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
				this.getTab().getTfSuche().setText("");
				setSelectedItemJComboBox(comboBox.getSelectedItem().toString());
				reInitFilmTable(1);						
				getJTableFilm().getSelectionModel().setSelectionInterval(0,0);						
			}	
			if (comboBox.getName().equals("jComboBoxGenre")) {		
				this.getTab().getTfSuche().setText("");
				if(!comboBox.getSelectedItem().toString().equals(GENRE)){
					setSelectedItemJComboBox(comboBox.getSelectedItem().toString());
					reInitFilmTable(11);		
					getJTableFilm().getSelectionModel().setSelectionInterval(0,0);
				}
			}
			if (comboBox.getName().equals("jComboBoxSucheNach")) {		
				SelectedItemJComboBoxSucheNach = (comboBox.getSelectedIndex()+2);					
			}
			if (comboBox.getName().equals("jComboBoxSender")) {	
				this.getTab().getTfSuche().setText("");
				if(!comboBox.getSelectedItem().toString().equals(SENDER)){
					setSelectedItemJComboBox(comboBox.getSelectedItem().toString());
					reInitFilmTable(12);
					getJTableFilm().getSelectionModel().setSelectionInterval(0,0);
				}
			}
		}
	}
	
	
	/**
	 * @return int
	 * Gib den selectierten Eintrag der SucheNach-ComboBox zurück
	 */
	public int getSelectedItemJComboBoxSucheNach(){
		return SelectedItemJComboBoxSucheNach;
	}
	
	/**
	 * @param value
	 * Setz den selectierten Eintrag der SucheNach-ComboBox
	 */
	private void setSelectedItemJComboBox(String value){
		this.SelectedItemJComboBox = value;
	}
	
	/**
	 * @return String 
	 * Gibt das selectierte Element der ComboBox zurück
	 */
	public String getSelectedItemJComboBox(){
		return SelectedItemJComboBox;
	}
	
	/**
	 * @return Selectierte Row der FilmTable
	 * 
	 */
	public Integer getSelectRowFilmTable(){				
		if (this.getTab().mgFilmTableSorter.modelIndex(this.getJTableFilm().getSelectedRow())<=0){
			return new Integer(0);
		}else{			
			return new Integer(this.getTab().mgFilmTableSorter.modelIndex(this.getJTableFilm().getSelectedRow()));
		}
	}
	/**
	 * @return Selectierte Row der TimerTable
	 * 
	 */
	public int getSelectRowTimerTable(){		    
		return this.getTab().mgTimerTableSorter.modelIndex(this.getJTableTimer().getSelectedRow());
	}
	
	/**
	 * @param bomovieguide
	 * Setzt das aktuelle BOMovieGuide Object, das was in der FilmTable selectiert würde
	 */
	public void setBOMovieGuide4Timer(BOMovieGuide bomovieguide){
		boMovieGuide4Timer = bomovieguide;
	}
	
	/**
	 * @return
	 * Gibt aktuelle BOMovieGuide Object, das was in der FilmTable selectiert würde zurück
	 */
	public BOMovieGuide getBOMovieGuide4Timer(){
		return boMovieGuide4Timer;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent me) {		
		JTable table = (JTable) me.getSource();
		String tableName = table.getName();								
		if (tableName == "filmTable") {				
			reInitTable(new Integer(this.getTab().mgFilmTableSorter.modelIndex(table.getSelectedRow())));	
			if(this.getTab().getTfSuche().getText().length()>=0){
				findAndReplaceGui(this.getTab().getTfSuche().getText());
			}
		}
		if (tableName == "timerTable") {				
			this.getTab().getTaAudioVideo().setText(HTML_ON+"<u>"+ControlMain.getProperty("txt_audio")+"</u> "+getBOMovieGuide4Timer().getTon().get(getSelectRowTimerTable())+" / <u>"+ControlMain.getProperty("txt_video")+"</u> "+getBOMovieGuide4Timer().getBild().get(getSelectRowTimerTable())+HTML_OFF);
			if(me.getClickCount()>=2){ 						
				getTimerTableSelectToTimer();
		 	}
		}		
	}
	
	/**
	 * @param modelIndex
	 * Aktuallisiert die TimerTable und die entsprechen Textfelder (Inhalt, Genre...) für
	 * die selectierte Row(Titel) der FilmTable
	 */
	private void reInitTable(Integer modelIndex){
		setBOMovieGuide4Timer((BOMovieGuide)getTitelMap().get(modelIndex));			
		this.getTab().getTaEpisode().setText(HTML_ON+"<u>"+ControlMain.getProperty("txt_episode")+"</u> "+getBOMovieGuide4Timer().getEpisode()+HTML_OFF);					
		this.getTab().getTaGenre().setText(HTML_ON+"<u>"+ControlMain.getProperty("txt_genre")+"</u> "+getBOMovieGuide4Timer().getGenre()+HTML_OFF);		
		this.getTab().getTaAudioVideo().setText(HTML_ON+"<u>"+ControlMain.getProperty("txt_audio")+"</u> / <u>"+ControlMain.getProperty("txt_video")+"</u> "+HTML_OFF);
		this.getTab().getTaLand().setText(HTML_ON+"<u>"+ControlMain.getProperty("txt_prod")+"</u> "+getBOMovieGuide4Timer().getLand()+" / "+getBOMovieGuide4Timer().getJahr()+" / <u>"+ControlMain.getProperty("txt_regie")+"</u> "+getBOMovieGuide4Timer().getRegie()+HTML_OFF);													
		this.getTab().getTaDarsteller().setText(HTML_ON+"<u>"+ControlMain.getProperty("txt_darsteller")+"</u> "+getBOMovieGuide4Timer().getDarsteller()+HTML_OFF);
		this.getTab().getTaDarsteller().setCaretPosition(0);	
		this.getTab().getTaBeschreibung().setText(HTML_ON+"<u>"+ControlMain.getProperty("txt_inhalt")+"</u> "+getBOMovieGuide4Timer().getInhalt()+HTML_OFF);		
		this.getTab().getTaBeschreibung().setCaretPosition(0);
		setTimerTableSize(getBOMovieGuide4Timer().getDatum().size());
		reInitTimerTable();
	}

	/**
	 * @param senderName
	 * @return BOSender
	 * Gibts das aktuelle BOSender für den seletierter Timer zurück
	 * @throws IOException
	 */
	private BOSender getSenderObject(String senderName) throws IOException{
	    BOSender sender;
        ArrayList senderList = this.getBoxSenderList();
        for (int i=0; i<senderList.size(); i++) {
            sender = (BOSender)senderList.get(i);
            String boxSenderName = SerFormatter.replace(sender.getName()," ","");
            String mgSenderName = SerFormatter.replace(senderName," ","");
            if (boxSenderName.equals(mgSenderName)) {
                return sender;
            }
        }
        throw new IOException();
	}
	
	/**
	 * Erzeugt einen neuen Timer (BOTimer) aus der Selectierten Row(Titel) der TimerTable
	 */
	private void getTimerTableSelectToTimer(){
		try {
            int modelIndexTimer=getSelectRowTimerTable();
            String senderName = (String)getBOMovieGuide4Timer().getSender().get(modelIndexTimer);
            BOSender sender = this.getSenderObject(senderName);
            
            BOTimer botimer = new BOTimer();  	
            int timeBefore = Integer.parseInt(ControlMain.getSettings().getRecordTimeBefore())*-1;
            int timeAfter = Integer.parseInt(ControlMain.getSettings().getRecordTimeAfter());
            int timeAnnounce = (Integer.parseInt(ControlMain.getSettings().getRecordTimeBefore())+2)*60000;
            
            botimer.setModifiedId("new");
            botimer.setChannelId(sender.getChanId());
            botimer.setSenderName(sender.getName());		
            botimer.setUnformattedStartTime(SerFormatter.getGC((GregorianCalendar)getBOMovieGuide4Timer().getStart().get(modelIndexTimer),timeBefore));
            botimer.setUnformattedStopTime(SerFormatter.getGC((GregorianCalendar)getBOMovieGuide4Timer().getEnde().get(modelIndexTimer),timeAfter));			
            botimer.setAnnounceTime( String.valueOf(new GregorianCalendar().getTimeInMillis()-timeAnnounce));
            botimer.setEventRepeatId("0");
            botimer.setEventTypeId("5");
            botimer.setDescription(getBOMovieGuide4Timer().getTitel());
            try {
                ControlMain.getBoxAccess().writeTimer(botimer);
            } catch (IOException e) {
                SerAlertDialog.alertConnectionLost("ControlProgramTab", this.getMainView());
            }
        } catch (IOException e) {            
            Logger.getLogger("ControlMovieGuideTab").error(ControlMain.getProperty("error_sender"));
        }		
	}
	
    /**
     * @param size
     * Setzt die Größe für die TimerTable, die Größe kommt vom BOMovieGuide Object, wird bestimt
     * durch die Anzahl der SendeDaten des Films der in der FilmTable selectiert wurde.
     */
    private void setTimerTableSize(int size){
    	timerTableSize = size;
    }
    
    /**
     * @return
     * Gibt die Größe zurück die Aktuallisierung der TimerTable gebraucht wird
     */
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
		fc.setApproveButtonText("Auswählen");
		fc.setApproveButtonToolTipText("Datei auswählen");
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String path = fc.getSelectedFile().toString();		
			try{
				new SerMovieGuide2Xml(path, this.getMainView()).start();
			}catch (Exception e) {
				Logger.getLogger("ControlMovieGuideTab").error(ControlMain.getProperty("error_read_mg"));	
			}
		}
	}
	
	/**
	 * @return
	 * Gibt den Namen des aktuellen MovieGuides-Documents zurück.
	 */
	private File getMovieGuideFile(){
		return movieGuideFile;
	}
	
	/**
	 * @param filename
	 * Setzt den Namen des aktuellen MovieGuides-Documents
	 */
	private void setMovieGuideFile(File filename){
		movieGuideFile = filename;		
	}
	
	/**
	 * @throws DocumentException
	 * @throws MalformedURLException
	 * Setzt das RootElement für die movieguide.xml
	 */
	private void setRootElement()throws DocumentException, MalformedURLException {
		Document doc = SerXMLHandling.readDocument(getMovieGuideFile());
		root = doc.getRootElement();
	}
	
    /**
     * @return
     * @throws Exception
     * Gibt das aktuelle RootElement des aktuellen movieguide.xml zurück
     */
    private Element getRootElement() throws Exception{
    	return root;		
    }    
	 
    /**
     * @return
     * Gibt die ArrayList für die DatumComboBox zurück
     */
    public ArrayList getDatumList(){    	
    	return datumList;
    }
    
    /**
     * @param value
     * Setzt die ArrayList für die DatumComboBox, es wird geprüft ob das Datum schon in
     * der ArrayList ist, damit keine doppelten Eintrage rein kommen
     */
    private void setDatumList(String value){    	
    	if(!datumList.contains((String)value)){
    		datumList.add(value);    		
    	}    	
    }
    /**
     * @return
     * Gibt die ArrayList für die GenreComboBox zurück
     */
    public ArrayList getGenreList(){
    	return genreList;	
    }
    /**
     * @param value
     * Setzt die ArrayList für die GenreComboBox, es wird geprüft ob das Genre schon in
     * der ArrayList ist, damit keine doppelten Eintrage rein kommen
     */
    private void setGenreList(String value){
    	if(!genreList.contains(value) && value.length()>0){
    		genreList.add(value);
    	}
    }
    /**
     * @return
     * Gibt die ArrayList für die SenderComboBox zurück
     */
    public ArrayList getSenderList(){
    	return senderList;	
    }
    /**
     * @param value
     * Setzt die ArrayList für die SenderComboBox, es wird geprüft ob das Sender schon in
     * der ArrayList ist, damit keine doppelten Eintrage rein kommen
     */
    private void setSenderList(String value){
    	if(!senderList.contains(value) && value.length()>0){
    		senderList.add(value);
    	}
    }
    
    /**
     * @return
     * TitelListMap : Zähler, BOMovieGuide-Object
     * Gibt die aktulle TitelListMap zurück, die enthält immer die daten die entweder gesucht,
     * oder per DatumComboBox ausgewählt würden.
     */
    public HashMap getTitelMap(){
    	return titelListAktuell;		
    }    	           
   
    /**
     * @param key
     * @param value
     * Setzt einen Zähler und als value den Titel der von movieguide.xml kommt, der wird
     * zum Vergleich genutzt damit entschieden werden kann ob der Titel schon einmal gelesen
     * würde, wenn der Titel vorhanden ist, werden nur die zusätzlichen daten, wie SendeTermin
     * Uhrzeit usw. ins BOMovieGuide Objeckt geschrieben.
     */
    private void setControlMap(String key, Integer value){
    	if(!controlMap.containsKey(key)){
    		controlMap.put(key,value);
    	}
    }
   
    /**
     * @param bomovieguide
     * @param value
     * @param search
     * @param a
     * @return   
     * * Es wird die gelesene TitelMap(kompletter MovieGuide), nach dem aktuellen Suchkriterium
     * durchsucht, und in die titelListAktuell (Übergabe an die Methode setEntryInTitelMap)geschrieben, 
     * wenn das Suchkriteriem passt kommt das komplette BOMovieGuide Object in die Map. Es wird nur die Eigenschaft verglichen
     * die ausgewählt wurde, zb Genre, Darsteller usw.
     */
    private int doItSearch(BOMovieGuide bomovieguide ,String value, String search, int a){
    	if(value.toLowerCase().indexOf(search.toLowerCase())!=-1){    	
    		a = setEntryInTitelMap(bomovieguide,a);      		
		}		
    	return (a);
    }
    
    /**
     * @param bomovieguide
     * @param value
     * @param search
     * @param a
     * @return
     * * Es wird die gelesene TitelMap(kompletter MovieGuide), nach dem aktuellen Suchkriterium
     * durchsucht, und in die titelListAktuell (Übergabe an die Methode setEntryInTitelMap)geschrieben, 
     * wenn das Suchkriteriem passt kommt das komplette BOMovieGuide Object in die Map. Es wird nur die 
     * Eigenschaft verglichen die ausgewählt würde , aber nur wenn es sich dabei um eine Eigenschaft 
     * als AraryListe handelt, wie die SendeTermine Datum Startzeit usw.
     */
    private int doItSearchArray(BOMovieGuide bomovieguide ,ArrayList value, String search, int a){
    	if(bomovieguide.isValueInArray(value,search.toLowerCase())){
    		a = setEntryInTitelMap(bomovieguide,a);        		
		}
    	return (a);
    }
    
    /**
     * @param bomovieguide
     * @param a
     * @return
     * Die BOMovieGuide Objecte die zum Suchkriterium passen (die Objecte werden von den doIt-Methoden)
     * übergeben. die werden hier in die TitelListAktuell geschrieben.
     */
    private int setEntryInTitelMap(BOMovieGuide bomovieguide,int a){    	
		titelListAktuell.put(new Integer(a++),bomovieguide);
    	return (a++);
    }
    
    /**
     * @param searchValue
     * @param value
     * bauen der AnzeigeMap nach Suchkriterien  
     */
    public void setTitelMapSelected(Object searchValue,int value){    	
    	String search = (String)searchValue;
    	GregorianCalendar searchGC = new GregorianCalendar();
    	titelListAktuell = new HashMap();    	
    	Iterator i = titelList.entrySet().iterator();
    	int a = 0;   
    	if(value==1){
    		searchGC = SerFormatter.convString2GreCal(search,DATE_FULL);
    	}
    	while (i.hasNext()){
    		Map.Entry entry = (Map.Entry)i.next();
    		BOMovieGuide bomovieguide = (BOMovieGuide)entry.getValue();    		
    		switch (value){
    			case 1: //datum    				    				    			
    				if(bomovieguide.getDatum().contains(searchGC)){
    					a = setEntryInTitelMap(bomovieguide,a);		
    	    		}
    				break;
    			case 11: //genre
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
    			case 2:
					if(bomovieguide.getIfStringInObject(search)){
						a = setEntryInTitelMap(bomovieguide,a);						
					}
					break;
    			case 12: //sender    			
    				if(bomovieguide.getSender().contains(search)){
    					a = setEntryInTitelMap(bomovieguide,a);    					
    	    		}    	    		
    				break;    
    			case 13:
    					a = setEntryInTitelMap(bomovieguide,a);
    				break;
    		};    		    			
    	}    
    }
    
    /**
     * 
     */
    public void setTitelMap() {				    	    // einlesen der xml datei in Hashtable (key,BOMovieGuide Object)
    	if(this.getTitelMap()==null){				          	            			
    			titelList = new HashMap();
    			controlMap = new HashMap();
    	}    	
    	setGenreList(GENRE);
    	setSenderList(SENDER);
    	try {						
			for (Iterator i = root.elementIterator("entry"); i.hasNext();) {
				Element entry = (Element) i.next();						
				String sender = entry.element("sender").getStringValue();
				if( (aboList.contains(sender)) || (aboList.size()<=0)  ){			
								
				String datum = entry.element("datum").getStringValue();						
				if(SerFormatter.compareDates( datum,"today")) {				
				setDatumList(datum);
				String titel  = entry.element("titel").getStringValue();
				String dauer = entry.element("dauer").getStringValue();
				
				setSenderList(sender);
				if(!controlMap.containsKey(titel)){ //prüfen ob titel schon vorhanden ist, wenn ja nur neue Daten hinzugügen´			
				String start = entry.element("start").getStringValue();			
					BOMovieGuide bomovieguide = new BOMovieGuide(						
						sender,	
						SerFormatter.convString2GreCal(datum,DATE_FULL),
						SerFormatter.convString2GreCal(datum+","+start, DATE_FULL_TIME),
						SerFormatter.convString2GreCal(datum+","+SerFormatter.getCorrectEndTime(start,dauer), DATE_FULL_TIME),					
						titel,
						entry.element("episode").getStringValue(),
						entry.element("genre").getStringValue(),
						dauer,
						entry.element("land").getStringValue(),
						entry.element("jahr").getStringValue(),
						entry.element("regie").getStringValue(),
						entry.element("bild").getStringValue(),
						entry.element("ton").getStringValue(),
						entry.element("darsteller").getStringValue(),
						entry.element("inhalt").getStringValue()
				);				
				setGenreList(bomovieguide.getGenre());				
				titelList.put(new Integer(zaehler),bomovieguide);		
				setControlMap(bomovieguide.getTitel(),new Integer(zaehler));
				zaehler++;               
				}else{						 
				     String start = entry.element("start").getStringValue();
				     String ende  = SerFormatter.getCorrectEndTime(start,dauer);
				     BOMovieGuide bomovieguide = (BOMovieGuide)titelList.get(controlMap.get(titel));                     
					 bomovieguide.setDatum(SerFormatter.convString2GreCal(datum,DATE_FULL));
                     bomovieguide.setStart(SerFormatter.convString2GreCal(datum+","+start, DATE_FULL_TIME));
                     bomovieguide.setDauer(dauer);                                                  
                     bomovieguide.setSender(sender);
                     bomovieguide.setBild(entry.element("bild").getStringValue());
                     bomovieguide.setTon(entry.element("ton").getStringValue());
                     bomovieguide.setEnde(SerFormatter.convString2GreCal(datum+","+ende, DATE_FULL_TIME));	
                     titelList.put(controlMap.get(bomovieguide.getTitel()),bomovieguide);
				}
			}
				}
			}
    	}catch (Exception e) {
	           Logger.getLogger("ControlMovieGuideTab").error(ControlMain.getProperty("error_read_mg"));	
		}			
	}
    
    
    public JTable getJTableFilm() {
		return this.getTab().getJTableFilm();
	}
    
    /**
     * @return
     */
    public JTable getJTableTimer() {
		return this.getTab().getJTableTimer();
	}
    
    private GuiMovieGuideTimerTableModel getMovieGuideTimerTableModel() {
		return this.getTab().getGuiMovieGuideTimerTableModel();
	}
    
    /**
     * 
     */
    public void reInitTimerTable() {
		if (this.getMainView().getMainTabPane().tabMovieGuide != null) { 
			this.getMovieGuideTimerTableModel().fireTableDataChanged();
		}
	}
    
	/**
	 * @return
	 */
	private GuiMovieGuideFilmTableModel getMovieGuideFilmTableModel() {
		return this.getTab().getMovieGuideFilmTableModel();
	}
	
	/**
	 * @param value
	 */
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
	
    /**
     * @return Returns the boxSenderList.
     */
    public ArrayList getBoxSenderList() throws IOException {
        if (boxSenderList==null) {
            boxSenderList=ControlMain.getBoxAccess().getAllSender();
        }
        return boxSenderList;
    }
    
    /**
     * @param table
     * Anzeige aktualisieren der Inhalte, wenn in der TimerTable mit der Tastutur selectiert wurde
     */
    public void  timerTableChanged(JTable table) {
		if (table.getSelectedRow() > -1){			
			this.getTab().getTaAudioVideo().setText(HTML_ON+"<u>"+ControlMain.getProperty("txt_audio")+"</u> "+getBOMovieGuide4Timer().getTon().get(getSelectRowTimerTable())+" / <u>"+ControlMain.getProperty("txt_video")+"</u> "+getBOMovieGuide4Timer().getBild().get(getSelectRowTimerTable())+HTML_OFF);			
		}			
    }
    
    /**
     * @param table
     * Neu bauen der FilmTable bei Events neues Datum , Sorter usw.
     */
    public void filmTableChanged(JTable table) {
		if (table.getSelectedRow() > -1){
			reInitTable(new Integer(this.getTab().mgFilmTableSorter.modelIndex(table.getSelectedRow())));
			if(this.getTab().getTfSuche().getText().length()>=0){
				findAndReplaceGui(this.getTab().getTfSuche().getText());
			}
		}			
	}
    
    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    public void keyPressed(KeyEvent ke){   
    	if(ke.getKeyCode()==KeyEvent.VK_ENTER){
    		setSelectedItemJComboBox(this.getTab().getTfSuche().getText());
			if(getSelectedItemJComboBoxSucheNach()==0) {								
				reInitFilmTable(2);
			}else{					
				reInitFilmTable(getSelectedItemJComboBoxSucheNach());
			}
			getJTableFilm().getSelectionModel().setSelectionInterval(0,0);
    	}
    }
    public void keyTyped(KeyEvent ke){     
    }
    public void keyReleased(KeyEvent ke){     	
    }
    private BOSettings getSettings() {
        return ControlMain.getSettings();
    }
}