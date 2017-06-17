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
    /* Aufgabe 1 */
}
