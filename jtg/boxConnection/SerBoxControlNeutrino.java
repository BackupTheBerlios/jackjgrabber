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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import service.SerFormatter;

import model.BOBouquet;
import model.BOEpg;
import model.BOEpgDetails;
import model.BOSender;
import model.BOTimer;
import control.ControlMain;

/**
 * @author AlexG
 */
public class SerBoxControlNeutrino extends SerBoxControl{
			
	public String getName() {
		return "Neutrino";
	}
	
	public String getChanIdOfRunningSender() throws IOException {
		BufferedReader input = getConnection("/control/zapto");
		
		String line;
		while((line=input.readLine())!=null) {
			return line;
		}
		return line;
	}
	
	public BufferedReader getConnection(String request) throws IOException {
		return new BufferedReader(new InputStreamReader(new URL("http://"+ControlMain.getBoxIpOfSelectedBox()+request).openStream()));
	}
		
	public ArrayList getPids() throws IOException {
		ArrayList pidList = new ArrayList();
		String line;	
	
		BufferedReader input = getConnection("/control/zapto?getallpids");

		while((line=input.readLine())!=null) {
			pidList.add((Integer.toHexString(Integer.parseInt(line))));
		}

		return pidList;
	}	 
	
	public ArrayList getBouquetList() throws IOException {
		ArrayList bouquetList = new ArrayList();
		String line;
		
		BufferedReader input = getConnection("/control/getbouquets");
			
		while((line=input.readLine())!=null) {
        	StringTokenizer st = new StringTokenizer(line);
			String nummer = st.nextToken();
			String name = st.nextToken();
			bouquetList.add(new BOBouquet(nummer, name));
		}
		return bouquetList;
	}
	
	public ArrayList getAllSender() throws IOException {
		ArrayList senderList = new ArrayList();
		String line;
	
		BufferedReader in = getConnection("/control/channellist");
        while ((line = in.readLine()) != null) {
        	StringTokenizer st = new StringTokenizer(line);
			String chanId = st.nextToken();
			
			String name = new String();	
			while (st.hasMoreTokens()) {
				name += st.nextToken();
				name += " ";
			}
			senderList.add(new BOSender("1",chanId, name.substring(0, name.length()-1))); 
        }
        return senderList;
	}
	
	public ArrayList getSender(BOBouquet bouquet) throws IOException {
		ArrayList senderList = new ArrayList();
		String line;
	
		BufferedReader input = getConnection("/control/getbouquet?bouquet="+bouquet.getBouquetNummer()+"&mode=TV");
		
		while((line=input.readLine())!=null) {
			StringTokenizer st = new StringTokenizer(line);
			String nummer = st.nextToken();
			String channelId = st.nextToken();
			
			String name = new String();
			while (st.hasMoreTokens()) {
				name += st.nextToken();
				name += " ";
			}
			senderList.add(new BOSender(nummer,channelId, name));
		}
		return senderList;
	}	 
	
	public String zapTo(String channelId) throws IOException {
		//BufferedReader input = SerBoxControl.getConnection("/fb/switch.dbox2?zapto="+channelId);
		BufferedReader input = getConnection("/control/zapto?"+channelId);
		
		String line;
		while((line=input.readLine())!=null) {
			return line;
		}
		return line;
	}
	
	public ArrayList getEpg(BOSender sender) throws IOException {
		ArrayList epgList=new ArrayList();
		BufferedReader input = getConnection("/control/epg?"+sender.getChanId());
		String line, eventId, startTime, duration, title, endTime, valueStart, valueDuration;;
		GregorianCalendar startDate, endDate;
                
		while((line=input.readLine())!=null) {
			StringBuffer buffer = new StringBuffer(line);
			StringTokenizer st = new StringTokenizer(line);
			
			eventId = st.nextToken();
			valueStart=st.nextToken();
			valueDuration=st.nextToken();
			
			title = new String();
		    while (st.hasMoreTokens()) {
		    	title += st.nextToken();
		    	title += " ";
			}

			startTime = SerFormatter.getShortTime(Long.parseLong(valueStart)*1000);
			startDate = SerFormatter.formatUnixDate(valueStart);
			duration = SerFormatter.formatedEndTime(valueDuration);
			endTime = SerFormatter.getShortTime(Long.parseLong(valueStart) * 1000 + Long.parseLong(valueDuration) * 1000);
			endDate = SerFormatter.formatUnixDate(Long.parseLong(valueStart)*1000 + Long.parseLong(valueDuration)*1000);                        
			
			epgList.add(new BOEpg(sender, eventId, startTime, startDate, endTime, endDate, duration, title, valueStart, valueDuration));                        
		}
		return epgList;
	} 
	
	public BOEpgDetails getEpgDetail(BOEpg epg) throws IOException {
		BOEpgDetails epgDetail = new BOEpgDetails();
		BufferedReader input = getConnection("/control/epg?eventid="+epg.getEventId());
		
		String text = new String();
		String line;
		while((line=input.readLine())!=null) {
			text+=line+" \n";
		}
		epgDetail.setText(text);
		return epgDetail;
	}

	private String sendCommand (String message) throws IOException {
        String line;          
        BufferedReader input = getConnection("/control/"+message);          
        while ((line = input.readLine()) != null) {  
        	StringTokenizer st = new StringTokenizer(line);    
        	message = (st.nextToken());
        }         
       return message;  
	}
  
	public String sendMessage(String message) throws IOException {                                         
		Logger.getLogger("SerBoxControlNeutrino").info("Sende Message zur Box...");
		return sendCommand("message?popup="+message); 
	}
	
	public String standbyBox(String modus) throws IOException {
		Logger.getLogger("SerBoxControlNeutrino").info("Ihre Dbox wird in den StandbyModus gebracht.");
		return sendCommand("standby?"+modus);
	}
	
	public String shutdownBox() throws IOException {
		Logger.getLogger("SerBoxControlNeutrino").info("Ihre Dbox wird runtegefahren.");         
		return sendCommand("shutdown");
	}
	/**
	 * Rückgabe eines Arrays mit 2 ArrayListen. An 1. Postion Programm-Timer
	 * 2. Position ArrayList mit SystemTimer
	 */
	public ArrayList[] readTimer() throws IOException {
		ArrayList[] timerList = new ArrayList[2];
		timerList[0] = new ArrayList();
		timerList[1] = new ArrayList();
		
		BufferedReader inputNhttpd = getConnection("/control/timer");
		String line;
		while ((line = inputNhttpd.readLine()) != null) {
			String valueStart, valueStop, valueAnno, valueSenderName = new String();
			BOTimer botimer = new BOTimer();
						
	        StringTokenizer st = new StringTokenizer(line);
	        
            botimer.setTimerNumber(st.nextToken());
            botimer.setEventTypeId(st.nextToken());
            botimer.setEventRepeatId(st.nextToken());
            
            valueAnno=st.nextToken(); 
		    valueStart=st.nextToken();
		    valueStop=st.nextToken();
		    if (!valueStop.equals("0")) {
		    	while (st.hasMoreTokens()) {
		    		valueSenderName += st.nextToken();
		    		valueSenderName += " ";
				}
		    	botimer.setSenderName( valueSenderName.substring(0, valueSenderName.length()-1) );
		    }

		    botimer.setAnnounceTime(valueAnno);
		    botimer.setUnformattedStartTime(SerFormatter.formatUnixDate(valueStart));  
			botimer.setUnformattedStopTime(SerFormatter.formatUnixDate(valueStop)); 
		    
		    if (botimer.getEventTypeId().equals("5")) {
		    	timerList[0].add(botimer);
		    } else {
		    	timerList[1].add(botimer);   	
		    }
		}
		setTimerDesctiptionName(timerList[0]);
		return timerList;
	}
	
	/**
	 * Passenden Titel im Web-Interface-Aufruf finden (dirty!)
	 */
	private void setTimerDesctiptionName(ArrayList timerList) throws IOException {
		BufferedReader inputWebInf = getConnection("/fb/timer.dbox2");
		
		String line;
		String searchString="&startzeit=";
		int indexSearchString, indexIdentifier;
		
		while ((line = inputWebInf.readLine()) != null) {
			if ((indexSearchString = line.indexOf(searchString))>0) {
				for (int i=0; i<timerList.size(); i++) {
					BOTimer timer = (BOTimer)timerList.get(i);
					if (timer.getSenderName() != null && line.indexOf(timer.getSenderName())>0 && line.indexOf(timer.getShortStartTime())>0) {
						indexIdentifier= line.indexOf(">", indexSearchString);
						timer.setDescription(line.substring(indexIdentifier+1, line.length()-4));
					}
				}
			}
		}	
	} 
	
	public String writeTimer(BOTimer timer) throws IOException {
		StringBuffer buffer = this.fillRequestString(timer);
		BufferedReader input = getConnection(buffer.toString());
		System.out.println(buffer.toString());
		String line;
		while((line=input.readLine())!=null) {
			timer.setModifiedId(null);
			return line;
		}
		return line;
	}
	
	private StringBuffer fillRequestString(BOTimer timer) {
		String modifiedId = timer.getModifiedId();
		String alarm = Long.toString(timer.getUnformattedStartTime().getTimeInMillis()/1000);
		String stop = Long.toString(timer.getUnformattedStopTime().getTimeInMillis()/1000);
		StringBuffer buffer = new StringBuffer();
		if (modifiedId.equals("remove")) {
			buffer.append("/fb/timer.dbox2?action="+modifiedId);
			buffer.append("&id="+timer.getTimerNumber());
		}
		if (modifiedId.equals("modify")) {
			buffer.append("/fb/timer.dbox2?action="+modifiedId);
			//buffer.append("/control/timer?action="+modifiedId);
			buffer.append("&id="+timer.getTimerNumber());
			
			buffer.append("&ad="+timer.getUnformattedStartTime().get(Calendar.DAY_OF_MONTH));
			buffer.append("&amo="+(timer.getUnformattedStartTime().get(Calendar.MONTH)+1));
			buffer.append("&ay="+timer.getUnformattedStartTime().get(Calendar.YEAR));
			buffer.append("&ah="+timer.getUnformattedStartTime().get(Calendar.HOUR));
			buffer.append("&ami="+timer.getUnformattedStartTime().get(Calendar.MINUTE));
			
			buffer.append("&sd="+timer.getUnformattedStopTime().get(Calendar.DAY_OF_MONTH));
			buffer.append("&smo="+(timer.getUnformattedStopTime().get(Calendar.MONTH)+1));
			buffer.append("&sy="+timer.getUnformattedStopTime().get(Calendar.YEAR));
			buffer.append("&sh="+timer.getUnformattedStopTime().get(Calendar.HOUR));
			buffer.append("&smi="+timer.getUnformattedStopTime().get(Calendar.MINUTE));
			
			buffer.append("&announce="+timer.getAnnounceTime());
			buffer.append("&type="+timer.getEventTypeId());
			buffer.append("&rep="+timer.getEventRepeatId());
			buffer.append("&channel_id="+timer.getChannelId());
		} 
		if (modifiedId.equals("new")) {
			buffer.append("/control/timer?action="+modifiedId);
			buffer.append("&alarm="+alarm);
			buffer.append("&stop="+stop);
			buffer.append("&announce="+timer.getAnnounceTime());
			buffer.append("&type="+timer.getEventTypeId());
			buffer.append("&rep="+timer.getEventRepeatId());
			buffer.append("&channel_id="+timer.getChannelId());
		}
		return buffer;
	}
}
