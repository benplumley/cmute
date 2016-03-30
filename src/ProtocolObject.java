import java.io.Serializable;


@SuppressWarnings("serial") //Will be handled by non-abstract classes only!
public abstract class ProtocolObject implements Serializable {	
		private final boolean isMessage;
		
		public ProtocolObject(boolean message){
			this.isMessage = message;
		}

		public boolean isMessage() {
			return isMessage;
		}
}
