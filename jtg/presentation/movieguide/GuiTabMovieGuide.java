package presentation.movieguide;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javax.swing.JComboBox;
import javax.swing.JTable;

import javax.swing.JTextField;
import javax.swing.JProgressBar; 
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.BOMovieGuide;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import control.ControlMain;
import control.ControlMovieGuideTab;

public class GuiTabMovieGuide extends JPanel {

	private ControlMovieGuideTab control;	
	
	private JButton jButtonNeuEinlesen= null;
	private JButton jButtonDownload = null;
	private JButton jButtonSuche = null;
	private JButton jButtonDatumAll = null;
	private JButton jButtonMovieGuideFileChooser = null;
	private JButton jButtonToTimer = null;
	
	private JTextArea taBeschreibung;
	private JTextArea taDarsteller;
	private JTextArea taEpisode;
	private JTextArea taLand;
	private JTextArea taAudioVideo;
	private JTextArea taGenre;
	
	private JComboBox comboBoxGenre = null;
	private JComboBox comboBoxDatum = null;
	private JComboBox comboBoxSender = null;
	private JComboBox comboBoxSuchenNach = null;
	
	private JTable jTableFilm = null;	
	private JTable jTableTimer = null;
	
	private JScrollPane jScrollPaneChannel = null;
	private JScrollPane jScrollPaneTimer = null;
	private JScrollPane jScrollPaneInfo = null;
	private JScrollPane jScrollPaneDarsteller = null;
	private JScrollPane jScrollPaneEpisode = null;
	private JScrollPane jScrollPaneLand = null;
	private JScrollPane jScrollPaneAudioVideo;
	private JScrollPane jPaneTitel= null;
	private JScrollPane jScrollPaneGenre= null;
	
	private JTextField tfSuche;
	
	private JCheckBox  jCheckBoxAbAktuell;
	
	private JPanel jPanelSuche = null;
	private JPanel jPanelSucheErw = null;
	private JPanel jPanelChannel = null;
	private JPanel jPanelInfo = null;
	private JPanel jPanelDownload = null;
	private JPanel jPanelDatum= null;
	private JPanel jPanelProgressBar= null;	
	
	private JProgressBar jProgressBarDownload = null;
	private JLabel jLabelMovieGuide = null;
	
	public GuiMovieGuideTimerTableModel mgTimerTableModel;
	public GuiMovieGuideTimerTableSorter mgTimerTableSorter = null;
	
	public GuiMovieGuideFilmTableModel filmTableModel;
	public GuiMovieGuideFilmTableSorter mgFilmTableSorter = null;	
			
	public GuiTabMovieGuide(ControlMovieGuideTab ctrl) {
		super();
		this.setControl(ctrl);
		initialize();
	}

	private  void initialize() {
		FormLayout layout = new FormLayout(	
			      "320px:grow,10, 400px:grow",  				// columns
			      "pref, f:200px:grow, 10, pref, pref, pref");	// rows
		PanelBuilder builder = new PanelBuilder(this, layout);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();
		builder.addSeparator(ControlMain.getProperty("datum"),  cc.xywh	(1, 1, 1, 1));
		builder.add(this.getJPanelChannels(),  			cc.xywh	(1, 2, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
		
		builder.addSeparator(ControlMain.getProperty("prog_info"),	cc.xywh	(3, 1, 1, 1));						
		builder.add(this.getJPanelInfo(),				cc.xywh	(3, 2, 1, 1));
		
		builder.addSeparator(ControlMain.getProperty("suche"),	cc.xywh	(1, 4, 1, 1, CellConstraints.FILL, CellConstraints.FILL));		
		builder.add(this.getJPanelSuche(),  			cc.xywh	(1, 5, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
		builder.add(this.getJPanelSucheErw(),  			cc.xywh	(1, 6, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
		
		builder.addSeparator(ControlMain.getProperty("akt_mg"),		cc.xywh	(3, 4, 1, 1));
		builder.add(this.getJPanelDownload(),  			cc.xywh	(3, 5, 1, 1, CellConstraints.FILL, CellConstraints.FILL));			
			
		builder.add(this.getJPanelProgressBar(), 	 	cc.xywh	(3, 6, 1, 1, CellConstraints.FILL, CellConstraints.FILL));		
	}
	 
	private JPanel getJPanelChannels() {
		if (jPanelChannel == null) {
			jPanelChannel = new JPanel();
			FormLayout layout = new FormLayout(
				      "320px:grow",									//column 
				      "pref, pref, 120px:grow, pref");		//rows
			PanelBuilder builder = new PanelBuilder(jPanelChannel, layout);
			CellConstraints cc = new CellConstraints();			
			builder.add(this.getJPanelDatum(),		  		cc.xyw	(1, 1, 1, CellConstraints.FILL, CellConstraints.FILL));
			builder.addSeparator(ControlMain.getProperty("titel"),	cc.xyw	(1, 2, 1));
			builder.add(this.getJScrollPaneChannels(), 			cc.xyw	(1, 3, 1, CellConstraints.FILL, CellConstraints.FILL));			
			builder.add(this.getJButtonSelectedToTimer(), 	    cc.xyw	(1, 4, 1, CellConstraints.FILL, CellConstraints.FILL));								
		}
		return jPanelChannel;
	}
	
	private JPanel getJPanelInfo() {
		if (jPanelInfo == null) {
			jPanelInfo = new JPanel();
			FormLayout layout = new FormLayout(
				      "400px:grow",									//column 				
						"f:115:grow, f:100px:grow, f:42px:grow, f:pref:grow, f:pref:grow, f:pref:grow, f:pref:grow");		//rows
					
			PanelBuilder builder = new PanelBuilder(jPanelInfo, layout);
			CellConstraints cc = new CellConstraints();														
			builder.add(this.getJScrollPaneTimer(),			cc.xy	(1, 1));			
			builder.add(this.getJScrollPaneInfo(), 			cc.xy	(1, 2));	
			builder.add(this.getJScrollPaneDarsteller(), 	cc.xy	(1, 3));
			builder.add(this.getJScrollPaneEpisode(), 		cc.xy	(1, 4));
			builder.add(this.getJScrollPaneLand(), 			cc.xy	(1, 5));				
			builder.add(this.getJScrollPaneGenre(),			cc.xy	(1, 6));
			builder.add(this.getJScrollPaneAudioVideo(),	cc.xy	(1, 7));
		}
		return jPanelInfo;
	}
	
	private JPanel getJPanelSuche() {
		if (jPanelSuche == null) {
			jPanelSuche = new JPanel();
			FormLayout layout = new FormLayout(
				      "230px:grow,90px:grow",	 		//columna 
				      "pref");	//rows					
			PanelBuilder builder = new PanelBuilder(jPanelSuche, layout);
			CellConstraints cc = new CellConstraints();					
			builder.add(this.getTfSuche(),			 cc.xyw	(1, 1, 1));	
			builder.add(this.getJButtonSuche(),		 cc.xyw	(2, 1, 1));			
		}
		return jPanelSuche;
	}
	private JPanel getJPanelSucheErw() {
		if (jPanelSucheErw == null) {
			jPanelSucheErw = new JPanel();
			FormLayout layout = new FormLayout(
				      "150px:grow,10,170px:grow",	 		//columna 
				      "pref,pref,pref");	//rows					
			PanelBuilder builder = new PanelBuilder(jPanelSucheErw, layout);
			CellConstraints cc = new CellConstraints();										
			builder.add(this.getComboBoxSucheNach(), 	cc.xyw	(1, 1, 1));
			//builder.add(this.getCheckBoxAbAktuell(), 	cc.xyw	(3, 1, 1));
			builder.addSeparator(ControlMain.getProperty("suche_genre"), 	cc.xyw	(1, 2, 1));
			builder.add(this.getComboBoxGenre(), 	 	cc.xyw	(1, 3, 1));
			builder.addSeparator(ControlMain.getProperty("suche_sender"), 	cc.xyw	(3, 2, 1));
			builder.add(this.getComboBoxSender(), 	 	cc.xyw	(3, 3, 1));
		}
		return jPanelSucheErw;
	}
	
	private JPanel getJPanelProgressBar() {
		if (jPanelProgressBar == null) {
			jPanelProgressBar = new JPanel();
			FormLayout layout = new FormLayout(
				      "320px:grow",	 		//columna 
				      "20,pref,5,pref");	//rows					
			PanelBuilder builder = new PanelBuilder(jPanelProgressBar, layout);
			CellConstraints cc = new CellConstraints();	
			builder.addSeparator(ControlMain.getProperty("fortschritt"), cc.xyw	(1, 2, 1));			
			builder.add(this.getJProgressBarDownload(),	 cc.xyw	(1, 4, 1));												
		}
		return jPanelProgressBar;
	}
	private JPanel getJPanelDownload() {
		if (jPanelDownload == null) {
			jPanelDownload = new JPanel();
			FormLayout layout = new FormLayout(
				      "190px:grow,100px:grow,pref",	 		//columna 
				      "pref");	//rows					
			PanelBuilder builder = new PanelBuilder(jPanelDownload, layout);
			CellConstraints cc = new CellConstraints();														
			builder.add(this.getJButtonDownload(),			 cc.xyw	(1, 1, 1));	
			builder.add(this.getJButtonMovieGuideFileChooser(),	 cc.xyw	(2, 1, 1));			
			builder.add(this.getJButtonNeuEinlesen(),	 cc.xyw	(3, 1, 1));						
		}
		return jPanelDownload;
	}
	private JLabel getJLabelMovieGuide(){
		if (jLabelMovieGuide == null) {
			jLabelMovieGuide = new JLabel("MovieGuide f�r: Monat");
		}
		return jLabelMovieGuide;
	}
	private JPanel getJPanelDatum() {
		if (jPanelDatum == null) {
			jPanelDatum = new JPanel();
			FormLayout layout = new FormLayout(
				      "240px:grow,80px:grow",	 		//columna 
				      "pref");	//rows					
			PanelBuilder builder = new PanelBuilder(jPanelDatum, layout);
			CellConstraints cc = new CellConstraints();														
			builder.add(this.getComboBoxDatum(),	 cc.xyw	(1, 1, 1));	
			builder.add(this.getJButtonDatumAll(),	 cc.xyw	(2, 1, 1));									
		}
		return jPanelDatum;
	}

	public JProgressBar getJProgressBarDownload(){
		if (jProgressBarDownload == null){
			jProgressBarDownload = new JProgressBar(JProgressBar.HORIZONTAL, 0, 0);
			jProgressBarDownload.setStringPainted(true);			
		}
		return jProgressBarDownload;
	}
		
	public JButton getJButtonMovieGuideFileChooser() {
		if (jButtonMovieGuideFileChooser == null) {		
			jButtonMovieGuideFileChooser = new JButton();
			ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("ico/Open16.gif"));
			jButtonMovieGuideFileChooser.setIcon(icon);			
			jButtonMovieGuideFileChooser.setText(ControlMain.getProperty("open"));
			jButtonMovieGuideFileChooser.setToolTipText(ControlMain.getProperty("open_mg_local"));
			jButtonMovieGuideFileChooser.setActionCommand("movieGuidePath");
			jButtonMovieGuideFileChooser.addActionListener(this.getControl());
		}
		return jButtonMovieGuideFileChooser;
	}
	
	public JButton getJButtonDatumAll() {
		if (jButtonDatumAll == null) {
			jButtonDatumAll = new JButton();
			jButtonDatumAll.setText(ControlMain.getProperty("button_all"));
			jButtonDatumAll.setActionCommand("allDates");
			jButtonDatumAll.setToolTipText(ControlMain.getProperty("button_all_txt"));
			jButtonDatumAll.addActionListener(this.getControl());
		}
		return jButtonDatumAll;
	}
	public JButton getJButtonSelectedToTimer() {
		if (jButtonToTimer == null) {
			jButtonToTimer = new JButton();			
			ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("ico/attach.png"));
			jButtonToTimer.setIcon(icon);	
			jButtonToTimer.setActionCommand("select2Timer");
			jButtonToTimer.setText(ControlMain.getProperty("button_toTimer"));
			jButtonToTimer.setToolTipText(ControlMain.getProperty("buttontt_toTimer"));
			jButtonToTimer.addActionListener(this.getControl());
		}
		return jButtonToTimer;
	}
	public JButton getJButtonDownload() {
		if (jButtonDownload == null) {
			jButtonDownload = new JButton();
			ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("ico/new.png"));
			jButtonDownload.setIcon(icon);						
			jButtonDownload.setActionCommand("download");
			jButtonDownload.setText(ControlMain.getProperty("button_pmg_download"));			
			jButtonDownload.setToolTipText(ControlMain.getProperty("button_pmg_download_txt"));
			jButtonDownload.addActionListener(this.getControl());
		}
		return jButtonDownload;
	}
	public JButton getJButtonNeuEinlesen() {
		if (jButtonNeuEinlesen == null) {
			jButtonNeuEinlesen = new JButton();
			ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("ico/Refresh16.gif"));
			jButtonNeuEinlesen.setIcon(icon);		
			jButtonNeuEinlesen.setText(ControlMain.getProperty("button_new_read"));
			jButtonNeuEinlesen.setActionCommand("neuEinlesen");
			jButtonNeuEinlesen.setToolTipText(ControlMain.getProperty("button_new_read_txt"));
			jButtonNeuEinlesen.addActionListener(this.getControl());		
			jButtonNeuEinlesen.setEnabled(false);
		}
		return jButtonNeuEinlesen;
	}
	public JButton getJButtonSuche() {
		if (jButtonSuche == null) {
			jButtonSuche = new JButton();
			//ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("ico/suche.png"));
			//jButtonSuche.setIcon(icon);						
			jButtonSuche.setText(ControlMain.getProperty("button_new_suche"));
			jButtonSuche.setActionCommand("suchen");
			jButtonSuche.setToolTipText(ControlMain.getProperty("button_new_suche_txt"));
			jButtonSuche.addActionListener(this.getControl());
			jButtonSuche.addKeyListener(this.getControl());
		}
		return jButtonSuche;
	}
	
	public JCheckBox getCheckBoxAbAktuell() {
		if (jCheckBoxAbAktuell == null) {
			jCheckBoxAbAktuell = new JCheckBox("Erst ab heutigen Datum");
			jCheckBoxAbAktuell.setName("showAbHeute");
			jCheckBoxAbAktuell.setSelected(true);
			jCheckBoxAbAktuell.addItemListener(control);
			jCheckBoxAbAktuell.addMouseListener(control);
		}
		return jCheckBoxAbAktuell;
	}
	
	private JScrollPane getJScrollPaneChannels() {
		if (jScrollPaneChannel == null) {
			jScrollPaneChannel = new JScrollPane();
			jScrollPaneChannel.setViewportView(getJTableFilm());
		}
		return jScrollPaneChannel;
	}
	
	private JScrollPane getJScrollPaneTimer() {
		if (jScrollPaneTimer == null) {
			jScrollPaneTimer = new JScrollPane();
			jScrollPaneTimer.setViewportView(getJTableTimer());
		}
		return jScrollPaneTimer;
	}
	
	private JScrollPane getJScrollPaneInfo() {
		if (jScrollPaneInfo == null) {
			jScrollPaneInfo = new JScrollPane();
			jScrollPaneInfo.setViewportView(getTaBeschreibung());
		}
		return jScrollPaneInfo;
	}
	private JScrollPane getJScrollPaneDarsteller() {
		if (jScrollPaneDarsteller == null) {
			jScrollPaneDarsteller = new JScrollPane();
			jScrollPaneDarsteller.setViewportView(getTaDarsteller());
		}
		return jScrollPaneDarsteller;
	}
	private JScrollPane getJScrollPaneEpisode() {
		if (jScrollPaneEpisode == null) {
			jScrollPaneEpisode = new JScrollPane();
			jScrollPaneEpisode.setViewportView(getTaEpisode());
		}
		return jScrollPaneEpisode;
	}
	private JScrollPane getJScrollPaneLand() {
		if (jScrollPaneLand == null) {
			jScrollPaneLand = new JScrollPane();
			jScrollPaneLand.setViewportView(getTaLand());
		}
		return jScrollPaneLand;
	}
	
	private JScrollPane getJScrollPaneAudioVideo() {
		if (jScrollPaneAudioVideo == null) {
			jScrollPaneAudioVideo = new JScrollPane();
			jScrollPaneAudioVideo.setViewportView(getTaAudioVideo());
		}
		return jScrollPaneAudioVideo;
	}	
	
	private JScrollPane getJScrollPaneGenre() {
		if (jScrollPaneGenre == null) {
			jScrollPaneGenre = new JScrollPane();
			jScrollPaneGenre.setViewportView(getTaGenre());
		}
		return jScrollPaneGenre;
	}	
	
	public GuiMovieGuideTimerTableModel getGuiMovieGuideTimerTableModel() {
		return mgTimerTableModel;
	}
	
	public JTable getJTableTimer() {
		if (jTableTimer == null) {
			mgTimerTableModel = new GuiMovieGuideTimerTableModel(control);
			mgTimerTableSorter = new GuiMovieGuideTimerTableSorter(mgTimerTableModel);
			jTableTimer = new JTable(mgTimerTableSorter);
			mgTimerTableSorter.setTableHeader(jTableTimer.getTableHeader());									 
			jTableTimer.getColumnModel().getColumn(0).setMaxWidth(200);
			jTableTimer.getColumnModel().getColumn(1).setMaxWidth(45);
			jTableTimer.getColumnModel().getColumn(2).setMaxWidth(45);
			jTableTimer.getColumnModel().getColumn(3).setMaxWidth(45);		
			jTableTimer.getColumnModel().getColumn(4).setMaxWidth(165);			
			jTableTimer.addMouseListener(control);
			jTableTimer.setName("timerTable");
			jTableTimer.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (!e.getValueIsAdjusting()) {
						control.timerTableChanged(jTableTimer);
					}
				}
			});
		}
		return jTableTimer;
	}
	public JComboBox getComboBoxSucheNach(){
		if (comboBoxSuchenNach == null) {						
			comboBoxSuchenNach = new JComboBox(new BOMovieGuide().getSucheList().toArray());	
			comboBoxSuchenNach.addItemListener(control);
			comboBoxSuchenNach.setName("jComboBoxSucheNach");
		}
		return comboBoxSuchenNach;
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

	public JTextField getTfSuche() {
		if (tfSuche == null) {
			tfSuche = new JTextField();
			tfSuche.setEditable(true);			
			tfSuche.setAutoscrolls(true);
		}
		return tfSuche;
	}
	
	public JTextArea getTaGenre() {
		if (taGenre == null) {
			taGenre = new JTextArea();
			taGenre.setEditable(false);
			taGenre.setLineWrap(true);
			taGenre.setWrapStyleWord(true);
			taGenre.setAutoscrolls(true);	
			taGenre.setText(ControlMain.getProperty("txt_genre"));			
		}
		return taGenre;
	}
	
	public JTextArea getTaBeschreibung() {
		if (taBeschreibung == null) {
			taBeschreibung = new JTextArea();
			taBeschreibung.setEditable(false);
			taBeschreibung.setLineWrap(true);
			taBeschreibung.setWrapStyleWord(true);
			taBeschreibung.setAutoscrolls(true);		
			taBeschreibung.setText(ControlMain.getProperty("txt_inhalt"));
		}
		return taBeschreibung;
	}
	public JTextArea getTaLand() {
		if (taLand == null) {
			taLand = new JTextArea();
			taLand.setEditable(false);
			taLand.setLineWrap(true);
			taLand.setWrapStyleWord(true);
			taLand.setAutoscrolls(true);
			taLand.setText(ControlMain.getProperty("txt_prod"));			
		}
		return taLand;
	}
	public JTextArea getTaAudioVideo() {
		if (taAudioVideo == null) {
			taAudioVideo = new JTextArea();
			taAudioVideo.setEditable(false);
			taAudioVideo.setLineWrap(true);
			taAudioVideo.setWrapStyleWord(true);
			taAudioVideo.setAutoscrolls(true);				
			taAudioVideo.setText(ControlMain.getProperty("txt_audio")+" / "+ControlMain.getProperty("txt_video"));			
		}
		return taAudioVideo;
	}
	public JTextArea getTaDarsteller() {
		if (taDarsteller == null) {
			taDarsteller = new JTextArea();
			taDarsteller.setEditable(false);
			taDarsteller.setLineWrap(true);
			taDarsteller.setWrapStyleWord(true);
			taDarsteller.setAutoscrolls(true);				
			taDarsteller.setText(ControlMain.getProperty("txt_darsteller"));			
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
			taEpisode.setText(ControlMain.getProperty("txt_episode"));			
		}
		return taEpisode;
	}

	public JComboBox getComboBoxGenre(){
		if (comboBoxGenre == null) {					
			comboBoxGenre = new JComboBox();
			comboBoxGenre.setModel(new GuiMovieGuideGenreComboModel(this.getControl()));
			comboBoxGenre.addItemListener(control);
			comboBoxGenre.addActionListener(this.getControl());
			comboBoxGenre.setActionCommand("clickONGenreComboBox");
			comboBoxGenre.setName("jComboBoxGenre");			
		}
		return comboBoxGenre;
	}
	public JComboBox getComboBoxSender(){
		if (comboBoxSender == null) {						
			comboBoxSender = new JComboBox();
			comboBoxSender.setModel(new GuiMovieGuideSenderComboModel(this.getControl()));
			comboBoxSender.addItemListener(control);
			comboBoxSender.addActionListener(this.getControl());
			comboBoxSender.setActionCommand("clickONSenderComboBox");
			comboBoxSender.setName("jComboBoxSender");
		}
		return comboBoxSender;
	}
	public JComboBox getComboBoxDatum() {
		if (comboBoxDatum == null) {									
			comboBoxDatum = new JComboBox();
			comboBoxDatum.setModel(new GuiMovieGuideDatumComboModel(this.getControl()));
			comboBoxDatum.addItemListener(this.getControl());
			comboBoxDatum.addActionListener(this.getControl());
			comboBoxDatum.setActionCommand("clickONDatumComboBox");
			comboBoxDatum.setName("jComboBoxDatum");			
		}
		return comboBoxDatum;
	}
	public GuiMovieGuideFilmTableModel getMovieGuideFilmTableModel() {
		return filmTableModel;
	}
	public JTable getJTableFilm() {
		if (jTableFilm == null) {				
			filmTableModel = new GuiMovieGuideFilmTableModel(control);			
			mgFilmTableSorter = new GuiMovieGuideFilmTableSorter(filmTableModel);				 
			jTableFilm = new JTable(mgFilmTableSorter);
			//jTableFilm.getColumnModel().getColumn(0).setCellRenderer( new GuiMovieGuideColorCellRenderer(control)); //sinnvoll ?
			//jTableFilm.getColumnModel().getColumn(0).setCellEditor(new GuiMovieGuideFilmTableCellEditor());
			mgFilmTableSorter.setTableHeader(jTableFilm.getTableHeader());
			jTableFilm.setName("filmTable");
			jTableFilm.addMouseListener(control);		
			jTableFilm.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (!e.getValueIsAdjusting()) {
						control.filmTableChanged(jTableFilm);
					}
				}
			});
		}
		return jTableFilm;
	}	
}