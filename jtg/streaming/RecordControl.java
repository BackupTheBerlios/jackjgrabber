package streaming;
/*
 * RecordControl.java by Geist Alexander
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation,
 * Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *  
 */

import java.io.*;
import java.text.*;
import java.util.*;

import model.*;
import model.BOExternalProcess;
import model.BORecordArgs;

import org.apache.log4j.*;

import service.*;
import control.*;
import service.SerProcessStopListener;
import service.SerExternalProcessHandler;
import service.SerFormatter;
import control.ControlMain;
import control.ControlProgramTab;

public class RecordControl extends Thread implements SerProcessStopListener {
	Record record;
	public boolean isRunning = true;
	ControlProgramTab controlProgramTab;
	BORecordArgs recordArgs;
	String fileName;
	File directory;
	private static final String EPGFILENAME = "epg.txt";

	public RecordControl(BORecordArgs args, ControlProgramTab control) {
		recordArgs = args;
		controlProgramTab = control;
		this.detectRecord();
	}

	private boolean detectRecord() {
		if (controlProgramTab.isTvMode() && ControlMain.getSettingsRecord().getStreamingEngine() == 0) {
			record = new UdpRecord(recordArgs, this);
			return true;
		}
		if (controlProgramTab.isTvMode() && ControlMain.getSettingsRecord().getStreamingEngine() == 1) {
			record = new UdrecRecord(recordArgs, this);
			return true;
		} else {
			record = new TcpRecord(recordArgs, this);
			return true;
		}
	}

	/*
	 * Kontrolle der Stopzeit einer Sofortaufnahme
	 */
	public void run() {

		if (ControlMain.getSettingsRecord().isStoreEPG()) {
			saveEPGInfos();
		}

		try {

			if (ControlMain.getSettings().getRecordSettings().isStopPlaybackAtRecord()) {
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

			File f = new File(file);

			print = new PrintStream(new FileOutputStream(file));

			StringTokenizer tok = new StringTokenizer(epg.toString(), "\n");
			while (tok.hasMoreTokens()) {
				print.println(tok.nextToken());
			}

		} catch (Exception e) {
			e.printStackTrace();
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
		record.stop();
		if (ControlMain.getSettingsRecord().isStartPX() && record.getFiles() != null && record.getFiles().size() > 0) {
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
	}

	private void checkForShutdown() {
		if (recordArgs.isQuickRecord() && controlProgramTab.isShutdownAfterRecord()) {
			ControlMain.shutdownPC();
		}
		if (!recordArgs.isQuickRecord() && ControlMain.getSettingsRecord().isShutdownAfterRecord()) {
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

		BOExternalProcess proc = SerExternalProcessHandler.startProcess(this, "ProjectX", param, true);
	}

	public String getFileName() {
		if (this.fileName == null) {

			/*
			 * SimpleDateFormat f = new SimpleDateFormat("dd-MM-yy_HH-mm"); String date = f.format(new Date());
			 * 
			 * BORecordArgs args = this.recordArgs; if (args.getEpgTitle() != null) { Object[] obj = {date, args.getSenderName(),
			 * args.getEpgTitle()}; MessageFormat form = new MessageFormat("{0}_{1}_{2}"); fileName = form.format(obj); } else { fileName =
			 * date + "_" + args.getSenderName(); }
			 */

			String pattern = ControlMain.getSettingsRecord().getFilePattern();

			if (pattern == null || pattern.length() == 0) {
				pattern = ControlMain.getSettingsRecord().getDirPattern();
			}

			fileName = SerHelper.createFileName(recordArgs, pattern);

			// create directory
			String dirName = SerHelper.createFileName(recordArgs, ControlMain.getSettingsRecord().getDirPattern());

			directory = new File(ControlMain.getSettingsPath().getSavePath(), SerFormatter.removeInvalidCharacters(dirName
					.replace(' ', '_')));
			directory.mkdir();
		}
		return SerFormatter.removeInvalidCharacters(fileName.replace(' ', '_'));
	}

	public File getDirectory() {
		if (directory == null) {
			getFileName();
		}
		return directory;
	}
}