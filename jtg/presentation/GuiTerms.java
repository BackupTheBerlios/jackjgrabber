package presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import control.ControlMain;

/**
 * @author Alexander Geist
 * Terms & ConditionsGUI, based on X-Project-StartUp-Gui
 */

public class GuiTerms extends JFrame  {
	
	boolean agreement = false;
	JRadioButton disagree;
	JRadioButton agree;
	ControlMain listener;

	public GuiTerms(ControlMain list) {
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
	public ControlMain getListener() {
		return listener;
	}
	/**
	 * @param listener The listener to set.
	 */
	public void setListener(ControlMain listener) {
		this.listener = listener;
	}
}

