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

  //Obtains a connection to the DB
    private static void getConnection(String[] args, int portNumber) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

	    	String userName   = args[1];
	    	String password   = args[2];

	        connection = null;
	        Properties connectionProperties = new Properties();

	        connectionProperties.put("user", userName);
	        connectionProperties.put("password", password);

	        connection = DriverManager.getConnection(
	                   "jdbc:mysql://mysql5host.bath.ac.uk/cm20215db15_068",
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
		}
	}

    private static void acceptClients(){
        System.out.println("Accepting clients");
		try {
			while(true){
				ServerClientThread clientThread;
				clientThread = new ServerClientThread(serverSocket.accept());
				clientThread.start();
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

		case RIDE_BOOKING:

            int bookingUUID = ((BookRide) in).getUUID();
            int currentSeatsRemaining = getSeatsRemaining(bookingUUID);

            if (currentSeatsRemaining > 0) {
                String bookingString =
                "UPDATE rides_bjp36" +
                " SET seats_remaining=" + (currentSeatsRemaining - 1) +
                " WHERE UUID=" + bookingUUID;
    			try {
    				pstmt = connection.prepareStatement(bookingString);
    				pstmt.executeUpdate();
    				connection.commit();
    				pstmt.close();
    			} catch (SQLException e) {
                    e.printStackTrace();
    				throw new ServerSideException(MessageContent.RIDE_BOOKING_FAILURE, "Unable to book ride");
    			}
            } else {
                throw new ServerSideException(MessageContent.RIDE_BOOKING_FAILURE, "No seats remaining");
            }
			break;

		case NEW_RIDE:
            Ride listing = ((NewRide) in).getRide();

            String listingString =
            "INSERT INTO rides_bjp36" +
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
				pstmt = connection.prepareStatement(listingString);
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
		Statement stmt = null;

		try {

		    String queryString =
			"SELECT * FROM rides_bjp36 WHERE" +
	        " is_to_uni = " + query.isToUni() +
	        " AND date_and_time >= " + query.getStartTime() +
	        " AND date_and_time <= " + query.getEndTime() +
	        " AND seats_remaining > 0";

	        stmt = connection.createStatement();


	        ResultSet rs = stmt.executeQuery(queryString);

	        if(!rs.isBeforeFirst()){
	        	//Empty result set
	        	throw new ServerSideException(MessageContent.QUERY_FAILURE, "No rides at this time");
	        }

	        return new QueryResults(rs);

	    } catch (SQLException e ) {
	    	System.out.println(e.getMessage());
            e.printStackTrace();
	       throw new ServerSideException(MessageContent.QUERY_FAILURE, "Query could not be successfully executed");
	    } finally {
	        if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} }
	    }
	}

    private static int getSeatsRemaining(int id) throws ServerSideException {
        try {
		    Statement stmt = null;
		    String queryString =
			"SELECT seats_remaining FROM rides_bjp36 WHERE" +
	        " UUID = " + id + ";";
	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(queryString);
            rs.next();
	        return rs.getInt("seats_remaining");
	    } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new ServerSideException(MessageContent.QUERY_FAILURE, "Query could not be successfully executed");
	    }
    }

}
