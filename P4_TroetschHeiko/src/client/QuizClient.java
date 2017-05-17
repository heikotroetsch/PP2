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
		   } else {
			   System.out.println("Sorry: no connection to " + host + ":" +  port);
		   }
	   }
	   
	   /* Verbindung aufbauen und Streams initialisieren */
	   private boolean connect(){
		   return true;
	   }
	   

	   /* Frage vom Server anfordern */
	   Question getQuestionFromServer(Kategorie k) {
			 return QuizClient.q_test;  // Dummy Testfrage 

	   }
	   
	   /* Antwort zu der aktuellen (der als letzten gestellten) 
	    * Frage vom Server anfordern 
	    * */
	   int getAnswerFromServer(){
		   return QuizClient.q_test.getAntwort();
	   }
	   
	   /* Server benachrichtigen und Verbindung schließen */
	   void endQuiz(){
	   }
	   

}
