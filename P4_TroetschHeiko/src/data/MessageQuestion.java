package data;

public class MessageQuestion extends Message {
	private static final long serialVersionUID = 1L;
	Question question;
	
	public MessageQuestion(Question q, int hashCodeMotherQuestion){
		super(MessageType.QUESTION);
		this.question = q;
	}

	public Question getQuestion() {
		return this.question;
	}
	

}
