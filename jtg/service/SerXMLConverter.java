/*
 * Created on 20.04.2004
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package service;


import java.util.Iterator;
import java.util.List;

import model.BOSettings;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
/**
 * @author QSE2419
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SerXMLConverter {

	public static BOSettings buildSettings(Document document)
		{	//Auslesen der Werte aus der XMLDatei und Aufbereitung
		BOSettings settings = new BOSettings();	
			Element root = document.getRootElement();
			for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
				Element element = (Element) i.next();
				List nodes = SerXPathHandling.getDescendentNodes(element);
				Node boxIp = (Node)nodes.get(0);
				Node vlcpath = (Node)nodes.get(1); 
				settings.setDboxIp(boxIp.getText());
				settings.setVlcPath(vlcpath.getText());
			};
			return settings;
		}
		
	public static void buildXMLDocument(Document document, BOSettings settings)
		{	//Erstellen eines XML-Dokuments aus einer ArrayList von Settings
			Element root = document.getRootElement();
			root.clearContent();

			Element boxIpElement = DocumentHelper.createElement("Settings");
			boxIpElement.addElement("boxIp").addText(settings.getDboxIp());
			boxIpElement.addElement("vlcPath").addText(settings.getVlcPath());
			root.add(boxIpElement);
		}
}
