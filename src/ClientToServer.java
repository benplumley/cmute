@SuppressWarnings("serial")
public abstract class ClientToServer extends ProtocolObject implements SQLable {

	public ClientToServer() {
		super(false);
	}
	
	public abstract boolean isQuery();

}
