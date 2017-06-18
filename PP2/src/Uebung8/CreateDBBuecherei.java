package Uebung8;

import java.sql.*;
import java.util.Properties;

public class CreateDBBuecherei {
    Properties properties;
    private Connection c = null;
    private Statement stmt;
    private PreparedStatement pstm1;


    private void init(){
	properties = new Properties();
	properties.setProperty("PRAGMA foreign_keys", "ON");
    }

    // mit Datenbank buecherei verbinden (bzw. neu oeffnen)
    public void connect (String filename){
    }


    // Relation buch anlegen
    private void createTableBuch(){
    }

    // Relation person anlegen
    private void createTablePerson(){
    }

    // Relation ausgeliehen_von anlegen
    private void createTableAusgeliehen(){
    }

    // Verbindung schliessen
    private void disconnect(){
    }

    public static void main(String args[]) {
	if (args.length != 1){
	    System.out.println("Usage: java CreateDBBuecherei <new sqlite dbfile>");
	    System.exit(0);
	}
	CreateDBBuecherei db = new CreateDBBuecherei();
	db.connect(args[0]);
	db.init();
	db.createTableBuch();
	db.createTablePerson();
	db.createTableAusgeliehen();
	db.disconnect();
    }


}
