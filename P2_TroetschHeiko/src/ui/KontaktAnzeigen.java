package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.Kontakt;

/**
 * Fenster, um einen Kontakt anzuzeigen.
 */
public class KontaktAnzeigen extends JFrame {

	private static final long serialVersionUID = 1L;
	private Kontakt kontakt;

	/**
	 * Konstruktor f�r das neue Fenster.
	 * 
	 * @param alterKontakt
	 *            null, falls ein neuer Kontakt angelegt wird.
	 */
	public KontaktAnzeigen(Kontakt kontakt) {
		super(kontakt.getName());
		this.kontakt = kontakt;
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
		JLabel[] field = new JLabel[6];
		JButton b = new JButton();

		label[0] = new JLabel("Name:      ");
		label[1] = new JLabel("Nummer: ");
		label[2] = new JLabel("Mail:         ");
		label[3] = new JLabel("<HTML>Stra&szlig;e:&#160;&#160;&#160;</HTML>");
		label[4] = new JLabel("Wohnort:");

		for (int i = 0; i < 5; i++) {
			panel[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));
			field[i] = new JLabel();
			if(i!=2){
				panel[i].add(label[i]);
				panel[i].add(field[i]);
			}else{
				panel[i].add(label[i]);
				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Desktop desktop = Desktop.getDesktop(); 
						try {
							desktop.mail(new URI("mailto:john@example.com?subject=Hello%20World"));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				panel[i].add(b);
			}
			if (i == 4) {
				field[5] = new JLabel();
				panel[4].add(field[5]);
			}
			bigPanel.add(panel[i]);
		}

		field[0].setText(kontakt.getName());
		if (kontakt.getNummer() != null) {
			field[1].setText(kontakt.getNummer());
		}
		if (kontakt.getMail() != null) {
			field[2].setText(kontakt.getMail());
			b.setText(kontakt.getMail());
		}
		if (kontakt.getStrasse() != null) {
			field[3].setText(kontakt.getStrasse());
		}
		if (kontakt.getWohnort() != null) {
			field[4].setText(kontakt.getWohnort().getPlz());
			field[5].setText(kontakt.getWohnort().getName());
		}

		JButton speichern = new JButton("<HTML>Schlie&szlig;en</HTML>");
		speichern.addActionListener(new ActionListener() {

			/**
			 * Schliesst das Fenster.
			 */
			public void actionPerformed(ActionEvent arg0) {
				KontaktAnzeigen.this.dispose();
			}

		});

		c.add(bigPanel, BorderLayout.CENTER);
		c.add(speichern, BorderLayout.SOUTH);

	}
}
