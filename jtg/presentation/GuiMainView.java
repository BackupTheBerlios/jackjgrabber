package presentation;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import org.apache.log4j.Logger;
import projectX.X;
import service.SerGUIUtils;
import service.SerXMLConverter;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.plaf.plastic.Plastic3DLookAndFeel;
import com.jgoodies.plaf.plastic.PlasticLookAndFeel;
import com.jgoodies.plaf.plastic.PlasticTheme;

import control.ControlMain;
import control.ControlMainView;
import control.ControlProgramTab;

/*
 * Created on 31.08.2004
 * Haupt-Gui, hier werden die einzelnen Tabs verwaltet
 */
public class GuiMainView extends JFrame {

	
	private GuiMainTabPane mainTabPane = null;
	private ControlMainView control;    
	
	public GuiMainView(ControlMainView ctrl) {
		super("FormLayout");
		addWindowListener (new WindowAdapter() { 
			public void windowClosing(WindowEvent e) { 
				try {
					SerXMLConverter.saveAllSettings();
					Logger.getLogger("ControlMainView").info("Settings saved");
				} catch (IOException e1) {
					Logger.getLogger("ControlMainView").error("Error while save Settings");
				}
				X.inisave();
				System.exit(0); 
			}
		});
		setLookAndFeel();
		control = ctrl;
		initialize();
		setTitle(ControlMain.version[0]+"/"+ControlMain.version[1]+
				" "+ControlMain.version[2]+" "+ControlMain.version[3]);
		pack();
		SerGUIUtils.center(this);
		setVisible(true);	
	}
	
	private void setLookAndFeel() {
		try {
			PlasticTheme inst = (PlasticTheme)(Class.forName("com.jgoodies.plaf.plastic.theme."+ControlMain.getSettings().getThemeLayout())).newInstance();
			PlasticLookAndFeel.setMyCurrentTheme(inst);
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
		} catch (Exception e) {e.printStackTrace();}
	}
	
	/**
	 * This method initializes this
	 */
	private void initialize() {
		this.getContentPane().add(this.getMainTabPane());
	}
	/**
	 * Haupt-TabPane. Neue Tabs werden hier angemeldet.
	 */    
	public GuiMainTabPane getMainTabPane() {
		if (mainTabPane == null) {
			mainTabPane = new GuiMainTabPane(this);
			mainTabPane.addChangeListener(control);
			
			mainTabPane.addTab("Programm", mainTabPane.getTabProgramm());
			mainTabPane.addTab("Timerliste", new JPanel());
			mainTabPane.addTab("Project-X", new JPanel());
			mainTabPane.addTab("Settings", new JPanel());
		}
		return mainTabPane;
	}
	    
	public GuiTabProgramm getTabProgramm() {
		return this.getMainTabPane().getTabProgramm();
	}
	    
	public JPanel getTabTimer() {
		return this.getMainTabPane().getTabTimer();
	}
	    
	public GuiTabSettings getTabSettings() {
		return this.getMainTabPane().getTabSettings();
	}
	    
	public GuiTabProjectX getTabProjectX() {
		return this.getMainTabPane().getTabProjectX();
	}
	/**
	 * @return Returns the control.
	 */
	public ControlMainView getControl() {
		return control;
	}
	/**
	 * @param control The control to set.
	 */
	public void setControl(ControlMainView control) {
		this.control = control;
	}
}
