//For our own implementation of date and times

import java.text.DateFormat;

public class DateTime implements java.io.Serializable {

    long epoch = 0;

    public DateTime(long epoch) {
        this.epoch = epoch;
    }

    public String dateString() {
        return DateFormat.getDateInstance().format(epoch);
    }

    public String timeString() {
        return DateFormat.getTimeInstance().format(epoch);
    }

}
