package model;

import org.apache.log4j.Logger;

import service.SerErrorStreamReadThread;
import service.SerInputStreamReadThread;
import service.SerProcessStopListener;

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
public class BOExternalProcess extends Thread {
	
	private String progName;
	private String execString;
	private String[] execStringArray;
	private Process process;
	private SerProcessStopListener listener;
	private boolean logging=false;
	private boolean logErrorAsInfo=false;
	private boolean closeWithoutPrompt=true;

	public BOExternalProcess(SerProcessStopListener listener, String name, String[] execString, boolean logging) {
	    this.setListener(listener);
		this.setProgName(name);
		this.setExecStringArray(execString);
		this.setLogging(logging);
	}
	
	public BOExternalProcess(String name, String execString, boolean logging) {
		this.setProgName(name);
		this.setExecString(execString);
		this.setLogging(logging);
	}
	
	public BOExternalProcess(String name, String[] execString, boolean logging, boolean logErrorAsInfo) {
	    this.setLogErrorAsInfo(logErrorAsInfo);
	    this.setLogging(logging);
		this.setProgName(name);
		this.setExecStringArray(execString);
	}
	
	public BOExternalProcess(String name, String[] execStringArray, boolean logging) {
		this.setProgName(name);
		this.setExecStringArray(execStringArray);
		this.setLogging(logging);
	}
	
	public void run() {
	    this.logExecString();
		try {
		    if (this.getExecString()==null) {
		    	this.setProcess(Runtime.getRuntime().exec(this.getExecStringArray()));
		    } else {
		        this.setProcess(Runtime.getRuntime().exec(this.getExecString()));
		    }
		    new SerInputStreamReadThread(this.isLogging(), this.getProcess().getInputStream()).start();
		    new SerErrorStreamReadThread(this.isLogging(), this.isLogErrorAsInfo(), this.getProcess().getErrorStream()).start();
		    
		    if (this.getListener()!=null) {
		        int exitValue = this.getProcess().waitFor();
		        this.getListener().processStopped(exitValue);
		    }
		} catch (Exception e) {
		    Logger.getLogger("BOExternalProcess").error(e.getMessage());
		} 
	}
	
	private void logExecString() {
	    if (this.getExecString()==null) {
    		String logString=new String();
    		for (int i = 0;i < execStringArray.length; i++) {
    		    logString += " "+ execStringArray[i];
    		}
    		Logger.getLogger("BOExternalProcess").info(logString.trim());
	    } else {
	        Logger.getLogger("BOExternalProcess").info(this.getExecString());
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
	 * @return Returns the execString.
	 */
	public String[] getExecStringArray() {
		return execStringArray;
	}
	/**
	 * @param execString The execString to set.
	 */
	public void setExecStringArray(String[] execStringArray) {
		this.execStringArray = execStringArray;
	}
	/**
	 * @return Returns the name.
	 */
	public String getProgName() {
		return progName;
	}
	/**
	 * @param name The name to set.
	 */
	public void setProgName(String name) {
		this.progName = name;
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
    /**
     * @return Returns the logErrorAsInfo.
     */
    public boolean isLogErrorAsInfo() {
        return logErrorAsInfo;
    }
    /**
     * @param logErrorAsInfo The logErrorAsInfo to set.
     */
    public void setLogErrorAsInfo(boolean logErrorAsInfo) {
        this.logErrorAsInfo = logErrorAsInfo;
    }
    /**
     * @return Returns the closeWithoutPrompt.
     */
    public boolean isCloseWithoutPrompt() {
        return closeWithoutPrompt;
    }
    /**
     * @param closeWithoutPrompt The closeWithoutPrompt to set.
     */
    public void setCloseWithoutPrompt(boolean closeWithoutPrompt) {
        this.closeWithoutPrompt = closeWithoutPrompt;
    }
    /**
     * @return Returns the listener.
     */
    public SerProcessStopListener getListener() {
        return listener;
    }
    /**
     * @param listener The listener to set.
     */
    public void setListener(SerProcessStopListener listener) {
        this.listener = listener;
    }
}
