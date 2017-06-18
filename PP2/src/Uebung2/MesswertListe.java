package Uebung2;

import java.util.Date;
import java.util.Vector;
import java.util.Iterator;

class MesswertFake {
    // erzeugt Zufallswert im Intervall [a,b[
    // Ergebnis nach einer Nachkommastelle abgeschnitten
    public static double createDoubleWert(int a, int b){
	double d = Math.random();
	double v = (a + d * (b-a));
	return (double)((int)(v * 10.0)) / 10.0;
    }

   // erzeugt ganzzahligen  Zufallswert im Intervall [a,b[
    public static int createIntWert(int a, int b){
	double d = Math.random();
	return  (int) (a + d * (b-a));
    }
}

class Messwert {
    private double temperatur;
    private int windstaerke;

    public Messwert(double t, int w){
	this.temperatur = t;
	this.windstaerke = w;
    }
    
    public String toString(){
	return "***Messwert***: \n" + "Temperatur: " + this.temperatur +"\nWindstaerke: " + + this.windstaerke + "\n";
    }
}

class MesswertListe{
	Vector<Double> messungen1;
	Vector<Messwert> messungen2;
	Vector<Vector<Messwert>> messungen3;
	
	public void addMesswert1(double e){
		messungen1.add(e);
	}
	
	public void addMesswert2(double e, int i){
		messungen2.add(new Messwert(e, i));
	}
	
	public void addMesswert3(double d1, double d2, double d3, int i){
		Vector<Messwert> tag = new Vector<Messwert>();
		tag.add(new Messwert(d1, i));
		tag.add(new Messwert(d2, i));
		tag.add(new Messwert(d3, i));
		messungen3.add(tag);
	}
	
	public void printMessungen1(){
		for(double d:messungen1){
			System.out.println("Messung: "+d);
		}
	}
	
	public void printMessungen2(){
		for(Messwert m: messungen2){
			System.out.println(m);
		}
	}
	
	public void printMessungen3(){
		for(Vector<Messwert> v: messungen3){
			for(Messwert m: v){
				System.out.println(m);			
			}
		}
	}
	
}
