package service;

/*
 * SerSettingsHandler.java by Geist Alexander
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
import java.io.*;
import java.util.*;

import model.*;

import org.dom4j.*;

import com.jgoodies.plaf.plastic.*;

import control.*;

public class SerSettingsHandler {

	/**
	 * Auslesen der Werte aus der XMLDatei und Aufbereitung
	 */
	public static BOSettings buildSettings(Document document) {
		BOSettings settings = new BOSettings();
		Element root = document.getRootElement();

		//		Auswertung der User-Settings
		getSettingsLookAndFeel(root, settings);
		getSettingsTheme(root, settings);
		getSettingsStreamingServerPort(root, settings);
		getSettingsStartStreamingServer(root, settings);
		getSettingsSavePath(root, settings);
		getSettingsLocale(root, settings);
		getSettingsPlaybackPlayer(root, settings);
		getJGrabberStreamType(root, settings);
		getUdrecStreamType(root, settings);
		getSettingsStartPX(root, settings);
		getSettingsStreamingEngine(root, settings);
		getSettingsUdrecPath(root, settings);
		getSettingsRecordAllPids(root, settings);
		getSettingsUseAlwaysStandardPlayback(root, settings);
		getSettingsShowLogo(root, settings);
		getSettingsStartFullscreen(root, settings);
		getSettingsUseSysTray(root, settings);
		getSettingsRecordtimeBefore(root, settings);
		getSettingsRecordtimeAfter(root, settings);
		getSettingsAc3ReplaceStereo(root, settings);
		getSettingsUdrecOptions(root, settings);
		getSettingsProjectXPath(root, settings);
		getSettingsStoreEPG(root,settings);
		getSettingsStoreLogAfterRecord(root,settings);
		getSettingsMovieguide(root,settings);
		getSettingsRecordVtxt(root,settings);
		getSettingsStartVlc(root,settings);
		getSettingsVlcPath(root,settings);
		
		settings.setBoxList(buildBoxSettings(root));
		settings.setPlaybackOptions(buildPlaybackSettings(root));
		return settings;
	}
	
	private static void getSettingsStartVlc(Element root, BOSettings settings) {
			Node node = root.selectSingleNode("/settings/startVlc");
			if (node != null) {
				settings.startVlcAtStart = node.getText().equals("true");
			} else {
				SerXMLHandling.setElementInElement(root, "startVlc", "false");
				settings.setStartVlcAtStart(false);
			}
		}
	
	private static void getSettingsVlcPath(Element root, BOSettings settings) {
			Node node = root.selectSingleNode("/settings/vlcPath");
			if (node != null) {
				settings.vlcPath = node.getText();
			} else {
				String path = new File("vlc.exe").getAbsolutePath();
				SerXMLHandling.setElementInElement(root, "vlcPath", path);
				settings.setVlcPath(path);
			}
		}

	private static void getSettingsAc3ReplaceStereo(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/ac3ReplaceStereo");
		if (node != null) {
			settings.ac3ReplaceStereo = node.getText().equals("true");
		} else {
			SerXMLHandling.setElementInElement(root, "ac3ReplaceStereo", "false");
			settings.setAc3ReplaceStereo(true);
		}
	}

	private static void getSettingsUdrecOptions(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/udrecOptions");
		if (node != null) {
			settings.udrecOptions = node.getText();
		} else {
			SerXMLHandling.setElementInElement(root, "udrecOptions", "");
		}
	}

	private static void getSettingsRecordtimeBefore(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/recordTimeBefore");
		if (node != null) {
			settings.recordTimeBefore = node.getText();
		} else {
			SerXMLHandling.setElementInElement(root, "recordTimeBefore", "0");
			settings.setRecordTimeBefore("0");
		}
	}

	private static void getSettingsRecordtimeAfter(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/recordTimeAfter");
		if (node != null) {
			settings.recordTimeAfter = node.getText();
		} else {
			SerXMLHandling.setElementInElement(root, "recordTimeAfter", "0");
			settings.setRecordTimeAfter("0");
		}
	}

	private static void getSettingsShowLogo(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/showLogo");
		if (node != null) {
			settings.showLogo = node.getText().equals("true");
		} else {
			SerXMLHandling.setElementInElement(root, "showLogo", "true");
			settings.setShowLogo(true);
		}
	}

	private static void getSettingsStartFullscreen(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/startFullscreen");
		if (node != null) {
			settings.startFullscreen = node.getText().equals("true");
		} else {
			SerXMLHandling .setElementInElement(root, "startFullscreen", "false");
			settings.setStartFullscreen(false);
		}
	}

	private static void getSettingsUseSysTray(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/useSysTray");
		if (node != null) {
			settings.useSysTray = node.getText().equals("true");
		} else {
			SerXMLHandling.setElementInElement(root, "useSysTray", "false");
			settings.setUseSysTray(false);
		}
	}

	private static void getSettingsStreamingEngine(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/engine");
		if (node != null) {
			settings.streamingEngine = Integer.parseInt(node.getText());
		} else {
			SerXMLHandling.setElementInElement(root, "engine", "0");
			settings.setStreamingEngine(0);
		}
	}

	private static void getSettingsUdrecPath(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/udrecPath");
		if (node != null) {
			settings.udrecPath = node.getText();
		} else {
			String path = new File("udrec.exe").getAbsolutePath();
			SerXMLHandling.setElementInElement(root, "udrecPath", path);
			settings.setUdrecPath(path);
		}
	}

	private static void getSettingsProjectXPath(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/projectXPath");
		if (node != null) {
			settings.projectXPath = node.getText();
		} else {
			String path = new File("ProjectX.jar").getAbsolutePath();
			SerXMLHandling.setElementInElement(root, "projectXPath", path);
			settings.setProjectXPath(path);
		}
	}

	private static void getSettingsStartPX(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/startPX");
		if (node != null) {
			settings.startPX = node.getText().equals("true");
		} else {
			SerXMLHandling.setElementInElement(root, "startPX", "true");
			settings.setStartPX(true);
		}
	}

	private static void getSettingsUseAlwaysStandardPlayback(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/useStandardPlayback");
		if (node != null) {
			settings.alwaysUseStandardPlayback = node.getText().equals("true");
		} else {
			SerXMLHandling.setElementInElement(root, "useStandardPlayback", "true");
			settings.setAlwaysUseStandardPlayback(true);
		}
	}

	private static void getSettingsRecordAllPids(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/recordAllPids");
		if (node != null) {
			settings.recordAllPids = node.getText().equals("true");
		} else {
			SerXMLHandling.setElementInElement(root, "recordAllPids", "true");
			settings.setRecordAllPids(true);
		}
	}
	
	private static void getSettingsRecordVtxt(Element root, BOSettings settings) {
			Node node = root.selectSingleNode("/settings/recordVtxt");
			if (node != null) {
				settings.recordVtxt = node.getText().equals("true");
			} else {
				SerXMLHandling.setElementInElement(root, "recordVtxt", "false");
				settings.setRecordVtxt(false);
			}
		}

	private static void getJGrabberStreamType(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/jgrabberStreamType");
		if (node != null) {
			settings.jgrabberStreamType = node.getText();
		} else {
			SerXMLHandling.setElementInElement(root, "jgrabberStreamType", "PES MPEG-Packetized Elementary");
			settings.setJgrabberStreamType("PES");
		}
	}

	private static void getUdrecStreamType(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/udrecStreamType");
		if (node != null) {
			settings.udrecStreamType = node.getText();
		} else {
			SerXMLHandling.setElementInElement(root, "udrecStreamType", "PES MPEG-Packetized Elementary");
			settings.setUdrecPath("PES");
		}
	}

	private static void getSettingsTheme(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/theme");
		if (node != null) {
			settings.themeLayout = node.getText();
		} else {
			SerXMLHandling.setElementInElement(root, "theme", "ExperienceBlue");
			settings.setThemeLayout("Silver");
		}
	}

	private static void getSettingsLookAndFeel(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/lookandfeel");
		if (node != null) {
			settings.setLookAndFeel(node.getText());
		} else {
			SerXMLHandling.setElementInElement(root, "lookandfeel", PlasticLookAndFeel.class.getName());
			settings.setLookAndFeel(PlasticLookAndFeel.class.getName());
		}
	}
	
	private static void getSettingsStoreEPG(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/storeepg");
		if (node != null) {
			settings.setStoreEPG(node.getText().equals("true"));
		} else {
			SerXMLHandling.setElementInElement(root, "storeepg", "false");
			settings.setStoreEPG(false);
		}
	}	

	private static void getSettingsStoreLogAfterRecord(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/storelogafterrecord");
		if (node != null) {
			settings.setStoreLogAfterRecord(node.getText().equals("true"));
		} else {
			SerXMLHandling.setElementInElement(root, "storelogafterrecord", "false");
			settings.setStoreLogAfterRecord(false);
		}
	}	

	
	private static void getSettingsStreamingServerPort(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/streamingServerPort");
		if (node != null) {
			settings.streamingServerPort = node.getText();
		} else {
			SerXMLHandling.setElementInElement(root, "streamingServerPort", "4000");
			settings.setStreamingServerPort("4000");
		}
	}

	private static void getSettingsStartStreamingServer(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/startStreamingServer");
		if (node != null) {
			settings.startStreamingServer = node.getText().equals("true");
		} else {
			SerXMLHandling.setElementInElement(root, "startStreamingServer", "true");
			settings.setStartStreamingServer(true);
		}
	}

	private static void getSettingsSavePath(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/savePath");
		if (node != null) {
			settings.savePath = node.getText();
		} else {
			String path = new File(System.getProperty("user.home")) .getAbsolutePath();
			SerXMLHandling.setElementInElement(root, "savePath", path);
			settings.setSavePath(path);
		}
	}

	private static void getSettingsLocale(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/locale");
		if (node != null) {
			settings.locale = node.getText();
		} else {
			SerXMLHandling.setElementInElement(root, "locale", "DE");
			settings.setLocale("DE");
		}
	}

	private static void getSettingsPlaybackPlayer(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/playbackPlayer");
		if (node != null) {
			settings.playbackString = node.getText();
		} else {
			SerXMLHandling.setElementInElement(root, "playbackPlayer", "vlc");
			settings.setPlaybackString("vlc");
		}
	}
	
	private static void getSettingsMovieguide(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/mgselectedchannels");
		if (node != null) {
			ArrayList list = new ArrayList();
			StringTokenizer tok = new StringTokenizer(node.getText(),"|");
			while(tok.hasMoreTokens())
			{
				list.add(tok.nextToken());
			}
			settings.setMgSelectedChannels(list);
		} else {
			SerXMLHandling.setElementInElement(root, "mgselectedchannels", "");
			ArrayList list = new ArrayList();
			String[] channels = new String[]{"13TH STREET", "CLASSICA", "DISNEY CHANNEL", "FOX KIDS", "HEIMATKANAL", "HIT24", "JUNIOR",
					"MGM", "PREMIERE 1", "PREMIERE 2", "PREMIERE 3", "PREMIERE 4", "PREMIERE 5", "PREMIERE 6", "PREMIERE 7",
					"PREMIERE KRIMI", "PREMIERE NOSTALGIE", "PREMIERE SERIE", "PREMIERE START", "SCI FI"};
			list.addAll(Arrays.asList(channels));
			
			settings.setMgSelectedChannels(list);
		}
		
		node = root.selectSingleNode("/settings/mgloadtype");
		if (node != null) {
			settings.setMgLoadType(Integer.parseInt(node.getText()));
		} else {
			SerXMLHandling.setElementInElement(root, "mgloadtype", ControlSettingsTabMovieGuide.MGLOADTYPE_AUTO + "");
			settings.setMgLoadType(ControlSettingsTabMovieGuide.MGLOADTYPE_AUTO);
		}
		
		node = root.selectSingleNode("/settings/mgdefault");
		if (node != null) {
			settings.setMgDefault(Integer.parseInt(node.getText()));
		} else {
			SerXMLHandling.setElementInElement(root, "mgdefault", ControlSettingsTabMovieGuide.MGDEFAULTDATE_ALL + "");
			settings.setMgDefault(ControlSettingsTabMovieGuide.MGDEFAULTDATE_ALL);
		}
		
		node = root.selectSingleNode("/settings/mgstoreoriginal");
		if (node != null) {
			settings.setMgStoreOriginal(node.getText().equals("true"));
		} else {
			SerXMLHandling.setElementInElement(root, "mgstoreoriginal", "false");
			settings.setMgStoreOriginal(false);
		}
		
		
	}	

	/**
	 * @param rootElement of the Settings-Document
	 * @return ArrayList of BOBox-Objects
	 */
	private static ArrayList buildBoxSettings(Element rootElement) {
		List boxListNodes = rootElement.selectNodes("//box"); //All Box-Nodes
		ArrayList boxList = new ArrayList(boxListNodes.size());

		for (int i = 0; i < boxListNodes.size(); i++) { //Schleife über die
			// Box-Elemente
			BOBox box = new BOBox();
			Node boxElement = (Node) boxListNodes.get(i);
			List boxValueNodes = boxElement.selectNodes("descendant::*");

			for (int i2 = 0; i2 < boxValueNodes.size(); i2++) { //Schleife über
				// die
				// BoxValue-Elemente
				Node value = (Node) boxValueNodes.get(i2);
				switch (i2) {
				case 0:
					box.dboxIp = (value.getText());
					break;
				case 1:
					box.login = (value.getText());
					break;
				case 2:
					box.password = (value.getText());
					break;
				case 3:
					box.standard = (Boolean.valueOf(value.getText()));
					break;
				}
			}
			if ((box.isStandard().booleanValue())) {
				box.setSelected(true);
			}
			boxList.add(box);
		}
		return boxList;
	}

	/**
	 * @param rootElement  of the Settings-Document
	 * @return ArrayList of BOPlaybackOption-Objects
	 */
	private static ArrayList buildPlaybackSettings(Element rootElement) {
		List playbackListNodes = rootElement.selectNodes("//playbackOption"); //All Box-Nodes
		ArrayList boxList = new ArrayList(playbackListNodes.size());

		for (int i = 0; i < playbackListNodes.size(); i++) { //Schleife über die Box-Elemente
			BOPlaybackOption playbackOption = new BOPlaybackOption();
			Node playbackElement = (Node) playbackListNodes.get(i);
			List playbackValueNodes = playbackElement
					.selectNodes("descendant::*");

			for (int i2 = 0; i2 < playbackValueNodes.size(); i2++) { //Schleife über die BoxValue-Elemente
				Node value = (Node) playbackValueNodes.get(i2);
				switch (i2) {
				case 0:
					playbackOption.name = (value.getText());
					break;
				case 1:
					playbackOption.execString = (value.getText());
					break;
				case 2:
					playbackOption.standard = (Boolean.valueOf(value.getText()));
					break;
				case 3:
					playbackOption.logOutput = (Boolean
							.valueOf(value.getText()));
					break;
				}
			}
			if ((playbackOption.isStandard().booleanValue())) {
			}
			boxList.add(playbackOption);
		}
		return boxList;
	}

	/*
	 * Erstellen eines XML-Settings-Dokuments und spreichern diesen
	 */
	public static void savePlaybackSettings() throws IOException {
		Element settingsDocument = ControlMain.getSettingsDocument().getRootElement();
		Node playbackListRoot = settingsDocument.selectSingleNode("/settings/playbackList");
		if (playbackListRoot != null) {
		    settingsDocument.remove(playbackListRoot);    
		}
		
		//Aufbereitung der Box-Settings
		Element newPlaybackListRoot = DocumentHelper.createElement("playbackList");
		ArrayList playbackList = ControlMain.getSettings().getPlaybackOptions();
	
		for (int i=0; i<playbackList.size(); i++) {
		    BOPlaybackOption playback = (BOPlaybackOption)playbackList.get(i);
			Element boxElement = DocumentHelper.createElement("playbackOption");
			boxElement.addElement("name").addText(playback.getName());
			boxElement.addElement("execString").addText(playback.getExecString());
			boxElement.addElement("standard").addText(playback.isStandard().toString());
			boxElement.addElement("logOutput").addText(playback.isLogOutput().toString());
			newPlaybackListRoot.add(boxElement);
		}
		settingsDocument.add(newPlaybackListRoot);
	}
	
	/*
	 * Erstellen eines XML-Settings-Dokuments und spreichern diesen
	 */
	public static void saveBoxSettings() throws IOException {
		Element settingsDocument = ControlMain.getSettingsDocument().getRootElement();
		Node boxListRoot = settingsDocument.selectSingleNode("/settings/boxList");
		settingsDocument.remove(boxListRoot);
		
		//Aufbereitung der Box-Settings
		Element newBoxListRoot = DocumentHelper.createElement("boxList");
		ArrayList boxList = ControlMain.getSettings().getBoxList();
	
		for (int i=0; i<boxList.size(); i++) {
			BOBox box = (BOBox)boxList.get(i);
			Element boxElement = DocumentHelper.createElement("box");
			boxElement.addElement("boxIp").addText(box.getDboxIp());
			boxElement.addElement("login").addText(box.getLogin());
			boxElement.addElement("password").addText(box.getPassword());
			boxElement.addElement("standard").addText(box.isStandard().toString());
			newBoxListRoot.add(boxElement);
		}
		settingsDocument.add(newBoxListRoot);
	}

	public static void saveUserSettings() throws IOException {
		Element settingsDocument = ControlMain.getSettingsDocument() .getRootElement();
		Node theme = settingsDocument.selectSingleNode("/settings/theme");
		Node lookAndFeel = settingsDocument.selectSingleNode("/settings/lookandfeel");
		Node locale = settingsDocument.selectSingleNode("/settings/locale");
		Node serverPort = settingsDocument.selectSingleNode("/settings/streamingServerPort");
		Node startServer = settingsDocument.selectSingleNode("/settings/startStreamingServer");
		Node savePath = settingsDocument.selectSingleNode("/settings/savePath");
		Node playbackPlayer = settingsDocument.selectSingleNode("/settings/playbackPlayer");
		Node jgrabberStreamType = settingsDocument.selectSingleNode("/settings/jgrabberStreamType");
		Node udrecStreamType = settingsDocument.selectSingleNode("/settings/udrecStreamType");
		Node startPx = settingsDocument.selectSingleNode("/settings/startPX");
		Node engine = settingsDocument.selectSingleNode("/settings/engine");
		Node udrecPath = settingsDocument.selectSingleNode("/settings/udrecPath");
		Node projectXPath = settingsDocument.selectSingleNode("/settings/projectXPath");
		Node recordAllPids = settingsDocument.selectSingleNode("/settings/recordAllPids");
		Node useStandardPlayback = settingsDocument.selectSingleNode("/settings/useStandardPlayback");
		Node useSysTray = settingsDocument.selectSingleNode("/settings/useSysTray");
		Node startFullscreen = settingsDocument.selectSingleNode("/settings/startFullscreen");
		Node showLogo = settingsDocument.selectSingleNode("/settings/showLogo");
		Node recordTimeBefore = settingsDocument.selectSingleNode("/settings/recordTimeBefore");
		Node recordTimeAfter = settingsDocument.selectSingleNode("/settings/recordTimeAfter");
		Node ac3ReplaceStereo = settingsDocument.selectSingleNode("/settings/ac3ReplaceStereo");
		Node udrecOptions = settingsDocument.selectSingleNode("/settings/udrecOptions");
		Node storeEPG = settingsDocument.selectSingleNode("/settings/storeepg");
		Node storeLogAfterRecord = settingsDocument.selectSingleNode("/settings/storelogafterrecord");
		Node recordVtxt = settingsDocument.selectSingleNode("/settings/recordVtxt");
		Node mgSelectedChannels = settingsDocument.selectSingleNode("/settings/mgselectedchannels");
		Node mgLoadType = settingsDocument.selectSingleNode("/settings/mgloadtype");
		Node mgDefault = settingsDocument.selectSingleNode("/settings/mgdefault");
		Node mgStoreOriginal = settingsDocument.selectSingleNode("/settings/mgstoreoriginal");
		Node vlcPath = settingsDocument.selectSingleNode("/settings/vlcPath");
		Node startVlc = settingsDocument.selectSingleNode("/settings/startVlc");
		
		startVlc.setText(Boolean.toString(ControlMain.getSettings().isStartVlcAtStart()));
		vlcPath.setText(ControlMain.getSettings().getVlcPath());
		recordVtxt.setText(Boolean.toString(ControlMain.getSettings().isRecordVtxt()));
		projectXPath.setText(ControlMain.getSettings().getProjectXPath());
		ac3ReplaceStereo.setText(Boolean.toString(ControlMain.getSettings().isAc3ReplaceStereo()));
		udrecOptions.setText(ControlMain.getSettings().getUdrecOptions());
		recordTimeBefore.setText(ControlMain.getSettings().getRecordTimeBefore());
		recordTimeAfter.setText(ControlMain.getSettings().getRecordTimeAfter());
		useSysTray.setText(Boolean.toString(ControlMain.getSettings().isUseSysTray()));
		startFullscreen.setText(Boolean.toString(ControlMain.getSettings().isStartFullscreen()));
		showLogo.setText(Boolean.toString(ControlMain.getSettings().isShowLogo()));
		useStandardPlayback.setText(Boolean.toString(ControlMain.getSettings().isAlwaysUseStandardPlayback()));
		recordAllPids.setText(Boolean.toString(ControlMain.getSettings().isRecordAllPids()));
		engine.setText(Integer.toString(ControlMain.getSettings().getStreamingEngine()));
		udrecPath.setText(ControlMain.getSettings().getUdrecPath());
		startPx.setText(Boolean.toString(ControlMain.getSettings().isStartPX()));
		jgrabberStreamType.setText(ControlMain.getSettings().getJgrabberStreamType());
		udrecStreamType.setText(ControlMain.getSettings().getUdrecStreamType());
		playbackPlayer.setText(ControlMain.getSettings().getPlaybackString());
		startServer.setText(Boolean.toString(ControlMain.getSettings().isStartStreamingServer()));
		savePath.setText(ControlMain.getSettings().getSavePath());
		serverPort.setText(ControlMain.getSettings().getStreamingServerPort());
		theme.setText(ControlMain.getSettings().getThemeLayout());
		lookAndFeel.setText(ControlMain.getSettings().getLookAndFeel());
		locale.setText(ControlMain.getSettings().getLocale());
		storeEPG.setText(Boolean.toString(ControlMain.getSettings().isStoreEPG()));
		storeLogAfterRecord.setText(Boolean.toString(ControlMain.getSettings().isStoreLogAfterRecord()));

		ArrayList l = ControlMain.getSettings().getMgSelectedChannels();
		StringBuffer channelList = new StringBuffer();
		if (l != null)
		{
			Iterator it = l.iterator();
			while(it.hasNext()) {
				channelList.append(it.next());
				if (it.hasNext())
				{
					channelList.append("|");
				}
			}
		}
		
		mgSelectedChannels.setText(channelList.toString());
		mgLoadType.setText(ControlMain.getSettings().getMgLoadType() + "");
		mgDefault.setText(ControlMain.getSettings().getMgDefault() + "");
		mgStoreOriginal.setText(ControlMain.getSettings().isMgStoreOriginal() + "");
		
	}

	public static void saveAllSettings() throws IOException {
		saveUserSettings();
		saveBoxSettings();
		savePlaybackSettings();
		SerXMLHandling.saveXMLFile(new File(ControlMain.settingsFilename), ControlMain.getSettingsDocument());
	}
}