package presentation.about;
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

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import control.ControlAboutTab;
import control.ControlMain;

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
	private JPanel jGrabberInfo;
	private JLabel linkHomePage, linkApache, linkProjectX, linkUdrec, linkDom4j, linkForms, linkSystray, labelOther, labelJGrabber, labelJGrabber2;

	public GuiTabAbout(ControlAboutTab ctrl) {
		super();
		this.setControl(ctrl);
		initialize();
	}

	private  void initialize() {
		FormLayout layout = new FormLayout(
						  "200:grow, 8dlu, pref:grow",  		// columns 
						  "pref, 4dlu, f:50:grow, 4dlu, pref, 4dlu, f:320:grow, pref"); 			// rows
				PanelBuilder builder = new PanelBuilder(this, layout);
				builder.setDefaultDialogBorder();
				CellConstraints cc = new CellConstraints();
		
				builder.addSeparator(ControlMain.getProperty("label_version"),					cc.xy	(1, 1));
				builder.add(this.getScrollPaneVersion(),															cc.xy	(1, 3));
				builder.addSeparator(ControlMain.getProperty("label_authors"),					cc.xy	(1, 5));
				builder.add(this.getScrollPaneAuthors(),															cc.xywh	(1, 7, 1, 2));
				builder.add(this.getPanelJGrabberInfo(),															cc.xywh	(3, 1, 1, 6));	
				builder.add(new JLabel(this.getImageLogo()),												cc.xywh	(3, 7, 1, 1));
				builder.add(this.getLinkHomePage(),																cc.xywh	(3, 8, 1, 1, CellConstraints.CENTER, CellConstraints.FILL ));
	}
	
	private JPanel getPanelJGrabberInfo() {
		if (jGrabberInfo == null) {
			jGrabberInfo = new JPanel();
			FormLayout layout = new FormLayout(
			        "f:pref:grow, f:20,  f:pref:grow",	 		//columns 
			  "pref, pref, 10, pref, pref, pref, pref, pref");							//rows
			PanelBuilder builder = new PanelBuilder(jGrabberInfo, layout);
			CellConstraints cc = new CellConstraints();

			builder.add(this.getLabelJgrabber(),																cc.xywh	(1, 1, 3, 1));
			builder.add(this.getLabelJgrabber2(),																cc.xywh	(1, 2, 3, 1));
			builder.add(this.getLabelOther(),																		cc.xywh	(1, 4, 3, 1));
			builder.add(this.getLinkApache(),																	cc.xywh	(1, 5, 1, 1));
			builder.add(this.getLinkDom4j(),																		cc.xywh	(1, 6, 1, 1));
			builder.add(this.getLinkForms(),																		cc.xywh	(1, 7, 1, 1));
			builder.add(this.getLinkProjectX(),																	cc.xywh	(3, 5, 1, 1));
			builder.add(this.getLinkUdrec(),																		cc.xywh	(3, 6, 1, 1));
			builder.add(this.getLinkSystray(),																	cc.xywh	(3, 7, 1, 1));
		}
		return jGrabberInfo;
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
	
	private ImageIcon getImageLogo() {
		if (imageLogo == null) {
			imageLogo = new ImageIcon(ClassLoader.getSystemResource("ico/grabber1.png"));
		}
		return imageLogo;
	}
	
	/**
	 * @return Returns the linkHomePage.
	 */
	public JLabel getLinkApache() {
		if (linkApache == null) {
			linkApache = new JLabel("<HTML><font color=blue><u>commons & log4j</u></font></HTML>");
			linkApache.setName("www.apache.org");
			linkApache.addMouseListener(control);
		}
		return linkApache;
	}
	
	/**
	 * @return Returns the linkHomePage.
	 */
	public JLabel getLinkProjectX() {
		if (linkProjectX == null) {
			linkProjectX = new JLabel("<HTML><font color=blue><u>Project X</u></font></HTML>");
			linkProjectX.setName("www.lucike.info/");
			linkProjectX.addMouseListener(control);
		}
		return linkProjectX;
	}
	
	/**
	 * @return Returns the linkHomePage.
	 */
	public JLabel getLinkUdrec() {
		if (linkUdrec == null) {
			linkUdrec = new JLabel("<HTML><font color=blue><u>udrec</u></font></HTML>");
			linkUdrec.setName("www.haraldmaiss.de");
			linkUdrec.addMouseListener(control);
		}
		return linkUdrec;
	}
	
	/**
	 * @return Returns the linkHomePage.
	 */
	public JLabel getLinkDom4j() {
		if (linkDom4j == null) {
			linkDom4j = new JLabel("<HTML><font color=blue><u>dom4j</u></font></HTML>");
			linkDom4j.setName("www.dom4j.org");
			linkDom4j.addMouseListener(control);
		}
		return linkDom4j;
	}
	
	/**
	 * @return Returns the linkHomePage.
	 */
	public JLabel getLinkForms() {
		if (linkForms == null) {
			linkForms = new JLabel("<HTML><font color=blue><u>forms & looks</u></font></HTML>");
			linkForms.setName("www.jgoodies.com/freeware/forms/index.html");
			linkForms.addMouseListener(control);
		}
		return linkForms;
	}
	
	/**
	 * @return Returns the linkHomePage.
	 */
	public JLabel getLinkSystray() {
		if (linkSystray == null) {
			linkSystray = new JLabel("<HTML><font color=blue><u>systray4j</u></font></HTML>");
			linkSystray.setName("http://sourceforge.net/projects/systray/");
			linkSystray.addMouseListener(control);
		}
		return linkSystray;
	}
	
	/**
	 * @return Returns the linkGpl.
	 */
	public JLabel getLabelOther() {
		if (labelOther == null) {
			labelOther = new JLabel("This product includes software developed by other projects");
		}
		return labelOther;
	}
	
	/**
	 * @return Returns the linkHomePage.
	 */
	public JLabel getLinkHomePage() {
		if (linkHomePage == null) {
			linkHomePage = new JLabel("<HTML><font color=blue><u>Jack the Grabber</u></font></HTML>");
			linkHomePage.setName("www.jackthegrabber.de");
			linkHomePage.addMouseListener(control);
		}
		return linkHomePage;
	}
	
	/**
	 * @return Returns the linkHomePage.
	 */
	public JLabel getLabelJgrabber() {
		if (labelJGrabber == null) {
			labelJGrabber = new JLabel("Jack the JGrabber is free software, and is released under the");
			labelJGrabber.setName("www.jackthegrabber.de");
			labelJGrabber.addMouseListener(control);
		}
		return labelJGrabber;
	}
	
	/**
	 * @return Returns the linkHomePage.
	 */
	public JLabel getLabelJgrabber2() {
		if (labelJGrabber2 == null) {
			labelJGrabber2 = new JLabel("<HTML><font color=blue><u>GNU General Public License</u></font></HTML>");
			labelJGrabber2.setName("www.gnu.org/licenses/gpl.txt");
			labelJGrabber2.addMouseListener(control);
		}
		return labelJGrabber2;
	}
}
