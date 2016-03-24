import java.io.*;
import java.net.*;
import java.sql.Date;

public class ClientConnection {

    private static String hostname = "localhost";
    private static int portNumber = 55511;
    private static Socket socket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;

    public ClientConnection(String hostname, int portNumber) {
        this.hostname = hostname;
        this.portNumber = portNumber;
        connect();
    }

    private void connect() {
        try {
            socket = new Socket(hostname, portNumber);
            // this socket is used to connect to the server
            out = new ObjectOutputStream(socket.getOutputStream());
            // this stream is used to write messages to the server
            in = new ObjectInputStream(socket.getInputStream());
            // this stream is used to read incoming messages from the server
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public Ride[] getMatchingRides(Boolean isToUni, Date dateAndTime, int timeTolerance, MapPoint startLocation, int locationTolerance) {
        Object rideQuery = new Query(isToUni, dateAndTime, timeTolerance, startLocation, locationTolerance);
        try {
            out.writeObject(rideQuery);
        } catch (IOException e) {
            System.err.println("Could not send query to server:");
            System.err.println(e.getMessage());

        }

        return null;
    }

}