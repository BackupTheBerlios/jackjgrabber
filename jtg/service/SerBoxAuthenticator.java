/*
 * Created on 06.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package service;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * @author Treito
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SerBoxAuthenticator extends Authenticator
{
	protected PasswordAuthentication getPasswordAuthentication()
	{
			String UserName="root";
			String Password="dbox2";
			return new PasswordAuthentication(UserName, Password.toCharArray());
		
	}
}
