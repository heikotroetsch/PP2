package Uebung8;

import java.sql.*; 
import java.io.File;
import java.util.HashSet;
import java.util.List;

public class SQLiteDBHandler {
    private Connection c = null;
    private Statement stmt;

    public static String quote(String s){
	return "'" + s + "'";
    }
    

    // schliesse ggf Verbindung zu einer anderen Datenbank
    // oeffne neue Verbindung zur angegebenen Datenbank (Dateiname)D
    public boolean connect (String databasefile){
	boolean success = false;
	// ggf. alte Verbindung beenden
	if (c != null){
	    this.disconnect();
	}
	try {
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:" + databasefile);
	    c.setAutoCommit(false);
	    success = checkDBForTables(new String[]{"buch","person","ausgeliehen_von"}); // pruefen, ob es die richtige DB ist

	} catch ( Exception e ) {
	    System.err.println(e.getClass().getName() + ": " + e.getMessage() );
	} finally{
	    return success;
	}
    }

    // gibt true zurueck, wenn die geladene Datenbank 
    // alle Tabellen enthält, deren Namen in dem Feld tablenames
    // gespeichert sind
    private boolean checkDBForTables(String[] tablenames){
	boolean ok = true;

	return ok;
    }

    // neues Buch in Datenbank einfuegen: 
    // Buch mit Titel, Autor und Kategorien
    // gibt die ID des neuen Buchs zurueck
    public int addBuch(Buch b){
	int newID = -1;

	return newID;
    }

    // vorhandenes Buch in Datenbank ueberschreiben: 
    // Buch mit Titel, Autor und Kategorien
    public void updateBuch(Buch b){

     }


    public java.util.List<Buch> getBuchListe(){
	java.util.List<Buch> bookList = new java.util.ArrayList<Buch>(20);

	return bookList;
    }		    

    // alle Buecher suchen und in einer Liste zurueckgeben,
    // die auf ein Suchmuster passen:
    // Teil des Autornamens (als substring), Teile des Titels (als Substring)
    // ein oder mehrere Kategorien (mit log. UND verbunden)
    public java.util.List<Buch> searchBuecher(String autorTeil, String titelTeil, List<Kategorie> kategorien){
	java.util.List<Buch> bookList = new java.util.ArrayList<Buch>(20);

	return bookList;
    }


    public void disconnect(){
	try {
	    c.close();
	} catch(SQLException e){
	    System.err.println(e.getClass().getName() + ": " + e.getMessage() );
	    e.printStackTrace();
	    System.exit(0);
	}
	c = null;
    }
}
