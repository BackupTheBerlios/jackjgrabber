package streaming;
/*
PESWriteStream.java by Geist Alexander 

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
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import control.ControlMain;


/**
 * 
 */
public class PESWriteStream {

    String baseFileName;
	String fileNameExtension;
	BufferedOutputStream fileOut;
	int stremNum;
	boolean isActive = true;

	public PESWriteStream(char dataType, int number, String filename) throws FileNotFoundException {
	    baseFileName = ControlMain.getSettings().getSavePath()+"/"+filename;
	    stremNum = number;
		switch (dataType) {
			case 'v':
				fileNameExtension = ".mpv";
				break;
			case 'a':
				fileNameExtension = ".apes";
				break;
			case 3:
				fileNameExtension = ".ts";
				break;
			default:
				return;
		}
		
		File file = new File(baseFileName+"_"+stremNum+fileNameExtension);
		fileOut = new BufferedOutputStream(new FileOutputStream(file));			
	}
	
	public void write(UdpPacket udpPacket) {
		try {
            fileOut.write(udpPacket.buffer, udpPacket.dataOffset, udpPacket.UsedLength - udpPacket.dataOffset);
        } catch (IOException e) {
            Logger.getLogger("PESWriteStream").error("unable to write Data");
        }
	}
	
	public void stop() {
	    try {
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}