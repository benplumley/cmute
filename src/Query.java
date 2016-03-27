// Query class should be accessible to both client and server so that the same
// code is used for serialisation as deserialisation.

public class Query implements java.io.Serializable {

    private Boolean isToUni;
    private DateTime dateAndTime;
    private int timeTolerance;

    public Query(Boolean isToUni, DateTime dateAndTime, int timeTolerance) {
        this.isToUni = isToUni;
        this.dateAndTime = dateAndTime;
        this.timeTolerance = timeTolerance;
    }

    public String toSQLString() {
        long startTime = dateAndTime.subtractMinutes(timeTolerance).getDateTime();
        long endTime = dateAndTime.addMinutes(timeTolerance).getDateTime();
        String queryString = "SELECT * FROM rides WHERE" +
            " isToUni = " + isToUni +
            " AND dateAndTime >= " + startTime +
            " AND dateAndTime <= " + endTime;
            // TODO location and tolerance in WHERE clause
        return queryString;
    }

}
