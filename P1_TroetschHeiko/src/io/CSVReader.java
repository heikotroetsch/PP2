package io;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Locale;

import javax.swing.text.html.parser.ParserDelegator;

import model.Vocable;

/**
 * This Class is used to Read a csv file and create a vocable list.
 * 
 * The Class extends the Class LineReader.
 * @author heikotroetsch
 *
 */
public class CSVReader extends LineReader{
	//eng;deu;tree;Baum;big plant with green leafs|Christmas tree|CS: a data structure for hierarchies;1; ;0.0

	@Override
	/**
	 * This method is used to create a Vocable from a singe String line. 
	 */
	protected Vocable parseLine(String line) {
		//eng;deu;tree;Baum;big plant with green leafs|Christmas tree|CS: a data structure for hierarchies;1; ;0.0
		StringBuffer s = new StringBuffer(line);
		LinkedList<String> data = new LinkedList<String>();
		while(s.length()!=0){
			StringBuffer parsedWord = new StringBuffer("");
			while(s.length()!=0&&s.charAt(0)!=IOSettings.entrySep){
				parsedWord.append(s.charAt(0));
				s.deleteCharAt(0);
			}
			if(s.length()!=0&&s.charAt(0)==IOSettings.entrySep){
			s.deleteCharAt(0);
			}
			data.add(parsedWord.toString());
		}
		
		Locale src = IOSettings.getLocale((data.pop()));
		Locale target = IOSettings.getLocale((data.pop()));
		String word = data.pop();
		
		StringBuffer trans = new StringBuffer(data.pop());
		LinkedList<String> translations = new LinkedList<String>();
		while(trans.length()!=0){
			StringBuffer parsedWord = new StringBuffer("");
			while(trans.length()!=0&&trans.charAt(0)!=IOSettings.fieldSep){
				parsedWord.append(trans.charAt(0));
				trans.deleteCharAt(0);
			}
			if(trans.length()!=0&&trans.charAt(0)==IOSettings.fieldSep){
			trans.deleteCharAt(0);
			}
			translations.add(parsedWord.toString());
		}
		
		StringBuffer ex = new StringBuffer(data.pop());
		LinkedList<String> examples = new LinkedList<String>();
		while(ex.length()!=0){
			StringBuffer parsedWord = new StringBuffer("");
			while(ex.length()!=0&&ex.charAt(0)!=IOSettings.fieldSep){
				parsedWord.append(ex.charAt(0));
				ex.deleteCharAt(0);
			}
			if(ex.length()!=0){
				ex.deleteCharAt(0);
			}
			examples.add(parsedWord.toString());
		}
		int unit = 0;
		try{
			unit = Integer.parseInt(data.pop());
		}
		catch (NumberFormatException e) {
		}
		
		int section = 0;
		try{
		section = Integer.parseInt(data.pop());
		}
		catch (NumberFormatException e) {
		}
		return new Vocable(src, target, word, translations,  examples, unit, section);

	}

	
	
	
}
