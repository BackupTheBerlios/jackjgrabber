/*
 * Created on 10.12.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.io.File;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentException;

import control.ControlMain;

import java.net.MalformedURLException;

import service.SerFormatter;
import service.SerXMLHandling;

import model.BOMovieGuide;

/**
 * @author ralph
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BOMovieGuideContainer{
	Hashtable titelList = new Hashtable();	
	ArrayList returnList = new ArrayList();
	
	ArrayList genreList = new ArrayList();
	ArrayList datumList = new ArrayList();
	ArrayList senderList = new ArrayList();
	
	private static final String DATE_FULL = "EEEE, dd. MMMM yyyy";
	private static final String DATE_FULL_TIME = "EEEE, dd. MMMM yyyy,HH:mm";	
	
	public int importXML(File file, ArrayList aboList){		
		Document doc = null;
		try{
		doc = SerXMLHandling.readDocument(file);
		}catch(DocumentException DoEx){
			DoEx.printStackTrace();	
		}
		 catch(MalformedURLException MEx){
		    Logger.getLogger("ControlMovieGuideTab").error(file.getName()+" not found");	
		 }
		Element root = doc.getRootElement();
		//clear();
		try {						
			for (Iterator i = root.elementIterator("entry"); i.hasNext();) {				
				Element entry = (Element) i.next();										
				String sender = entry.element("sender").getStringValue();				
				if( !(aboList.contains(sender)) && !aboList.isEmpty() ) continue;
				String datum = entry.element("datum").getStringValue();						
				if(!SerFormatter.compareDates( datum,"today")) continue;			
				setDatumList(datum);
				String titel  = entry.element("titel").getStringValue();
				String dauer = entry.element("dauer").getStringValue();				
				setSenderList(sender);
				BOMovieGuide bomovieguide = (BOMovieGuide)titelList.get(titel);
				if(bomovieguide == null){
						String start = entry.element("start").getStringValue();			
						bomovieguide = new BOMovieGuide(						
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
						titelList.put(titel,bomovieguide);			
				}else{						 
				     String start = entry.element("start").getStringValue();
				     String ende  = SerFormatter.getCorrectEndTime(start,dauer);
				     bomovieguide = (BOMovieGuide)titelList.get(titel);                     
					 bomovieguide.setDatum(SerFormatter.convString2GreCal(datum,DATE_FULL));
                     bomovieguide.setStart(SerFormatter.convString2GreCal(datum+","+start, DATE_FULL_TIME));
                     bomovieguide.setDauer(dauer);                                                  
                     bomovieguide.setSender(sender);
                     bomovieguide.setBild(entry.element("bild").getStringValue());
                     bomovieguide.setTon(entry.element("ton").getStringValue());
                     bomovieguide.setEnde(SerFormatter.convString2GreCal(datum+","+ende, DATE_FULL_TIME));	
				}
			}		
    	}catch (Exception e) {
	           Logger.getLogger("ControlMovieGuideTab").error(ControlMain.getProperty("error_read_mg"));	
		}			
    	Collections.sort(getSenderList());		//alphabetisch geordnet 
        Collections.sort(getGenreList());		//alphabetisch geordnet
		return titelList.size();
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
	private void clear(){
		titelList.clear();
		genreList.clear();
		datumList.clear();
		senderList.clear();
	}
	
	public ArrayList searchByDate(GregorianCalendar search){
		returnList.clear();
		Iterator i = titelList.entrySet().iterator();
		while(i.hasNext()){
			BOMovieGuide bomovieguide = (BOMovieGuide)((Map.Entry)i.next()).getValue();
			if(bomovieguide.getDatum().contains(search)){
			    returnList.add(bomovieguide);
			}
		}
		return returnList;
	}
	public ArrayList searchByGenre(String search){
		returnList.clear();
		Iterator i = titelList.entrySet().iterator();
		while(i.hasNext()){
			BOMovieGuide bomovieguide = (BOMovieGuide)((Map.Entry)i.next()).getValue();			
			if(bomovieguide.getGenre().toLowerCase().indexOf(search.toLowerCase())!=-1){    
				returnList.add(bomovieguide);    
			}
		}
		return returnList;
	}
	public ArrayList searchByTitle(String search){
		returnList.clear();
		Iterator i = titelList.entrySet().iterator();
		while(i.hasNext()){
			BOMovieGuide bomovieguide = (BOMovieGuide)((Map.Entry)i.next()).getValue();			
			if(bomovieguide.getTitel().toLowerCase().indexOf(search.toLowerCase())!=-1){    
				returnList.add(bomovieguide);    
			}
		}
		return returnList;
	}
	public ArrayList searchByActor(String search){
		returnList.clear();
		Iterator i = titelList.entrySet().iterator();
		while(i.hasNext()){
			BOMovieGuide bomovieguide = (BOMovieGuide)((Map.Entry)i.next()).getValue();			
			if(bomovieguide.getDarsteller().toLowerCase().indexOf(search.toLowerCase())!=-1){    
				returnList.add(bomovieguide);    
			}
		}
		return returnList;
	}
	public ArrayList searchByEpisode(String search){
		returnList.clear();
		Iterator i = titelList.entrySet().iterator();
		while(i.hasNext()){
			BOMovieGuide bomovieguide = (BOMovieGuide)((Map.Entry)i.next()).getValue();			
			if(bomovieguide.getEpisode().toLowerCase().indexOf(search.toLowerCase())!=-1){    
				returnList.add(bomovieguide);    
			}
		}
		return returnList;
	}
	public ArrayList searchByCountry(String search){
		returnList.clear();
		Iterator i = titelList.entrySet().iterator();
		while(i.hasNext()){
			BOMovieGuide bomovieguide = (BOMovieGuide)((Map.Entry)i.next()).getValue();			
			if(bomovieguide.getLand().toLowerCase().indexOf(search.toLowerCase())!=-1){    
				returnList.add(bomovieguide);    
			}
		}
		return returnList;
	}
	public ArrayList searchByYear(String search){
		returnList.clear();
		Iterator i = titelList.entrySet().iterator();
		while(i.hasNext()){
			BOMovieGuide bomovieguide = (BOMovieGuide)((Map.Entry)i.next()).getValue();			
			if(bomovieguide.getJahr().toLowerCase().indexOf(search.toLowerCase())!=-1){    
				returnList.add(bomovieguide);    
			}
		}
		return returnList;
	}
	public ArrayList searchByRegie(String search){
		returnList.clear();
		Iterator i = titelList.entrySet().iterator();
		while(i.hasNext()){
			BOMovieGuide bomovieguide = (BOMovieGuide)((Map.Entry)i.next()).getValue();			
			if(bomovieguide.getRegie().toLowerCase().indexOf(search.toLowerCase())!=-1){    
				returnList.add(bomovieguide);    
			}
		}
		return returnList;
	}
	public ArrayList searchBySender(String search){
		returnList.clear();
		Iterator i = titelList.entrySet().iterator();
		while(i.hasNext()){
			BOMovieGuide bomovieguide = (BOMovieGuide)((Map.Entry)i.next()).getValue();						
			 if(isValueInArray(bomovieguide.getSender(),search.toLowerCase())){
			 	returnList.add(bomovieguide);    
			}
		}
		return returnList;
	}
	public ArrayList searchByPicture(String search){
		returnList.clear();
		Iterator i = titelList.entrySet().iterator();
		while(i.hasNext()){
			BOMovieGuide bomovieguide = (BOMovieGuide)((Map.Entry)i.next()).getValue();						
			 if(isValueInArray(bomovieguide.getBild(),search.toLowerCase())){
			 	returnList.add(bomovieguide);    
			}
		}
		return returnList;
	}
	
	public ArrayList searchByAudio(String search){
		returnList.clear();
		Iterator i = titelList.entrySet().iterator();
		while(i.hasNext()){
			BOMovieGuide bomovieguide = (BOMovieGuide)((Map.Entry)i.next()).getValue();						
			 if(isValueInArray(bomovieguide.getTon(),search.toLowerCase())){
			 	returnList.add(bomovieguide);    
			}
		}
		return returnList;
	}
	public ArrayList getAllMovies(){
		returnList.clear();
		Iterator i = titelList.entrySet().iterator();
		while(i.hasNext()){
			returnList.add((BOMovieGuide)((Map.Entry)i.next()).getValue());
		}
		return returnList;
	}
	
	public ArrayList searchByAll(String search){
		returnList.clear();
		Iterator i = titelList.entrySet().iterator();
		while(i.hasNext()){
			BOMovieGuide bomovieguide = (BOMovieGuide)((Map.Entry)i.next()).getValue();						
			if(getIfStringInObject(bomovieguide,search)){
				returnList.add(bomovieguide); 
			}
		}
		return returnList;
	}
	
	
	public boolean isValueInArray(ArrayList value,String search){
    	boolean retVal = false;
    	for (Iterator i = value.iterator(); i.hasNext();) {   			
    			if(i.next().toString().toLowerCase().indexOf(search)>=0){
    				retVal = true;
    			}
    	}
    	return retVal;
    }
	public boolean getIfStringInObject(BOMovieGuide bomovieguide, String search){
    	boolean value = false;
    	search = search.toLowerCase();
    	if (bomovieguide.getGenre().toLowerCase().indexOf(search)>=0){
    		value = true;
    	}else if(bomovieguide.getDarsteller().toLowerCase().indexOf(search)>=0){
    		value = true;
    	}else if(bomovieguide.getEpisode().toLowerCase().indexOf(search)>=0){
    		value = true;
    	}else if(bomovieguide.getInhalt().toLowerCase().indexOf(search)>=0){
    		value = true;    	
    	}else if(bomovieguide.getLand().toLowerCase().indexOf(search)>=0){
    		value = true;
    	}else if(bomovieguide.getJahr().toLowerCase().indexOf(search)>=0){
    		value = true;
    	}else if(isValueInArray(bomovieguide.getBild(),search.toLowerCase())){
    		value = true;
    	}else if(isValueInArray(bomovieguide.getTon(),search.toLowerCase())){
    		value = true;
    	}else if(bomovieguide.getRegie().toLowerCase().indexOf(search)>=0){
    		value = true;
    	}else if(bomovieguide.getTitel().toLowerCase().indexOf(search)>=0){
    		value = true;
    	}
    	return value;
    }
}
