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
	
    private static ServerSocket serverSocket;
    private static Connection connection;

    public static void main(String[] args) {
    	
    	initialise(args);
    	acceptClients();
    			
    }
    
    private static void initialise(String[] args){
    	//Set up the server via input args
		try{
			
        	int portNumber = Integer.parseInt(args[0]);
        	serverSocket = new ServerSocket(portNumber);
        	
        	getConnection(args, portNumber);
        
        //Error catching for server set up process
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException eArgs) {
        	System.err.println("Error: Incorrect arguments.");
        } catch (IOException eIO) {
			System.err.println("Error: IO exception occured");
			System.err.println(eIO.getMessage());
		} catch (Exception e) {
			System.err.println("Error while setting up server");
			System.err.println(e.getMessage());
		}
    }

  //Obtains a connection to the DB
    private static void getConnection(String[] args, int portNumber) {
        try {
	    	String userName   = args[1];
	    	String password   = args[2];
	    	
	        connection = null;
	        Properties connectionProperties = new Properties();
	        
	        connectionProperties.put("user", userName);
	        connectionProperties.put("password", password);
	    	
	        
	        connection = DriverManager.getConnection(
	                   "jdbc:mysql://localhost:3306/",
	                   connectionProperties);
	       

			System.out.println("Connected to database");
	        connection.setAutoCommit(false); //TODO Is necessary?
	        
	    //Error catching occurs below
		} catch (ArrayIndexOutOfBoundsException eArgs) {
			System.err.println("Error: incorrect arguments");
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			System.err.println("Error establishing DB connection");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Server.close();
		}
	}
    
    //This entire method needs to be re-worked
    private static void acceptClients(){
		// WIP WIP WIP WIP WIP WIP WIP WIP WIP WIP WIP WIP WIP WIP WIP WIP WIP WIP WIP
		//Accept new clients and create threads for them
		//TODO Rethink this entire thing tbh
		try {
			while(true){
				ServerClientThread clientThread;
				clientThread = new ServerClientThread(serverSocket.accept());
				clientThread.start();
			}
		} catch (IOException e) {
			// TODO This is not a clever way of dealing with the system tbh
			//Rethink this try/catch
			e.printStackTrace();
		} finally {
			//Close all resources
	       	Server.close();
        }
		// WIP WIP WIP WIP WIP WIP WIP WIP WIP WIP WIP WIP WIP WIP WIP WIP WIP WIP WIP
    }
	
	private static void close(){
    	try {
			serverSocket.close();
		} catch (IOException e) {
			System.err.println("Error when closing server socket.");
		} finally {
			System.exit(101);
		}
    }

	public static synchronized void processClientToServerObject(ClientToServer in) throws ServerSideException, SQLException {

		switch(in.getMyPurpose()){
		
		case QUERY:
			
		case NEW_RIDE:
		
		case RIDE_BOOKING:
	
		default:
			throw new ServerSideException(MessageContent.COMMUNICATION_ERROR, "Invalid CTS object recieved");
		}
		
		
		
		
		
		
		PreparedStatement pstmt;

		pstmt = connection.prepareStatement("UPDATE COFFEES " +
                "SET PRICE = ? " +
                "WHERE COF_NAME = ?");
		pstmt.setFloat(1, in.something());
		pstmt.setString(2, cofName);
		pstmt.executeUpdate();
    
		connection.commit();
		pstmt.close();
		
		
	}
	
	public static void viewTable(Connection con, String dbName)
		    throws SQLException {

		    Statement stmt = null;
		    String query =
		        "select COF_NAME, SUP_ID, PRICE, " +
		        "SALES, TOTAL " +
		        "from " + dbName + ".COFFEES";

		    try {
		        stmt = con.createStatement();
		        ResultSet rs = stmt.executeQuery(query);
		        while (rs.next()) {
		            String coffeeName = rs.getString("COF_NAME");
		            int supplierID = rs.getInt("SUP_ID");
		            float price = rs.getFloat("PRICE");
		            int sales = rs.getInt("SALES");
		            int total = rs.getInt("TOTAL");
		            System.out.println(coffeeName + "\t" + supplierID +
		                               "\t" + price + "\t" + sales +
		                               "\t" + total);
		        }
		    } catch (SQLException e ) {
		        JDBCTutorialUtilities.printSQLException(e);
		    } finally {
		        if (stmt != null) { stmt.close(); }
		    }
		}
	
}
