package domain;

/**
 * Diese Klasse speichert alle Informationen eines Kontakts, insbesondere auch
 * den Wohnort.
 */
public class Kontakt {

	private int id;
	private String name, nummer, strasse, mail;
	private Wohnort wohnort;

	public Kontakt(int id, String name, String nummer, String mail, String strasse, Wohnort wohnort) {
		this.id = id;
		this.name = name;
		this.nummer = nummer;
		this.strasse = strasse;
		this.mail = mail;
		this.wohnort = wohnort;
	}

	/**
	 * Kopiert einen gegebenen Kontakt k.
	 * 
	 * @param k
	 *            Zu kopierender Kontakt.
	 */
	public Kontakt(Kontakt k) {
		this.id = k.id;
		this.name = k.name;
		this.nummer = k.nummer;
		this.strasse = k.strasse;
		this.mail = k.mail;
		this.wohnort = new Wohnort(k.wohnort);
	}

	public int getId(){
		return id;
	}
	
	public String getName() {
		return name;
	}

	public String getNummer() {
		return nummer;
	}

	public String getStrasse() {
		return strasse;
	}

	public String getMail() {
		return mail;
	}

	public Wohnort getWohnort() {
		return wohnort;
	}

	public void setWohnort(Wohnort wohnort) {
		this.wohnort = wohnort;
	}

	public String toString() {
		if (wohnort != null && wohnort.getName() != null) {
			return name + " in " + wohnort.getName() + ", " + wohnort.getBundesland();
		} else {
			return name;
		}
	}

}
