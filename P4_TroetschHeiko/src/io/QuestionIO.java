package io;

import general.Parameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import data.Question;

public class QuestionIO {

	/* schreibt zunächst die Anzahl der zu serialisierenden Objekte und dann
	 * alle Fragen (Instanzen der Klasse Question aus der als Parameter übergebenen 
	 * Liste fragen in die Datei mit dem als Paramter angegebenen Dateinamen 
	 * Hier wird ein absoluter Pfad erwartet (wie er z.B. bei der Auswahl 
	 * mit einem JFileChooser generiert wird.
	 */
	public static void writeQuestions(String filename, ArrayList<Question> fragen){
		try (ObjectOutputStream datOut = new ObjectOutputStream(new FileOutputStream(filename))){
			datOut.writeInt(fragen.size());
			for (Question q : fragen){
				datOut.writeObject(q);
			}
			
		} catch (IOException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/* liest zunächst die Anzahl der serialisierten Objekte und dann alle 
	 * Objekte, welche vom Typ data.Question sein müssen.
	 * Die Frage-Objekte werden in einer ArrayList gespeichert und zurueckgegeben
	 */
	public static ArrayList<Question> readQuestions(String filename)  {
		ArrayList<Question> qList = new ArrayList<Question>();
		try (ObjectInputStream oEin = new ObjectInputStream(new FileInputStream(filename))){
			int anzahl = oEin.readInt();
			//System.out.println("Die Datei " + filename + " enthaelt " +  anzahl + " Fragen");
			System.out.println(anzahl + " Fragen geladen.");
			for (int i=1; i<=anzahl; i++) {
				Question q = (Question) oEin.readObject(); 
				qList.add(q);
			}
		} catch(IOException | ClassNotFoundException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		return qList;
	}
	
	/* startet JFileChooser zur Auswahl einer Datei zum Laden (loadFileP=true)
	 * (loadFileP=false) bzw. Speichern einer Fragenliste
	 * 
	 */
	public static String getFilename(boolean loadFileP){
		String filename = null;

		JFileChooser fc = new JFileChooser(Parameters.currentDir);
		fc.setFileFilter(new QFileFilter()); 
		if (loadFileP){
			if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				File f = fc.getSelectedFile();
				filename = f.getAbsolutePath();
				Parameters.currentDir = f.getParent(); // set new currentDir
				System.out.println(Parameters.currentDir);
			}
		} else {
			if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
				File f = fc.getSelectedFile();
				filename = f.getAbsolutePath();
				Parameters.currentDir = f.getParent(); // set new currentDir
				System.out.println(Parameters.currentDir);
			}
		}

		return filename;
	}
	
	/* Filter-Klasse für Auswahl von Binärdateien mit serialisierten Objekten
	 * mit den Endungen .dat oder .DAT
	 */
	private static class QFileFilter extends FileFilter {
		ArrayList<String> extensions = new ArrayList<String>();
		
		public QFileFilter(){	
			extensions.add(".dat");	
			extensions.add(".DAT");		
		}

		public boolean accept(File f) {
			String name = null;
			name = f.getAbsolutePath();
			String ext = name.substring(name.length()-4);
			return this.extensions.contains(ext);			
		}
		
		public String getDescription() {
				return ".dat files containing number of objects and question instances";
		}
	
	}

}
