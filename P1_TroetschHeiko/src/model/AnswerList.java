package model;

import java.util.Arrays;
import java.util.Vector;

class AwnserVocable{
	protected String awnser;
	protected int mode;
	
	protected AwnserVocable(String awnser, int mode){
		this.awnser = awnser;
		this.mode = mode;
	}
}

public class AnswerList implements AnswerCollection{
	
	private Vector<AwnserVocable> vocableAnswers;
	public static final int SOURCE_GIVEN=0,TARGET_GIVEN=1;
	double lernfortschritt = 100;
	
	public AnswerList (int min){
		
	}
	
	@Override
	public boolean addAnswer(String word, int mode) {
		this.vocableAnswers.add(new AwnserVocable(word, mode));
		return true;
	}

	@Override
	public void check(Vector<Vocable> vocables) {
		double fehler = 100/vocableAnswers.size();
		//goes through all words in the lists
		for(int i = 0; i<vocableAnswers.size(); i++){
			//if the mode of the typed word was with the target given
			if(vocableAnswers.elementAt(i).mode == TARGET_GIVEN){
				//compares awnser to source word
				lernfortschritt -= compareWords(vocableAnswers.elementAt(i).awnser, vocables.elementAt(i).getWord(), fehler);
			}else if(vocableAnswers.elementAt(i).mode == SOURCE_GIVEN){
				//compares awnser to all translations and selects the smallest
				double temp[] = new double[vocables.elementAt(i).getTranslations().size()];
				for(int p = 0; p<vocables.elementAt(i).getTranslations().size() ;p++){
					temp[p] = compareWords(vocableAnswers.elementAt(i).awnser, vocables.elementAt(i).getTranslations().get(p), fehler);
				}
				Arrays.sort(temp);
				lernfortschritt -= temp[0];
			}
		}
		
	}

	
	public double compareWords(String wordOne, String wordTwo, double bigFehler){
		double result = 0;
			if(wordOne.toLowerCase().matches(wordTwo.toLowerCase())){
				result -= bigFehler/2;
				return result;
			}else{
				result -= bigFehler;
					result += bigFehler* (Math.abs(wordOne.compareTo(wordTwo)) / wordOne.length());
				return result;
			}
	}
	
	@Override
	public int getCurrentIndex() {
		return vocableAnswers.size()+1;
	}

}
