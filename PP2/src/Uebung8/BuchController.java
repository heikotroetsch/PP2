package Uebung8;

import java.io.File;
import java.util.List;

public class BuchController {
    SQLiteDBHandler dbHandler = null;
    B_GUI gui;

    public BuchController(){
	dbHandler = new SQLiteDBHandler();
	gui = new B_GUI(this,false);
	gui.setVisible(true);
    }

    public BuchController(String filename){
	dbHandler = new SQLiteDBHandler();
	boolean successOnOpenDB = dbHandler.connect(filename);
	gui = new B_GUI(this,successOnOpenDB);
	gui.setVisible(true);
    }

    public boolean openDB(String filename){
	return dbHandler.connect(filename);
    }

    public void searchBuch(String autorTeil, String titelTeil, List<Kategorie> kategorien){
	List<Buch> trefferListe = dbHandler.searchBuecher(autorTeil,titelTeil,kategorien);
	gui.showSuchListe(trefferListe);
    }

    public boolean addBuch(Buch b){
	boolean success = true;
	if (dbHandler != null){
	    int newID = dbHandler.addBuch(b);
	    if (newID != -1){
		gui.setCurrentID(newID);
	    }
	}
	return success;
    }

    public boolean setBuch(Buch b,int id){
	boolean success = true;
	if (dbHandler != null){
	    dbHandler.updateBuch(b);
	    //dbHandler.updateBuch();
	}
	return success;
    }

    protected void saveBook(Buch b, boolean isNew, int id){
	if (isNew){
	    addBuch(b);
	}
	else {
	    setBuch(b,id);
	}
    }

    public java.util.List<Buch> getBuchListe(){
	return dbHandler.getBuchListe();
    }

    public static void main(String[] args){
	if (args.length == 0){
	    BuchController controller = new BuchController();
	}
	else if (args.length == 1){
	    BuchController controller = new BuchController(args[0]);
	}
    }

}
