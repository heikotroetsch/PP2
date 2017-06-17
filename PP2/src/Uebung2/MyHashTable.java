package Uebung2;

import java.lang.reflect.Array;

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

 
    public static void main(String[] args){


    }

}
