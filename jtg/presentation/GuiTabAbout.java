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

import javax.swing.ImageIcon;
import javax.swing.JLabel;
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
	private ImageIcon imageLogo;
	private JLabel linkHomePage;

	public GuiTabAbout(ControlAboutTab ctrl) {
		super();
		this.setControl(ctrl);
		initialize();
	}

	private  void initialize() {
		FormLayout layout = new FormLayout(
						  "pref:grow, 8dlu, 400:grow",  		// columns 
						  "pref, 4dlu, f:50:grow, 4dlu, pref, 4dlu, f:300:grow, pref"); 			// rows
				PanelBuilder builder = new PanelBuilder(this, layout);
				builder.setDefaultDialogBorder();
				CellConstraints cc = new CellConstraints();
		
				builder.addSeparator("Version",					cc.xy	(1, 1));
				builder.add(this.getScrollPaneVersion(),		cc.xy	(1, 3));
				builder.addSeparator("Authors",					cc.xy	(1, 5));
				builder.add(this.getScrollPaneAuthors(),		cc.xywh	(1, 7, 1, 2));
				builder.add(new JLabel(this.getImageLogo()),	cc.xywh	(3, 1, 1, 7));
				builder.add(this.getLinkHomePage(),				cc.xywh	(3, 8, 1, 1, CellConstraints.CENTER, CellConstraints.FILL ));
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
			scrollPaneVersion.setPreferredSize(new Dimension(250, 120));
		}
		return scrollPaneVersion;
	}
	
	private ImageIcon getImageLogo() {
		if (imageLogo == null) {
			imageLogo = new ImageIcon(ClassLoader.getSystemResource("ico/grabber1.png"));
		}
		return imageLogo;
	}
	
	/**
	 * @return Returns the linkHomePage.
	 */
	public JLabel getLinkHomePage() {
		if (linkHomePage == null) {
			linkHomePage = new JLabel("<HTML><font color=blue><u>www.jackthegrabber.de</u></font></HTML>");
			linkHomePage.setName("www.jackthegrabber.de");
			linkHomePage.addMouseListener(control);
		}
		return linkHomePage;
	}
}
