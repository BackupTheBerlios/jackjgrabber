/*
 * Created on 20.09.2004
 */
package model;

/**
 * @author Geist Alexander
 *
 */
public class BOBox {

	private String dboxIp;
	private String login="root";
	private String password="dbox2";
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
