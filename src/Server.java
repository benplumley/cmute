/* Integrated project 2015/16
 * University of Bath
 * 
 * IMPORTANT JDBC RESOURCES
 * 
 * https://docs.oracle.com/javase/tutorial/jdbc/basics/connecting.html
 * http://www.cs.nott.ac.uk/~psznza/G51DBS/dbs19.pdf
 */


import java.io.*;
import java.sql.*;
import java.util.Properties;
import java.net.*;

public class Server {
    public static Server theServer;

    private static int portNumber;
	private static String serverName;
	private static String dbms;
	
    private static ServerSocket serverSocket;
    private static Connection connection;

    public static void main(String[] args) {
    	theServer = new Server(args);
    }

    public Server(String[] args){
		try{
			
        	portNumber = Integer.parseInt(args[0]);
        	serverSocket = new ServerSocket(portNumber);
        	
        	getConnection(args[1], args[2]);
        	
            while (true)
            {
                ServerClientThread clientThread = new ServerClientThread(serverSocket.accept());
                clientThread.start();
            }

        } catch (Exception e) {
        	System.err.println("Shit");
        	System.err.println(e.getMessage());
        } finally {
        	Server.close();
        }

    }

    private static void close(){
    	try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void getConnection(String userName, String password){

        connection = null;
        Properties connectionProperties = new Properties();
        
        connectionProperties.put("user", userName);
        connectionProperties.put("password", password);

        try {
			connection = DriverManager.getConnection(
			               "jdbc:" + dbms + "://" +
			               serverName +
			               ":" + portNumber + "/",
			               connectionProperties);
			
			System.out.println("Connected to database");
	        connection.setAutoCommit(false);
	        
		} catch (SQLException e) {
			// TODO Connection not established
			e.printStackTrace();
		}
    }

    /* 
     * TODO May be necessary to seperate addding rides and getting rides. Review this decision
     * 
     * All SQL statements are processed here by this one method so
     * as to avoid concurrency issues. This is, admittedly, a naïve
     * implementation and admittedly would not be scalable. However
     * for now it should be functional.
     * 
     * @param   The input SQL request.
     * @return  The relevant SQL results from the DB.
     */
	public static synchronized String processSQLStatement(String sqlString) throws SQLException {
		//TODO yeah this is gonna need a lot of work
		try {
			connection.nativeSQL(sqlString);
	    } catch (SQLException e ) {
	    	e.printStackTrace();
	    }
		
		return "Hello";
	}
	
}
