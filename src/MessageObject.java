/* Message objects are used to facillitate simple
 * communication between server and client. They
 * encode basic messages, e.g. whether the client
 * is quitting or if a ride has been successfully
 * booked.
 */

public class MessageObject extends ProtocolObject {

	private static final long serialVersionUID = -5467477515213534232L;
	private final MessageContent myMessage;
	
	public MessageObject(MessageContent newMessage){
		super(true); //Inidicate that the object is indeed a message
		this.myMessage = newMessage;
	}
	
	public MessageContent getMessage() {
		return myMessage;
	}
	
}
