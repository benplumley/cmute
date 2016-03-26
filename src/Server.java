/* Integrated project 2015/16
 * University of Bath
 * 
 * IMPORTANT JDBC RESOURCES
 * 
 * https://docs.oracle.com/javase/tutorial/jdbc/basics/connecting.html
 * http://www.cs.nott.ac.uk/~psznza/G51DBS/dbs19.pdf
 * 
 */


import java.io.*;
import java.sql.*;
import java.util.Properties;
import java.net.*;

public class Server {
    private static int portNumber;
    private static String userName;
    private static String password;
	private static String serverName;
	private static String dbms;
    private static Server theServer;
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
    	theServer = new Server(args);
    }

    public Server(String[] args){
		try{
        	portNumber = Integer.parseInt(args[0]);
        	userName = args[1]; //For JDBC/sql stuff
        	password = args[2]; //For JDBC/sql stuff
        	serverSocket = new ServerSocket(portNumber);
        	

            while (true)
            {
            	//Will pass either conn or the server to handle access to SQL db
                ServerClientThread clientThread = new ServerClientThread(serverSocket.accept());
                clientThread.start();
            }

        } catch (Exception e) {
        	System.err.println("Shit");
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
    
    public static Connection getConnection() throws SQLException {

        Connection conn = null;
        Properties connectionProps = new Properties();
        
        connectionProps.put("user", userName);
        connectionProps.put("password", password);

        conn = DriverManager.getConnection(
                       "jdbc:" + dbms + "://" +
                       serverName +
                       ":" + portNumber + "/",
                       connectionProps);

        System.out.println("Connected to database");
        return conn;
    }


}
