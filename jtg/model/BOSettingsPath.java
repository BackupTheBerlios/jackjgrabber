package model;
/*
BOSettingsRecords.java by Geist Alexander 

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
public class BOSettingsPath {
	
	private BOSettings settings;
	public String savePath;
	public String udrecPath;
	public String projectXPath;
	public String vlcPath;
	public String shutdownToolPath;

	public BOSettingsPath(BOSettings settings) {
		this.setSettings(settings);
	}
	
	private void setSettingsChanged(boolean value) {
		this.getSettings().setSettingsChanged(value);
	}
	
	/**
	 * @return Returns the settings.
	 */
	public BOSettings getSettings() {
		return settings;
	}
	/**
	 * @param settings The settings to set.
	 */
	public void setSettings(BOSettings settings) {
		this.settings = settings;
	}
	/**
     * @return Returns the vlcPath.
     */
    public String getVlcPath() {
        return vlcPath;
    }
    /**
     * @param vlcPath The vlcPath to set.
     */
    public void setVlcPath(String vlcPath) {
        if (this.vlcPath == null || !this.vlcPath.equals(vlcPath)) {
      			setSettingsChanged(true);
      			this.vlcPath = vlcPath;
      		}
    }
	/**
	 * @return Returns the savePath.
	 */
	public String getSavePath() {
		return savePath;
	}
	/**
	 * @param savePath
	 *            The savePath to set.
	 */
	public void setSavePath(String savePath) {
		if (!this.savePath.equals(savePath)) {
			setSettingsChanged(true);
			this.savePath = savePath;
		}
	}
	/**
	 * @return Returns the udrecPath.
	 */
	public String getUdrecPath() {
		return udrecPath;
	}
	/**
	 * @param udrecPath
	 *            The udrecPath to set.
	 */
	public void setUdrecPath(String path) {
		if (this.udrecPath == null || !this.udrecPath.equals(path)) {
			setSettingsChanged(true);
			this.udrecPath = path;
		}
	}
	/**
	 * @return Returns the projectXPath.
	 */
	public String getProjectXPath() {
		return projectXPath;
	}
	/**
	 * @param projectXPath The projectXPath to set.
	 */
	public void setProjectXPath(String projectXPath) {
		if (this.projectXPath == null || !this.projectXPath.equals(projectXPath)) {
			setSettingsChanged(true);
			this.projectXPath = projectXPath;
		}
	}
    /**
     * @return Returns the shutdownToolPath.
     */
    public String getShutdownToolPath() {
        return shutdownToolPath;
    }
    /**
     * @param shutdownToolPath The shutdownToolPath to set.
     */
    public void setShutdownToolPath(String shutdownToolPath) {
        if (this.shutdownToolPath == null || !this.shutdownToolPath.equals(shutdownToolPath)) {
			setSettingsChanged(true);
			this.shutdownToolPath = shutdownToolPath;
		}
    }
}
