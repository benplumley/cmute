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

public class Server {
    private static int portNumber;
    private static String hostName;
    private static Server theServer;
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
    	theServer = new Server(args);
    }

    public Server(String[] args){
		try{
        	portNumber = Integer.parseInt(args[0]);
        	hostName = args[1];
        	serverSocket = new ServerSocket(portNumber);
        	
            while (true)
            {
                ServerClientThread clientThread = new ServerClientThread(serverSocket.accept());
                clientThread.start();
            }
            
        } catch (Exception e) {
        	System.err.println("args: portnumber hostname");
        	System.err.println(e.getMessage());
        } finally {
        	this.close();
        }

    }
    
    public void close(){
    	try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }


}
