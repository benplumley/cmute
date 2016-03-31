@SuppressWarnings("serial")
public abstract class ClientToServer extends ProtocolObject implements SQLable {
	
	private final int myClientID; //TODO client ID may not be an int. Change me!!!!!!
	
	public ClientToServer(int clientID) {
		super(false); //Indicate that the object is not a message
		this.myClientID = clientID;
	}
	
	public int getClientID() {
		return myClientID;
	}
}
