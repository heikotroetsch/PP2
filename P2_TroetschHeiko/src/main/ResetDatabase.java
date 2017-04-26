package main;

import java.io.File;

import database.CreateDatabase;

/**
 * Diese Methode legt die Datenbank neu an oder ueberschreibt eine vorhandene.
 * Kann eine Weile dauern.
 */
public class ResetDatabase {

	public static void main(String[] args) {
		File datei = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "resources"
				+ System.getProperty("file.separator") + "Adressbuch.db");
		if (datei.exists()) {
			System.out.println("Datenbank geloescht: " + datei.delete());
		}
		new CreateDatabase();
	}

}
