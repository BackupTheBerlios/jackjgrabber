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
import control.ControlMain;
import model.BOBouquet;
import model.BOEpg;
import model.BOEpgDetails;
import model.BOSender;
import java.net.Authenticator;

import service.SerFormatter;

/**
 * @author Treito
 */
public class SerBoxControlEnigma extends SerBoxControl {
	
	public String getName() {
		return "Enigma";
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
		ArrayList senderList = new ArrayList();
		String line;
		String line2="";
		int channelStart=0;
		int channelEnd=0;
		int channelLength=0;
		ArrayList readEChannels= new ArrayList();
		ArrayList readEChannelsRef= new ArrayList();
		BufferedReader in = getConnection("/body");
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
		return status;
	}
	
	public ArrayList getEpg(BOSender sender) throws IOException {
		ArrayList epgList=new ArrayList();
		BufferedReader input = getConnection("/getcurrentepg2?ref="+sender.getChanId());
		int startEpgInfo;
		int endEpgInfo;
		Date startDate=new Date(), endDate=new Date();
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
				startTime=SerFormatter.formatUnixTime(valueStart);
				startDate =	SerFormatter.formatUnixDate(valueStart);
				startEpgInfo=endEpgInfo+10;
				endEpgInfo=line.indexOf("&descr=",startEpgInfo);
				valueDuration=line.substring(startEpgInfo,endEpgInfo);
				duration=SerFormatter.formatedEndTime(valueDuration);
				startEpgInfo=line.indexOf("<span class=\"event\">")+20;
				endEpgInfo=line.indexOf("</span>",startEpgInfo);
				endTime = SerFormatter.formatUnixTime(valueStart,valueDuration);
				endDate = SerFormatter.formatUnixDate(valueStart,valueDuration);    
				title=line.substring(startEpgInfo,endEpgInfo);
				epgList.add(new BOEpg(sender, eventId, startTime, startDate, endTime, endDate, duration, title));
			}
		}
		return epgList;
	}
	
	public BOEpgDetails getEpgDetail(BOEpg epg) throws IOException {
		BOEpgDetails epgDetail = new BOEpgDetails();
		BOSender sender=epg.getSender();
		BufferedReader input = getConnection("/getcurrentepg2?ref="+sender.getChanId());
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
	public String sendMessage(String message) throws IOException {
        throw new IOException();
	}
   
	public String shutdownBox() throws IOException {
        throw new IOException();
	}
   
	public String standbyBox(String modus) throws IOException {
        throw new IOException();
	}
	public ArrayList getTimer() throws IOException {
        throw new IOException();
	}
}
