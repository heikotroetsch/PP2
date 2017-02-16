package Uebung1;
enum Power{
    AN, AUS
}

enum Level {
    NIEDRIG, MITTEL, HOCH
}

public class Staubsauger{
    Power power = Power.AUS;
    Level level = Level.NIEDRIG; 

    public void power(){
	this.power = (this.power.equals(Power.AN)) ? Power.AUS : Power.AN;
    }

    public void up(){
	if (this.power.equals(Power.AN)){
	    switch (this.level) {
	    case NIEDRIG: this.level = Level.MITTEL; break;
	    case MITTEL: this.level = Level.HOCH; break;
	    default: break;
	    }
	}

	// alternativ: neu berechnen
	// Level[] allLevels = Level.values();
	// this.level = allLevels[Math.min(allLevels.length - 1, this.level.ordinal() + 1)];
	
    }

    public void down(){
	if (this.power.equals(Power.AN)){
	    switch (this.level) {
	    case MITTEL: this.level = Level.NIEDRIG; break;
	    case HOCH: this.level = Level.MITTEL; break;
	    default: break;
	    }
	}

	// alternativ: neu berechnen
	// Level[] allLevels = Level.values();
	// this.level = allLevels[Math.max(0, this.level.ordinal() - 1)];
    }

    public String toString(){
	if (this.power.equals(Power.AUS)){
	    return "Staubsauger ist aus";
	} else {
	    return "Staubsauger ist an und auf Stufe " +  this.level;
	}
    }

    public static void main(String[] args){
	Staubsauger vc = new Staubsauger();
	System.out.println(vc);
	vc.power();
	for (int i=1;i<=5;i++){
	    vc.up();
	}
	System.out.println(vc);
	vc.down();
	System.out.println(vc);
    }
}
