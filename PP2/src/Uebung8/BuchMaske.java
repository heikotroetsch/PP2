package Uebung8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;

public class BuchMaske extends JPanel{
    private static final long serialVersionUID = 1L;
    private Font font  = new Font("SansSerif",Font.PLAIN,20);
    private Font font2  = new Font("SansSerif",Font.PLAIN,14);
    private BuchController controller = null;
    
    private JTextField autor = new JTextField(30);
    private JTextField titel = new JTextField(30);
    private JList<Kategorie> kategListe = new JList<Kategorie> ();
    private JButton buttons[] = new JButton[]{new JButton("NEW"),new JButton("SAVE"),new JButton("SEARCH")};
    private JComboBox<Buch> bookItems = new JComboBox<Buch>();
    private boolean isNew = true; // Bezug: neuer Eintrag ?
    private int shownBookID = -1;

    public BuchMaske(BuchController controller){
	this.controller =  controller;
	this.createContent();
    }


    private void createContent(){
	this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

	JPanel upper = new JPanel();
	upper.setLayout(new GridLayout(2,2));
	JLabel autorLabel = new JLabel("Autor");
	autorLabel.setFont(font2);
	upper.add(autorLabel);
	autor.setFont(font2);
	autor.setEnabled(false);
	upper.add(autor);
	JLabel titelLabel = new JLabel("Titel");
	titelLabel.setFont(font2);
	upper.add(titelLabel);
	upper.add(titel);
	titel.setFont(font2);
	titel.setEnabled(false);
	upper.setPreferredSize(new Dimension(400,50));
	JPanel middle1 = new JPanel();
	middle1.setLayout(new GridLayout(1,2));
	JLabel katLabel = new JLabel("Kategorien");
	katLabel.setFont(font2);
	middle1.add(katLabel);
	kategListe.setListData(Kategorie.values());
	kategListe.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
	kategListe.setEnabled(false);
	middle1.add(new JScrollPane(kategListe));
	middle1.setPreferredSize(new Dimension(400,100));

	JPanel middle2 = new JPanel();
	//middle1.setLayout(new GridLayout(1,2));
	ButtonActionListener butLis = new ButtonActionListener();
	bookItems.addItemListener(new BookItemListener());
	middle2.add(bookItems);

	JPanel lower = new JPanel();
	lower.setLayout(new BoxLayout(lower,BoxLayout.X_AXIS));
	for (int i=0;i<buttons.length;i++){
	    lower.add(buttons[i]);
	    buttons[i].setFont(font);
	    buttons[i].addActionListener(butLis);
	    buttons[i].setEnabled(false);
	    if (i != (buttons.length-1)){
		lower.add(Box.createRigidArea(new Dimension(75,0))); // Platz
	    }
	}

	lower.setPreferredSize(new Dimension(400,50));

	this.add(upper);
	this.add(middle1);
	this.add(new JSeparator());
	this.add(middle2);
	this.add(new JSeparator());
	this.add(lower);
    }

    public void enableInput(){
	for (int i=0;i<buttons.length;i++){
	    setButton(i,true);
	}
	titel.setEnabled(true);
	autor.setEnabled(true);
	kategListe.setEnabled(true);
	addBookList(controller.getBuchListe());
    }

    private void updateBookListChoice(){
	addBookList(controller.getBuchListe());
    }

    public void setButton(int index, boolean enabled){
	if ((index>=0)&&(index<buttons.length)){
	    buttons[index].setEnabled(enabled);
	}
    }
    
    private String mapKategorienToString(java.util.List<Kategorie> klist){
	Iterator<Kategorie> it = klist.iterator();
	StringBuffer buf = new StringBuffer();
	while(it.hasNext()){
	    buf.append(it.next().toString());
	}
	return buf.toString();
    }

    private void saveBook(Buch b){
	String t = titel.getText().trim();
	String a = autor.getText().trim();
	if ((t.length() > 0) && (a.length() > 0)){
	    java.util.List<Kategorie> kl = kategListe.getSelectedValuesList();
	    Buch buch = new Buch(a,t);
	    buch.setID(this.shownBookID);
	    buch.setKategorien(kl);
	    controller.saveBook(b,this.isNew,this.shownBookID);
	}
    }

    public void addBookList(java.util.List<Buch> bList){
	bookItems.setModel(new DefaultComboBoxModel<Buch>(new Vector<Buch>(bList)));
	//bookItems.repaint();
    }
    
    public void setCurrentID(int newID){
	this.shownBookID = newID;
    }


    private class ButtonActionListener implements ActionListener {
	public void actionPerformed(ActionEvent e){
	    String text = ((JButton)e.getSource()).getText();
	    if (text.equals("NEW")){
		autor.setText("");
		titel.setText("");
		kategListe.setSelectedIndices(new int[0]);
		BuchMaske.this.isNew = true;
		BuchMaske.this.shownBookID = -1;
		repaint();
	    }
	    else if (text.equals("SAVE")){
		Buch buch = new Buch(autor.getText(), 
				     titel.getText());
		buch.setID(BuchMaske.this.shownBookID);
		buch.setKategorien(kategListe.getSelectedValuesList());
		BuchMaske.this.saveBook(buch);
		BuchMaske.this.updateBookListChoice();
	    }
	     else if (text.equals("SEARCH")){
		 String aName = autor.getText().trim();
		 String tName = titel.getText().trim();
		 if ((aName.length() > 0) ||  
		     (tName.length() > 0) ||
		     kategListe.getSelectedValuesList().size() > 0){
		     controller.searchBuch(((aName.length() == 0) ? null : aName),
					   ((tName.length() == 0) ? null : tName),
					   kategListe.getSelectedValuesList());			
		 } else {
		     JOptionPane.showMessageDialog(BuchMaske.this,"Keine Suchparameter spezifiziert");
		 }	
	     }
	}
    }

    private class BookItemListener implements ItemListener{
	public void itemStateChanged(ItemEvent e){
	    Buch b = (Buch) e.getItem();
	    BuchMaske.this.isNew = false;
	    BuchMaske.this.shownBookID = b.getID();
	    // Eintraege Buch anzeigen
	    BuchMaske.this.autor.setText(b.getAutor());
	    BuchMaske.this.titel.setText(b.getTitel());
	    Kategorie[] kats = b.getKategorien();
	    for (int i=0;i<kats.length;i++){
		//((JList)kategListe.getModel()).setSelectedValue(kats[i],true);
	    }
	}
    }
    
}
