package streaming;
/*
DataWriteStream.java by Geist Alexander 

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
import java.util.ArrayList;

import org.apache.log4j.Logger;

import service.SerFormatter;

import control.ControlMain;

public class DataWriteStream {

    RecordControl recordControl;
    BufferedOutputStream fileOut;
    int streamNumber;
    
    File directory;
    String fileName;
	String fileNameExtension;;
	int fileNumber;
	File currentFile;
	ArrayList fileList = new ArrayList();
	
	boolean isActive = true;
	boolean foundHeader = false;

	public DataWriteStream(char dataType, int number, String filename, RecordControl control ) {
	    recordControl = control;
	    streamNumber = number;
	    fileNumber = 1;
	    this.setFileName(filename);
		switch (dataType) {
			case 't':
				fileNameExtension = ".ts";
				foundHeader=true;
				break;
			default:
				return;
		}
		this.createFileOutput();
	}
	
	public DataWriteStream(String filename, RecordControl control, String extension ) {
	    recordControl = control;
	    fileNameExtension=extension;
	    streamNumber = 0;
	    fileNumber = 0;
	    this.setFileName(filename);
		this.createFileOutput();
	}
	
	private void createFileOutput () {
	    try {
            fileNumber = fileList.size();
            String fullFileName = fileName+"_"+streamNumber+"_"+fileNumber+fileNameExtension;
            currentFile = new File(this.getDirectory(), fullFileName);
            
            fileOut = new BufferedOutputStream(new FileOutputStream(currentFile));
            fileList.add(fileNumber, currentFile);
        } catch (FileNotFoundException e) {
            Logger.getLogger("UdpRecord").error("Unable to create Output-Files");
            recordControl.stopRecord();
        }
	}
	
	private File getDirectory() {
	    if (directory == null) {
	        directory = new File(ControlMain.getSettings().getSavePath(), fileName);
            directory.mkdir();
	    }
	    return directory;
	}
	
	public boolean scanForMPEGHeader(byte[] input) {		
	    int index=0;
		int counter=0;
		byte queue[] = new byte[3] ;

		try {
            while (!foundHeader) {
            	queue[index] = input[counter];
            	index++;
            	counter++;
            	if (index == 3){
            		if (queue[0] == 0 && queue[1] == 0 && queue[2] == 1) {
            		    if ((input[counter] & 0xE0) == 0xC0) {
            		        foundHeader=true;
            		        fileNameExtension = ".mp2";
            		        Logger.getLogger("DataWriteStream").info("Found Audio-Stream");
            		        break;
            		    }    
            		    if ((input[counter] & 0xff) == 0xbd) {
            		        foundHeader=true;
            		        fileNameExtension = ".ac3";
            		        Logger.getLogger("DataWriteStream").info("Found AC3-Stream");
            		        break;
            		    }
            		    if ((input[counter] & 0xE0) == 0xE0) {
            		        foundHeader=true;
            		        fileNameExtension = ".mpv";
            		        Logger.getLogger("DataWriteStream").info("Found Video-Stream");
            		        break;
            		    }
            		}
            		queue[0] = queue[1];
            		queue[1] = queue[2];
            		index = 2;
            	}
            }
            this.createFileOutput();
            return true;
        } catch (ArrayIndexOutOfBoundsException  e) {
            return false;
        }
	}
	
	public void write(UdpPacket udpPacket) {
	    if (!foundHeader) {
	        this.scanForMPEGHeader(udpPacket.buffer);
	    } else {
	        try {
	            fileOut.write(udpPacket.buffer, udpPacket.dataOffset, udpPacket.UsedLength - udpPacket.dataOffset);
	        } catch (IOException e) {
	            Logger.getLogger("DataWriteStream").error("unable to write Data");
	            recordControl.stopRecord();
	        }
	    }
	}
	
	public void write(byte[] data) {
	    try {
            fileOut.write(data);
        } catch (IOException e) {
            Logger.getLogger("DataWriteStream").error("unable to write Data");
            recordControl.stopRecord();
        }
	}
	
	public void stop() {
	    try {
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    /**
     * @param fileName The fileName to set.
     */
    public void setFileName(String fileName) {
        this.fileName = SerFormatter.stripOff(fileName);
    }
}