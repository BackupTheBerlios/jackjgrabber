package model;
/*
 * BOSettings.java
 *
 * Created on 11. September 2004, 08:56
 */

/**
 *
 * @author  ralix
 */
public class BOSettings {
    
    private String dboxIp;
    private String login;
    private String password;
    private String vlcPath;
    
    /** Creates a new instance of BOSettings */
    public BOSettings() {
    }
    
    public BOSettings (String dboxIp, String login, String password, String vlcPath){
        this.dboxIp   = dboxIp;
        this.login    = login;
        this.password = password;
        this.vlcPath  = vlcPath;
    }
    
    public String getDboxIp(){
        return this.dboxIp;
    }
    
    public void setDboxIp(String dboxIp){
        this.dboxIp = dboxIp;
    }
    
    public String getLogin(){
        return this.login;
    }
    
    public void setLogin(String login){
        this.login = login;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    public String getVlcPath() {
        return vlcPath;
    }
	
    public void setVlcPath(String vlcPath) {
        this.vlcPath = vlcPath;
    }
    public void setAll(BOSettings bosettings){       
        this.dboxIp   = bosettings.getDboxIp();
        this.login    = bosettings.getLogin();
        this.password = bosettings.getPassword();
        this.vlcPath  = bosettings.getVlcPath();
    }
}
