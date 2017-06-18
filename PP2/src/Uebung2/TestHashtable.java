package Uebung2;

import java.util.*;
//import java.util.Map.Entry;
import java.text.DecimalFormat;

class Messwert {
    private final static DecimalFormat df = new DecimalFormat("0.0");
    private double temperatur;
    private Date datum;
    private int windstaerke;

    private Messwert(double t){
	this.temperatur = t;
    }

    public Messwert(double t, int jahr, int monat, int tag) {
	this(t);
	this.datum = createDatum(jahr, monat, tag);
    }
    
    public String toString(){
	return "Temperatur am: " + this.datum +":    " + df.format(this.temperatur) + " Grad.";
    }

    public long getKey(){
	return datum.getTime();
    }

    public static Date createDatum(int jahr, int monat, int tag) {
	return  new GregorianCalendar(jahr, monat, tag, 12,0,0).getTime();
    }
    
    public Messwert(double t, int w){
    	this.temperatur = t;
    	this.windstaerke = w;
        }

}

public class TestHashtable {
	
	
    public static void main(String[] args){
    	Hashtable<Long, Messwert> table = new Hashtable<Long, Messwert>();
    	for(int i = 0; i<15; i++){
    		Messwert w1 = new Messwert(Math.random()*40, 2017, 1, (int)(Math.random()*31));
        	table.put(w1.getKey(), w1);
    	}
    
    	for(long w: table.keySet()){
    		System.out.println(table.get(w).toString());
    	}
    	
    	for(long w: table.keySet()){
    		System.out.println("Key: "+w+"\tWert: "+table.get(w).toString());
    	}
    	
    }

    
}
