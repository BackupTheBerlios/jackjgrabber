package presentation;
/*
GuiTabAbout.java by Geist Alexander 

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

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import control.ControlAboutTab;

public class GuiTabAbout extends JPanel {

	private ControlAboutTab control;
	private JTextArea taVersion;
	private JTextArea taAuthors;;
	private JTextArea taLicense;
	private JTextArea taOther;
	private JScrollPane scrollPaneLicense;
	private JScrollPane scrollPaneVersion;
	private JScrollPane scrollPaneAuthors;
	private JScrollPane scrollPaneOther;

	public GuiTabAbout(ControlAboutTab ctrl) {
		super();
		this.setControl(ctrl);
		initialize();
	}

	private  void initialize() {
		FormLayout layout = new FormLayout(
						  "150:grow, 8dlu, pref:grow, 8dlu, pref:grow, 8dlu,  pref:grow",  		// columns 
						  "pref, 4dlu, f:50:grow, 4dlu, pref, 4dlu, f:330:grow"); 			// rows
				PanelBuilder builder = new PanelBuilder(this, layout);
				builder.setDefaultDialogBorder();
				CellConstraints cc = new CellConstraints();
		
				builder.addSeparator("Version",					cc.xy	(1, 1));
				builder.add(this.getScrollPaneVersion(),		cc.xy	(1, 3));
				builder.addSeparator("Authors",					cc.xy	(1, 5));
				builder.add(this.getScrollPaneAuthors(),		cc.xy	(1, 7));
	}
	    
	/**
	 * @return Returns the control.
	 */
	public ControlAboutTab getControl() {
		return control;
	}
	/**
	 * @param control The control to set.
	 */
	public void setControl(ControlAboutTab control) {
		this.control = control;
	}
	/**
	 * @return Returns the taAuthors.
	 */
	public JTextArea getTaAuthors() {
		if (taAuthors == null) {
			taAuthors = new JTextArea();
			taAuthors.setEditable(false);
			taAuthors.setLineWrap(true);
			taAuthors.setWrapStyleWord(true);
			taAuthors.setAutoscrolls(true);
		}
		return taAuthors;
	}
	/**
	 * @return Returns the taLicense.
	 */
	public JTextArea getTaLicense() {
		if (taLicense == null) {
			taLicense = new JTextArea();
			taLicense.setEditable(false);
			taLicense.setLineWrap(true);
			taLicense.setWrapStyleWord(true);
			taLicense.setAutoscrolls(true);
		}
		return taLicense;
	}
	/**
	 * @return Returns the taOther.
	 */
	public JTextArea getTaOther() {
		if (taOther == null) {
			taOther = new JTextArea();
			taOther.setEditable(false);
			taOther.setLineWrap(true);
			taOther.setWrapStyleWord(true);
			taOther.setAutoscrolls(true);
		}
		return taOther;
	}
	/**
	 * @return Returns the taVersion.
	 */
	public JTextArea getTaVersion() {
		if (taVersion == null) {
			taVersion = new JTextArea();
			taVersion.setEditable(false);
			taVersion.setLineWrap(true);
			taVersion.setWrapStyleWord(true);
			taVersion.setAutoscrolls(true);
		}
		return taVersion;
	}
	/**
	 * @return Returns the scrollPaneLicense.
	 */
	public JScrollPane getScrollPaneLicense() {
		if (scrollPaneLicense == null) {
			scrollPaneLicense = new JScrollPane();
			scrollPaneLicense.setViewportView(this.getTaLicense());
		}
		return scrollPaneLicense;
	}
	/**
	 * @return Returns the scrollPaneAuthors.
	 */
	public JScrollPane getScrollPaneAuthors() {
		if (scrollPaneAuthors == null) {
			scrollPaneAuthors = new JScrollPane();
			scrollPaneAuthors.setViewportView(this.getTaAuthors());
		}
		return scrollPaneAuthors;
	}
	/**
	 * @return Returns the scrollPaneOther.
	 */
	public JScrollPane getScrollPaneOther() {
		if (scrollPaneOther == null) {
			scrollPaneOther = new JScrollPane();
			scrollPaneOther.setViewportView(this.getTaOther());
		}
		return scrollPaneOther;
	}
	/**
	 * @return Returns the scrollPaneVersion.
	 */
	public JScrollPane getScrollPaneVersion() {
		if (scrollPaneVersion == null) {
			scrollPaneVersion = new JScrollPane();
			scrollPaneVersion.setViewportView(this.getTaVersion());
		}
		return scrollPaneVersion;
	}
}
