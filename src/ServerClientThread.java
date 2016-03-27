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
	private Query inputQuery;

	private ObjectOutputStream outToClient;
	private Ride[] outputRides;


	public ServerClientThread(Socket accept) {
		myServerSocket = accept;
        try {
        	inFromClient = new ObjectInputStream(myServerSocket.getInputStream());
			outToClient = new ObjectOutputStream(myServerSocket.getOutputStream());
		} catch (IOException e) {
            System.err.println(e.getMessage());
		}
	}

	public void start() {
		//Initialise protocol here?

	}

	public void run() {
		try {
			while((inputQuery = (Query) inFromClient.readObject()) != null){ //TODO is this valid??? This might make the server close immediately after starting because that will be null when there's no client currently sending a request
				processRequest(inputQuery);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} finally {
			this.close();
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

	public void processRequest(Query query){
        String queryString = query.toSQLString();
        Ride[] result;
		// TODO JDBC stuff - run queryString and parse results into Ride array
		sendRequestResults(result);
	}

	public void sendRequestResults(Ride[] requestResult){
		try {
			outToClient.writeObject(requestResult);
			outToClient.flush();
		} catch (IOException e) {
			//Shit got fucked
			System.err.println(e.getMessage());
		}
	}


}
