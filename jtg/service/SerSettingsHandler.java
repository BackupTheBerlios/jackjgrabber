package service;
/*
 * SerSettingsHandler.java by Geist Alexander
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
import java.beans.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

import model.*;

import org.apache.log4j.*;

import com.jgoodies.plaf.plastic.*;

import control.*;

public class SerSettingsHandler {

	public static void saveAllSettings() throws Exception {

		XMLEncoder dec = new XMLEncoder(new FileOutputStream(new File(ControlMain.getSettingsFilename())));
		dec.writeObject(ControlMain.getSettings());
		dec.flush();
		dec.close();
	}

	public static void readSettings() {
		try {
			XMLDecoder dec = new XMLDecoder(new FileInputStream(ControlMain.getSettingsFilename()),null,new ExceptionListener() {
				public void exceptionThrown(Exception e) {
					Logger.getLogger("ControlMain").error(e.getMessage());
				}
			});

			BOSettings settings = (BOSettings) dec.readObject();
			settings.setSettingsChanged(false);
			ControlMain.setSettings(settings);
			Logger.getLogger("ControlMain").info("Settings found");

		}catch(FileNotFoundException e)
		{
			try {
				// create default settings
				BOSettings set = createStandardSettingsFile(new File(ControlMain.getSettingsFilename()));
				ControlMain.setSettings(set);
				SerSettingsHandler.saveAllSettings();
				
				
			} catch (Exception e1) {
				Logger.getLogger("ControlMain").error("Cant create settings: " + e1.getMessage());
			}
		}
		
		catch (Exception ex) {
			Logger.getLogger("ControlMain").info("old settings not longer supported");
			JOptionPane.showMessageDialog(null,ControlMain.getProperty("msg_oldSettingsNotSupported"));
		}
	}
	
	/**
	 * Erstellen eines neuen XML-Settingsdokumentes mit Defaultwerten
	 */
	public static BOSettings createStandardSettingsFile(File path) throws IOException {
		BOSettings settings = new BOSettings();
		BOSettingsLayout layout = new BOSettingsLayout(settings);
		BOSettingsMain main = new BOSettingsMain(settings);
		BOSettingsMovieGuide mg = new BOSettingsMovieGuide(settings);
		BOSettingsPath pathS = new BOSettingsPath(settings);
		BOSettingsPlayback play = new BOSettingsPlayback(settings);
		BOSettingsRecord rec = new BOSettingsRecord(settings);
		
		settings.setLayoutSettings(layout);
		settings.setMainSettings(main);
		settings.setMovieGuideSettings(mg);
		settings.setPathSettings(pathS);
		settings.setPlaybackSettings(play);
		settings.setRecordSettings(rec);
		settings.standardSettings=true;
		
		
		play.setPlaybackString("d: http://$ip:31339/$vPid,$aPid");
		play.setAlwaysUseStandardPlayback(false);
		play.setPlaybackOptions(new ArrayList());

		pathS.setUdrecPath( new File("udrec.exe").getAbsolutePath());
		pathS.setProjectXPath(new File("ProjectX.jar").getAbsolutePath());
		pathS.setVlcPath(new File("vlc.exe").getAbsolutePath());
		pathS.setShutdownToolPath("");
		pathS.setSavePath( System.getProperty("user.home"));
		
		rec.setStartStreamingServer(true);
		rec.setStreamingServerPort("4000");
		rec.setStreamType("PES MPEG-Packetized Elementary");
		rec.setUdrecStreamType("PES MPEG-Packetized Elementary");
		rec.setStartPX(true);
		rec.setShutdownAfterRecord(false);
		rec.setStreamingEngine(0);
		rec.setRecordTimeAfter("0");
		rec.setRecordTimeBefore("0");
		rec.setAc3ReplaceStereo(false);
		rec.setStereoReplaceAc3(false);
		rec.setUdrecOptions(new BOUdrecOptions());
		rec.setRecordVtxt(false);
		rec.setRecordAllPids(true);
		rec.setStoreEPG(false);
		rec.setStoreLogAfterRecord(false);
		rec.setDirPattern("%DATE YY-MM-DD% %TIME% %CHANNEL% %NAME%");
		rec.setFilePattern("");
		
		main.setThemeLayout("ExperienceBlue");
		main.setLocale("DE");
		main.setShowLogo(true);
		main.setShowLogWindow(true);
		main.setStartFullscreen(false);
		main.setUseSysTray(false);
		main.setStartVlcAtStart(false);
		main.setLookAndFeel(PlasticLookAndFeel.class.getName());
		main.setBoxList(new ArrayList());
		
		ArrayList selChannels = new ArrayList();
		String[] channels = new String[]{"13TH STREET", "CLASSICA", "DISNEY CHANNEL", "FOX KIDS", "HEIMATKANAL", "HIT24", "JUNIOR",
				"MGM", "PREMIERE 1", "PREMIERE 2", "PREMIERE 3", "PREMIERE 4", "PREMIERE 5", "PREMIERE 6", "PREMIERE 7",
				"PREMIERE KRIMI", "PREMIERE NOSTALGIE", "PREMIERE SERIE", "PREMIERE START", "SCI FI"};
		selChannels.addAll(Arrays.asList(channels));
		mg.setMgSelectedChannels(selChannels);
		mg.setMgLoadType(ControlSettingsTabMovieGuide.MGLOADTYPE_ASK);
		mg.setMgDefault(ControlSettingsTabMovieGuide.MGDEFAULTDATE_ALL);
		mg.setMgStoreOriginal(false);
		return settings;
	}	
}