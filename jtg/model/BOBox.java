package model;
/*
BOBox.java by Geist Alexander 

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
public class BOBox {

	private String dboxIp;
	private String login="root"; //Defaultwert
	private String password="dbox2"; //Defaultwert
	private Boolean standard = Boolean.FALSE;
	private boolean selected;
	
	
	public BOBox() {}
	
	public BOBox (String ip) {
		this.setDboxIp(ip);
	}
	/**
	 * @return Returns the dboxIp.
	 */
	public String getDboxIp() {
		return dboxIp;
	}
	/**
	 * @param dboxIp The dboxIp to set.
	 */
	public void setDboxIp(String dboxIp) {
		this.dboxIp = dboxIp;
	}
	/**
	 * @return Returns the login.
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login The login to set.
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return Returns the standard.
	 */
	public Boolean isStandard() {
		return standard;
	}
	/**
	 * @param standard The standard to set.
	 */
	public void setStandard(Boolean standard) {
		this.standard = standard;
	}
	/**
	 * @return Returns the selected.
	 */
	public boolean isSelected() {
		return selected;
	}
	/**
	 * @param selected The selected to set.
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
