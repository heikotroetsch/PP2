package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class QuestionList {
	private ArrayList<ArrayList<Question>> questions = new ArrayList<ArrayList<Question>>();
	private HashMap<String,Integer> categoryNames;
	
	public QuestionList(){
		Kategorie[] kategorien = Kategorie.values();
		categoryNames = new HashMap<String,Integer>(2*kategorien.length);
		for (int i=0;i<kategorien.length;i++){
			questions.add(new ArrayList<Question>()); // new sublist fpr category
			categoryNames.put(kategorien[i].toString(),new Integer(i));
		}
	}
	
	public void insertQuestions(ArrayList<Question> qList){	
		Iterator<Question> it = qList.iterator();	
		while (it.hasNext()){
			Question q = it.next();
			Kategorie k = q.getKategorie();
			Integer i = categoryNames.get(k.toString());
			if (i != null){
				questions.get(i).add(q); // put in correspinding sublist
			}
		}
	}
	
	public Question randomQuestion(String kName){
		Integer i = categoryNames.get(kName);
		if (i != null){
			ArrayList<Question> listI = this.questions.get(i.intValue());
			int r = (int)(Math.random()* listI.size());
			return listI.get(r);
		} else {
			return null;
		}
	}

}
