package model;
/*
BOSender.java by Geist Alexander 

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.  

*/ 
import java.io.IOException;
import java.util.ArrayList;

import control.ControlMain;

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
		ArrayList epgList = ControlMain.getBoxAccess().getEpg(this);
		this.setEpg(epgList);
		return epgList;
	}
}
