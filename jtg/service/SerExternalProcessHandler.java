package service;

import java.util.ArrayList;

import model.BOExternalProcess;

/*
 * SerExternalProcessHandler.java by Geist Alexander
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 675 Mass
 * Ave, Cambridge, MA 02139, USA.
 *  
 */
public class SerExternalProcessHandler {
	
	private static ArrayList processList;

	public SerExternalProcessHandler() {
		
	}
	
	public static void startProcess(String name, String execString) {
		BOExternalProcess process = new BOExternalProcess(name, execString);
		getProcessList().add(process);
		process.start();
	}

	public void closeAll() {
		ArrayList list = getProcessList();
		if (list.size()>0) {
			for (int i=0; i<list.size(); i++) {
				BOExternalProcess boProc = (BOExternalProcess)list.get(i);
				Process proc = boProc.getProcess();
				if (proc.exitValue()>0) {
					proc.destroy();
				}
			}
		}
	}
	/**
	 * @return Returns the processList.
	 */
	private static ArrayList getProcessList() {
		if (processList==null) {
			processList=new ArrayList();
		}
		return processList;
	}
	/**
	 * @param processList The processList to set.
	 */
	private static void setProcessList(ArrayList processList) {
		SerExternalProcessHandler.processList = processList;
	}
}
