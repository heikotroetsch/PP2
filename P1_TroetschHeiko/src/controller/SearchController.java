package controller;

import java.util.Vector;

import model.GlobalData;
import model.Vocable;
import view.SearchGUI;

public class SearchController implements Controller {
	SearchGUI gui = new SearchGUI(this);
	Controller parentController;
	
	public SearchController(Controller parentController){
		this.parentController = parentController;
	}
	
	/** Just show the window */
	public void startController() {
		this.gui.setVisible(true);
	}

	/** Hides the window and marks it for GC,
	 * after calling this method, the program is supposed to end (exit) */
	public void stopController() {
		this.gui.setVisible(false);
		this.gui.dispose();
	}
	
	/* executes one of the commands received from the SearchGUI */
	public void execCommand(String cmd) {
		switch(cmd){
			case Controller.CLOSE:
				this.gui.setVisible(false);
				break;
			case Controller.SEARCH:
				String[] searchItems = this.gui.getSearchEntries();
				Vector<Vocable> vec = new Vector<Vocable>();
				vec.addAll(GlobalData.currentLoadList.find(searchItems[0],searchItems[1]));
				this.gui.setResultList(vec);
				break;
			case Controller.EDIT:
				parentController.execCommand(Controller.ADD);
				break;
			default: 	
				break;
		}
	}

}
