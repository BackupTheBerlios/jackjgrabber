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
	private String login;
	private String password;
	private Boolean standard = Boolean.FALSE;
	
	
	public BOBox() {}
	
	public BOBox (String ip, String login, String password) {
		this.setDboxIp(ip);
		this.setLogin(login);
		this.setPassword(password);
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
}
