package service;
//	Mit SerAlertDialog.alert(String nachricht, Compnonent parentComponent) aufrufen!!!


import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class SerAlertDialog extends JFrame //implements ActionListener
{
	
	public static void alert(String nachricht, Component comp ) 
		{ 
			JOptionPane.showMessageDialog(
							comp,
							nachricht,
							"Achtung!",
							JOptionPane.YES_OPTION
							);
		}	
}

