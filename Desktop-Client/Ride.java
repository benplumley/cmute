public class Ride {

	private MapPoint startPoint = new MapPoint();
	private MapPoint endPoint = new MapPoint();
	private DateTime dateTime = new DateTime();
	private char[] repeatingDays;
	private byte numberOfSeats;
	private byte seatsRemaining;

	public Ride(MapPoint startPoint, MapPoint endPoint, DateTime dateTime, char[] repeatingDays, byte numberOfSeats, byte seatsRemaining) {
		startPoint = this.startPoint;
		endPoint = this.endPoint;
		dateTime = this.dateTime;
		repeatingDays = this.repeatingDays;
		numberOfSeats = this.numberOfSeats;
		seatsRemaining = this.seatsRemaining;
	}


}
