package service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.GregorianCalendar;

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
}
