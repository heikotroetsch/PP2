package ThreadPractice;

import java.util.LinkedList;
import java.util.Queue;

public class Drucker{
	
	static int id = 0;
	
	Queue<String> que = new LinkedList<String>();
	
	public synchronized boolean addToQue (String s){
		que.add("ID: "+id+"\t\t"+s);
		System.out.println("Add ID: "+id+"\t\t"+s);
		id++;
		return true;
	}
	
	public synchronized String printFromQue(){
		return que.remove();
	}
}
