package Uebung1;
public enum Tee {
    GRUEN(Aroma.NONE,3),
    SCHWARZ(Aroma.NONE,4),
    ROOIBOS(Aroma.NONE,10),
    FRUECHTE(Aroma.NONE,15);

    private Aroma aromatisierung;
    private int ziehzeit;

    private Tee(Aroma a, int t){
	aromatisierung = a;
	ziehzeit = t;
    }

    public String toString(){
	StringBuffer sBuf = new StringBuffer();
	switch(this) {
	case GRUEN: sBuf.append("Gruener Tee "); break;
	case SCHWARZ: sBuf.append("Schwarzer Tee "); break;
	case ROOIBOS: sBuf.append("Rooibos Tee "); break;
	case FRUECHTE: sBuf.append("Fruechte-Tee "); break;
	default:  sBuf.append("Unbekannter Tee "); break;
	}
	if (this.aromatisierung.equals(Aroma.NONE)) {
	    sBuf.append("mit ");
	}
	else {
	    sBuf.append("in der Geschmacksrichtung ");
	    sBuf.append(this.aromatisierung);
	    sBuf.append(" mit ");
	}
 	sBuf.append(this.ziehzeit);
 	sBuf.append(" Minuten Ziehzeit. ");
	return sBuf.toString();
  }

    public void setAroma(Aroma a){
	aromatisierung = a;
    }

    public void setZiehzeit(int t){
	ziehzeit = t;
    }

}
