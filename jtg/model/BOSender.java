/*
 * Created on 08.08.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package model;

import java.io.IOException;
import java.util.ArrayList;

import control.ControlMain;


/**
 * @author AlexG
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BOSender {
	String nummer;
	String chanId;
	String name;
	ArrayList epg;
	
	public BOSender(String nummer, String chanId, String name) {
		super();
		this.setNummer(nummer);
		this.setChanId(chanId);
		this.setName(name);
	}
	/**
	 * @return Returns the chanId.
	 */
	public String getChanId() {
		return chanId;
	}
	/**
	 * @param chanId The chanId to set.
	 */
	public void setChanId(String chanId) {
		this.chanId = chanId;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the nummer.
	 */
	public String getNummer() {
		return nummer;
	}
	/**
	 * @param nummer The nummer to set.
	 */
	public void setNummer(String nummer) {
		this.nummer = nummer;
	}
	/**
	 * @return Returns the epg.
	 */
	public ArrayList getEpg() {
		return epg;
	}
	/**
	 * @param epg The epg to set.
	 */
	public void setEpg(ArrayList value) throws IOException {
		epg=value;
	}
	
	public ArrayList readEpg() throws IOException {
		ArrayList epgList = ControlMain.getBox().getEpg(this);
		this.setEpg(epgList);
		return epgList;
	}
}
