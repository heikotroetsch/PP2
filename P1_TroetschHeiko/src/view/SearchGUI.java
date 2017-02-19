package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.Controller;
import controller.Settings;
import model.GlobalData;
import model.Vocable;

import static controller.Controller.*;

public class SearchGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JTextField word1, word2;
	private JList<Vocable> results = new JList<Vocable>(new DefaultListModel<Vocable>());
	private JButton clear, search, edit, close;
	private final Color bg = GUISettings.color_10;

	public SearchGUI(Controller c){
		super("Search");
		this.controller = c;
		this.getContentPane().setBackground(this.bg);
		this.createGUIPanel();
		this.setLocation(new Point(60, 660));
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setPreferredSize(new Dimension(400,400));
		this.pack();
	}
	
	private void createGUIPanel(){
		JPanel p1 = createSearchPanel();
		this.getContentPane().add(p1,BorderLayout.CENTER);	

		JPanel p2 = this.createActionPanel();
		this.getContentPane().add(p2,BorderLayout.SOUTH);	
	}
	
	private JPanel createSearchPanel(){
		JPanel p1 = new JPanel();
		p1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		p1.setBackground(this.bg);
		p1.setLayout(new GridLayout(2,0,10,10));
		JLabel l1 = new JLabel(Settings.sourceLanguage.getLanguage());
		JLabel l2 = new JLabel(Settings.targetLanguage.getLanguage());
		this.word1 = new JTextField("english");
		this.word2 = new JTextField(25);
		for (JComponent c : new JComponent[]{l1,l2,this.word1,this.word2}){
			c.setFont(GUISettings.ff);
			p1.add(c);
		}
		JPanel p2 = createResultPanel();
		JPanel p = new JPanel();
		p.setBackground(this.bg);
		p.setFont(GUISettings.ff);
		p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
		p1.setAlignmentX(LEFT_ALIGNMENT);
		p2.setAlignmentX(LEFT_ALIGNMENT);
		p.add(p1);
		p.add(p2);
		return p;
	}
	
	private JPanel createResultPanel(){
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		p.setBackground(this.bg);
		//p.setAlignmentX(LEFT_ALIGNMENT);
		JLabel lt = new JLabel("Search Results");
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		lt.setAlignmentX(LEFT_ALIGNMENT);
		lt.setFont(GUISettings.ff);
		this.results.setVisibleRowCount(5);
		this.results.setBorder(BorderFactory.createLineBorder(Color.black));
		ListSelectionListener searchResultListener = new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				Vocable v = SearchGUI.this.results.getSelectedValue();
				GlobalData.searchVocable = v;
//				if ((SearchGUI.this.results != null) && (SearchGUI.this.results.getModel().getSize() > 0)){
			}
			
		};
		this.results.addListSelectionListener(searchResultListener);
		JScrollPane scroller = new JScrollPane(this.results);
		scroller.setAlignmentX(LEFT_ALIGNMENT);
		DefaultListModel<Vocable> model = (DefaultListModel<Vocable>) (this.results.getModel());
		model.addElement(GlobalData.dummyVoc);
		p.setBackground(bg);
		p.add(lt);
		p.add(Box.createVerticalStrut(5));
		p.add(scroller);
		return p;
	}
	
	private JPanel createActionPanel(){
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
		p.setLayout(new GridLayout(1,0,10,10));
		p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		ImageIcon icon1 = new ImageIcon(Settings.imageClear);
		ImageIcon icon2 = new ImageIcon(Settings.imageSearch);
		ImageIcon icon3 = new ImageIcon(Settings.imageEdit);
		ImageIcon icon4 = new ImageIcon(Settings.imageClose);
		this.clear = new JButton("Clear",icon1);
		this.clear.setActionCommand(CLEAR);
		this.search = new JButton("Search",icon2);
		this.search.setActionCommand(SEARCH);
		this.edit = new JButton("Edit",icon3);
		this.edit.setActionCommand(EDIT);
		this.close = new JButton("Close",icon4);
		this.close.setActionCommand(CLOSE);
		ActionListener buttonLis = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = e.getActionCommand();
				switch(s){
				case SEARCH:
					SearchGUI.this.controller.execCommand(s);
					break;
				case EDIT:
					SearchGUI.this.controller.execCommand(s);
					break;
				case CLOSE: 
					SearchGUI.this.controller.execCommand(s);
					break;
				case CLEAR:  // clear all textfields
					for (JTextField tf : new JTextField[]{SearchGUI.this.word1, 
														  SearchGUI.this.word2}													  ){
						tf.setText("");
					}
					SearchGUI.this.clearResultList((DefaultListModel<Vocable>)SearchGUI.this.results.getModel());
					GlobalData.searchVocable = null;
					SearchGUI.this.edit.setEnabled(false); // no more items to be selected, i.e. no edit op.
					break;
				default: break;
				}
				
			}
		};
		for (JButton b : new JButton[]{clear, search, edit, close}){
			b.setBorder(BorderFactory.createRaisedBevelBorder());
			b.setPreferredSize(new Dimension(80,100));
			b.setMaximumSize(new Dimension(80,100));
			b.setVerticalTextPosition(AbstractButton.TOP);
		    b.setHorizontalTextPosition(AbstractButton.CENTER);
		    b.addActionListener(buttonLis);	
		    p.add(b);
		}
		//p.setPreferredSize(new Dimension(180,100));
		p.setBackground(this.bg);
		return p;
	}
	
	private void clearResultList(DefaultListModel<Vocable> model){
		if (model.getSize() != 0){
			model.removeAllElements();
		}
		GlobalData.searchVocable = null;
		this.edit.setEnabled(false);
	}
	
	/* returns an array of two Strings: the first elements contains
	 * the entry in the field representing the search item specified
	 * for the source language, the second element the search item specified
	 * in the target language.
	 * In most cases one of the two elements is an empty String
	 */
	public String[] getSearchEntries(){
		String[] entries = new String[2];
		entries[0] = this.word1.getText().trim();
		entries[1] = this.word2.getText().trim();
		
		return entries;
	}
	
	/* sets the list of matching items (vocables) in the Search Results panel 
	 */
	public void setResultList(Vector<Vocable> hits){
		DefaultListModel<Vocable> model = (DefaultListModel<Vocable>)this.results.getModel();
		this.clearResultList(model);
		for (Vocable v : hits) {
			model.addElement(v);
		}
		if (hits.size() > 0){
			this.results.setSelectedIndex(0);
			this.edit.setEnabled(true);
		} else {
			GlobalData.searchVocable = null;
		}
	}
	
	/* returns selected value (vocable) in result/search list
	 * if list is empty or no vocable selected, null is returned
	 */
	public Vocable getSelectedVocable(){
		return this.results.getSelectedValue();
	}
}
