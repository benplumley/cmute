import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Date;

public class Client extends JPanel implements ActionListener, MouseListener, Runnable {

	private static String hostname = "localhost";
	private static int portNumber = 55511;
	private Map map = new Map();
    // private ClientConnection connection;
    private FakeServer connection; // TODO for debugging
    private Boolean isToUni;
    private DateTime dateAndTime;
    private int timeTolerance;
    private Ride[] currentRides;
    private JLayeredPane mapView;

	public static void main(String[] args) {
		Client client = new Client();
	}

	public Client() {
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run() {
		setupPanels();
		setupFrame();
		// connection = new ClientConnection(hostname, portNumber);
		connection = new FakeServer(); // TODO for debugging
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
		Dimension windowSize = new Dimension(816, 537);
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
		mapView = new JLayeredPane();
        mapView.setPreferredSize(new Dimension(800,450));
        map.setBounds(0,0,800,450);
        map.setPreferredSize(new Dimension(800,450));
		mapView.add(map, new Integer(0));
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

        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// sets the LnF after creating the to/from buttons, which have their own LnF
		} catch (ClassNotFoundException | InstantiationException |
			IllegalAccessException | UnsupportedLookAndFeelException ex) {}

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 2;
        layoutConstraints.gridheight = 1;
        layoutConstraints.gridwidth = 1;
		JSpinner dateSpinner = new JSpinner( new SpinnerDateModel() );
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
		dateSpinner.setEditor(dateEditor);
		dateSpinner.setValue(new Date()); // will only show the current date
		frame.add(dateSpinner, layoutConstraints);

        layoutConstraints.gridx = 2;
        layoutConstraints.gridy = 2;
        layoutConstraints.gridheight = 1;
        layoutConstraints.gridwidth = 1;
		JSpinner timeSpinner = new JSpinner( new SpinnerDateModel() );
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
		timeSpinner.setEditor(timeEditor);
		timeSpinner.setValue(new Date()); // will only show the current time
		frame.add(timeSpinner, layoutConstraints);
	}

    public void actionPerformed(ActionEvent e) {
        String actionPerformed = e.getActionCommand();
        switch (actionPerformed) {
            case "TO UNI":
                isToUni = true;
                break;
            case "FROM UNI":
                isToUni = false;
                break;
        }
        updateRides();
    }

    public void mouseClicked(MouseEvent e) {
        Ride rideSelected = ((Pin) e.getSource()).getRide();
        // get the ride associated with the pin the user clicked
        book(rideSelected);
    }

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    private void updateRides() {
        currentRides = connection.getMatchingRides(isToUni, dateAndTime, timeTolerance);
        updateMap();
    }

    private void updateMap() {
        for (Component oldPin : mapView.getComponentsInLayer(1)) {
            mapView.remove(mapView.getIndexOf(oldPin));
        }
        mapView.repaint();
        for (Ride thisRide : currentRides) {
            MapPoint rideLocation = thisRide.getLocation();
            Pin pin = new Pin(thisRide);
            pin.addMouseListener(this);
            mapView.add(pin, new Integer(1));
            pin.setBounds(rideLocation.getX() - 5, rideLocation.getY() - 16, 11, 18);
            // TODO change 5 and 16 to constants. they are the relative location of the point of the pin to its top left corner. 11 and 18 are the dimensions of a pin
        }
    }

    private void book(Ride ride) {
        Object[] options = {"Book this ride!", "Cancel"};
        int confirmed = JOptionPane.showOptionDialog(this,
            ride.getReadableDescription(),
            "Confirm Booking",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        Boolean successful = false;
        if (confirmed == JOptionPane.YES_OPTION) {
            successful = connection.book(ride);
            if (successful) {
                JOptionPane.showMessageDialog(this, "Your booking was successful.");
            } else {
                JOptionPane.showMessageDialog(this, "Your booking failed.");
            }
        }
    }

}
