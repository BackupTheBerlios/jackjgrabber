package presentation.recordInfo;
/*
 * 
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

/**
 * Zeigt Informationen zur aktuellen Aufnahme an
 * 
 * @author Reinhard Achleitner
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;

import javax.swing.*;

import presentation.GuiTab;

import service.SerHelper;

import com.jgoodies.forms.builder.*;
import com.jgoodies.forms.layout.*;

import control.*;

public class GuiTabRecordInfo extends GuiTab {

	private ControlRecordInfoTab control;

	private JTextArea state;
	private JTextArea type;
	private JTextArea engine;

	private JTextArea start;

	private JTextArea end;
	private JTextArea video;
	private JTextArea audio;
	private JTextArea other;
	private JTextArea log;
	private JTextArea recordTitle;

	private javax.swing.Timer fileInfoTimer;

	private File currentDir;

	private static final int REFRESH_TIME = 1000; // Refresh Zeit der Dateiinfos
	// in Millisekunden


	public GuiTabRecordInfo(ControlRecordInfoTab control) {
		this.setControl(control);
		initialize();
	}

	/**
	 * erzeugt die GUI
	 *  
	 */
	protected void initialize() {

		FormLayout layout = new FormLayout("pref:grow", // columns
				"pref,pref,pref, pref,pref,pref,f:150:grow"); // rows
		PanelBuilder builder = new PanelBuilder(this, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();

		builder.add(initRecordPanel(), cc.xywh(1, 1, 1, 1));
		builder.add(initStatePanel(), cc.xywh(1, 3, 1, 1));
		builder.add(initFilePanel(), cc.xywh(1, 5, 1, 1));
		builder.add(initLogPanel(), cc.xywh(1, 7, 1, 1));
	}

	private JPanel initRecordPanel() {
		recordTitle = new JTextArea();

		recordTitle.setEditable(false);
		recordTitle.setBorder(BorderFactory.createEtchedBorder());

		JPanel p = new JPanel();
		FormLayout layout = new FormLayout("710:grow", // columns
				"pref,10,pref"); // rows

		p.setLayout(layout);
		PanelBuilder builder = new PanelBuilder(p, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();

		builder.addSeparator(ControlMain.getProperty("label_recordTitle"));
		builder.add(recordTitle, cc.xywh(1, 3, 1, 1));
		return p;
	}

	private JPanel initStatePanel() {
		state = new JTextArea("");
		type = new JTextArea("");
		start = new JTextArea("");
		end = new JTextArea();
		engine = new JTextArea();

		state.setEditable(false);
		type.setEditable(false);
		start.setEditable(false);
		end.setEditable(false);
		engine.setEditable(false);
		state.setBorder(BorderFactory.createEtchedBorder());
		type.setBorder(BorderFactory.createEtchedBorder());
		start.setBorder(BorderFactory.createEtchedBorder());
		end.setBorder(BorderFactory.createEtchedBorder());
		engine.setBorder(BorderFactory.createEtchedBorder());

		JPanel p = new JPanel();
		FormLayout layout = new FormLayout("pref, 20, 120, 20,30,20,100,20,50,pref,f:330:grow", // columns
				"pref, pref,pref,pref,pref,pref"); // rows

		p.setLayout(layout);
		PanelBuilder builder = new PanelBuilder(p, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();

		builder.add(new JLabel(ControlMain.getProperty("label_recordState")), cc.xywh(1, 1, 1, 1));
		builder.add(state, cc.xywh(3, 1, 1, 1));
		builder.add(new JLabel(ControlMain.getProperty("label_recordType")), cc.xywh(5, 1, 1, 1));
		builder.add(type, cc.xywh(7, 1, 1, 1));
		builder.add(new JLabel(ControlMain.getProperty("label_recordEngine")), cc.xywh(9, 1, 1, 1));
		builder.add(engine, cc.xywh(11, 1, 1, 1));

		builder.add(new JLabel(ControlMain.getProperty("label_recordStart")), cc.xywh(1, 3, 1, 1));
		builder.add(start, cc.xywh(3, 3, 1, 1));
		builder.add(new JLabel(ControlMain.getProperty("label_recordEnd")), cc.xywh(5, 3, 1, 1));
		builder.add(end, cc.xywh(7, 3, 1, 1));

		return p;
	}

	private JPanel initFilePanel() {
		video = new JTextArea();
		audio = new JTextArea();
		other = new JTextArea();

		video.setEditable(false);
		audio.setEditable(false);
		other.setEditable(false);

		JPanel p = new JPanel();
		FormLayout layout = new FormLayout("170:grow, 10, 170:grow, 10, 350:grow, pref", // columns
				"pref, 10,f:130:grow"); // rows

		p.setLayout(layout);
		PanelBuilder builder = new PanelBuilder(p, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();

		builder.addSeparator(ControlMain.getProperty("label_recordVideo"), cc.xywh(1, 1, 1, 1));
		builder.add(new JScrollPane(video), cc.xywh(1, 3, 1, 1));

		builder.addSeparator(ControlMain.getProperty("label_recordAudio"), cc.xywh(3, 1, 1, 1));
		builder.add(new JScrollPane(audio), cc.xywh(3, 3, 1, 1));

		builder.addSeparator(ControlMain.getProperty("label_recordOther"), cc.xywh(5, 1, 1, 1));
		builder.add(new JScrollPane(other), cc.xywh(5, 3, 1, 1));
		return p;
	}

	private JPanel initLogPanel() {
		log = new JTextArea() {
			/** automatisch nach unten scrollen */
			public void append(String str) {
				super.append(str);
				scrollRectToVisible(log.getBounds());
			}
		};

		log.setEditable(false);

		JPanel p = new JPanel();
		FormLayout layout = new FormLayout("710:grow", // columns
				"pref, 10,f:120:grow"); // rows

		p.setLayout(layout);
		PanelBuilder builder = new PanelBuilder(p, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();

		builder.addSeparator(ControlMain.getProperty("label_recordLog"), cc.xywh(1, 1, 1, 1));
		builder.add(new JScrollPane(log), cc.xywh(1, 3, 1, 1));
		return p;
	}

	/**
	 * @return ControlProgramTab
	 */
	public ControlRecordInfoTab getControl() {
		return control;
	}

	/**
	 * Sets the control.
	 * 
	 * @param control
	 *            The control to set
	 */
	public void setControl(ControlRecordInfoTab control) {
		this.control = control;
	}

	/**
	 * muss aufgerufen werden wenn eine Aufnahme gestartet wird
	 * 
	 * @param title
	 *            Titel der Aufnahme (Filmtitel)
	 * @param engine
	 *            Verwendete Engine und Einstellung
	 * @param directory
	 *            Verzeichnis in der die Dateien geschrieben werden
	 * @param timer
	 *            true, wenn es eine Timeraufnahme ist
	 */
	public void startRecord(String title, String engine, File directory, boolean timer) {

		// Lösche Log
		log.setText("");
		end.setText("");
		currentDir = directory;
		state.setText(ControlMain.getProperty("label_recordInProgress"));
		state.setForeground(Color.red);
		state.setFont(state.getFont().deriveFont(Font.BOLD));
		start.setText(SimpleDateFormat.getTimeInstance().format(new Date()));

		recordTitle.setText(title);

		setEngine(engine);
		setType(timer);

		// Erzeuge Timer der periodisch die Dateiinfos aktualisiert
		if (fileInfoTimer == null) {
			fileInfoTimer = new javax.swing.Timer(REFRESH_TIME, new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					reloadFileInfos();
				}

			});
		}
		fileInfoTimer.start();

	}

	/**
	 * aktualisiert die Dateiinformationen (wird vom Timer aufgerufen)
	 *  
	 */
	protected void reloadFileInfos() {

		// Lade alle Files
		File[] aFiles = currentDir.listFiles();

		StringBuffer video = new StringBuffer();
		StringBuffer audio = new StringBuffer();
		StringBuffer other = new StringBuffer();
		int videoCount = 0;
		int audioCount = 0;
		int otherCount = 0;

		for (int i = 0; i < aFiles.length; i++) {
			String size = calcSize(aFiles[i].length(), "MB");

			String end = getEnd(aFiles[i]);
			if (SerHelper.isVideo(aFiles[i].getName())) {
				videoCount++;
				video.append("Video " + end + " (" + videoCount + ")  : " + size + "\n");
			} else if (SerHelper.isAudio(aFiles[i].getName())) {
				audioCount++;
				audio.append("Audio " + " " + end + " (" + audioCount + ")  : " + size + "\n");
			} else {
				size = calcSize(aFiles[i].length(), "KB");
				otherCount++;
				other.append(aFiles[i].getName() + ":      " + size + "\n");
			}
		}

		this.video.setText(video.toString());
		this.audio.setText(audio.toString());
		this.other.setText(other.toString());

	}

	/**
	 * @param file
	 * @return
	 */
	private String getEnd(File file) {

		String fileName = file.getAbsolutePath();
		int end = fileName.lastIndexOf(".");
		if (end > -1) {
			return fileName.substring(end + 1);
		}
		return "";
	}

	/**
	 * berechnet die angezeigte Größe einer Datei in der angegebenen Einheit
	 * 
	 * @param size
	 * @param type
	 *            MB für MByte und KB für Kilobyte
	 * @return
	 */
	private String calcSize(long size, String type) {

		double s = size;
		if (type.equals("MB")) {
			s = s / 1024; // kb
			s = s / 1024; // MB
			return NumberFormat.getNumberInstance().format(s) + " " + type;
		} else if (type.equals("KB")) {
			s = s / 1024; // kb
			return NumberFormat.getNumberInstance().format(s) + " " + type;
		}
		return NumberFormat.getNumberInstance().format(s) + " " + type;
	}

	/**
	 * setzt den Status auf Aufnahme beendet und stoppt den Dateiinfo timer
	 * Speichert bei Bedarf das Log
	 */
	public void stopRecord() {
		state.setText(ControlMain.getProperty("label_recordStopped"));
		state.setForeground((Color) UIManager.get("TextArea.foreground"));
		state.setFont(state.getFont().deriveFont(Font.PLAIN));
		end.setText(SimpleDateFormat.getTimeInstance().format(new Date()));

		if (fileInfoTimer != null && fileInfoTimer.isRunning()) {
			fileInfoTimer.stop();
		}

	}

	/**
	 * setzt den Typ der Aufnahme
	 * 
	 * @param timer
	 *            wenn true, handelt es sich um eine Timeraufnahme
	 */
	public void setType(boolean timer) {
		if (timer) {
			type.setText(ControlMain.getProperty("label_recordTimer"));
		} else {
			type.setText(ControlMain.getProperty("label_recordDirect"));
		}
	}

	/**
	 * setzt den Text für die Engine
	 * 
	 * @param engineText
	 */
	public void setEngine(String engineText) {
		engine.setText(engineText);
	}

	/**
	 * @return
	 */
	public JTextArea getLogArea() {

		return log;
	}

	/**
	 * @return
	 */
	public String getTitle() {

		return recordTitle.getText();
	}

	/**
	 * @return Erzeugte Videofiles
	 */
	public String getVideo() {
		return video.getText();
	}

	/**
	 * @return Erzeugte Audiofiles
	 */
	public String getAudio() {
		return audio.getText();
	}

	/**
	 * @return Log für die aktuelle, bzw. beendete Aufnahme
	 */
	public String getLog() {

		return log.getText();
	}

}