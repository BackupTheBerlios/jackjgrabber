/*
GuiSettingsTabPlayback.java by Geist Alexander 

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
package presentation;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import control.ControlSettingsTabPlayback;

public class GuiSettingsTabPlayback extends GuiTab{
    
    private ControlSettingsTabPlayback control;
    private JPanel panelPlaybackSettings = null;
	private JTextField jTextFieldPlaybackString = null;
    
    public GuiSettingsTabPlayback(ControlSettingsTabPlayback ctrl) {
		super();
		this.setControl(ctrl);
		initialize();
	}
    
    protected void initialize() {
        FormLayout layout = new FormLayout(
				  "f:pref:grow",  		// columns 
				  "f:pref"); 			// rows
		PanelBuilder builder = new PanelBuilder(this, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();

		builder.add(this.getPanelPlaybackSettings(),		   		cc.xy(1,1));        
    }

    private JPanel getPanelPlaybackSettings() {
		if (panelPlaybackSettings == null) {
			panelPlaybackSettings = new JPanel();
			FormLayout layout = new FormLayout(
					  "pref, 10, pref",	 		//columna 
			  "pref, 10, pref, pref, 15, pref");	//rows
			PanelBuilder builder = new PanelBuilder(panelPlaybackSettings, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator("Wiedergabe-Optionen",										cc.xywh	(1, 1, 3, 1));
			builder.add(new JLabel("z.B. xine http://$ip:31339/$vPid,$aPid"),				cc.xywh	(1, 3, 3, 1));
			builder.add(new JLabel("z.B. d://programme/mplayer/mplayer.exe http://$ip:31339/$vPid,$aPid"),	cc.xywh	(1, 4, 3, 1));
			builder.add(new JLabel("Execute"),												cc.xy	(1, 6));
			builder.add(this.getJTextFieldPlaybackString(),									cc.xy	(3, 6));
		}
		return panelPlaybackSettings;
	}
    /**
	 * @return Returns the jTextFieldPlaybackString.
	 */
	public JTextField getJTextFieldPlaybackString() {
		if (jTextFieldPlaybackString == null) {
			jTextFieldPlaybackString = new JTextField();
			jTextFieldPlaybackString.addKeyListener(control);
			jTextFieldPlaybackString.setName("playbackString");
			jTextFieldPlaybackString.setPreferredSize(new Dimension(340, 19));
		}
		return jTextFieldPlaybackString;
	}
    /**
     * @return Returns the control.
     */
    public ControlSettingsTabPlayback getControl() {
        return control;
    }
    /**
     * @param control The control to set.
     */
    public void setControl(ControlSettingsTabPlayback control) {
        this.control = control;
    }
}
