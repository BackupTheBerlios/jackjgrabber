package service;
/*
 * SerSettingsHandler.java by Geist Alexander
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation,
 * Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *  
 */
import java.beans.*;
import java.io.*;

import javax.swing.*;

import model.*;

import org.apache.log4j.*;

import control.*;

public class SerSettingsHandler {

	public static void saveAllSettings() throws Exception {

		XMLEncoder dec = new XMLEncoder(new FileOutputStream(new File(ControlMain.getSettingsFilename())));
		dec.writeObject(ControlMain.getSettings());
		dec.flush();
		dec.close();
	}

	public static void readSettings() {
		try {
			XMLDecoder dec = new XMLDecoder(new FileInputStream(ControlMain.getSettingsFilename()),null,new ExceptionListener() {
				public void exceptionThrown(Exception e) {
					Logger.getLogger("ControlMain").error(e.getMessage());
				}
			});

			BOSettings settings = (BOSettings) dec.readObject();
			settings.setSettingsChanged(false);
			ControlMain.setSettings(settings);
			Logger.getLogger("ControlMain").info("Settings found");

		} catch (Exception ex) {
			Logger.getLogger("ControlMain").info("old settings not longer supported");
			JOptionPane.showMessageDialog(null,ControlMain.getProperty("msg_oldSettingsNotSupported"));
		}
	}
}