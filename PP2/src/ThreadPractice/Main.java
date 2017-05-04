package ThreadPractice;

public class Main {
	
	
	
	
	public static void main(String[]args){
		Drucker d = new Drucker();
		AddToQue add = new AddToQue(d);
		PrintFromQue print= new PrintFromQue(d);
		
		for(int i = 0; i<15; i++){
			add.run();
			add.run();
			print.run();
			add.run();
			print.run();
		}
		
	
	
	}
}
