/* Integrated project 2015/16
 * University of Bath
 *
 * Okay I tried to create the foundations of the server class
 * while emulating the the client class.
 */
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.*;
import java.net.*;

@SuppressWarnings("unused")
public class Server implements Runnable {
    private int portNumber;
    private String hostName;

    public static void main(String[] args) {
        Server theServer = new Server(args);
		// start server with Server portnumber hostname
		
        // try{
        // 	portNumber = Integer.parseInt(args[0]);
        // 	hostName = args[1];
        // } catch (Exception e) {
        // 	System.err.println("M8 u messed up the args");
        // 	System.err.println(e.getMessage());
        // }
    }

    public Server(String[] args){
		try{
        	portNumber = Integer.parseInt(args[0]);
        	hostName = args[1];
        } catch (Exception e) {
        	System.err.println("M8 u messed up the args");
        	System.err.println(e.getMessage());
        }
        Thread thread = new Thread(this);
        thread.start();
    }

    @SuppressWarnings("resource")
	public void run(){
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);

            while (true)
            {
                ServerClientThread clientThread = new ServerClientThread(serverSocket.accept());
                clientThread.start();
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
