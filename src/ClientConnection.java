import java.io.*;
import java.net.*;

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

    public Ride[] getMatchingRides(Boolean isToUni, DateTime dateAndTime, int timeTolerance) {
        Object rideQuery = new Query(isToUni, dateAndTime, timeTolerance);
        Ride[] responseRides = null;
        try {
            out.writeObject(rideQuery);
            ProtocolObject response = (ProtocolObject) in.readObject();
            if (response.isMessage()) { // the server returned an error
                handleMessage((MessageObject) response);
            } else { // the server returned rides
                responseRides = ((RideCollectionResults) response).getRides();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error requesting matching rides:");
            System.err.println(e.getMessage());
        }
        return responseRides;
    }

    public void close() {
		try {
            out.writeObject(new MessageObject(MessageContent.CLIENT_QUIT, "Connection closed by client."));
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public Boolean book(Ride ride) {
        // TODO send a request to book this ride
        return false;
    }

    public Boolean list(Ride ride) {
        // TODO
        return false;
    }

    private void handleMessage(MessageObject message) {
        // TODO
    }

}
