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
import java.io.*;
import java.net.*;

/**
 * @author ralix
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SerMovieGuide2Xml {
    static Hashtable htToken = new Hashtable();
    static Hashtable htGenreMap = new Hashtable();
    static Document doc;
    static Element root;
    static Element movie;
    
    private static void buildEmptyXMLFile() throws IOException {
        doc = DocumentHelper.createDocument();
        root = doc.addElement("movieguide");
    }
    
    public static void saveXMLFile(Document doc) throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(new FileWriter("/tmp/output.xml"), format);
        writer.write(doc);
        writer.close();
    }
    
    public static void setElementInElement(Element parentElement, String childElementName, String childElementValue) {
        Element element = DocumentHelper.createElement(childElementName);
        element.setText(childElementValue);
        parentElement.add(element);
    }
    
    private static void generateGenreMap(String entry) {
        htGenreMap.put(entry, entry);
    }
    
    private static String getAktuellDateString() {
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("ECT"));
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
    
    private static String[] createElement(int i, String input) {
        String value[] = new String[3];
        try {
            switch (i) {
                case 0:
                    value[0] = input.substring(0, input.indexOf(":"));
                    value[1] = SerFormatter.getCorrectDate(input.substring(input.indexOf(":") + 2));
                    value[2] = input.substring(input.indexOf("/")+1);
                    break;
                case 1:
                case 5:
                    value[0] = input.substring(input.indexOf(":") + 2);
                    break;
                case 2:
                    value[0] = input.substring(9, input.indexOf("Genre") - 2);
                    value[1] = input.substring(input.indexOf("Genre") + 7, input.indexOf("Länge") - 2);
                    generateGenreMap(value[1]);
                    value[2] = input.substring(input.indexOf("Länge") + 7, input.indexOf("Stunden") - 1);
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
    
    private static boolean[] getLineCounter(String input) {
        boolean[] value = new boolean[2];
        try {
            value[0] = false;
            value[0] = SerFormatter.isCorrectDate(input.substring(input.indexOf(":") + 2));
        } catch (Exception ex) {}
        try {
            value[1] = false;
            value[1] = htToken.containsKey(input.substring(0, input.indexOf(":")));
        } catch (Exception ex) {}
        return value;
    }
    
    private static int getNumber(String input) {
        int value = 0;
        try{
            value = ((Integer) htToken.get((String) input.substring(0, input.indexOf(":")))).intValue();
        } catch (Exception ex) {}
        return value;
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
            buildEmptyXMLFile();
            String input = new String();
            StringBuffer inhalt = new StringBuffer();
            createHashTable();
            boolean[] lineCounter = new boolean[3];
            int number = 0;
            String[] out;
            while ((input = in.readLine()) != null) {
                lineCounter = getLineCounter(input);
                if (lineCounter[0]) {                    
                    out = createElement(0, input);
                    movie = root.addElement("entry");
                    setElementInElement(movie,"sender",out[0]);
                    setElementInElement(movie,"datum", out[1]);
                    setElementInElement(movie,"start", out[2]);
                } else if (lineCounter[1]) {
                    number = getNumber(input);
                    switch (number) {
                        case 1:
                            setElementInElement(movie,"titel", createElement(number, input)[0]);
                            break;
                        case 2:
                            out = createElement(number, input);
                            setElementInElement(movie,"episode",out[0]);
                            setElementInElement(movie,"genre", out[1]);
                            setElementInElement(movie,"dauer", out[2]);
                            break;
                        case 3:
                            out = createElement(number, input);
                            setElementInElement(movie,"land",out[0]);
                            setElementInElement(movie,"jahr", out[1]);
                            setElementInElement(movie,"regie", out[2]);
                            break;
                        case 4:
                            out = createElement(number, input);
                            setElementInElement(movie,"bild",out[0]);
                            setElementInElement(movie,"ton", out[1]);
                        case 5:
                            setElementInElement(movie,"darsteller", createElement(number, input)[0]);
                            break;
                    }
                } else if ((lineCounter[0] && lineCounter[1]) == false){
                    if(input.length() > 0){
                        inhalt.append(input);
                    }else{
                        setElementInElement(movie,"inhalt", inhalt.toString());
                        inhalt.setLength(0);
                    }
                }
            }
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException: " + e);
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        saveXMLFile(doc);
    }
    
    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        try {
            readGuide("/tmp/1.txt",1);
        } catch (Exception e) {
        }
    }
}