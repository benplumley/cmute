
public class ServerSQLException extends Exception {

	private static final long serialVersionUID = -119911457011945208L;
	private final MessageObject myMessage;
	
	public ServerSQLException(MessageContent errorMessage, String errorDescription){
		this.myMessage = new MessageObject(errorMessage, errorDescription);
	}

	public MessageObject getMessageObject() {
		return myMessage;
	}

}
