package service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.GregorianCalendar;
import java.text.ParseException;
import java.text.DateFormat;

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
    
	public static String getCorrectEndTime(String start, String ende){
		SimpleDateFormat formatter  = new SimpleDateFormat("hh:mm");         
        GregorianCalendar cal = new GregorianCalendar( TimeZone.getTimeZone("ECT") );        
        try{              
            cal.setTimeInMillis(formatter.parse(start).getTime());                                    
            cal.set(GregorianCalendar.HOUR_OF_DAY, cal.get(GregorianCalendar.HOUR_OF_DAY)+Integer.parseInt(ende.substring(0,2)));
            cal.set(GregorianCalendar.MINUTE, cal.get(GregorianCalendar.MINUTE)+Integer.parseInt(ende.substring(3,5)));
        }catch(Exception ex){}
            return out(cal.get(GregorianCalendar.HOUR_OF_DAY))+":"+out(cal.get(GregorianCalendar.MINUTE));        
    }
	
	public static String getCorrectDate(String datum){
        SimpleDateFormat formatter  = new SimpleDateFormat("dd.MM./HH:mm");         
        GregorianCalendar cal = new GregorianCalendar( TimeZone.getTimeZone("ECT") );        
        try{              
            cal.setTimeInMillis(SerFormatter.setCorrectYear(formatter.parse(datum)).getTime());
            DateFormat formater2 = DateFormat.getDateInstance(DateFormat.FULL);
            datum = formater2.format(cal.getTime()).toString();            
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
    
    public static String stripOff(String input) {
        String delim = new String("<>*?|/\":");
        StringTokenizer t = new StringTokenizer(input,delim);
        StringBuffer    b = new StringBuffer(input.length());
        while(t.hasMoreTokens()) b.append(t.nextToken());
        return b.toString();
    }
            
    public static String getAktuellDateString() {
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("ECT"));
        return "mguide_d_s_" + String.valueOf((cal.get(GregorianCalendar.MONTH) + 1)) + "_" + String.valueOf((cal.get(GregorianCalendar.YEAR))).substring(2) + ".txt";        
    }
    
    public static String getDatumToday(){
    	GregorianCalendar cal = new GregorianCalendar( TimeZone.getTimeZone("ECT") );           
    	DateFormat formater2 = DateFormat.getDateInstance(DateFormat.FULL);
    	return formater2.format(cal.getTime()).toString();
    }
    public static String getShortDate(long i){    
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd.MMM.yy");
		return sdf.format(new Date(i));               
    }
    public static long getStringToLong(String start){    
        long value = 0L;	
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd. MMMM yyyy");		                
        GregorianCalendar cal = new GregorianCalendar( TimeZone.getTimeZone("ECT") );        
        try{              
            cal.setTimeInMillis(formatter.parse(start).getTime());                                                
            value = cal.getTimeInMillis();
        }catch(Exception ex){}
        return value;
    }
    public static long getStringToLongWithTime(String start,long time){    
        long value = 0L;	
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd. MMMM yyyy,HH:mm");		                
        GregorianCalendar cal = new GregorianCalendar( TimeZone.getTimeZone("ECT") );        
        try{              
            cal.setTimeInMillis(formatter.parse(start).getTime()+time);                                                
            value = cal.getTimeInMillis();
        }catch(Exception ex){}
        return value;
    }
}
