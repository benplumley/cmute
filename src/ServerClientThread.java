/* Integrated Project 2015/16
 * University of Bath
 *
 * This class is supposed to present a user on the server
 * side of things.
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerClientThread implements Runnable {
	private Socket myServerSocket;

	private ObjectInputStream inFromClient;
//	private ToServerObject readObject;

	private ObjectOutputStream outToClient;
//	private ToClientObject writeObject;


	public ServerClientThread(Socket accept) {
		myServerSocket = accept;
        try {
        	inFromClient = new ObjectInputStream(myServerSocket.getInputStream());
			outToClient = new ObjectOutputStream(myServerSocket.getOutputStream());
		} catch (IOException e) {
            System.err.println(e.getMessage());
		} finally {
			this.close();
		}
	}

	public void start() {
		//Initialise protocol here!

	}

	public void run() {
		Object inputObject; //Change this !!! TODO
		try {
			while((inputObject = inFromClient.readObject()) != null){
//				inputObject.dealwithstuff
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void close(){
		try {
			inFromClient.close();
			outToClient.close();
			myServerSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void processRequest(String request){
		//Create protocol communication class?
	}

	public void sendRequestResults(String requestResult){
		//TODO
		try {
			outToClient.writeUTF(requestResult);
			outToClient.flush();
		} catch (IOException e) {
			//Shit got fucked
			System.err.println(e.getMessage());
		}
	}


}
