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

/**
 * @author ralph
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SerMovieGuide2Xml {
	 public static Document buildEmptyXMLFile() throws IOException {
        
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement( "movieguide" );
        
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
        XMLWriter writer = new XMLWriter( new FileWriter("/tmp/output.xml"), format );
        //writer.write( ControlMain.getSettingsDocument() );
        writer.write(doc);
        writer.close();
    }
    
    public static void readGuide() throws FileNotFoundException, IOException{
        String datei = "/tmp/1.txt";
        //String datei = "/tmp/mguide_d_s_10_04.txt";
        String input = "";
        SimpleDateFormat formatter  = new SimpleDateFormat("dd.MM./HH:mm");
        BufferedReader test = new BufferedReader(new FileReader(datei));
        String sender ="";
        String datum  ="";
        String titel  ="";
        String episode = "";
        String genre = "";
        String laenge = "";
        String land = "";
        String jahr = "";
        String regie = "";
        String bild = "";
        String ton = "";
        String darsteller = "";
        String inhalt = "";
        boolean value = false;
        String lineCount = "";        
        int i = 1;
        while((input = test.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(input,":");
            if (st.hasMoreTokens()){
                lineCount=st.nextToken();
                if (!value){                    
                    
                    try{
                        sender = lineCount;
                    }catch(StringIndexOutOfBoundsException ex){}
                    datum = input.substring((sender.length()+2));
                    try{
                        datum = SerFormatter.setCorrectYear(formatter.parse(datum)).toString();
                        System.out.println("1 "+sender);                        
                        System.out.println("1 "+datum);                                  
                    }catch(ParseException pex){
                        value = true;
                    }                                                        
                }
                if(lineCount.equalsIgnoreCase("Titel")){
                    try{
                        titel = input.substring((lineCount.length()+2));                        
                    }catch(StringIndexOutOfBoundsException ex){}
                    System.out.println("2 "+titel);                    
                }else
                    if(lineCount.equalsIgnoreCase("Episode")){
                        try{
                            episode = input.substring(9,input.indexOf("Genre")-2);
                            genre= input.substring(input.indexOf("Genre")+7,input.indexOf("Länge")-2);
                            laenge = input.substring(input.indexOf("Länge")+7,input.indexOf("Stunden")-1);                            
                        }catch(StringIndexOutOfBoundsException ex){}
                        System.out.println("3 "+episode);
                        System.out.println("3 "+genre);
                        System.out.println("3 "+laenge);
                    }else
                        if(lineCount.equalsIgnoreCase("Produktionsland")){
                            try{
                                land = input.substring(input.indexOf(":")+2,input.indexOf("Produktionsjahr")-2);
                                jahr = input.substring(input.indexOf("Produktionsjahr")+17,input.indexOf("Regie")-2);
                                regie = input.substring(input.indexOf("Regie")+7);                                
                            }catch(StringIndexOutOfBoundsException ex){}
                            System.out.println("4 "+land);
                            System.out.println("4 "+jahr);
                            System.out.println("4 "+regie);
                        }else
                            if(lineCount.equalsIgnoreCase("Bild- und Tonformate")){
                                try{
                                    bild = input.substring(input.indexOf(":")+2,input.indexOf("/"));
                                    ton = input.substring(input.indexOf("/")+1);                                    
                                }catch(StringIndexOutOfBoundsException ex){}
                                System.out.println("5 "+bild);
                                System.out.println("5 "+ton);
                            }else
                                if(lineCount.equalsIgnoreCase("Darsteller")){
                                    try{
                                        darsteller = input.substring((lineCount.length()+2));                                        
                                    }catch(StringIndexOutOfBoundsException ex){}
                                    System.out.println("6 "+darsteller);
                                }
                                else{
                                    if(value){
                                        inhalt = inhalt + input;
                                    }
                                }
            }
            else{
                System.out.println("7 "+inhalt);                
                //System.out.println("no token"+input);
                inhalt = "";
                value = false;                 
            }
        }        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //buildEmptyXMLFile();            
            readGuide();
            
        }catch (Exception e){}
    }
    
}
