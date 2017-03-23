package io;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Locale;

import javax.swing.text.html.parser.ParserDelegator;

import model.Vocable;

public class CSVReader extends LineReader{

	@Override
	protected Vocable parseLine(String line) {
		//eng;deu;tree;Baum;big plant with green leafs|Christmas tree|CS:
		//a data structure for  hierarchies;1; ;0.0
		StringBuffer s = new StringBuffer(line);
		LinkedList<String> data = new LinkedList<String>();
		while(s.length()!=0){
			StringBuffer parsedWord = new StringBuffer("");
			while(s.charAt(0)!=IOSettings.entrySep&&s.length()!=0){
				parsedWord.append(line.charAt(0));
				s.deleteCharAt(0);
			}
			s.deleteCharAt(0);
			data.add(parsedWord.toString());
		}
		
		Locale src = new Locale(data.pop());
		Locale target = new Locale(data.pop());
		String word = data.pop();
		
		StringBuffer trans = new StringBuffer(data.pop());
		LinkedList<String> translations = new LinkedList<String>();
		while(trans.length()!=0){
			StringBuffer parsedWord = new StringBuffer("");
			while(trans.charAt(0)!=IOSettings.fieldSep&&trans.length()!=0){
				parsedWord.append(line.charAt(0));
				trans.deleteCharAt(0);
			}
			trans.deleteCharAt(0);
			translations.add(parsedWord.toString());
		}
		
		StringBuffer ex = new StringBuffer(data.pop());
		LinkedList<String> examples = new LinkedList<String>();
		while(ex.length()!=0){
			StringBuffer parsedWord = new StringBuffer("");
			while(ex.charAt(0)!=IOSettings.fieldSep&&ex.length()!=0){
				parsedWord.append(line.charAt(0));
				ex.deleteCharAt(0);
			}
			ex.deleteCharAt(0);
			examples.add(parsedWord.toString());
		}
		
		int unit = Integer.parseInt(data.pop());
		int section = Integer.parseInt(data.pop());
		return new Vocable(src, target, word, translations,  examples, unit, section);

	}

	
	
	
}
