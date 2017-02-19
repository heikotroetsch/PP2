package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.Vocable;
import model.VocableList;
public abstract class LineReader {

	protected abstract Vocable parseLine(String line);

	/* opens the file specified by the parameter filename 
	 * reads the data linewise from it and passes each line
	 * to the methode parseLine.
	 */
	public VocableList readFile(String filename){
		ArrayList<Vocable> vocables = new ArrayList<Vocable>();
		try (BufferedReader bufR = new BufferedReader(new FileReader(filename))){
			String line;
			while ((line = bufR.readLine()) != null){
				Vocable v = parseLine(line);
				if (v != null){
					vocables.add(v);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e2){
			e2.printStackTrace();
		} 
		return new VocableList(vocables); 
	}
}
