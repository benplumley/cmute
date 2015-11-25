import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client extends JPanel implements Runnable {

	private static String hostname = "localhost";
	private static int portNumber = 55511;
	private static Socket socket;
	private static PrintWriter out;
	private static BufferedReader in;
	private Map map = new Map();

	public static void main(String[] args) {
		Client client = new Client();
	}

	public Client() {
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// matches the operating system's look and feel
		} catch (ClassNotFoundException | InstantiationException |
			IllegalAccessException | UnsupportedLookAndFeelException ex) {}
		setupPanels();
		setupFrame();
		connect();
	}

	private void setupFrame() {
		JFrame window = new JFrame("Car Sharing");
		window.setIconImage(Toolkit.getDefaultToolkit()
			.getImage("graphics/icon.png"));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		populateFrame(window.getContentPane());
		window.pack();
		window.setLocationByPlatform(true);
		window.setVisible(true);
		Dimension windowSize = new Dimension(400, 200);
		window.setSize(windowSize);
		window.setResizable(true);
	}

	private void setupPanels() {

	}

	private void populateFrame(Container frame) {
		frame.setLayout(new GridBagLayout());
		JPanel mapView = new JPanel();
		mapView.add(map);
		GridBagConstraints layoutConstraints = new GridBagConstraints();
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridheight = 1;
		layoutConstraints.gridwidth = 4;
		frame.add(mapView, layoutConstraints);
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.gridwidth = 1;
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
