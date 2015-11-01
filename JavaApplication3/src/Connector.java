import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Vector;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.DefaultListModel;

//This class handles connection operations

public class Connector {
	
	private Socket socket = null;			//Socket for server connection
	private Scanner scanner = null;			//reads server messages
	private PrintWriter writer = null;		//send to server message
	private Session session;				//keep host port username and password
	private MessageBox box;					//keeps messages

	//Constructer
	public Connector(Session session) {
		this.session = session;
	}   
        
        //Accessor of message box variable
        public MessageBox getBox()
        {
            return this.box;
        }
	
	//Connects to the server
	//If connects returns true else false
	public boolean connect()
	{
		
		try {
			//Creating new socket
			SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			socket = (SSLSocket) socketFactory.createSocket(this.session.getServer(), this.session.getPort());
			//Initializg buffers
			writer = new PrintWriter(socket.getOutputStream(), true);
			scanner = new Scanner(this.socket.getInputStream());
			
			//Reading message that server sends
			String s;
			while(scanner.hasNextLine())	//this is infinite loop because there is no null character
			{
				s = scanner.nextLine();
				System.out.println(s);
				//To finish loop
				if(s.contains("OK Gimap ready"))
					return true;
			}
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return false;
	}
	
	//Logs in account
	//Returns true if it logs in else false
	public boolean login()
	{
		try {   //Operates login command
			writer.println(". Login " + this.session.getUsername() + " " +  this.session.getPass());
			scanner = new Scanner(this.socket.getInputStream());
			String s;
                        //taking server responses
			while(scanner.hasNextLine())
			{
				s = scanner.nextLine();
				System.out.println(s);
				if(s.contains("authenticated (Success)"))
					return true;
			}
		} catch (IOException e) {
			return false;
		}
		return false;
	}
        //Operates select inbox command
	public void selectInbox()
	{
		writer.println("tag SELECT INBOX");
		String s;
		while(scanner.hasNextLine())
		{
			s = scanner.nextLine();
			System.out.println(s);
			if(s.contains("INBOX selected"))
				break;
		}
	}
	
        //Reads inbox messages headers
	public void readInbox()
	{
            //Operates fetch command. I have  fetched just 20 message.
		writer.println("TAG FETCH 1:20 (BODY[HEADER.FIELDS (FROM SUBJECT DATE)])");
		String s;
		box = new MessageBox(); //inbox object
		int idd = 0;
                
                //Reads server response
		while(scanner.hasNextLine())
		{
			
			s = scanner.nextLine();
                        //Getting server response that includes "fetch"
			if(s.contains("FETCH"))
			{	
                            //Assigning response to Email object
				EMail head = new EMail();
				head.setMessageId(++idd);
				head.setDate(scanner.nextLine());
				head.setSubject(scanner.nextLine());
				head.setFrom(scanner.nextLine());
				box.addMessageBox(head);
			}
                        //Finishes loop
			if(s.contains("TAG OK Success"))
				break;
		}
		box.printMessageBox();
	}
	
        //Getting Email body
	public void writeMail(int MailId)
	{
		//Operating command
		writer.println("tag fetch " + MailId + " body[text]");
		String s;
		String acc = "";
                
                //Getting server response
		while(scanner.hasNextLine())
		{
			s = scanner.nextLine();
			acc += s + "\n";
			if(s.contains("tag OK Success"))
				break;
		}
		box.getMessage(MailId).setMessageBody(acc);
		System.out.println(box.getMessage(MailId).getMessageBody());
		
		
	}
	//Loging out from server
	public void logOut()
	{
		writer.println("tag logout");
		String s;
		while(scanner.hasNextLine())
		{
			s = scanner.nextLine();
			System.out.println(s);
		}
	}
	
        //Closing socket
	public void disconnect()
	{
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
