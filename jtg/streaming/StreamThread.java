package streaming;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PipedInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import control.ControlMain;

import model.BORecordArgs;

/**
 * @author Alexander Geist
 *
 */
public class StreamThread extends Thread {
	
	StreamReadPES videoStream, audioStream;
	BufferedOutputStream fileVideoOutput;
	BufferedOutputStream fileAudioOutput;
	String boxIp;
	File videoFile;
	File audioFile;
	int port = 31338;
	PipedInputStream pipeVideoInput;
	PipedInputStream pipeAudioInput; 
	BORecordArgs recordArgs;
	private boolean runFlag;
	
	
	public StreamThread(BORecordArgs args) {
			this.setBoxIp(ControlMain.getBoxIpOfSelectedBox());
			this.setRecordArgs(args);
			this.createFiles();
			this.setPipeVideoInput(new PipedInputStream());
			this.setPipeAudioInput(new PipedInputStream());
	}
	
	public void run() {
		String vPid = this.getRecordArgs().getVPid();
		String[] aPids = (String[])this.getRecordArgs().getAPids().get(0);
		videoStream = new StreamReadPES(vPid, this.getPipeVideoInput(), fileVideoOutput );
		audioStream = new StreamReadPES(aPids[0], this.getPipeAudioInput(), fileAudioOutput);
		videoStream.start();
		audioStream.start();
	}
	
	private void createFiles() {
		SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
	    Date now = new Date();
	    String date = f.format(now);
	    
		BORecordArgs args = this.getRecordArgs();
		String videoFile = date+" "+args.getSenderName()+" "+args.getEpgTitle()+".mpv";
		String audioFile = date+" "+args.getSenderName()+" "+args.getEpgTitle()+".mp2";
		this.setVideoFile(new File(videoFile));
		this.setAudioFile(new File(audioFile));
		try {
			this.setFileVideoOutput(new BufferedOutputStream(new FileOutputStream(this.getVideoFile())));
			this.setFileAudioOutput(new BufferedOutputStream(new FileOutputStream(this.getAudioFile())));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return Returns the fileOutput.
	 */
	public BufferedOutputStream getFileVideoOutput() {
		return fileVideoOutput;
	}
	/**
	 * @param fileOutput The fileOutput to set.
	 */
	public void setFileVideoOutput(BufferedOutputStream out) {
		this.fileVideoOutput = out;
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
	 * @return Returns the audioStream.
	 */
	public StreamReadPES getAudioStream() {
		return audioStream;
	}
	/**
	 * @param audioStream The audioStream to set.
	 */
	public void setAudioStream(StreamReadPES audioStream) {
		this.audioStream = audioStream;
	}
	/**
	 * @return Returns the PESStream.
	 */
	public StreamReadPES getVideoStream() {
		return videoStream;
	}
	/**
	 * @param PESStream The PESStream to set.
	 */
	public void setVideoStream(StreamReadPES videoStream) {
		this.videoStream = videoStream;
	}
	/**
	 * @return Returns the pipeVideoInput.
	 */
	public PipedInputStream getPipeVideoInput() {
		return pipeVideoInput;
	}
	/**
	 * @param pipeVideoInput The pipeVideoInput to set.
	 */
	public void setPipeVideoInput(PipedInputStream pipeInput) {
		this.pipeVideoInput = pipeInput;
	}
	/**
	 * @return Returns the pipeAudioInput.
	 */
	public PipedInputStream getPipeAudioInput() {
		return pipeAudioInput;
	}
	/**
	 * @param pipeAudioInput The pipeAudioInput to set.
	 */
	public void setPipeAudioInput(PipedInputStream pipeAudioInput) {
		this.pipeAudioInput = pipeAudioInput;
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
	 * @return Returns the audioFile.
	 */
	public File getAudioFile() {
		return audioFile;
	}
	/**
	 * @param audioFile The audioFile to set.
	 */
	public void setAudioFile(File audioFile) {
		this.audioFile = audioFile;
	}
	/**
	 * @return Returns the videoFile.
	 */
	public File getVideoFile() {
		return videoFile;
	}
	/**
	 * @param videoFile The videoFile to set.
	 */
	public void setVideoFile(File videoFile) {
		this.videoFile = videoFile;
	}
	/**
	 * @return Returns the fileAudioOutput.
	 */
	public BufferedOutputStream getFileAudioOutput() {
		return fileAudioOutput;
	}
	/**
	 * @param fileAudioOutput The fileAudioOutput to set.
	 */
	public void setFileAudioOutput(BufferedOutputStream fileAudioOutput) {
		this.fileAudioOutput = fileAudioOutput;
	}
	public void setRunFlag (boolean flag) {
		this.runFlag = flag;
		if (!flag) {
			this.getVideoStream().setRunFlag(false);
			this.getAudioStream().setRunFlag(false);
		}
	}
	public boolean getRunFlag() {
		return this.runFlag;
	}
}
