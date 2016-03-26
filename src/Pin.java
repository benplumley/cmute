import javax.swing.ImageIcon;

public class Pin extends javax.swing.JLabel {

    private Ride ride;

    public Pin(Ride ride) {
        this.ride = ride;
        ImageIcon pinIcon;
        if (ride.getIsToUni()) {
            pinIcon = new ImageIcon("graphics/orangepin.png");
        } else {
            pinIcon = new ImageIcon("graphics/purplepin.png");
        }
        this.setIcon(pinIcon);
    }

    public Ride getRide() {
        return ride;
    }

}
