package model;

import java.io.IOException;

import org.apache.log4j.Logger;

import service.SerErrorStreamReadThread;
import service.SerInputStreamReadThread;

/*
 * BOExternalProcess.java by Geist Alexander
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
public class BOExternalProcess {
	
	private String name;
	private String execString;
	private Process process;
	private boolean logging;

	public BOExternalProcess(String name, String execString, boolean logging) {
		this.setName(name);
		this.setExecString(execString);
		this.setLogging(logging);
	}
	
	public void start() {
		try {
			this.setProcess(Runtime.getRuntime().exec(this.getExecString()));
			new SerInputStreamReadThread(this.isLogging(), this.getProcess().getInputStream()).start();
			new SerErrorStreamReadThread(this.isLogging(), this.getProcess().getErrorStream()).start();
			Logger.getLogger("BOExternalProcess").info(this.getExecString());
		} catch (IOException e) {
			Logger.getLogger("BOExternalProcess").error(e.getMessage());
		}
	}

	/**
	 * @return Returns the execString.
	 */
	public String getExecString() {
		return execString;
	}
	/**
	 * @param execString The execString to set.
	 */
	public void setExecString(String execString) {
		this.execString = execString;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the process.
	 */
	public Process getProcess() {
		return process;
	}
	/**
	 * @param process The process to set.
	 */
	public void setProcess(Process process) {
		this.process = process;
	}
    /**
     * @return Returns the logging.
     */
    public boolean isLogging() {
        return logging;
    }
    /**
     * @param logging The logging to set.
     */
    public void setLogging(boolean logging) {
        this.logging = logging;
    }
}
