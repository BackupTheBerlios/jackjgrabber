

package service;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.dom4j.Document;
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
	public static Document buildEmptyXMLFile(File path) throws IOException {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement( "settings" );
		
		Element locale = DocumentHelper.createElement("locale");
		locale.setText("DE");
		
		Element theme = DocumentHelper.createElement("theme");
		theme.setText("Silver");
		
		root.add(locale);
		root.add(theme);
		
		root.addElement("boxList");
		saveXMLFile(path);
		return doc;
	}
		
	public static void saveXMLFile(File path) throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint(); 
		XMLWriter writer = new XMLWriter( new FileWriter( path ), format );
		writer.write( ControlMain.getSettingsDocument() );
		writer.close();
	}
	
	public static Document readDocument(File path) throws Exception {
		SAXReader reader = new SAXReader();  
		Document doc = reader.read(path);
		return doc;
	}
}
