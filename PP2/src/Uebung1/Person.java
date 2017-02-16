package Uebung1;
import java.util.GregorianCalendar;
import java.util.Calendar;

    public class Person implements KeyHolder {
	private String name;
	private int gebJahr;

	public Person(String n, int j){
	    this.name = n;
	    this.gebJahr = j;
	}

	public String toString(){
	    return name + " (*" + this.gebJahr +")";
	}

	public int getKey(){
	    return (new GregorianCalendar()).get(Calendar.YEAR) - gebJahr;
	}
    }
