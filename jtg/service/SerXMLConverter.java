package service;

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
/**
 * @author Geist Alexander
 * 
 */
public class SerXMLConverter {

	/**
	 * Auslesen der Werte aus der XMLDatei und Aufbereitung
	 */
	public static BOSettings buildSettings(Document document) {	
		BOSettings settings = new BOSettings();	
		Element root = document.getRootElement();
		
		//Auswertung der User-Settings
		settings.setThemeLayout(root.selectSingleNode("/settings/theme").getText());
		settings.setStreamingServerPort(root.selectSingleNode("/settings/streamingServerPort").getText());
		settings.setLocale(root.selectSingleNode("/settings/locale").getText());
		
		//Auswertund der Box-Settings
		List boxListNodes = root.selectNodes("//box"); //All Box-Nodes
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
		settings.setBoxList(boxList);
		return settings;
	}
	
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
		
		SerXMLHandling.saveXMLFile(new File(ControlMain.filename));
	}
	
	public static void saveUserSettings() throws IOException {
		Element settingsDocument = ControlMain.getSettingsDocument().getRootElement();
		Node theme = settingsDocument.selectSingleNode("/settings/theme");
		Node locale = settingsDocument.selectSingleNode("/settings/locale");
		Node serverPort = settingsDocument.selectSingleNode("/settings/streamingServerPort");
		
		serverPort.setText(ControlMain.getSettings().getStreamingServerPort());
		theme.setText(ControlMain.getSettings().getThemeLayout());
		locale.setText(ControlMain.getSettings().getLocale());
		
		SerXMLHandling.saveXMLFile(new File(ControlMain.filename));
	}
	
	public static void saveAllSettings() throws IOException {
		Element settingsDocument = ControlMain.getSettingsDocument().getRootElement();
		
		//Aufbereitung der User-Settings
		Node theme = settingsDocument.selectSingleNode("/settings/theme");
		Node locale = settingsDocument.selectSingleNode("/settings/locale");
		Node serverPort = settingsDocument.selectSingleNode("/settings/streamingServerPort");
		
		serverPort.setText(ControlMain.getSettings().getStreamingServerPort());
		theme.setText(ControlMain.getSettings().getThemeLayout());
		locale.setText(ControlMain.getSettings().getLocale());
		
		
		//Aufbereitung der Box-Settings
		Node boxListRoot = settingsDocument.selectSingleNode("/settings/boxList");
		settingsDocument.remove(boxListRoot);
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
		
		SerXMLHandling.saveXMLFile(new File(ControlMain.filename));
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
