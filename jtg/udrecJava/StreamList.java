/*
StreamList.java by Geist Alexander 

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

/**
 *
 */
class StreamList
{
	public UdpPacketList[] list;
//	public WriterList[] list2; 
	public String avString;
	
	String[] dboxArgs;	
	Record record;

	public StreamList(Record record)
	{
		 this.record = record;
	}

	public int parseDBoxReply(String dboxReply, int spktBufNum)
	{
		dboxArgs = dboxReply.split(" ");

		if (dboxArgs[0].equals("INFO:")) return 0;
		if (!dboxArgs[0].equals("PID")) return -1;  // "EXIT" ist moeglich
		if (dboxArgs.length < 4) return -2;
	  	avString = dboxArgs[1];
		int pidNum = Integer.parseInt(dboxArgs[2]);
		if (pidNum + 3 > dboxArgs.length) return -3;

		list = new UdpPacketList[pidNum];
		for (int i = 0; i < pidNum; i++) {
			if (avString.charAt(i) == 'v') {
				list[i] = new UdpPacketList(5 * 64 * spktBufNum, record,
					5 * 32 * spktBufNum);
			} else {
				list[i] = new UdpPacketList(64 * spktBufNum, record,
					48 * spktBufNum);
			}
		}
		return pidNum;
	}


	public UdpPacket write(UdpPacket packet)
	{
		if (packet == null) return new UdpPacket();
		if (packet.getStream() >= list.length) return packet;
		return ((UdpPacketList[])list)[packet.getStream()].Write(packet);
	}

	public void stop()
	{
		for (int i = 0; i < list.length; i++) {
			((UdpPacketList[])list)[i].stop();
		}
	}
			
}
