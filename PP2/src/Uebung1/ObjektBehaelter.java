package Uebung1;

public class ObjektBehaelter<T> {

	T objekt;
	static int NEXT_KEY = 0;
	final int key;
	
	public ObjektBehaelter(T objekt){
		this.objekt = objekt;
		this.key = NEXT_KEY;
		NEXT_KEY++;
	}
	
	public T getObject(){
		return this.objekt;
	}
	
	public int getKey(){
		return this.key;
	}
	
	public String toString(){
		return this.key+this.objekt.toString();
	}
	
	
}
