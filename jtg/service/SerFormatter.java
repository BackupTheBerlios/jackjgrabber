package service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * @author  ralix
 */
public class SerFormatter {
        
	public static String getShortTime(long i){    
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(new Date(i));
	}
        
	private static String out( int i ) {
	    return ( i >= 10 ) ? Integer.toString(i) : "0"+i;
	} 
        
	public static GregorianCalendar formatUnixDate(String date){
		GregorianCalendar cal = new GregorianCalendar( TimeZone.getTimeZone("ECT") );
		cal.setTimeInMillis(Long.parseLong(date) * 1000);                                 
		return cal;
	}
	
	public static GregorianCalendar formatUnixDate(long date){
		GregorianCalendar cal = new GregorianCalendar( TimeZone.getTimeZone("ECT") );
		cal.setTimeInMillis(date*1000);                                 
		return cal;
	}
	
	public static GregorianCalendar formatDate(long date){
		GregorianCalendar cal = new GregorianCalendar( TimeZone.getTimeZone("ECT") );
		cal.setTimeInMillis(date);                                 
		return cal;
	}
        
	public static String formatedEndTime(String zeit){       
		int dauer=Integer.parseInt(zeit);
		int sekunden = dauer%60; 
		dauer/=60; 
		int minuten = dauer%60; 
		dauer/=60; 
		int stunden = dauer;
		return out(stunden)+":"+out(minuten);      
	}
	
	public static String replace(String sourcestring, String stringToReplace, String newString) {
	    int i = -1;
	    while ((i = sourcestring.indexOf(stringToReplace, i + 1)) != -1)
	      sourcestring = sourcestring.substring(0, i) + newString + sourcestring.substring(i + stringToReplace.length());
	    return sourcestring;
	  }
	
	public static Date setCorrectYear(Date datum){
		GregorianCalendar cal = new GregorianCalendar( TimeZone.getTimeZone("ECT") );                        
        int i = cal.get(GregorianCalendar.YEAR);            
        cal.setTimeInMillis(datum.getTime());
        cal.set(GregorianCalendar.YEAR, i);
        return cal.getTime();
    }
    
    public static String getCorrectDate(String datum){            
        SimpleDateFormat formatter  = new SimpleDateFormat("dd.MM./HH:mm");
        try{
           datum = SerFormatter.setCorrectYear(formatter.parse(datum)).toString();               
        }catch(ParseException pex){               
        }            
        return datum;
    }
    
    public static boolean isCorrectDate(String datum){
        boolean value = true;
        SimpleDateFormat formatter  = new SimpleDateFormat("dd.MM./HH:mm");
        try{
           datum = SerFormatter.setCorrectYear(formatter.parse(datum)).toString();               
        }catch(ParseException pex){
            value = false;
        }            
        return value;
    }
}
