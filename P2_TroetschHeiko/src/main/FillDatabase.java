package main;

import database.DatabaseHandler;
import domain.Kontakt;
import domain.Wohnort;

/**
 * Die Klasse dient zum Fuellen der Datenbank mit Beispielen.
 */
public class FillDatabase {

	/**
	 * Fuellt die (bestehende) Datenbank "kontakte" mit Beispielen. Hierzu wird
	 * ausschliesslich die insertKontakt-Methode in DatabaseHandler benoetigt.
	 * 
	 * @param args
	 *            Nicht beruecksichtigt.
	 */
	public static void main(String[] args) {

		DatabaseHandler database = new DatabaseHandler();

		database.insertKontakt(new Kontakt(0, "Aristoteles", "0621 384322", "aristoteles@mail.uni-mannheim.de",
				"Bismarckstrasse 1", new Wohnort(1980, "", "", "")));
		database.insertKontakt(new Kontakt(0, "Demokrit", "0621 460371", "demokrit@mail.uni-mannheim.de",
				"Bismarckstrasse 3", new Wohnort(1980, "", "", "")));
		database.insertKontakt(new Kontakt(0, "Epikuros", "0621 341270", null, null, new Wohnort(67059, "", "", "")));
		database.insertKontakt(new Kontakt(0, "Heraklit", null, "heraklit@mail.de", "Philosophenweg 2",
				new Wohnort(1982, "", "", "")));
		database.insertKontakt(
				new Kontakt(0, "Parmenides", null, "parmenides@mail.de", null, new Wohnort(2905, "", "", "")));
		database.insertKontakt(new Kontakt(0, "Platon", "089 427347", "platon@mail.de", "Blumenstrasse 23",
				new Wohnort(2906, "", "", "")));
		database.insertKontakt(
				new Kontakt(0, "Plotin", "07254 205270", "plotin@mail.de", null, new Wohnort(2001, "", "", "")));
		database.insertKontakt(new Kontakt(0, "Pythagoras", null, "pythagoras@mail.de", "Philosophenweg 42",
				new Wohnort(1980, "", "", "")));
		database.insertKontakt(new Kontakt(0, "Sokrates", null, "sokrates@mail.de", null, null));
		database.insertKontakt(new Kontakt(0, "Thales", null, null, "Rundweg 7", new Wohnort(1351, "", "", "")));
		
		System.out.println("Datenbank gefuellt!");
	}

}
