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
import java.util.*;

import javax.swing.JTextArea;
import javax.swing.text.*;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.spi.LoggingEvent;

import presentation.GuiMainView;

public class SerLogAppender extends RollingFileAppender {
	
	GuiMainView view;
	PatternLayout customlayout;
	
	private ArrayList logDestination;
	
	public SerLogAppender(PatternLayout layout) throws IOException {
		super(layout, "jackLog.log");
		logDestination = new ArrayList();
	}
	
	public void doAppend(LoggingEvent event) {
		if (this.getView() != null) {	
			
			if (logDestination.size() == 0)
			{
				logDestination.add(view.getTabProgramm().getJTextPaneAusgabe());
			}
			PatternLayout layout = new PatternLayout();
			//Set outputformat for textpane
			layout.setConversionPattern("%d{HH:mm:ss} %-5p - %m%n");			
			String outputString = layout.format(event);
			
			//JTextArea outputArea = view.getTabProgramm().getJTextPaneAusgabe(); 
			Iterator iter = logDestination.iterator();

			while (iter.hasNext()) {
				JTextArea outputArea = (JTextArea) iter.next();
				outputArea.append(outputString);
				outputArea.setCaretPosition(outputArea.getText().length());	
			}
			
			
		}
		super.doAppend(event);
	}
	/**
	 * @return Returns the tabSettings.
	 */
	public GuiMainView getView() {
		return view;
	}
	/**
	 * @param tabSettings The tabSettings to set.
	 */
	public void setView(GuiMainView view) {
		this.view = view;
	}
	/**
	 * @return Returns the customlayout.
	 */
	public PatternLayout getCustomlayout() {
		return customlayout;
	}
	/**
	 * @param customlayout The customlayout to set.
	 */
	public void setCustomlayout(PatternLayout customlayout) {
		this.customlayout = customlayout;
	}
	
	/** fügt eine weitere JTextArea der Log Ziele hinzu
	 *
	 */ 
	public void addTextArea(JTextArea area)
	{
		logDestination.add(area);
	}
	
	public void removeTextArea(JTextArea area)
	{
		logDestination.remove(area);
	}
}
