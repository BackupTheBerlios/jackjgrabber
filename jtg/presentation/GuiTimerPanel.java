package presentation;

import javax.swing.JPanel;

/**
 * @author Alexander Geist
 *
 */
public abstract class GuiTimerPanel {
	
	public static JPanel getTimerPanel(String boxName) {
		if (boxName.equals("Enigma")) {
			return new JPanel();
		}
		//return new GuiTimerPanelNeutrino()
		return new JPanel();
	
	}
}
