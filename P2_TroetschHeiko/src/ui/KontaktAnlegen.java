package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.Kontakt;
import domain.Wohnort;
import main.Main;

/**
 * Fenster, um einen Kontakt zu bearbeiten oder neu anzulegen.
 */
public class KontaktAnlegen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField field[];
	private Kontakt alterKontakt;
	private Adressbuch adressbuch;

	/**
	 * Konstruktor fuer das neue Fenster.
	 * 
	 * @param alterKontakt
	 *            null, falls ein neuer Kontakt angelegt wird.
	 */
	public KontaktAnlegen(Adressbuch adressbuch, Kontakt alterKontakt) {
		super();
		this.adressbuch = adressbuch;
		this.alterKontakt = alterKontakt;
		if (alterKontakt == null) {
			this.setTitle("Kontakt anlegen");
		} else {
			this.setTitle("Kontakt bearbeiten");
		}
		this.setSize(300, 270);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		final Dimension dimension = this.getToolkit().getScreenSize();
		this.setLocation((int) ((dimension.getWidth() - this.getWidth()) / 2),
				(int) ((dimension.getHeight() - this.getHeight()) / 2));
		
		createComponents();
		this.setVisible(true);
	}

	/**
	 * Legt die Komponenten des Fensters an.
	 */
	private void createComponents() {

		Container c = getContentPane();
		c.setLayout(new BorderLayout());

		JPanel[] panel = new JPanel[5];
		JPanel bigPanel = new JPanel();
		bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
		JLabel[] label = new JLabel[5];
		field = new JTextField[6];

		label[0] = new JLabel("Name      ");
		label[1] = new JLabel("Nummer ");
		label[2] = new JLabel("Mail         ");
		label[3] = new JLabel("<HTML>Stra&szlig;e:&#160;&#160;&#160;&#160;</HTML>");
		label[4] = new JLabel("Wohnort");

		for (int i = 0; i < 5; i++) {
			panel[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));
			field[i] = new JTextField();
			field[i].setPreferredSize(new Dimension(200, 24));
			panel[i].add(label[i]);
			panel[i].add(field[i]);
			if (i == 4) {
				field[4].setPreferredSize(new Dimension(70, 24));
				field[5] = new JTextField();
				panel[4].add(field[5]);
				field[5].setPreferredSize(new Dimension(125, 24));
			}
			bigPanel.add(panel[i]);
		}

		if (alterKontakt != null) {
			field[0].setText(alterKontakt.getName());
			if (alterKontakt.getNummer() != null) {
				field[1].setText(alterKontakt.getNummer());
			}
			if (alterKontakt.getMail() != null) {
				field[2].setText(alterKontakt.getMail());
			}
			if (alterKontakt.getStrasse() != null) {
				field[3].setText(alterKontakt.getStrasse());
			}
			if (alterKontakt.getWohnort() != null) {
				field[4].setText(alterKontakt.getWohnort().getPlz());
				field[5].setText(alterKontakt.getWohnort().getName());
			}
		}

		JButton speichern = new JButton("Speichern");
		speichern.addActionListener(new ActionListener() {

			/**
			 * Schaut ob Wohnort eindeutig und fragt gegebenenfalls nach, legt
			 * dann Kontakt an oder bearbeitet ihn.
			 */
			public void actionPerformed(ActionEvent arg0) {
				String name = field[0].getText();
				if (name == null || name.equals("")) {
					JOptionPane.showMessageDialog(KontaktAnlegen.this, "Bitte geben Sie einen Namen ein!", "Error!",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				String nummer = field[1].getText();
				String mail = field[2].getText();
				String strasse = field[3].getText();
				String plz = field[4].getText();
				String ort = field[5].getText();
				if (name.equals("")) {
					name = null;
				}
				if (nummer.equals("")) {
					nummer = null;
				}
				if (mail.equals("")) {
					mail = null;
				}
				if (strasse.equals("")) {
					strasse = null;
				}
				if (plz.equals("")) {
					plz = null;
				}
				if (ort.equals("")) {
					ort = null;
				}
				
				if (plz == null && ort == null) {
					if (alterKontakt == null) {
						Main.database.insertKontakt(new Kontakt(0, name, nummer, mail, strasse, null));
					} else {
						Main.database.editKontakt(alterKontakt, new Kontakt(0, name, nummer, mail, strasse, null));
					}
				} else {

					List<Wohnort> wohnorte = Main.database.getWohnorte(plz, ort);
					if (wohnorte.isEmpty()) {
						JOptionPane.showMessageDialog(KontaktAnlegen.this,
								"Der angegebene Wohnort konnte nicht gefunden werden!", "Error!",
								JOptionPane.ERROR_MESSAGE);
						return;
					} else if (wohnorte.size() == 1) {
						if (alterKontakt == null) {
							Main.database.insertKontakt(new Kontakt(0, name, nummer, mail, strasse, wohnorte.get(0)));
							;
						} else {
							Main.database.editKontakt(alterKontakt,
									new Kontakt(0, name, nummer, mail, strasse, wohnorte.get(0)));
						}
					} else {
						new Ortsauswahl(alterKontakt, new Kontakt(0, name, nummer, mail, strasse, null), wohnorte);
					}
				}

				KontaktAnlegen.this.dispose();
				adressbuch.suchen();
			}

		});

		c.add(bigPanel, BorderLayout.CENTER);
		c.add(speichern, BorderLayout.SOUTH);

	}

}
