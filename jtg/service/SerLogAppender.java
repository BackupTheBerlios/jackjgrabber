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

import javax.swing.JTextArea;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.spi.LoggingEvent;

import presentation.GuiMainView;

public class SerLogAppender extends RollingFileAppender {
	
	GuiMainView view;
	PatternLayout customlayout;
	
	public SerLogAppender(PatternLayout layout) throws IOException {
		super(layout, "jackLog.log");
	}
	
	public void doAppend(LoggingEvent event) {
		if (this.getView() != null) {		
			PatternLayout layout = new PatternLayout();
			//Set outputformat for textpane
			layout.setConversionPattern("%d{HH:mm:ss} %-5p - %m%n");			
			String outputString = layout.format(event);
			
			JTextArea outputArea = view.getTabProgramm().getJTextPaneAusgabe(); 
			outputArea.append(outputString);
			outputArea.setCaretPosition(outputArea.getText().length());
		}
		super.doAppend(event);
	}
	/**
	 * @return Returns the view.
	 */
	public GuiMainView getView() {
		return view;
	}
	/**
	 * @param view The view to set.
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
}
