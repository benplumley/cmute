/*
 * Declares that the class must be able to produce a relevant SQL
 */
public interface SQLable {
	
	public abstract String toSQLString();

}
