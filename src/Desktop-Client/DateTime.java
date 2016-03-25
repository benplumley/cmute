//For our own implementation of date and times

import java.text.DateFormat;

public class DateTime implements java.io.Serializable {

    long epoch = 0; // in milliseconds
    private static final int MINUTES_TO_MS = 60000;

    public DateTime(long epoch) {
        this.epoch = epoch;
    }

    public String dateString() {
        return DateFormat.getDateInstance().format(epoch);
    }

    public String timeString() {
        return DateFormat.getTimeInstance().format(epoch);
    }

    public DateTime addMinutes(int m) {
        return new DateTime(epoch + (m * MINUTES_TO_MS));
    }

    public DateTime subtractMinutes(int m) {
        return addMinutes(-1 * m);
    }

    public long getDateTime() {
        return epoch;
    }

}
