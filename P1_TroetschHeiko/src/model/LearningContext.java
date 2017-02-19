package model;

public interface LearningContext {
	/* Calculates whether the vocable v matches the learning context 
	 * and returns true if this is the case and false otherwise 
	 */
	boolean matches(Vocable v);
	
	/* returns learning mode: value should be one of 
	 * 
	 */
	public int getMode();
	public int getSize();
}
