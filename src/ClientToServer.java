@SuppressWarnings("serial")
public abstract class ClientToServer extends ProtocolObject {
	
	public ClientToServer(boolean message) {
		super(false); //Indicate that the object is not a message
		// TODO Auto-generated constructor stub
	}

	public abstract String toSQLString();
}
