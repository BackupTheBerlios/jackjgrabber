/*
 * Created on 21.10.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package model;

import java.util.ArrayList;

import service.SerMovieGuide2Xml;

/**
 * @author ralix
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BOMovieGuide {
	 /**
     * Holds value of property sender.
     */
    private String sender;
    
    /**
     * Holds value of property datum.
     */
    private String datum;
    
    /**
     * Holds value of property start.
     */
    private String start;
    
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
    private String dauer;
    
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
    private String bild;
    
    /**
     * Holds value of property ton.
     */
    private String ton;
    
    /**
     * Holds value of property darsteller.
     */
    private String darsteller;
    
    /**
     * Holds value of property inhalt.
     */
    private String inhalt;
    
    private ArrayList genreList = new ArrayList();
    private SerMovieGuide2Xml guide = new SerMovieGuide2Xml();
    /** Creates a new instance of BOMovieGuide */
    public BOMovieGuide() {
    }
    
    public BOMovieGuide(String sender, String datum , String start, String titel, String episode,
                        String genre, String dauer, String land, String jahr, String regie,
                        String bild, String ton, String darsteller, String inhalt){
        this.sender     = sender;
        this.datum      = datum;
        this.start      = start;
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
    public String getSender() {
        return this.sender;
    }
    
    /**
     * Setter for property sender.
     * @param sender New value of property sender.
     */
    public void setSender(String sender) {
        this.sender = sender;
    }
    
    /**
     * Getter for property datum.
     * @return Value of property datum.
     */
    public String getDatum() {
        return this.datum;
    }
    
    /**
     * Setter for property datum.
     * @param datum New value of property datum.
     */
    public void setDatum(String datum) {
        this.datum = datum;
    }
    
    /**
     * Getter for property start.
     * @return Value of property start.
     */
    public String getStart() {
        return this.start;
    }
    
    /**
     * Setter for property start.
     * @param start New value of property start.
     */
    public void setStart(String start) {
        this.start = start;
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
    public String getDauer() {
        return this.dauer;
    }
    
    /**
     * Setter for property dauer.
     * @param dauer New value of property dauer.
     */
    public void setDauer(String dauer) {
        this.dauer = dauer;
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
    public String getBild() {
        return this.bild;
    }
    
    /**
     * Setter for property bild.
     * @param bild New value of property bild.
     */
    public void setBild(String bild) {
        this.bild = bild;
    }
    
    /**
     * Getter for property ton.
     * @return Value of property ton.
     */
    public String getTon() {
        return this.ton;
    }
    
    /**
     * Setter for property ton.
     * @param ton New value of property ton.
     */
    public void setTon(String ton) {
        this.ton = ton;
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
    public ArrayList getDatumList(){
    	setValueList("datum");
    	return genreList;
    }
   
    public ArrayList getGenreList(){
    	setValueList("genre");
    	return genreList;
    }
    public ArrayList getTitelList(){
    	setValueList("titel");
    	return genreList;
    }
    public ArrayList getSenderList(){
    	setValueList("sender");
    	return genreList;
    }
    public void  setValueList(String value){
    	try{
    		this.genreList = guide.getGenryList(value);
    	}catch(Exception ex){}
    }
    
    public void toOut(){
    	System.out.println("this.sender     = "+sender);
    	System.out.println("this.datum      = "+datum);
    	System.out.println("this.start      = "+start);
		System.out.println("this.titel      = "+titel);        
		System.out.println("this.episode    = "+episode);
		System.out.println("this.genre      = "+genre);
		System.out.println("this.dauer      = "+dauer);
		System.out.println("this.land       = "+land);
		System.out.println("this.jahr       = "+jahr);
		System.out.println("this.regie      = "+regie);
		System.out.println("this.bild       = "+bild);
		System.out.println("this.ton        = "+ton);
		System.out.println("this.darsteller = "+darsteller);
		System.out.println("this.inhalt     = "+inhalt);              
    }
}
