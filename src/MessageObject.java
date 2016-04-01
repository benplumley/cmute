/* Message objects are used to facillitate simple
 * communication between server and client. They
 * encode basic messages, e.g. whether the client
 * is quitting or if a ride has been successfully
 * booked.
 */

public class MessageObject extends ProtocolObject {

	private static final long serialVersionUID = -5467477515213534232L;
	private final MessageContent myMessage;
	private final String myDescription;
	
	public MessageObject(MessageContent newMessage, String newDescription){
		super(true); //Inidicate that the object is indeed a message
		this.myMessage = newMessage;
		this.myDescription = newDescription;
	}
	
	public MessageContent getMessage() {
		return myMessage;
	}

	public String getMyDescription() {
		return myDescription;
	}
	
	public boolean isError(){
		
		switch(myMessage){
				
			case GENERAL_ERROR:
				return true;

			case NEW_RIDE_FAILURE:
				return true;
				
			case QUERY_FAILURE:
				return true;

			case RIDE_BOOKING_FAILURE:
				return true;
				
			default:
				return false;
		
		}

	}
	
}
