// Query class should be accessible to both client and server so that the same
// code is used for serialisation as deserialisation.

import java.sql.Date;

public class Query implements java.io.Serializable {

    private Boolean isToUni;
    private Date dateAndTime;
    private int timeTolerance;
    private MapPoint startLocation;
    private int locationTolerance;

    public Query(Boolean isToUni, Date dateAndTime, int timeTolerance, MapPoint startLocation, int locationTolerance) {
        this.isToUni = isToUni;
        this.dateAndTime = dateAndTime;
        this.timeTolerance = timeTolerance;
        this.startLocation = startLocation;
        this.locationTolerance = locationTolerance;
    }

    public String createSQLString() {
        String queryString = "SELECT * FROM rides WHERE"; // TODO correct where clause
        return queryString;
    }

}
