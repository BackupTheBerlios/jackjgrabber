/*
PESWriter.java by Geist Alexander 

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

import java.io.FileNotFoundException;

import org.apache.log4j.Logger;

/**
 *
 */
class PESWriter 
{
	Record record;
	PESSplitWriterList writerList;
	
	public PESWriter(Record record, PESSplitWriterList writerList)
	{
		if (record.streams == null) return;
		if (record.streams.list.length == 0) return;

		this.record = record;
		this.writerList = writerList;
		
		try {
            for (int i = 0; i < record.streams.list.length; i++) {
            	if (record.streams.avString.charAt(i) == 'v') {
            		writerList.Add(record.BaseFileName, 0);
            	} else {
            		writerList.Add(record.BaseFileName, 1);
            	}
            }
        } catch (FileNotFoundException e) {
            Logger.getLogger("PESWriter").error("unable to create Ouput-File");
        }

		UdpPacket packet; 
		boolean isAllStreamsStopped;
		do {
			isAllStreamsStopped = true;
			for (int i = 0; i < record.streams.list.length; i++) {
				while (true) {
					if(!record.streams.list[i].checkReadWriteDistance()) {
						isAllStreamsStopped = false;
						try {
                            Thread.sleep(100);
                        } catch (InterruptedException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
						break;
					}
					packet = record.streams.list[i].read();
					if (packet == null)  break; 
					isAllStreamsStopped = false;
					PESSplitWriter writer = (PESSplitWriter)writerList.writers.get(i);
					writer.write( packet.buffer, packet.dataOffset, packet.UsedLength - packet.dataOffset);
				}
			}
		} while (!record.IsStopped && !isAllStreamsStopped);
		record.IsStreamWriterExit = true;
		Logger.getLogger("PESWriter").info("PESWriter stopped");

		writerList.Close();
	}	
}
