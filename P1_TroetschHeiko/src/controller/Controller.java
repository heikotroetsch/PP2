package controller;
/** Controller Interface */
public interface Controller {
	/** list of possible Controller commands */
	public static final String 	ADD = "add",
								CLEAR = "clear", 
								EDIT = "edit", 
								SEARCH = "search", 
								START = "start",
								CHECK = "check",
								SAVE = "save",
								LEARN = "learn",
								NEXT = "next",
								EXPORT = "export",
								LOAD = "load",
								CLOSE = "close",
								EXIT = "exit";

	
	/** All steps to start the controller (and the part of the program)*/
	public void startController();
	
	/** All steps to stop the controller (and the part of the program) */
	public void stopController();
	
	/** executes command specified by a string */
	public void execCommand(String cmd);
}
