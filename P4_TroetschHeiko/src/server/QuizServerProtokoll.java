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
		
	}

	public void run(){
		
	}
	
	private void disconnect(){
		
	}

}
