package model;

import java.util.ArrayList;

import view.LearnGUI;

public class LearnSettings implements LearningContext{

	public static final int SOURCE_GIVEN=0,TARGET_GIVEN=1, MIXED=2;
	private ArrayList<Integer> chosenUnits;
	private ArrayList<Integer> chosenSections;
	private int MaxAnzahlVokabeln;
	private int learnMode;
	

	public LearnSettings(ArrayList<Integer> chosenUnits,ArrayList<Integer> chosenSections,int MaxAnzahlVokabeln, int learnMode){
		this.chosenUnits = chosenUnits;
		this.chosenSections = chosenSections;
		this.MaxAnzahlVokabeln = MaxAnzahlVokabeln;
		this.learnMode = learnMode;
	}
	
	@Override
	public boolean matches(Vocable v) {
		boolean isInUnit = false;
		boolean isInSection = false;
		for(int unit: this.chosenUnits){
			if(unit==v.getUnit()){
				isInUnit = true;
			}
		}
		for(int section: this.chosenSections){
			if(section==v.getSection()){
				isInSection = true;
			}
		}
		if(isInUnit&&isInSection){
			return true;
		}
		return false;
	}

	@Override
	public int getMode() {
		if(this.learnMode==LearnGUI.SOURCE_GIVEN||this.learnMode==LearnGUI.TARGET_GIVEN){
			return this.learnMode;
		}else{
			return (int)(Math.random()*2);
		}
	}

	@Override
	public int getSize() {
		return this.MaxAnzahlVokabeln;
	}
	
}
