package service;
/*
SerXMLHandling.java by Geist Alexander 

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
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import control.ControlMain;


public class SerXMLHandling {
	
	/**
	 * Erstellen eines neuen XML-Settingsdokumentes mit Defaultwerten
	 */
	public static Document createStandardSettingsFile(File path) throws IOException {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement( "settings" );	
		
		setElementInElement(root,"playbackPlayer", "d: http://$ip:31339/$vPid,$aPid");
		setElementInElement(root,"savePath", new File(System.getProperty("user.home")).getAbsolutePath());
		setElementInElement(root,"startStreamingServer", "true");
		setElementInElement(root,"streamingServerPort", "4000");
		setElementInElement(root,"theme", "Silver");
		setElementInElement(root,"locale", "DE");
		setElementInElement(root,"jgrabberStreamType", "PES MPEG-Packetized Elementary");
		setElementInElement(root,"udrecStreamType", "PES MPEG-Packetized Elementary");
		setElementInElement(root,"startPX", "true");
		setElementInElement(root,"udrecPath", new File("udrec.exe").getAbsolutePath());
		setElementInElement(root,"engine", "0");
		setElementInElement(root,"recordAllPids", "true");
		setElementInElement(root,"useStandardPlayback", "false");
		setElementInElement(root,"showLogo", "true");
		setElementInElement(root,"useSysTray", "false");
		setElementInElement(root,"startFullscreen", "false");
		setElementInElement(root,"recordTimeBefore", "0");
		setElementInElement(root,"recordTimeAfter", "0");

		root.addElement("boxList");
		root.addElement("playbackList");
		ControlMain.setSettingsDocument(doc);
		saveXMLFile(path, doc);
		return doc;
	}
	
	 public static Document createEmptyMovieguideFile() throws IOException {
        Document doc = DocumentHelper.createDocument();
        doc.addElement("movieguide");
        return doc;
    }
	
	/**
	 * @param parentElement
	 * @param childElementName
	 * @param childElementValue
	 */
	public static void setElementInElement(Element parentElement, String childElementName, String childElementValue) {
		Element element = DocumentHelper.createElement(childElementName);
		element.setText(childElementValue);
		parentElement.add(element);
	}
		
	public static void saveXMLFile(File path, Document doc ) throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint(); 
		XMLWriter writer = new XMLWriter( new FileWriter( path ), format );
		writer.write( doc );
		writer.close();
	}
	
	public static Document readDocument(File path)  throws DocumentException, MalformedURLException {
		SAXReader reader = new SAXReader();  
		Document doc = reader.read(path);
		return doc;
	}
}
