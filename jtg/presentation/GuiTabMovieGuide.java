package presentation;
/*
GuiTabMovieGuide.java by Ralph Henneberger 

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

//import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JTable;
//import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javax.swing.JProgressBar; 

import service.SerMovieGuide2Xml;
import model.BOMovieGuide;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import control.ControlMovieGuideTab;




public class GuiTabMovieGuide extends JPanel {

	private ControlMovieGuideTab control;
	private JButton jButtonNeuEinlesen= null;
	private JButton jButtonDownload = null;
	private JButton jButtonSelect2Timer = null;
	private JPanel jPanelButtonsGui = null;
	private JTextArea taBeschreibung;
	private JTextArea taDarsteller;
	private JTextArea taEpisode;
	private JComboBox comboBoxGenre = null;
	private JComboBox comboBoxDatum = null;
	private JTable jTableFilm = null;
	private JTable jTableSender = null;
	private JTable jTableTimer = null;
	private JScrollPane scrollPaneBeschreibung;
	private JScrollPane scrollPaneDarsteller;
	private JScrollPane scrollPaneEpisode;
	private JFormattedTextField tfBild;
	private JFormattedTextField tfTon;
	private JProgressBar pbDownload;
	private JTextField tfSuche;
	private JScrollPane jPaneTitel= null;
	private JPanel jPanelTitel = null;
	
	private JComboBox comboBoxSender = null;
	private JPanel jPanelChannel = null;
	private JPanel jPanelTimer = null;
	private JPanel jPanelButtonsDatum = null;
	private JButton jButtonToTimer = null;
	private JScrollPane jScrollPane = null;
	private JScrollPane jScrollPaneTimer = null;
	public MovieGuideTimerTableModel mgTimerTableModel;
	public GuiTableSorter sorter = null;
	private JButton jButtonAllSender = null;
	public MovieGuideTimerTableModel timerTableModel;
	public MovieGuideSenderTableModel senderTableModel;
	public MovieGuideFilmTableModel filmTableModel;
	
	public GuiTabMovieGuide(ControlMovieGuideTab ctrl) {
		super();
		this.setControl(ctrl);
		initialize();
	}

	private  void initialize() {
		FormLayout layout = new FormLayout(
			      "pref, 10, 150, 10, pref, 10, pref, 10, 250:grow",  							// columns 
			      "pref, 263px:grow, 10, pref, pref, 3dlu, pref, 100px:grow");	// rows
		PanelBuilder builder = new PanelBuilder(this, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();
		builder.add(this.getJPanelButtonsDatum(),  					cc.xywh	(1, 1, 1, 1, CellConstraints.FILL, CellConstraints.FILL));		
		builder.add(this.getJPanelChannels(),  						cc.xywh	(1, 2, 1, 1, CellConstraints.FILL, CellConstraints.FILL));		

		//builder.addSeparator("TimerInfos",			   				cc.xywh	(3, 1, 7, 1));
		builder.add(this.getJPanelTimer(),							cc.xywh	(3, 1, 7, 1));
	
	}
	
	private JPanel getJPanelButtonsDatum() {
		if (jPanelButtonsDatum == null) {
			jPanelButtonsDatum = new JPanel();
			FormLayout layout = new FormLayout(		
			  "pref, 1dlu,pref, 1dlu",	 		//columna 
		      "pref");	//rows
			PanelBuilder builder = new PanelBuilder(jPanelButtonsDatum, layout);
			CellConstraints cc = new CellConstraints();
			builder.add(this.getComboBoxDatum(),			cc.xyw	(1, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJButtonSetAllSender(),		cc.xyw	(3, 1, 1, CellConstraints.FILL, CellConstraints.FILL));			
		}
		return jPanelButtonsDatum;
	}

	private JPanel getJPanelChannels() {
		if (jPanelChannel == null) {
			jPanelChannel = new JPanel();
			FormLayout layout = new FormLayout(
				      "320",									//column 
				      "pref, 4px, pref, pref, min:grow, pref, pref");		//rows
			PanelBuilder builder = new PanelBuilder(jPanelChannel, layout);
			CellConstraints cc = new CellConstraints();			
			builder.addSeparator("Titel, Doppelklick toTimer",	cc.xyw	(1, 1, 1));
			builder.add(this.getJScrollPaneChannels(), 			cc.xyw	(1, 5, 1, CellConstraints.FILL, CellConstraints.FILL));			
			builder.add(this.getJButtonSelectedToTimer(), 	    cc.xyw	(1, 6, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.add(this.getJButtonDownload(), 			    cc.xyw	(1, 7, 1, CellConstraints.FILL, CellConstraints.FILL));
		}
		return jPanelChannel;
	}
	private JPanel getJPanelTimer() {
		if (jPanelTimer == null) {
			jPanelTimer = new JPanel();
			FormLayout layout = new FormLayout(
				      "320",									//column 
				      "pref, 4px, pref, pref, min:grow, pref, pref");		//rows
			PanelBuilder builder = new PanelBuilder(jPanelTimer, layout);
			CellConstraints cc = new CellConstraints();			
			builder.add(this.getComboBoxSender(),		  		cc.xyw	(1, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.addSeparator("Datum",	cc.xyw	(1, 3, 1));
			builder.add(this.getJScrollPaneTimer(), 			cc.xyw	(1, 5, 1, CellConstraints.FILL, CellConstraints.FILL));			
		}
		return jPanelTimer;
	}
	
	
	public JButton getJButtonSelectedToTimer() {
		if (jButtonToTimer == null) {
			jButtonToTimer = new JButton();
			jButtonToTimer.setText("Selected to Timer");
			jButtonToTimer.setToolTipText("ausgewählte Programme zum Timer hinzufügen.");
			jButtonToTimer.addActionListener(this.getControl());
		}
		return jButtonToTimer;
	}
	public JButton getJButtonDownload() {
		if (jButtonDownload == null) {
			jButtonDownload = new JButton();
			jButtonDownload.setText("PMG Download");
			jButtonDownload.setToolTipText("Holt den aktuellen MovieGuide.");
			jButtonDownload.addActionListener(this.getControl());
		}
		return jButtonDownload;
	}
	public JButton getJButtonSetAllSender() {
		if (jButtonAllSender == null) {
			jButtonAllSender = new JButton();
			jButtonAllSender.setText("Alle");
			jButtonAllSender.setToolTipText("Zeigt alle Sender.");
			jButtonAllSender.addActionListener(this.getControl());
		}
		return jButtonAllSender;
	}
	
	private JScrollPane getJScrollPaneChannels() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			//jScrollPane.setViewportView(getJTableChannels());
		}
		return jScrollPane;
	}
	private JScrollPane getJScrollPaneTimer() {
		if (jScrollPaneTimer == null) {
			jScrollPaneTimer = new JScrollPane();
			jScrollPaneTimer.setViewportView(getJTableTimer());
		}
		return jScrollPaneTimer;
	}
	public JTable getJTableTimer() {
		if (jTableTimer == null) {
			mgTimerTableModel = new MovieGuideTimerTableModel(control);
			sorter = new GuiTableSorter(mgTimerTableModel);
			jTableTimer = new JTable(sorter);
			sorter.setTableHeader(jTableTimer.getTableHeader());
			
			TableColumn eventIdColumnt = jTableTimer.getColumnModel().getColumn(0);
			jTableTimer.getTableHeader().getColumnModel().removeColumn(eventIdColumnt); //eventId ausblenden 
			jTableTimer.getColumnModel().getColumn(0).setMaxWidth(50);
			jTableTimer.getColumnModel().getColumn(1).setMaxWidth(50);
			jTableTimer.getColumnModel().getColumn(2).setMaxWidth(60);
			jTableTimer.getColumnModel().getColumn(3).setPreferredWidth(50);
			//jTableTimer.getColumnModel().getColumn(3).setMaxWidth(280);
			jTableTimer.addMouseListener(control);
			jTableTimer.setName("Info");
		}
		return jTableTimer;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @return Returns the control.
	 */
	public ControlMovieGuideTab getControl() {
		return control;
	}
	/**
	 * @param control The control to set.
	 */
	public void setControl(ControlMovieGuideTab control) {
		this.control = control;
	}
	
	public JPanel getJPanelButtonsGui() {
		if (jPanelButtonsGui == null) {
			jPanelButtonsGui = new JPanel();
			FormLayout layout = new FormLayout(
					"pref, 10, 150, 10, pref, 10, pref, 10, 250:grow",  							// columns 
		      "pref, 263px:grow, 10, pref, pref, 3dlu, pref, 100px:grow");	// rows
			PanelBuilder builder = new PanelBuilder(jPanelButtonsGui, layout);
			CellConstraints cc = new CellConstraints();	
			builder.add(this.getJButtonSelect2Timer(), cc.xy(1, 5));
			builder.add(this.getJButtonNeuEinlesen(), cc.xy(3, 5));
			builder.add(this.getJButtonDownload(), cc.xy(5, 5));				
			builder.add(this.getComboBoxDatum(), cc.xy(1,2));
			builder.add(this.getComboBoxGenre(), cc.xy(3,2));
			builder.add(this.getJTableFilm(), cc.xy(5,2));			
			
		}
		return jPanelButtonsGui;
	}
	
	public JFormattedTextField getTfBild() {
		if (tfBild == null) {
			tfBild = new JFormattedTextField();
			tfBild.setEditable(false);			
			tfBild.setAutoscrolls(false);
		}
		return tfBild;
	}
	
	public JFormattedTextField getTfTon() {
		if (tfTon == null) {
			tfTon = new JFormattedTextField();
			tfTon.setEditable(false);			
			tfTon.setAutoscrolls(false);
		}
		return tfTon;
	}
	public JTextField getTfSuche() {
		if (tfSuche == null) {
			tfSuche = new JFormattedTextField();
			tfSuche.setEditable(true);			
			tfSuche.setAutoscrolls(true);
		}
		return tfBild;
	}
	

	public JTextArea getTaBeschreibung() {
		if (taBeschreibung == null) {
			taBeschreibung = new JTextArea();
			taBeschreibung.setEditable(false);
			taBeschreibung.setLineWrap(true);
			taBeschreibung.setWrapStyleWord(true);
			taBeschreibung.setAutoscrolls(true);
			taBeschreibung.setPreferredSize(new java.awt.Dimension(40,19));
		}
		return taBeschreibung;
	}
	public JTextArea getTaDarsteller() {
		if (taDarsteller == null) {
			taDarsteller = new JTextArea();
			taDarsteller.setEditable(false);
			taDarsteller.setLineWrap(true);
			taDarsteller.setWrapStyleWord(true);
			taDarsteller.setAutoscrolls(true);
		}
		return taDarsteller;
	}
	public JTextArea getTaEpisode() {
		if (taEpisode == null) {
			taEpisode = new JTextArea();
			taEpisode.setEditable(false);
			taEpisode.setLineWrap(true);
			taEpisode.setWrapStyleWord(true);
			taEpisode.setAutoscrolls(true);
		}
		return taEpisode;
	}
	
	public JScrollPane getScrollPaneBeschreibung() {
		if (scrollPaneBeschreibung == null) {
			scrollPaneBeschreibung = new JScrollPane();
			scrollPaneBeschreibung.setViewportView(this.getTaBeschreibung());
		}
		return scrollPaneBeschreibung;
	}
	public JScrollPane getScrollPaneDarsteller() {
		if (scrollPaneDarsteller == null) {
			scrollPaneDarsteller = new JScrollPane();
			scrollPaneDarsteller.setViewportView(this.getTaDarsteller());
		}
		return scrollPaneDarsteller;
	}
	public JScrollPane getScrollPaneEpisode() {
		if (scrollPaneEpisode == null) {
			scrollPaneEpisode = new JScrollPane();
			scrollPaneEpisode.setViewportView(this.getTaEpisode());
		}
		return scrollPaneEpisode;
	}
	
	public JButton getJButtonNeuEinlesen() {
		if (jButtonNeuEinlesen == null) {
			jButtonNeuEinlesen = new JButton("Neu einlesen");
			jButtonNeuEinlesen.setActionCommand("neuEinlesen");
			jButtonNeuEinlesen.setPreferredSize(new Dimension(150,25));
			jButtonNeuEinlesen.addActionListener(control);
		}
		return jButtonNeuEinlesen;
	}
	public JButton getJButtonSelect2Timer() {
		if (jButtonSelect2Timer == null) {
			jButtonSelect2Timer = new JButton("Select2Timer");
			jButtonSelect2Timer.setActionCommand("select2Timer");
			jButtonSelect2Timer.setPreferredSize(new Dimension(150,25));
			jButtonSelect2Timer.addActionListener(control);
		}
		return jButtonSelect2Timer;
	}
	public JComboBox getComboBoxGenre(){
		if (comboBoxGenre == null) {
			SerMovieGuide2Xml guide = new SerMovieGuide2Xml();				
			comboBoxGenre = new JComboBox(new BOMovieGuide().getGenreList().toArray());
		}
		return comboBoxGenre;
	}
	public JComboBox getComboBoxDatum() {
		if (comboBoxDatum == null) {
			//comboBoxDatum = new JComboBox(new GuiTimerSenderComboModel(control));
			comboBoxDatum = new JComboBox(new BOMovieGuide().getDatumList().toArray());
		}
		return comboBoxDatum;
	}
	public JComboBox getComboBoxSender() {
		if (comboBoxSender == null) {
			//comboBoxDatum = new JComboBox(new GuiTimerSenderComboModel(control));
			comboBoxSender = new JComboBox(new BOMovieGuide().getSenderList().toArray());
		}
		return comboBoxSender;
	}
	
	private JPanel getPanelTitel() {
		if (jPanelTitel == null) {
			jPanelTitel = new JPanel();
			FormLayout layout = new FormLayout(
					  "pref:grow, 5, pref",	 		//columna 
			  "pref:grow, pref:grow, pref:grow, 40:grow");				//rows
			PanelBuilder builder = new PanelBuilder(jPanelTitel, layout);
			CellConstraints cc = new CellConstraints();

			builder.addSeparator("Box-Settings",						cc.xywh	(1, 1, 3, 1));
	//		builder.add(this.getJScrollTitel(),	  			cc.xywh	(1, 2, 1, 3));		
		}
		return jPanelTitel;
	}
	

	public JTable getJTableFilm() {
		if (jTableFilm == null) {			
			filmTableModel = new MovieGuideFilmTableModel(control);
			jTableFilm = new JTable(filmTableModel);
			jTableFilm.setName("filmTable");
			jTableFilm.addMouseListener(control);
			jTableFilm.setRowHeight(20);
			jTableFilm.getColumnModel().getColumn(0).setPreferredWidth(120);
			jTableFilm.getColumnModel().getColumn(0).setMaxWidth(120);
			TableColumn columnEventType = jTableFilm.getColumnModel().getColumn(0);			
			//columnEventType.setCellEditor(new DefaultCellEditor(this.getComboBoxEventType()));					
		}
		return jTableFilm;
	}
	public JTable getJTableSender() {
		if (jTableSender == null) {			
			senderTableModel = new MovieGuideSenderTableModel(control);
			jTableSender = new JTable(senderTableModel);
			jTableSender.setName("senderTable");
			jTableSender.addMouseListener(control);
			jTableSender.setRowHeight(20);
			jTableSender.getColumnModel().getColumn(0).setPreferredWidth(120);
			jTableSender.getColumnModel().getColumn(0).setMaxWidth(120);
			TableColumn columnEventType = jTableSender.getColumnModel().getColumn(0);			
			//columnEventType.setCellEditor(new DefaultCellEditor(this.getComboBoxEventType()));					
		}
		return jTableSender;
	}

}
