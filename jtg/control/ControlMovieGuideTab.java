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
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.BOMovieGuide;
import model.BOMovieGuideContainer;
import model.BOSender;
import model.BOSettingsMovieGuide;
import model.BOTimer;

import org.apache.log4j.Logger;

import presentation.GuiMainView;
import presentation.movieguide.GuiTabMovieGuide;
import presentation.movieguide.GuiMovieGuideFilmTableModel;
import presentation.movieguide.GuiMovieGuideTimerTableModel;
import service.SerAlertDialog;
import service.SerFormatter;
import service.SerMovieGuide2Xml;



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

	BOMovieGuideContainer movieList = new BOMovieGuideContainer();
	ArrayList titelListAktuell;
		
	ArrayList boxSenderList;
	
	String searchString = "";
	
	public static File movieGuideFile = new File("movieguide_"+SerFormatter.getAktuellDateString(0,"MM_yy")+".xml");
	public static File movieGuideFileNext = new File("movieguide_"+SerFormatter.getAktuellDateString(1,"MM_yy")+".xml");	
	
	private static final String DATE_FULL = "EEEE, dd. MMMM yyyy";
	private static final String GENRE  = ControlMain.getProperty("txt_genre2");
	private static final String SENDER = ControlMain.getProperty("txt_sender2");
	private static final String GET_FORMAT_GRE_CAL = SerFormatter.getFormatGreCal();
	private static final String GET_AKTUELL_DATE_STRING_0 = SerFormatter.getAktuellDateString(0,"MMMM");
	private static final String GET_AKTUELL_DATE_STRING_1 = SerFormatter.getAktuellDateString(1,"MMMM"); 
		
	String SelectedItemJComboBox;
	int SelectedItemJComboBoxSucheNach;
    int timerTableSize;    
	public ControlMovieGuideTab(GuiMainView view) {		
		this.setMainView(view);						
	}
	
	public void run() {
          this.setTab((GuiTabMovieGuide)this.getMainView().getTabMovieGuide());          
          if(getSettings().getMgLoadType()==0){          	
          	if(SerMovieGuide2Xml.checkNewMovieGuide()&& (!movieGuideFileNext.exists())){
          		SerAlertDialog.alert(ControlMain.getProperty("txt_mg_info1")+GET_AKTUELL_DATE_STRING_1+ControlMain.getProperty("txt_mg_info2"),this.getMainView()); 					
          	}else{
          		if( (SerMovieGuide2Xml.checkNewMovieGuide()) && (!movieGuideFileNext.exists())){
        			try{
        				new SerMovieGuide2Xml(null, this.getMainView()).start();
        			}catch (Exception ex){
        				Logger.getLogger("ControlMovieGuideTab").error(ControlMain.getProperty("error_not_download"));
        			}
        		}
          		if( (!movieGuideFile.exists())){
          			SerAlertDialog.alert(ControlMain.getProperty("txt_mg_info1")+GET_AKTUELL_DATE_STRING_0+" "+ControlMain.getProperty("txt_mg_info2"),this.getMainView());
        			try{
        				new SerMovieGuide2Xml(null, this.getMainView()).start();
        			}catch (Exception ex){
        				Logger.getLogger("ControlMovieGuideTab").error(ControlMain.getProperty("error_not_download"));
        			}
        		}
          	}
          }
          if(getSettings().getMgLoadType()==1 && (!movieGuideFile.exists())){
          	SerAlertDialog.alert(ControlMain.getProperty("txt_mg_info1")+GET_AKTUELL_DATE_STRING_0+" "+ControlMain.getProperty("txt_mg_info2"),this.getMainView());
          }
          if(this.getTitelMap()==null && (movieGuideFile.exists())){				          	
          	movieList.importXML(movieGuideFile,getSettings().getMgSelectedChannels());	  
          	beautifyGui(); 
          }           
          if(movieGuideFileNext.exists()){          	
          	setMovieGuideFile(movieGuideFileNext);          	
          	movieList.importXML(movieGuideFileNext,getSettings().getMgSelectedChannels());
          	beautifyGui(); 
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
			setTitelMapSelected(GET_FORMAT_GRE_CAL,13);   // TitelMap Alles      
		}else{
			setTitelMapSelected(GET_FORMAT_GRE_CAL,1);  // TitelMap für den heutigen Tag   
			this.getTab().getComboBoxDatum().setSelectedItem(GET_FORMAT_GRE_CAL);	 
		}              
        this.getTab().getComboBoxGenre().setSelectedIndex(0);          
        this.getTab().getComboBoxSender().setSelectedIndex(0);
        try{
        	this.getTab().mgFilmTableSorter.setSortingStatus(0,2); //alphabetisch geordnet
        	getJTableFilm().getSelectionModel().setSelectionInterval(0,0); //1 Row selected
        }catch(ArrayIndexOutOfBoundsException ex){System.out.println(ex);}
   }
	
	
	private void downloadMovieGuide(){
		if(getMovieGuideFile().exists()){
			if(SerMovieGuide2Xml.checkNewMovieGuide()){
				SerAlertDialog.alert(ControlMain.getProperty("txt_mg_info1")+GET_AKTUELL_DATE_STRING_1+ControlMain.getProperty("txt_mg_info2"),this.getMainView()); 					
          	}else{
          		SerAlertDialog.alert(ControlMain.getProperty("txt_mg_info3")+GET_AKTUELL_DATE_STRING_0+".\n"+ControlMain.getProperty("txt_mg_info4")+GET_AKTUELL_DATE_STRING_1+" "+ControlMain.getProperty("txt_mg_info5"),this.getMainView());
          	}
		}else{				
			try{
				new SerMovieGuide2Xml(null, this.getMainView()).start();
			}catch (Exception ex){
				Logger.getLogger("ControlMovieGuideTab").error(ControlMain.getProperty("error_not_download"));
			}		
		}
		if( (SerMovieGuide2Xml.checkNewMovieGuide()) && (!movieGuideFileNext.exists())){
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
		if ( (action == "suchen") || (action == "textsuche") ) {		
			if(this.getTitelMap()!=null){
			setSelectedItemJComboBox(this.getTab().getTfSuche().getText());
			searchString = getSelectedItemJComboBox();
			if(getSelectedItemJComboBoxSucheNach()==0) {								
				reInitFilmTable(2);
			}else{					
				reInitFilmTable(getSelectedItemJComboBoxSucheNach());
			}		
			getJTableFilm().getSelectionModel().setSelectionInterval(0,0);		
			getJTableFilm().scrollRectToVisible(getJTableFilm().getCellRect(1,1,true));
			findAndReplaceGui(getSelectedItemJComboBox());
			}
		}
		if (action == "allDates") {	
			if(this.getTitelMap()!=null){
				this.getTab().getTfSuche().setText("");
				searchString = "";
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
				searchString = "";
				this.getTab().getComboBoxGenre().setSelectedIndex(0);          
				this.getTab().getComboBoxSender().setSelectedIndex(0); 
				setSelectedItemJComboBox(comboBox.getSelectedItem().toString());
				reInitFilmTable(1);						
				getJTableFilm().getSelectionModel().setSelectionInterval(0,0);	
				getJTableFilm().scrollRectToVisible(getJTableFilm().getCellRect(1,1,true));
			}
		}
		if (action == "clickONGenreComboBox"){
			JComboBox comboBox = this.getTab().getComboBoxGenre();
			if(comboBox.getItemCount()>=1){
				this.getTab().getTfSuche().setText("");
				searchString = "";
				if(!comboBox.getSelectedItem().toString().equals(GENRE)){
					setSelectedItemJComboBox(comboBox.getSelectedItem().toString());
					reInitFilmTable(11);		
					getJTableFilm().getSelectionModel().setSelectionInterval(0,0);
					getJTableFilm().scrollRectToVisible(getJTableFilm().getCellRect(1,1,true));
				}
			}
		}
		if (action == "clickONSenderComboBox"){
			JComboBox comboBox = this.getTab().getComboBoxSender();
			if(comboBox.getItemCount()>=1){
				this.getTab().getTfSuche().setText("");
				searchString = "";
				if(!comboBox.getSelectedItem().toString().equals(SENDER)){
					setSelectedItemJComboBox(comboBox.getSelectedItem().toString());
					reInitFilmTable(12);
					getJTableFilm().getSelectionModel().setSelectionInterval(0,0);
					getJTableFilm().scrollRectToVisible(getJTableFilm().getCellRect(1,1,true));
				}
			}
		}
	}
	
	private void findAndReplaceGui(String search){			
		if(search.length()>=1){
			SerFormatter.highlight(this.getTab().getTaEpisode(),search);								
			SerFormatter.highlight(this.getTab().getTaGenre(),search);		
			SerFormatter.highlight(this.getTab().getTaAudioVideo(),search);
			SerFormatter.highlight(this.getTab().getTaLand(),search);													
			SerFormatter.highlight(this.getTab().getTaDarsteller(),search);
			this.getTab().getTaDarsteller().setCaretPosition(0);		
			SerFormatter.highlight(this.getTab().getTaBeschreibung(),search);		
			this.getTab().getTaBeschreibung().setCaretPosition(0);								
		}else{
			SerFormatter.removeHighlights(this.getTab().getTaEpisode());								
			SerFormatter.removeHighlights(this.getTab().getTaGenre());		
			SerFormatter.removeHighlights(this.getTab().getTaAudioVideo());
			SerFormatter.removeHighlights(this.getTab().getTaLand());													
			SerFormatter.removeHighlights(this.getTab().getTaDarsteller());
			this.getTab().getTaDarsteller().setCaretPosition(0);		
			SerFormatter.removeHighlights(this.getTab().getTaBeschreibung());		
			this.getTab().getTaBeschreibung().setCaretPosition(0);
		}
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
				searchString = "";
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
				searchString = "";
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
			this.getTab().getTaAudioVideo().setText("");			
			SerFormatter.underScore(this.getTab().getTaAudioVideo()," "+getBOMovieGuide4Timer().getTon().get(getSelectRowTimerTable())+" ",false,0);			
			SerFormatter.underScore(this.getTab().getTaAudioVideo(), ControlMain.getProperty("txt_video"),true,this.getTab().getTaAudioVideo().getText().length());
			SerFormatter.underScore(this.getTab().getTaAudioVideo()," "+getBOMovieGuide4Timer().getBild().get(getSelectRowTimerTable()),false,this.getTab().getTaAudioVideo().getText().length());
			SerFormatter.underScore(this.getTab().getTaAudioVideo(), ControlMain.getProperty("txt_audio"),true,0);
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
		setBOMovieGuide4Timer((BOMovieGuide)getTitelMap().get(modelIndex.intValue()));					
		clearAllTextArea();
		
		SerFormatter.underScore(this.getTab().getTaEpisode()," "+getBOMovieGuide4Timer().getEpisode(),false,0);
		SerFormatter.underScore(this.getTab().getTaEpisode(),ControlMain.getProperty("txt_episode"),true,0);		
								
		SerFormatter.underScore(this.getTab().getTaGenre()," "+getBOMovieGuide4Timer().getGenre(),false,0);
		SerFormatter.underScore(this.getTab().getTaGenre(),ControlMain.getProperty("txt_genre"),true,0);		
				
		SerFormatter.underScore(this.getTab().getTaDarsteller()," "+getBOMovieGuide4Timer().getDarsteller(),false,0);
		SerFormatter.underScore(this.getTab().getTaDarsteller(),ControlMain.getProperty("txt_darsteller"),true,0);
		this.getTab().getTaDarsteller().setCaretPosition(0);				
				
		SerFormatter.underScore(this.getTab().getTaBeschreibung()," "+getBOMovieGuide4Timer().getInhalt(),false,0);
		SerFormatter.underScore(this.getTab().getTaBeschreibung(), ControlMain.getProperty("txt_inhalt"),true,0);
		this.getTab().getTaBeschreibung().setCaretPosition(0);
				
		SerFormatter.underScore(this.getTab().getTaAudioVideo(), ControlMain.getProperty("txt_audio"),true,0);
		SerFormatter.underScore(this.getTab().getTaAudioVideo()," / ",false,ControlMain.getProperty("txt_audio").length());
		SerFormatter.underScore(this.getTab().getTaAudioVideo(), ControlMain.getProperty("txt_video"),true,ControlMain.getProperty("txt_audio").length()+3);
							
		SerFormatter.underScore(this.getTab().getTaLand()," "+getBOMovieGuide4Timer().getLand()+" / "+getBOMovieGuide4Timer().getJahr()+" / ",false,0);
		SerFormatter.underScore(this.getTab().getTaLand(), ControlMain.getProperty("txt_prod"),true,0);				
		SerFormatter.underScore(this.getTab().getTaLand(), ControlMain.getProperty("txt_regie"),true,this.getTab().getTaLand().getText().length());
		SerFormatter.underScore(this.getTab().getTaLand()," "+getBOMovieGuide4Timer().getRegie(),false,this.getTab().getTaLand().getText().length());																
		
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
            int timeBefore = Integer.parseInt(ControlMain.getSettingsRecord().getRecordTimeBefore())*-1;
            int timeAfter = Integer.parseInt(ControlMain.getSettingsRecord().getRecordTimeAfter());
            int timeAnnounce = (Integer.parseInt(ControlMain.getSettingsRecord().getRecordTimeBefore())+2)*60000;
            
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
	public ArrayList getSenderList(){
		ArrayList senderList = new ArrayList();
		senderList.add(SENDER);
		senderList.addAll(movieList.getSenderList());
    	return senderList;
    }
	public ArrayList getGenreList(){
		ArrayList genreList = new ArrayList();
		genreList.add(GENRE);
		genreList.addAll(movieList.getGenreList());
    	return genreList;	
    }
	public ArrayList getDatumList(){    	
    	return movieList.getDatumList();
    }
    /**
     * @return
     * TitelListMap : Zähler, BOMovieGuide-Object
     * Gibt die aktulle TitelListMap zurück, die enthält immer die daten die entweder gesucht,
     * oder per DatumComboBox ausgewählt würden.
     */
    public ArrayList getTitelMap(){
    	return titelListAktuell;		
    }    	           
    
    /**
     * @param searchValue
     * @param value
     * bauen der AnzeigeMap nach Suchkriterien  
     */
    public void setTitelMapSelected(Object searchValue,int value){    	
    	String search = (String)searchValue;
    	GregorianCalendar searchGC = new GregorianCalendar();	    	 
    	if(value==1){
    		searchGC = SerFormatter.convString2GreCal(search,DATE_FULL);
    	}    	
		switch (value){
			case 1: //datum    	
				titelListAktuell=movieList.searchByDate(searchGC);				
				break;			
			case 2: //in allen
				titelListAktuell=movieList.searchByAll(search);  
				break;				
			case 3: //titel
				titelListAktuell=movieList.searchByTitle(search);       				
				break;
			case 4: //darsteller
				titelListAktuell=movieList.searchByActor(search);       				
				break;
			case 5: // episode
				titelListAktuell=movieList.searchByEpisode(search);      				
				break;
			case 6: // bild    			
				titelListAktuell=movieList.searchByPicture(search);       				
				break; 				
			case 7: // ton
				titelListAktuell=movieList.searchByAudio(search);       				
				break;
			case 8: // Prodland
				titelListAktuell=movieList.searchByCountry(search);      				
				break;
			case 9: //Prodjahr
				titelListAktuell=movieList.searchByYear(search);       				
				break;
			case 10: //regie
				titelListAktuell=movieList.searchByRegie(search);       				
				break;
			case 11: //genre
				titelListAktuell=movieList.searchByGenre(search);    				
				break;				
			case 12: //sender    			
				titelListAktuell=movieList.searchBySender(search);    	    		
				break;    				
			case 13: // ALLES kopletten mg					
					titelListAktuell = movieList.getAllMovies();  
				break;
		};    		
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
			this.getTab().getTaAudioVideo().setText("");			
			SerFormatter.underScore(this.getTab().getTaAudioVideo()," "+getBOMovieGuide4Timer().getTon().get(getSelectRowTimerTable())+" ",false,0);			
			SerFormatter.underScore(this.getTab().getTaAudioVideo(), ControlMain.getProperty("txt_video"),true,this.getTab().getTaAudioVideo().getText().length());
			SerFormatter.underScore(this.getTab().getTaAudioVideo()," "+getBOMovieGuide4Timer().getBild().get(getSelectRowTimerTable()),false,this.getTab().getTaAudioVideo().getText().length());
			SerFormatter.underScore(this.getTab().getTaAudioVideo(), ControlMain.getProperty("txt_audio"),true,0);
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
    private BOSettingsMovieGuide getSettings() {
        return ControlMain.getSettingsMovieGuide();
    }
    
    private void clearAllTextArea(){
    	this.getTab().getTaLand().setText("");	
		this.getTab().getTaAudioVideo().setText("");
		this.getTab().getTaBeschreibung().setText("");
		this.getTab().getTaDarsteller().setText("");
		this.getTab().getTaGenre().setText("");
		this.getTab().getTaEpisode().setText("");
    }    
    public String getSearchString(){
    	return searchString;
    }
}