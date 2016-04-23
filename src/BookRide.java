public class BookRide extends ClientToServer {

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
