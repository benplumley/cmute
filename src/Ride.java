// repeatingDays should take the form eg 0b0100100 to mean a ride that is
// repeated every Tuesday and Friday. SQL can store the decimal value (36) which
// is converted back to binary to get the days.
// Make a class which converts byte to repeating days?

public class Ride implements java.io.Serializable {

    private int UUID; // this is set by the database not the client
    private Boolean isToUni;
    private DateTime dateAndTime;
    private MapPoint startLocation;
	private byte repeatingDays;
	private byte numberOfSeats;
	private byte seatsRemaining;

	public Ride(Boolean isToUni, MapPoint startLocation, DateTime dateAndTime, byte repeatingDays, byte numberOfSeats, byte seatsRemaining) {
		this.startLocation = startLocation;
		this.dateAndTime = dateAndTime;
		this.repeatingDays = repeatingDays;
		this.numberOfSeats = numberOfSeats;
		this.seatsRemaining = seatsRemaining;
	}

    public MapPoint getLocation() {
        return startLocation;
    }


}
