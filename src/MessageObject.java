public class MessageObject extends ProtocolObject {

	private static final long serialVersionUID = -5467477515213534232L;
	private final MessageContent myMessage;
	
	public MessageObject(MessageContent newMessage){
		this.myMessage = newMessage;
	}
	
	public MessageContent getMessage() {
		return myMessage;
	}
	
}
