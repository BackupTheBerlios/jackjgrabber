/*
SerErrorStreamReadThread.java by Geist Alexander 

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.  

*/ 
package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class SerErrorStreamReadThread extends Thread {
    BufferedReader input;
    boolean logging=true;
    boolean logAsInfo=false;
    
    public SerErrorStreamReadThread(boolean log, boolean logasinfo, InputStream in) {
        logAsInfo=logasinfo;
        logging=log;
        input = new BufferedReader(new InputStreamReader(in));
    }
    
    public void run() {
        String line;
        try {
            while((line=input.readLine())!=null) {
                if (logging && !logAsInfo) {
                    Logger.getLogger("SerErrorStreamReadThread").error(line);
                }
                if (logging && logAsInfo) {
                    Logger.getLogger("SerErrorStreamReadThread").info(line);
                }
                line=null;
            }
        } catch (IOException e) {
            Logger.getLogger("SerErrorStreamReadThread").error(e.getMessage());
        }
    }
}
