/*
UdpPacketList.java by Geist Alexander 

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

import org.apache.log4j.Logger;



/**
 *
 */
class UdpPacketList
{
    Record Record;
	UdpPacket[] packetList;
	boolean[] packetUsedList;
	UdpPacket lastReadPacket;
	boolean isStopped = false;

	int absReadPos = 0;
	int maxWritePos = 0;
	int readWriteDistance;
	boolean isOverflow = false;
	int overflowCount;
	boolean isPacketLost = false;
	int lostCount;
	public boolean IsActive = true;

	public boolean IsStopped()
	{
		return isStopped;
	}

	public UdpPacketList(int length, Record record, int distance)
	{
		lastReadPacket = new UdpPacket();
		packetList = new UdpPacket[length];
		packetUsedList = new boolean[length];
		this.Record = record;
		for (int i = 0; i < packetList.length; i++) {
			packetList[i] = new UdpPacket();
			packetUsedList[i] = false;
		}
		readWriteDistance = distance;
	}

	public UdpPacket Write(UdpPacket packet)
	{
		if (!IsActive) return packet;
		int absWritePos = packet.getStreamPacket();
		if (maxWritePos < absWritePos) maxWritePos = absWritePos;

		if (absWritePos < absReadPos ||
		    absWritePos >= absReadPos + packetList.length) {
			if (isStopped) return packet;
			if (!isOverflow) {
				isOverflow = true;
				int max = absReadPos + packetList.length;
				String logString = "Buffer Overflow Start: packet "+absWritePos+" read "+absReadPos+" max "+max;
				Logger.getLogger("UdpPacketList").error(logString);
				overflowCount = 1;
			} else {
				overflowCount++;
			}
			return packet;
		} else {
			if (isOverflow) {
				isOverflow = false;
				Logger.getLogger("UdpPacketList").info("Buffer Overflow Stop: packet "+absWritePos);
			}
		}
	
		int writePos = absWritePos % packetList.length;
		if (writePos >= packetList.length) {
			writePos -= packetList.length;
		}
	
		UdpPacket returnPacket = packetList[writePos];
			
		packetList[writePos] = packet;
		System.out.println(writePos);
		packetUsedList[writePos] = true;

		return returnPacket;
	}
	
	final int stopDistance = 1;
	public void stop()
	{
		// damit die Puffer leer geschrieben werden
		isStopped = true;
		readWriteDistance = stopDistance;
	}
	
	public boolean checkReadWriteDistance()
	{
		if (maxWritePos - absReadPos < readWriteDistance) return false;  
		                                             else return true;
	}
	
	public UdpPacket read()
	{
		//bool isFirstTry = true;
		int readPos;	
		while (true) {	
			if (absReadPos >= maxWritePos) {
				if (isStopped) return null;
					try {
                        // bei Unsynced wir gewartet bis Daten da sind bzw. bis IsStopped
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
					continue;
			}
			readPos = absReadPos % packetList.length;
			if (packetUsedList[readPos] == true) {
				if (isPacketLost) {
					isPacketLost = false;
					Logger.getLogger("UdpPacketList").error(lostCount+" packets lost");
				}
				break;
			}
			if (isStopped) return null;
			if (!isPacketLost) {
				isPacketLost = true;
				lostCount = 1;
				Logger.getLogger("UdpPacketList").error(maxWritePos+" "+absReadPos+" lost");
			} else {
				lostCount++;
			}	
			absReadPos++;
		}

		packetUsedList[readPos] = false;
		UdpPacket returnPacket = packetList[readPos];
		packetList[readPos] = lastReadPacket;
		lastReadPacket = returnPacket;
		absReadPos++;
		if (returnPacket == null) {
		    Logger.getLogger("UdpPacketList").error("acketList returnPacket == null");
		}
		return returnPacket;
	}
}

