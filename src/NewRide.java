
public class NewRide extends ClientToServer implements java.io.Serializable {

	private static final long serialVersionUID = -8586202014045591404L;
	private Ride ride;

	public NewRide(Ride ride) {
		super(ClientToServerPurpose.NEW_RIDE);
		this.ride = ride;
	}

	public Ride getRide() {
		return ride;
	}

}
