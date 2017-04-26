package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import domain.Kontakt;
import domain.Wohnort;
import main.Main;

/**
 * In diesem Fenster kann man zwischen mehreren moeglichen Orten auswählen.
 */
public class Ortsauswahl extends JFrame {

	private static final long serialVersionUID = 1L;
	private JList<Wohnort> liste;
	private Vector<Wohnort> wohnorte;
	private Kontakt alterKontakt, neuerKontakt;

	/**
	 * Legt ein Fenster zur Ortsauswahl an.
	 * 
	 * @param alterKontakt
	 *            Zu veraendernder Kontakt. Null, falls ein neuer Kontakt
	 *            angelegt wird.
	 * @param neuerKontakt
	 *            Anzulegender zu geaenderter Kontakt.
	 * @param wohnorte
	 *            Liste mit allen moeglichen Wohnorten.
	 */
	public Ortsauswahl(Kontakt alterKontakt, Kontakt neuerKontakt, List<Wohnort> wohnorte) {
		super("Ortsauswahl");
		this.wohnorte = new Vector<Wohnort>(wohnorte);
		this.alterKontakt = alterKontakt;
		this.neuerKontakt = neuerKontakt;
		this.setSize(300, 500);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		final Dimension dimension = this.getToolkit().getScreenSize();
		this.setLocation((int) ((dimension.getWidth() - this.getWidth()) / 2),
				(int) ((dimension.getHeight() - this.getHeight()) / 2));
		
		createComponents();
		this.setVisible(true);
	}

	/**
	 * Erzeugt die Komponenten dieses Fensters
	 */
	private void createComponents() {
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());

		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {

			/**
			 * Kontakt wird ausgelesen und in die Datenbank eingefügt.
			 */
			public void actionPerformed(ActionEvent arg0) {
				if (alterKontakt == null) {
					neuerKontakt.setWohnort(liste.getSelectedValue());
					Main.database.insertKontakt(neuerKontakt);
				} else {
					neuerKontakt.setWohnort(liste.getSelectedValue());
					Main.database.editKontakt(alterKontakt, neuerKontakt);
				}
				Ortsauswahl.this.dispose();
			}

		});

		liste = new JList<Wohnort>(wohnorte);
		liste.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		liste.setSelectedIndex(0);
		JScrollPane scroll = new JScrollPane(liste);
		c.add(ok, BorderLayout.SOUTH);
		c.add(scroll, BorderLayout.CENTER);
	}

}
