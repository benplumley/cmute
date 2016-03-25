import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Date;

public class Client extends JPanel implements ActionListener, Runnable {

	private static String hostname = "localhost";
	private static int portNumber = 55511;
	private Map map = new Map();
    private ClientConnection connection;
    private Boolean isToUni;
    private DateTime dateAndTime;
    private int timeTolerance;
    private MapPoint startLocation;
    private int locationTolerance;
    private Ride[] currentRides;
    private JPanel mapView;

	public static void main(String[] args) {
		Client client = new Client();
	}

	public Client() {
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run() {
		// try {
		// 	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		// 	// matches the operating system's look and feel
		// } catch (ClassNotFoundException | InstantiationException |
		// 	IllegalAccessException | UnsupportedLookAndFeelException ex) {}
		setupPanels();
		setupFrame();
		connection = new ClientConnection(hostname, portNumber);
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
		Dimension windowSize = new Dimension(800, 500);
		window.setSize(windowSize);
		window.setResizable(true);
	}

	private void setupPanels() {
		// TODO time and date picker
		// slider for time range
		// slider for start radius
		// slider for end radius
	}

	private void populateFrame(Container frame) {
		frame.setLayout(new GridBagLayout());
		mapView = new JPanel(null);
		mapView.add(map);
		GridBagConstraints layoutConstraints = new GridBagConstraints();
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridheight = 1;
		layoutConstraints.gridwidth = 2;
		layoutConstraints.fill = GridBagConstraints.HORIZONTAL;
		layoutConstraints.weightx = 0.5;
		JButton from = new JButton("FROM UNI");
		from.setBackground(new Color(198, 0, 167));
		from.setForeground(Color.WHITE);
        from.setFocusPainted(false);
		from.setRolloverEnabled(false);
		from.setBorderPainted(false);
        from.addActionListener(this);
		frame.add(from, layoutConstraints);

		layoutConstraints.gridx = 2;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridheight = 1;
		layoutConstraints.gridwidth = 2;
		layoutConstraints.fill = GridBagConstraints.HORIZONTAL;
		layoutConstraints.weightx = 0.5;
		JButton to = new JButton("TO UNI");
		to.setBackground(new Color(255, 116, 0));
		to.setForeground(Color.WHITE);
        to.setFocusPainted(false);
		to.setRolloverEnabled(false);
		to.setBorderPainted(false);
        to.addActionListener(this);
		frame.add(to, layoutConstraints);

		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.gridwidth = 4;
		frame.add(mapView, layoutConstraints);

		// layoutConstraints.gridx = 0;
		// layoutConstraints.gridy = 2;
		// layoutConstraints.gridheight = 1;
		// layoutConstraints.gridwidth = 1;
		//
		// JSpinner timeSpinner = new JSpinner( new SpinnerDateModel() );
		// JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
		// timeSpinner.setEditor(timeEditor);
		// timeSpinner.setValue(new Date()); // will only show the current time
		// frame.add(timeSpinner, layoutConstraints);
	}

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "TO UNI":
                isToUni = true;
                break;
            case "FROM UNI":
                isToUni = false;
                break;
        }
        updateRides();
    }

    private void updateRides() {
        currentRides = connection.getMatchingRides(isToUni, dateAndTime, timeTolerance, startLocation, locationTolerance);
        updateMap();
    }

    private void updateMap() {
        ImageIcon pinIcon;
        if (isToUni) {
            pinIcon = new ImageIcon("graphics/orangepin.png");
        } else {
            pinIcon = new ImageIcon("graphics/purplepin.png");
        }
        for (Ride thisRide : currentRides) {
            MapPoint rideLocation = thisRide.getLocation();
            JLabel pinLabel = new JLabel(pinIcon);
            mapView.add(pinLabel);
            pinLabel.setLocation(rideLocation.getX() - 5, rideLocation.getY() - 16);
            // TODO change 5 and 16 to constants. they are the relative location of the point of the pin to its top left corner
        }
    }

}
