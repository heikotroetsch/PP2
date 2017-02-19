package view;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JComponent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import controller.Controller;
import controller.Settings;

public class OverviewGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private Controller controller;

	public OverviewGUI(Controller c){
		this.controller = c;
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				OverviewGUI.this.controller.execCommand(Controller.CLOSE); // delegates closing of window to controller
			}
		});
		this.getContentPane().setBackground(GUISettings.color_0);
		this.createMenu();
		this.createButtonPanel();
		this.pack();
	}

	private void createMenu() {
		JMenu menu1 = new JMenu("File");
		ActionListener fileLis = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// pass command to Controller
				OverviewGUI.this.controller.execCommand(((JMenuItem)e.getSource()).getActionCommand());
			}
		};
		
		JMenuItem load = new JMenuItem("Load data");
		load.setActionCommand("load");
		load.addActionListener(fileLis);
		JMenuItem export = new JMenuItem("Export data");
		export.addActionListener(fileLis);
		export.setActionCommand("export");
		menu1.add(load);
		menu1.add(export);
		JMenu menu2 = new JMenu("Mode");
		JRadioButton b1 = new JRadioButton("CSV");
		b1.setSelected(true);
		JRadioButton b2 = new JRadioButton("XML");
		ButtonGroup bgroup = new ButtonGroup();
		bgroup.add(b1);
		bgroup.add(b2);
		ActionListener modeLis = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JRadioButton rb = (JRadioButton) e.getSource();
				Settings.mode = (rb.getText().equals("CSV")) ? Settings.CSVMode : Settings.XMLMode;
			}
			
		};
		b1.addActionListener(modeLis);
		b2.addActionListener(modeLis);
		menu2.add(b1);
		menu2.add(b2);
		JMenuBar menubar = new JMenuBar();
		menubar.setBackground(GUISettings.color_6);
		menu1.setBackground(GUISettings.color_6);
		menu2.setBackground(GUISettings.color_6);
		menubar.add(menu1);
		menubar.add(menu2);
		menubar.setBorder(BorderFactory.createLineBorder(GUISettings.color_7));
		
		this.setJMenuBar(menubar);
	}
	
	private void createButtonPanel(){
		JButton addVoc = new JButton("Add vocabulary");
		addVoc.setBackground(GUISettings.color_1);
		addVoc.setActionCommand(Controller.ADD);
		JButton search = new JButton("Find vocabulary");
		search.setBackground(GUISettings.color_2);
		search.setActionCommand(Controller.SEARCH);
		JButton learn = new JButton("Learn vocabulary");
		learn.setBackground(GUISettings.color_3);
		learn.setActionCommand(Controller.LEARN);
		Box empty = Box.createHorizontalBox();
		ImageIcon icon1 = new ImageIcon(Settings.imageSave);
		ImageIcon icon2 = new ImageIcon(Settings.imageExit);
		JButton save = new JButton("Save all",icon1);
		save.setActionCommand(Controller.SAVE);
		JButton exit = new JButton("Save & Close",icon2);
		exit.setActionCommand(Controller.EXIT);
		for (JButton b : new JButton[]{save,exit}){
			b.setVerticalTextPosition(AbstractButton.TOP);
		    b.setHorizontalTextPosition(AbstractButton.CENTER);
		}
		JComponent[] components = new JComponent[] {addVoc, search, learn, empty,save,exit};
		JPanel panel = new JPanel();
		panel.setBackground(GUISettings.color_4);

		panel.setLayout(new GridLayout(2,3,10,30));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		for (JComponent c : components){
			if (c != empty){
				c.setBorder(BorderFactory.createRaisedBevelBorder());
				c.setPreferredSize(new Dimension(180,100));
			}
			panel.add(c);
		}
		// Listener fuer Buttons
		ActionListener buttonLis = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String s = e.getActionCommand();
				OverviewGUI.this.controller.execCommand(s);
			}
		};
		for (JButton b : new JButton[]{addVoc,search,learn, save,exit}){
			b.addActionListener(buttonLis);	
		}
		this.getContentPane().add(panel, BorderLayout.CENTER);
	}

}
