package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Diese Klasse repraesentiert die zugrundeliegende Datenbank. Ueber das
 * Attribut connection kann man darauf zugreifen.
 */
public class Database {

	protected Connection connection;

	public Database() {
		this.connect(System.getProperty("user.dir") + System.getProperty("file.separator") + "resources"
				+ System.getProperty("file.separator") + "Adressbuch.db");
	}

	/**
	 * Stellt die Verbindung mit der Datenbank her.
	 * 
	 * @param file
	 *            Name und Pfad der Datenbank
	 */
	protected void connect(String file) {
		try {
			Class.forName("org.sqlite.JDBC");
			this.connection = DriverManager.getConnection("jdbc:sqlite:" + file);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
