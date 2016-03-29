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
		}
	}

	public void start() {
		//Initialise protocol here?

	}

	public void run() {
		try {
			@SuppressWarnings("unused")
			Object input = null;
			while((input = (Query) in.readObject()) != null){
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
	
	private void processInput(Object inObject){
		
	}
	
	private void processOutput(Object outObject){
		
	}
	
	private void processRequest(Query query){
//        String queryString = query.toSQLString();
        try {
			Server.processSQLStatement(query.toSQLString());
		} catch (SQLException e) {
			// TODO HOW TO DEAL WITH THIS??????????
			e.printStackTrace();
		}

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
