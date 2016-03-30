//TODO Review use of enum and class methods. It feels rather messy rn
public class MessageObject extends ProtocolObject {

	private static final long serialVersionUID = -5467477515213534232L;

	//TODO move this to seperate enum file???
	public enum MessageContent {
		QUERY_FAILURE ("QUERY_FAILURE"),
		NEW_RIDE_CONFIRMATION ("NEW_RIDE_CONFIRMATION"),
		RIDE_BOOKING_CONFIRMATION ("RIDE_BOOKING_CONFIRMATION"),
		CLIENT_QUIT ("CLIENT_QUIT"),
		DEFAULT_ERROR ("DEFAULT_ERROR");
		
		private final String message;
		MessageContent(String theMessage) {
			this.message = theMessage;
		}
	}
	
	private MessageContent myMessage;
	
	public MessageContent getMessage() {
		return myMessage;
	}

	public void setMessage(MessageContent myMessage) {
		this.myMessage = myMessage;
	}
	
}
