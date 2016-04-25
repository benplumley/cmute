import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryResults extends ServerToClient {
	private ArrayList<Ride> myRides = new ArrayList<Ride>();

	private static final long serialVersionUID = -1391915633649891722L;

	public QueryResults(ResultSet rs) throws SQLException {
		super();
        while (rs.next()) {
        	        	
        	myRides.add(
        			new Ride(
        					rs.getInt("UUID"),
        					rs.getBoolean("istouni"),
        					new MapPoint(
        							rs.getInt("map_point_x"),
        							rs.getInt("map_point_y")),
        					new DateTime(
        							rs.getLong("date_and_time")),
        					rs.getInt("repeating_days"),
        					rs.getInt("number_of_seats"),
        					rs.getInt("seats_remaining")
        					)
        			);
        }
	}

	public ArrayList<Ride> getRides() {
		return myRides;
	}

}
