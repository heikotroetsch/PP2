package ThreadPractice;

public class AddToQue extends Thread{
	
	Drucker d;
	
	AddToQue(Drucker d){
		this.d = d;
	}
	
	public void run(){
		d.addToQue("");
	}
	
}
