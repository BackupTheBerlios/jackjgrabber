package control;

import presentation.GuiMainView;


/**
 * @author Treito
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ControlDefaultTimerTab extends ControlTab {
	
	GuiMainView mainView;

	
	public ControlDefaultTimerTab(GuiMainView view) {
		this.setMainView(view);
	}
	
	public void initialize() {}

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
