package presentation;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import service.SerGUIUtils;

import com.jgoodies.plaf.plastic.Plastic3DLookAndFeel;
import com.jgoodies.plaf.plastic.PlasticLookAndFeel;
import com.jgoodies.plaf.plastic.PlasticTheme;

import control.ControlMain;
import control.ControlMainView;

/*
 * Created on 31.08.2004
 * Haupt-Gui, hier werden die einzelnen Tabs verwaltet
 */
public class GuiMainView extends JFrame {

	
	private GuiMainTabPane mainTabPane = null;
	private ControlMainView control;    
	
	public GuiMainView(ControlMainView ctrl) {
		super();
		setLookAndFeel();
		control = ctrl;
		initialize();
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
		this.setForeground(java.awt.SystemColor.windowText);
		this.setResizable(false);
		this.setSize(785, 555);
		this.setTitle(ControlMain.version[0]+"/"+ControlMain.version[1]+
				" "+ControlMain.version[2]+" "+ControlMain.version[3]);
		this.setContentPane(getMainTabPane());
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		
	}
	/**
	 * Haupt-TabPane. Neue Tabs werden hier angemeldet.
	 */    
	public GuiMainTabPane getMainTabPane() {
		if (mainTabPane == null) {
			mainTabPane = new GuiMainTabPane(this);
			mainTabPane.addChangeListener(control);
			mainTabPane.setBackground(new java.awt.Color(204,204,204));
			mainTabPane.setComponentOrientation(java.awt.ComponentOrientation.LEFT_TO_RIGHT);
			mainTabPane.setForeground(java.awt.Color.black);
			mainTabPane.setDoubleBuffered(true);
			
			mainTabPane.addTab("Programm", null, new JPanel(), null);
			mainTabPane.addTab("Timerliste", null, new JPanel(), null);
			mainTabPane.addTab("Project-X", null, new JPanel(), null);
			mainTabPane.addTab("Settings", null, new JPanel(), null);
		}
		return mainTabPane;
	}
	    
	public GuiTabProgramm getTabProgramm() {
		return this.getMainTabPane().getTabProgramm();
	}
	    
	public GuiTabTimer getTabTimer() {
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
