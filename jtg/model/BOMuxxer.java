/*
BOMuxxer.java by Zielke Sven 

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

package model;

import java.util.ArrayList;

public class BOMuxxer {
	public String videoFile;
	public ArrayList audioOptions; // Liste der Audio-Dateien, Sprache durch Komma getrennt.
	public ArrayList subtitleOptions; // Liste der Untertitel-Dateien, Sprache durch Komma getrennt.
	
	public String getVideoFile() {
		return this.videoFile;
	}
	
	public void setVideoFile(String file) {
		this.videoFile=file;
	}
	
	public ArrayList getAudioOptions() {
		return this.audioOptions;
	}
	
	public void setAudioOptions(ArrayList audioOpts) {
		this.audioOptions=audioOpts;
	}
	
	public ArrayList getSubtitleOptions() {
		return this.subtitleOptions;
	}
	
	public void setSubtitleOptions(ArrayList subtitleOpts) {
		this.subtitleOptions=subtitleOpts;
	}

}
