public class BookRide extends ClientToServer implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4109739758499069884L;
	private int UUID;

	public BookRide(int UUID) {
		super(ClientToServerPurpose.RIDE_BOOKING);
		this.UUID = UUID;
	}

	public int getUUID() {
		return UUID;
	}

}
