
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import common.*;

/*
 * Added for E50 -NS
 * 
 * This class was added to enable input from the server side
 * It implements the ChatIF interface meaning that it must have 
 * a method that displays messages to a user 
 */

public class ServerConsole implements ChatIF {

	//there is an association with EchoServer
	public EchoServer echoServer;
	
	public ServerConsole(EchoServer es){
		
		this.echoServer = es;
	}
	
	/*
	 * This method is analogous to the the method used by the client side 
	 * to read messages from the user console
	 */
	public void accept() 
	{
		try
		{
			BufferedReader fromConsole = 
					new BufferedReader(new InputStreamReader(System.in));
			String message;

			while (true) 
			{
				message = fromConsole.readLine();
				if(message.startsWith("#"))
				{
					echoServer.performCommand(message.substring(1));
				}
				else
				{
					display("SERVER MSG> " + message);	      
					echoServer.sendToAllClients("SERVER MSG> " + message);
				}
			}
		} 
		catch (Exception ex) 
		{
			System.out.println
			("Unexpected error while reading from console!");
		}
	}
	
	/*
	 * This method is a most basic way of displaying
	 * messages to the server user console 
	 */
	public void display(String message) {
		
		System.out.println(message);		
	}

}
