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

    public void main(String[] args) {
        Server theServer = new Server();
        portNumber = Integer.parseInt(args[0]);
    }
    
    public Server(){
        Thread thread = new Thread(this);
        thread.start();
    }
    
    @SuppressWarnings("resource")
	public void run(){
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            
            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                //ServerClientThread serverClientThread = new ServerClientThread(serverSocket.accept());
                //accept a connection;
                //create a thread to deal with the client;
            }
            
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
}