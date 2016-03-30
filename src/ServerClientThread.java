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
		//Initialise protocol here?

	}

	public void run() {
		try {
			ProtocolObject input = null;
			while((input = (ProtocolObject) in.readObject()) != null){
				this.processInput(input); //Surround with try catcth?
				/*
				 * TODO is this valid???
				 * This might make the server close immediately after starting
				 * because that will be null when there's no client currently sending a request
				 * 
				 * Who knows???
				 */
				
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			//Send error message to client. Try to handle failure more gracefully.
		} finally {
			this.close();
		}
	}

	public void close(){
		try {
			in.close();
			out.close();
			myServerSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void processInput(ProtocolObject inObject){
		if(inObject.isMessage()){
			this.handleMessage((MessageObject) inObject);
		} else {
			//probably just send SQL stuff to be processed
		}
	}
	
	private void handleMessage(MessageObject inObject) {
		// TODO Auto-generated method stub
		
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
