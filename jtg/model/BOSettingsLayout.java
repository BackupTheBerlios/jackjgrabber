package model;

import java.awt.*;

/*
 * BOSettingsLayout by Achleitner Reinhard
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
public class BOSettingsLayout {

	private BOSettings settings;

	private Dimension size;

	private Point location;

	private int recordInfoDirectorySplitPos;

	public BOSettingsLayout(BOSettings settings) {
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

	public Point getLocation() {
		return location;
	}
	public void setLocation(Point location) {
		if (this.location == null || !location.equals(this.location)) {
			this.location = location;
			setSettingsChanged(true);
		}
	}
	public int getRecordInfoDirectorySplitPos() {
		return recordInfoDirectorySplitPos;
	}
	public void setRecordInfoDirectorySplitPos(int recordInfoDirectorySplitPos) {
		if (recordInfoDirectorySplitPos != this.recordInfoDirectorySplitPos) {
			this.recordInfoDirectorySplitPos = recordInfoDirectorySplitPos;
			setSettingsChanged(true);
		}

	}
	public Dimension getSize() {
		return size;
	}
	public void setSize(Dimension size) {
		if (this.size == null || !size.equals(this.size)) {
			this.size = size;
			setSettingsChanged(true);
		}
	}
}