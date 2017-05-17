package data;

import java.io.Serializable;

public class Question implements Serializable{
	private static final long serialVersionUID = 1L;
	private Kategorie kategorie;
	public Kategorie getKategorie() {
		return kategorie;
	}

	private String frage;
	private String[] auswahl;
	private int antwort;
	
	public Question(Kategorie k, String q, String a1, String a2, String a3,  String a4, int lsg){
		this.kategorie = k;
		this.frage = q;
		this.auswahl = new String[] {a1,a2,a3,a4};
		this.antwort = lsg;
	}
	
	/* almost copy constructor ;-) 
	 * copies everything bit correct answer 
	 * */
	public Question(Question q2){
		this.kategorie = q2.kategorie;
		this.frage = q2.frage;
		this.auswahl = new String[q2.auswahl.length];
		System.arraycopy(q2.auswahl, 0, this.auswahl, 0, q2.auswahl.length);
		this.antwort = -1;  // leave answer open (unknown)
	}

	public String getFrage() {
		return frage;
	}

	public String[] getAuswahl() {
		return auswahl;
	}
	
	public String getAuswahl(int i) {
		if ((i >=0) && (i<auswahl.length)){
			return auswahl[i];
		} else {
			return "Ungültiger Wert für " + i;
		}
	}

	public int getAntwort() {
		return antwort;
	}
	
	public String toString(){
		if (this.frage.length() > 25) {
            return this.frage.substring(0, 25) + "...";
        } else {
            return this.frage;
        }
	}

	public void setKategorie(Kategorie kategorie) {
		this.kategorie = kategorie;
	}

	public void setFrage(String frage) {
		this.frage = frage;
	}

	public void setAuswahl(String[] auswahl) {
		this.auswahl = auswahl;
	}

	public void setAntwort(int antwort) {
		this.antwort = antwort;
	}

}
 