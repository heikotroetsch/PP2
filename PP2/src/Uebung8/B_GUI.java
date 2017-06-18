package Uebung8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.util.List;
import java.util.Iterator;

public class B_GUI extends JFrame{
    private static final long serialVersionUID = 1L;
    private File currentDir = new File("./");  // aktuelles Verzeichnis fuer JFileChooser merken
    BuchController controller = null;
    BuchMaske bMaske = null;
    JMenuBar menuBar = null;
    

    public B_GUI(BuchController controller, boolean dbOpen) {
	super("B*B*B");
	this.controller = controller;
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	this.setBounds(100,100,400,300); // Groesse und Position von GUI setzen
	//this.setResizable(false); // Groesse des Fensters unveraenderbar machen
	this.createContent();
	if (dbOpen){
	    bMaske.enableInput();
	}
	this.pack();
    }

    private void createContent(){
	Container c = this.getContentPane();
	bMaske = new BuchMaske(controller);
	c.add(bMaske);
	this.setJMenuBar(createMenu());
    }

    private JMenuBar createMenu() {
        menuBar = new JMenuBar();
	JMenu menu = new JMenu("Data");
	menuBar.add(menu);

	JMenuItem openDB = new JMenuItem("Open Database");
	openDB.addActionListener(new MenuListener());
	menu.add(openDB);
	return menuBar;
    }

    public void setCurrentID(int newID){
	bMaske.setCurrentID(newID);
    }

    private class MenuListener implements ActionListener{
	public void actionPerformed(ActionEvent e){
	    String command = ((JMenuItem) e.getSource()).getText();
	    File file;

	    if (command.equals("Open Database")){
		file = chooseFile();
		if (file != null){
		    // open connection to database
		    if (B_GUI.this.controller.openDB(file.getName())){
			bMaske.enableInput();
		    }
		}
	    }
	}

	private File chooseFile(){
	    File file = null;
	    
	    JFileChooser chooser = new JFileChooser(currentDir);
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("SQLite database", "db");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(B_GUI.this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
		file = chooser.getSelectedFile();
		B_GUI.this.currentDir = chooser.getCurrentDirectory();
	    }
	    return file;
	}
    }


    public void showSuchListe(List<Buch> bListe){
	DefaultListModel<String> bL = new DefaultListModel<String>();
	if ((bListe != null) && (bListe.size() > 0)){
	    Iterator<Buch> it = bListe.iterator();
	    while (it.hasNext()){
		Buch b = it.next();
		bL.addElement(b.toString());
	    }
	} else {
	    bL.addElement("--- Keine Treffer ---"); 
	}
	JList<String> buchliste = new JList<String>(bL);
	buchliste.setVisibleRowCount(10);
	JScrollPane scrollPane = new JScrollPane(buchliste);
	JFrame rFrame = new JFrame();
	rFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	rFrame.getContentPane().add(scrollPane);
	Rectangle r = this.getBounds();
	rFrame.setBounds(r.x+r.width,r.y,250,300);
	rFrame.setVisible(true);
    }
}
