package model;

import java.util.Locale;

public class GlobalData {
	public static final Locale ENGLISH = Locale.ENGLISH;
	public static final Locale GERMAN = Locale.GERMAN;
	
	/* example vocable
	 */
	public static final Vocable dummyVoc = new Vocable(ENGLISH, GERMAN, "english", 
			new String[]{"englisch"}, 
			new String[]{"Teacher: \"In english, please\"",
	                     "Most People in Australia speak english."});
	
	/* list of vocables loaded and currently in use
	 */
	public static VocableList currentLoadList;  // list of all vocables (loades ones + added ones)
	
	static {
		currentLoadList = new VocableList();
		currentLoadList.add(dummyVoc);   // init list with one dummy entry
	}
	
	                     
	/* vocable selected from search interface (GUI)
	 */
	public static Vocable searchVocable = null;
	
}
