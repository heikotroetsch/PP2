package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;


public class Vocable implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Locale sourceLanguage, targetLanguage;  // Language locals for word and translations
	private String word;                            // word in source language                          
	private ArrayList<String> translations = new ArrayList<String>(); // words in target language
	private ArrayList<String> examples = new ArrayList<String>();     // examples of usage (of the word)
	private int unit = -1;            // unit number (-1 is default and means "no unit")
	private int section = -1;         // section number (-1 is default and means "no section")
	private double lFactor = 0.0;     // a value of how well the vocable is "learned"
	
	public Vocable(Locale src, Locale target, String word, Collection<String> translations, Collection<String> examples){
		this.sourceLanguage = src;
		this.targetLanguage = target;
		this.word = word;
		this.translations.addAll(translations);
		if (examples != null){
			this.examples.addAll(examples);
		}
	}
	
	public Vocable(Locale src, Locale target, String word, Collection<String> translations,  Collection<String> examples, int unit, int section){
		this(src, target, word,translations,examples);
		this.unit = unit;
		this.section = section;
	}
	
	public Vocable(Locale src, Locale target, String word, String[] translations, String[] examples){
		this.sourceLanguage = src;
		this.targetLanguage = target;
		this.word = word;
		for (String s : translations){
			this.translations.add(s);
		}
		for (String s : examples){
			this.examples.add(s);
		}		
	}

	/* produces a short description of the vocable 
	 * used to produce an entry in the list of search results (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		StringBuffer sBuf = new StringBuffer();
		sBuf.append(String.format("%-20s", this.word) + " -  ");
		for (String s : this.translations){
			if ((s!=null) && (s.length() > 0)){
				sBuf.append(s + "; ");
			}
		}
		return 	sBuf.toString(); 
	}
		
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public ArrayList<String> getTranslations() {
		return translations;
	}

	public void setTranslations(ArrayList<String> translations) {
		this.translations = translations;
	}

	public int getUnit() {
		return this.unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public int getSection() {
		return this.section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	public ArrayList<String> getExamples() {
		return this.examples;
	}

	public void setExamples(ArrayList<String> examples) {
		this.examples = examples;
	}
	
	public Locale getSourceLanguage() {
		return sourceLanguage;
	}

	public void setSourceLanguage(Locale sourceLanguage) {
		this.sourceLanguage = sourceLanguage;
	}

	public Locale getTargetLanguage() {
		return targetLanguage;
	}
	
	public void setTargetLanguage(Locale targetLanguage) {
		this.targetLanguage = targetLanguage;
	}
	
	public double getlFactor(){
		return this.lFactor;
	}
	
	public void setLFactor(double lf_new){
		this.lFactor = lf_new;
	}
	
	public String getRandomTranslation(){
		int i = (int)(Math.random() * this.translations.size());
		return this.translations.get(i);
	}
	
}
