package model;

import java.util.ArrayList;

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
    
	private String themeLayout;
    private String vlcPath;
    private ArrayList boxList;
    private boolean boxIpChanged = false;
    private boolean settingsChanged = false;
    
    /** Creates a new instance of BOSettings */
    public BOSettings() {
    }
    
    public BOSettings (String dboxIp, String login, String password, String vlcPath){
        this.vlcPath  = vlcPath;
    }
    
    public String getVlcPath() {
        return vlcPath;
    }
	
    public void setVlcPath(String vlcPath) {
        this.vlcPath = vlcPath;
    }
    public void setAll(BOSettings bosettings){       
        this.vlcPath  = bosettings.getVlcPath();
    }
	/**
	 * @return Returns the boxList.
	 */
	public ArrayList getBoxList() {
		return boxList;
	}
	/**
	 * @param boxList The boxList to set.
	 */
	public void setBoxList(ArrayList box) {
		this.boxList = box;
	}
	/**
	 * @return Returns the themeLayout.
	 */
	public String getThemeLayout() {
		return themeLayout;
	}
	/**
	 * @param themeLayout The themeLayout to set.
	 */
	public void setThemeLayout(String themeLayout) {
		this.themeLayout = themeLayout;
	}
	/**
	 * @return Returns the boxIpChanged.
	 */
	public boolean isBoxIpChanged() {
		return boxIpChanged;
	}
	/**
	 * @param boxIpChanged The boxIpChanged to set.
	 */
	public void setBoxIpChanged(boolean changed) {
		if (changed) {
			this.setSettingsChanged(true);
		}
		this.boxIpChanged = changed;
	}
	/**
	 * @return Returns the settingsChanged.
	 */
	public boolean isSettingsChanged() {
		return settingsChanged;
	}
	/**
	 * @param settingsChanged The settingsChanged to set.
	 */
	public void setSettingsChanged(boolean settingsChanged) {
		this.settingsChanged = settingsChanged;
	}
}
