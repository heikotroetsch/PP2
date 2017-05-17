package data;

import java.awt.Color;

public enum Kategorie {
	JAVA(new Color(99,184,255)), SPORT(new Color(255,160,100)), GESCHICHTE(new Color(255,255,0));
	
	private Color color;
	
	private Kategorie(Color c){
		this.color = c;
	}
	
	public Color getColor(){
		return this.color;
	}
}
