package service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.GregorianCalendar;
import java.text.ParseException;
import java.text.DateFormat;

/**
 * @author ralix
 */
public class SerFormatter {

	/** entferne alle unerlaubten Zeichen
	 * 
	 * @param input
	 * @return
	 */
	private static final String DATE_FULL = "EEEE, dd. MMMM yyyy";
	
	public static String removeInvalidCharacters(String input) {

		StringBuffer umlBuf = new StringBuffer();
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			switch (c) {
				case '�' : {
					umlBuf.append("ae");
					break;
				}
				case '�' : {
					umlBuf.append("oe");
					break;
				}
				case '�' : {
					umlBuf.append("ue");
					break;
				}
				case '�' : {
					umlBuf.append("ss");
					break;
				}
				case '�' : {
					umlBuf.append("Ue");
					break;
				}
				case '�' : {
					umlBuf.append("Oe");
					break;
				}
				case '�' : {
					umlBuf.append("Ae");
					break;
				}
				case '?':
				case '\\' :
				case ':' :
				case '/' : {
					break;
				}
				default : {
					umlBuf.append(c);
				}
			}
		}
		String s = umlBuf.toString();
		s = s.replaceAll("'", "");
		return s;
	}
	/**
	 * @param timeInMillis
	 * @return short-Time String "HH:mm"
	 */
	public static String getShortTime(long timeInMillis) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(new Date(timeInMillis));
	}

	private static String out(int i) {
		return (i >= 10) ? Integer.toString(i) : "0" + i;
	}

	/**
	 * @param unix-date
	 * @return GregorianCalendar
	 */
	public static GregorianCalendar formatUnixDate(String date) {
		GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("ECT"));
		cal.setTimeInMillis(Long.parseLong(date) * 1000);
		return cal;
	}

	/**
	 * @param unix-date
	 * @return GregorianCalendar
	 */
	public static GregorianCalendar formatUnixDate(long date) {
		GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("ECT"));
		cal.setTimeInMillis(date * 1000);
		return cal;
	}

	/**
	 * @param timeInMillis
	 * @return GregorianCalendar
	 */
	public static GregorianCalendar formatTimeInMillisToCal(long timeInMillis) {
		GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("ECT"));
		cal.setTimeInMillis(timeInMillis);
		return cal;
	}

	/**
	 * @param unixTime
	 * @return duration-string hours:minutes
	 */
	public static String formatUnixTimeToDuration(String unixTime) {
		int dauer = Integer.parseInt(unixTime);
		int sekunden = dauer % 60;
		dauer /= 60;
		int minuten = dauer % 60;
		dauer /= 60;
		int stunden = dauer;
		return out(stunden) + ":" + out(minuten);
	}

	/**
	 * @param sourcestring
	 * @param stringToReplace
	 * @param newString
	 * @return formattedString
	 * replaces in sourceString the stringToReplace with newString
	 */
	public static String replace(String sourcestring, String stringToReplace,String newString) {
		int i = -1;
		while ((i = sourcestring.indexOf(stringToReplace, i + 1)) != -1)
			sourcestring = sourcestring.substring(0, i) + newString + sourcestring.substring(i + stringToReplace.length());
		return sourcestring;
	}

	public static Date setCorrectYear(Date datum) {
		GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("ECT"));
		int i = cal.get(GregorianCalendar.YEAR);
		cal.setTimeInMillis(datum.getTime());
		cal.set(GregorianCalendar.YEAR, i);
		return cal.getTime();
	}

	public static String getCorrectEndTime(String start, String ende) {
	    GregorianCalendar cal = SerFormatter.convString2GreCal(start, "hh:mm");
		cal.set(GregorianCalendar.HOUR_OF_DAY, cal.get(GregorianCalendar.HOUR_OF_DAY)+ Integer.parseInt(ende.substring(0, 2)));
		cal.set(GregorianCalendar.MINUTE, cal.get(GregorianCalendar.MINUTE)+ Integer.parseInt(ende.substring(3, 5)));
		return out(cal.get(GregorianCalendar.HOUR_OF_DAY)) + ":"
				+ out(cal.get(GregorianCalendar.MINUTE));
	}

	public static String getCorrectDate(String datum) {
	    GregorianCalendar cal = SerFormatter.convString2GreCal(datum, "dd.MM./HH:mm");
		DateFormat formater2 = DateFormat.getDateInstance(DateFormat.FULL);
		datum = formater2.format(cal.getTime()).toString();
		return datum;
	}

	public static boolean isCorrectDate(String datum) {
		boolean value = true;
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM./HH:mm");
		try {
			datum = SerFormatter.setCorrectYear(formatter.parse(datum)).toString();
		} catch (ParseException pex) {
			value = false;
		}
		return value;
	}

	public static String stripOff(String input) {
		String delim = new String("<>'*?|/\":");
		StringTokenizer t = new StringTokenizer(input, delim);
		StringBuffer b = new StringBuffer(input.length());
		while (t.hasMoreTokens())b.append(t.nextToken());
		return b.toString();
	}

	public static String getAktuellDateString(int monat) {
		GregorianCalendar calmg = new GregorianCalendar(TimeZone.getTimeZone("ECT"));
		calmg.set(GregorianCalendar.MONTH, (calmg.get(GregorianCalendar.MONTH)+monat));
        return getFormatGreCal(calmg,"MM")+"_"+getFormatGreCal(calmg,"yy");				
	}

	public static String getDatumToday() {
		GregorianCalendar caltoday = new GregorianCalendar(TimeZone.getTimeZone("ECT"));
		return getFormatGreCal(caltoday,DATE_FULL);
	}
	
	public static String getFormatGreCal(GregorianCalendar value, String format){
		SimpleDateFormat fmt = new SimpleDateFormat(format);		       
        return fmt.format(value.getTime());                           
	}
	
    public static GregorianCalendar getDateFromString (String date, String format) {
    	return convString2GreCal(date, format);
    }
    public static GregorianCalendar convString2GreCal (String date, String format) {	
        SimpleDateFormat formatter  = new SimpleDateFormat(format);
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("ECT"));
        try {
            cal.setTimeInMillis(SerFormatter.setCorrectYear(formatter.parse(date)).getTime());
        }catch(ParseException pex){
        }        
        return cal;
    }

    public static GregorianCalendar getGC(GregorianCalendar gc, int value){
        GregorianCalendar newCal = (GregorianCalendar)gc.clone();
        newCal.set(GregorianCalendar.MINUTE, gc.get(GregorianCalendar.MINUTE)+value);
    	return newCal;
    }
  
    public static boolean compareDates(String date1, String date2){    	
    	if( date2.equals("today")){
    		date2=getDatumToday();
    	}    	  
    	return convString2GreCal(date1, DATE_FULL).getTimeInMillis() >= convString2GreCal(date2, DATE_FULL).getTimeInMillis();
    }

    
    /**
     * @param cal1
     * @param cal2
     * @return the value 0 if the time represented by the argument
     *  is equal to the time represented by first Calendar; a value less
     *  than 0 if the time of first Calendar is before the time represented
     *  by the argument; and a value greater than 0 if the time of first
     *  Calendar is after the time represented by the argument.
     */
    public static int compareDates (GregorianCalendar cal1, GregorianCalendar cal2) {
        long date1 = cal1.getTimeInMillis();
        long date2 = cal2.getTimeInMillis();
        if (date1==date2) {
            return 0;
        }
        if (date1<date2) {
            return -1;
        } else {
            return 1;
        }
    }
}