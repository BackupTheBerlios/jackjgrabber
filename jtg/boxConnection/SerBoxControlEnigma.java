/*
 * Created on 08.08.2004
 *
 */
package boxConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import control.ControlMain;
import model.BOBouquet;
import model.BOEpg;
import model.BOEpgDetails;
import model.BOPids;
import model.BOSender;
import model.BOTimer;
import service.SerFormatter;

/*
SerBoxControlEnigma.java by Geist Alexander, Treito

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

public class SerBoxControlEnigma extends SerBoxControl {
	
	public String getName() {
		return "Enigma";
	}
	
	public String getChanIdOfRunningSender() throws IOException {
		BufferedReader input = getConnection("/cgi-bin/streaminfo");
		String line;
		int startpos, endpos;
		while((line=input.readLine())!=null) {
		    startpos=line.indexOf("<td>Service reference");
		    if (startpos>0) {
		        startpos=line.indexOf("<td>",startpos+1);
		        endpos=line.indexOf("</td>", startpos+1);
		        return line.substring(startpos+4, endpos);
		    }
		}
		return "ok";
	}
	
	public BufferedReader getConnection(String request) throws IOException {
		return new BufferedReader(new InputStreamReader(new URL("http://"+ControlMain.getBoxIpOfActiveBox()+request).openStream()));
	}
		
	public BOPids getPids(boolean tvMode) throws IOException {
	    BOPids pids = new BOPids();
		String line;
		BufferedReader input = getConnection("/control/zapto?getpids");
		if (tvMode) {
		    String[] vPid = new String[1];
		    line=input.readLine();
		    System.out.println(line);
		    if (line.length() > 5) {
		        Logger.getLogger("SerBoxControlEnigma").info("Fehler beim Zappen, bitte erneut versuchen!");
		        return pids;
		    }
		    vPid[0]=Integer.toHexString(Integer.parseInt(line));
		    pids.setVPid(vPid);
		} 
		while((line=input.readLine())!=null) {
		    String[] aPid = new String[1];
		    if (line.length() > 5) {
		        Logger.getLogger("SerBoxControlEnigma").info("Fehler beim Zappen, bitte erneut versuchen!");
		        return pids;
		    }
		    aPid[0]=Integer.toHexString(Integer.parseInt(line));
			pids.getAPids().add(aPid);
		}

		return pids;
	}	 
	
	public ArrayList getBouquetList() throws IOException {
		ArrayList bouquetList = new ArrayList();
		String line;
		String name;
		int bouquetStart=0;
		int bouquetEnd=0;
		int bouquetNr=0;
		BufferedReader in = getConnection("/body");
		while ((line = in.readLine()) != null) {
			if (line.indexOf("bouquets = new Array")>0) {
				line = in.readLine();
				bouquetStart=0;
				bouquetEnd=0;
   		 		while ((bouquetStart=line.indexOf("\"",bouquetEnd+1))>0)
   		 		{
   		 			bouquetEnd=line.indexOf("\"",bouquetStart+1);
   		 			name=line.substring(bouquetStart+1, bouquetEnd);
   		 			bouquetList.add(new BOBouquet(""+bouquetNr, name));
   		 			bouquetNr++;
   		 		}
   			}
		}
   		return bouquetList;
	}
	
	public ArrayList getAllSender() throws IOException {
	    Logger.getLogger("SerBoxControlEnigma").info("Lese Sender");
	    ArrayList senderList = new ArrayList();
		String line;
		String line2="";
		int channelStart=0;
		int channelEnd=0;
		int channelLength=0;
		ArrayList readEChannels= new ArrayList();
		ArrayList readEChannelsRef= new ArrayList();
		BufferedReader in = getConnection("/body");
		Logger.getLogger("SerBoxControlEnigma").info("Sender geladen");
		while ((line = in.readLine()) != null) {
			if (line.indexOf("channels[0]")>0) {
   		 		channelStart=0;
   		 		channelEnd=0;
   		 		while ((channelStart=line.indexOf("\"",channelEnd+1))>0) {
   		 			channelLength=line.indexOf(" - ",channelStart+1);
   		 			if ((channelLength<=0)|(channelLength>line.indexOf("\"",channelStart+1)))
   		 				channelLength=line.indexOf("\"",channelStart+1);
   		 			line2=line.substring(channelStart+1, channelLength);
   		 			readEChannels.add(line2);
   		 			channelEnd=line.indexOf("\"",channelStart+1);
   		 		}
   			}
   		 	if (line.indexOf("channelRefs[0]")>0) {
   		 		channelStart=0;
   		 		channelEnd=0;
   		 		while ((channelStart=line.indexOf("\"",channelEnd+1))>0) {
   		 			channelLength=line.indexOf("\"",channelStart+1);
   		 			line2=line.substring(channelStart+1, channelLength);
   		 			readEChannelsRef.add(line2);
   		 			channelEnd=line.indexOf("\"",channelStart+1);
   		 		}
   		 	}
		}
		if (readEChannels.size()==readEChannelsRef.size()) {
    		for (int i = 0; i < readEChannels.size(); ++i) {
    			senderList.add(new BOSender(""+(i+1),(String)readEChannelsRef.get(i),(String)readEChannels.get(i)));
    		}
    	}
		Logger.getLogger("SerBoxControlEnigma").info("Sender verarbeitet");
        return senderList;
	}
	
	public ArrayList getSender(BOBouquet bouquet) throws IOException {
		ArrayList senderList = new ArrayList();
		String line;
		String line2="";
		int channelStart=0;
		int channelEnd=0;
		int channelLength=0;
		int bouquetStart=0;
		int countChannels=0;
		ArrayList readEChannels= new ArrayList();
		ArrayList readEChannelsRef= new ArrayList();
		BufferedReader in = getConnection("/body");
		while ((line = in.readLine()) != null) {
			if (line.indexOf("channels["+bouquet.getBouquetNummer()+"]")>0) {
   		 		channelStart=0;
   		 		channelEnd=0;  		 		
   		 		bouquetStart=line.indexOf("channels["+bouquet.getBouquetNummer()+"]");
   		 		while (((channelStart=line.indexOf("\"",channelEnd+1))>0)&(channelStart<bouquetStart)) {
   		 			countChannels++;
   		 			channelEnd=line.indexOf("\"",channelStart+1);
   		 		}
   		 		channelStart=0;
   		 		channelEnd=bouquetStart;
   		 		while (((channelStart=line.indexOf("\"",channelEnd+1))>0)&(channelStart<line.indexOf(");",bouquetStart))) {
   		 			channelLength=line.indexOf(" - ",channelStart+1);
   		 			if ((channelLength<=0)|(channelLength>line.indexOf("\"",channelStart+1)))
   		 				channelLength=line.indexOf("\"",channelStart+1);
   		 			line2=line.substring(channelStart+1, channelLength);
   		 			readEChannels.add(line2);
   		 			channelEnd=line.indexOf("\"",channelStart+1);
   		 		}
   			}
			if (line.indexOf("channelRefs["+bouquet.getBouquetNummer()+"]")>0) {
   		 		channelStart=0;
   		 		bouquetStart=line.indexOf("channelRefs["+bouquet.getBouquetNummer()+"]");
   		 		channelEnd=bouquetStart;
   		 		while (((channelStart=line.indexOf("\"",channelEnd+1))>0)&(channelStart<line.indexOf(");",bouquetStart))) {
   		 			channelLength=line.indexOf("\"",channelStart+1);
   		 			line2=line.substring(channelStart+1, channelLength);
   		 			readEChannelsRef.add(line2);
   		 			channelEnd=line.indexOf("\"",channelStart+1);
   		 		}
   		 	}
		}
		if (readEChannels.size()==readEChannelsRef.size()) {
    		for (int i = 0; i < readEChannels.size(); ++i) {
    			countChannels++;
    			senderList.add(new BOSender(""+(countChannels),(String)readEChannelsRef.get(i),(String)readEChannels.get(i)));
    		}
    	}		
		return senderList;
	}	 

	public String zapTo(String channelId) throws IOException {
		String status = new String();
		//BufferedReader input = SerBoxControl.getConnection("/fb/switch.dbox2?zapto="+channelId);
		BufferedReader input = getConnection("/cgi-bin/zapTo?path="+channelId);
		String line;
		while((line=input.readLine())!=null) {
			status = line;
		}
		return "ok";
	}
	
	public ArrayList getEpg(BOSender sender) throws IOException {
		ArrayList epgList=new ArrayList();
		BufferedReader input;
		input=null;
		try {
		    input = getConnection("/getcurrentepg2?ref="+sender.getChanId());
		}
		 catch (IOException e) {}
		if (input==null) {
		    input = getConnection("/getcurrentepg?type=extended&ref="+sender.getChanId());
		}
		int startEpgInfo;
		int endEpgInfo;
		GregorianCalendar startDate=new GregorianCalendar(), endDate=new GregorianCalendar();
		String line, eventId=new String(), startTime=new String(), endTime=new String(), duration=new String(), title=new String() ;
		String valueStart, valueDuration;
		while((line=input.readLine())!=null) {
			if ((startEpgInfo=line.indexOf("<span class=\"time\">"))>0) {
				endEpgInfo=line.indexOf("</span>",startEpgInfo);
				//eventId=sender.getChanId()+line.substring(startEpgInfo+19,endEpgInfo-1);
				eventId=line.substring(startEpgInfo+19,endEpgInfo-1);
				startEpgInfo=line.indexOf("&start=")+7;
				endEpgInfo=line.indexOf("&duration=",startEpgInfo);
				valueStart=line.substring(startEpgInfo,endEpgInfo);
				startTime=SerFormatter.getShortTime(Long.parseLong(valueStart)*1000);
				startDate =	SerFormatter.formatUnixDate(valueStart);
				startEpgInfo=endEpgInfo+10;
				endEpgInfo=line.indexOf("&descr=",startEpgInfo);
				valueDuration=line.substring(startEpgInfo,endEpgInfo);
				duration=SerFormatter.formatedEndTime(valueDuration);
				startEpgInfo=line.indexOf("<span class=\"event\">")+20;
				endEpgInfo=line.indexOf("</span>",startEpgInfo);
				endTime = SerFormatter.getShortTime(Long.parseLong(valueStart) * 1000 + Long.parseLong(valueDuration) * 1000);
				endDate = SerFormatter.formatUnixDate(Long.parseLong(valueStart)*1000 + Long.parseLong(valueDuration)*1000);    
				title=line.substring(startEpgInfo,endEpgInfo);
				epgList.add(new BOEpg(sender, eventId, startTime, startDate, endTime, endDate, duration, title, valueStart, valueDuration));
			}
		}
		return epgList;
	}
	
	public BOEpgDetails getEpgDetail(BOEpg epg) throws IOException {
		BOEpgDetails epgDetail = new BOEpgDetails();
		BOSender sender=epg.getSender();
		BufferedReader input;
		input=null;
		try {
		    input = getConnection("/getcurrentepg2?ref="+sender.getChanId());
		}
		 catch (IOException e) {}
		if (input==null) {
		    input = getConnection("/getcurrentepg?type=extended&ref="+sender.getChanId());
		}
		String text = new String();
		String line;
		String eventId=epg.getEventId();		
		while((line=input.readLine())!=null) {
			int startEpgInfo;
			int endEpgInfo;
			if (line.indexOf("<span class=\"time\">"+eventId)>0) {
				startEpgInfo=line.indexOf("<span class=\"description\">")+26;
				endEpgInfo=line.indexOf("</span>",startEpgInfo);
				if (endEpgInfo<startEpgInfo) {
					text+=line.substring(startEpgInfo)+"\n";
					if ((line=input.readLine())!=null) {
						startEpgInfo=0;
						endEpgInfo=line.indexOf("</span>",startEpgInfo);
					}
				}
				text+=line.substring(startEpgInfo,endEpgInfo)+"\n";
			}
		}
		epgDetail.setText(text);
		epg.setEpgDetail(epgDetail);
		return epgDetail;
	}
	private String sendCommand (String message) throws IOException {
        String line;          
        BufferedReader input = getConnection("/cgi-bin/admin?command="+message);          
        while ((line = input.readLine()) != null) {  
          StringTokenizer st = new StringTokenizer(line);    
              message = (st.nextToken());
        }         
       return message;  
	}
	
	public String sendMessage(String message) throws IOException {
        throw new IOException();
	}
   
	public String shutdownBox() throws IOException {
		Logger.getLogger("SerBoxControlEnigma").info("Ihre Dbox wird in den StandbyModus gebracht.");
		return sendCommand("shutdown");
	}
   
	public String standbyBox(String modus) throws IOException {
		Logger.getLogger("SerBoxControlEnigma").info("Ihre Dbox wird in den StandbyModus gebracht.");
		return sendCommand("standby");
	}
	public ArrayList[] readTimer() throws IOException {
		ArrayList[] timerList = new ArrayList[2];
		timerList[0] = new ArrayList();
		timerList[1] = new ArrayList();
		Logger.getLogger("SerBoxControlEnigma").info("Lese Timer-Liste");
		BufferedReader input=getConnection("/body?mode=controlTimerList");
		Logger.getLogger("SerBoxControlEnigma").info("Timer-Liste gelesen");
		boolean recurring = false;
		boolean onetimer=false;
		GregorianCalendar startDate=new GregorianCalendar(), endDate=new GregorianCalendar();
		String line, channelID=new String(), eventId=new String(), startTime=new String(), endTime=new String(), duration=new String(), title=new String(), channel=new String();
		String valueStart, valueDuration, timerType;
		String eventRepeatId="0";
		int startpos, endpos;
		while ((line = input.readLine()) != null) {
			if ((line.indexOf("Recurring Timer Events")>0)|(line.indexOf("Repeating Timer Events")>0)) {
				recurring=true;
				onetimer=false;
			}
			if ((line.indexOf("One-time Timer Events")>0)|(line.indexOf("One-Time Timer Events")>0)) {
				recurring=false;
				onetimer=true;
			}
			if (line.indexOf("javascript:editTimerEvent")>0) {
				startpos=0;
				while (line.indexOf("javascript:editTimerEvent", startpos)>0) {
					eventRepeatId="0";
				    endpos=line.indexOf("javascript:editTimerEvent", startpos);
					startpos=line.indexOf("(\'ref=",endpos);
					endpos=line.indexOf("&start=", startpos);
					channelID=line.substring(startpos+6,endpos);
					startpos=endpos;
					endpos=line.indexOf("&duration=", startpos);
					valueStart=line.substring(startpos+7,endpos);
					startpos=endpos;
					endpos=line.indexOf("&channel=", startpos);
					duration=line.substring(startpos+10,endpos);
					valueDuration=duration;
					startpos=endpos;
					endpos=line.indexOf("&descr=", startpos);
					channel=line.substring(startpos+9,endpos);
					startpos=endpos;
					endpos=line.indexOf("&type=", startpos);
					title=line.substring(startpos+7,endpos);
					startpos=endpos;
					endpos=line.indexOf("\')", startpos);
					timerType=line.substring(startpos+6,endpos);
					startTime=SerFormatter.getShortTime(Long.parseLong(valueStart));
					startDate =	SerFormatter.formatUnixDate(valueStart);
					endTime = SerFormatter.getShortTime((Long.parseLong(valueStart) + Long.parseLong(valueDuration)));
					endDate = SerFormatter.formatUnixDate((Long.parseLong(valueStart) + Long.parseLong(valueDuration)));
					if (recurring) {
					    /*long typeValue=0;
					    String recurringDays;
					    startpos=line.indexOf("nbsp;</td><td>",endpos);
					    endpos=line.indexOf("</td>",startpos+10);
					    recurringDays=line.substring(startpos+13,endpos);
					    if (recurringDays.indexOf("Mo")>0) {
					        typeValue+=256;
					    }
					    if (recurringDays.indexOf("Tu")>0) {
					        typeValue+=512;
					    }
					    if (recurringDays.indexOf("We")>0) {
					        typeValue+=1024;
					    }
					    if (recurringDays.indexOf("Th")>0) {
					        typeValue+=2048;
					    }
					    if (recurringDays.indexOf("Fr")>0) {
					        typeValue+=4096;
					    }
					    if (recurringDays.indexOf("Sa")>0) {
					        typeValue+=8192;
					    }
					    if (recurringDays.indexOf("Su")>0) {
					        typeValue+=16384;
					    }
					    System.out.println(recurringDays+" "+typeValue);*/
					    long typeValue=Long.parseLong(timerType);
					    long typeValue2=0;
					    if ((typeValue&1048576)==1048576) {
					        typeValue2+=512;
					    }
					    if ((typeValue&2097152)==2097152) {
					        typeValue2+=1024;
					    }
					    if ((typeValue&4194304)==4194304) {
					        typeValue2+=2048;
					    }
					    if ((typeValue&8388608)==8388608) {
					        typeValue2+=4096;
					    }
					    if ((typeValue&16777216)==16777216) {
					        typeValue2+=8192;
					    }
					    if ((typeValue&33554432)==33554432) {
					        typeValue2+=16384;
					    }
					    if ((typeValue&524288)==524288) {
					        typeValue2+=32768;
					    }
					    eventRepeatId=""+typeValue2;
					}
					BOTimer botimer = new BOTimer();
					botimer.setEventTypeId(timerType);
					botimer.setTimerNumber("");
					botimer.setEventRepeatId(eventRepeatId);
	    			botimer.setSenderName(channel);
	    			botimer.setChannelId(channelID);
	    			botimer.setAnnounceTime(""); //vorwarnzeit
	    			botimer.setUnformattedStartTime(startDate);  //startDatum
	    			botimer.setUnformattedStopTime(endDate);
	    			botimer.setDescription(title);
	    			botimer.setTimerNumber("ref="+channelID+"&start="+valueStart+"&type="+timerType+"&force=no");
	    			timerList[0].add(botimer);
	    			startpos=endpos;
				} 
			}
		}
		Logger.getLogger("SerBoxControlEnigma").info("Timer-Liste verarbeitet");
		return timerList;
	}
	public String writeTimer(BOTimer timer) throws IOException {
	    String modifiedId = timer.getModifiedId();
	    String title = timer.getDescription();
	    String announce = timer.getAnnounceTime();			
	    String eventType = timer.getEventTypeId();
		String repeat = timer.getEventRepeatId();
		String chanId = timer.getChannelId();
		String success;
		success="failed";
		int startpos;
		if (title==null) {
		    title="";
		}
		String line;
		if (modifiedId.equalsIgnoreCase("deleteall")) {
		    String requestString = "/clearTimerList";
		    BufferedReader input = getConnection(requestString); 
		}
		if (modifiedId.equalsIgnoreCase("cleanup")) {
		    String requestString = "/cleanupTimerList";
		    BufferedReader input = getConnection(requestString); 
		}
		if (modifiedId.equalsIgnoreCase("remove")|modifiedId.equalsIgnoreCase("modify")) {
		    String requestString = "/deleteTimerEvent?"+timer.getTimerNumber();
		    BufferedReader input = getConnection(requestString); 
		}
		if (modifiedId.equalsIgnoreCase("new")|modifiedId.equalsIgnoreCase("modify")) {
		    long alarm = (timer.getUnformattedStartTime().getTimeInMillis())/1000;
			long stop = (timer.getUnformattedStopTime().getTimeInMillis())/1000;
			title=title.replaceAll(" ","%20");
			title=title.replaceAll("&","und");
		    long duration=stop-alarm;
		    long repeatId=Long.parseLong(repeat);
		    //System.out.println (repeatId);
		    if (repeatId<30) {
		        repeatId=0;
		    }
		    String requestString;
		    if (repeatId==0) {
		        requestString = "/addTimerEvent?timer=regular&ref="+chanId+"&start="+alarm+"&duration="+duration+"&descr="+title+"&after_event="+eventType;
		    } else {
		        String mo="off";
		        String tu="off";
		        String we="off";
		        String th="off";
		        String fr="off";
		        String sa="off";
		        String su="off";
		        if ((repeatId&512)==512) {
		            mo="on";
		        }
		        if ((repeatId&1024)==1024) {
		            tu="on";
		        }
		        if ((repeatId&2048)==2048) {
		            we="on";
		        }
		        if ((repeatId&4096)==4096) {
		            th="on";
		        }
		        if ((repeatId&8192)==8192) {
		            fr="on";
		        }
		        if ((repeatId&16384)==16384) {
		            sa="on";
		        }
		        if ((repeatId&32768)==32768) {
		            su="on";
		        } 
		        requestString = "/addTimerEvent?timer=repeating&ref="+chanId+"&shour="+timer.getShortStartTime().substring(0,2)+"&smin="+timer.getShortStartTime().substring(3,5)+"&ehour="+timer.getStopTime().substring(0,2)+"&emin=" +timer.getStopTime().substring(3,5)+ "&mo=" + mo + "&tu=" + tu + "&we=" + we + "&th=" + th + "&fr=" + fr + "&sa=" + sa + "&su=" + su +"&duration="+duration+"&descr="+title+"&after_event="+eventType;
		    }
		    //System.out.println (requestString);
		    BufferedReader input = getConnection(requestString);
		    while((line=input.readLine())!=null) {
		       	if ((line.indexOf("success")>0)^(line.indexOf("erfolgreich")>0)) {
		       	    success="ok";
		       	}
			}
		    if (success.equals("ok")) { 
	 		    Logger.getLogger("SerBoxControlEnigma").info("Timer "+timer.getStartTime()+" "+timer.senderName+" - "+timer.description+" erfolgreich übertragen");
		    } else {
		        Logger.getLogger("SerBoxControlEnigma").info("Fehler beim Timer "+timer.getStartTime()+" "+timer.senderName+" - "+timer.description);
		    }
		}
		
		return success;
	}
	
	public boolean isTvMode() throws IOException{
		BufferedReader input = getConnection("/body");
		String line;
		String zapmode;
		boolean tvMode;
		int startpos;
		while((line=input.readLine())!=null) {
		    if (line.indexOf("zapMode")>0) {
		        startpos=(line.indexOf("zapMode")+10);
		        zapmode=line.substring(startpos,startpos+1);
		        if (zapmode.equals("0")) {
		            tvMode=true;
		        } else {
		            tvMode=false;
		        }
		        return tvMode;
		    }
			
		}
		return true;
	}
	
	public String setRadioTvMode(String mode) throws IOException {
		String zapmode;
		if (mode.equalsIgnoreCase("radio")) {
		    zapmode="1";
		} else {
		    zapmode="0";
		}
	    BufferedReader input = getConnection("/body?mode=zap&zapmode="+zapmode+"&zapsubmode=4");
		return "ok";
	}
}
