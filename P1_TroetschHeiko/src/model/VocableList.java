package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class VocableList {
	private ArrayList<Vocable> vList;
	
	public VocableList(){
		this.vList = new ArrayList<Vocable>();
	}
	
	public VocableList(ArrayList<Vocable> lis){
		this.vList = new ArrayList<Vocable>(lis);
	}
	
	public void add(Vocable v){
		this.vList.add(v);
	}
	
	public ArrayList<Vocable> getVocableList(){
			return this.vList;
	}
	
	public int size(){
		return this.vList.size();
	}
	
	public boolean contains(Vocable v){
		return this.vList.contains(v);
	}
	

	/* creates a subset of vocables chosen from the current vocable list
	 * (GlobalData.vocableList) which fulfills the requirements defined
	 * in the LearnSettings instance 
	 * if there are not enough vocables fulfilling the requirements
	 * the result list might be smaller (and even empty)
	 */
	public Collection<Vocable> chooseLearningList(LearningContext ls){
		// collect all matching vocables
		LinkedList<Vocable> matchingVoc = new LinkedList<Vocable>(); 
		for(Vocable v: vList){
			if(ls.matches(v)){
				matchingVoc.add(v);
			}
		}
		
		
		
		Collections.sort(matchingVoc);
		while(matchingVoc.size()>ls.getSize()){
			matchingVoc.removeFirst();
		}
		
		Collections.shuffle(matchingVoc);
		
		
		return matchingVoc;
	}
	
	/* looks for the word w1 (if specified) in all vocables
	 * in the word field (source language) and for the word 
	 * w2 in the possible translations. 
	 * Returns a collections of all vocables that fit.
	 * The search item has to at least 2 letters long
	 */
	public Collection<Vocable> find(String w1, String w2){
		String word1 = w1;
		String word2= w2;
		
		System.out.println(word1);
		System.out.println(word2);

		LinkedList<Vocable> matchingVoc = new LinkedList<Vocable>(); 
		LinkedList<Vocable> matchingVocWord = new LinkedList<Vocable>(); 
		LinkedList<Vocable> matchingVocStrings = new LinkedList<Vocable>(); 
		for (Vocable v: vList) {
			if(v.getWord().contains(word1)){
				matchingVocWord.add(v);
			}
				for(String s: v.getTranslations()){
					if(s.contains(word2)){
						matchingVocStrings.add(v);
					}
				}
			}
		
		for(Vocable v: matchingVocWord){
			if(matchingVocStrings.contains(v)){
				matchingVoc.add(v);
			}
		}
		return matchingVoc;
	}
	
	
}
