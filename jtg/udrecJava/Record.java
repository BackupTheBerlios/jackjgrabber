package udrecJava;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import control.ControlMain;

import model.BORecordArgs;

/**
 * @author Alexander Geist
 *
 */
public class Record {

	BORecordArgs recordArgs;
	int TcpPort = 31340;
	int udpPort = 31341;
	int spktBufNum = 16;
	FileOutputStream fileOutput;
	FileOutputStream fileOutput2;
	public boolean IsESSync = false;
	public boolean IsStopped = false;
	public boolean IsStreamWriterExit = false;
	public String BaseFileName = "stream";
	StreamList streams;
	Socket tcpSocket;
	String boxIp;
	boolean runFlag;
	
	public Record(BORecordArgs args) {
		this.setRecordArgs(args);
		this.setBoxIp(ControlMain.getBoxIpOfActiveBox());
		streams = new StreamList(this);
	}
	
	public boolean start() {
		try {
			tcpSocket = new Socket(this.getBoxIp(),31340);
			
			PrintWriter out = new PrintWriter(tcpSocket.getOutputStream());	
			out.write(this.getRequestString());
			out.flush();

			boolean isPid = false;

			do {
				byte[] buffer = new byte[1024];
				
				int length = tcpSocket.getInputStream().read(buffer);
				String replyString = new String(buffer, 0, length).replace("\n", "");
				
				boolean isExit = false;	
				Logger.getLogger("Record").info("from DBox: "+replyString);
				if (replyString.indexOf("EXIT") > -1) isExit = true;
				if (0 < streams.parseDBoxReply(replyString, spktBufNum)) isPid = true;

				if (isExit) {
					IsStopped = true;
					return false;
				}
			} while(!isPid);

			new UdpReceiver(this).start();
			new TcpReceiver(this).start();
			
			out.write("START\n");
			out.flush();
			
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public String getRequestString() {
		StringBuffer cmd = new StringBuffer();
		cmd.append("VIDEO ");
		cmd.append(udpPort);
		cmd.append(" ");
		cmd.append(spktBufNum);
		cmd.append(" ");
		cmd.append(this.getRecordArgs().getBouquetNr());
		cmd.append(" ");
		cmd.append(this.getRecordArgs().getEventId());
		cmd.append(" ");
		cmd.append("\n");
		return cmd.toString();
	}
	
	public void writeFile(byte[] output) {
		try {
			this.getFileOutput().write(output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return Returns the boxIp.
	 */
	public String getBoxIp() {
		return boxIp;
	}
	/**
	 * @param boxIp The boxIp to set.
	 */
	public void setBoxIp(String boxIp) {
		this.boxIp = boxIp;
	}
	/**
	 * @return Returns the fileOutput.
	 */
	public FileOutputStream getFileOutput() {
		if (fileOutput == null) {
			try {
				fileOutput = new FileOutputStream(new File("udrecJava.mpv"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fileOutput;
	}
	
	public FileOutputStream getFileOutput2() {
		if (fileOutput2 == null) {
			try {
				fileOutput2 = new FileOutputStream(new File("udrecJava.mp2"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fileOutput2;
	}
	/**
	 * @param fileOutput The fileOutput to set.
	 */
	public void setFileOutput(FileOutputStream fileOutput) {
		this.fileOutput = fileOutput;
	}
	public void setRunFlag (boolean runFlag) {
		this.runFlag = runFlag;
	}
	public boolean getRunFlag() {
		return this.runFlag;
	}
	/**
	 * @return Returns the recordArgs.
	 */
	public BORecordArgs getRecordArgs() {
		return recordArgs;
	}
	/**
	 * @param recordArgs The recordArgs to set.
	 */
	public void setRecordArgs(BORecordArgs recordArgs) {
		this.recordArgs = recordArgs;
	}
	/**
	 * @return Returns the spktBufNum.
	 */
	public int getSpktBufNum() {
		return spktBufNum;
	}
	/**
	 * @param spktBufNum The spktBufNum to set.
	 */
	public void setSpktBufNum(int spktBufNum) {
		this.spktBufNum = spktBufNum;
	}
	/**
	 * @return Returns the tcpPort.
	 */
	public int getTcpPort() {
		return TcpPort;
	}
	/**
	 * @param tcpPort The tcpPort to set.
	 */
	public void setTcpPort(int tcpPort) {
		TcpPort = tcpPort;
	}
	/**
	 * @return Returns the udpPort.
	 */
	public int getUdpPort() {
		return udpPort;
	}
	/**
	 * @param udpPort The udpPort to set.
	 */
	public void setUdpPort(int udpPort) {
		this.udpPort = udpPort;
	}
}
