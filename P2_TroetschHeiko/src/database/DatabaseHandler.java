package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import domain.Kontakt;
import domain.Wohnort;

/**
 * Ueber diese Klasse wird die Datenbank veraendert.
 */
public class DatabaseHandler extends Database {

	private PreparedStatement insertStatement;
	private PreparedStatement getWohnorteStatement;
	private PreparedStatement getKontaktStatement;
	private PreparedStatement countKontakteStatement;
	private PreparedStatement deleteKontaktStatement;

	
	public DatabaseHandler(){
		try {
			insertStatement = connection.prepareStatement("INSERT INTO KONTAKTE VALUES (?, ?, ?, ?, ?, ?);");
			getWohnorteStatement = connection.prepareStatement("SELECT * FROM WOHNORTE WHERE plz LIKE ? AND ort LIKE ?;");
			getKontaktStatement = connection.prepareStatement("SELECT kontakte.id as kontakteid, kontakte.name, kontakte.nummer, kontakte.mail, kontakte.strasse, kontakte.wohnort, wohnorte.id as wohnorteid, wohnorte.plz, wohnorte.ort, wohnorte.bundesland FROM KONTAKTE LEFT JOIN WOHNORTE ON kontakte.wohnort = wohnorte.id WHERE kontakte.name LIKE ? AND wohnorte.ort LIKE ? OR wohnorte.plz LIKE ? ORDER BY kontakte.name;");
			countKontakteStatement = connection.prepareStatement("SELECT * FROM Kontakte;");
			deleteKontaktStatement = connection.prepareStatement("DELETE from KONTAKTE where ID=?;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	
	/**
	 * Fuegt einen neuen Kontakt in die Datenbank ein.
	 * 
	 * @param kontakt
	 *            Kontakt der eingefuegt werden soll.
	 */
	public void insertKontakt(Kontakt kontakt) {
		try {
			int wohnortID;
			if(kontakt.getWohnort()==null){
				wohnortID = 0;
			}else{
				wohnortID = kontakt.getWohnort().getId();
			}
			insertStatement.setString(2, kontakt.getName());
			insertStatement.setString(3, kontakt.getNummer());
			insertStatement.setString(4, kontakt.getMail());
			insertStatement.setString(5, kontakt.getStrasse());
			insertStatement.setInt(6, wohnortID);
			insertStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * Gibt eine Liste mit Wohnorten zurueck. Wichtig fuer Anlegen eines neuen
	 * Kontakts.
	 * 
	 * @param plz
	 *            (Teil der) PLZ des gesuchten Wohnorts. Kann auch null sein!
	 * @param ort
	 *            (Teil vom) Ort des gesuchten Wohnorts. Kann auch null sein!
	 * @return Liste der passenden Wohnorte.
	 */
	public List<Wohnort> getWohnorte(String plz, String ort) {
		List<Wohnort> result = new ArrayList<Wohnort>();
	    try {
	    	ResultSet rs;

	    	if(plz==null){
	    		plz = "";
	    	}
	    	if(ort==null){
	    		ort = "";
	    	}
	    	getWohnorteStatement.setString(1, plz);
	    	getWohnorteStatement.setString(2, ort);
			rs = getWohnorteStatement.executeQuery();
			while (rs.next()) {
				result.add(new Wohnort(rs.getInt("id"), rs.getString("plz"), rs.getString("ort"), rs.getString("bundesland")));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Gibt alle Kontakte alphabetisch sortiert zurueck, die Name und/oder Ort
	 * beinhalten. Wichtig fuer Suchfunktion.
	 * 
	 * @param name
	 *            (Teil von) Name des gesuchten Kontakts. Kann auch null sein!
	 * @param ort
	 *            Entweder (Teil der) PLZ oder (Teil vom) Ort des gesuchten Kontakts. 
	 *            Kann auch null sein!
	 * @return Liste aller passenden Kontakte.
	 */
	public List<Kontakt> getKontakte(String name, String ort) {
		List<Kontakt> result = new ArrayList<Kontakt>();

	    try {
	    	Statement stmt = connection.createStatement();
	    	ResultSet rs;

	    	if(name == null){
	    		name = "";
	    	}
	    	if(ort==null){
	    		ort = "";
	    	}
	    	getKontaktStatement.setString(1, "%"+name+"%");
	    	getKontaktStatement.setString(2, "%"+ort+"%");
	    	getKontaktStatement.setString(3, "%"+ort+"%");
	    	
			rs = getKontaktStatement.executeQuery();
			while (rs.next()) {
				Wohnort w = new Wohnort(rs.getInt("wohnorteid"), rs.getString("plz"), rs.getString("ort"), rs.getString("bundesland"));
				Kontakt k = new Kontakt(rs.getInt("kontakteid"), rs.getString("name"), rs.getString("nummer"), rs.getString("mail"), rs.getString("strasse"), w);
				result.add(k);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * @return Anzahl aller Kontakte.
	 */
	public int countKontakte(){
    	int count = 0;
		try {
        	ResultSet rs;
			rs = countKontakteStatement.executeQuery();
			
			while(rs.next()){
				count++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * Loescht den angegebenen Kontakt aus der Datenbank.
	 * 
	 * @param kontakt
	 *            Kontakt der geloescht werden soll.
	 */
	public void deleteKontakt(Kontakt kontakt) {
		try {
			deleteKontaktStatement.setInt(1, kontakt.getId());
			deleteKontaktStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	}
	
	/**
	 * Aendert einen bestehenden Kontakt. Die id bleibt gleich.
	 * 
	 * @param original
	 *            Der zu aendernde Kontakt.
	 * @param neu
	 *            Die neuen Kontaktdaten ohne id (id=0).
	 */
	public void editKontakt(Kontakt original, Kontakt neu) {
		deleteKontakt(original);
		Kontakt k = new Kontakt(original.getId(), neu.getName(), neu.getNummer(), neu.getMail(), neu.getStrasse(), neu.getWohnort());
		insertKontakt(k);
	}

}
