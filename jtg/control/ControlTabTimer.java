package control;
/*
ControlTabTimer.java by Geist Alexander 

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
import java.io.IOException;
import java.util.ArrayList;

import model.BOTimer;
import model.BOTimerList;
/*
 * Superklasse fuer die Timer-Controls
 */
public abstract class ControlTabTimer implements Runnable {

    public abstract void reReadTimerList();
	public abstract ArrayList getSenderList();
	public abstract String[] getRepeatOptions();
	public abstract String[] getTimerType();
	public abstract String convertShortEventRepeat(String shortString);
	public abstract String convertLongEventRepeat(String longString);
	public abstract BOTimerList getTimerList();
	public abstract void addRecordTimer (BOTimer timer);
	public abstract void writeTimer (BOTimer timer) throws IOException;
	
}
