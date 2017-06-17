package Uebung1;

public enum Tee {
	SCHWARZ, GRUEN, ROOIBOS, FRUECHTE;
	
	Aroma aroma;
	int zeit;
	
	Tee(){
	}
	
	Tee(Aroma aroma, int zeit){
		this.aroma = aroma;
		this.zeit = zeit;
	}
	
	public void setZeit(int zeit){
		this.zeit = zeit;
	}
	
	public void setAroma(Aroma aroma){
		this.aroma = aroma;
	}
	
	public String toSting(){
		return "Schwarzer Tee in der Geschmacksrichtung "+this.aroma+" und "+this.zeit+" Minuten";
	}
	
}
