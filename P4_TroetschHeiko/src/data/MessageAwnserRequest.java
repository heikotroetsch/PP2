package data;

public class MessageAwnserRequest extends Message {
	private static final long serialVersionUID = 1L;
	int hash;
	
	public MessageAwnserRequest(int hash){
		super(MessageType.AWNSER_REQUEST);
		this.hash = hash;
	}

	public int getHash() {
		return this.hash;
	}

}
