package server;

import general.Parameters;
import io.QuestionIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import data.Question;
import data.QuestionList;

public class QuizServer extends Thread {

    private ServerSocket serverSocket;
	public static QuestionList allQuestions;

	public QuizServer(){
		this.prepareData(); // get questions
		this.start();
	}
	
	public void run(){
		try (ServerSocket server = new ServerSocket(Parameters.port)){  // Server-Socket
			this.serverSocket = server;
			System.out.println("Quiz Server laeuft");    // Statusmeldung
			Socket s;
			while(true){
				s = server.accept();     				 // Client-Verbindung akzeptieren
				new QuizServerProtokoll(s).start();      // Protokoll abwickeln
			}	
		} catch (SocketException e) {
			System.out.println("Socket closed");
		} catch (IOException e2) {
			e2.printStackTrace();
        }
	}
	
	/* shutdown server */
	public void stopServer(){
    	try {
    		if (!this.serverSocket.isClosed()){
        		System.out.println("stopping server......");
    			this.serverSocket.close();	
    		}
    	} catch (SocketException  e1){
    		System.out.println("Server stopped");	
    	}  catch (IOException e2){
    		e2.printStackTrace();  	
    	}
    }
	
	/* Load questions (as Objects) and store them in an instance of a QuestionList
	 * 
	 */
	private void prepareData(){
		ArrayList<Question> qList = QuestionIO.readQuestions(Parameters.questionFilename);
		allQuestions = new QuestionList();
		allQuestions.insertQuestions(qList);
	}
	

}
