package domain;

/**
 * Diese Klasse speichert alle Informationen eines Wohnorts, insbesondere auch
 * die ID.
 */
public class Wohnort {

	private int id;
	private String plz, name, bundesland;

	public Wohnort(int id, String plz, String name, String bundesland) {
		this.id = id;
		this.plz = plz;
		this.name = name;
		this.bundesland = bundesland;
	}

	/**
	 * Kopiert einen Wohnort.
	 * 
	 * @param wohnort
	 *            Zu kopierender Wohnort.
	 */
	public Wohnort(Wohnort wohnort) {
		this.id = wohnort.id;
		this.plz = wohnort.plz;
		this.name = wohnort.name;
		this.bundesland = wohnort.bundesland;
	}

	public String getPlz() {
		return plz;
	}

	public String getName() {
		return name;
	}

	public String getBundesland() {
		return bundesland;
	}

	public int getId() {
		return id;
	}

	public String toString() {
		return plz + " "+name+", " + bundesland;
	}

}
