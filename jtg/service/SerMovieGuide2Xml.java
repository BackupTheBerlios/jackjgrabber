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
import org.dom4j.*;

import java.util.*;
import java.io.*;
import java.net.*;

import control.ControlMovieGuideTab;

/**
 * @author ralix
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SerMovieGuide2Xml {
    static Hashtable htToken = new Hashtable();
    static Document doc;
    static Element root;
    static Element movie;
    
    private static void buildEmptyXMLFile() throws IOException {
        doc = DocumentHelper.createDocument();
        root = doc.addElement("movieguide");
    }
    
    
    public static void saveXMLFile(File path) throws IOException {
    //	public static void saveXMLFile(Document doc) throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();
    //    XMLWriter writer = new XMLWriter(new FileWriter("/tmp/output.xml"), format);
        XMLWriter writer = new XMLWriter(new FileWriter(path), format);
        writer.write(ControlMovieGuideTab.getMovieGuideDocument());
        //writer.write(doc);
        writer.close();    
    }
          
    private static final void createHashTable() {
        htToken.put((String) "Titel", new Integer(1));
        htToken.put((String) "Episode", new Integer(2));
        htToken.put((String) "Produktionsland", new Integer(3));
        htToken.put((String) "Bild- und Tonformate", new Integer(4));
        htToken.put((String) "Darsteller", new Integer(5));
    }
    
    private static void createElement(int i, String input) {
        try {
            switch (i) {
                case 0:
                	SerXMLHandling.setElementInElement(movie,"sender",input.substring(0, input.indexOf(":")));
                	SerXMLHandling.setElementInElement(movie,"datum",SerFormatter.getCorrectDate(input.substring(input.indexOf(":") + 2)));
                	SerXMLHandling.setElementInElement(movie,"start", input.substring(input.indexOf("/")+1));
                    break;
                case 1:
                	SerXMLHandling.setElementInElement(movie,"titel", input.substring(input.indexOf(":") + 2));
                    break;
                case 2:
                	SerXMLHandling.setElementInElement(movie,"episode",input.substring(9, input.indexOf("Genre") - 2));
                	SerXMLHandling.setElementInElement(movie,"genre", input.substring(input.indexOf("Genre") + 7, input.indexOf("Länge") - 2));
                	SerXMLHandling.setElementInElement(movie,"dauer", input.substring(input.indexOf("Länge") + 7, input.indexOf("Stunden") - 1));
                    break;
                case 3:
                	SerXMLHandling.setElementInElement(movie,"land",input.substring(input.indexOf(":") + 2, input.indexOf("Produktionsjahr") - 2));
                	SerXMLHandling.setElementInElement(movie,"jahr", input.substring(input.indexOf("Produktionsjahr") + 17, input.indexOf("Regie") - 2));
                	SerXMLHandling.setElementInElement(movie,"regie", input.substring(input.indexOf("Regie") + 7));
                    break;
                case 4:
                	SerXMLHandling.setElementInElement(movie,"bild",input.substring(input.indexOf(":") + 2, input.indexOf("/")));
                	SerXMLHandling.setElementInElement(movie,"ton", input.substring(input.indexOf("/") + 1));
                    break;
                case 5:
                	SerXMLHandling.setElementInElement(movie,"darsteller", input.substring(input.indexOf(":") + 2));
                    break;
            }
        } catch (StringIndexOutOfBoundsException ex) {}
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
                    URL url = new URL("http://www.premiere.de/content/download/"+ SerFormatter.getAktuellDateString());
                    Reader is = new InputStreamReader(url.openStream());
                    in = new BufferedReader(is);
                    break;
            }
            buildEmptyXMLFile();
            String input = new String();
            StringBuffer inhalt = new StringBuffer();
            createHashTable();
            boolean[] lineCounter = new boolean[2];            
            while ((input = in.readLine()) != null) {
                lineCounter = getLineCounter(input);
                if (lineCounter[0]) {
                    movie = root.addElement("entry");
                    createElement(0, input);
                } else if (lineCounter[1]) {
                    createElement(getNumber(input),input);
                } else if ((lineCounter[0] && lineCounter[1]) == false){
                    if(input.length() > 0){
                        inhalt.append(input);
                    }else{
                    	SerXMLHandling.setElementInElement(movie,"inhalt", inhalt.toString());
                        inhalt.setLength(0);
                    }
                }
            }
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException: " + e);
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        saveXMLFile(new File(ControlMovieGuideTab.movieGuideFileName));
        //saveXMLFile(doc);
    }
    
    public ArrayList getGenryList(String value) throws Exception{    	            	
    	Document doc = SerXMLHandling.readDocument(new File("/tmp/output.xml"));    	
    	Element root = doc.getRootElement();                
        ArrayList al = new ArrayList();
        for ( Iterator i = root.elementIterator("entry"); i.hasNext(); ) {
            Element entry = (Element) i.next();                     
            al.add(entry.element(value).getStringValue());
        }
        return al;
    }
    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        try {
            //readGuide("/tmp/1.txt",1);
        	//System.out.println(getGenryList().get(1));
        } catch (Exception e) {
        }
    }
}