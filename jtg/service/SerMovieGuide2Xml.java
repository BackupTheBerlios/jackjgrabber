/*
 * Created on 21.10.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package service;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.util.*;
import java.text.*;
import java.io.*;
import java.net.*;

/**
 * @author ralph
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SerMovieGuide2Xml {
	static Hashtable htToken = new Hashtable();

	static Hashtable htGenreMap = new Hashtable();

	public static Document buildEmptyXMLFile() throws IOException {

		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("movieguide");

		//Element locale = DocumentHelper.createElement("locale");
		//locale.setText("DE");

		Element sender = DocumentHelper.createElement("sender");
		Element datum = DocumentHelper.createElement("datum");
		Element start = DocumentHelper.createElement("start");
		Element titel = DocumentHelper.createElement("titel");
		Element episode = DocumentHelper.createElement("episode");
		Element genre = DocumentHelper.createElement("genre");
		Element dauer = DocumentHelper.createElement("dauer");
		Element land = DocumentHelper.createElement("land");
		Element jahr = DocumentHelper.createElement("jahr");
		Element regie = DocumentHelper.createElement("regie");
		Element bild = DocumentHelper.createElement("bild");
		Element ton = DocumentHelper.createElement("ton");
		Element darsteller = DocumentHelper.createElement("darsteller");
		Element inhalt = DocumentHelper.createElement("inhalt");

		//root.add(locale);
		//root.add(theme);

		//root.addElement("boxList");
		//ControlMain.setSettingsDocument(doc);
		//saveXMLFile();
		return doc;
	}

	public static void saveXMLFile(Document doc) throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(new FileWriter("/tmp/output.xml"),
				format);
		//writer.write( ControlMain.getSettingsDocument() );
		writer.write(doc);
		writer.close();
	}

	private static void generateGenreMap(String entry) {
		htGenreMap.put(entry, entry);
	}

	private static String getAktuellDateString() {
		Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("ECT"));
		String monat = String.valueOf((cal.get(Calendar.MONTH) + 1));
		String jahr = String.valueOf((cal.get(Calendar.YEAR))).substring(2);
		return "mguide_d_s_" + monat + "_" + jahr + ".txt";
	}
	
	private static void createHashTable() {
		htToken.put((String) "Titel", new Integer(1));
		htToken.put((String) "Episode", new Integer(2));
		htToken.put((String) "Produktionsland", new Integer(3));
		htToken.put((String) "Bild- und Tonformate", new Integer(4));
		htToken.put((String) "Darsteller", new Integer(5));
	}

	private static String[] createElement(Integer i, String input) {
		String value[] = new String[3];
		try {
			switch (i.intValue()) {
			case 0:
				value[0] = input.substring(0, input.indexOf(":"));
				value[1] = SerFormatter.getCorrectDate(input.substring(input.indexOf(":") + 2));
				break;
			case 1:
			case 5:
				value[0] = input.substring(input.indexOf(":") + 2);
				break;
			case 2:
				value[0] = input.substring(9, input.indexOf("Genre") - 2);
				value[1] = input.substring(input.indexOf("Genre") + 7, input.indexOf("Länge") - 2);
				generateGenreMap(value[1]);
				value[2] = input.substring(input.indexOf("Länge") + 7, input
						.indexOf("Stunden") - 1);
				break;
			case 3:
				value[0] = input.substring(input.indexOf(":") + 2, input.indexOf("Produktionsjahr") - 2);
				value[1] = input.substring(
						input.indexOf("Produktionsjahr") + 17, input.indexOf("Regie") - 2);
				value[2] = input.substring(input.indexOf("Regie") + 7);
				break;
			case 4:
				value[0] = input.substring(input.indexOf(":") + 2, input.indexOf("/"));
				value[1] = input.substring(input.indexOf("/") + 1);
				break;
			}
		} catch (StringIndexOutOfBoundsException ex) {
		}
		return value;
	}

	public static boolean[] getLineCounter(String input) {
		boolean[] value = new boolean[2];
		try {
			value[0] = false;
			value[0] = SerFormatter.isCorrectDate(input.substring(input.indexOf(":") + 2));
		} catch (Exception ex) {
			value[0] = false;
		}
		try {
			value[1] = false;
			value[1] = htToken.containsKey(input.substring(0, input.indexOf(":")));
		} catch (Exception ex) {
			value[1] = false;
		}
		return value;
	}

	public static int getNumber(String input) {
		return ((Integer) htToken.get((String) input.substring(0, input.indexOf(":")))).intValue();
	}

	public static void readGuide(String datei,int quelle) throws FileNotFoundException, IOException {		
		BufferedReader in = (null);
		try {
		switch (quelle){
		case 1:
		    in = new BufferedReader(new FileReader(datei));
		    break;
		case 2:    	
			URL url = new URL("http://www.premiere.de/content/download/"+ getAktuellDateString());
			Reader is = new InputStreamReader(url.openStream());
			in = new BufferedReader(is);
			break;
		}
		String input = new String();
		String inhalt = "";
		createHashTable();
		boolean[] lineCounter = new boolean[2];
		int number = 0;
		String[] out;
		while ((input = in.readLine()) != null) {
			lineCounter = getLineCounter(input);
			if (lineCounter[0]) {
				out = createElement(new Integer(0), input);
				System.out.println("1 " + out[0]);
				System.out.println("1 " + out[1]);
			} else if (lineCounter[1]) {
				number = getNumber(input);
				out = createElement(new Integer(number), input);
				switch (number) {
				case 1:
					inhalt = "";
					System.out.println("2 " + out[0]);
					break;
				case 2:
					System.out.println("3 " + out[0]);
					System.out.println("3 " + out[1]);
					System.out.println("3 " + out[2]);
					break;
				case 3:
					System.out.println("4 " + out[0]);
					System.out.println("4 " + out[1]);
					System.out.println("4 " + out[2]);
					break;
				case 4:
					System.out.println("5 " + out[0]);
					System.out.println("5 " + out[1]);
					break;
				case 5:
					System.out.println("6 " + out[0]);
					break;
				}
			} else		
			if ((lineCounter[0] && lineCounter[1]) == false	&& (input.length() > 0)) {
				inhalt = inhalt + input;
				System.out.println("7 " + inhalt);
			}
		}
		} catch (MalformedURLException e) {
			System.out.println("MalformedURLException: " + e);
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		try {
			//buildEmptyXMLFile();		
			readGuide("/tmp/1.txt",2);

		} catch (Exception e) {
		}
	}

}