/*
 * Created on 21.10.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package model;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;

/**
 * @author ralix
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BOMovieGuide {
	
	private static final String[] LIST_ENTRYS = {"Alles","Titel","Darsteller","Episode","Bild","Ton","Produktionsland","Produktionsjahr","Regie"};
	private static final String[] ABO_ENTRYS = {"PREMIERE START","PREMIERE NOSTALGIE"};
	/**
     * Holds value of property sender.
     */
    private ArrayList sender;
    
    /**
     * Holds value of property datum.
     */
    private ArrayList datum;
    
    /**
     * Holds value of property start.
     */
    private ArrayList start;
    
    /**
     * Holds value of property start.
     */
    private ArrayList ende;
    
    /**
     * Holds value of property titel.
     */
    private String titel;
    
    /**
     * Holds value of property episode.
     */
    private String episode;
    
    /**
     * Holds value of property genre.
     */
    private String genre;
    
    /**
     * Holds value of property dauer.
     */
    private ArrayList dauer;
    
    /**
     * Holds value of property land.
     */
    private String land;
    
    /**
     * Holds value of property jahr.
     */
    private String jahr;
    
    /**
     * Holds value of property regie.
     */
    private String regie;
    
    /**
     * Holds value of property bild.
     */
    private ArrayList bild;
    
    /**
     * Holds value of property ton.
     */
    private ArrayList ton;
    
    /**
     * Holds value of property darsteller.
     */
    private String darsteller;
    
    /**
     * Holds value of property inhalt.
     */
    private String inhalt;
  
    private ArrayList sucheList = new ArrayList();
    private ArrayList aboList = new ArrayList();
    
    /** Creates a new instance of BOMovieGuide */
    
    public BOMovieGuide() {  
    	
    }
    public BOMovieGuide(String sender, GregorianCalendar datum , GregorianCalendar start, GregorianCalendar ende, String titel, String episode,
            String genre, String dauer, String land, String jahr, String regie,
            String bild, String ton, String darsteller, String inhalt){
    		this.sender = new ArrayList();
    		this.datum  = new ArrayList();
    		this.start  = new ArrayList();
    		this.ende  = new ArrayList(); 
    		this.dauer  = new ArrayList();
    		this.bild  = new ArrayList();
    		this.ton  = new ArrayList();
    		this.setSender(sender);
			this.setDatum(datum);
			this.setStart(start);
			this.setEnde(ende);
			this.titel      = titel;        
			this.episode    = episode;
			this.genre      = genre;
			this.setDauer(dauer);
			this.land       = land;
			this.jahr       = jahr;
			this.regie      = regie;
			this.setBild(bild);
			this.setTon(ton);
			this.darsteller = darsteller;
			this.inhalt     = inhalt;        			
    }
    
    public BOMovieGuide(ArrayList sender, ArrayList datum , ArrayList start, ArrayList ende,String titel, String episode,
                        String genre, ArrayList dauer, String land, String jahr, String regie,
                        ArrayList bild, ArrayList ton, String darsteller, String inhalt){        
    	this.sender     = sender;
        this.datum      = datum;
        this.start      = start;
        this.ende		= ende;
        this.titel      = titel;        
        this.episode    = episode;
        this.genre      = genre;
        this.dauer      = dauer;
        this.land       = land;
        this.jahr       = jahr;
        this.regie      = regie;
        this.bild       = bild;
        this.ton        = ton;
        this.darsteller = darsteller;
        this.inhalt     = inhalt;             
    }
    /**
     * Getter for property sender.
     * @return Value of property sender.
     */
    public ArrayList getSender() {
        return this.sender;
    }
    
    /**
     * Setter for property sender.
     * @param sender New value of property sender.
     */
    public void setSender(String sender) {
        this.sender.add(sender);
    }
    
    /**
     * Getter for property datum.
     * @return Value of property datum.
     */
    public ArrayList getDatum() {
        return this.datum;
    }
    
    /**
     * Setter for property datum.
     * @param datum New value of property datum.
     */
    public void setDatum(GregorianCalendar datum) {
        this.datum.add(datum);
    }
    
    /**
     * Getter for property start.
     * @return Value of property start.
     */
    public ArrayList getStart() {
        return this.start;
    }
    
    /**
     * Setter for property start.
     * @param start New value of property start.
     */
    public void setStart(GregorianCalendar start) {
        this.start.add(start);
    }
    
    public ArrayList getEnde() {
        return this.ende;
    }
    
    /**
     * Setter for property start.
     * @param start New value of property start.
     */
    public void setEnde(GregorianCalendar ende) {
        this.ende.add(ende);
    }
    
    
    /**
     * Getter for property titel.
     * @return Value of property titel.
     */
    public String getTitel() {
        return this.titel;
    }
    
    /**
     * Setter for property titel.
     * @param titel New value of property titel.
     */
    public void setTitel(String titel) {
        this.titel = titel;
    }
    
    /**
     * Getter for property episode.
     * @return Value of property episode.
     */
    public String getEpisode() {
        return this.episode;
    }
    
    /**
     * Setter for property episode.
     * @param episode New value of property episode.
     */
    public void setEpisode(String episode) {
        this.episode = episode;
    }
    
    /**
     * Getter for property genre.
     * @return Value of property genre.
     */
    public String getGenre() {
        return this.genre;
    }
    
    /**
     * Setter for property genre.
     * @param genre New value of property genre.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    /**
     * Getter for property dauer.
     * @return Value of property dauer.
     */
    public ArrayList getDauer() {
        return this.dauer;
    }
    
    /**
     * Setter for property dauer.
     * @param dauer New value of property dauer.
     */
    public void setDauer(String dauer) {
        this.dauer.add(dauer);
    }
    
    /**
     * Getter for property land.
     * @return Value of property land.
     */
    public String getLand() {
        return this.land;
    }
    
    /**
     * Setter for property land.
     * @param land New value of property land.
     */
    public void setLand(String land) {
        this.land = land;
    }
    
    /**
     * Getter for property jahr.
     * @return Value of property jahr.
     */
    public String getJahr() {
        return this.jahr;
    }
    
    /**
     * Setter for property jahr.
     * @param jahr New value of property jahr.
     */
    public void setJahr(String jahr) {
        this.jahr = jahr;
    }
    
    /**
     * Getter for property regie.
     * @return Value of property regie.
     */
    public String getRegie() {
        return this.regie;
    }
    
    /**
     * Setter for property regie.
     * @param regie New value of property regie.
     */
    public void setRegie(String regie) {
        this.regie = regie;
    }
    
    /**
     * Getter for property bild.
     * @return Value of property bild.
     */
    public ArrayList getBild() {
        return this.bild;
    }
    
    /**
     * Setter for property bild.
     * @param bild New value of property bild.
     */
    public void setBild(String bild) {
    	this.bild.add(bild);
    }
    
    /**
     * Getter for property ton.
     * @return Value of property ton.
     */
    public ArrayList getTon() {
        return this.ton;
    }
    
    /**
     * Setter for property ton.
     * @param ton New value of property ton.
     */
    public void setTon(String ton) {
    	this.ton.add(ton);
    }
    
    /**
     * Getter for property darsteller.
     * @return Value of property darsteller.
     */
    public String getDarsteller() {
        return this.darsteller;
    }
    
    /**
     * Setter for property darsteller.
     * @param darsteller New value of property darsteller.
     */
    public void setDarsteller(String darsteller) {
        this.darsteller = darsteller;
    }
    
    /**
     * Getter for property inhalt.
     * @return Value of property inhalt.
     */
    public String getInhalt() {
        return this.inhalt;
    }
    
    /**
     * Setter for property inhalt.
     * @param inhalt New value of property inhalt.
     */
    public void setInhalt(String inhalt) {
        this.inhalt = inhalt;
    }
   
    public ArrayList getSucheList(){    	
    	if(sucheList.isEmpty()){
    		setSucheList();
    	}
    	return sucheList;
    }
 
    public void setSucheList(){    
    	try{
	    	sucheList.add(LIST_ENTRYS[0]);
	    	sucheList.add(LIST_ENTRYS[1]);
	    	sucheList.add(LIST_ENTRYS[2]);    	
	    	sucheList.add(LIST_ENTRYS[3]);
	    	sucheList.add(LIST_ENTRYS[4]);
	    	sucheList.add(LIST_ENTRYS[5]);
	    	sucheList.add(LIST_ENTRYS[6]);
	    	sucheList.add(LIST_ENTRYS[7]);
	    	sucheList.add(LIST_ENTRYS[8]);
	    	sucheList.add(LIST_ENTRYS[9]);
    	}catch(Exception ex){}
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
    
    public boolean getIfStringInObject(String search){
    	boolean value = false;
    	search = search.toLowerCase();
    	if (this.getGenre().toLowerCase().indexOf(search)>=0){
    		value = true;
    	}else if(this.getDarsteller().toLowerCase().indexOf(search)>=0){
    		value = true;
    	}else if(this.getEpisode().toLowerCase().indexOf(search)>=0){
    		value = true;
    	}else if(this.getInhalt().toLowerCase().indexOf(search)>=0){
    		value = true;    	
    	}else if(this.getLand().toLowerCase().indexOf(search)>=0){
    		value = true;
    	}else if(this.getJahr().toLowerCase().indexOf(search)>=0){
    		value = true;
    	}else if(this.isValueInArray(this.getBild(),search.toLowerCase())){
    		value = true;
    	}else if(this.isValueInArray(this.getTon(),search.toLowerCase())){
    		value = true;
    	}else if(this.getRegie().toLowerCase().indexOf(search)>=0){
    		value = true;
    	}else if(this.getTitel().toLowerCase().indexOf(search)>=0){
    		value = true;
    	}
    	return value;
    }
    
    public ArrayList getAboList(){    	
    	if(aboList.isEmpty()){
    		setAboList();
    	}
    	return aboList;
    }
    public void setAboList(ArrayList aboList){    
    	this.aboList = aboList;
    }
    public void setAboList(){    
    	try{
	    	//aboList.add(ABO_ENTRYS[0]);
	    	//aboList.add(ABO_ENTRYS[1]);	    	
    	}catch(Exception ex){}
    }
}
