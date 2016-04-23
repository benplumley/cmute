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

			System.out.println("Initialising server");
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

  //Obtains a connection to the DB TODO REVIEW THIS
    private static void getConnection(String[] args, int portNumber) {
        try {
	    	String userName   = args[1];
	    	String password   = args[2];

	        connection = null;
	        Properties connectionProperties = new Properties();

	        connectionProperties.put("user", userName);
	        connectionProperties.put("password", password);

	        connection = DriverManager.getConnection(
	                   "jdbc:mysql://localhost:3306/", //Port number?
	                   connectionProperties);


			System.out.println("Connected to database");
	        connection.setAutoCommit(false);

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

    private static void acceptClients(){

		try {
			while(true){
				ServerClientThread clientThread;
				clientThread = new ServerClientThread(serverSocket.accept());
				clientThread.start();
				System.out.println("New client accepted");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//Close all resources
	       	Server.close();
        }
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

	public static synchronized void processClientToServerObject(ClientToServer in) throws ServerSideException{

		PreparedStatement pstmt;

		switch(in.getMyPurpose()){

		case RIDE_BOOKING: //TODO SQL interaction for ride booking
            int bookingUUID = ((BookRide) in).getUUID();
            int currentSeatsRemaining = getSeatsRemaining(bookingUUID);
            String bookingString =
            "UPDATE rides" +
            "SET seats_remaining=(" + (currentSeatsRemaining - 1) + ")" +
            "WHERE UUID=" + bookingUUID;
			try {
				pstmt = connection.prepareStatement(
					"UPDATE COFFEES " +
				    "SET PRICE = ? " +
				    "WHERE COF_NAME = ?");
				pstmt.executeUpdate();
				connection.commit();
				pstmt.close();
			} catch (SQLException e) {
				throw new ServerSideException(MessageContent.RIDE_BOOKING_FAILURE, "Unable to book ride");
			}
			break;

		case NEW_RIDE: //TODO SQL interaction for ride listing. Are the values in the right format, eg how does JDBC expect a Boolean?
            Ride listing = ((NewRide) in).getRide();
            String listingString =
            "INSERT INTO rides" +
                "(map_point_x," +
                "map_point_y," +
                "date_and_time," +
                "region," +
                "seats_remaining," +
                "is_to_uni," +
                "repeating_days)" +
            "VALUES" +
                "(" + listing.getLocation().getX() + "," +
                listing.getLocation().getY() + "," +
                listing.getDateTime().getDateTime() + ",1," +
                listing.getSeatsRemaining() + "," +
                listing.getIsToUni() + "," +
                listing.getRepeatingDays() + ")";
			try {
				pstmt = connection.prepareStatement(
					"INSERT INTO _________ " +
				    "VAL PRICE = ? " +
				    "WHERE COF_NAME = ?");
				pstmt.executeUpdate();
				connection.commit();
				pstmt.close();
			} catch (SQLException e) {
				throw new ServerSideException(MessageContent.NEW_RIDE_FAILURE, "Unable to post ride");
			}
			break;

		default:
			throw new ServerSideException(MessageContent.COMMUNICATION_ERROR, "Invalid CTS object recieved");
		}


	}

	public static QueryResults getQueryResults(Query query) throws ServerSideException {
	    try {

		    Statement stmt = null;
		    String queryString =
			"SELECT * FROM rides WHERE" +
	        " is_to_uni = " + query.isToUni() +
	        " AND date_and_time >= " + query.getStartTime() +
	        " AND date_and_time <= " + query.getEndTime() +
	        " AND seats_remaining > 0";

	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(queryString);

	        if (stmt != null) { stmt.close(); }

	        return new QueryResults(rs);
	    } catch (SQLException e ) {
	       throw new ServerSideException(MessageContent.QUERY_FAILURE, "Query could not be successfully executed");
	    }
	}

    private static int getSeatsRemaining(int id) {
        //TODO write a method to get the number of seats remaining in the ride with the given id. This can't be passed straight from client because they might have downloaded their data half an hour ago, and the seats remaining could have changed since.
        return 1;
    }

}
