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
import java.util.Date;

/**
 * @author  ralix
 */
public class SerFormatter {

	private static Date shortDateDefault(long i){		
		Calendar cal = new GregorianCalendar( TimeZone.getTimeZone("ECT") );
		cal.setTimeInMillis(i);                  
                cal.set(Calendar.HOUR_OF_DAY,0);
                cal.set(Calendar.MINUTE,0);
                cal.set(Calendar.SECOND,0);                
                cal.set(Calendar.MILLISECOND,0);                
                return cal.getTime();
	}
        
	public static String shortDate(long i){
		DateFormat formater;
		Calendar cal = new GregorianCalendar( TimeZone.getTimeZone("ECT") );
		cal.setTimeInMillis(i);                                  
		formater = DateFormat.getDateInstance(DateFormat.MEDIUM );
		return formater.format(cal.getTime());      		                                
	}        
        
	private static String shortTime(long i){    
		Calendar cal = new GregorianCalendar( TimeZone.getTimeZone("ECT") );
		cal.setTimeInMillis(i); 
		return out(cal.get(Calendar.HOUR_OF_DAY))+":"+out(cal.get(Calendar.MINUTE));		
	}  
        
	private static String out( int i ) {
	    return ( i >= 10 ) ? Integer.toString(i) : "0"+i;
	} 
		
        private static long formatLong(String date){
            long a = 0L;
	    try {
	    a = Long.parseLong(date);       
	    }catch (Exception ex){
	        System.out.println(ex);
	    }  
	    return a * 1000;                 
        }
                        
        public static String formatUnixTime(String date, String dauer){
            return shortTime( (formatLong(date)+formatLong(dauer)) );
	}
	public static String formatUnixTime(String date){
            return shortTime(formatLong(date));
	}
        
	public static Date formatUnixDate(String date, String dauer){	    
	    return shortDateDefault( (formatLong(date)+formatLong(dauer)) );
	}
        
	public static Date formatUnixDate(String date){	    
	    return shortDateDefault(formatLong(date));
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
