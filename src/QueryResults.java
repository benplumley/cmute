import java.sql.ResultSet;


public class QueryResults extends ServerToClient {
	private Ride[] myRides;

	private static final long serialVersionUID = -1391915633649891722L;

	public QueryResults(ResultSet rs) {
		super();
        while (rs.next()) {
            String coffeeName = rs.getString("COF_NAME");
            int supplierID = rs.getInt("SUP_ID");
            float price = rs.getFloat("PRICE");
            int sales = rs.getInt("SALES");
            int total = rs.getInt("TOTAL");
            System.out.println(coffeeName + "\t" + supplierID +
                               "\t" + price + "\t" + sales +
                               "\t" + total);
        }
	}

	private void addRide(Ride rideToAdd){
//		myRides.
	}
	
	public Ride[] getRides() {
		return myRides;
	}

}
