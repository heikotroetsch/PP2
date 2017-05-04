package ThreadPractice;

public class PrintFromQue extends Thread{
	
	Drucker d;
	
	PrintFromQue(Drucker d){
		this.d = d;
	}
	
	public void run(){
		System.out.println("Print"+d.printFromQue());
	}
	
}
