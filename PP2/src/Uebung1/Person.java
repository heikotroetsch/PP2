package Uebung1;

public class Person implements KeyHolder{
	
	static int NEXT_KEY;
	final int key;
	String name;
	
	Person(){
		this.key = NEXT_KEY;
		NEXT_KEY++;
	}
	
	Person(String s){
		this.key = NEXT_KEY;
		this.name = s;
		NEXT_KEY++;
	}
	
	
	@Override
	public int getKey() {
		return this.key;
	}

	public static void main(String[]args){
		KeyHolder kh = new Person();
		System.out.println(kh.getKey());
		KeyHolder kh2 = new Person();
		System.out.println(kh2.getKey());
	}
	
}
