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
import java.util.Date;
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
			int blankIndex = line.indexOf(" ");
			String nummer = line.substring(0, blankIndex);
			String name = line.substring(blankIndex, line.length());
			bouquetList.add(new BOBouquet(nummer, name));
		}
		return bouquetList;
	}
	
	public ArrayList getAllSender() throws IOException {
		ArrayList senderList = new ArrayList();
		String line;
	
		BufferedReader in = getConnection("/control/channellist");
        while ((line = in.readLine()) != null) {
        	int blankIndex = line.indexOf(" ");
			String chanId = line.substring(0, blankIndex);
			String name = line.substring(blankIndex, line.length());
			senderList.add(new BOSender("1",chanId, name)); //TODO no Number available here
        }
        return senderList;
	}
	
	public ArrayList getSender(BOBouquet bouquet) throws IOException {
		ArrayList senderList = new ArrayList();
		String line;
	
		BufferedReader input = getConnection("/control/getbouquet?bouquet="+bouquet.getBouquetNummer()+"&mode=TV");
		
		while((line=input.readLine())!=null) {
			int firstBlank = line.indexOf(" ");
			int secondBlank = line.indexOf(" ", firstBlank+1);
			String nummer = line.substring(0, firstBlank);
			String channelId = line.substring(firstBlank+1, secondBlank);
			String name = line.substring(secondBlank+1, line.length());
			senderList.add(new BOSender(nummer,channelId, name));
		}
		return senderList;
	}	 
	
	public String zapTo(String channelId) throws IOException {
		String status = new String();
		//BufferedReader input = SerBoxControl.getConnection("/fb/switch.dbox2?zapto="+channelId);
		BufferedReader input = getConnection("/control/zapto?"+channelId);
		
		String line;
		while((line=input.readLine())!=null) {
			status = line;
		}
		return status;
	}
	
	public ArrayList getEpg(BOSender sender) throws IOException {
		ArrayList epgList=new ArrayList();
		BufferedReader input = getConnection("/control/epg?"+sender.getChanId());
		String line, eventId=new String(), startTime=new String(), duration=new String(), title=new String(),
		endTime=new String();
                Date startDate=new Date(), endDate=new Date();
		String valueStart, valueDuration;
                
		while((line=input.readLine())!=null) {
			int firstBlank = line.indexOf(" ");
			int secondBlank = line.indexOf(" ", firstBlank+1);
			int thirdBlank = line.indexOf(" ", secondBlank+1);
			eventId = line.substring(0, firstBlank);
			
                        valueStart=line.substring(firstBlank+1, secondBlank);			
                        startTime = SerFormatter.formatUnixTime(valueStart);
			startDate = SerFormatter.formatUnixDate(valueStart);                        
			
                        valueDuration=line.substring(secondBlank+1, thirdBlank);
                        duration = SerFormatter.formatedEndTime(valueDuration);
                        endTime = SerFormatter.formatUnixTime(valueStart,valueDuration);
			endDate = SerFormatter.formatUnixDate(valueStart,valueDuration);                        
                        
                        //duration = SerFormatter.formatedEndTime(line.substring(secondBlank+1, thirdBlank));
			title = line.substring(thirdBlank+1, line.length());
			epgList.add(new BOEpg(sender, eventId, startTime, startDate, endTime, endDate, duration, title));                        
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
	
	public ArrayList getTimer() throws IOException {
		ArrayList timerList = new ArrayList();
	  	BOTimer botimer = new BOTimer();
		BufferedReader input = getConnection("/control/timer");	
		String text = new String();
		String line, valueStart, valueStop, valueAnno;
		while ((line = input.readLine()) != null) {  
	        StringTokenizer st = new StringTokenizer(line);    
            botimer.setEventId(st.nextToken());
            botimer.setEventType(st.nextToken());
            botimer.setEventRepeat(st.nextToken());
            valueAnno=st.nextToken();
            botimer.setAnnounceTime(SerFormatter.formatUnixTime(valueAnno)+" "+SerFormatter.formatUnixTime(valueAnno));
		    valueStart=st.nextToken(st.nextToken());
		    botimer.setStartTime(SerFormatter.formatUnixTime(valueStart)); //start
		    botimer.setStartDate(SerFormatter.formatUnixDate(valueStart));
		    valueStop=st.nextToken(st.nextToken());
		    botimer.setStopTime(SerFormatter.formatUnixTime(valueStop)); //ende
            botimer.setSender(st.nextToken());  
            timerList.add(botimer);  
		}   	
		return timerList;
	}
	
	public String setTimer(BOTimer timer) throws IOException {
		return new String();
	}
}
