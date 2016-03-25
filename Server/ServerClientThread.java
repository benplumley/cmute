/* Integrated Project 2015/16
 * University of Bath
 *
 * This class is supposed to present a user on the server
 * side of things.
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerClientThread implements Runnable {
	private Socket myServerSocket;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;

	public ServerClientThread(Socket accept) {
		myServerSocket = accept;
        try {
        	inFromClient = new BufferedReader(new InputStreamReader(myServerSocket.getInputStream()));
			outToClient = new DataOutputStream(myServerSocket.getOutputStream());
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
		// TODO
		
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