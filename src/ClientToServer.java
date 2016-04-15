@SuppressWarnings("serial")
public abstract class ClientToServer extends ProtocolObject {

	private final ClientToServerPurpose myPurpose;
	
	public ClientToServer(ClientToServerPurpose myPurp) {
		super(false);
		this.myPurpose = myPurp;
	}

	public ClientToServerPurpose getMyPurpose() {
		return myPurpose;
	}

}
