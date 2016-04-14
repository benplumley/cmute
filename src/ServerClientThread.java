/* Integrated Project 2015/16
 * University of Bath
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

public class ServerClientThread implements Runnable {
	
	private Socket myServerSocket;

	private ObjectInputStream in;
	private ObjectOutputStream out;


	public ServerClientThread(Socket accept) {
		myServerSocket = accept;
        try {
        	in = new ObjectInputStream(myServerSocket.getInputStream());
			out = new ObjectOutputStream(myServerSocket.getOutputStream());
		} catch (IOException e) {
            System.err.println(e.getMessage());
            this.close();
		}
	}

	public void start() {

	}

	public void run() {
		
		try {
			
			ProtocolObject input = null;
			
			while((input = (ProtocolObject) in.readObject()) != null){
				
				if(input.isMessage()){
					
					this.handleMessage((MessageObject) input);
					
				} else {
					 this.handleRequest((ClientToServer) input);
				}
				
			}
			
		} catch (ClassNotFoundException eClass) {
			this.sendErrorMessageToClient(MessageContent.GENERAL_ERROR ,"ERROR"); //TODO
			//Send error message to client. Try to handle failure more gracefully?
		} catch (IOException eIO) {
			eIO.printStackTrace();
		} finally {
			this.close(); //TODO
		}
		
	}

	public void close(){
		try {
			in.close();
			out.close();
			myServerSocket.close();
			Thread.currentThread().interrupt();
			return;
			//This should stop the current thread
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	private void handleMessage(MessageObject inMessage) {
		switch(inMessage.getMessage()){
		
		case CLIENT_QUIT:
			this.close();

		default:
			System.err.println("ServerClientThread error");
			System.err.println(inMessage.getMyDescription());
			break;
		
		}
	}
	
	private void handleRequest(ClientToServer inOb){
		
		if(inOb.isQuery()){
			
		} else {
			try {
				
				Server.processClientToServerObject(((ClientToServer) inOb));
				
				//send success message
				
			}  catch (ServerSQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.sendErrorMessageToClient(MessageContent.GENERAL_ERROR ,"ERROR");		
			} catch (Exception e) {
				this.sendErrorMessageToClient(MessageContent.GENERAL_ERROR ,e.getMessage());
			}
		}
	}
	
	private void sendErrorMessageToClient(MessageContent errorContent, String errorDescription) {
		processOutput(new MessageObject(
				errorContent, 
				errorDescription));
	}

	private void processOutput(ProtocolObject outObject){
		
	}
	
	private void sendRequestResults(Ride[] requestResult){
		try {
			out.writeObject(requestResult);
			out.flush();
		} catch (IOException e) {
			//Shit got fucked
			System.err.println(e.getMessage());
		}
	}

}
