package model;

import java.io.IOException;
import java.util.ArrayList;

import control.ControlMain;

/*
 * BOBouquet.java
 *
 * Created on 11. September 2004, 07:53
 */

/**
 *
 * @author  ralix
 */
public class BOBouquet extends java.lang.Object {
    String bouquetNummer;
    String bouquetName;
    ArrayList sender;
    /** Creates a new instance of BOBouquet */
    public BOBouquet() {
    }
 
    public BOBouquet(String bouquetNummer, String bouquetName) {		
	this.bouquetNummer = bouquetNummer;
	this.bouquetName   = bouquetName;		
    }
    
    public String getBouquetNummer() {
	return bouquetNummer;
    }   
	
    public void setBouquetNummer(String bouquetNummer) {
	this.bouquetNummer = bouquetNummer;
    }
    public String getBouquetName() {
	return bouquetName;
    }
	
    public void setBouquetName(String bouquetName) {
        this.bouquetName = bouquetName;
    }
	
    public void setAll(BOBouquet bobounquet){        
        this.bouquetNummer = bobounquet.getBouquetNummer();   
        this.bouquetName   = bobounquet.getBouquetName();
    }  

	/**
	 * @return ArrayList
	 */
	public ArrayList getSender() {
		return sender;
	}

	/**
	 * Sets the sender.
	 * @param sender The sender to set
	 */
	public void setSender(ArrayList sender) {
		this.sender = sender;
	}
	
	public void readSender() throws IOException {
		this.setSender(ControlMain.getBoxAccess().getSender(this));
	}

}
