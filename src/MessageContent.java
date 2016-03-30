	/*
	 * Each MessageObject has a MessageContent.
	 * 
	 * This should allow for the most basic communication between Server and client.
	 */
	public enum MessageContent {
		QUERY_FAILURE,
		NEW_RIDE_CONFIRMATION,
		NEW_RIDE_FAILURE,
		RIDE_BOOKING_CONFIRMATION,
		RIDE_BOOKING_FAILURE,
		CLIENT_QUIT;
	}