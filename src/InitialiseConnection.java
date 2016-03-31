/* This class is intended to give the client their client ID.
 * It is the inital communication between server and object.
 */
public class InitialiseConnection extends MessageObject implements SQLable {

	private static final long serialVersionUID = -8079303601107262422L;
	private Object clientID = null; //If client ID turns up as null then error
	private String userName;
	
	public InitialiseConnection(String name) {
		super(MessageContent.NEW_CLIENT_CONNECTION);
		this.userName = name;
	}

	public int getClientID() {
		return (int) clientID; //USED ONLY BY CLIENT
	}

	public void setClientID(int clientID) {
		this.clientID = clientID; //USED ONLY BY SERVER
	}

	public String toSQLString() {
		//Used by server to get the clients ID then sends back the object with the client ID
		return null;
	}

	//Useful?
	public String getUserName() {
		return userName;
	}

}
