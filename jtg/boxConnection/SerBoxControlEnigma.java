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

import model.BOBouquet;
import model.BOEpg;
import model.BOEpgDetails;
import model.BOPid;
import model.BOPids;
import model.BOSender;
import model.BOTimer;
import model.BOTimerList;

import org.apache.log4j.Logger;

import service.SerFormatter;
import control.ControlMain;

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
    
    private BOTimerList timerList;
    
    public GregorianCalendar getBoxTime() throws IOException {
	    return new GregorianCalendar();
	}
    
    public BOTimerList getTimerList() throws IOException {
        if (timerList==null) {
            timerList=this.readTimer();
        }
        return timerList;
    }
    
    public BOTimerList reReadTimerList() throws IOException {
        timerList=this.readTimer();
        return timerList;
    }
    
	
	public String getName() {
		return "Enigma";
	}
	
	public String getChanIdOfRunningSender() throws IOException {
	    BufferedReader input = getConnection("/cgi-bin/currentService");
		String line;
		while((line=input.readLine())!=null) {
			return line;
		}
		return line;
	}
	
	public BufferedReader getConnection(String request) throws IOException {
	    return new BufferedReader(new InputStreamReader(new URL("http://"+ControlMain.getBoxIpOfActiveBox()+request).openStream(),"UTF-8"));
	}
		
	public BOPids getPids(boolean tvMode) throws IOException {
	    BOPids pids = new BOPids();
		String line;
		BufferedReader input = getConnection("/control/zapto?getpids");
		if (tvMode) {
		    line=input.readLine();
		    if (line.length() > 5) {
		        Logger.getLogger("SerBoxControlEnigma").info(ControlMain.getProperty("err_zapping"));
		        return pids;
		    }
		    pids.setVPid(new BOPid(Integer.toHexString(Integer.parseInt(line)),"", 0));
		} 
		while((line=input.readLine())!=null) {
		    if (line.length() > 5) {
		        Logger.getLogger("SerBoxControlEnigma").info(ControlMain.getProperty("err_zapping"));
		        return pids;
		    }
			pids.getAPids().add(new BOPid(Integer.toHexString(Integer.parseInt(line)),"", 1));
		}
		return pids;
	}	 
	
	public ArrayList getBouquetList() throws IOException {
		ArrayList bouquetList = new ArrayList();
		String line;
		int seperator;
		boolean tvMode=isTvMode();
		BufferedReader in;
		if (tvMode) {
		    in = getConnection("/cgi-bin/getServices?ref=4097:7:0:6:0:0:0:0:0:0:");
		} else {
		    in = getConnection("/cgi-bin/getServices?ref=4097:7:0:4:0:0:0:0:0:0:");
		}
		while ((line = in.readLine()) != null) {
		    seperator=line.indexOf(";");
		    if (seperator>0) {
		        bouquetList.add(new BOBouquet(line.substring(0,seperator), line.substring(seperator+1)));
		    }
		}
   		return bouquetList;
	}
	
	public ArrayList getAllSender() throws IOException {
	    ArrayList senderList = new ArrayList();
		String line;
		int seperator;
		boolean tvMode=isTvMode();
		BufferedReader in;
		long countChannels = 0;
		if (tvMode) {
		    in = getConnection("/cgi-bin/getServices?ref=4097:7:0:6:0:0:0:0:0:0:&listContent=true");
		} else {
		    in = getConnection("/cgi-bin/getServices?ref=4097:7:0:4:0:0:0:0:0:0:&listContent=true");
		}
		while ((line = in.readLine()) != null) {
		    if (line.substring(0,4).equals("4097")) {
		    } else {
		        seperator=line.indexOf(";");
		        if (seperator>0) {
		            countChannels++;
		            senderList.add(new BOSender(""+(countChannels),line.substring(0,seperator),line.substring(seperator+1,line.indexOf(";",seperator+1))));
		        }
		    }
		}
        return senderList;
	}
	
	public ArrayList getSender(BOBouquet bouquet) throws IOException {
		/*ArrayList senderList = new ArrayList();
		String line;
		int seperator;
		BufferedReader in = getConnection("/cgi-bin/getServices?ref="+bouquet.getBouquetNummer());
		long countChannels = 0;
		while ((line = in.readLine()) != null) {
		    seperator=line.indexOf(";");
		    if (seperator>0) {
		        countChannels++;
		        senderList.add(new BOSender(""+(countChannels),line.substring(0,seperator),line.substring(seperator+1,line.indexOf(";",seperator+1))));
		    }
		}*/
	    ArrayList senderList = new ArrayList();
		String line;
		int seperator;
		boolean tvMode=isTvMode();
		BufferedReader in;
		long countChannels = 0;
		if (tvMode) {
		    in = getConnection("/cgi-bin/getServices?ref=4097:7:0:6:0:0:0:0:0:0:&listContent=true");
		} else {
		    in = getConnection("/cgi-bin/getServices?ref=4097:7:0:4:0:0:0:0:0:0:&listContent=true");
		}
		while ((line = in.readLine()) != null) {
		    if (line.substring(0,4).equals("4097")) {
		        if (line.indexOf(bouquet.getBouquetNummer().substring(1))>0) {
		            while ((line = in.readLine()) != null) {
		                if (line.substring(0,4).equals("4097")) {
		                    return senderList;
		                } else {
		                    seperator=line.indexOf(";");
		    		        if (seperator>0) {
		    		            countChannels++;
		    		            senderList.add(new BOSender(""+(countChannels),line.substring(0,seperator),line.substring(seperator+1,line.indexOf(";",seperator+1))));
		    		        }
		                }
		            }
		        }
		    } else {
		        countChannels++;
		        
		    }
		}
		return senderList;
	}	 

	public String zapTo(String channelId) throws IOException {
		String status = "ok";
		if (isRecording()) {
		    Logger.getLogger("SerBoxControlEnigma").error(ControlMain.getProperty("err_zappingRecord"));
		    status="error";
		} else {
		    BufferedReader input = getConnection("/cgi-bin/zapTo?path="+channelId);
		    String line;
		    while((line=input.readLine())!=null) {
		        if (line.equalsIgnoreCase("error")) {
		            status="error";
		        }
		    }
		}
		return status;
	}
	
	public ArrayList getEpg(BOSender sender) throws IOException {
	    ArrayList epgList=new ArrayList();
	    BufferedReader input = getConnection("/getcurrentepg?ref="+sender.getChanId());
	    int startEpgInfo;
		int endEpgInfo, startpos, endpos;
		long durationlong;
		GregorianCalendar startDate=new GregorianCalendar(), endDate=new GregorianCalendar();
		String line, line2, eventId=new String(), startTime=new String(), endTime=new String(), title=new String() ;
		String valueStart, valueEnd, valueDuration,startDateString,endDateString,duration;
		while((line=input.readLine())!=null) {
		    startpos=line.indexOf("ID: ");
		    if (startpos > 0){
		        while ((line2=input.readLine())!=null) {
		            if (line2.indexOf("ID: ")>0) {
		                startpos+=4;
		                endpos=line.indexOf(" ", startpos+1);
		                eventId=line.substring(startpos, endpos);
		                startpos=line.indexOf("<span class=\"epg\">")+18;
		                endpos=line.indexOf(" ", startpos+1);
		                startDateString=line.substring(startpos, endpos)+".";
		                startpos=endpos+3;
		                endpos=line.indexOf(" ", startpos+1);
		                startTime=line.substring(startpos, endpos);
		                startpos=endpos+1;
		                endpos=line.indexOf("</span", startpos);
		                title=line.substring(startpos, endpos);
		                startpos=line2.indexOf("<span class=\"epg\">")+18;
		                endpos=line2.indexOf(" ", startpos+1);
		                endDateString=line2.substring(startpos, endpos)+".";
		                startpos=endpos+3;
		                endpos=line2.indexOf(" ", startpos+1);
		                endTime=line2.substring(startpos, endpos);
		                startDate=SerFormatter.getDateFromString(startDateString+"/"+startTime, "dd.MM./HH:mm");
		                endDate=SerFormatter.getDateFromString(endDateString+"/"+endTime, "dd.MM./HH:mm");
		                valueStart=""+(startDate.getTimeInMillis()/1000);
		                valueDuration = ""+((endDate.getTimeInMillis()/1000-startDate.getTimeInMillis()/1000));
		                duration=SerFormatter.formatUnixTimeToDuration(valueDuration);
		                startTime=SerFormatter.getShortTime(Long.parseLong(valueStart)*1000);
						startDate =	SerFormatter.formatUnixDate(valueStart);
						endTime = SerFormatter.getShortTime(Long.parseLong(valueStart) *1000  + Long.parseLong(valueDuration) * 1000);
						endDate = SerFormatter.formatUnixDate(Long.parseLong(valueStart)*1000 + Long.parseLong(valueDuration)*1000);    
						epgList.add(new BOEpg(sender, eventId, startTime, startDate, endTime, endDate, duration, title, valueStart, valueDuration));
		                line=line2;
		                startpos=line2.indexOf("ID: ");
		            }
		        }
		    }
		}
	    
		return epgList;
	}
	
	public BOEpgDetails getEpgDetail(BOEpg epg) throws IOException {
	    BOEpgDetails epgDetail = new BOEpgDetails();
		BOSender sender=epg.getSender();
		String text = "";
		String line, line2;
		String eventId=epg.getEventId();	
		BufferedReader input = getConnection("/EPGDetails?ID="+eventId+"&ref="+sender.getChanId());
		while((line=input.readLine())!=null) {
		    if (line.indexOf("</h1") > 0) {
		        while((line2=input.readLine())!=null) {
		            if (line2.indexOf("body") < 1) {
		                if (text.length() >0) {
		                    text+="\n";
		                }
		                text+=line2.trim();
		            } else {
		                epgDetail.setText(text);
		        		epg.setEpgDetail(epgDetail);
		                return epgDetail;
		            }
		        }
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
	public BOTimerList readTimer() throws IOException {
	    BOTimerList timerList = new BOTimerList();
		BufferedReader input=getConnection("/body?mode=controlTimerList");
		boolean recurring = false;
		boolean onetimer=false;
		GregorianCalendar startDate=new GregorianCalendar(), endDate=new GregorianCalendar();
		String line, channelID=new String(), eventId=new String(), startTime=new String(), endTime=new String(), duration=new String(), title=new String(), channel=new String();
		String valueStart, valueDuration, timerType;
		String eventRepeatId="0";
		int startpos, startpos2, endpos, endpos2;
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
					if ((startpos==0)|(startpos<endpos+1)) {
					    startpos2=line.indexOf("(\"ref=",endpos);
					    if (startpos2>0) {
					        startpos=startpos2;
					    }
					}
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
					endpos2=line.indexOf("\")", startpos);
					if (((endpos<startpos)|endpos2<endpos)&(endpos2>startpos)) {
					    endpos=endpos2;
					}
					timerType=line.substring(startpos+6,endpos);
					startTime=SerFormatter.getShortTime(Long.parseLong(valueStart));
					startDate =	SerFormatter.formatUnixDate(valueStart);
					endTime = SerFormatter.getShortTime((Long.parseLong(valueStart) + Long.parseLong(valueDuration)));
					endDate = SerFormatter.formatUnixDate((Long.parseLong(valueStart) + Long.parseLong(valueDuration)));
					if (recurring) {
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
	    			botimer.unformattedStartTime=startDate;  //startDatum
	    			botimer.unformattedStopTime=endDate;
	    			botimer.setDescription(title);
	    			botimer.setTimerNumber("ref="+channelID+"&start="+valueStart+"&type="+timerType+"&force=no");
	    			timerList.getRecordTimerList().add(botimer);
	    			startpos=endpos;
				} 
			}
		}
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
	 		    Logger.getLogger("SerBoxControlEnigma").info(ControlMain.getProperty("msg_sentTimer")+" "+timer.getStartTime()+" "+timer.senderName+" - "+timer.description);
		    } else {
		        Logger.getLogger("SerBoxControlEnigma").info(ControlMain.getProperty("msg_timerError")+" "+timer.getStartTime()+" "+timer.senderName+" - "+timer.description);
		    }
		}
		
		return success;
	}
	
	public boolean isTvMode() throws IOException{
		BufferedReader input = getConnection("/cgi-bin/status");
		String line;
		String zapmode;
		boolean tvMode;
		int startpos;
		while((line=input.readLine())!=null) {
		    if (line.indexOf("Mode:</td><td>")>0) {
		        startpos=(line.indexOf("Mode:</td><td>")+14);
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
	
	public boolean isRecording() throws IOException{
		BufferedReader input = getConnection("/cgi-bin/status");
		String line;
		String zapmode;
		boolean Recording=true;
		int startpos;
		while((line=input.readLine())!=null) {
		    if (line.indexOf("Recording:</td><td>")>0) {
		        startpos=(line.indexOf("Recording:</td><td>")+19);
		        zapmode=line.substring(startpos,startpos+2);
		        if (zapmode.equals("ON")) {
		            Recording=true;
		        } else {
		            Recording=false;
		        }
		        return Recording;
		    }
			
		}
		return true;
	}
	
	public String setRadioTvMode(String mode) throws IOException {
		String zapmode;
		String status ="ok";
		int firstChannelStart;
		if (mode.equalsIgnoreCase("radio")) {
		    zapmode="1";
		} else {
		    zapmode="0";
		}
	    BufferedReader input = getConnection("/body?mode=zap&zapmode="+zapmode+"&zapsubmode=4");
	    String line;
		while((line=input.readLine())!=null) {
			if (line.equalsIgnoreCase("error")) {
			    status="error";
			}
			firstChannelStart = line.indexOf("\"1:0");
			if (firstChannelStart > 0) {
			    return zapTo(line.substring(firstChannelStart+1,line.indexOf("\"",firstChannelStart+1)));
			}
		}
		return status;
	}

	/* (non-Javadoc)
	 * @see boxConnection.SerBoxControl#setRecordModusWithPlayback()
	 */
	public String setRecordModusWithPlayback() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see boxConnection.SerBoxControl#setRecordModus()
	 */
	public String setRecordModus() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see boxConnection.SerBoxControl#stopRecordModus()
	 */
	public String stopRecordModus() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
