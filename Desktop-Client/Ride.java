import java.sql.Date;

public class Ride implements java.io.Serializable {

	private MapPoint startPoint;
	private MapPoint endPoint;
	private Date dateTime;
	private char[] repeatingDays;
	private byte numberOfSeats;
	private byte seatsRemaining;

	public Ride(MapPoint startPoint, MapPoint endPoint, Date dateTime, char[] repeatingDays, byte numberOfSeats, byte seatsRemaining) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.dateTime = dateTime;
		this.repeatingDays = repeatingDays;
		this.numberOfSeats = numberOfSeats;
		this.seatsRemaining = seatsRemaining;
	}


}
