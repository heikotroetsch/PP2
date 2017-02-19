package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.GlobalData;
import model.Vocable;

import controller.Controller;
import controller.Settings;
	
public class InsertGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JCheckBox cbUnit, cbSection;
	private JComboBox<Integer> unitBox;
	private JComboBox<Character>sectionBox;
	private JTextField word,translation1,translation2, translation3, example1, example2, example3;
	private final Color bg = GUISettings.color_8;
	private Vocable currentVoc = GlobalData.dummyVoc;


	public InsertGUI(Controller c){
		super("Add Vocables");
		this.controller = c;		
		this.getContentPane().setBackground(GUISettings.color_0);
		this.createGUIPanel();
		this.setLocation(new Point(60, 300));
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e){
				Vocable initV = (GlobalData.searchVocable != null) ? GlobalData.searchVocable : InsertGUI.this.currentVoc;
				if (initV != null){
					InsertGUI.this.word.setText(initV.getWord());
					ArrayList<String> transl = initV.getTranslations();
					JTextField[] trFields = new JTextField[]{translation1,translation2,translation3};
					for (int i=0;i<transl.size() && (i<3);i++){ // show. max. 3 translations
						trFields[i].setText(transl.get(i));
						trFields[i].setCaretPosition(0);
					}
					ArrayList<String> examples = initV.getExamples();
					JTextField[] exFields = new JTextField[]{example1,example2,example3};
					for (int i=0;i<examples.size() && (i<3);i++){ // show. max. 3 examples
						exFields[i].setText(examples.get(i));
						exFields[i].setCaretPosition(0);
					}

					int u = initV.getUnit();
					int s = initV.getSection();
					if (u == -1){
						InsertGUI.this.cbUnit.setSelected(false);
						InsertGUI.this.unitBox.setEnabled(false);
					} else {
						InsertGUI.this.unitBox.setSelectedIndex(u-1);
						InsertGUI.this.unitBox.setEnabled(true);
					}
					if (s == -1){
						InsertGUI.this.cbSection.setSelected(false);
						InsertGUI.this.sectionBox.setEnabled(false);
					} else {
						InsertGUI.this.sectionBox.setSelectedIndex(s-1);
						InsertGUI.this.sectionBox.setEnabled(true);
					}
				}
				InsertGUI.this.currentVoc = initV;
			}
		});
		this.pack();
	}
	
	private void createGUIPanel(){
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		p.setLayout(new GridLayout(0,1,10,10));
		p.setAlignmentX(LEFT_ALIGNMENT);
		p.add(createUnitAndSectionBox());
		p.add(createInputPanel());
		p.add(createActionPanel());
		Component[] comps = p.getComponents();
		for (Component c : comps){
			c.setBackground(bg);
		}
		p.setBackground(GUISettings.color_8);
		this.getContentPane().add(p,BorderLayout.CENTER);
		
		JPanel p2 = new JPanel();
		p2.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		p2.setBackground(bg);
		p2.setAlignmentX(CENTER_ALIGNMENT);
		JLabel l = new JLabel("English to German:");
		l.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		p2.add(l);
		this.getContentPane().add(p2,BorderLayout.NORTH);
	}
	
	private JPanel createUnitAndSectionBox(){
		JPanel p = new JPanel();
		JPanel pu = new JPanel();
		JPanel ps = new JPanel();
		//p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
		//p.setLayout(new GridLayout(1,0,5,5));
		this.cbUnit = new JCheckBox("Unit");
		this.cbUnit.setSelected(true);
		this.cbSection = new JCheckBox("Section");
		this.unitBox = new JComboBox<>(new Integer[]{1,2,3,4,5,6,7,8});
		this.cbSection.setSelected(true);
		this.sectionBox = new JComboBox<>(new Character[]{'A','B','C','D'});
		this.unitBox.setSelectedIndex(0);
		this.sectionBox.setSelectedIndex(0);
		
		ActionListener cbListener = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JCheckBox cb = (JCheckBox) e.getSource();
				if (cb == InsertGUI.this.cbUnit){
					InsertGUI.this.unitBox.setEnabled(cb.isSelected());
					if (!cb.isSelected()){
						InsertGUI.this.sectionBox.setEnabled(false);
						InsertGUI.this.cbSection.setSelected(false);
					}
				} else {
					InsertGUI.this.sectionBox.setEnabled(cb.isSelected());
				}
			}		
		};
		
		for (JCheckBox cb : new JCheckBox[]{this.cbUnit,this.cbSection}){
			cb.setBackground(this.bg);
			cb.addActionListener(cbListener);
		} 
		
		for (JComponent c : new JComponent[]{this.cbUnit, this.unitBox}){
			pu.add(c);
		}
		for (JComponent c : new JComponent[]{this.cbSection, this.sectionBox}){
			ps.add(c);
		}
		//p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
		p.setLayout(new GridLayout(2,0,5,5));
		p.add(pu);
		pu.setBackground(this.bg);
		pu.setAlignmentX(LEFT_ALIGNMENT);
		ps.setBackground(this.bg);
		p.add(ps);
		ps.setAlignmentX(LEFT_ALIGNMENT);
		p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		return p;
	}
	
	private JPanel createInputPanel(){
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(4,3));
		JLabel l1 = new JLabel("english word");
		JLabel l2 = new JLabel("translation(s) to German");
		JLabel l3 = new JLabel("examples of usage");
		for (JLabel l : new JLabel[]{l1,l2,l3}){
			l.setFont(GUISettings.ff);
		}
		this.word = new JTextField(20);
		this.translation1 = new JTextField(20);
		this.translation2 =  new JTextField(20);
		this.translation3 =  new JTextField(20);
		this.example1 = new JTextField(20);
		this.example2 = new JTextField(20);
		this.example3 = new JTextField(20);
		
		Box empty1 = Box.createHorizontalBox();
		Box empty2 = Box.createHorizontalBox();
		for (JComponent c: new JComponent[]{l1,l2,l3,
											this.word,this.translation1, this.example1,
											empty1, this.translation2, this.example2,
											empty2, this.translation3, this.example3}){
			c.setFont(GUISettings.ff);
			p.add(c);
		}
		this.add(p);
		return p;	
	}
	
	private JPanel createActionPanel(){
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
		p.setLayout(new GridLayout(1,0,40,40));
		ImageIcon icon1 = new ImageIcon(Settings.imageClear);
		ImageIcon icon2 = new ImageIcon(Settings.imageAdd);
		ImageIcon icon3 = new ImageIcon(Settings.imageClose);
		JButton clear = new JButton("Clear",icon1);
		clear.setActionCommand(Controller.CLEAR);
		JButton add = new JButton("Add/Update",icon2);
		add.setActionCommand(Controller.ADD);
		JButton close = new JButton("Close",icon3);
		close.setActionCommand(Controller.CLOSE);
		ActionListener buttonLis = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = e.getActionCommand();
				switch(s){
				case Controller.ADD:
					InsertGUI.this.updateVocable();
				case Controller.CLOSE: 
					InsertGUI.this.controller.execCommand(s);
					break;
				case Controller.CLEAR:  // clear all textfields
					clearAll();
					InsertGUI.this.currentVoc = null;
					break;
				default: break;
				}
				
			}
		};
		Component b1 = Box.createHorizontalStrut(80);
		Component b2 = Box.createHorizontalStrut(80);
		for (JButton b : new JButton[]{clear, add, close}){
			b.setBorder(BorderFactory.createRaisedBevelBorder());
			b.setPreferredSize(new Dimension(80,80));
			b.setVerticalTextPosition(AbstractButton.TOP);
		    b.setHorizontalTextPosition(AbstractButton.CENTER);
		    b.addActionListener(buttonLis);	
		    //p.add(b);
		}
		for (Component c : new Component[]{clear, b1, add, b2, close}){
			p.add(c);
		}
		
		return p;
	}
	

	private void clearAll(){
		for (JTextField tf : new JTextField[]{InsertGUI.this.word, 
				InsertGUI.this.translation1,
				InsertGUI.this.translation2,
				InsertGUI.this.translation3,
				InsertGUI.this.example1,
				InsertGUI.this.example2,
				InsertGUI.this.example3}){
			tf.setText("");
		}
		this.currentVoc = null;
	}


	// returns array with 2 elements: Unit and section number (1,2,...)
	private int[] getUnitAndSection(){
		int[] us = {-1,-1};
		if (this.cbUnit.isSelected()){
			us[0] = this.unitBox.getSelectedIndex() + 1;
		}
		if (this.cbSection.isSelected()){
			us[1] = this.sectionBox.getSelectedIndex() + 1;
		}
		return us;
	}
	
	// returns english word
	private String getWord(){
		return this.word.getText();
	}
	
	// return non-empty entries in an array of JTextFields
	private ArrayList<String> getAllEntries(JTextField[] fields){
		ArrayList<String>  trls = new ArrayList<String>();
		for (JTextField tf : fields){
			String s = tf.getText().trim();
			if (s.length() > 0){
				trls.add(s);
			}
		}
		return trls;
	}
	
	private ArrayList<String> getTranslations(){
		return getAllEntries(new JTextField[]{this.translation1, this.translation2, this.translation3});
	}
	
	private ArrayList<String> getExamples(){
		return getAllEntries(new JTextField[]{this.example1, this.example2, this.example3});
	}
	
	
	public Vocable getVocable(){
		return this.currentVoc;
	}
	
	/* updates the attribute which stores the currently shown vocable from the Textfields and the
	 * unit and section Comboboxes.
	 */
	private void updateVocable(){
		String wrd = this.getWord();
		ArrayList<String> translations = this.getTranslations();
		ArrayList<String> examples = this.getExamples();
		if ((wrd.length() > 0) && (translations.size() > 0)){ // at least the word and one translation of it
			int[] ups = getUnitAndSection();
			if (this.currentVoc == null){
				this.currentVoc = new Vocable(Settings.sourceLanguage,Settings.targetLanguage,wrd,translations,examples,ups[0],ups[1]);
			} else { // update
				this.currentVoc.setWord(wrd);
				this.currentVoc.setTranslations(translations);
				this.currentVoc.setExamples(examples);
				this.currentVoc.setUnit(ups[0]);
				if (ups[0] != -1){
					this.cbUnit.setSelected(true);
				} else {
					this.cbUnit.setSelected(false);
				}
				this.currentVoc.setSection(ups[1]);
				if (ups[1] != -1){
					this.cbSection.setSelected(true);
				} else {
					this.cbSection.setSelected(false);
				}
			}
		}
	}
	
	
}
