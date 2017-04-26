package model;

import java.util.Arrays;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 * This is an inner class to represent a Awnser from the tool. Its a simple way to create an Awnser object. This way the awnsers can be easily saved.
 * @author heikotroetsch
 *
 */
class AwnserVocable{
	protected String awnser;
	protected int mode;
	
	protected AwnserVocable(String awnser, int mode){
		this.awnser = awnser;
		this.mode = mode;
	}
}

/**
 * This class is used to make an Awnser list and has multiple methods to compare vocab.
 * @author heikotroetsch
 *
 */
public class AnswerList implements AnswerCollection{
	
	private Vector<AwnserVocable> vocableAnswers;
	public static final int SOURCE_GIVEN=0,TARGET_GIVEN=1;
	private int min;
	
	/**
	 * AnswerList is a Constructor to create an new list of Answers and set the minimum. 
	 * @param min
	 */
	public AnswerList (int min){
		this.vocableAnswers = new Vector<AwnserVocable>();
		this.min = min;
	}
	
	
	/**
	 * This method adds an awnser to the Awnser list.
	 */
	@Override
	public boolean addAnswer(String word, int mode) {
		this.vocableAnswers.add(new AwnserVocable(word, mode));
		return vocableAnswers.size()==min;
	}

	/**
	 * This method checks a list of Vocables against the Awnsers inputted. It creates a Index that represents how well a vocable is learned.
	 * At the end this method returns a Message box that lists the Learning index and shows the awnsers and words.
	 */
	@Override
	public void check(Vector<Vocable> vocables) {
		StringBuffer result = new StringBuffer("Results: \n\n\n");
		double fehler = 100/vocableAnswers.size();
		//goes through all words in the lists
		for(int i = 0; i<vocableAnswers.size(); i++){
			//if the mode of the typed word was with the target given
			if(vocableAnswers.elementAt(i).mode == TARGET_GIVEN){
				//compares awnser to source word
				result.append("Word:\t\t"+vocables.elementAt(i)+"\n");
				result.append("Awnser:\t\t"+vocableAnswers.elementAt(i).awnser+"\n");
				vocables.elementAt(i).setLFactor(vocables.elementAt(i).getlFactor()+compareWords(vocableAnswers.elementAt(i).awnser, vocables.elementAt(i).getWord()));
				result.append("Learning Factor:\t"+vocables.elementAt(i).getlFactor()+"\n\n");
			}else if(vocableAnswers.elementAt(i).mode == SOURCE_GIVEN){
				//compares awnser to all translations and selects the smallest
				double temp[] = new double[vocables.elementAt(i).getTranslations().size()];
				for(int p = 0; p<vocables.elementAt(i).getTranslations().size() ;p++){
					temp[p] = compareWords(vocableAnswers.elementAt(i).awnser, vocables.elementAt(i).getTranslations().get(p));
				}
				Arrays.sort(temp);
				result.append("Word:\t\t"+vocables.elementAt(i)+"\n");
				result.append("Awnser:\t\t"+vocableAnswers.elementAt(i).awnser+"\n");
				vocables.elementAt(i).setLFactor(vocables.elementAt(i).getlFactor()+temp[0]);
				result.append("Learning Factor:\t"+vocables.elementAt(i).getlFactor()+"\n\n");
			}
		}
		
		JOptionPane.showMessageDialog(null, result);
	}

	/**
	 * This Method is used by the Check method and is the main implementation of the learning index. This method compares two Strings and creates a double value that represents how close the two strings are.
	 * @param wordOne
	 * @param wordTwo
	 * @return
	 */
	public double compareWords(String wordOne, String wordTwo){
		double result = 0;
			if(wordOne.toLowerCase().matches(wordTwo.toLowerCase())){
				result += 5;
				return result;
			}else{
				if(Math.abs(wordOne.compareTo(wordTwo))==0){
					result +=10;
				}else if(Math.abs(wordOne.compareTo(wordTwo))==1){
					result +=6;
				}else if(Math.abs(wordOne.compareTo(wordTwo))>=2){
				}else {
					result -=5;
				}
				return result;
			}
	}
	
	/**
	 * A method to get the Current Index. 
	 */
	@Override
	public int getCurrentIndex() {
		return vocableAnswers.size();
	}

}
