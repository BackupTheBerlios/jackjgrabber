/*
 * Created on 22.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package model;

import java.util.ArrayList;

/**
 * @author ralix
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class BOLocale {
	public ArrayList localeList = new ArrayList();
	public String locale = ""; //= "";
	
	public BOLocale(){
		localeList.add("de,Deutsch");
		localeList.add("en,Englisch");
		localeList.add("fi,Finisch");
	}
		
	public ArrayList getLocaleList() {
		return localeList;		
	}
	/**
	 * @param boxList The boxList to set.
	 */
	public void setLocaleList(ArrayList locale) {
		this.localeList = locale;
	}
	public void setLocale(String locale){
		this.locale = locale;
	}	
	public String getLocale(){
		return locale;		
	}
}