package streaming;
/*
RecordControl.java by Geist Alexander 

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import model.BORecordArgs;
import model.BOTimer;

import org.apache.log4j.Logger;

import service.SerExternalProcessHandler;
import service.SerFormatter;
import service.SerHelper;
import service.SerProcessStopListener;
import service.SerTimerHandler;
import control.ControlMain;
import control.ControlProgramTab;

public class RecordControl extends Thread implements SerProcessStopListener {
	Record record;
	public boolean isRunning = true;
	public boolean tvMode;
	ControlProgramTab controlProgramTab;
	BORecordArgs recordArgs;
	String fileName;
	File directory;
    public String initSptsStatus;
	private static final String EPGFILENAME = "epg.txt";

	public RecordControl(BORecordArgs args, ControlProgramTab control) throws IOException {
	    tvMode = ControlMain.getBoxAccess().isTvMode();
		recordArgs = args;
		controlProgramTab = control;
		this.detectRecord();
        this.initializeSptsStatus();
	}

	private void detectRecord() {
		if (tvMode) {
            if (recordArgs.getLocalTimer().getStreamingEngine() == 0) {
                record = new UdpRecord(recordArgs, this);
            } else if (recordArgs.getLocalTimer().getStreamingEngine() == 1) {
                record = new UdrecRecord(recordArgs, this);
            } else if (recordArgs.getLocalTimer().getStreamingEngine() == 2) {
                record = new VlcRecord(recordArgs, this);
            }
		} else {
            record = new TcpRecord(recordArgs, this); 
        }
	}
    
    private void initializeSptsStatus() {
        initSptsStatus=ControlMain.getBoxAccess().getSptsStatus();
        if (initSptsStatus.equals("pes") && record.streamType.equals("TS")) {
            ControlMain.getBoxAccess().setSptsStatus("spts");
        } else if (initSptsStatus.equals("spts") && record.streamType.equals("PES")) {
            ControlMain.getBoxAccess().setSptsStatus("pes");
        }
    }

    private void setOldSptsStatus() {
        if (!initSptsStatus.equals(ControlMain.getBoxAccess().getSptsStatus())) {
            ControlMain.getBoxAccess().setSptsStatus(initSptsStatus);
        }
    }
    
	/*
	 * Kontrolle der Stopzeit einer Sofortaufnahme
	 */
	public void run() {

		if (recordArgs.getLocalTimer().isStoreEPG()) {
			saveEPGInfos();
		}

		try {
			if (recordArgs.getLocalTimer().isStopPlaybackAtRecord()) {
				ControlMain.getBoxAccess().setRecordModusWithPlayback();
			} else {
				ControlMain.getBoxAccess().setRecordModus();
			}
		} catch (IOException e) {
			Logger.getLogger("RecordControl").error(e.getMessage());
		}
		record.start();

		if (recordArgs.isQuickRecord()) {
			waitForStop();
		}
	}

	/**
	 * speichert die EPG Informationen in einer Datei
	 *  
	 */
	private void saveEPGInfos() {

		PrintStream print = null;
		try {

			StringBuffer epg = new StringBuffer();

			String title = recordArgs.getEpgTitle();
			if (title != null) {
				String info1 = recordArgs.getEpgInfo1();
				if (info1 == null) {
					info1 = "";
				}
				if (!info1.startsWith(title)) {
					epg.append(title);
					epg.append("\n");
				}
			}

			String info1 = recordArgs.getEpgInfo1();
			if (info1 != null && !info1.equals(title)) {
				epg.append(info1);
				epg.append("\n");
			}
			String info2 = recordArgs.getEpgInfo2();
			if (info2 != null) {
				epg.append(info2);
			}

			String file = getDirectory().toString() + File.separatorChar + EPGFILENAME;
			print = new PrintStream(new FileOutputStream(file));

			StringTokenizer tok = new StringTokenizer(epg.toString(), "\n");
			while (tok.hasMoreTokens()) {
				print.println(tok.nextToken());
			}

		} catch (Exception e) {
			Logger.getLogger("RecordControl").error(e);
		} finally {
			if (print != null) {
				print.close();
			}
		}
	}

	public void processStopped(int exitCode) {
		this.checkForShutdown();
	}

	private void waitForStop() {
		boolean running = true;
		while (running) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			if (new Date().getTime() - controlProgramTab.getRecordStopTime().getTime() > 0) {
				running = false;
			}
		}
		controlProgramTab.stopRecord();
	}

	public void stopRecord() {
        BOTimer timer = recordArgs.getLocalTimer().getMainTimer();
        if (timer != null) {  //Lokal-Timer Aufnahme
            timer.setModifiedId("remove");
            SerTimerHandler.saveTimer(timer, true);
            LocalTimerRecordDaemon.running=false;   
        }

		record.stop();
		if (recordArgs.getLocalTimer().isStartPX() && record.getFiles() != null && record.getFiles().size() > 0) {
			Logger.getLogger("RecordControl").info(ControlMain.getProperty("msg_startPX"));
			this.startProjectX();
		} else {
			this.checkForShutdown();
		}
		isRunning = false;
		try {
			ControlMain.getBoxAccess().stopRecordModus();
		} catch (IOException e) {
			Logger.getLogger("RecordControl").error(e.getMessage());
		}
        this.setOldSptsStatus();
	}

	private void checkForShutdown() {
		if (recordArgs.isQuickRecord() && controlProgramTab.isShutdownAfterRecord()) {
			ControlMain.shutdownPC();
		}
		if (!recordArgs.isQuickRecord() && recordArgs.getLocalTimer().isShutdownAfterRecord()) {
			ControlMain.shutdownPC();
		}
	}

	public void startProjectX() {
		ArrayList files = record.getFiles();
		String[] param = new String[3 + files.size()];
		String separator = System.getProperty("file.separator");

		param[0] = System.getProperty("java.home") + separator + "bin" + separator + "java";
		param[1] = "-jar";
		param[2] = ControlMain.getSettingsPath().getProjectXPath();

		for (int i = 0; i < files.size(); i++) {
			param[i + 3] = (String) files.get(i);
		}
		SerExternalProcessHandler.startProcess(this, "ProjectX", param, true);
	}

	public String getFileName() {
		if (this.fileName == null) {
			String pattern = recordArgs.getLocalTimer().getFilePattern();

			if (pattern == null || pattern.length() == 0) {
				pattern = recordArgs.getLocalTimer().getDirPattern();
			}

			fileName = SerHelper.createFileName(recordArgs, pattern);

			// create directory
			String dirName = SerHelper.createFileName(recordArgs, recordArgs.getLocalTimer().getDirPattern());

			directory = new File(recordArgs.getLocalTimer().getSavePath(), SerFormatter.removeInvalidCharacters(dirName));
			directory.mkdir();
		}
		return SerFormatter.removeInvalidCharacters(fileName);
	}

	public File getDirectory() {
		if (directory == null) {
			getFileName();
		}
		return directory;
	}
}