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

//TODO Wird Programm beendet muss auch die Aufnahme beendet werden, oder?

//TODO Fehler wenn Aufnahme nicht gestartet werden kann und noch mal auf den
// Aufnahmebutton gedrückt wird

//TODO Es kommt ein Fehler bei Pfadlängen größer 256, muss überprüft werden

//TODO Laufende Programme werden nicht selektiert wenn sie der letzte Eintrag in der Liste sind --------- erledigt

//TODO EPG Reset funkt nicht

//TODO Aufnahme Tab in der alle Informationen stehen (Log, Dateien inkl. Größe

// usw.)
/*
 * Kennzeichen ob Aufnahme läuft oder nicht Ordner Log Video Dateien (Größe)
 * Audio Dateien (Größe und Typ)
 */

import java.io.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import model.BORecordArgs;

import org.apache.log4j.Logger;

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
	public Date stopTime;
	private static final String EPGFILENAME = "epg.txt";

	public RecordControl(BORecordArgs args, ControlProgramTab control) {
		recordArgs = args;
		controlProgramTab = control;
		this.detectRecord();
	}

	private boolean detectRecord() {
		if (controlProgramTab.isTvMode()
				&& ControlMain.getSettings().getStreamingEngine() == 0) {
			record = new UdpRecord(recordArgs, this);
			return true;
		}
		if (controlProgramTab.isTvMode()
				&& ControlMain.getSettings().getStreamingEngine() == 1) {
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

		saveEPGInfos();
		record.start();

		if (recordArgs.isQuickRecord()) {
			long millis = new Date().getTime();
			stopTime = new Date(millis + 1500000);
			controlProgramTab.setRecordStoptTime(stopTime);
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
			if (title != null)
			{
				String info1 = recordArgs.getEpgInfo1();
				if (!info1.startsWith(title))
				{
					epg.append(title);
					epg.append("\n");
				}
			}
			
			String info1 = recordArgs.getEpgInfo1();
			if (info1 != null && !info1.equals(title))
			{
				epg.append(info1);
				epg.append("\n");
			}
			String info2 = recordArgs.getEpgInfo2();
			if (info2 != null)
			{
				epg.append(info2);
			}
			
			String file = getDirectory().toString() + File.separatorChar
					+ EPGFILENAME;

			File f = new File(file);
			
			print = new PrintStream(file);
			
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
			if (new Date().getTime() - stopTime.getTime() > 0) {
				running = false;
			}
		}
		stopRecord();
	}

	public void stopRecord() {
		record.stop();
		controlProgramTab.getMainView().getTabProgramm().stopRecordModus();
		controlProgramTab.getMainView().setSystrayDefaultIcon();
		if (ControlMain.getSettings().isStartPX() && record.getFiles() != null
				&& record.getFiles().length > 0) {
			this.startProjectX();
			Logger.getLogger("RecordControl").info(
					ControlMain.getProperty("msg_startPX"));
		}
		isRunning = false;
	}

	public void startProjectX() {
//		ControlProjectXTab control = new ControlProjectXTab(controlProgramTab.getMainView(), record.getFiles());
//		control.initialize();
	}

	public String getFileName() {
		if (this.fileName == null) {
			SimpleDateFormat f = new SimpleDateFormat("dd-MM-yy_HH-mm");
			Date now = new Date();
			String date = f.format(now);

			BORecordArgs args = this.recordArgs;
			if (args.getEpgTitle() != null) {
				fileName = date + " " + args.getSenderName() + " "
						+ args.getEpgTitle();
			} else {
				fileName = date + " " + args.getSenderName();
			}
		}
		return SerFormatter.ersetzeUmlaute(fileName.replace(' ', '_'));
	}

	public File getDirectory() {
		if (directory == null) {
			directory = new File(ControlMain.getSettings().getSavePath(),
					SerFormatter
							.ersetzeUmlaute(getFileName().replace(' ', '_')));
			directory.mkdir();
		}
		return directory;
	}

}