package service;
/*
SerTimerHandler.java by Geist Alexander 

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
import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.List;

import model.BOLocalTimer;
import model.BOTimer;
import model.BOTimerList;
import model.BOUdrecOptions;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import control.ControlMain;

public class SerTimerHandler {
    
    private static Document timerDocument;
    private static String timerFile = ControlMain.getSettingsPath().getWorkDirectory()+File.separator+"localTimer.xml";
    
    /**
     * @return LocalTimer
     * synchronisiere Box-Timer
     * hole den ersten faelligen Timer
     * vergleiche mit der Box-Zeit
     */
    public static BOLocalTimer getRunningLocalTimer() {
        try {
            BOTimerList timerList = ControlMain.getBoxAccess().getTimerList();
            synchroniseTimer(timerList);
            BOLocalTimer timer = timerList.getFirstLocalTimer();
            if (isValidTimer(timer)) {
                return timer;
            }
            return null;
        } catch (IOException e) {
            Logger.getLogger("SerTimerHandler").error(e.getMessage());
            return null;
        }
    }
    
    private static boolean isValidTimer(BOLocalTimer timer) {
        try {
            GregorianCalendar boxTime = ControlMain.getBoxAccess().getBoxTime();
            if (boxTime.getTimeInMillis()-timer.getStartTime()>0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
     
    /**
     * @param timerList
     * Zuordnen der Local-Timer zu den Box-Timern
     * Ist zu einem local-timer kein Box-Timer verfügbar, wird die Node geläscht
     */
    public static void synchroniseTimer(BOTimerList timerList) {
        Element root = getTimerDocument().getRootElement();
        List nodes = SerXPathHandling.getNodes("/timerList/recordTimer/startTime", getTimerDocument());
        
        for (int i = 0; i<nodes.size(); i++) { //Schleife über die Startzeit-Nodes
		    Node node = (Node) nodes.get(i);		    
		    long localTimerStart=Long.parseLong(node.getText());
		        
		    for (int i2=0; i2<timerList.getRecordTimerList().size(); i2++) { //Schleife über die Timer-Liste
		        BOTimer mainTimer=(BOTimer)timerList.getRecordTimerList().get(i2);
		        long mainTimerStart=mainTimer.getUnformattedStartTime().getTimeInMillis();
		        
		        if (mainTimerStart==localTimerStart) { //passenden BoxTimer gefunden
		            buildLocalTimer(node.getParent(), new BOLocalTimer(mainTimer));
		            break;
		        }
		        if (i2+1==timerList.getRecordTimerList().size()){
		            root.remove(node.getParent()); //kein BoxTimer zu LocalTimer vorhanden>>LocalTimer löschen    
		        }
		    }
		}
        try {
            SerXMLHandling.saveXMLFile(new File(timerFile), getTimerDocument());
        } catch (IOException e) {}
    }
    
    public static void deleteLocalTimer(BOLocalTimer timer) {
        if (timer.getTimerNode()!=null) {
            try {
                getTimerDocument().getRootElement().remove(timer.getTimerNode());
                SerXMLHandling.saveXMLFile(new File(timerFile), getTimerDocument());
            } catch (IOException e) {
                Logger.getLogger("SerTimerHandler").error(e.getMessage());
            }
        }
    }
    
    private static void saveNewTimer(BOLocalTimer timer) {
		Element root = getTimerDocument().getRootElement();
		Element recordTimer = DocumentHelper.createElement("recordTimer");
		
		recordTimer.addElement("startPX").addText(Boolean.toString(timer.isStartPX()));
		recordTimer.addElement("recordAllPids").addText(Boolean.toString(timer.isRecordAllPids()));
		recordTimer.addElement("ac3ReplaceStereo").addText(Boolean.toString(timer.isAc3ReplaceStereo()));
		recordTimer.addElement("stereoReplaceAc3").addText(Boolean.toString(timer.isStereoReplaceAc3()));
		recordTimer.addElement("shutdownAfterRecord").addText(Boolean.toString(timer.isShutdownAfterRecord()));
		recordTimer.addElement("description").addText(timer.getDescription());
		recordTimer.addElement("udrecOptions").addText(timer.getUdrecOptions().toString());
		recordTimer.addElement("savePath").addText(timer.getSavePath());
		recordTimer.addElement("jgrabberStreamType").addText(timer.getJgrabberStreamType());
		recordTimer.addElement("udrecStreamType").addText(timer.getUdrecStreamType());
		recordTimer.addElement("streamingEngine").addText(Integer.toString(timer.getStreamingEngine()));
		recordTimer.addElement("storeLogAfterRecord").addText(Boolean.toString(timer.isStoreLogAfterRecord()));
		recordTimer.addElement("storeEpg").addText(Boolean.toString(timer.isStoreEPG()));
		recordTimer.addElement("recordVtxt").addText(Boolean.toString(timer.isRecordVtxt()));
		recordTimer.addElement("stopPlaybackAtRecord").addText(Boolean.toString(timer.isStopPlaybackAtRecord()));
		recordTimer.addElement("dirPattern").addText(timer.getDirPattern());
		recordTimer.addElement("filePattern").addText(timer.getFilePattern());
		recordTimer.addElement("startTime").addText(Long.toString(timer.getStartTime()));	
		root.add(recordTimer);
		try {
            SerXMLHandling.saveXMLFile(new File(timerFile), getTimerDocument());
        } catch (IOException e) {
            Logger.getLogger("SerTimerHandler").error(e.getMessage());
        }
    }
    
    public static void editOldTimer(BOLocalTimer timer) {
        Node timerNode = timer.getTimerNode();
        timerNode.selectSingleNode("ac3ReplaceStereo").setText(Boolean.toString(timer.isAc3ReplaceStereo()));
        timerNode.selectSingleNode("description").setText(timer.getDescription());
        timerNode.selectSingleNode("dirPattern").setText(timer.getDirPattern());
        timerNode.selectSingleNode("filePattern").setText(timer.getFilePattern());
        timerNode.selectSingleNode("jgrabberStreamType").setText(timer.getJgrabberStreamType());
        timerNode.selectSingleNode("recordAllPids").setText(Boolean.toString(timer.isRecordAllPids()));
        timerNode.selectSingleNode("recordVtxt").setText(Boolean.toString(timer.isRecordVtxt()));
        timerNode.selectSingleNode("savePath").setText(timer.getSavePath());
        timerNode.selectSingleNode("shutdownAfterRecord").setText(Boolean.toString(timer.isShutdownAfterRecord()));
        timerNode.selectSingleNode("startPX").setText(Boolean.toString(timer.isStartPX()));
        timerNode.selectSingleNode("startTime").setText(Long.toString(timer.getStartTime()));
        timerNode.selectSingleNode("stereoReplaceAc3").setText(Boolean.toString(timer.isStereoReplaceAc3()));
        timerNode.selectSingleNode("stopPlaybackAtRecord").setText(Boolean.toString(timer.isStopPlaybackAtRecord()));
        timerNode.selectSingleNode("storeEpg").setText(Boolean.toString(timer.isStoreEPG()));
        timerNode.selectSingleNode("storeLogAfterRecord").setText(Boolean.toString(timer.isStoreLogAfterRecord()));
        timerNode.selectSingleNode("streamingEngine").setText(Integer.toString(timer.getStreamingEngine()));
        timerNode.selectSingleNode("udrecOptions").setText(timer.getUdrecOptions().toString());
        timerNode.selectSingleNode("udrecStreamType").setText(timer.getUdrecStreamType());
		try {
            SerXMLHandling.saveXMLFile(new File(timerFile), getTimerDocument());
        } catch (IOException e) {
            Logger.getLogger("SerTimerHandler").error(e.getMessage());    
        }
    }
    
    public static void saveTimer(BOTimer timer) {
        if (timer.getLocalTimer().getTimerNode()==null){
            saveNewTimer(timer.getLocalTimer());
        } else {
            editOldTimer(timer.getLocalTimer());
        }
    }
    
    /**
     * @param Main-Timer
     * @return Local-Timer
     * sucht den passenden Local-Timer in XML-Datenbank
     * wenn keiner gefunden Standard-Local-Timer zurückgeben
     */
    public static BOLocalTimer findLocalTimer(BOTimer timer) {
        Node timerNode = findTimerNode(timer);
        if (timerNode != null) {
            return buildLocalTimer(timerNode, new BOLocalTimer(timer));    
        } else {
            return BOLocalTimer.getDefaultLocalTimer(timer);
        }
        
    }
    
    /**
     * @param mainTimer
     * @return XML-Timer-Node
     * Sucht anhand des Start-Datums die passende XML-Node
     */
    public static Node findTimerNode(BOTimer mainTimer) {
        List nodes = SerXPathHandling.getNodes("/timerList/recordTimer/startTime", getTimerDocument());
        long mainTimerStart=mainTimer.getUnformattedStartTime().getTimeInMillis();
		for (int i = 0; i<nodes.size(); i++) {
		    Node node = (Node) nodes.get(i);		    
		    long localTimerStart=Long.parseLong(node.getText());
		    if (mainTimerStart==localTimerStart) {
		        return node.getParent();
		    }
		}
		return null;
    }
    
    public static BOLocalTimer buildLocalTimer(Node timerNode, BOLocalTimer localTimer) {
        localTimer.setAc3ReplaceStereo(timerNode.selectSingleNode("ac3ReplaceStereo").getText().equals("true"));
        localTimer.setDescription(timerNode.selectSingleNode("description").getText());
        localTimer.setDirPattern(timerNode.selectSingleNode("dirPattern").getText());
        localTimer.setFilePattern(timerNode.selectSingleNode("filePattern").getText());
        localTimer.setJgrabberStreamType(timerNode.selectSingleNode("jgrabberStreamType").getText());
        localTimer.setRecordAllPids(timerNode.selectSingleNode("recordAllPids").getText().equals("true"));
        localTimer.setRecordVtxt(timerNode.selectSingleNode("recordVtxt").getText().equals("true"));
        localTimer.setSavePath(timerNode.selectSingleNode("savePath").getText());
        localTimer.setShutdownAfterRecord(timerNode.selectSingleNode("shutdownAfterRecord").getText().equals("true"));
        localTimer.setStartPX(timerNode.selectSingleNode("startPX").getText().equals("true"));
        localTimer.setStartTime(Long.parseLong(timerNode.selectSingleNode("startTime").getText()));
        localTimer.setStereoReplaceAc3(timerNode.selectSingleNode("stereoReplaceAc3").getText().equals("true"));
        localTimer.setStopPlaybackAtRecord(timerNode.selectSingleNode("stopPlaybackAtRecord").getText().equals("true"));
        localTimer.setStoreEPG(timerNode.selectSingleNode("storeEpg").getText().equals("true"));
        localTimer.setStoreLogAfterRecord(timerNode.selectSingleNode("storeLogAfterRecord").getText().equals("true"));
        localTimer.setStreamingEngine(Integer.parseInt(timerNode.selectSingleNode("streamingEngine").getText()));
        localTimer.setUdrecOptions(new BOUdrecOptions(timerNode.selectSingleNode("udrecOptions").getText().split(" ")));
        localTimer.setUdrecStreamType(timerNode.selectSingleNode("udrecStreamType").getText());
        localTimer.setTimerNode(timerNode);
        return localTimer;
    }

    /**
     * @return Returns the timerDocument.
     * gibt das XML-Document der Localen Timer
     * erstellt ein leeres wenn noch keines vorhanden
     */
    private static Document getTimerDocument() {
        try {
            if (timerDocument==null) {;
                File listFile = new File(timerFile);
                if (!listFile.exists()) {
                    timerDocument=SerXMLHandling.createEmptyTimerFile(listFile);
                } else {
                    timerDocument=SerXMLHandling.readDocument(new File(timerFile));    
                }
            }
        } catch (DocumentException e) {
        } catch (IOException e) {} 
        return timerDocument;
    }
}
