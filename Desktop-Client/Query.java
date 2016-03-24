import java.sql.Date;

public class Query implements java.io.Serializable {

    Boolean isToUni;
    Date dateAndTime;
    int timeTolerance;
    MapPoint startLocation;
    int locationTolerance;

    public Query(Boolean isToUni, Date dateAndTime, int timeTolerance, MapPoint startLocation, int locationTolerance) {
        this.isToUni = isToUni;
        this.dateAndTime = dateAndTime;
        this.timeTolerance = timeTolerance;
        this.startLocation = startLocation;
        this.locationTolerance = locationTolerance;
    }

}
