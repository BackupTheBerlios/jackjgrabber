package service;

import java.io.File;
import java.text.*;
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
  	    String[] videoEndings = new String[]{"mpv", "mpg", "ts", "vpes","vob","mpeg"};
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
  	public static String getVideoFile(File[] files) {
	    for (int i=0; i<files.length; i++) {
	        if (isVideo(files[i].getName())) {
	            return files[i].getAbsolutePath();
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
	            fileList.add(files[i].getAbsolutePath());
	        }
	    }
	    return fileList;
	}

	/**
	 * @param file
	 * @return the ending of the given file
	 */
	public static String getEnding(File file) {
		int end = file.getName().lastIndexOf(".");
		if (end > -1)
		{
			return file.getName().substring(end + 1); 
		}
		return "";
	}
	
	/**
	 * berechnet die angezeigte Größe einer Datei in der angegebenen Einheit
	 * 
	 * @param s
	 * @param type
	 *            MB for MByte and KB for Kilobyte
	 * @param div
	 *            divisor (e.g. 1024)
	 * @return
	 */
	public static String calcSize(double s, String type, int div) {

		if (type.equals("MB")) {
			s = s / div; // kb
			s = s / div; // MB
			return NumberFormat.getNumberInstance().format(s) + " " + type;
		} else if (type.equals("KB")) {
			s = s / div; // kb
			return NumberFormat.getNumberInstance().format(s) + " " + type;
		} else if (type.equals("MBit")) {
			s *= 8;
			s = s / div; // kb
			s = s / div; // MB
			return NumberFormat.getNumberInstance().format(s) + " " + type;
		}
		return NumberFormat.getNumberInstance().format(s) + " " + type;
	}

	/**
	 * berechnet die angezeigte Größe einer Datei in der angegebenen Einheit
	 * 
	 * @param s
	 * @param type
	 *            MB for MByte and KB for Kilobyte
	 * @return
	 */
	public static String calcSize(double s, String type) {

		return calcSize(s, type, 1024);
	}


}
