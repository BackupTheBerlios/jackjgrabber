/*
BOPids.java by Geist Alexander 

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
package model;

import java.util.ArrayList;

public class BOPids {

    String[] vPid; //erste Position Pid, 2. Position Beschreibung
    ArrayList aPids = new ArrayList(); //ArrayList von String[] Objekten, erste Position Pid, 2. Position Beschreibung
    String[] vtxtPid;
   
    /**
     * @return Returns the aPids.
     */
    public ArrayList getAPids() {
        return aPids;
    }
    /**
     * @param pids The aPids to set.
     */
    public void setAPids(ArrayList pids) {
        aPids = pids;
    }
    /**
     * @return Returns the vPid.
     */
    public String[] getVPid() {
        return vPid;
    }
    /**
     * @param pid The vPid to set.
     */
    public void setVPid(String[] pid) {
        vPid = pid;
    }
    public int getPidCount() {
        int count = 0;
        if (this.getVPid()!= null) {
            count++;
        }
        if (this.getVtxtPid()!= null) {
            count++;
        }
        return this.getAPids().size()+count;
    }
    public String getAPidNumber(int index) {
        String[] aPid = (String[])this.getAPids().get(index);
        return aPid[0];
    }
    
    public String getAPidDescription(int index) {
        String[] aPid = (String[])this.getAPids().get(index);
        return aPid[1];
    }
    
    /**
     * @return Returns the vtxtPid.
     */
    public String[] getVtxtPid() {
        return vtxtPid;
    }
    /**
     * @param vtxtPid The vtxtPid to set.
     */
    public void setVtxtPid(String[] vtxtPid) {
        this.vtxtPid = vtxtPid;
    }
}
