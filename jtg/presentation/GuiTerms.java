package presentation;
/*
GuiTerms.java by Geist Alexander 

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
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import control.ControlMain;
import control.ControlMainView;

/**
 * Terms & ConditionsGUI, based on X-Project-StartUp-Gui
 */

public class GuiTerms extends JFrame  {
	
	boolean agreement = false;
	JRadioButton disagree;
	JRadioButton agree;
	ControlMainView listener;

	public GuiTerms(ControlMainView list) {
		this.setListener(list);
		open("Jack the JGrabber");
	}

	public GuiTerms(String title) {
		open(title);
	}

	protected void open(String title) {
		setTitle(title);

		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.setBorder( BorderFactory.createEmptyBorder(10,10,10,10));

		String terms[] = ControlMain.getTerms();

		for (int a=0; a<terms.length; a++) 
			container.add(new JLabel(terms[a]));

		disagree = new JRadioButton("I disagree (closing)");
		disagree.setOpaque(false);
		agree = new JRadioButton("I agree");
		agree.setOpaque(false);

		ButtonGroup BrGroup = new ButtonGroup();
		BrGroup.add(disagree);
		BrGroup.add(agree);

		container.add(disagree);
		container.add(agree);
		disagree.addActionListener(this.getListener());
		agree.addActionListener(this.getListener());

		JPanel container2 = new JPanel();
		container2.setBorder( BorderFactory.createRaisedBevelBorder());
		container2.add(container);

		getContentPane().add(container2);
		pack();
		setLocation(200,200);

		addWindowListener (new WindowAdapter() { 
			public void windowClosing(WindowEvent e) { 
				System.exit(0); 
			}
		});

		return;
	}

	public void set( boolean agreement) {
		this.agreement = agreement;
		agree.setSelected(agreement);
		if (agreement)
			agree.setForeground(Color.green);
	}

	public boolean get() {
		return agree.isSelected();
	}

	public void close() {
		dispose();
	}
	/**
	 * @return Returns the listener.
	 */
	public ControlMainView getListener() {
		return listener;
	}
	/**
	 * @param listener The listener to set.
	 */
	public void setListener(ControlMainView listener) {
		this.listener = listener;
	}
}

