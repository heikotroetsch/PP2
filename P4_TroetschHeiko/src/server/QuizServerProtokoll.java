package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import data.*;

public class QuizServerProtokoll extends Thread {	  
	Socket s;                   // Socket in Verbindung mit dem Client
	private ObjectOutputStream zumClient;  // Ausgabe-Strom zum Client
	private ObjectInputStream vomClient;   // Eingabe-Strom vom Client
	
	

	public QuizServerProtokoll (Socket s) { 
		this.s = s;
		try {
			zumClient = new ObjectOutputStream(s.getOutputStream());
			vomClient = new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run(){
		boolean connected = true;
		while(connected){
			Message m = null;
			try {
				m = (Message)vomClient.readObject();
			} catch (Exception e) {
				e.printStackTrace();
			}
			switch(m.getType()){
			case QUESTION_REQUEST:
				MessageQuestionRequest mr = (MessageQuestionRequest) m;
				try {
					Question randomQuestion = QuizServer.allQuestions.randomQuestion(mr.getKategorie().name());
					zumClient.writeObject(new MessageQuestion(new Question(randomQuestion),randomQuestion.hashCode()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case AWNSER_REQUEST:
				MessageAwnserRequest ar = (MessageAwnserRequest) m;
				try {
					zumClient.writeObject(new MessageAwnser(QuizServer.allQuestions.getQuestionByHash(ar.getHash()).getAntwort()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case END_CONNECTION:
				connected = false;
				disconnect();
				break;
			default:
				break;
			}
		}
			
	}
	
	private void disconnect(){
		try {
			s.close();
			vomClient.close();
			zumClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
