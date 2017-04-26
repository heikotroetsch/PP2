package io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import model.Vocable;

/**
 * This class is used to Write all Vocables to a CSV file.
 * 
 * The Class extends the VocableWriter Class. 
 * @author heikotroetsch
 *
 */
public class CSVWriter extends VocableWriter {
	
	LinkedList<String> totalRepresentation = new LinkedList<String>();
	/**
	 * This method creates a String with an intermediate Representation of the Vocab as a CSV String. The String is seperated by characters specified in the {@link IOSettings}. 
	 * The finished String is added to the List totalRepresentation.
	 * @throws EmptyVocabAttribute
	 */
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

	/**
	 * This Class is an Exception and extends the {@link IOException} class.
	 * @author heikotroetsch
	 *
	 */
	public class CSVWriteException extends IOException {
		public CSVWriteException(String message) {
			super("An CSVWrite Exception Occured while writing the file");
			System.out.println("An CSVWrite Exception Occured while writing the file");
	    }
	}
	
	/**
	 * This class is an Exception to occour when the FileWriter throws an exception. It extends the CSVWriterException class.
	 * @author heikotroetsch
	 *
	 */
	class FileWriterException extends CSVWriteException {
		public FileWriterException() {
			super("The File Writer could not write the file");
			System.out.println("The File Writer could not write the file");
	    }
	}
	
	/**
	 * This class is an Exception to occour when the the VocabList is empty. It extends the CSVWriterException class.
	 * @author heikotroetsch
	 *
	 */
	class EmptyVocabListException extends CSVWriteException {
		public EmptyVocabListException() {
			super("There was no vocab List to be written");
			System.out.println("There was no vocab List to be written");
	    }
	}
	
	/**
	 * This class is an Exception to occour when the the Vocable is empty. It extends the CSVWriterException class.
	 * @author heikotroetsch
	 *
	 */
	class EmptyVocabAttribute extends CSVWriteException {
		public EmptyVocabAttribute() {
			super("A Vocab Attribute was Empty");
			System.out.println("A Vocab Attribute was Empty");
	    }
	}
	
	/**
	 * This Method writes all mapped vocabs to a file. It uses the totlRepresentation variable. 
	 * throws FileWriterException
	 */
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
