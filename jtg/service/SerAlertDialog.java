package service;
//	Mit SerAlertDialog.alert(String nachricht, Compnonent parentComponent) aufrufen!!!


import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;


public class SerAlertDialog extends JFrame //implements ActionListener
{
	
	public static void alert(String nachricht, Component comp )  { 
		JOptionPane.showMessageDialog(
			comp,
			nachricht,
			"Achtung!",
			JOptionPane.YES_OPTION
		);
	}
	
	public static void alertConnectionLost( String loggingClass, Component comp )  { 
		JOptionPane.showMessageDialog(
			comp,
			"No connection to box",
			"Achtung!",
			JOptionPane.YES_OPTION
		);
		Logger.getLogger(loggingClass).error("No connection to box");
	}
}

