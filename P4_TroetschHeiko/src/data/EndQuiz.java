package data;

public class EndQuiz extends Message {
	private static final long serialVersionUID = 1L;
	
	public EndQuiz(){
		super(MessageType.END_CONNECTION);
	}

}
