package control;

import java.io.IOException;
import java.util.ArrayList;

import presentation.GuiMainView;
import service.SerAlertDialog;

/**
 * @author Treito
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ControlTimerTab extends ControlTab {
	
	GuiMainView mainView;
	ArrayList timerList;
	
	public ControlTimerTab(GuiMainView view) {
		this.setMainView(view);
	}
	
	public void initialize() {
		try {
			this.setTimerList(ControlMain.getBoxAccess().getTimer());
			this.getMainView().getTabTimer().getTimerTableModel().fireTableDataChanged();
		} catch (IOException e) {
			SerAlertDialog.alertConnectionLost("ControlTimerTab", this.getMainView());
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

	/**
	 * @return Returns the timerList.
	 */
	public ArrayList getTimerList() {
		return timerList;
	}
	/**
	 * @param timerList The timerList to set.
	 */
	public void setTimerList(ArrayList timerList) {
		this.timerList = timerList;
	}
}
