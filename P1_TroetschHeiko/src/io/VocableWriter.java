package io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

import io.CSVWriter.EmptyVocabAttribute;
import io.CSVWriter.EmptyVocabListException;
import io.CSVWriter.FileWriterException;
import model.Vocable;
import model.VocableList;

public abstract class VocableWriter {
	protected String filename;
	protected abstract void mapVoc(Vocable v) throws EmptyVocabAttribute; // write single vocable to intermediate representation
	protected abstract void writeAll(FileWriter fw, Collection<Vocable> vList) throws FileWriterException, EmptyVocabListException, EmptyVocabAttribute; // write intermediate representation to file
	
	public void writeFile(VocableList vList, String filename){
		this.filename = filename;
		try (FileWriter fw = new FileWriter(new File(filename))){
			writeAll(fw,vList.getVocableList());
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
