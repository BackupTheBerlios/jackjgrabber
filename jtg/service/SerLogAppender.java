package service;
/*
SerLogAppender.java by Geist Alexander 

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.  

*/
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextArea;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.spi.LoggingEvent;

public class SerLogAppender extends RollingFileAppender {
	
	static ArrayList textAreas = new ArrayList();
	static PatternLayout patternLayout;
	
	public SerLogAppender(PatternLayout layout) throws IOException {
		super(layout, "jackLog.log");
	}
	
	public void doAppend(LoggingEvent event) {
		if (getTextAreas().size()>0) {	
			String outputString = getPatternLayout().format(event);
			
			for (int i=0; i<getTextAreas().size(); i++) {
			    JTextArea outputArea = (JTextArea)getTextAreas().get(i);
			    outputArea.append(outputString);
					outputArea.setCaretPosition(outputArea.getText().length());			    
			}
		}
		super.doAppend(event);
	}
	
	 /**
   * @return Returns the patternLayout.
   */
  public static PatternLayout getPatternLayout() {
      if (patternLayout==null) {
          patternLayout = new PatternLayout();
          patternLayout.setConversionPattern("%d{HH:mm:ss} %-5p - %m%n");			
      }
      return patternLayout;
  }
    /**
     * @return Returns the textAreas.
     */
    public static ArrayList getTextAreas() {
        return textAreas;
    }
    /**
     * @param textAreas The textAreas to set.
     */
    public static void setTextAreas(ArrayList textAreas) {
        SerLogAppender.textAreas = textAreas;
    }
}
