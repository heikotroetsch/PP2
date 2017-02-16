package Uebung1;
class Adresse {
    private String name;
    private String strasse;
    private int hausnummer;
    private String plz;
    private String ort;

    public Adresse(String name, String strasse, int hausnummer, String plz, String ort){
	this.name = name;
	this.strasse = strasse;
	this.hausnummer = hausnummer;
	this.plz = plz;
	this.ort = ort;
    }
    
    public Adresse(Adresse adr2){
	this.name = adr2.name;
	this.strasse = adr2.strasse;
	this.hausnummer = adr2.hausnummer;
	this.plz = adr2.plz;
	this.ort = adr2.ort;	
    }

    public String toString(){
	StringBuffer sbuf = new StringBuffer();
	sbuf.append(name + ", ");
	sbuf.append(strasse + " " + hausnummer + ", ");
	sbuf.append(plz + " " + ort);
	return sbuf.toString();
    }
}
