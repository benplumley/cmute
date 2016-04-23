// repeatingDays logic now handled by Repetitions class

public class Ride implements java.io.Serializable {

	private static final long serialVersionUID = 1219954696866557441L;
	//http://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
	private int UUID; // this is set by the database not the client
    private Boolean isToUni;
    private DateTime dateAndTime;
    private MapPoint startLocation;
	private int repeatingDays;
	private int numberOfSeats;
	private int seatsRemaining;

	public Ride(Boolean isToUni, MapPoint startLocation, DateTime dateAndTime, int repeatingDays, int numberOfSeats, int seatsRemaining) {
        this.isToUni = isToUni;
		this.startLocation = startLocation;
		this.dateAndTime = dateAndTime;
		this.repeatingDays = repeatingDays;
		this.numberOfSeats = numberOfSeats;
		this.seatsRemaining = seatsRemaining;
	}

    public Ride(int UUID, Boolean isToUni, MapPoint startLocation, DateTime dateAndTime, int repeatingDays, int numberOfSeats, int seatsRemaining) {
        // constructor for the server
        this.UUID = UUID;
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

	public DateTime getDateTime() {
		return dateAndTime;
	}

	public int getSeatsRemaining() {
		return seatsRemaining;
	}

	public int getRepeatingDays() {
		return repeatingDays;
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

	public int getUUID() {
		return UUID;
	}

}
