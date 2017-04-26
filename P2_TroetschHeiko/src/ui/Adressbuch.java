package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import domain.Kontakt;
import main.Main;

/**
 * Das Fenster, dass das Adressbuch darstellt.
 */
public class Adressbuch extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel suchfenster, buttons;
	private JTextField wer, wo;
	private JButton suchen, neu, anzeigen, bearbeiten, loeschen;
	private JList<Kontakt> liste;
	private JScrollPane listenPanel;
	private Container c;
	private DefaultListModel<Kontakt> listModel;

	public Adressbuch() {
		super("Adressbuch ("+Main.database.countKontakte()+")");
		this.setSize(400, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		final Dimension dimension = this.getToolkit().getScreenSize();
		this.setLocation((int) ((dimension.getWidth() - this.getWidth()) / 2),
				(int) ((dimension.getHeight() - this.getHeight()) / 2));
		
		createComponents();
		this.setVisible(true);

		suchen();
		this.setVisible(true);
	}

	/**
	 * Erzeugt die Komponenten.
	 */
	private void createComponents() {
		c = this.getContentPane();
		c.setLayout(new BorderLayout());

		ButtonListener bl = new ButtonListener();

		suchfenster = new JPanel();
		buttons = new JPanel();
		wer = new JTextField("Wer");
		wer.setPreferredSize(new Dimension(100, 24));
		wo = new JTextField("Wo");
		wo.setPreferredSize(new Dimension(100, 24));

		suchen = new JButton("Suchen");
		neu = new JButton("Neu");
		anzeigen = new JButton("Anzeigen");
		bearbeiten = new JButton("Bearbeiten");
		loeschen = new JButton("<HTML>L&ouml;schen</HTML>");
		suchen.addActionListener(bl);
		neu.addActionListener(bl);
		anzeigen.addActionListener(bl);
		bearbeiten.addActionListener(bl);
		loeschen.addActionListener(bl);
		anzeigen.setEnabled(false);
		bearbeiten.setEnabled(false);
		loeschen.setEnabled(false);

		liste = new JList<Kontakt>();
		liste.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listModel = new DefaultListModel<Kontakt>();
		liste.setModel(listModel);
		listenPanel = new JScrollPane(liste);

		suchfenster.add(wer);
		suchfenster.add(wo);
		suchfenster.add(suchen);

		buttons.add(neu);
		buttons.add(anzeigen);
		buttons.add(bearbeiten);
		buttons.add(loeschen);

		c.add(suchfenster, BorderLayout.NORTH);
		c.add(listenPanel, BorderLayout.CENTER);
		c.add(buttons, BorderLayout.SOUTH);
	}
	
	/**
	 * Sucht und listet die passenden Eintraege.
	 */
	void suchen() {
		String name = wer.getText();
		String ort = wo.getText();
		if (name.equals("") || name.equals("Wer")) {
			name = null;
		}
		if (ort.equals("") || ort.equals("Wo")) {
			ort = null;
		}
		List<Kontakt> kontakte = Main.database.getKontakte(name, ort);
		if (!kontakte.isEmpty()) {
			listModel.removeAllElements();

			for (Kontakt i : kontakte) {
				listModel.addElement(i);
			}
			liste.setSelectedIndex(0);

			anzeigen.setEnabled(true);
			bearbeiten.setEnabled(true);
			loeschen.setEnabled(true);
			c.revalidate();
		} else {
			listModel.removeAllElements();
			anzeigen.setEnabled(false);
			bearbeiten.setEnabled(false);
			loeschen.setEnabled(false);
			c.revalidate();
		}
		Adressbuch.this.setTitle("Adressbuch ("+Main.database.countKontakte()+")");
	}

	/**
	 * Innere Klasse, die als ActionListener fuer die Buttons dient.
	 */
	class ButtonListener implements ActionListener {

		/**
		 * Schaut, welcher Button das Ereignis ausgeloest hat und reagiert
		 * dementsprechend.
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {

			switch (((JButton) e.getSource()).getText()) {
			case "Suchen":
				suchen();
				break;
			case "Neu":
				new KontaktAnlegen(Adressbuch.this, null);
				break;
			case "Anzeigen":
				new KontaktAnzeigen(liste.getSelectedValue());
				break;
			case "Bearbeiten":
				new KontaktAnlegen(Adressbuch.this, liste.getSelectedValue());
				break;
			default: // Fall Loeschen
				Kontakt kontakt = liste.getSelectedValue();
				Main.database.deleteKontakt(kontakt);
				suchen();
				break;
			}
		}	
	}
}
