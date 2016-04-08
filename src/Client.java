import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import java.net.*;
import java.util.Date;

public class Client extends JPanel implements ActionListener, MouseListener,
    ChangeListener, Runnable {

    private static final int MS_IN_DAY = (24 * 60 * 60 * 1000);
    private static final int TOLERANCE_MIN = 0;
    private static final int TOLERANCE_MAX = 60;
    private static final int TOLERANCE_INIT = 5;

	private static String hostname = "localhost";
	private static int portNumber = 55511;
	private Map map = new Map();
    // private ClientConnection connection;
    private FakeServer connection; // TODO for debugging
    private Boolean isToUni = true;
    private Boolean newListing = false;
    private DateTime dateAndTime = new DateTime((new Date()).getTime());
    private int timeTolerance = TOLERANCE_INIT;
    private Ride[] currentRides;
    private int newSeatCount = 3;
    private int repeatingDays = 0;

    private JLayeredPane mapView;
    private JSpinner dateSpinner;
    private JButton newListingButton;
    private JButton repeatingDaysButton;
    private JComboBox seatCountBox;
    private JSlider toleranceSlider;
    private JLabel toleranceLabel;

	public static void main(String[] args) {
		Client client = new Client();
	}

	public Client() {
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run() {
		setupFrame();
		// connection = new ClientConnection(hostname, portNumber);
		connection = new FakeServer(); // TODO for debugging
        updateRides();
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
		Dimension windowSize = new Dimension(816, 543);
		window.setSize(windowSize);
		window.setResizable(true);
	}

	private void populateFrame(Container frame) {
		frame.setLayout(new GridBagLayout());
		mapView = new JLayeredPane();
        mapView.setPreferredSize(new Dimension(800,450));
        map.setBounds(0,0,800,450);
        map.setPreferredSize(new Dimension(800,450));
		mapView.add(map, new Integer(0));
        mapView.addMouseListener(this);
        JPanel topButtons = new JPanel(new GridLayout(1, 2));
        JButton from = new JButton("FROM UNI");
		from.setBackground(new Color(198, 0, 167));
		from.setForeground(Color.WHITE);
        from.setFocusPainted(false);
		from.setRolloverEnabled(false);
		from.setBorderPainted(false);
        from.addActionListener(this);
        JButton to = new JButton("TO UNI");
        to.setBackground(new Color(255, 116, 0));
        to.setForeground(Color.WHITE);
        to.setFocusPainted(false);
        to.setRolloverEnabled(false);
        to.setBorderPainted(false);
        to.addActionListener(this);
        topButtons.add(from);
        topButtons.add(to);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // sets the LnF after creating the to/from buttons, which have their own LnF
        } catch (ClassNotFoundException | InstantiationException |
            IllegalAccessException | UnsupportedLookAndFeelException ex) {}
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy HH:mm");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setValue(new Date()); // will show the current date and time
        dateSpinner.addChangeListener(this);
        toleranceSlider = new JSlider(JSlider.HORIZONTAL, TOLERANCE_MIN, TOLERANCE_MAX, TOLERANCE_INIT);
        toleranceSlider.addChangeListener(this);
        toleranceLabel = new JLabel("\u00B1" + timeTolerance + "m");
        newListingButton = new JButton("New Listing");
        newListingButton.addActionListener(this);

		GridBagConstraints layoutConstraints = new GridBagConstraints();
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridheight = 1;
		layoutConstraints.gridwidth = 4;
		layoutConstraints.fill = GridBagConstraints.HORIZONTAL;
		layoutConstraints.weightx = 0;
        frame.add(topButtons, layoutConstraints);
		layoutConstraints.gridy = 1;
		frame.add(mapView, layoutConstraints);
        layoutConstraints.gridy = 2;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.weightx = 0.5;
		frame.add(dateSpinner, layoutConstraints);
        layoutConstraints.gridx = 1;
        layoutConstraints.weightx = 0.25;
		frame.add(toleranceSlider, layoutConstraints);
        layoutConstraints.gridx = 2;
        frame.add(toleranceLabel, layoutConstraints);
        layoutConstraints.gridx = 3;
        frame.add(newListingButton, layoutConstraints);
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
            case "New Listing":
                newListing = true;
                createNewListing();
                break;
            case "Cancel":
                newListing = false;
                cancelNewListing();
                break;
        }
        updateRides();
    }

    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();
        if (source instanceof Pin) {
            Ride rideSelected = ((Pin) e.getSource()).getRide();
            // get the ride associated with the pin the user clicked
            book(rideSelected);
        } else if ((source instanceof JLayeredPane) && newListing) {
            MapPoint location = new MapPoint(e.getX(), e.getY());
            locationChosen(location);
        }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void stateChanged(ChangeEvent e) {
        timeTolerance = (int)toleranceSlider.getValue();
        toleranceLabel.setText("\u00B1" + timeTolerance + "m");
        if (!toleranceSlider.getValueIsAdjusting()) {
            SpinnerModel dateModel = dateSpinner.getModel();
            updateDateTime(((SpinnerDateModel)dateModel).getDate());
            updateRides();
        }
    }

    private void updateRides() {
        if (!newListing) {
            currentRides = connection.getMatchingRides(isToUni, dateAndTime, timeTolerance);
            updateMap();
        }
    }

    private void updateMap() {
        for (Component oldPin : mapView.getComponentsInLayer(1)) {
            mapView.remove(mapView.getIndexOf(oldPin)); // remove all old pins from the panel
        }
        mapView.repaint();
        for (Ride thisRide : currentRides) {
            MapPoint rideLocation = thisRide.getLocation();
            Pin pin = new Pin(thisRide);
            pin.addMouseListener(this);
            mapView.add(pin, new Integer(1));
            pin.setBounds(rideLocation.getX() - Pin.OFFSETX, rideLocation.getY() - Pin.OFFSETY, Pin.WIDTH, Pin.HEIGHT);
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

    private void updateDateTime(Date date) {
        long epochDate = date.getTime();
        dateAndTime = new DateTime(epochDate);
    }

    private void createNewListing() {
        toleranceSlider.setEnabled(false);
        currentRides = new Ride[0];
        updateMap();
        newListingButton.setText("Cancel");
    }

    private void cancelNewListing() {
        toleranceSlider.setEnabled(true);
        newListingButton.setText("New Listing");
    }

    private void locationChosen(MapPoint location) {
        Ride newRide = new Ride(isToUni, location, dateAndTime, 0, 5, 5);
        // TODO allow the user to change repeats and number of seats
        currentRides = new Ride[] {newRide};
        updateMap();
        confirmCreate(newRide);
    }

    private void confirmCreate(Ride ride) {
        Object[] options = {"Create this listing!", "Cancel"};
        int confirmed = JOptionPane.showOptionDialog(this,
            ride.getReadableDescription(),
            "Confirm Creation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        Boolean successful = false;
        if (confirmed == JOptionPane.YES_OPTION) {
            successful = connection.list(ride);
            if (successful) {
                JOptionPane.showMessageDialog(this, "Your ride was listed successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Your listing failed.");
            }
        }
    }

}
