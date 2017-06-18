package Uebung2;

import java.lang.reflect.Array;
import java.util.Hashtable;

class Person implements KeyGenerator {
    private String name;
    private String beruf;

    public Person(String name, String  beruf){
        this.name = name;
        this.beruf = beruf;
    }

    public int createKey(){
	return KeySet.createKey();
    }
    
    public String toString(){
        return "***Person***: \n" + "Name: " + this.name +"\nBeruf: " + this.beruf;
    }
}


class KeyPersonPair {
    private final int key;
    private Person inhalt;
 
    public KeyPersonPair(Person obj){
        inhalt = obj;
        key = obj.createKey();
    }

    public Person getInhalt(){
       return inhalt;
    }

    public void setInhalt(Person obj){
        inhalt = obj;
    }

    public int getKey(){
        return this.key;
    }

    public String toString (){
        return "Key: " + this.key + "\n" +  "Content: " + this.inhalt.toString() + "\n";
    }
}


public class MyHashTable {
	int M;
	KeyPersonPair[] table;
	
	MyHashTable(int size){
		M = size;
		table = new KeyPersonPair[size];
	    }

	public int hash(int i ){
		return (i%M);
	}
	
	public boolean put(KeyPersonPair p){
		int count = 0;  // Zaehler fuer die Anzahl der ueberprueften Stellen
		int baseIndex = hash(p.getKey());
		int index = baseIndex;
				 
		while ((table[index] != null) && (count < M)){
		    count++;
		    index = (baseIndex + count) % M;
		    System.out.println("try index " +  index);
		}

		if (count < M){
		    table[index] = p;
		    System.out.println("put into slot: " + index);
		    return true;
		}
		else
		    return false;
	}
 
	public Person get(int key){
		int count = 0;  // Zaehler fuer die Anzahl der ueberprueften Stellen
		int baseIndex = hash(key);
		int index = baseIndex;
				 
		while ((table[index] != null) &&
		       (table[index].getKey() != key) && 
		       (count < M)){
		    count++;
		    index = (baseIndex + count) % M;
		    System.out.println("try index " +  index);
		}
		if ((count == M) || (table[index] == null)){
		    return null;
		}
		else {
		    return table[index].getInhalt();
		}
	    }

	    public void printTable(){
		for (int i=0;i<table.length;i++){
		    System.out.println(i + " . " + table[i]);
		}
	    }


	    public static void main(String[] args){
		MyHashTable myMap = new MyHashTable(11);
	        myMap.printTable();

		myMap.put(new KeyPersonPair(new Person("Tim","Taucher")));
		myMap.put(new KeyPersonPair(new Person("Ben", "Baecker")));
		myMap.put(new KeyPersonPair(new Person("Otto","Optiker")));
		myMap.put(new KeyPersonPair(new Person("Ed","Elektriker")));

		KeyPersonPair kp = new KeyPersonPair(new Person("Paul","Polizist"));
		int key = kp.getKey();
		myMap.put(kp);
		
		if (myMap.get(key) != null){
		    System.out.println("Objekt fuer Schluessel " + key + " gefunden.\n");
		}

		myMap.printTable();

	    }

	}
