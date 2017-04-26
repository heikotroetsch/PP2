package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

import model.LearnSettings;
import model.LearningContext;
import model.Vocable;
import controller.Controller;
import controller.Settings;

import static controller.Controller.*;

 public class LearnGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final int SOURCE_GIVEN=0,TARGET_GIVEN=1, MIXED=2;
	private Controller controller;
	private JComboBox<Integer> numVoc;
	private JCheckBox[] units;
	private JCheckBox[] sections;
	private JTextField word1, word2;
	private JButton start, check, next;
	private JRadioButton[] modes = new JRadioButton[3];
	private final Color bg = GUISettings.color_11;
	private final int width = 600;
	private int learnMode = TARGET_GIVEN; // specifies which word is being as given
	private NextButtonListener nextListener = new NextButtonListener();
	
	public LearnGUI(Controller c){
		this.controller = c;
		this.getContentPane().setBackground(this.bg);
		this.createGUIPanel();
		this.setLocation(new Point(650, 60));
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setPreferredSize(new Dimension(this.width,400));
		this.pack();
	}
	
	private void createGUIPanel(){
		JPanel p1 = createControlPanel();
		this.getContentPane().add(p1,BorderLayout.CENTER);	

		JPanel p2 = this.createQuizPanel();
		this.getContentPane().add(p2,BorderLayout.NORTH);	
		
		JPanel p3 = this.createActionPanel();
		this.getContentPane().add(p3,BorderLayout.SOUTH);
		
		for (JPanel p : new JPanel[]{p1,p2,p3}){
			p.setBackground(this.bg);
		}
		this.init();
	}
	
	private void init(){
		this.word1.setText("");
		this.word1.setEnabled(false);
		this.word2.setText("");
		this.word2.setEnabled(false);
		this.start.setEnabled(true);
		this.check.setEnabled(false);
	}
	
	private JPanel createControlPanel(){
		JLabel l1 = new JLabel("Choose from selected Units and Sections:");

		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(1,0,5,5));
		p1.setBackground(this.bg);
		//p1.setPreferredSize(new Dimension(this.width, 30));
		this.units = new JCheckBox[Settings.maxUnit];
		for (int i=0;i < this.units.length;i++){
			this.units[i] = new JCheckBox("U" + (i+1));
			this.units[i].setAlignmentX(LEFT_ALIGNMENT);
			this.units[i].setBackground(this.bg);
			p1.add(this.units[i]);
		}
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(1,0,5,5));
		p2.setBackground(this.bg);
		//p2.setPreferredSize(new Dimension(this.width / 2, 30));
		this.sections = new JCheckBox[Settings.maxSection];
		for (int i=0;i < this.sections.length;i++){
			this.sections[i] = new JCheckBox("" + (char)('A' + i));
			this.sections[i].setBackground(this.bg);
			p2.add(this.sections[i]);
		}
		JPanel p3 = new JPanel();
		JLabel l2 = new JLabel("Number of vocables: ");
		this.numVoc = new JComboBox<>(new Integer[]{5,10,15,20});
		this.numVoc.setSelectedIndex(1);
		p3.add(l2);
		p3.add(this.numVoc);
	
		JPanel p4 = new JPanel();
		p4.add(new JLabel("Interrogation Mode: "));
		ActionListener rbListener = new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				for (int i=0;i<LearnGUI.this.modes.length;i++){
					if (LearnGUI.this.modes[i].isSelected()){
						int m;
						switch(i){
						case 0: m = SOURCE_GIVEN; break;
						case 1: m = TARGET_GIVEN; break;
						case 2: m = MIXED; break;
						default: m =  TARGET_GIVEN; break;
						}
						LearnGUI.this.learnMode = m;
						break;
					}
				}
			}
			
		};
		this.modes[0] = new JRadioButton(Settings.sourceLanguage.getDisplayLanguage()  + " -> " + Settings.targetLanguage.getDisplayLanguage());
		this.modes[1] = new JRadioButton(Settings.targetLanguage.getDisplayLanguage() + " -> " + Settings.sourceLanguage.getDisplayLanguage());
		this.modes[1].setSelected(true);
		this.modes[2] = new JRadioButton("Mixed");
		ButtonGroup bGroup = new ButtonGroup();
		for (JRadioButton br : this.modes){
			br.setBackground(this.bg);
			br.addActionListener(rbListener);
			bGroup.add(br);
			p4.add(br);
		}
		
		
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
		p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
		for (JComponent c : new JComponent[]{l1,p1,p2,p3,p4}){
			c.setAlignmentX(LEFT_ALIGNMENT);
			c.setBackground(this.bg);
			//c.setBorder(BorderFactory.createLineBorder(Color.black));
			p.add(c);
		}	
		
		p1.setMaximumSize(new Dimension(this.width, 30));
		p2.setMaximumSize(new Dimension(280, 30));
		p3.setMaximumSize(new Dimension(210, 30));
		//p4.setMaximumSize(new Dimension(400, 30));

		return p;
	}
	
	private JPanel createQuizPanel(){
		JPanel p = new JPanel();
		Border b = BorderFactory.createLineBorder(GUISettings.color_7);
		p.setBorder(BorderFactory.createTitledBorder(b,"Quiz"));
		p.setLayout(new GridLayout(2,3,10,10));
		//p.setBorder(BorderFactory.createEmptyBorder(10,10,10,5));
		p.setPreferredSize(new Dimension(this.width, 100));

		JLabel l1 = new JLabel(Settings.sourceLanguage.getLanguage());
		JLabel l2 = new JLabel(Settings.targetLanguage.getLanguage());
		this.word1 = new JTextField(25);
		this.word2 = new JTextField(25);
		next = new JButton("Next");
		next.setEnabled(false);
		next.setBorder(BorderFactory.createRaisedBevelBorder());
		next.setBackground(GUISettings.color_5);
		next.addActionListener(this.nextListener);
		Box empty = Box.createHorizontalBox();
		for (JComponent c : new JComponent[]{l1, l2, empty, this.word1, this.word2, next}){
			c.setFont(GUISettings.ff);
			p.add(c);
		}
		
		return p;
	}
	
	private JPanel createActionPanel(){
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
		p.setLayout(new GridLayout(1,0,10,10));
		p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		ImageIcon icon1 = new ImageIcon(Settings.imageStart);
		ImageIcon icon2 = new ImageIcon(Settings.imageCheck);
		ImageIcon icon3 = new ImageIcon(Settings.imageClose);
		this.start = new JButton("Start",icon1);
		this.start.setActionCommand(START);
		this.check = new JButton("Check",icon2);
		this.check.setActionCommand(CHECK);
		JButton close = new JButton("Close",icon3);
		close.setActionCommand(CLOSE);
		ActionListener buttonLis = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = e.getActionCommand();
				switch(s){
				case START:
					LearnGUI.this.controller.execCommand(START);
					break;
				case CHECK:
					LearnGUI.this.controller.execCommand(CHECK);
					break;
				case CLOSE: 
					LearnGUI.this.controller.execCommand(s);
					break;
				default: break;
				}

			}
		};
		for (JButton b : new JButton[]{check, start, close}){
			b.setBorder(BorderFactory.createRaisedBevelBorder());
			b.setPreferredSize(new Dimension(80,100));
			b.setMaximumSize(new Dimension(80,100));
			b.setVerticalTextPosition(AbstractButton.TOP);
			b.setHorizontalTextPosition(AbstractButton.CENTER);
			b.addActionListener(buttonLis);	
			p.add(b);
		}

		return p;
	}
	
	public LearningContext getLearnSettings(){
		ArrayList<Integer> chosenUnits = new ArrayList<Integer>();
		for (int i=0;i<this.units.length;i++){
			if (units[i].isSelected()){
				chosenUnits.add((i+1));  // autoboxing
			}
		}
		ArrayList<Integer> chosenSections = new ArrayList<Integer>();
		for (int i=0;i<this.sections.length;i++){
			if (sections[i].isSelected()){
				chosenSections.add((i+1));  // autoboxing
			}
		}
		int anzahl = (Integer)this.numVoc.getSelectedItem(); // autounboxing
		LearningContext ls = null;
		ls = new LearnSettings(chosenUnits,chosenSections,anzahl,this.learnMode);

		return ls;
	}
	
	public void enableLearning(){
		//LearnGUI.this.nextListener.update(LearnGUI.this.learnMode, LearnGUI.this.vocList);
		LearnGUI.this.next.setEnabled(true);
		LearnGUI.this.check.setEnabled(false);
		LearnGUI.this.start.setEnabled(true);
		LearnGUI.this.word1.setEnabled(true);
		LearnGUI.this.word2.setEnabled(true);
	}
	
	public void enableCheck(){
		LearnGUI.this.next.setEnabled(false);
		LearnGUI.this.check.setEnabled(true);
		LearnGUI.this.start.setEnabled(false);
		LearnGUI.this.word1.setEnabled(false);
		LearnGUI.this.word2.setEnabled(false);	
	}
	
	public void disableLearning(){
		LearnGUI.this.next.setEnabled(false);
		LearnGUI.this.check.setEnabled(false);
		LearnGUI.this.start.setEnabled(true);
		LearnGUI.this.word1.setEnabled(false);
		LearnGUI.this.word2.setEnabled(false);	
	}
	
	private class NextButtonListener implements ActionListener{			
		public void actionPerformed(ActionEvent arg0) {
			LearnGUI.this.controller.execCommand(NEXT);
		}
	}

	/* sets the content of the textfields according to the learning mode
	 * (either first or second textfield)
	 */
	public void setEntry(Vocable v, int mode){
		if (mode == TARGET_GIVEN){
			this.word1.setText("");
			this.word2.setText(v.getRandomTranslation());
		} else {
			this.word1.setText(v.getWord());
			this.word2.setText("");
		}
		
	}
	/* depending on the mode, the method returns the text in the 
	 * corresponding textfield (either the word in source or in the 
	 * target language
	 */
	public String getEntry(int mode){
		if (mode == SOURCE_GIVEN){
			return this.word2.getText();
		} else {
			return this.word1.getText();
		}
	}
}
