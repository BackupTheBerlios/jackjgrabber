package service;

import java.io.File;
import java.util.ArrayList;

/*

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

public class SerHelper {
    
  	/**
  	 * überprüft ob es sich bei der Datei um eine Audio Datei handelt
  	 * 
  	 * @param file
  	 * @return
  	 */
  	public static boolean isAudio(String file) {
  	    String[] audioEndings = new String[]{"mp2", "ac3", "apes"};
  	    file = file.toLowerCase();
  	    for (int i = 0; i < audioEndings.length; i++) {
  	        if (file.endsWith(audioEndings[i])) {
  	            return true;
  	        }
  	    }
  	    return false;
  	}
  	
  	/**
  	 * überprüft ob es sich um eine Videodatei handelt (auch MPG)
  	 * 
  	 * @param file
  	 * @return
  	 */
  	public static boolean isVideo(String file) {
  	    String[] videoEndings = new String[]{"mpv", "mpg", "ts", "vpes"};
  	    file = file.toLowerCase();
  	    for (int i = 0; i < videoEndings.length; i++) {
  	        if (file.endsWith(videoEndings[i])) {
  	            return true;
  	        }
  	    }
  	    return false;
  	}
  	
  	
  	/**
  	 * @param files
  	 * @return the first Video-File in the Array
  	 */
  	public static File getVideoFile(File[] files) {
		    for (int i=0; i<files.length; i++) {
		        if (isVideo(files[i].getName())) {
		            return files[i];
		        }
		    }
		    return null;
		}
  	
  	/**
  	 * @param files, fileList
  	 * @return the fileList filled with Audio-Files from files-Array
  	 */
  	public static ArrayList fillArrayWithAudioFiles(File[] files, ArrayList fileList) {
		    for (int i=0; i<files.length; i++) {
		        if (isAudio(files[i].getName())) {
		            fileList.add(files[i]);
		        }
		    }
		    return fileList;
		}

}
