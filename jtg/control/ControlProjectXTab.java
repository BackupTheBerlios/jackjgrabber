/*
 * Created on 17.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package control;

import presentation.GuiMainView;
import projectX.X;

/**
 * @author Treito
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ControlProjectXTab extends ControlTab {
	
	GuiMainView mainView;
	
	public ControlProjectXTab(GuiMainView view) {
		this.setMainView(view);
	}
	
	/**
	 * Start ProjectX 
	 */
	public void initialize() {
		this.getMainView().getTabProjectX().add(X.start(this.getMainView().getTabProjectX()));
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
