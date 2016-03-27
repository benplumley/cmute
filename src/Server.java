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
    private static String userName;
    private static String password;
	private static String serverName;
	private static String dbms;
	
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
    	theServer = new Server(args);
    }

    public Server(String[] args){
		try{
			
        	portNumber = Integer.parseInt(args[0]);
        	//For JDBC/sql stuff
        	userName = args[1];
        	password = args[2];
        	serverSocket = new ServerSocket(portNumber);
        	
            while (true)
            {
                ServerClientThread clientThread = new ServerClientThread(serverSocket.accept());
                clientThread.start(theServer);
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
    
    public static Connection getConnection() throws SQLException {

        Connection connection = null;
        Properties connectionProperties = new Properties();
        
        connectionProperties.put("user", userName);
        connectionProperties.put("password", password);

        connection = DriverManager.getConnection(
                       "jdbc:" + dbms + "://" +
                       serverName +
                       ":" + portNumber + "/",
                       connectionProperties);

        System.out.println("Connected to database");
        return connection;
    }

    /* All SQL statements are processed here by this one method so
     * as to avoid concurrency issues. This is, admittedly, a naïve
     * implementation and admittedly would not be scalable. However
     * for now it should be functional.
     * 
     * @param   The input SQL request.
     * @return  The relevant SQL results from the DB.
     */
	public static synchronized String processSQLStatement(String sqlString) {
		//TODO review this architechture!
		return "ello";
	}


}
