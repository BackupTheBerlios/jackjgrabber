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

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javax.swing.JProgressBar; 

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import control.ControlMovieGuideTab;
import control.ControlTab;



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
						  "150:grow, 8dlu, pref:grow, 8dlu, pref:grow, 8dlu,  pref:grow",  		// columns 
						  "pref, 4dlu, f:50:grow, 4dlu, pref, 4dlu, f:330:grow"); 			// rows
				PanelBuilder builder = new PanelBuilder(this, layout);
				builder.setDefaultDialogBorder();
				CellConstraints cc = new CellConstraints();	
				builder.addSeparator("MovieGuide", cc.xyw  (1, 1, 3));
				builder.add(this.getJPanelButtonsGui(), cc.xywh (1, 6, 1, 2, CellConstraints.CENTER, CellConstraints.BOTTOM));				
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
				      "Fill:pref",	 		//columna
		      "pref, pref, pref, ");	//rows
			PanelBuilder builder = new PanelBuilder(jPanelButtonsGui, layout);
			CellConstraints cc = new CellConstraints();	
			builder.add(this.getJButtonSelect2Timer(), cc.xy(1, 1));
			builder.add(this.getJButtonNeuEinlesen(), cc.xy(1, 2));
			builder.add(this.getJButtonDownload(), cc.xy(1, 3));
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
	
	public JButton getJButtonDownload() {
		if (jButtonDownload == null) {
			jButtonDownload = new JButton("PMG-Download");
			jButtonDownload.setActionCommand("pmgDownload");
			jButtonDownload.setPreferredSize(new Dimension(150,25));
			jButtonDownload.addActionListener(control);
		}
		return jButtonDownload;
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
	public JComboBox getComboBoxGenre() {
		if (comboBoxGenre == null) {
			//comboBoxGenre = new JComboBox(new GuiTimerSenderComboModel(control));
			comboBoxGenre = new JComboBox();
		}
		return comboBoxGenre;
	}
	public JComboBox getComboBoxDatum() {
		if (comboBoxDatum == null) {
			//comboBoxDatum = new JComboBox(new GuiTimerSenderComboModel(control));
			comboBoxDatum = new JComboBox();
		}
		return comboBoxDatum;
	}
	public JTable getJTableFilm() {
		if (jTableFilm == null) {
			//systemTimerTableModel = new GuiNeutrinoSystemTimerTableModel(control);
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
	public JTable getJTableTimer() {
		if (jTableTimer == null) {			
			timerTableModel = new MovieGuideTimerTableModel(control);
			jTableTimer = new JTable(timerTableModel);
			jTableTimer.setName("timerTable");
			jTableTimer.addMouseListener(control);			
			jTableTimer.setRowHeight(20);
			jTableTimer.getColumnModel().getColumn(0).setPreferredWidth(120);
			jTableTimer.getColumnModel().getColumn(0).setMaxWidth(120);
			TableColumn columnEventType = jTableTimer.getColumnModel().getColumn(0);			
			//columnEventType.setCellEditor(new DefaultCellEditor(this.getComboBoxEventType()));					
		}
		return jTableTimer;
	}
}
