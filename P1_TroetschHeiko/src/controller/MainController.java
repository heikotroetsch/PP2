package controller;

import io.CSVReader;
import io.CSVWriter;
import io.LineReader;
import io.VocableWriter;
import io.XMLReader;
import io.XMLVocableReader;
import io.XMLVocableWriter;
import io.XMLWriter;

import javax.swing.JOptionPane;

import model.GlobalData;
import model.VocableList;

import view.OverviewGUI;

public class MainController implements Controller{
	private OverviewGUI overviewGUI;  // main GUI 
	private InsertController insertController;  // controller for insertion of new Vocables
	private SearchController searchController;
	private LearnController learnController;
		
	
	public static void main(String[] args){		
		MainController mainController = new MainController();
		mainController.startController();
	}

	/* starts all Controller instances */
	public void startController() {
		this.loadFile(Settings.getDefaultFilename(true));
		this.insertController = new InsertController();
		this.searchController = new SearchController(this);
		this.learnController = new LearnController();
		this.overviewGUI = new OverviewGUI(this);
		this.overviewGUI.setVisible(true);
	}

	/** stops all Controller instances preparing for exiting */
	public void stopController() {
		this.insertController.stopController();
		this.searchController.stopController();
		this.overviewGUI.setVisible(false);
		this.overviewGUI.dispose();
		System.exit(0);
	}
	
	/* sets the controller specified by the command cmd to visible.
	 * (non-Javadoc)
	 * @see controller.Controller#execCommand(java.lang.String)
	 */
	public void execCommand(String cmd){
		switch (cmd){
		case Controller.ADD: 
			this.insertController.startController();
			break;
		case Controller.SEARCH: 
			this.searchController.startController();
			break;
		case Controller.LEARN: 
			this.learnController.startController();
			break;
		case Controller.SAVE: 
			this.saveToFile(false);
			break;
		case Controller.EXPORT:
			this.saveToFile(false);
			break;
		case Controller.LOAD:
			this.loadFile(Settings.getFilename(true));
			break;
		case Controller.CLOSE:
			this.stopController();
			System.exit(0);
			break;
		case Controller.EXIT: // save and exit
				String filename = Settings.getDefaultFilename();
				this.saveToFile(filename);
				this.stopController();
				JOptionPane.showMessageDialog(overviewGUI, "Saved Data to file. Exiting now!");
				System.exit(0);
				break;
		}	
	}
	
	
	/* loads a vocable file: depending on the mode, either 
	 * a file in CSV format is loaded or a XML file.
	 * the vocables read are stored in GlobalData.currentLoadList
	 * */
	private void loadFile(String filename){
		if (Settings.mode == Settings.CSVMode){
			CSVReader csvReader = new CSVReader();
			VocableList vList = csvReader.readFile(filename);
			if ((vList != null) && (vList.size() > 0)){
				GlobalData.currentLoadList = vList;
			}
		}
		else {
			XMLReader xmlReader = new XMLVocableReader();
			VocableList vList = xmlReader.readFile(filename);
			if ((vList != null) && (vList.size() > 0)){
				GlobalData.currentLoadList = vList;
			}
		}
	}
	
	/* determines the filename to be used for storing the vocables
	 * This file could be the one the vocable data have been load from
	 * or another one that is to be specified by the user.
	 */
	private void saveToFile(boolean useLoadFile){
		String filename = Settings.getFilename(useLoadFile);
		if (filename != null){
			this.saveToFile(filename);
		}
	}
	
	/* stores the current vocable list in a file: depending on the mode, either 
	 * a file in CSV format is used or a XML file.
	 * */
	private void saveToFile(String filename){	
		if (Settings.mode == Settings.CSVMode){
			CSVWriter csvWriter = new CSVWriter();
			csvWriter.writeFile(GlobalData.currentLoadList, filename);
		}
		else {
			XMLWriter xmlWriter = new XMLVocableWriter();
			xmlWriter.writeFile(GlobalData.currentLoadList, filename);
		}
	}

}
