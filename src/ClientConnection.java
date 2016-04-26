import java.io.*;
import java.net.*;

public class ClientConnection {

    private static String hostname = "localhost";
    private static int portNumber = 55511;
    private static Socket socket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static Client client;

    public ClientConnection(String hostname, int portNumber, Client client) {
        this.hostname = hostname;
        this.portNumber = portNumber;
        this.client = client;
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
            if (response.isMessage()) {
                MessageObject responseMessage = (MessageObject) response;
                handleMessage(responseMessage);
            } else { // the server returned rides
                responseRides = ((QueryResults) response).getRideArray();
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
        Object booking = new BookRide(ride.getUUID());
        try {
            out.writeObject(booking);
            ProtocolObject response = (ProtocolObject) in.readObject();
            MessageObject responseMessage = (MessageObject) response;
            if (responseMessage.isMessage()) { // the server returned an error
                if (responseMessage.getMessage() == MessageContent.RIDE_BOOKING_CONFIRMATION) {
                    return true;
                } else {
                    handleMessage(responseMessage);
                    return false;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error booking ride:");
            System.err.println(e.getMessage());
        }
        return false;
    }

    public Boolean list(Ride ride) {
        NewRide listing = new NewRide(ride);
        try {
            out.writeObject(listing);
            out.flush();
            // ProtocolObject response = (ProtocolObject) in.readObject();
            ProtocolObject response;
            MessageObject responseMessage = null;
            while((response = (ProtocolObject) in.readObject()) != null){
                responseMessage = (MessageObject) response;
                break;
            }
            if (responseMessage.isMessage()) { // the server returned an error
                System.out.println(responseMessage.getMyDescription());
                if (responseMessage.getMessage() == MessageContent.NEW_RIDE_CONFIRMATION) {
                    return true;
                } else {
                    handleMessage(responseMessage);
                    return false;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error listing ride:");
            System.err.println(e.getMessage());
        }
        return false;
    }

    private void handleMessage(MessageObject message) {
        client.handleMessage(message);
    }

}
