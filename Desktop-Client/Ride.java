import java.sql.Date;

public class Ride implements java.io.Serializable {

	private MapPoint startPoint;
	private MapPoint endPoint;
	private Date dateTime;
	private char[] repeatingDays;
	private byte numberOfSeats;
	private byte seatsRemaining;

	public Ride(MapPoint startPoint, MapPoint endPoint, Date dateTime, char[] repeatingDays, byte numberOfSeats, byte seatsRemaining) {
		startPoint = this.startPoint;
		endPoint = this.endPoint;
		dateTime = this.dateTime;
		repeatingDays = this.repeatingDays;
		numberOfSeats = this.numberOfSeats;
		seatsRemaining = this.seatsRemaining;
	}


}
