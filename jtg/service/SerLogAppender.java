package service;

import java.io.IOException;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.spi.LoggingEvent;

import presentation.GuiMainView;




/**
 * @author Alexander Geist
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SerLogAppender extends RollingFileAppender {
	
	GuiMainView view;
	PatternLayout customlayout;
	
	public SerLogAppender(PatternLayout layout) throws IOException {
		super(layout, "jackLog.log");
	}
	
	public void doAppend(LoggingEvent event) {
		if (this.getView() != null) {
			String outputString = layout.format(event);
			view.getTabProgramm().getJTextPaneAusgabe().append(outputString);
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
