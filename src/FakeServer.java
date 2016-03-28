// This class allows the UI to be tested without connecting to a real server

public class FakeServer {

    public FakeServer() {

    }

    public Ride[] getMatchingRides(Boolean isToUni, DateTime dateAndTime, int timeTolerance) {
        byte zero = 0;
        byte four = 4;
        if (isToUni) {
            Ride[] result = {new Ride(true, new MapPoint(30,85), new DateTime(1459183241L),zero,four,four),
                             new Ride(true, new MapPoint(35,85), new DateTime(1459189641L),zero,four,four),
                             new Ride(true, new MapPoint(305,150), new DateTime(1459189641L),zero,four,four),
                             new Ride(true, new MapPoint(720,300), new DateTime(1459189641L), zero,four,four)};
            return result;
        } else {
            Ride[] result = {new Ride(false, new MapPoint(52,99), new DateTime(1459183241L),zero,four,four),
                             new Ride(false, new MapPoint(253,102), new DateTime(1459189641L),zero,four,four),
                             new Ride(false, new MapPoint(652,400), new DateTime(1459189641L),zero,four,four),
                             new Ride(false, new MapPoint(670,48), new DateTime(1459189641L), zero,four,four)};
            return result;
        }
    }

    public Boolean book(Ride ride) {
        // TODO send a request to book this ride
        return false;
    }

}
