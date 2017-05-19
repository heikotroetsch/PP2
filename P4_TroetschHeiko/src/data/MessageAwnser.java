package data;

public class MessageAwnser extends Message {
	private static final long serialVersionUID = 1L;
	int awnser;
	
	public MessageAwnser(int awnser){
		super(MessageType.AWNSER);
		this.awnser = awnser;
	}

	public int getAwnser() {
		return this.awnser;
	}

}
