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
import java.awt.Dimension;
import java.awt.Point;
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
		
		getSettingsLayout(root,settings);
		
		settings.getMainSettings().setBoxList(buildBoxSettings(root));
		settings.getPlaybackSettings().setPlaybackOptions(buildPlaybackSettings(root));
		return settings;
	}
	
	private static void getSettingsStartVlc(Element root, BOSettings settings) {
			Node node = root.selectSingleNode("/settings/startVlc");
			if (node != null) {
				settings.getMainSettings().startVlcAtStart = node.getText().equals("true");
			} else {
				SerXMLHandling.setElementInElement(root, "startVlc", "false");
				settings.getMainSettings().setStartVlcAtStart(false);
			}
		}
	
	private static void getSettingsVlcPath(Element root, BOSettings settings) {
			Node node = root.selectSingleNode("/settings/vlcPath");
			if (node != null) {
				settings.getPathSettings().vlcPath = node.getText();
			} else {
				String path = new File("vlc.exe").getAbsolutePath();
				SerXMLHandling.setElementInElement(root, "vlcPath", path);
				settings.getPathSettings().setVlcPath(path);
			}
		}

	private static void getSettingsAc3ReplaceStereo(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/ac3ReplaceStereo");
		if (node != null) {
			settings.getRecordSettings().ac3ReplaceStereo = node.getText().equals("true");
		} else {
			SerXMLHandling.setElementInElement(root, "ac3ReplaceStereo", "false");
			settings.getRecordSettings().setAc3ReplaceStereo(true);
		}
	}

	private static void getSettingsUdrecOptions(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/udrecOptions");
		if (node != null) {
			settings.getRecordSettings().udrecOptions = node.getText();
		} else {
			SerXMLHandling.setElementInElement(root, "udrecOptions", "");
		}
	}

	private static void getSettingsRecordtimeBefore(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/recordTimeBefore");
		if (node != null) {
			settings.getRecordSettings().recordTimeBefore = node.getText();
		} else {
			SerXMLHandling.setElementInElement(root, "recordTimeBefore", "0");
			settings.getRecordSettings().setRecordTimeBefore("0");
		}
	}

	private static void getSettingsRecordtimeAfter(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/recordTimeAfter");
		if (node != null) {
			settings.getRecordSettings().recordTimeAfter = node.getText();
		} else {
			SerXMLHandling.setElementInElement(root, "recordTimeAfter", "0");
			settings.getRecordSettings().setRecordTimeAfter("0");
		}
	}

	private static void getSettingsShowLogo(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/showLogo");
		if (node != null) {
			settings.getMainSettings().showLogo = node.getText().equals("true");
		} else {
			SerXMLHandling.setElementInElement(root, "showLogo", "true");
			settings.getMainSettings().setShowLogo(true);
		}
	}

	private static void getSettingsStartFullscreen(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/startFullscreen");
		if (node != null) {
			settings.getMainSettings().startFullscreen = node.getText().equals("true");
		} else {
			SerXMLHandling .setElementInElement(root, "startFullscreen", "false");
			settings.getMainSettings().setStartFullscreen(false);
		}
	}

	private static void getSettingsUseSysTray(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/useSysTray");
		if (node != null) {
			settings.getMainSettings().useSysTray = node.getText().equals("true");
		} else {
			SerXMLHandling.setElementInElement(root, "useSysTray", "false");
			settings.getMainSettings().setUseSysTray(false);
		}
	}

	private static void getSettingsStreamingEngine(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/engine");
		if (node != null) {
			settings.getRecordSettings().streamingEngine = Integer.parseInt(node.getText());
		} else {
			SerXMLHandling.setElementInElement(root, "engine", "0");
			settings.getRecordSettings().setStreamingEngine(0);
		}
	}

	private static void getSettingsUdrecPath(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/udrecPath");
		if (node != null) {
			settings.getPathSettings().udrecPath = node.getText();
		} else {
			String path = new File("udrec.exe").getAbsolutePath();
			SerXMLHandling.setElementInElement(root, "udrecPath", path);
			settings.getPathSettings().setUdrecPath(path);
		}
	}

	private static void getSettingsProjectXPath(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/projectXPath");
		if (node != null) {
			settings.getPathSettings().projectXPath = node.getText();
		} else {
			String path = new File("ProjectX.jar").getAbsolutePath();
			SerXMLHandling.setElementInElement(root, "projectXPath", path);
			settings.getPathSettings().setProjectXPath(path);
		}
	}

	private static void getSettingsStartPX(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/startPX");
		if (node != null) {
			settings.getRecordSettings().startPX = node.getText().equals("true");
		} else {
			SerXMLHandling.setElementInElement(root, "startPX", "true");
			settings.getRecordSettings().setStartPX(true);
		}
	}

	private static void getSettingsUseAlwaysStandardPlayback(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/useStandardPlayback");
		if (node != null) {
			settings.getPlaybackSettings().alwaysUseStandardPlayback = node.getText().equals("true");
		} else {
			SerXMLHandling.setElementInElement(root, "useStandardPlayback", "true");
			settings.getPlaybackSettings().setAlwaysUseStandardPlayback(true);
		}
	}

	private static void getSettingsRecordAllPids(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/recordAllPids");
		if (node != null) {
			settings.getRecordSettings().recordAllPids = node.getText().equals("true");
		} else {
			SerXMLHandling.setElementInElement(root, "recordAllPids", "true");
			settings.getRecordSettings().setRecordAllPids(true);
		}
	}
	
	private static void getSettingsRecordVtxt(Element root, BOSettings settings) {
			Node node = root.selectSingleNode("/settings/recordVtxt");
			if (node != null) {
				settings.getRecordSettings().recordVtxt = node.getText().equals("true");
			} else {
				SerXMLHandling.setElementInElement(root, "recordVtxt", "false");
				settings.getRecordSettings().setRecordVtxt(false);
			}
		}

	private static void getJGrabberStreamType(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/jgrabberStreamType");
		if (node != null) {
			settings.getRecordSettings().jgrabberStreamType = node.getText();
		} else {
			SerXMLHandling.setElementInElement(root, "jgrabberStreamType", "PES MPEG-Packetized Elementary");
			settings.getRecordSettings().setJgrabberStreamType("PES");
		}
	}

	private static void getUdrecStreamType(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/udrecStreamType");
		if (node != null) {
			settings.getRecordSettings().udrecStreamType = node.getText();
		} else {
			SerXMLHandling.setElementInElement(root, "udrecStreamType", "PES MPEG-Packetized Elementary");
			settings.getPathSettings().setUdrecPath("PES");
		}
	}

	private static void getSettingsTheme(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/theme");
		if (node != null) {
			settings.getMainSettings().themeLayout = node.getText();
		} else {
			SerXMLHandling.setElementInElement(root, "theme", "ExperienceBlue");
			settings.getMainSettings().setThemeLayout("Silver");
		}
	}

	private static void getSettingsLookAndFeel(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/lookandfeel");
		if (node != null) {
			settings.getMainSettings().setLookAndFeel(node.getText());
		} else {
			SerXMLHandling.setElementInElement(root, "lookandfeel", PlasticLookAndFeel.class.getName());
			settings.getMainSettings().setLookAndFeel(PlasticLookAndFeel.class.getName());
		}
	}
	
	private static void getSettingsStoreEPG(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/storeepg");
		if (node != null) {
			settings.getRecordSettings().setStoreEPG(node.getText().equals("true"));
		} else {
			SerXMLHandling.setElementInElement(root, "storeepg", "false");
			settings.getRecordSettings().setStoreEPG(false);
		}
	}	

	private static void getSettingsStoreLogAfterRecord(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/storelogafterrecord");
		if (node != null) {
			settings.getRecordSettings().setStoreLogAfterRecord(node.getText().equals("true"));
		} else {
			SerXMLHandling.setElementInElement(root, "storelogafterrecord", "false");
			settings.getRecordSettings().setStoreLogAfterRecord(false);
		}
	}	

	
	private static void getSettingsStreamingServerPort(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/streamingServerPort");
		if (node != null) {
			settings.getRecordSettings().streamingServerPort = node.getText();
		} else {
			SerXMLHandling.setElementInElement(root, "streamingServerPort", "4000");
			settings.getRecordSettings().setStreamingServerPort("4000");
		}
	}

	private static void getSettingsStartStreamingServer(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/startStreamingServer");
		if (node != null) {
			settings.getRecordSettings().startStreamingServer = node.getText().equals("true");
		} else {
			SerXMLHandling.setElementInElement(root, "startStreamingServer", "true");
			settings.getRecordSettings().setStartStreamingServer(true);
		}
	}

	private static void getSettingsSavePath(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/savePath");
		if (node != null) {
			settings.getPathSettings().savePath = node.getText();
		} else {
			String path = new File(System.getProperty("user.home")) .getAbsolutePath();
			SerXMLHandling.setElementInElement(root, "savePath", path);
			settings.getPathSettings().setSavePath(path);
		}
	}

	private static void getSettingsLocale(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/locale");
		if (node != null) {
			settings.getMainSettings().locale = node.getText();
		} else {
			SerXMLHandling.setElementInElement(root, "locale", "DE");
			settings.getMainSettings().setLocale("DE");
		}
	}

	private static void getSettingsPlaybackPlayer(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/playbackPlayer");
		if (node != null) {
			settings.getPlaybackSettings().playbackString = node.getText();
		} else {
			SerXMLHandling.setElementInElement(root, "playbackPlayer", "vlc");
			settings.getPlaybackSettings().setPlaybackString("vlc");
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
			settings.getMovieGuideSettings().setMgSelectedChannels(list);
		} else {
		    SerXMLHandling.setElementInElement(root, "mgselectedchannels", "");
				ArrayList list = new ArrayList();
				String[] channels = new String[]{"13TH STREET", "CLASSICA", "DISNEY CHANNEL", "FOX KIDS", "HEIMATKANAL", "HIT24", "JUNIOR",
						"MGM", "PREMIERE 1", "PREMIERE 2", "PREMIERE 3", "PREMIERE 4", "PREMIERE 5", "PREMIERE 6", "PREMIERE 7",
						"PREMIERE KRIMI", "PREMIERE NOSTALGIE", "PREMIERE SERIE", "PREMIERE START", "SCI FI"};
				list.addAll(Arrays.asList(channels));
				
				settings.getMovieGuideSettings().setMgSelectedChannels(list);
		}
		
		node = root.selectSingleNode("/settings/mgloadtype");
		if (node != null) {
			settings.getMovieGuideSettings().setMgLoadType(Integer.parseInt(node.getText()));
		} else {
			SerXMLHandling.setElementInElement(root, "mgloadtype", ControlSettingsTabMovieGuide.MGLOADTYPE_AUTO + "");
			settings.getMovieGuideSettings().setMgLoadType(ControlSettingsTabMovieGuide.MGLOADTYPE_AUTO);
		}
		
		node = root.selectSingleNode("/settings/mgdefault");
		if (node != null) {
			settings.getMovieGuideSettings().setMgDefault(Integer.parseInt(node.getText()));
		} else {
			SerXMLHandling.setElementInElement(root, "mgdefault", ControlSettingsTabMovieGuide.MGDEFAULTDATE_ALL + "");
			settings.getMovieGuideSettings().setMgDefault(ControlSettingsTabMovieGuide.MGDEFAULTDATE_ALL);
		}
		
		node = root.selectSingleNode("/settings/mgstoreoriginal");
		if (node != null) {
			settings.getMovieGuideSettings().setMgStoreOriginal(node.getText().equals("true"));
		} else {
			SerXMLHandling.setElementInElement(root, "mgstoreoriginal", "false");
			settings.getMovieGuideSettings().setMgStoreOriginal(false);
		}
	}	
	
	private static void getSettingsLayout(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/screensize");
		if (node != null) {
			String strText = node.getText();
			Dimension dim = new Dimension(Integer.parseInt(strText.substring(0,strText.indexOf(","))),Integer.parseInt(strText.substring(strText.indexOf(",") + 1)));
			settings.getLayoutSettings().setSize(dim);
		} else {
		    SerXMLHandling.setElementInElement(root, "screensize", "800,600");
				settings.getLayoutSettings().setSize(new Dimension(800,600));
		}
		
		node = root.selectSingleNode("/settings/screenpos");
		if (node != null) {
			String text = node.getText();
			if (text != null && text.length() > 0)
			{
				Point pos = new Point(Integer.parseInt(text.substring(0,text.indexOf(","))),Integer.parseInt(text.substring(text.indexOf(",") + 1)));
				settings.getLayoutSettings().setLocation(pos);
			}
		} else {
		    SerXMLHandling.setElementInElement(root, "screenpos", "");
		}
		
		node = root.selectSingleNode("/settings/recordInfoDirectorySplitPos");
		if (node != null) {
			String strText = node.getText();
			settings.getLayoutSettings().setRecordInfoDirectorySplitPos(Integer.parseInt(strText));
		} else {
			SerXMLHandling.setElementInElement(root, "recordInfoDirectorySplitPos", "300");
			settings.getLayoutSettings().setRecordInfoDirectorySplitPos(300);
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
		Node useStandardPlayback = settingsDocument.selectSingleNode("/settings/useStandardPlayback");
		Node playbackPlayer = settingsDocument.selectSingleNode("/settings/playbackPlayer");
		
		if (playbackListRoot != null) {
		    settingsDocument.remove(playbackListRoot);    
		}
		
		//Aufbereitung der Box-Settings
		Element newPlaybackListRoot = DocumentHelper.createElement("playbackList");
		ArrayList playbackList = ControlMain.getSettings().getPlaybackSettings().getPlaybackOptions();
	
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
		
		useStandardPlayback.setText(Boolean.toString(ControlMain.getSettings().getPlaybackSettings().isAlwaysUseStandardPlayback()));
		playbackPlayer.setText(ControlMain.getSettings().getPlaybackSettings().getPlaybackString());
	}
	
	public static void saveMainSettings() throws IOException {
		Element settingsDocument = ControlMain.getSettingsDocument().getRootElement();
		Node theme = settingsDocument.selectSingleNode("/settings/theme");
		Node lookAndFeel = settingsDocument.selectSingleNode("/settings/lookandfeel");
		Node locale = settingsDocument.selectSingleNode("/settings/locale");
		Node startVlc = settingsDocument.selectSingleNode("/settings/startVlc");
		Node useSysTray = settingsDocument.selectSingleNode("/settings/useSysTray");
		Node startFullscreen = settingsDocument.selectSingleNode("/settings/startFullscreen");
		Node showLogo = settingsDocument.selectSingleNode("/settings/showLogo");
		
		startVlc.setText(Boolean.toString(ControlMain.getSettings().getMainSettings().isStartVlcAtStart()));
		useSysTray.setText(Boolean.toString(ControlMain.getSettings().getMainSettings().isUseSysTray()));
		startFullscreen.setText(Boolean.toString(ControlMain.getSettings().getMainSettings().isStartFullscreen()));
		showLogo.setText(Boolean.toString(ControlMain.getSettings().getMainSettings().isShowLogo()));
		theme.setText(ControlMain.getSettings().getMainSettings().getThemeLayout());
		lookAndFeel.setText(ControlMain.getSettings().getMainSettings().getLookAndFeel());
		locale.setText(ControlMain.getSettings().getMainSettings().getLocale());
		saveBoxSettings();
		
	}
	
	public static void saveBoxSettings() throws IOException {
		Element settingsDocument = ControlMain.getSettingsDocument().getRootElement();
		Node boxListRoot = settingsDocument.selectSingleNode("/settings/boxList");
		settingsDocument.remove(boxListRoot);
		
		//Aufbereitung der Box-Settings
		Element newBoxListRoot = DocumentHelper.createElement("boxList");
		ArrayList boxList = ControlMain.getSettings().getMainSettings().getBoxList();
	
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

	public static void saveRecordSettings() throws IOException {
		Element settingsDocument = ControlMain.getSettingsDocument().getRootElement();
		Node serverPort = settingsDocument.selectSingleNode("/settings/streamingServerPort");
		Node startServer = settingsDocument.selectSingleNode("/settings/startStreamingServer");
		Node jgrabberStreamType = settingsDocument.selectSingleNode("/settings/jgrabberStreamType");
		Node udrecStreamType = settingsDocument.selectSingleNode("/settings/udrecStreamType");
		Node startPx = settingsDocument.selectSingleNode("/settings/startPX");
		Node engine = settingsDocument.selectSingleNode("/settings/engine");
		Node recordAllPids = settingsDocument.selectSingleNode("/settings/recordAllPids");
		Node recordTimeBefore = settingsDocument.selectSingleNode("/settings/recordTimeBefore");
		Node recordTimeAfter = settingsDocument.selectSingleNode("/settings/recordTimeAfter");
		Node ac3ReplaceStereo = settingsDocument.selectSingleNode("/settings/ac3ReplaceStereo");
		Node udrecOptions = settingsDocument.selectSingleNode("/settings/udrecOptions");
		Node storeEPG = settingsDocument.selectSingleNode("/settings/storeepg");
		Node storeLogAfterRecord = settingsDocument.selectSingleNode("/settings/storelogafterrecord");
		Node recordVtxt = settingsDocument.selectSingleNode("/settings/recordVtxt");
		
		recordVtxt.setText(Boolean.toString(ControlMain.getSettings().getRecordSettings().isRecordVtxt()));
		ac3ReplaceStereo.setText(Boolean.toString(ControlMain.getSettings().getRecordSettings().isAc3ReplaceStereo()));
		udrecOptions.setText(ControlMain.getSettings().getRecordSettings().getUdrecOptions());
		recordTimeBefore.setText(ControlMain.getSettings().getRecordSettings().getRecordTimeBefore());
		recordTimeAfter.setText(ControlMain.getSettings().getRecordSettings().getRecordTimeAfter());
		recordAllPids.setText(Boolean.toString(ControlMain.getSettings().getRecordSettings().isRecordAllPids()));
		engine.setText(Integer.toString(ControlMain.getSettings().getRecordSettings().getStreamingEngine()));
		startPx.setText(Boolean.toString(ControlMain.getSettings().getRecordSettings().isStartPX()));
		jgrabberStreamType.setText(ControlMain.getSettings().getRecordSettings().getJgrabberStreamType());
		udrecStreamType.setText(ControlMain.getSettings().getRecordSettings().getUdrecStreamType());
		startServer.setText(Boolean.toString(ControlMain.getSettings().getRecordSettings().isStartStreamingServer()));
		serverPort.setText(ControlMain.getSettings().getRecordSettings().getStreamingServerPort());
		storeEPG.setText(Boolean.toString(ControlMain.getSettings().getRecordSettings().isStoreEPG()));
		storeLogAfterRecord.setText(Boolean.toString(ControlMain.getSettings().getRecordSettings().isStoreLogAfterRecord()));
	}
	
	public static void savePathSettings() throws IOException {
		Element settingsDocument = ControlMain.getSettingsDocument().getRootElement();
		Node savePath = settingsDocument.selectSingleNode("/settings/savePath");
		Node udrecPath = settingsDocument.selectSingleNode("/settings/udrecPath");
		Node projectXPath = settingsDocument.selectSingleNode("/settings/projectXPath");
		Node vlcPath = settingsDocument.selectSingleNode("/settings/vlcPath");

		projectXPath.setText(ControlMain.getSettings().getPathSettings().getProjectXPath());
		udrecPath.setText(ControlMain.getSettings().getPathSettings().getUdrecPath());
		savePath.setText(ControlMain.getSettings().getPathSettings().getSavePath());
		vlcPath.setText(ControlMain.getSettings().getPathSettings().getVlcPath());
	}
	
	public static void saveMovieGuideSettings() throws IOException {
		Element settingsDocument = ControlMain.getSettingsDocument().getRootElement();
		
		Node mgSelectedChannels = settingsDocument.selectSingleNode("/settings/mgselectedchannels");
		Node mgLoadType = settingsDocument.selectSingleNode("/settings/mgloadtype");
		Node mgDefault = settingsDocument.selectSingleNode("/settings/mgdefault");
		Node mgStoreOriginal = settingsDocument.selectSingleNode("/settings/mgstoreoriginal");
		
		
		ArrayList l = ControlMain.getSettings().getMovieGuideSettings().getMgSelectedChannels();
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
		mgLoadType.setText(ControlMain.getSettings().getMovieGuideSettings().getMgLoadType() + "");
		mgDefault.setText(ControlMain.getSettings().getMovieGuideSettings().getMgDefault() + "");
		mgStoreOriginal.setText(ControlMain.getSettings().getMovieGuideSettings().isMgStoreOriginal() + "");
	}
	
	public static void saveLayoutSettings() throws IOException {
		Element settingsDocument = ControlMain.getSettingsDocument().getRootElement();
		
		Node screenSize = settingsDocument.selectSingleNode("/settings/screensize");
		Node screenPos = settingsDocument.selectSingleNode("/settings/screenpos");
		Node recordInfoDirectorySplitPos = settingsDocument.selectSingleNode("/settings/recordInfoDirectorySplitPos");
		
		
		Dimension size = ControlMain.getSettings().getLayoutSettings().getSize();
		Point pos = ControlMain.getSettings().getLayoutSettings().getLocation();
		int recordInfoDirectorySplitPosValue = ControlMain.getSettings().getLayoutSettings().getRecordInfoDirectorySplitPos(); 

		if (size != null)
		{
			screenSize.setText(size.width + "," + size.height);
		}
		if (pos != null)
		{
			screenPos.setText(pos.x + "," + pos.y);
		}
		recordInfoDirectorySplitPos.setText("" + recordInfoDirectorySplitPosValue);
	}	

	public static void saveAllSettings() throws IOException {
		savePathSettings();
		saveRecordSettings();
		saveMainSettings();
		saveMovieGuideSettings();
		savePlaybackSettings();
		saveLayoutSettings();
		SerXMLHandling.saveXMLFile(new File(ControlMain.settingsFilename), ControlMain.getSettingsDocument());
	}
}