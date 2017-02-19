package controller;

import model.GlobalData;
import model.Vocable;
import view.InsertGUI;

public class InsertController implements Controller {
	InsertGUI gui = new InsertGUI(this);
	

	
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
	
	/* executes one of the commands received from the InsertGUI */
	public void execCommand(String cmd) {
		switch(cmd){
			case Controller.ADD:
				Vocable v = gui.getVocable();
				if (!GlobalData.currentLoadList.contains(v)){
					GlobalData.currentLoadList.add(v);
				}
				break;
			case Controller.CLOSE:
				this.gui.setVisible(false);
				break;
			case Controller.SAVE:
				break;
			default: 	
				break;
		}
	}

}
