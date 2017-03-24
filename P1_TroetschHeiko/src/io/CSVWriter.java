package io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import model.Vocable;

public class CSVWriter extends VocableWriter {
	
	LinkedList<String> totalRepresentation = new LinkedList<String>();
	
	@Override
	protected void mapVoc(Vocable v) throws EmptyVocabAttribute {
		// write single vocable to intermediate representation
		//eng;deu;tree;Baum;big plant with green leafs|Christmas tree|CS:
		//a data structure for  hierarchies;1; ;0.0
		if(v == null){
			throw new EmptyVocabAttribute();
		}
		StringBuffer intermediateRepresentation = new StringBuffer();
		intermediateRepresentation.append(v.getSourceLanguage().getISO3Language()+IOSettings.entrySep);
		intermediateRepresentation.append(v.getTargetLanguage().getISO3Language()+IOSettings.entrySep);
		intermediateRepresentation.append(v.getWord()+IOSettings.entrySep);
		for(String s: v.getTranslations()){
			intermediateRepresentation.append(s+IOSettings.fieldSep);
		}
		intermediateRepresentation.deleteCharAt(intermediateRepresentation.length()-1);
		intermediateRepresentation.append(IOSettings.entrySep);
		for(String s: v.getExamples()){
			intermediateRepresentation.append(s+IOSettings.fieldSep);
		}
		intermediateRepresentation.deleteCharAt(intermediateRepresentation.length()-1);
		intermediateRepresentation.append(IOSettings.entrySep);
		intermediateRepresentation.append(v.getUnit());
		intermediateRepresentation.append(IOSettings.entrySep);
		intermediateRepresentation.append(v.getSection());
		intermediateRepresentation.append(IOSettings.entrySep);
		intermediateRepresentation.append(v.getlFactor());
		intermediateRepresentation.append("\n");

		totalRepresentation.add(intermediateRepresentation.toString());
	}

	public class CSVWriteException extends IOException {
		public CSVWriteException(String message) {
			super("An CSVWrite Exception Occured while writing the file");
			System.out.println("An CSVWrite Exception Occured while writing the file");
	    }
	}
	
	public class FileWriterException extends CSVWriteException {
		public FileWriterException() {
			super("The File Writer could not write the file");
			System.out.println("The File Writer could not write the file");
	    }
	}
	public class EmptyVocabListException extends CSVWriteException {
		public EmptyVocabListException() {
			super("There was no vocab List to be written");
			System.out.println("There was no vocab List to be written");
	    }
	}
	public class EmptyVocabAttribute extends CSVWriteException {
		public EmptyVocabAttribute() {
			super("A Vocab Attribute was Empty");
			System.out.println("A Vocab Attribute was Empty");
	    }
	}
	@Override
	protected void writeAll(FileWriter fw, Collection<Vocable> vList) throws FileWriterException, EmptyVocabListException, EmptyVocabAttribute {
		// write intermediate representation to file
		if(vList == null){
			throw new EmptyVocabListException();
		}
		for(Vocable v: vList){
			this.mapVoc(v);
		}
		StringBuffer result = new StringBuffer("");
		for (String s: totalRepresentation) {
			result.append(s);
		}
		
		try {
			
			fw.write(result.toString());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			throw new FileWriterException();
		}
		
	}
	
	

}
