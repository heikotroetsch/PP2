package Uebung8;

import java.sql.*;
import java.util.Properties;
import java.io.*;

public class FillDBBuecherei {
    Properties properties;
    private Connection c;
    private PreparedStatement pstm1;
    private PreparedStatement pstm2;

    FillDBBuecherei(){
    	try {
			c = DriverManager.getConnection("jdbc:sqlite:src/Uebung8/my_library.db");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void init(){
	properties = new Properties();
	properties.setProperty("PRAGMA foreign_keys", "ON");
    }

    public void connect (String filename){
    }

    // Vorbereitung von Statements zum Einfuegen von 
    // Buechern bzw Personen
    // Titel, Autor, Kategorien werden eingefügt
    // IDs werden später automatisch erzeugt, 
    // Status wird auf Default Wert gesetzt
    public void prepareStatements(){  
    }

    // Hier wird das erste PreparedStatement verwendet, 
    // um ein Buch in die Datenbank einzufuegen
    private void insertBook(String autor, String titel, String kategorien){
    }

    private void insertPerson(String vorname, String nachname){
    }

    // Datei mit csv Daten (Buecher) einlesen, Buecher in DB speichern
    // Anzahl einegfuegter Buecher zurueckgeben
    private int importBooks(String filename){
	String separator = "\\|"; // | ist Sonderzeichen in Reg. Ausdruck
		return 0;
    }

    // Datei mit csv Daten (Personen) einlesen, Personen in DB speichern
    // Anzahl eingefuegter Personen zurueckgeben
    private int importPersons(String filename){
	String separator = "\\|"; // | ist Sonderzeichen in Reg. Ausdruck
	return 0;
    }

    private void disconnect(){
    }

    public static void main(String args[]) {
	if (args.length != 3){
	    System.out.println("Usage: java FillDBuecherei <sqlite db file> <csv buecher file> <csv personen file>");
	    System.exit(0);
	}
	FillDBBuecherei db = new FillDBBuecherei();
	db.connect(args[0]);
	db.init();
	int anzahlBuecher = db.importBooks(args[1]);
	int anzahlPersonen = db.importPersons(args[2]);
	System.out.println("Anzahl eingelesener Buecher: " + anzahlBuecher);
	System.out.println("Anzahl eingelesener Personen: " + anzahlPersonen);
	db.disconnect();
    }

}
