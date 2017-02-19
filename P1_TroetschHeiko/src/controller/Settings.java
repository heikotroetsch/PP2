package controller;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import model.GlobalData;

public class Settings {
	public static final int CSVMode = 0, XMLMode = 1; // mode for IO operations
	public static int mode = CSVMode;
	
	public static final char listSeparator = ';'; 
	public static Locale sourceLanguage = GlobalData.ENGLISH;
	public static Locale targetLanguage = GlobalData.GERMAN;
	
	public static int maxUnit = 10; // 10 units is maximum expected
	public static int maxSection = 5; // 10 units is maximum expected

	// file resources
	public final static String resourcePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "resources";
	public final static String imageSave = resourcePath + System.getProperty("file.separator") + "Disc.png";
	public final static String imageExit = resourcePath + System.getProperty("file.separator") + "Exit.png";
	public final static String imageClear = resourcePath + System.getProperty("file.separator") + "Clear.png";
	public final static String imageClose = resourcePath + System.getProperty("file.separator") + "Close.png";
	public final static String imageSearch = resourcePath + System.getProperty("file.separator") + "Search.png";
	public final static String imageEdit = resourcePath + System.getProperty("file.separator") + "Edit.png";
	public final static String imageAdd = resourcePath + System.getProperty("file.separator") + "Add.png";
	public final static String imageStart = resourcePath + System.getProperty("file.separator") + "Start.png";
	public final static String imageCheck = resourcePath + System.getProperty("file.separator") + "Check.png";
	
	private static String filenameCSV = resourcePath + System.getProperty("file.separator") + "test.csv";
	private static String filenameXML= resourcePath + System.getProperty("file.separator") + "test.xml	";

	// set filename w.r.t. to current mode
	public static void setLoadFilename(String filename){
		if (mode == CSVMode){
			filenameCSV = resourcePath + System.getProperty("file.separator") + filename;
		} else {
			filenameXML = resourcePath + System.getProperty("file.separator") + filename;
		}
	}
	
	// return filename  w.r.t. to current mode
	public static String getDefaultFilename(){
		if (mode == CSVMode){
			return filenameCSV;
		} else {
			return filenameXML;
		}
	}
	
	public static String getDefaultFilename(boolean loadFileP){
		return (mode == CSVMode) ? filenameCSV : filenameXML;
	}
	
	/* start a JFileChooser instance to choose a file for
	 * either loading or saving a vocable list to a file
	 * (mode specific:  xml or csv file)
	 * and returns the filename to be used by the classes
	 * in the io package
	 */
	public static String getFilename(boolean loadFileP){
		String filename = null;

		JFileChooser fc = new JFileChooser(resourcePath);
		fc.setFileFilter(new VocFileFilter(mode)); // xml or csv
		if (loadFileP){
			if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				filename = fc.getSelectedFile().getAbsolutePath();
			}
		} else {
			if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
				filename = fc.getSelectedFile().getAbsolutePath();
			}
		}

		return filename;
	}
	
	
	/* creates and returns a specific FileFilter according to mode set
	 */
	public static FileFilter createFileFilter(){
		return new VocFileFilter(mode);
	}
	
	/* File Filter to filter out either files with extension csv or xml 
	 * An instance of the class is to be used with a JFileChooser object 
	 * (to either load or save files)
	 */
	private static class VocFileFilter extends FileFilter {
		ArrayList<String> extensions = new ArrayList<String>();
		
		public VocFileFilter(int mode){
			if (mode == CSVMode){  //CSV mode
				extensions.add(".csv");	
				extensions.add(".CSV");
			} else {                   // XML mode
				extensions.add(".xml");	
				extensions.add(".XML");
			}
		}

		public boolean accept(File f) {
			String name = null;
			try {
				name = f.getCanonicalPath();
				String ext = name.substring(name.length()-4);
				return this.extensions.contains(ext);
				
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}	
		}
		
		public String getDescription() {
			if (mode == CSVMode){  //CSV mode
				return "CSV files";
			} else {
				return "XML files";
			}
		}
	
	}
}
