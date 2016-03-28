// This class allows the UI to be tested without connecting to a real server

public class FakeServer {

    public FakeServer() {

    }

    public Ride[] getMatchingRides(Boolean isToUni, DateTime dateAndTime, int timeTolerance) {
        byte zero = 0;
        byte four = 4;
        Ride[] result = {new Ride(true, new MapPoint(31,76), new DateTime(1459183241L),zero,four,four),
                         new Ride(true, new MapPoint(97,181), new DateTime(1459189641L),zero,four,four),
                         new Ride(false, new MapPoint(305,150), new DateTime(1459189641L),zero,four,four),
                         new Ride(false, new MapPoint(720,300), new DateTime(1459189641L), zero,four,four)};
        return result;
    }

    public Boolean book(Ride ride) {
        // TODO send a request to book this ride
        return false;
    }

}
