/*
 * @(#)RAWREAD.java - alias class definition, may be replaced by additional disk access
 *
 * Copyright (c) 2004 by dvb.matt, All Rights Reserved.
 * 
 * This file is part of X, a free Java based demux utility.
 * X is intended for educational purposes only, as a non-commercial test project.
 * It may not be used otherwise. Most parts are only experimental.
 *  
 *
 * This program is free software; you can redistribute it free of charge
 * and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package projectX;
import java.util.ArrayList;

import projectX.io.RawReadIF;

public class RawRead implements RawReadIF 
{

	// check DLL load status and errors
	native public String GetLoadStatus();

	// directory read functions
	native public int findOpen(String directoryname);
	native public String findNextFile(int findhandle);
	native public int findClose(int findhandle);

	// file read functions
	native public int openFile(String filename);
	native public int readFile(int filehandle,byte[] array,int offsetinbuffer,int readlen);
	native public int closeFile(int filehandle);
	native public long skipBytes(int filehandle,long skipbytes);
	native public long getFileSize(String filename);
	native public long lastModified(String filename);

	static boolean DllLoaded;

	public void add_native_files(ArrayList arraylist)
	{
		String s;
		int i=0;
		int hdl=findOpen("\\");

		if (hdl!=0)
		{
			do
			{
				s=findNextFile(hdl);

				if (s.length()!=0)
					arraylist.add(s);

				i++;

				if (i>100)
					break;
			}
			while (s.length()!=0);

			findClose(hdl);
		}

		return;
	}

	public boolean isAccessibleDisk(String sourcefile)
	{
		// make sure it's not a PC file (c: or UNC prefix) and not a Linux file (/ prefix)
		// support of URLs untested ( file:// , http:// .. )
		if (DllLoaded && sourcefile.charAt(1) != ':' && sourcefile.charAt(1) != '\\' && sourcefile.charAt(0) != '/')
			return true;

		else
			return false;
	}

	public boolean AccessEnabled()
	{
		return DllLoaded;
	}

	// Loads the file Rawread.dll at run-time
	// don't load if access isn't required
	static {
		try 
		{
			System.loadLibrary("Rawread");
			DllLoaded=true;
		}
		catch (UnsatisfiedLinkError exc)
		{
			DllLoaded=false;
		}
	}

	// Constructor
	public RawRead()
	{}
}
