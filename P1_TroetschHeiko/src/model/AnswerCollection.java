package model;

import java.util.Vector;

public interface AnswerCollection {
	/* add String answer and mode to collection of answers and modes */
	
	boolean addAnswer(String word, int mode);
	/* checks the stored answers by comparing them to the vocables 
	 * which represent the correct answers/solutions.
	 */
	void check(Vector<Vocable> vocables);
	
	/* returns the index of the next answer that is about to be added */
	int getCurrentIndex();
}
