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

	public static String ersetzeUmlaute(String input) {

		StringBuffer umlBuf = new StringBuffer();
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			switch (c) {
				case 'ä' : {
					umlBuf.append("ae");
					break;
				}
				case 'ö' : {
					umlBuf.append("oe");
					break;
				}
				case 'ü' : {
					umlBuf.append("ue");
					break;
				}
				case 'ß' : {
					umlBuf.append("ss");
					break;
				}
				case 'Ü' : {
					umlBuf.append("Ue");
					break;
				}
				case 'Ö' : {
					umlBuf.append("Oe");
					break;
				}
				case 'Ä' : {
					umlBuf.append("Ae");
					break;
				}
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
	    GregorianCalendar cal = SerFormatter.getDateFromString(start, "hh:mm");
		cal.set(GregorianCalendar.HOUR_OF_DAY, cal.get(GregorianCalendar.HOUR_OF_DAY)+ Integer.parseInt(ende.substring(0, 2)));
		cal.set(GregorianCalendar.MINUTE, cal.get(GregorianCalendar.MINUTE)+ Integer.parseInt(ende.substring(3, 5)));
		return out(cal.get(GregorianCalendar.HOUR_OF_DAY)) + ":"
				+ out(cal.get(GregorianCalendar.MINUTE));
	}

	public static String getCorrectDate(String datum) {
	    GregorianCalendar cal = SerFormatter.getDateFromString(datum, "dd.MM./HH:mm");
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

	public static String getAktuellDateString() {
		GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("ECT"));
		return "mguide_d_s_"
				+ String.valueOf((cal.get(GregorianCalendar.MONTH) + 1))
				+ "_"
				+ String.valueOf((cal.get(GregorianCalendar.YEAR))).substring(2) + ".txt";
	}

	public static String getDatumToday() {
		GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("ECT"));
		DateFormat formater2 = DateFormat.getDateInstance(DateFormat.FULL);
		return formater2.format(cal.getTime()).toString();
	}
	public static String getShortDate(long i) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd.MMM.yy");
		return sdf.format(new Date(i));
	}
	public static long getStringToLong(String start) {
		 GregorianCalendar cal = SerFormatter.getDateFromString(start, "EEEE, dd. MMMM yyyy");
		 return cal.getTimeInMillis();
	}
	
	public static long getStringToLongWithTime(String start, long time) {
		GregorianCalendar cal = SerFormatter.getDateFromString(start, "EEEE, dd. MMMM yyyy,HH:mm");
		cal.setTimeInMillis(cal.getTimeInMillis() + time);
		return cal.getTimeInMillis();
	}
	
    public static GregorianCalendar getDateFromString (String date, String format) {
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
    public static String getGC2String(GregorianCalendar gc){
    	return ( out(gc.get(GregorianCalendar.HOUR_OF_DAY))+":"+out(gc.get(GregorianCalendar.MINUTE)));
    }
}