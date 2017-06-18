package Uebung6;

import java.text.DecimalFormat;

public class Ware {
    private String id;
    private double preis;
    private int anzahl;
    private String name;
    private String kategorie;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public void setId(String id){
	this.id = id;
    }

    public void setPreis (double preis){
	this.preis = preis;
    }

    public void setAnzahl(int anzahl){
	this.anzahl = anzahl;
    }

    public void setKategorie(String kategorie){
	this.kategorie = kategorie;
    }
    
    public String toString(){
	return id + ": " + name.trim() + "(" +  anzahl + ") " + ", Preis: " + df.format(preis);
    }

    public void setName(String name){
	this.name = name;
    }
    
}
