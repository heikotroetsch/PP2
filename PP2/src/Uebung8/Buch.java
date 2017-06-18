package Uebung8;

import java.util.*;

public class Buch {
    private String titel;
    private String autor;
    private int id = -1;
    private List<Kategorie> kategorien = new ArrayList<Kategorie>();
    private Status status = Status.VORHANDEN;
    
    public Buch(String autor, String titel){
	this.titel = titel;
	this.autor = autor;
    }


    public void setKategorien(String katStr){
	katStr = katStr.toUpperCase();
	Kategorie[] kVals = Kategorie.values();
	for (int i=0;i<kVals.length;i++){
	    if (katStr.indexOf(kVals[i].toString()) != -1){
		this.kategorien.add(kVals[i]);
	    }
	}	
    }


    public void setKategorien(Kategorie[] kategorien){
	for (int i=0;i<kategorien.length;i++){
	    this.kategorien.add(kategorien[i]);
	}
    }
    
    public void setKategorien(String[] kategorien){
	for (int i=0;i<kategorien.length;i++){
	    this.kategorien.add(Kategorie.valueOf(kategorien[i]));
	}
    }

    public void setKategorien(List<Kategorie> kategorien){
	this.kategorien.addAll(kategorien);
    }

    public String getAutor (){
	return autor;
    }
	
    public String getTitel (){
	return titel;
    }

    public void setID(int id){
	this.id = id;
    }
	
    public int getID (){
	return id;
    }
	
    public Kategorie[] getKategorien (){
	Kategorie[] alleKategorien = new Kategorie[kategorien.size()];
	return kategorien.toArray(alleKategorien);
    }

    public boolean istAusgeliehen(){
	if (status.equals(Status.AUSGELIEHEN)){
	    return true;
	}
	else {
	    return false;
	}
    }	

    // ID + Titel
    public String toString(){
	return this.id + ": " + this.titel + " (" + this.autor + ")";
    }
	
}
