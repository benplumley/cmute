import java.io.*;
import java.net.*;

public class ClientConnection {

    private static String hostname = "localhost";
    private static int portNumber = 55511;
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;

    public ClientConnection(String hostname, int portNumber) {
        this.hostname = hostname;
        this.portNumber = portNumber;
        connect();
    }

    private void connect() {
        try {
            socket = new Socket(hostname, portNumber);
            // this socket is used to connect to the server
            out = new PrintWriter(socket.getOutputStream(), true);
            // this stream is used to write messages to the server
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // this stream is used to read incoming messages from the server
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
