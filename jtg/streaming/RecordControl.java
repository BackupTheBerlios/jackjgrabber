package streaming;
/*
 * RecordControl.java by Geist Alexander
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 675 Mass
 * Ave, Cambridge, MA 02139, USA.
 *  
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import model.BORecordArgs;

import org.apache.log4j.Logger;

import service.SerExternalProcessHandler;
import service.SerFormatter;
import control.ControlMain;
import control.ControlProgramTab;

public class RecordControl extends Thread {
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
		}
		isRunning = false;
	}

	public void startProjectX() {
		String separator = System.getProperty("file.separator");
		ArrayList files = record.getFiles();
		String fileString = new String();
		for (int i = 0; i < files.size(); i++) {
			fileString += ((String) files.get(i)) + " ";
		}

		Object[] args = {System.getProperty("java.home") + separator + "bin" + separator + "java -jar",
					ControlMain.getSettingsPath().getProjectXPath(),
					//"-g",
					fileString};
		MessageFormat form = new MessageFormat("{0} {1} {2}");
		SerExternalProcessHandler.startProcess("ProjectX", form.format(args), true);
	}

	public String getFileName() {
		if (this.fileName == null) {
			SimpleDateFormat f = new SimpleDateFormat("dd-MM-yy_HH-mm");
			String date = f.format(new Date());

			BORecordArgs args = this.recordArgs;
			if (args.getEpgTitle() != null) {
				Object[] obj = {date, args.getSenderName(), args.getEpgTitle()};
				MessageFormat form = new MessageFormat("{0}_{1}_{2}");
				fileName = form.format(obj);
			} else {
				fileName = date + "_" + args.getSenderName();
			}
		}
		return SerFormatter.removeInvalidCharacters(fileName);
	}

	public File getDirectory() {
		if (directory == null) {
			directory = new File(ControlMain.getSettingsPath().getSavePath(), SerFormatter.removeInvalidCharacters(getFileName()));
			directory.mkdir();
		}
		return directory;
	}
}