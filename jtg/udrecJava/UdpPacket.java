package udrecJava;

import java.net.DatagramPacket;

/**
 * @author Geist Alexander
 *
 */
class UdpPacket
{
	byte[] buffer = new byte[1472];
	DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
	int dataOffset = 8;
	int UsedLength = 1472;
	
	public int length() {
	    return buffer.length;
	}
	
	public int getPacketPos() {
		return buffer[0];
	}
	 
	public int getPacketStatus() {
		return buffer[1];
	}
	
	public int getSPktBuf() {
		return buffer[2];
	}
		
	public int getStream() {
		return buffer[3];
	}
	
	public int getStreamPacket() {
		return (((buffer[4] * 256 + buffer[5]) * 256 + 
				       buffer[6]) * 256) + buffer[7];
	}
	
	public byte[] getUsedData() {
	    byte[] writeBuffer = new byte[1464];
	    System.arraycopy(buffer, dataOffset, writeBuffer, 0, length()-dataOffset);
	    return writeBuffer;
	}
}

