/*
 * Created on 13.09.2004
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package control;

import presentation.GuiMainView;

/**
 * @author Alexander Geist
 *
 * Superklasse fuer die Controlklassen
 */
public abstract class ControlTab {

	public abstract GuiMainView getMainView();
	public abstract void setMainView(GuiMainView view);
	/**
	 * Mit dieser Methode wird der zugehörige TAB mit Daten versorgt 
	 */
	public abstract void initialize();
}
