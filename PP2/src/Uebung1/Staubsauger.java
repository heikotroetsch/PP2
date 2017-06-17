package Uebung1;

import java.util.concurrent.SynchronousQueue;

enum Power{
    AN, AUS
}

enum Level {
    NIEDRIG, MITTEL, HOCH
}



public class Staubsauger {
	Power power = Power.AN;
	Level level = Level.NIEDRIG;
	
	public void toggle(){
		if(power == Power.AN){
			power = Power.AUS;
		}else{
			power = Power.AUS;
		}
	}
	
	public void up(){
		if(level == Level.NIEDRIG){
			level = Level.MITTEL;
		}else{
			level = Level.HOCH;
		}
	}
	
	public void down(){
		if(level == Level.HOCH){
			level = Level.MITTEL;
		}else{
			level = Level.NIEDRIG;
		}
	}
	
	public String toString(){
		return "Der Staubsauger ist "+power.toString()+" und auf der Stufe "+level.toString();
	}
	
	
	public static void main (String[]args){
		Staubsauger s = new Staubsauger();
		System.out.println(s);
		s.up();
		s.toggle();
		System.out.println(s);
	}
	
}
