package service;
/*
 * SerMovieGuide2Xml.java by Ralph Henneberger
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 675 Mass
 * Ave, Cambridge, MA 02139, USA.
 *  
 */
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;

import javax.swing.JProgressBar;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import control.ControlMovieGuideTab;

public class SerMovieGuide2Xml extends Thread{
    static Hashtable htToken = new Hashtable();
    static Document doc;
    static Element root;
    static Element movie;
    String path; 
    JProgressBar bar;
    
    public SerMovieGuide2Xml(String file, JProgressBar comp) {
    		try {
				bar=comp;
				path = file;
				buildEmptyXMLFile();
				createHashTable();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    }
    
    private static void buildEmptyXMLFile() throws IOException {
        doc = DocumentHelper.createDocument();
        root = doc.addElement("movieguide");
    }
    
    //public static void saveXMLFile(File path) throws IOException {
    	public static void saveXMLFile(Document doc) throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();
    //    XMLWriter writer = new XMLWriter(new FileWriter("/tmp/output.xml"), format);
        //XMLWriter writer = new XMLWriter(new FileWriter(path), format);
        XMLWriter writer = new XMLWriter(new FileWriter(ControlMovieGuideTab.movieGuideFileName), format);
        //writer.write(ControlMovieGuideTab.getMovieGuideDocument());
        writer.write(doc);
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
    
    public void run()  {
        BufferedReader in;
        URLConnection con;
        try {
        	if (path != null) {
            	con = (new URL(path)).openConnection();
        	} else {
        		URL url = new URL("http://www.premiere.de/content/download/"+ SerFormatter.getAktuellDateString());
                con =url.openConnection();
        	}
        	
        	int fileLength = con.getContentLength();
            bar.setMaximum(fileLength);
            in = new BufferedReader( new InputStreamReader(con.getInputStream()));
            
            String input = new String();
            StringBuffer inhalt = new StringBuffer();
            boolean[] lineCounter = new boolean[2];
            int sumValue = 0;
            while ((input = in.readLine()) != null) {
            	sumValue = sumValue+input.getBytes().length;
            	bar.setValue(sumValue);				
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
            bar.setValue(fileLength);
            saveXMLFile(doc);
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
            //readGuide("/tmp/1.txt",1);
        	//System.out.println(getGenryList().get(1));
        } catch (Exception e) {
        }
    }
}