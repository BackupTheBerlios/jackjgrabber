/*
 * SerFormatter.java
 *
 * Created on 13. September 2004, 13:15
 */

package service;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.GregorianCalendar;

/**
 * @author  ralix
 */
public class SerFormatter {
        
	private static String shortTime(long i){    
		Calendar cal = new GregorianCalendar( TimeZone.getTimeZone("ECT") );
		cal.setTimeInMillis(i); 
		return out(cal.get(Calendar.HOUR_OF_DAY))+":"+out(cal.get(Calendar.MINUTE));		
	}  
        
	private static String out( int i ) {
	    return ( i >= 10 ) ? Integer.toString(i) : "0"+i;
	} 
		
	private static long formatLong(String date){
		return Long.parseLong(date) * 1000;                          
	}
	
	public static String getUnixEndTime(String start, String dauer){ 	
		int startInt = Integer.parseInt(start);
		int dauerInt = Integer.parseInt(dauer);
		return Integer.toString(startInt+dauerInt); 
    }
                        
	public static String formatUnixTime(String date, String dauer){
		return shortTime( (formatLong(date)+formatLong(dauer)) );
	}
	public static String formatUnixTime(String date){
		return shortTime(formatLong(date));
	}
        
	public static GregorianCalendar formatUnixDate(String date, String dauer){	
		GregorianCalendar cal = new GregorianCalendar( TimeZone.getTimeZone("ECT") );
		cal.setTimeInMillis( (formatLong(date)+formatLong(dauer)) );                                 
		return cal;
	}
        
	public static GregorianCalendar formatUnixDate(String date){
		GregorianCalendar cal = new GregorianCalendar( TimeZone.getTimeZone("ECT") );
		cal.setTimeInMillis(formatLong(date));                                 
		return cal;
	}
	
	public static GregorianCalendar formatUnixDate(long date){
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
}
