/*
 * Created on 20.04.2004
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.BOBox;
import model.BOSettings;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import control.ControlMain;
/**
 * @author QSE2419
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
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
		Node themeNode = settingsDocument.selectSingleNode("/settings/theme");
		Node themeLocale = settingsDocument.selectSingleNode("/settings/locale");
		
		themeNode.setText(ControlMain.getSettings().getThemeLayout());
		themeLocale.setText(ControlMain.getSettings().getLocale());
		
		SerXMLHandling.saveXMLFile(new File(ControlMain.filename));
	}
	
	public static void saveAllSettings() throws IOException {
		Element settingsDocument = ControlMain.getSettingsDocument().getRootElement();
		
		//Aufbereitung der User-Settings
		Node themeNode = settingsDocument.selectSingleNode("/settings/theme");
		Node themeLocale = settingsDocument.selectSingleNode("/settings/locale");
		
		themeNode.setText(ControlMain.getSettings().getThemeLayout());
		themeLocale.setText(ControlMain.getSettings().getLocale());
		
		
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
}
