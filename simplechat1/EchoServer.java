// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.ArrayList;

import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  private static ArrayList<Person> people;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

  public ServerConsole serverConsole;
  
  //Instance methods ************************************************
  
  /**
   * Modified for E51 -NS
   * 
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
	  // This had to be modified to deal with user logins -NS
	  String clientLoggedIn = null;
	  try
	  {
		  //An attempt is made to see if the client has logged in yet -NS
		  clientLoggedIn = client.getInfo("clientLoggedIn").toString();

		  //If the client has logged in, but tries the login, an error should be displayed -NS
		  if(clientLoggedIn.matches("true") && msg.toString().startsWith("#login"))
		  {
			  client.sendToClient("SERVER MSG> Error: You have already logged in");
			  serverConsole.display("Message received: " + msg + " from " + client);
			  return;
		  }

	  }
	  catch(NullPointerException ex) //an exception is thrown if the user hasn't logged in yet -NS
	  {
		  if(msg.toString().startsWith("#login")) //If the message is the login command with an id, they will be logged in
		  {
			  String[] words = ((String) msg).split(" ");
			  String email = words[1];
			  String password = words[2];
			  for (Person p : people){
				  if (p.getEmail().equals(email) && p.getPassword().equals(password)){
					  client.setInfo("login id", email);
					  client.setInfo("clientLoggedIn", "true");
					  serverConsole.display(email + " has successfully logged in");
					  return;
				  }
			  }
			  serverConsole.display("Invalid email or password entered");
			  return;
		  }
		  else
		  {
			  //Client must login with their first communication
			  try {
				client.sendToClient("SERVER MSG> Error: You must log in");
				client.close();
			} catch (IOException e) {

			}
			  serverConsole.display("Message received: " + msg + " from " + client);
			  return;
		  }
	  } catch (IOException e) {

	  }	 
	  
	  String login = client.getInfo("login id").toString();
	  serverConsole.display("Message received: " + msg + " from " + login);
	  this.sendToAllClients(login + "> " + msg);	  

  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    serverConsole.display("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
	  serverConsole.display("Server has stopped listening for connections.");
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
	ArrayList<AccountType> accountTypes = new ArrayList<AccountType>();
	people = new ArrayList<Person>();
	AccountType checking = new AccountType("Checking", 1.5);
	accountTypes.add(checking);
	AccountType savings = new AccountType("Savings", 2.5);
	accountTypes.add(savings);
	Person p1 = new Person("Jack", "1234", "jack@gmail.com");
	people.add(p1);
	Person p2 = new Person("Bob", "5678", "bob@gmail.com");
	people.add(p2);
	Account jackChecking = new Account(100.50, "Jack's Checking Account", checking, p1);
	Account bobSavings = new Account(500.21, "Bob's Savings Account", savings, p2);
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    ServerConsole sc = new ServerConsole(sv); // An instance of ServerConsole is created here.  
    sv.serverConsole = sc;                    //It will handle all UI operations -NS
    
    try 
    {
      sv.listen(); //Start listening for connections
      sc.accept(); //Start the server console -NS
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
    
  }
    
  /**
   * Added for E49 -NS
   * This added method overrides the clientConnected method from abstract server
   * It prints a message whenever a client connects -NS
  */
  protected void clientConnected(ConnectionToClient client) 
  {
	  serverConsole.display("A client has connected");
  }
    
  /*
   * Added for E49 -NS
   * This added method overrides the clientDisonnected method from abstract server
   * It prints a message whenever a client disconnects by action of the server -NS
   */
  synchronized protected void clientDisconnected(ConnectionToClient client)
  {
	  String login = client.getInfo("login id").toString();	  
	  serverConsole.display(login + " has disconnected");
  }  
  
  /*
   * Added for E49 -NS
   * This added method overrides the clientException method from abstract server
   *It prints a message whenever a client disconnects -NS
   */
  synchronized protected void clientException(ConnectionToClient client, Throwable exception)
  {
	  String login = client.getInfo("login id").toString();
	  serverConsole.display(login + " has disconnected");
  }
 
  /*
   * Added for E50 (c) -NS
   * 
   * It was placed in this class since it should be separate from UI functions
   * this method handles the various commands that a user
   * can use by typing a command prefixed with "#"
   */
  public void performCommand(String comm) 
  {
	  if(comm.matches("quit"))
	  {
		  System.exit(0);
	  }
	  else if(comm.matches("stop"))
	  {
		  this.stopListening();
		  this.sendToAllClients("SERVER MSG> Server has stopped listening for connections.");
	  }
	  else if(comm.matches("close"))
	  {
		  this.sendToAllClients("SERVER MSG> Server is shutting down.");
		  this.stopListening();
		  try {
			  this.close();
		  } catch (IOException e) {

		  }
	  }
	  else if(comm.startsWith("setport "))
	  {			  
		  try {
			  int newPort = Integer.parseInt(comm.substring(8));
			  this.setPort(newPort);
			  serverConsole.display("Port set to: " + comm.substring(8));
		  } catch (Exception e)
		  {
			  serverConsole.display(comm.substring(8) + " is not a port number");
		  }
	  }
	  else if(comm.matches("start"))
	  {			  
		  try {
			  this.listen();
		  } catch (IOException e) {
		  }			 
	  }
	  else if(comm.matches("getport"))
	  {
		  serverConsole.display(Integer.toString(this.getPort()));
	  }
	  else
	  {
		  serverConsole.display(comm + " is not a valid command");
	  }
  }
	
}
//End of EchoServer class
