package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import data.*;

public class QuizClient {
	
	private final static Question q_test = new Question(Kategorie.SPORT,
			"Bei welchem Verein spielt Toni Kroos aktuell (Mai 2017)",
			"Real Madrid","Bayern München","FC Barcelona","Manchester United",1);
	
	   String hostName = "";  // Rechner-Name bzw. -Adresse
	   int port;              // Port-Nummer
	   Socket c = null;       // Socket fuer die Verbindung zum Server
	   private ObjectOutputStream zumServer;  // Ausgabe-Strom zum Server
	   private ObjectInputStream vomServer;   // Eingabe-Strom vom Server
	   QuizClientUI ui;
		
	   public QuizClient(String host, int port){
		   this.hostName = host;
		   this.port = port;
		   this.ui = new QuizClientUI(this);
		   boolean connectionEstablished = connect();
		   if (connectionEstablished){
			   this.ui.setVisible(true);	
			   System.out.println("connected");
		   } else {
			   System.out.println("Sorry: no connection to " + host + ":" +  port);
		   }
	   }
	   
	   /* Verbindung aufbauen und Streams initialisieren */
	   private boolean connect(){
		   boolean connected = false;
		   while(!connected){
			   try {
					c = new Socket(this.hostName, this.port);
					zumServer = new ObjectOutputStream(c.getOutputStream());
					vomServer = new ObjectInputStream(c.getInputStream());
					connected = true;
				} catch (Exception e) {
					e.printStackTrace();
					connected = false;
				}
		   }
		   return connected;
	   }
	   

	   /* Frage vom Server anfordern */
	   Question getQuestionFromServer(Kategorie k) {
		   try {
			zumServer.writeObject(new MessageQuestionRequest(k));
			Message m = ((Message)vomServer.readObject());
			MessageQuestion mq = (MessageQuestion)m;
			return mq.getQuestion();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	   }
	   
	   /* Antwort zu der aktuellen (der als letzten gestellten) 
	    * Frage vom Server anfordern 
	    * */
	   int getAnswerFromServer(int hash){
		   try {
			zumServer.writeObject(new MessageAwnserRequest(hash));
			Message m = (Message)vomServer.readObject();
			MessageAwnser ma = (MessageAwnser)m;
			ma.getAwnser();
			return ma.getAwnser();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	   }
	   
	   /* Server benachrichtigen und Verbindung schließen */
	   void endQuiz(){
		   try {
			zumServer.writeObject(new EndQuiz());
			zumServer.close();
			vomServer.close();
			c.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	   }
	   

}
