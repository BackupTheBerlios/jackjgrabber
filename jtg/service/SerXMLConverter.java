package service;
/*
SerXMLConverter.java by Geist Alexander 

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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.BOBox;
import model.BORecordArgs;
import model.BOSettings;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import control.ControlMain;

public class SerXMLConverter {

	/**
	 * Auslesen der Werte aus der XMLDatei und Aufbereitung
	 */
	public static BOSettings buildSettings(Document document) {	
		BOSettings settings = new BOSettings();	
		Element root = document.getRootElement();
		
//		Auswertung der User-Settings
		getSettingsTheme(root, settings);
		getSettingsStreamingServerPort(root, settings);
		getSettingsStartStreamingServer(root, settings);
		getSettingsSavePath(root, settings);
		getSettingsLocale(root, settings);
		getSettingsPlaybackPlayer(root, settings);
		getSettingsStreamType(root, settings);
		
		settings.setBoxList(buildBoxSettings(root));
		return settings;
	}
	
	private static void getSettingsStreamType(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/streamType");
		if (node != null) {
			settings.streamType=node.getText();
		} else {
			SerXMLHandling.setElementInElement(root,"streamType", "PES");
			settings.setStreamType("PES");
		}
	}
	
	private static void getSettingsTheme(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/theme");
		if (node != null) {
			settings.themeLayout=node.getText();
		} else {
			SerXMLHandling.setElementInElement(root,"theme", "Silver");
			settings.setThemeLayout("Silver");
		}
	}
	
	private static void getSettingsStreamingServerPort(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/streamingServerPort");
		if (node != null) {
			settings.streamingServerPort=node.getText();
		} else {
			SerXMLHandling.setElementInElement(root,"streamingServerPort", "4000");
			settings.setStreamingServerPort("4000");
		}
	}
		
	private static void getSettingsStartStreamingServer(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/startStreamingServer");
		if (node != null) {
			settings.startStreamingServer=Boolean.parseBoolean(node.getText());
		} else {
			SerXMLHandling.setElementInElement(root,"startStreamingServer", "true");
			settings.setStartStreamingServer(true);
		}
	}
		
	private static void getSettingsSavePath(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/savePath");
		if (node != null) {
			settings.savePath=node.getText();
		} else {
			String path = new File(System.getProperty("user.home")).getAbsolutePath();
			SerXMLHandling.setElementInElement(root,"savePath", path);
			settings.setSavePath(path);
		}
	}
		
	private static void getSettingsLocale(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/locale");
		if (node != null) {
			settings.locale=node.getText();
		} else {
			SerXMLHandling.setElementInElement(root,"locale", "DE");
			settings.setLocale("DE");
		}
	}
	
	private static void getSettingsPlaybackPlayer(Element root, BOSettings settings) {
		Node node = root.selectSingleNode("/settings/playbackPlayer");
		if (node != null) {
			settings.playbackString=node.getText();
		} else {
			SerXMLHandling.setElementInElement(root,"playbackPlayer", "vlc");
			settings.setPlaybackString("vlc");
		}
	}
	
	/**
	 * @param rootElement of the Settings-Document
	 * @return ArrayList of BOBox-Objects
	 */
	private static ArrayList buildBoxSettings(Element rootElement) {
		List boxListNodes = rootElement.selectNodes("//box"); //All Box-Nodes
		ArrayList boxList = new ArrayList(boxListNodes.size());
	
		for (int i=0; i<boxListNodes.size(); i++) { //Schleife über die Box-Elemente
			BOBox box = new BOBox();
			Node boxElement = (Node)boxListNodes.get(i);
			List boxValueNodes = boxElement.selectNodes("descendant::*");
	
			for (int i2=0; i2<boxValueNodes.size(); i2++) { //Schleife über die BoxValue-Elemente
				Node value = (Node)boxValueNodes.get(i2);
				switch(i2) {
					case 0: box.setDboxIp(value.getText());
					break;
					case 1: box.setLogin(value.getText());
					break;
					case 2: box.setPassword(value.getText());
					break;
					case 3: box.setStandard(Boolean.valueOf(value.getText()));
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
		Element settingsDocument = ControlMain.getSettingsDocument().getRootElement();
		Node theme = settingsDocument.selectSingleNode("/settings/theme");
		Node locale = settingsDocument.selectSingleNode("/settings/locale");
		Node serverPort = settingsDocument.selectSingleNode("/settings/streamingServerPort");
		Node startServer = settingsDocument.selectSingleNode("/settings/startStreamingServer");
		Node savePath = settingsDocument.selectSingleNode("/settings/savePath");
		Node playbackPlayer = settingsDocument.selectSingleNode("/settings/playbackPlayer");
		Node streamType = settingsDocument.selectSingleNode("/settings/streamType");
		
		streamType.setText(ControlMain.getSettings().getStreamType());
		playbackPlayer.setText(ControlMain.getSettings().getPlaybackString());
		startServer.setText(Boolean.toString(ControlMain.getSettings().isStartStreamingServer()));
		savePath.setText(ControlMain.getSettings().getSavePath());
		serverPort.setText(ControlMain.getSettings().getStreamingServerPort());
		theme.setText(ControlMain.getSettings().getThemeLayout());
		locale.setText(ControlMain.getSettings().getLocale());
	}
	
	public static void saveAllSettings() throws IOException {
		saveUserSettings();
		saveBoxSettings();
		SerXMLHandling.saveSettingsFile(new File(ControlMain.filename));
	}
	/**
	 * @param XML-Document der BOX
	 * @return BORecordArgs
	 */
	public static BORecordArgs parseRecordDocument(Document document) {
		BORecordArgs recordArgs = new BORecordArgs();
		Element root = document.getRootElement();
		
		Element command = (Element)root.selectSingleNode("//record");
		Node channelname = root.selectSingleNode("//channelname");
		Node epgtitle = root.selectSingleNode("//epgtitle");
		Node channelId = root.selectSingleNode("//id");
		Node epgInfo1 = root.selectSingleNode("//info1");
		Node epgInfo2 = root.selectSingleNode("//info2");
		Node epgid = root.selectSingleNode("//epgid");
		Node mode = root.selectSingleNode("//mode");
		Node videopid = root.selectSingleNode("//videopid");
		Element selectedAudiopid = (Element)root.selectSingleNode("//audiopids selected");
		List aPidNodes = selectedAudiopid.selectNodes("//audio");
		Node vtxtpid = root.selectSingleNode("//vtxtpid");
		
		recordArgs.setCommand(command.attributeValue("command"));
		recordArgs.setSenderName(channelname.getStringValue());
		recordArgs.setEpgTitle(epgtitle.getText());
		recordArgs.setChannelId(channelId.getText());
		recordArgs.setEpgInfo1(epgInfo1.getText());
		recordArgs.setEpgInfo2(epgInfo2.getText());
		recordArgs.setEpgId(epgid.getText());
		recordArgs.setMode(mode.getText());
		recordArgs.setVPid(videopid.getText());
		recordArgs.setVideotextPid(vtxtpid.getText());
		
		ArrayList pidList = new ArrayList();
		for( int i=0; i<aPidNodes.size(); i++ ) {
			String[] pidInfo = new String[2];
			Element aPid = (Element)aPidNodes.get(i);
			pidInfo[0] = aPid.attributeValue("pid");
			pidInfo[1] = aPid.attributeValue("name");
			pidList.add(pidInfo);
		}
		recordArgs.setAPids(pidList);
		return recordArgs;
	}
}
