package game;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;

import javax.swing.*;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	/** Darstellungsflaeche fuer das eigentliche Spielgeschehen */
	protected GamePanel gamePanel;

	/** Darstellungsflaeche der naechsten Steine */
	protected PreviewPanel previewPanel;

	/** Start- und Restart-Button */
	private JButton startButton;

	/** Pause- und Fortsetzen-Button */
	private JButton stopButton;

	private JButton gridButton;
	/** Anzeige der bisherigen geloeschten Lines */
	private JLabel lines;

	/** Anzeige des aktuellen Levels */
	private JLabel level;

	/** Anzeige des bisherigen Scores */
	private JLabel score;

	/** Tetris-Schriftart fuer die Label */
	private Font font;

	/** Speicherung aller spielrelevanten Daten */
	private GameState gs;

	private Container c;

	/** Thread, der Informationen updatet */
	private GameFrameUpdater gameUpdater;

	/** Thread, der in Dauerschleife die Musik abspielt */
	private SingMusikThread smthread;

	public GameFrame(GameState gs) {
		this.setTitle("Tetris");
		this.gs = gs;
		c = getContentPane();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setBounds(100, 100, GameSettings.gameFrameWidth, GameSettings.gameFrameHeight);
		c.setLayout(null);
		c.setBackground(new Color(77, 109, 132));
		createComponents(c);

		/** Positioniere Fenster in der Mitte des Bildschirms */
		final Dimension dimension = this.getToolkit().getScreenSize();
		this.setLocation((int) ((dimension.getWidth() - this.getWidth()) / 2),
				(int) ((dimension.getHeight() - this.getHeight()) / 2));

		this.addKeyBindings(this.gamePanel);
		
		/*  Starten der Threads */
			//TODO
	}

	private void createComponents(Container c) {

		this.createFixedComponents();
		float size = GameSettings.fontSize;
		int space = GameSettings.stoneSize;
		gamePanel = new GamePanel();
		previewPanel = new PreviewPanel();

		level = new JLabel("1");
		level.setHorizontalAlignment(SwingConstants.CENTER);
		level.setForeground(Color.LIGHT_GRAY);
		level.setFont(font.deriveFont(size));
		level.setBounds(GameSettings.textPositionWidth + space * 2, GameSettings.textPositionHeight + space * 1,
				(int) (size * 6), (int) size);

		score = new JLabel("0");
		score.setHorizontalAlignment(SwingConstants.CENTER);
		score.setForeground(Color.LIGHT_GRAY);
		score.setFont(font.deriveFont(size));
		score.setBounds(GameSettings.textPositionWidth + space * 2, GameSettings.textPositionHeight + space * 5,
				(int) (size * 6), (int) size);

		lines = new JLabel("0");
		lines.setHorizontalAlignment(SwingConstants.CENTER);
		lines.setForeground(Color.LIGHT_GRAY);
		lines.setFont(font.deriveFont(size));
		lines.setBounds(GameSettings.textPositionWidth + space * 2, GameSettings.textPositionHeight + space * 3,
				(int) (size * 6), (int) size);

		startButton = new JButton("Start");
		startButton.setBackground(Color.LIGHT_GRAY);
		startButton.setBounds((int) (GameSettings.textPositionWidth + space * (int) (GameSettings.previewWidth / 4)),
				GameSettings.textPositionHeight + (int) (space * 6.5), space * 3, space);
		startButton.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.LIGHT_GRAY));

		stopButton = new JButton("Stop");
		stopButton.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.LIGHT_GRAY));
		stopButton.setBackground(Color.LIGHT_GRAY);
		stopButton.setBounds((int) (GameSettings.textPositionWidth + space * (int) (GameSettings.previewWidth / 4)),
				GameSettings.textPositionHeight + (int) (space * 8), space * 3, space);

		gridButton = new JButton("Grid Off");
		gridButton.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.LIGHT_GRAY));
		gridButton.setBackground(Color.LIGHT_GRAY);
		gridButton.setBounds(GameSettings.spaceLeft - 5,
				(int) (GameSettings.gameFrameHeight - GameSettings.spaceDown + (space / 4)), (int) (space * 2.5),
				space / 2);
		getContentPane().add(gridButton);

		ButtonListener bL = new ButtonListener();
		startButton.addActionListener(bL);
		stopButton.addActionListener(bL);
		gridButton.addActionListener(bL);

		c.add(gamePanel);
		c.add(previewPanel);
		c.add(level);
		c.add(score);
		c.add(lines);
		c.add(startButton);
		c.add(stopButton);
		createBorders();

	}

	private void createBorders() {
		JPanel border1 = new JPanel();
		border1.setBounds(GameSettings.spaceLeft - 5, GameSettings.spaceUp - 5, GameSettings.gamePanelWidth + 10,
				GameSettings.gamePanelHeight + 10);
		border1.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(border1);

		JPanel border2 = new JPanel();
		border2.setBounds(GameSettings.previewPositionWidth - 5,
				(int) (GameSettings.spaceUp + GameSettings.fontSize) - 5, GameSettings.previewPanelWidth + 10,
				GameSettings.previewPanelHeight + 10);
		border2.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(border2);

	}

	private void createFixedComponents() {
		

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("resources" + FileSystems.getDefault().getSeparator() + "ericssonga-628.TTF"));

		} catch (FontFormatException | IOException e) {
			font = new Font("Arial", Font.BOLD, 12);
		}

		float size = GameSettings.fontSize;
		int space = GameSettings.stoneSize;
		JLabel labellevel = new JLabel("LEVEL");
		labellevel.setFont(font.deriveFont(size));
		labellevel.setBounds(GameSettings.textPositionWidth + 10, GameSettings.textPositionHeight + space * 1,
				(int) (size * 5), (int) size);
		c.add(labellevel);

		JLabel labellines = new JLabel("LINES");
		labellines.setFont(font.deriveFont(size));
		labellines.setBounds(GameSettings.textPositionWidth + 10, GameSettings.textPositionHeight + space * 3,
				(int) (size * 5), (int) size);
		c.add(labellines);

		JLabel labelpoints = new JLabel("SCORE");
		labelpoints.setFont(font.deriveFont(size));
		labelpoints.setBounds(GameSettings.textPositionWidth + 10, GameSettings.textPositionHeight + space * 5,
				(int) (size * 5), (int) size);
		c.add(labelpoints);
		JLabel labelPreview = new JLabel("NEXT");
		labelPreview.setFont(font.deriveFont((float) (size * 1.5)));
		labelPreview.setBounds(GameSettings.textPositionWidth, GameSettings.spaceUp - 10, (int) (size * 6),
				(int) (size * 1.5));
		c.add(labelPreview);

		JLabel labelTetris = new JLabel("Tetris");
		labelTetris.setFont(font.deriveFont((float) (size * 2.5)));

		labelTetris.setBounds(GameSettings.textPositionWidth - 5,
				(int) (GameSettings.gameFrameHeight - GameSettings.spaceDown - size * 1.5), (int) (size * 10),
				(int) (size * 2.5));
		c.add(labelTetris);
	}

	/**
	 * Generiert Frame mit GameOver-Meldung und erreichtem Punktestand
	 */
	public void createGameOverFrame(int score) {
		new GameOverFrame(score);
	}

	public int getPanelHeight() {
		return this.gamePanel.getHeight();
	}

	public int getPanelWidth() {
		return this.gamePanel.getWidth();
	}

	public GamePanel getGamePanel() {
		return this.gamePanel;
	}

	public SingMusikThread getMusikThread() {
		return this.smthread;
	}
	

	private class GameOverFrame extends JFrame {
		private static final long serialVersionUID = 1L;

		/**
		 * Generiert Frame mit GameOver-Meldung und erreichtem Punktestand
		 * 
		 * @param score
		 *            Endpunktestand nach verlorenem Spiel
		 */
		public GameOverFrame(int score) {

			this.setTitle("Game Over!");
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			final Dimension dimension = this.getToolkit().getScreenSize();

			this.setResizable(true);
			getContentPane().setLayout(null);

			Icon img = new ImageIcon("resources/tetris2.jpg");

			JLabel scoreLabel = new JLabel("Sie haben " + score + " Punkte");
			JLabel secondLabel = new JLabel("erreicht!");
			scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
			scoreLabel.setFont(font.deriveFont(GameSettings.fontSize));
			secondLabel.setHorizontalAlignment(SwingConstants.CENTER);
			secondLabel.setFont(font.deriveFont(GameSettings.fontSize));
			scoreLabel.setBounds(0, 0, 404, 126);
			secondLabel.setBounds(0,40,404,126);
			getContentPane().add(scoreLabel);
			getContentPane().add(secondLabel);
			

			JButton okayButton = new JButton("Okay");
			okayButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					GameOverFrame.this.dispose();
				}
			});

			okayButton.setBounds(136, 140, 128, 50);
			getContentPane().add(okayButton);

			JLabel image = new JLabel(img);
			image.setBounds(0, 0, 404, 215);
			getContentPane().add(image);
			this.setSize(426, 271);
			this.setLocation((int) ((dimension.getWidth() - this.getWidth()) / 2),
					(int) ((dimension.getHeight() - this.getHeight()) / 2));

			this.setVisible(true);

		}

	}
	
	public PreviewPanel getPreviewPanel() {
		return previewPanel;
	}

	/**
	 * Class GamePanel: Dies ist die Anzeigefl�che f�r das Spielgeschehen.
	 * Intern wird die Spielfl�che als Array dargestellt, um die einzelnen
	 * Reihen darstellen zu k�nnen.
	 **/

	public class GamePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public int[][] gamePanelArray = new int[GameSettings.gameHeight][GameSettings.gameWidth];

		/** Breite des GamePanels */
		private int width = GameSettings.gamePanelWidth;

		/** Hoehe des GamePanels */
		private int height = GameSettings.gamePanelHeight;

		public GamePanel() {
			super();
			this.setBounds(GameSettings.spaceLeft, GameSettings.spaceUp, width, height);
		}

		/**
		 * Zeichnet das GamePanel.
		 */
		public synchronized void paint(Graphics g) {

			Graphics2D graphics2D = (Graphics2D) g;
			graphics2D.setColor(GameSettings.gamePanelBackgroundColor);
			graphics2D.fillRect(0, 0, width, height);

			/**
			 * Zeichnet das Gitternetz auf der Spielflaeche, wenn erwuenscht
			 */
			if (gs.isGridOn()) {
				drawGrid(graphics2D);
			}
			/** Zeichnet aktuellen Stein */
				//TODO

			/** Zeichnet die makierten Stein-Teile im Array */
				//TODO
		}

		private void drawGrid(Graphics2D graphics2D) {

			graphics2D.setColor(Color.lightGray);
			for (int i = 1; i < GameSettings.gameWidth; i++) {
				graphics2D.drawLine(i * GameSettings.stoneSize, 0, i * GameSettings.stoneSize,
						GameSettings.stoneSize * GameSettings.gameHeight);
			}
			for (int i = 1; i < GameSettings.gameHeight; i++) {
				graphics2D.drawLine(0, i * GameSettings.stoneSize, GameSettings.gameWidth * GameSettings.stoneSize,
						i * GameSettings.stoneSize);
			}
		}

		public int getHeight() {
			return this.height;
		}

		public int getWidth() {
			return this.width;
		}
	}

	/**
	 * Class GamePanel Dies ist die Anzeigefl�che f�r den n�chsten Spielstein.
	 **/

	class PreviewPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		/** Breite des GamePanels */
		private int width = GameSettings.previewPanelWidth;
		/** Hoehe des GamePanels */
		private int height = GameSettings.previewPanelHeight;

		public PreviewPanel() {
			super();
			this.setBounds(GameSettings.previewPositionWidth, (int) (GameSettings.spaceUp + GameSettings.fontSize),
					width, height);
		}

		/**
		 * Zeichnet PreviewPanel.
		 *
		 * @param g
		 *            Graphics
		 */
		public void paint(Graphics g) {

			Graphics2D graphics2D = (Graphics2D) g;
			graphics2D.setColor(GameSettings.gamePanelBackgroundColor);
			graphics2D.fillRect(0, 0, width, height);

			//TODO

		}

	}


	private void addKeyBindings(JComponent jc) {
		//Beispiel
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false),
				"right");
		jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_KP_RIGHT, 0, false),
				"right");
		jc.getActionMap().put("right", new ActionOnKeyEvent("right"));
		
		//TODO

	}

	private class ActionOnKeyEvent extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private String step;

		public ActionOnKeyEvent(String actionStr) {
			step = actionStr;
		}

		@Override
		public void actionPerformed(ActionEvent ae) {
			//TODO
		}
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO
		}
	}

	

	private class GameFrameUpdater extends Thread {
		/**
		 * Run-Methode soll Informationsanzeigen der GUI (lines, level,
		 * score) in kurzen Abstaenden mit den Daten aus der Klasse gameState
		 * aktualisieren.
		 */
		public void run() {
			//TODO
		}
	}

	private class SingMusikThread extends Thread {
		public void run() {
			//TODO
		}
	}
}
