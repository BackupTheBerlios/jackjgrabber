/*
 * Created on 11.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;

import presentation.GuiMainView;
import service.SerAlertDialog;
import service.SerXMLConverter;
import service.SerXMLHandling;


/**
 * @author AlexG
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ControlSettingsTab extends ControlTab implements ActionListener, MouseListener {

	GuiMainView mainView;
	
	public ControlSettingsTab(GuiMainView view) {
		this.setMainView(view);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see control.ControlTab#initialize()
	 */
	public void initialize() {
		this.getMainView().getTabSettings().getTfBoxIp().setText(ControlMain.getBoxIp());
		this.getMainView().getTabSettings().getTfVlcPath().setText(ControlMain.getSettings().getVlcPath());
	}
	
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action == "save") {
			this.actionSpeichern();
		}
		if (action == "VlcFileChooser") {
			this.openFileChooser();
		}
	}
	
	public void mousePressed(MouseEvent me) {
		JTable table = (JTable)me.getSource();
		String tableName = table.getName();
		if (tableName == "bla") {
			
		}
	}
	
	public void mouseClicked(MouseEvent me)
	{}
	public void mouseReleased(MouseEvent me)
	{}
	public void mouseExited(MouseEvent me)
	{}
	public void mouseEntered(MouseEvent me)
	{}

	private void actionSpeichern() {	
		//Abfrage der zu speichernden Werte
		ControlMain.setBoxIp(this.getMainView().getTabSettings().tfBoxIp.getText());
		ControlMain.setVlcPath(this.getMainView().getTabSettings().tfVlcPath.getText());
		//erstellen eines XML-Dokuments aus den erstellten Settings
		SerXMLConverter.buildXMLDocument(ControlMain.getSettingsDocument(), ControlMain.getSettings());
		try {
			SerXMLHandling.saveXMLFile(new File("settings.xml"), ControlMain.getSettingsDocument());				
		} catch (IOException ex) {SerAlertDialog.alert("Fehler beim Speichern!", this.getMainView());};
	}
	
	private void openFileChooser() {		
		JFileChooser chooser = new JFileChooser();
		FileFilter filter = new FileFilter(){
			public boolean accept(File f){
				return (f.getName().endsWith("vlc.exe") || f.isDirectory() );
			}
			public String getDescription(){
				return "vlc.exe";
			}
		};
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog( null ) ;
	
		if ( returnVal == JFileChooser.APPROVE_OPTION ) {
			String path = chooser.getSelectedFile().toString();
			this.getMainView().getTabSettings().getTfVlcPath().setText(path);
			actionSpeichern();		
		}
	}
	
	/**
	 * @return Returns the mainView.
	 */
	public GuiMainView getMainView() {
		return mainView;
	}
	/**
	 * @param mainView The mainView to set.
	 */
	public void setMainView(GuiMainView mainView) {
		this.mainView = mainView;
	}
}
