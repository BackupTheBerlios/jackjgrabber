package model;

import java.util.ArrayList;

/*
 * BOSettingsPlayback.java by Geist Alexander
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation,
 * Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *  
 */
public class BOSettingsPlayback {

	private BOSettings settings;
	public ArrayList playbackOptions;
	public boolean alwaysUseStandardPlayback;
	public String playbackString;

	public BOSettingsPlayback(BOSettings settings) {
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
	 * @param settings
	 *            The settings to set.
	 */
	public void setSettings(BOSettings settings) {
		this.settings = settings;
	}
	/**
	 * @return Returns the playbackPlayer.
	 */
	public String getPlaybackString() {
		if (playbackString != null) {
			return playbackString;
		}
		return "";
	}
	/**
	 * @param playbackPlayer
	 *            The playbackPlayer to set.
	 */
	public void setPlaybackString(String playbackString) {
		if (this.playbackString == null || !this.playbackString.equals(playbackString)) {
			setSettingsChanged(true);
			this.playbackString = playbackString;
		}
	}

	/**
	 * @return Returns the playbackOptions.
	 */
	public ArrayList getPlaybackOptions() {
		return playbackOptions;
	}
	/**
	 * @param playbackOptions
	 *            The playbackOptions to set.
	 */
	public void setPlaybackOptions(ArrayList playbackOptions) {
		this.playbackOptions = playbackOptions;
	}
	/**
	 * @return Returns the alwaysUseStandardPlayback.
	 */
	public boolean isAlwaysUseStandardPlayback() {
		return alwaysUseStandardPlayback;
	}
	/**
	 * @param alwaysUseStandardPlayback
	 *            The alwaysUseStandardPlayback to set.
	 */
	public void setAlwaysUseStandardPlayback(boolean alwaysUseStandardPlayback) {
		if (this.alwaysUseStandardPlayback != alwaysUseStandardPlayback) {
			setSettingsChanged(true);
			this.alwaysUseStandardPlayback = alwaysUseStandardPlayback;
		}
	}

	public void addPlaybackOption(BOPlaybackOption playbackOption) {
		setSettingsChanged(true);
		this.getPlaybackOptions().add(playbackOption);
	}

	public void removePlaybackOption(int number) {
		setSettingsChanged(true);
		this.getPlaybackOptions().remove(number);
	}

	/**
	 * if more Options available, return the standard-option if no standard-option declared, return 1st Option
	 */
	public BOPlaybackOption getStandardPlaybackOption() {
		if (this.getPlaybackOptions() == null || this.getPlaybackOptions().size() == 0) {
			return null;
		}
		for (int i = 0; i < this.getPlaybackOptions().size(); i++) {
			BOPlaybackOption option = (BOPlaybackOption) this.getPlaybackOptions().get(i);
			if (option.isStandard()) {
				return option;
			}
		}
		return (BOPlaybackOption) this.getPlaybackOptions().get(0);
	}

	public Object[] getPlaybackOptionNames() {
		String[] names = new String[this.getPlaybackOptions().size()];
		for (int i = 0; i < this.getPlaybackOptions().size(); i++) {
			names[i] = ((BOPlaybackOption) this.getPlaybackOptions().get(i)).getName();
		}
		return names;
	}

	public BOPlaybackOption getPlaybackOption(String searchName) {
		for (int i = 0; i < this.getPlaybackOptions().size(); i++) {
			BOPlaybackOption option = (BOPlaybackOption) this.getPlaybackOptions().get(i);
			if (option.getName().equals(searchName)) {
				return option;
			}
		}
		return null; //should not happen
	}

}