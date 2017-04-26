package model;

import java.util.ArrayList;

import view.LearnGUI;
/**
 * Class for storing, changing and looking up Learn Context Settings.
 * @author heikotroetsch
 * 
 * @param chosenUnits is a parameter which contains all the Units from which vocab words are selected. 
 * @param choseSections is a parameter which contains all the Sections from which vocab words are selected.
 * @param MaxAnzahlWokabln is the maximum number of vocab words that are in included in one learn session.
 * @param learnMode selects the Mode in wich the vocab words are asked in. (SOURCE_GIVEN=0,TARGET_GIVEN=1, MIXED=2)
 *
 */
public class LearnSettings implements LearningContext{

	public static final int SOURCE_GIVEN=0,TARGET_GIVEN=1, MIXED=2;
	private ArrayList<Integer> chosenUnits;
	private ArrayList<Integer> chosenSections;
	private int MaxAnzahlVokabeln;
	private int learnMode;
	
	/**
	 * Constructor for the creation of one Object from the class LearnSettings. 
	 * @param chosenUnits is a parameter which contains all the Units from which vocab words are selected. 
	 * @param choseSections is a parameter which contains all the Sections from which vocab words are selected.
	 * @param MaxAnzahlWokabln is the maximum number of vocab words that are in included in one learn session.
	 * @param learnMode selects the Mode in wich the vocab words are asked in. (SOURCE_GIVEN=0,TARGET_GIVEN=1, MIXED=2)
	 *
	 */
	public LearnSettings(ArrayList<Integer> chosenUnits,ArrayList<Integer> chosenSections,int MaxAnzahlVokabeln, int learnMode){
		this.chosenUnits = chosenUnits;
		this.chosenSections = chosenSections;
		this.MaxAnzahlVokabeln = MaxAnzahlVokabeln;
		this.learnMode = learnMode;
	}
	
	/**
	 * This method returns a boolean true or false. True if the Vocab word give matches the learn settings of the object. This method specifically checks if unit and section are correct.
	 *@param v will be a vocable which is checked if it fits into the learning context.
	 *@return True if the parameter v matches the learning context and false if not.
	 */
	@Override
	public boolean matches(Vocable v) {
		boolean isInUnit = false;
		boolean isInSection = false;
		if(this.chosenUnits.isEmpty()){
			isInUnit = true;
		}
		if(this.chosenSections.isEmpty()){
			isInSection = true;
		}
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

	/**
	 * This method returns the current Learn Mode that the programm will go through.
	 * @return int SOURCE_GIVEN=0,TARGET_GIVEN=1, MIXED=2
	 */
	@Override
	public int getMode() {
		if(this.learnMode==LearnGUI.SOURCE_GIVEN||this.learnMode==LearnGUI.TARGET_GIVEN){
			return this.learnMode;
		}else{
			return (int)(Math.random()*2);
		}
	}

	/**
	 * @return Max vocab words that are in the learn context.
	 */
	@Override
	public int getSize() {
		return this.MaxAnzahlVokabeln;
	}
	
}
