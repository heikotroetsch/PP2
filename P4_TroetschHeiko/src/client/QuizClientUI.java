package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import data.Kategorie;
import data.Question;

public class QuizClientUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private QuizClient client;
	private JPanel kategoriePanel, fragenPanel, nextPanel, buttonPanel;
	
	final static Font font = new Font("Arial",Font.PLAIN,14);
	final static Color antwortColor = new Color(255,248,220);
	final static Color fragenColor = new Color(255,255,240);
	final static Color nextColor = new Color(255,222,173);
	final static Color rightAnswer = Color.green;
	final static Color wrongAnswer = Color.red;
	
	private JButton antwortButtons[] = new JButton[4];
	private JLabel fragenLabel;
	private JButton nextButton;
	
	private int antwortClient = -1;
	
	Question currentQuestion;
	
	public QuizClientUI(QuizClient client){
		super("Quiz Client");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				QuizClientUI.this.client.endQuiz();
				QuizClientUI.this.dispose();
				System.exit(0);
			}
		});
		this.client = client;
		this.initUI();
		this.pack();
		this.setLocation(500,500);
		//this.setResizable(false);
		this.setVisible(true);
	}
	
	private void initUI(){
		// Panel showing categories
		this.kategoriePanel = new JPanel();
		this.kategoriePanel.setLayout(new GridLayout(0,1));
		Kategorie[] kategorien = Kategorie.values();
		int anzKategorien = kategorien.length;
		
		for (Kategorie k : kategorien){
			JButton b = new JButton(k.name());
			ActionListener butLis = new KatButListener(k);
			b.addActionListener(butLis);
			b.setBackground(k.getColor());
			b.setFont(font.deriveFont(Font.BOLD));
			this.kategoriePanel.add(b);
		}
		int h = (anzKategorien*30) >= 130 ? (anzKategorien*30) : 130;
		this.kategoriePanel.setPreferredSize(new Dimension(550,h));
		
		// Panel showing question and possible answers and next Button
		this.fragenPanel = new JPanel();
		this.fragenLabel = new JLabel("");
		//this.fragenLabel.setOpaque(true);
		//this.fragenLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, 
		//									new Color(126,192,238),new Color(79,148,205)));
		//this.fragenLabel.setBackground(fragenColor);
		this.fragenLabel.setFont(font.deriveFont(Font.BOLD));
		//this.fragenLabel.setPreferredSize(new Dimension(250,40));
		this.buttonPanel = new JPanel();
		this.buttonPanel.setLayout(new GridLayout(2,2,5,5));
		for (int i=0;i<antwortButtons.length;i++){
			AntwortListener aLis = new AntwortListener(i+1);
			antwortButtons[i] = new JButton("");
			antwortButtons[i].setFont(font);
			antwortButtons[i].setBackground(antwortColor);
			antwortButtons[i].setPreferredSize(new Dimension(250,30));
			buttonPanel.add(antwortButtons[i]);	
			antwortButtons[i].addActionListener(aLis);
		}
		this.nextPanel = new JPanel();
		this.nextButton = new JButton("Nächste Frage!");
		this.nextButton.setEnabled(false);
		this.nextButton.setBackground(nextColor);
		this.nextButton.addActionListener(new NextListener());
		this.nextPanel.add(nextButton);
		
		//this.fragenPanel.setLayout(new BoxLayout(this.fragenPanel,BoxLayout.Y_AXIS));
		//this.fragenPanel.setLayout(new GridLayout(0,1));

		this.fragenPanel.add(this.fragenLabel);
		this.fragenPanel.add(buttonPanel);
		this.fragenPanel.add(nextPanel);
		this.fragenPanel.setPreferredSize(new Dimension(550,0));
		this.getContentPane().add(this.kategoriePanel,BorderLayout.CENTER);
	}
	
	private void prepareFragenPanel(Question q){
		this.fragenLabel.setText(q.getFrage() + "?");
		for (int i=0;i<this.antwortButtons.length;i++){
			this.antwortButtons[i].setText(q.getAuswahl(i));
			this.antwortButtons[i].setBackground(antwortColor); // reset color
			this.antwortButtons[i].setEnabled(true);
			this.antwortButtons[i].setOpaque(true);
			this.antwortButtons[i].setBorder(null);


		}
		Color col = q.getKategorie().getColor();
		this.fragenPanel.setBackground(col);
		this.buttonPanel.setBackground(col);
		this.nextPanel.setBackground(col);
	}
	
	
	private void updateFragenPanel(int korrekteAntwort){
		if (this.antwortClient != korrekteAntwort){
			this.antwortButtons[this.antwortClient-1].setBackground(wrongAnswer);
			this.antwortButtons[korrekteAntwort-1].setBackground(rightAnswer);
		} else {
			this.antwortButtons[this.antwortClient-1].setBackground(rightAnswer);
			//punkte hoch
		}
	}
	
	private void exchangePanels(JPanel newPanel){
		this.getContentPane().remove(0);
		this.getContentPane().add(newPanel,BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}
	
	private class KatButListener implements ActionListener {
		Kategorie k;
		public KatButListener (Kategorie kat){
			this.k = kat;
		}
		public void actionPerformed(ActionEvent e) {
			Question q = QuizClientUI.this.client.getQuestionFromServer(k);
			if (q != null){
				QuizClientUI.this.currentQuestion = q;
				QuizClientUI.this.prepareFragenPanel(q);
				QuizClientUI.this.exchangePanels(QuizClientUI.this.fragenPanel);	
			}
		}
	}
	
	private class AntwortListener implements ActionListener {
		int a_nr;
		
		public AntwortListener (int nr){
			this.a_nr = nr;
		}
	
		@Override
		public void actionPerformed(ActionEvent arg0) {
			QuizClientUI.this.antwortClient = a_nr;
			int richtige_antw = QuizClientUI.this.client.getAnswerFromServer(QuizClientUI.this.currentQuestion.getHashCodeMotherQuestion());
			QuizClientUI.this.updateFragenPanel(richtige_antw);
			
			for (JButton b : QuizClientUI.this.antwortButtons){
				b.setEnabled(false);
			}


			QuizClientUI.this.nextButton.setEnabled(true);
		}
	}
	
	private class NextListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			QuizClientUI.this.nextButton.setEnabled(false);
			QuizClientUI.this.exchangePanels(QuizClientUI.this.kategoriePanel);
		}
	}
	
}
