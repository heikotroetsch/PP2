package questionGen;

import general.Parameters;
import io.QuestionIO;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.Kategorie;
import data.Question;

public class QuestionGenGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private ArrayList<Question> fragen = new ArrayList<Question>();	
	private JTextField qField, a1Field, a2Field, a3Field, a4Field;
	private JComboBox<Kategorie> kategorien;
	private JComboBox<Question> fragenAuswahl;
	private JCheckBox[] answers;
	private ButtonGroup checkBoxGroup = new ButtonGroup();
	private int selectedAnswer = -1;
	private final Color bg1 = new Color(200,220,255);
	private final Color bg2 = new Color(255,200,200);
	private final Color bg3 = new Color(220,255,220);
	private final Color bg4 = new Color(255,250,220);
	private final Font f = new Font("Arial", Font.PLAIN, 18);
	
	private Question currentQuestion = null;

	public QuestionGenGUI(){
		super("Erstelle Frage(n)");
		//this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
	
			@Override
			public void windowClosing(WindowEvent e) {
				String filename = Parameters.questionFilename + "#";
				QuestionIO.writeQuestions(filename, QuestionGenGUI.this.fragen);
				QuestionGenGUI.this.dispose();
				System.exit(0);
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		this.setLocation(400, 50);
		this.init();
		this.createMenu();
		this.pack();
		this.setResizable(false);
	}
	
	private void init(){
		JPanel panel = new JPanel();
		//panel.setPreferredSize();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JLabel lk = new JLabel("Kategorie");
		JLabel lq = new JLabel("Frage: ");
		JLabel l1 = new JLabel("Antwort 1: ");
		JLabel l2 = new JLabel("Antwort 2: ");
		JLabel l3 = new JLabel("Antwort 3: ");
		JLabel l4 = new JLabel("Antwort 4: ");
		this.kategorien = new JComboBox<>(Kategorie.values());
		this.kategorien.setSelectedIndex(0);
		int n = 40;
		this.qField = new JTextField(n);
		this.a1Field = new JTextField(n);
		this.a2Field = new JTextField(n);
		this.a3Field = new JTextField(n);
		this.a4Field = new JTextField(n);
		
		final int anzAntworten = 4;
		
		
		this.answers = new JCheckBox[anzAntworten];
		for (int i=0;i<anzAntworten;i++){
			this.answers[i] = new AnswerBox();
			//this.answers[i] = new JCheckBox();
			this.answers[i].setBackground(bg1);
			this.answers[i].setPreferredSize(new Dimension(20,20));
			checkBoxGroup.add(this.answers[i]);
			CheckBoxListener checkLis = new CheckBoxListener(i+1);	
			this.answers[i].addActionListener(checkLis);
		}

		JPanel p1 = new JPanel();
		p1.add(lk);
		p1.add(kategorien);
		JPanel p2 = new JPanel();
		p2.add(lq);
		p2.add(Box.createHorizontalStrut(30));
		p2.add(qField);
		JLabel fragezeichen = new JLabel(" ?");
		fragezeichen.setFont(f);
		p2.add(fragezeichen);
		//p2.add(Box.createHorizontalStrut(10));
		JPanel p3 = new JPanel();
		p3.add(l1);
		p3.add(a1Field);
		p3.add(this.answers[0]);
		JPanel p4 = new JPanel();
		p4.add(l2);
		p4.add(a2Field);
		p4.add(this.answers[1]);
		JPanel p5 = new JPanel();
		p5.add(l3);
		p5.add(a3Field);	
		p5.add(this.answers[2]);
		JPanel p6 = new JPanel();
		p6.add(l4);
		p6.add(a4Field);	
		p6.add(this.answers[3]);
		

		for (JPanel p : new JPanel[] {p1,p2,p3,p4,p5,p6}){
			p.setBackground(bg1);
			panel.add(p);
		}

		for (Component c : new Component[] {lk,kategorien,
											lq, qField,
											l1, a1Field,
											l2, a2Field, 
											l3, a3Field, 
											l4, a4Field}){
			c.setFont(f);
			if (c.getClass().getName().equals(new JTextField().getClass().getName())){
				c.setBackground(this.bg4);
			}
		}
		panel.setBorder(BorderFactory.createEtchedBorder());
		this.getContentPane().add(panel,BorderLayout.CENTER);	
		
		JButton submit = createSubmitButton();
		submit.setFont(f);
		submit.setBackground(bg2);
		JPanel pb = new JPanel();
		pb.setBackground(bg3);
		pb.setBorder(BorderFactory.createEtchedBorder());
		pb.add(submit);
		this.getContentPane().add(pb,BorderLayout.SOUTH);
	}
	
	private void createMenu() {
		JMenu menu = new JMenu("Data");
		menu.setFont(f);
		ActionListener fileLis = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String cmd = e.getActionCommand();
				String filename = null;
				switch(cmd){
				case "export": 
					filename = QuestionIO.getFilename(false);
					if (filename != null){
						QuestionIO.writeQuestions(filename, QuestionGenGUI.this.fragen);
					}
					break;
				case "import":
					filename = QuestionIO.getFilename(true);
					if (filename != null){
						int old_size = QuestionGenGUI.this.fragen.size();
						ArrayList<Question> qList = QuestionIO.readQuestions(filename);
						if (qList.size() > 0){
							QuestionGenGUI.this.fragen.addAll(qList); // alle am Ende hinzufügen
							QuestionGenGUI.this.currentQuestion = qList.get(old_size);
							QuestionGenGUI.this.updateQuestionList();
						} else {
							JOptionPane.showMessageDialog(QuestionGenGUI.this, "No questions found in file!");
						}
					}
					break;
				default:
					break;
				}
			}
		};
		
		JMenuItem export = new JMenuItem("Fragen speichern");
		export.setBackground(bg3);
		export.addActionListener(fileLis);
		export.setActionCommand("export");
		menu.add(export);
		
		JMenuItem load = new JMenuItem("Fragenliste laden");
		load.setBackground(bg3);
		load.addActionListener(fileLis);
		load.setActionCommand("import");
		menu.add(load);
		
		JButton newButton = new JButton("new/clear");
		newButton.setBackground(bg3);
		newButton.setFont(this.f.deriveFont(14));
		newButton.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				QuestionGenGUI.this.currentQuestion = null;
				QuestionGenGUI.this.clearInputFields();
			}
		});
		
		
		JMenuBar menubar = new JMenuBar();
		menubar.add(menu);
		menubar.add(newButton);
		menubar.setBorder(BorderFactory.createLineBorder(Color.black));
		menubar.setBackground(bg3);

		this.setJMenuBar(menubar);
	}
	
	JButton createSubmitButton(){
		JButton submit = new JButton("Frage speichern");
		
		submit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String q = QuestionGenGUI.this.qField.getText();
				String a1 = QuestionGenGUI.this.a1Field.getText();
				String a2 = QuestionGenGUI.this.a2Field.getText();
				String a3 = QuestionGenGUI.this.a3Field.getText();
				String a4 = QuestionGenGUI.this.a4Field.getText();
				Kategorie k = (Kategorie)QuestionGenGUI.this.kategorien.getSelectedItem();
				int lsg = QuestionGenGUI.this.selectedAnswer;
				if ((q.length() > 0) && (a1.length() > 0) && (a2.length() > 0) && (a3.length() > 0) && (a4.length() > 0)){
					if (lsg == -1){
						JOptionPane.showMessageDialog(null, 
								"Bitte korrekte Antwort auswählen", "Fehlende Information", JOptionPane.ERROR_MESSAGE);
					} else {
						if (QuestionGenGUI.this.currentQuestion == null){
							QuestionGenGUI.this.currentQuestion = new Question(k,q,a1,a2,a3,a4,lsg);
							QuestionGenGUI.this.fragen.add(QuestionGenGUI.this.currentQuestion);
							QuestionGenGUI.this.updateQuestionList();
						} else {
							Question qObj = QuestionGenGUI.this.currentQuestion;
							qObj.setFrage(q);
							qObj.setAuswahl(new String[]{a1,a2,a3,a4});
							qObj.setAntwort(lsg);
							qObj.setKategorie(k);
						}
						// clear input fields
						QuestionGenGUI.this.clearInputFields();
					}
				} else {
					JOptionPane.showMessageDialog(null, 
							"Bitte alle Felder ausfüllen", "Fehlende Information", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		return submit;
	}
	
	private void clearInputFields(){
		qField.setText("");
		this.a1Field.setText("");
		this.a2Field.setText("");
		this.a3Field.setText("");
		this.a4Field.setText("");
		this.checkBoxGroup.clearSelection();
		this.selectedAnswer = -1;
		this.currentQuestion = null;
//		if (this.fragenAuswahl != null){
//			this.fragenAuswahl.setSelectedIndex(-1); // clear selection
//		}
	}
	
	private void updateQuestionList(){
		if (this.fragenAuswahl != null){
			this.getJMenuBar().remove(this.fragenAuswahl);
		}
		if ((this.fragen != null) && (this.fragen.size() > 0)){
			this.fragenAuswahl = new JComboBox<Question>(new Vector<Question>(this.fragen));
			this.fragenAuswahl.setBackground(bg3);
			this.fragenAuswahl.setFont(this.f.deriveFont(12));
			this.fragenAuswahl.setMaximumSize(new Dimension(200,30));
			QSelectionListener lis = new QSelectionListener();
			this.fragenAuswahl.addItemListener(lis);
			this.fragenAuswahl.setSelectedItem(this.currentQuestion);
			this.getJMenuBar().add(this.fragenAuswahl);
			this.updateQuestion();
			this.revalidate();
			this.repaint();
		}
	}
	
	private void updateQuestion(){
		Question q = this.currentQuestion;
		this.qField.setText(q.getFrage());
		String[] auswahl = q.getAuswahl();
		this.a1Field.setText(auswahl[0]);
		this.a2Field.setText(auswahl[1]);
		this.a3Field.setText(auswahl[2]);
		this.a4Field.setText(auswahl[3]);
		int i = q.getAntwort();
		this.answers[i-1].setSelected(true);
		this.selectedAnswer = i;
		this.kategorien.setSelectedItem(q.getKategorie());
	}
	
	private class AnswerBox extends JCheckBox{
		private static final long serialVersionUID = 1L;
		int sz = 20;
		public void paint(Graphics g){
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(QuestionGenGUI.this.bg4);
			g2.fillRect(0,0,sz,sz);
			g2.setColor(Color.black);
			g2.drawRect(0, 0, sz-1,sz-1);
			if (this.isSelected()){
				g2.setStroke(new BasicStroke(2.0f));
				g2.drawLine(1, 1, sz-1, sz-1);
				g2.drawLine(0,sz-1,sz-1,0);
			}
		}	
	}
	
	private class QSelectionListener implements ItemListener{

		public void itemStateChanged(ItemEvent e) {
			JComboBox<Question> cb = (JComboBox<Question>)e.getSource();
			Question q = (Question) cb.getSelectedItem();
			QuestionGenGUI.this.currentQuestion = q;
			if (q != null){
				QuestionGenGUI.this.updateQuestion();
			} else {
				QuestionGenGUI.this.clearInputFields();
			}
			// repaint();
		}
		
	}

	private class CheckBoxListener implements ActionListener{
		int boxNr;
		public CheckBoxListener(int i){
			this.boxNr = i;
		}
		public void actionPerformed(ActionEvent e) {
			QuestionGenGUI.this.selectedAnswer = this.boxNr;
		}
	}
	
	public static void main(String[] args) {
		new QuestionGenGUI().setVisible(true);
	}

}
