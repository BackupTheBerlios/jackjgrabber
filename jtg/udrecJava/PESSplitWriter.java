/*
PESSplitWriter.java by Geist Alexander 

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
package udrecJava;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 */
public class PESSplitWriter {
    
    String baseFileName;
	String fileNameExtension;
	BufferedOutputStream fileOut;
	boolean isActive = true;

	public PESSplitWriter(String baseFileName, int streamNum, int dataType) throws FileNotFoundException
	{
		this.baseFileName = baseFileName;
		switch (dataType) {
			case 0:
				fileNameExtension = ".mpv";
				break;
			case 1:
				fileNameExtension = ".mp2";
				break;
			case 2:
				fileNameExtension = ".ac3";
				break;
			case 3:
				fileNameExtension = ".ts";
				break;
			default:
				isActive = false;
				return;
		}
		
		File file = new File(baseFileName+fileNameExtension);
		fileOut = new BufferedOutputStream(new FileOutputStream(file));			
	}
	
	public void write(byte[] buffer, int offset, int length)
	{
		try {
            if (!isActive) return;
            fileOut.write(buffer, offset, length);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	public void write(byte[] buffer, int offset, int length, int sortDts, int ptsOffset, boolean isPFrame)
	{
		try {
            if (!isActive) return;
            fileOut.write(buffer, offset, length);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	public void Close()
	{
		try {
            if (!isActive) return;
            fileOut.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
}
