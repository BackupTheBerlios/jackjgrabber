/*
 * @(#)RawInterface.java - provides a little interface
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


package projectX.io;
import java.io.*;
import java.util.*;

import projectX.common.Resource;


public class RawInterface
{
	private RawReadIF rawread;
	long stream_size;

	public RawInterface()
	{
		try {
			Class rawReadClass = Class.forName("projectX.RawRead");
			rawread = (RawReadIF)rawReadClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		stream_size = 0;
	}

	public void add_native_files(ArrayList arraylist)
	{
		if (rawread.AccessEnabled())
			rawread.add_native_files(arraylist);

		else
			return;
	}

	public String GetLoadStatus()
	{
		if (rawread != null && rawread.AccessEnabled())
			return rawread.GetLoadStatus();

		else
			return Resource.getString("rawread.msg1");
	}

	public boolean isAccessibleDisk(String sourcefile)
	{
		return rawread.isAccessibleDisk(sourcefile);
	}

	public long getFileSize(String sourcefile)
	{
		if (isAccessibleDisk(sourcefile))
			return rawread.getFileSize(sourcefile);

		else if (new File(sourcefile).exists())
			return (new File(sourcefile).length());

		else
			return -1L;
	}

	public String getFileDate(String sourcefile)
	{
		if (isAccessibleDisk(sourcefile))
		{
			long datetime = rawread.lastModified(sourcefile.substring(1));
			return java.text.DateFormat.getDateInstance(java.text.DateFormat.LONG).format(new java.util.Date(datetime)) + "  " + java.text.DateFormat.getTimeInstance(java.text.DateFormat.LONG).format(new java.util.Date(datetime));
		}
		else
			return java.text.DateFormat.getDateInstance(java.text.DateFormat.LONG).format(new java.util.Date(new File(sourcefile).lastModified())) + "  " + java.text.DateFormat.getTimeInstance(java.text.DateFormat.LONG).format(new java.util.Date(new File(sourcefile).lastModified()));
	}

	public boolean getScanData(String sourcefile, byte data[]) throws IOException
	{
		return getScanData(sourcefile, data, 0);
	}

	public boolean getScanData(String sourcefile, byte data[], long position) throws IOException
	{
		if (isAccessibleDisk(sourcefile))
		{
			RawFileInputStream rawin = new RawFileInputStream(rawread, sourcefile);

			rawin.skip(position);
			rawin.read(data, 0, data.length);
			rawin.close();

			return true;
		}
		else
			return false;
	}

	public boolean getData(String sourcefile, byte data[], long skip_size, int read_offset, int size) throws IOException
	{
		if (isAccessibleDisk(sourcefile))
		{
			RawFileInputStream rawin = new RawFileInputStream(rawread, sourcefile);
			rawin.skip(skip_size);
			rawin.read(data, read_offset, size);
			rawin.close();

			return true;
		}
		else
			return false;
	}

	public PushbackInputStream getStream(String sourcefile, int buffersize) throws IOException
	{
		PushbackInputStream stream = null;

		if (isAccessibleDisk(sourcefile))
		{
			RawFileInputStream rawin = new RawFileInputStream(rawread, sourcefile);
			stream = new PushbackInputStream(rawin, buffersize);
			stream_size = rawin.streamSize();
		}
		else
		{
			stream = new PushbackInputStream(new FileInputStream(sourcefile), buffersize);
			stream_size = new File(sourcefile).length();
		}

		return stream;
	}

	public long getStreamSize()
	{
		return stream_size;
	}
}
