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
//import java.io.BufferedReader;
import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

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

//import model.BOMovieGuide;

import presentation.GuiMainView;
import presentation.MovieGuideFilmTableModel;
import service.SerXMLHandling;


public class ControlMovieGuideTab extends ControlTab implements ActionListener,ItemListener, MouseListener,ChangeListener  {

	GuiMainView mainView;
	
	static Hashtable objectList = new Hashtable();		
	static Hashtable titelList = new Hashtable();
	static Hashtable timerList = new Hashtable();
	
	static ArrayList genreList = new ArrayList();
	static ArrayList datumList = new ArrayList();
	public static String[] elementListe = {"datum", "titel", "episode","genre", "land", "jahr", "regie", "ton", "bild", "darsteller", "inhalt"};
	public static String[] datumListe = {"datum", "start", "dauer","sender"};
	
	private static Element root;
	private static Document movieGuideDocument;

	public static String movieGuideFileName = "movieguide.xml";
	

	public ControlMovieGuideTab(GuiMainView view) {
		this.setMainView(view);
		try{
		setRootElement();
		}catch (Exception ex){}
		setTitelMap();
		this.initialize();
	}

	public void initialize() {							
		try{
			//setRootElement();
			//setTitelMap();
		}catch (Exception ex){}
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
		//int selectedRow = table.getSelectedRow();
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
	
	public static Document getMovieGuideDocument() {
		return movieGuideDocument;
	}

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
    	if(!genreList.contains(value)){
    		genreList.add(value);
    	}
    }
    
    public Hashtable getTitelMap(){
    	return titelList;		
    }    	
    
    ArrayList[] valueList = new ArrayList[10];
    /*
    public static Hashtable setTimerMap(String titel) throws Exception{
    //	timerList.clear();    
    	int a = 0;
        for ( Iterator i = root.elementIterator("entry"); i.hasNext(); ) {
            Element entry = (Element) i.next();
            setDatumList(entry.element(datumListe[0]).getStringValue()); //datum
                for ( Iterator d = entry.elementIterator("titel"); d.hasNext(); ) {
                    Element entry2 = (Element) d.next();
                    if(entry2.getStringValue().equals(titel)){                    	
                    	String value = entry.element(datumListe[1]).getStringValue(); //titel				
        				if(!titelList.containsValue(value)){  //jeder Titel nur 1x in die Hashtable
        					titelList.put(new Integer(a), (String)value);
        						StringBuffer objectValue = new StringBuffer();						
        						for(int x=2;x<=10;x++){
        							objectValue.append(entry.element(elementListe[x]).getStringValue());
        							objectValue.append(" |");
        							if(x==3){
        								setGenreList(entry.element(elementListe[x]).getStringValue());
        							}
        						}											
        						objectList.put(new Integer(a),objectValue);					
        						a++;
        				}         
                    }
                }                     
        }
        
        return titelList;
    }
    */
    
    /*
    public static Hashtable setTitelMap(String datum) throws Exception{
    	titelList.clear();    
    	int a = 0;
        for ( Iterator i = root.elementIterator("entry"); i.hasNext(); ) {
            Element entry = (Element) i.next();
            setDatumList(entry.element(elementListe[0]).getStringValue()); //datum
                for ( Iterator d = entry.elementIterator("datum"); d.hasNext(); ) {
                    Element entry2 = (Element) d.next();
                    if(entry2.getStringValue().equals(datum)){                    	
                    	String value = entry.element(elementListe[1]).getStringValue(); //titel				
        				if(!titelList.containsValue(value)){  //jeder Titel nur 1x in die Hashtable
        					titelList.put(new Integer(a), (String)value);
        						StringBuffer objectValue = new StringBuffer();						
        						for(int x=2;x<=10;x++){
        							objectValue.append(entry.element(elementListe[x]).getStringValue());
        							objectValue.append(" |");
        							if(x==3){
        								setGenreList(entry.element(elementListe[x]).getStringValue());
        							}
        						}											
        						objectList.put(new Integer(a),objectValue);					
        						a++;
        				}         
                    }
                }                     
        }
        return titelList;
    }
    */   
    static Hashtable controlDatumMap = new Hashtable();
    
    private static void setControlDatumMap(String key, Integer value){
    	if(!controlDatumMap.containsKey(key)){
    		controlDatumMap.put(key,value);
    	}
    }
    public static void setTitelMap() {				    	    
    	try {
			int a = 0;			
			for (Iterator i = root.elementIterator("entry"); i.hasNext();) {
				Element entry = (Element) i.next();		
				
				setDatumList(entry.element("datum").getStringValue());
				setControlDatumMap(entry.element("datum").getStringValue(),new Integer(a));		
				System.out.println(controlDatumMap.get(entry.element("datum").getStringValue()));
				
				setGenreList(entry.element("genre").getStringValue());
				if(!controlDatumMap.containsKey(entry.element("titel").getStringValue())){
					BOMovieGuide bomovieguide = new BOMovieGuide(
							entry.element("sender").getStringValue(), 
							entry.element("datum").getStringValue(),
							entry.element("start").getStringValue(),
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
					titelList.put(new Integer(a),bomovieguide);									
	                a++;     
				}else{							
					BOMovieGuide bomovieguide = (BOMovieGuide)titelList.get(controlDatumMap.get(entry.element("titel").getStringValue()));
					bomovieguide.setDatum(entry.element("datum").getStringValue());
					bomovieguide.setStart(entry.element("start").getStringValue());
					bomovieguide.setDauer(entry.element("dauer").getStringValue());
					bomovieguide.setSender(entry.element("sender").getStringValue());
					titelList.put(titelList.get(controlDatumMap.get(entry.element("titel").getStringValue())),bomovieguide);	
				}
			}
		} catch (Exception ex) {System.out.println(ex);}				
	}
    
    
    /*
	public static void setTitelMap() {				
		try {
			int a = 0;			
			for (Iterator i = root.elementIterator("entry"); i.hasNext();) {
				Element entry = (Element) i.next();								 									
				setDatumList(entry.element(elementListe[0]).getStringValue()); //datum				
				String value = entry.element(elementListe[1]).getStringValue(); //titel				
				if(!titelList.containsValue(value)){  //jeder Titel nur 1x in die Hashtable
					titelList.put(new Integer(a), (String)value);
						StringBuffer objectValue = new StringBuffer();						
						for(int x=2;x<=10;x++){
							objectValue.append(entry.element(elementListe[x]).getStringValue());
							objectValue.append(" |");
							if(x==3){
								setGenreList(entry.element(elementListe[x]).getStringValue());
							}
						}											
						objectList.put(new Integer(a),objectValue);					
						a++;
				}												
			}
		} catch (Exception ex) {}			
	}
*/	
	private JTable getJTableFilm() {
		return this.getMainView().getTabMovieGuide().getJTableFilm();
	}
	
	private MovieGuideFilmTableModel getMovieGuideFilmTableModel() {
		return this.getMainView().getTabMovieGuide().getMovieGuideFilmTableModel();
	}
}