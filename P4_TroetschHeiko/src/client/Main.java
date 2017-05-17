package client;

import general.Parameters;

public class Main {
	public static void main(String[] args){
		QuizClient qc = new QuizClient(Parameters.host, Parameters.port);
	}
}
