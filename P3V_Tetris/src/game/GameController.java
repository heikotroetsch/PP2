package game;

import objects.*;

public class GameController {

	/** Singleton */
	private static GameController instance;

	/** Haelt Informationen ueber den Zustand des Spiels */
	private GameState gameState;

	/**
	 * Zustaendig fuer die Darstellung und Entgegennahme von
	 * Benutzerinteraktionen
	 */
	private GameFrame gameFrame;
	
	/**
	 * Enthaelt die eigentliche Logik (Kollisions-, Score- und
	 * Schadensberechnung sowie Levelverwaltung)
	 */
	private GameManagementThread gameManagementThread = new GameManagementThread();

	/**
	 * Thread, der dafuer sorgt, dass bewegliche Objekte sich bewegen.
	 */
	private MoveObjects moveObjects = new MoveObjects();
	
	/**
	 * Einfacher array der alle Shapes beinhaltet. Dies macht das wählen von zufälligen Shapes einfacher
	 */
	private Shape[] shapeArray = {Shape.I, Shape.J, Shape.L, Shape.O, Shape.S, Shape.Z, Shape.T};


	/** Enthaelt Zustand des Spiels bzgl. gameOver */
	public boolean gameOver = true;

	public static GameController getInstance() {
		if (instance == null) {
			instance = new GameController();
		}

		return instance;
	}

	/**
	 * Initialisiert das Spiel. Legt GameState sowie GameFrame an, fuellt und
	 * zeichnet die objectList mit saemtlichen Objekten, die zum Spielstart
	 * noetig sind.
	 */
	public void initiate() {

		this.gameState = new GameState();
		this.gameFrame = new GameFrame(gameState);
		
		//Hier Initialisieren wir die Liste der Shapes die als nächstes kommen.
		while(gameState.nextList.size()<10){
			gameState.nextList.add(new Piece(shapeArray[(int)(Math.random()*shapeArray.length)],gameFrame));
		}
	}

	/** Startet Spiel zum ersten Mal. */
	public void startGame() {
		this.restartGame();
		gameState.setGameState(true);
		gameState.setGameOver(false);
		this.gameOver = false;
		gameManagementThread.start();
		moveObjects.start();
	}

	/** Startet das Spiel neu. */
	public void restartGame() {
		gameState.reset();
		for(int p = 0; p<gameFrame.gamePanel.gamePanelArray.length;p++){
			for(int i = 0; i<gameFrame.gamePanel.gamePanelArray[p].length;i++){
				gameFrame.gamePanel.gamePanelArray[p][i] = 0;
			}
		}
	}

	/**
	 * Pausiert das Spiel und ruft den GameOver-Frame auf und berechnet Punkte.
	 */
	public void endGame() {
		gameState.setGameState(false);
		gameState.setGameOver(true);
		gameState.setCurrent(null);
		this.gameOver = true;
		int [][] newGameArray = gameFrame.gamePanel.gamePanelArray.clone();
		for(int p = 0; p<newGameArray.length;p++){
			for(int i = 0; i<newGameArray[p].length;i++){
				newGameArray[p][i] = (1+(int)(Math.random()*7));
				gameFrame.gamePanel.gamePanelArray = newGameArray;
			try {
				    Thread.sleep(1);          
				} catch(Exception ex) {
				}
	
			}
		}
		gameFrame.createGameOverFrame((int)gameState.getScore());
	}

	/** Holt vordersten Spielstein aus Vektor, wenn moeglich */
	public synchronized void newPiece() {
		gameState.setCurrent(gameState.nextList.remove(0));
		while(gameState.nextList.size()<10){
			gameState.nextList.add(new Piece(shapeArray[(int)(Math.random()*shapeArray.length)],gameFrame));
		}
	}

	/** versucht Reihen ab Zeile y zu loeschen, indem er sie Schritt fuer Schritt ueberprueft 
	 * 
	  * @deprecated use {@link #removeOneLine(int)} instead.  
	  */
	@Deprecated	
	public void removeLinesIfPossible(int y) {
		
	}
	
	/** Loescht Zeile y, erhoeht Lines */
	public void removeOneLine(int y) {
		while(y>0){
			for(int i = 0; i < gameFrame.gamePanel.gamePanelArray[y].length; i++){
				gameFrame.gamePanel.gamePanelArray[y][i] = gameFrame.gamePanel.gamePanelArray[y-1][i];
			}
			y--;
		}
	}

	/** ueberprueft ob Zeile y vollstaendig guefllt ist */
	public boolean checkLineOfCompleteness(int y) {
		boolean isComplete = true;
		for(int i = 0; i<gameFrame.gamePanel.gamePanelArray[y].length;i++){
			if(gameFrame.gamePanel.gamePanelArray[y][i]==0){
				isComplete = false;
			}
		}
		return isComplete;
	}

	public GameState getGameState() {
		return this.gameState;
	}

	public GameFrame getGameFrame() {
		return this.gameFrame;
	}

	/**
	 * 
	 * Bewegt in jedem Schleifendurchlauf die Objekte
	 *
	 */
	class MoveObjects extends Thread {

		public void run() {
			while(true){
				try {
					Thread.sleep(gameState.getTimeFallDown());
					if(gameState.getGameState()){
						gameState.getCurrent().moveDown();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	class GameManagementThread extends Thread {

		/**
		 * Die Run-Methode soll in jeder Iteration �berpr�fen, ob genug Reihen gel�scht
		 * wurden, um dann das Level zu erh�hen.
		 */
		public void run() {
			while(true){
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int pointCount = 0;
				if(gameState.getCurrent()==null&&gameState.getGameState()){
					for(int p = 0; p<4;p++){
						for(int i = gameFrame.gamePanel.gamePanelArray.length-1;i>=0;i--){
							if(GameController.this.checkLineOfCompleteness(i)){
								GameController.this.removeOneLine(i);
								pointCount++;
							}
						}
					}
					switch(pointCount){
					case 1:
						gameState.addScore(40);
						break;
					case 2:
						gameState.addScore(100);
						break;
					case 3:
						gameState.addScore(300);
						break;
					case 4:
						gameState.addScore(400);
						break;
					default:
						break;	
					}
					gameState.addLines(pointCount);
					if(1+(gameState.getLines()/GameSettings.linesToNextLevel)!=gameState.getLevel()){
						gameState.setLevel(1+(gameState.getLines()/GameSettings.linesToNextLevel));
						if(gameState.getTimeFallDown()>100){
							gameState.setTimeFallDown(gameState.getTimeFallDown()-100/((gameState.getLevel()%5)+1));
							System.out.println(gameState.getTimeFallDown());
						}
					}
					GameController.this.newPiece();
				}
			}
		}
	}

}