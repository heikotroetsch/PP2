package main;

import database.DatabaseHandler;
import ui.Adressbuch;

/**
 * Startet das Hauptprogramm
 */
public class Main {

	public static DatabaseHandler database;

	public static void main(String[] args) {

		database = new DatabaseHandler();
		new Adressbuch();

	}

}
