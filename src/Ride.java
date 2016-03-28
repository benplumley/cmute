// repeatingDays should take the form eg 0b0100100 to mean a ride that is
// repeated every Tuesday and Friday. SQL can store the decimal value (36) which
// is converted back to binary to get the days.
// Make a class which converts byte to repeating days?

public class Ride implements java.io.Serializable {

	private static final long serialVersionUID = 1219954696866557441L;
	//http://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
	private int UUID; // this is set by the database not the client
    private Boolean isToUni;
    private DateTime dateAndTime;
    private MapPoint startLocation;
	private byte repeatingDays;
	private byte numberOfSeats;
	private byte seatsRemaining;

	public Ride(Boolean isToUni, MapPoint startLocation, DateTime dateAndTime, byte repeatingDays, byte numberOfSeats, byte seatsRemaining) {
        this.isToUni = isToUni;
		this.startLocation = startLocation;
		this.dateAndTime = dateAndTime;
		this.repeatingDays = repeatingDays;
		this.numberOfSeats = numberOfSeats;
		this.seatsRemaining = seatsRemaining;
	}

    public MapPoint getLocation() {
        return startLocation;
    }

    public Boolean getIsToUni() {
        return isToUni;
    }

    public String getReadableDescription() {
        String description;
        if (isToUni) {
            description = "This journey starts at the pin and ends at the University of Bath.";
        } else {
            description = "This journey starts at the University of Bath and ends at the pin.";
        }
        description = description + " It will leave at " + dateAndTime.timeString() + " on " + dateAndTime.dateString() + ". There are " + seatsRemaining + " seats available.";
        return description;
    }

}
