package presentation.start;
/*
GuiTabStart.java by Geist Alexander 

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

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;

import model.BOSender;
import model.BOTimer;
import model.BOTimerList;
import service.SerHyperlinkAdapter;
import service.SerIconManager;
import service.SerNewsHandler;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import control.ControlMain;
import control.ControlStartTab;


public class GuiTabStart extends JPanel {

	private ControlStartTab control;
	private JPanel panelClient;
	private JPanel panelInfo;
	private JPanel panelWarn;
	private JPanel panelNews;
	private JPanel panelVersion;
	private JTextPane paneClient;
	private JTextPane paneInfo;
	private JTextPane paneWarn;
	private JTextPane paneNews;
	private JTextPane linkWiki;
	private SerIconManager iconManager = SerIconManager.getInstance();
	Color background = (Color)UIManager.get("Panel.background");
	SerHyperlinkAdapter hyperlinkAdapter = new SerHyperlinkAdapter(this);
	
	
	public GuiTabStart(ControlStartTab ctrl) {
		super();
		this.setControl(ctrl);
		initialize();
	}
	
	/**
     * @return Returns the control.
     */
    public ControlStartTab getControl() {
        return control;
    }
    /**
     * @param control The control to set.
     */
    public void setControl(ControlStartTab control) {
        this.control = control;
    }

	private  void initialize() {
		FormLayout layout = new FormLayout(
						  "pref",  		// columns 
						  "20, pref, 20, pref, 40, pref, 40, pref"); 			// rows
		PanelBuilder builder = new PanelBuilder(this, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();
		
		builder.add(this.getPanelClient(),				cc.xy(1, 2));
		builder.add(this.getPanelInfo(), 				cc.xy(1, 4));
		builder.add(this.getPanelNews(), 				cc.xy(1, 6));
		builder.add(this.getPanelWarn(),	 			cc.xy(1, 8));
	}
	
    /**
     * @return Returns the panelClient.
     */
    public JPanel getPanelClient() {
        if (panelClient == null) {
            panelClient = new JPanel();
			FormLayout layout = new FormLayout("40, 10, pref", //columna
					"pref, 5, pref, 5, pref, 5, pref"); //rows
			PanelBuilder builder = new PanelBuilder(panelClient, layout);
			CellConstraints cc = new CellConstraints();
			
			builder.add(new JLabel(iconManager.getIcon("penguin.png")),			cc.xy(1, 1));
			builder.addTitle("<HTML><font size=5>"+ControlMain.getProperty("label_client")+"</font><HTML>",			cc.xy(3, 1));
			builder.addLabel(ControlMain.version[0],			cc.xy(3, 3));
			builder.add(this.getPanelVersion(),					cc.xy(3, 5));
			builder.add(this.getLinkWiki(),						cc.xy(3, 7));
		}
        return panelClient;
    }
    /**
     * @return Returns the panelInfo.
     */
    public JPanel getPanelInfo() {
        if (panelInfo == null) {
            panelInfo = new JPanel();
			FormLayout layout = new FormLayout("40, 10, pref", //columna
					"pref, 5, pref, 5, pref, pref"); //rows
			PanelBuilder builder = new PanelBuilder(panelInfo, layout);
			CellConstraints cc = new CellConstraints();
			
			builder.add(new JLabel(iconManager.getIcon("info2.png")),			cc.xy(1, 1));
			builder.addTitle("<HTML><font size=5>"+ControlMain.getProperty("label_info")+"</font><HTML>",			cc.xy(3, 1));
			builder.addLabel("Sender: "+this.getRunningSender(),				cc.xy(3, 3));
			builder.addLabel(ControlMain.getProperty("label_nextTimer")+this.getNextTimerInfo(),	cc.xy(3, 5));
//			builder.addLabel(ControlMain.version[0],			cc.xy(3, 3));
		}
        return panelInfo;
    }
    /**
     * @return Returns the panelNews.
     */
    public JPanel getPanelNews() {
        if (panelNews == null) {
            panelNews = new JPanel();
			FormLayout layout = new FormLayout("40, 10, pref", //columna
					"pref, 5, pref, 5, pref, pref"); //rows
			PanelBuilder builder = new PanelBuilder(panelNews, layout);
			CellConstraints cc = new CellConstraints();
			
			builder.add(new JLabel(iconManager.getIcon("browser.png")),			cc.xy(1, 1));
			builder.addTitle("<HTML><font size=5>"+ControlMain.getProperty("label_news")+"</font><HTML>",	cc.xy(3, 1));
			builder.add(this.getPaneNews(),    cc.xy(3, 3));
		}
        return panelNews;
    }
    /**
     * @return Returns the panelWarn.
     */
    public JPanel getPanelWarn() {
        if (panelWarn == null) {
            panelWarn = new JPanel();
			FormLayout layout = new FormLayout("40, 10, pref", //columna
					"pref, pref, pref, pref"); //rows
			PanelBuilder builder = new PanelBuilder(panelWarn, layout);
			CellConstraints cc = new CellConstraints();
			
			builder.add(new JLabel(iconManager.getIcon("warning.png")),			cc.xy(1, 1));
			builder.addTitle("<HTML><font size=5>"+ControlMain.getProperty("label_warn")+"</font><HTML>",			cc.xy(3, 1));
		}
        return panelWarn;
    }
    
    private JPanel getPanelVersion() {
        if (panelVersion==null) {
            panelVersion=new JPanel();
            FormLayout layout = new FormLayout("pref", //columna
			""); //rows 
            PanelBuilder builder = new PanelBuilder(panelVersion, layout);
            CellConstraints cc = new CellConstraints();
            
            try {
                ArrayList version = ControlMain.getBoxAccess().getBoxVersion();
                for (int i=0; i<version.size(); i++) {
                    builder.appendRow("pref");
                    builder.addLabel((String)version.get(i), cc.xy(1, i+1));
                }
                if (version.size()>0) {
                    String conString = ControlMain.getProperty("label_connected1")+
                    	ControlMain.getBoxIpOfActiveBox()+" "+ControlMain.getProperty("label_connected2");
                    builder.appendRow("pref");
                    builder.addLabel(conString, cc.xy(1, version.size()+1));
                } else {
                    
                }
            } catch (IOException e) {
                return panelVersion;
            }
            
        }
        return panelVersion;
    }
    /**
     * @return Returns the paneClient.
     */
    public JTextPane getPaneClient() {
        return paneClient;
    }
    /**
     * @return Returns the paneInfo.
     */
    public JTextPane getPaneInfo() {
        return paneInfo;
    }
    /**
     * @return Returns the paneNews.
     */
    public JTextPane getPaneNews() {
        if (paneNews==null) {
            paneNews = new JTextPane();
            paneNews.setEditable(false);
            paneNews.setBackground(background);
            paneNews.addHyperlinkListener(hyperlinkAdapter);
            new SerNewsHandler(paneNews).start();
        }
        return paneNews;
    }
    /**
     * @return Returns the paneWarn.
     */
    public JTextPane getPaneWarn() {
        return paneWarn;
    }
    /**
	 * @return Returns the linkHomePage.
	 */
	public JTextPane getLinkWiki() {
		if (linkWiki == null) {
		    linkWiki = new JTextPane();		
		    linkWiki.setBackground(background);
		    linkWiki.setContentType("text/html");
		    linkWiki.setEditable(false);
		    linkWiki.setText("<html><a href=\"http://wiki.tuxbox.org/Jack_the_JGrabber\">WIKI</a></html>");			
		    linkWiki.addHyperlinkListener(hyperlinkAdapter);
		}
		return linkWiki;
	}
	
	private String getRunningSender() {
	    try {
	        BOSender sender = ControlMain.getBoxAccess().getRunningSender();
	        if (sender !=null) {
	            return sender.getName();    
	        }
        } catch (IOException e) {
            return new String();
        }
        return new String();
	}
	
	private String getNextTimerInfo() {
	    try {
            BOTimerList list = ControlMain.getBoxAccess().getTimerList();
            BOTimer timer = list.getFirstBoxRecordTimer();
            if (timer!=null) {
                return timer.getStartTime()+"    Sender:"+timer.getSenderName();    
            }
        } catch (IOException e) {
            return new String();
        }
        return new String();
	}
}
