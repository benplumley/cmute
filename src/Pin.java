import javax.swing.ImageIcon;

public class Pin extends javax.swing.JLabel {

    private Ride ride;
    public static final int WIDTH = 11;
    public static final int HEIGHT = 18;
    public static final int OFFSETX = 5; // the distance in px from top left corner to point
    public static final int OFFSETY = 16;


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
