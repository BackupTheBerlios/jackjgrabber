/*
 * Created on 24.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package boxConnection;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.io.IOException;
import java.lang.InterruptedException;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.log4j.Logger;

import control.ControlMain;
/**
 * @author ralix
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class SerBoxTelnet  {	
	
	static TelnetClient telnet = new TelnetClient();

	private static void createTelnetSession(String command) throws IOException, InterruptedException{		 	    
		telnet.connect(ControlMain.getActiveBox().getDboxIp());
		InputStream istream = telnet.getInputStream();
	    OutputStream ostream = telnet.getOutputStream();
	    Reader reader = new InputStreamReader( istream );
	    Writer writer = new OutputStreamWriter( ostream );
	    writer.write( ControlMain.getActiveBox().getLogin() + "\n" );
        writer.flush();
        Thread.sleep(1000);	        
        writer.write( ControlMain.getActiveBox().getPassword() + "\n" );
        writer.flush();
        Thread.sleep(1000);	 
        writer.write( command +"\n" );
        writer.flush();
        closeTelnetSession();        
	}
	private static void closeTelnetSession() throws IOException, InterruptedException{
		Thread.sleep(2000); 
		telnet.disconnect();		 
	}
	public static void runNhttpdReset() throws IOException, InterruptedException{
		Logger.getLogger("SerBoxTelnet").info(ControlMain.getProperty("msg_nhttpd"));
		createTelnetSession("killall nhttpd && nhttpd");						            
	}
	public static void runSectiondReset() throws IOException, InterruptedException{
		Logger.getLogger("SerBoxTelnet").info(ControlMain.getProperty("msg_sectiond"));
		createTelnetSession("killall sectionsd && sectionsd");						            
	}
	/*
	public static void runReboot() throws IOException, InterruptedException{	
		Logger.getLogger("SerBoxTelnet").info(ControlMain.getProperty("msg_reboot"));
		createTelnetSession("reboot"); //FIXME reboot einfügen
		//createTelnetSession("reboot");
	}
	*/
	public static void runReboot() throws IOException, InterruptedException{	
		Logger.getLogger("SerBoxTelnet").info(ControlMain.getProperty("msg_reboot"));		
		telnet.connect(ControlMain.getActiveBox().getDboxIp());
		InputStream istream = telnet.getInputStream();
	    OutputStream ostream = telnet.getOutputStream();
	    Reader reader = new InputStreamReader( istream );
	    Writer writer = new OutputStreamWriter( ostream );
	    writer.write( ControlMain.getActiveBox().getLogin() + "\n" );
        writer.flush();
        Thread.sleep(1000);	        
        writer.write( ControlMain.getActiveBox().getPassword() + "\n" );
        writer.flush();
        Thread.sleep(1000);	 
        writer.write("killall sectionsd"+"\n" );
        writer.flush();
        Thread.sleep(1000);	 
        writer.write("killall camd2"+"\n" );
        writer.flush();
        Thread.sleep(1000);	 
        writer.write("killall timerd"+"\n" );
        writer.flush();
        Thread.sleep(1000);	 
        writer.write("killall timerd"+"\n" );
        writer.flush();
        Thread.sleep(1000);	 
        writer.write("killall zapit"+"\n" );
        writer.flush();
        Thread.sleep(1000);	 
        writer.write("killall controld"+"\n" );
        writer.flush();
        Thread.sleep(1000);	 
        writer.write("killall nhttpd"+"\n" );
        writer.flush();
        Thread.sleep(1000);	 
        writer.write("sleep 3"+"\n" );
        writer.flush();
        Thread.sleep(1000);	 
        writer.write("busybox -reboot" +"\n" );
        writer.flush();
        Thread.sleep(1000);	 
        closeTelnetSession();      
	}
}