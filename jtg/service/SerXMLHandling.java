

package service;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.BOBox;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import control.ControlMain;

public class SerXMLHandling {
	
	public static Document buildEmptyXMLFile(File path) throws IOException {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement( "settings" );
		root.addElement("boxList");
		saveXMLFile(path, doc);
		return doc;
	}
		
	public static void saveXMLFile(File path, Document doc) throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint(); 
		XMLWriter writer = new XMLWriter( new FileWriter( path ), format );
		writer.write( doc );
		writer.close();
	}
	
	public static Document readDocument(File path) throws Exception {
		SAXReader reader = new SAXReader();  
		Document doc = reader.read(path);
		return doc;
	}
}
