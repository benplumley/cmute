/* Integrated Project 2015/16
 * University of Bath
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
					
					//A message has been recieved
					//Probably (hopefully) just notification that client is quitting
					this.handleMessage((MessageObject) input);
					
				} else {
					//A request of some form has been made handle it
					this.handleCTSObject((ClientToServer) input);
				}
				
			}
			
		} catch (ClassNotFoundException eClass) {
			this.sendMessageToClient(MessageContent.COMMUNICATION_ERROR, "Invalid object recieved");
			//Send error message to client. Just in case.
		} catch (IOException eIO) {
			eIO.printStackTrace();
		} finally {
			this.close();
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
			sendMessageToClient(MessageContent.COMMUNICATION_ERROR, "You sent a bad message???");
			System.err.println("ServerClientThread error");
			System.err.println(inMessage.getMyDescription());
			break;
		
		}
	}
	
	private void handleCTSObject(ClientToServer inOb){
		
		try {
			
			//Queries are handled differently because server has to return object
			if(inOb.getMyPurpose().equals(ClientToServerPurpose.QUERY)){
				
				//Get the results and send them!
				sendOutput(Server.getQueryResults((Query) inOb));
				
			} else {
				
				//Try to update the server
				Server.processClientToServerObject(inOb);
				
				//If an error has not been thrown then send success message
				switch(inOb.getMyPurpose()){
					case NEW_RIDE:
						sendMessageToClient(MessageContent.NEW_RIDE_CONFIRMATION, 
								"Ride succesfully posted.");
						break;
						
					case RIDE_BOOKING:
						sendMessageToClient(MessageContent.RIDE_BOOKING_CONFIRMATION, 
								"Ride succesfully booked.");
						break;
						
					default:
						//lol this should not happen
						break;
				}
			}
			
		} catch (ServerSideException e) {
			sendOutput(e.getMessageObject()); //A failure has occured, send info to client.
		}
		
	}


	private void sendMessageToClient(MessageContent errorContent, String errorDescription) {
		sendOutput(new MessageObject(
				errorContent, 
				errorDescription));
	}

	private void sendOutput(ProtocolObject objectToSend) {
		//TODO REVIEW THIS
		try {
			out.writeObject(objectToSend);
			out.flush();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
	}

}
