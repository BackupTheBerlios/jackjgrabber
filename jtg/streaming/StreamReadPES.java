package streaming;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import control.ControlMain;

/**
 * @author Alexander Geist
 *
 */
public class StreamReadPES extends Thread{

	Socket socket;
	String pid;
	int port = 31338;
	BufferedOutputStream fileOutput;
	byte[] buffer = new byte[0];
	PipedOutputStream outputPipe;
	String boxIp;
	private boolean runFlag;
	
	public StreamReadPES(String pid, PipedInputStream inPipe, BufferedOutputStream out) {
		this.setPid(Integer.toHexString(Integer.parseInt(pid)));
		this.setBoxIp(ControlMain.getBoxIpOfSelectedBox());
		this.setFileOutput(out);
		this.setRunFlag(true);
		try {
			this.setOutputPipe(new PipedOutputStream(inPipe));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		PrintWriter out;
		try {
			Socket socket = new Socket(this.getBoxIp(),port);
			this.setSocket(socket);
			
			out = new PrintWriter(socket.getOutputStream());	
			out.write("GET /0x00"+this.getPid()+" HTTP/1.1\r\n\r\n");
			out.flush();
			
		    this.readStream(socket.getInputStream());
		} catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	
	public void readStream(InputStream inStream) throws IOException
	{
		BufferedInputStream in = new  BufferedInputStream(inStream);
		int length = 0;

		do {
			byte[] temp = new byte[65535];
			length = in.read(temp, 0, 65535);
			buffer = new byte[length];
			System.arraycopy(temp, 0, buffer, 0, length );
			
			this.writeFile(buffer);
		} while (length >= 0 && this.getRunFlag());
	} 
	
	private void writeFile(byte[] output) {	
		try {
			this.getFileOutput().write(output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return Returns the pid.
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * @param pid The pid to set.
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * @return Returns the port.
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @param port The port to set.
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * @return Returns the socket.
	 */
	public Socket getSocket() {
		return socket;
	}
	/**
	 * @param socket The socket to set.
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	/**
	 * @return Returns the outputPipe.
	 */
	public PipedOutputStream getOutputPipe() {
		return outputPipe;
	}
	/**
	 * @param outputPipe The outputPipe to set.
	 */
	public void setOutputPipe(PipedOutputStream outputPipe) {
		this.outputPipe = outputPipe;
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
	public BufferedOutputStream getFileOutput() {
		return fileOutput;
	}
	/**
	 * @param fileOutput The fileOutput to set.
	 */
	public void setFileOutput(BufferedOutputStream fileOutput) {
		this.fileOutput = fileOutput;
	}
	public void setRunFlag (boolean runFlag) {
		this.runFlag = runFlag;
	}
	public boolean getRunFlag() {
		return this.runFlag;
	}
}
