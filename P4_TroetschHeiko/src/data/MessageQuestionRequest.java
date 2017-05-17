package data;

public class MessageQuestionRequest extends Message {
	private static final long serialVersionUID = 1L;
	Kategorie kategorie;
	
	public MessageQuestionRequest(Kategorie k){
		super(MessageType.QUESTION_REQUEST);
		this.kategorie = k;
	}

	public Kategorie getKategorie() {
		return kategorie;
	}

}
