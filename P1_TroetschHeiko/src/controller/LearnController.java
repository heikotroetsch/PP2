package controller;

import java.util.Vector;

import javax.swing.JOptionPane;

import model.GlobalData;
import model.LearningContext;
import model.AnswerCollection;
import model.Vocable;
import view.LearnGUI;

public class LearnController implements Controller {
	LearnGUI gui = new LearnGUI(this);
	private Vector<Vocable> vocList;
	private LearningContext ls;
	private AnswerCollection answers;
	private int currentMode;

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

	/* executes one of the commands received from the LearnGUI */
	public void execCommand(String cmd) {
		switch(cmd){
		case Controller.CLOSE:
			this.gui.setVisible(false);
			break;
		case Controller.START:
			this.ls = this.gui.getLearnSettings();
			// create List of Vocabularies to test
			this.vocList = new Vector<Vocable>(GlobalData.currentLoadList.chooseLearningList(ls));

			if (this.vocList.size() > 0) {
				int sz = this.vocList.size();
				int n = ls.getSize();
				//this.answers = new AnswerList(Math.min(sz,n));
				this.currentMode = ls.getMode();
				this.gui.setEntry(this.vocList.get(0),this.currentMode);
			} else {
				JOptionPane.showMessageDialog(gui, "No matching vocables found.");
				this.gui.disableLearning();
			}
			break;
		case Controller.CHECK:
			this.answers.check(this.vocList);
			this.gui.enableLearning();
			break;
		case Controller.NEXT:
			boolean fertig = this.answers.addAnswer(this.gui.getEntry(this.currentMode),this.currentMode);
			if (fertig){
				this.gui.enableCheck();
			} else {
				this.currentMode = ls.getMode();
				this.gui.setEntry(this.vocList.get(this.answers.getCurrentIndex()), this.currentMode);
			}
			break;
		default: 	
			break;
		}
	}

}
