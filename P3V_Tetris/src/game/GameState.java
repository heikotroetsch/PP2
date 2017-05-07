package game;

import java.util.Vector;
import objects.*;
import objects.Shape;

public class GameState {

	/**
	 * Bestimmt Aktivitaet aller Thread-Objekte. Wenn 'false', ist das Spiel
	 * voruebergehend pausiert.
	 */
	private boolean gameActive;

	/**
	 * Wenn das Attribut gameOver 'true' ist, ist das Spiel vorbei. Auch hier
	 * werden alle Thread-Objekte inaktiv, koennen jedoch nur mit einem Neustart
	 * reinitialisiert werden.
	 */
	private boolean gameOver;

	/**
	 * Liste aller moeglichen Enumerations
	 */
	private Shape[] enums = null;

	/**
	 * Liste der aufeinanderfolgenden Spielsteine, die nacheinander im
	 * PreviewPanel gezeichnet werden sollen.
	 */
	private Vector<Piece> objectList = new Vector<Piece>();
	
	public Vector<Piece> nextList = new Vector<Piece>();
	
	public GameController gc;

	/**
	 * Aktueller Spielstein
	 */
	private Piece current = null;

	/**
	 * Bisheriger Punktestand, der vom Spieler erzielt wurde. Der Punktestand
	 * wird am Ende jedes Levels vom gameManagementThread erhoeht.
	 */
	private long score;

	/**
	 * Aktuelles Level. Je hoeher das Level, desto weiter fortgeschritten das
	 * Spiel und desto schneller fallen die Spielsteine runter.
	 */
	private int level;

	/**
	 * Aktuell geloeschte Reihen. Je mehr geloeschte Reihen, desto hoeher wird das
	 * Level.
	 */
	private int lines;

	/** Zeit die ein Spielstein wartet um einen Platz nach unten zu rutschen. */
	private int timeFallDown = 1000;

	/** speichert, ob das Gitter gezeichnet werden soll, oder nicht */
	private boolean gridOn = true;
	
	public GameState() {
		this.objectList = new Vector<Piece>();
		this.gameActive = false;
		this.level = 1;
		enums = Shape.values();
	}

	public void initiate() {
		this.score = 0;
		this.level = 1;
		this.lines = 0;
		objectList = new Vector<Piece>();
		GameController.getInstance();
	}

	public void addScore(long score) {
		this.score += score;
	}

	public long getScore() {
		return this.score;
	}

	public Piece getCurrent() {
		return current;
	}

	public void setCurrent(Piece current) {
		this.current = current;
	}

	public int getTimeFallDown() {
		return timeFallDown;
	}

	public void setTimeFallDown(int timeFallDown) {
		this.timeFallDown = timeFallDown;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLines() {
		return this.lines;
	}

	public void addLines(int lines) {
		this.lines += lines;
	}

	public synchronized Vector<Piece> getVector() {
		return objectList;
	}

	public Shape[] getEnumList() {
		return enums;
	}

	public synchronized void removeObjFromVector(int index) {
		objectList.remove(index);
	}

	public synchronized void addObjToVector(Piece o) {
		objectList.add(o);
	}

	public boolean getGameState() {
		return this.gameActive;
	}

	public void setGameState(boolean bo) {
		this.gameActive = bo;
	}

	public void setGameOver(boolean bo) {
		this.gameOver = bo;
	}

	public boolean getGameOver() {
		return this.gameOver;
	}

	public boolean isGridOn() {
		return gridOn;
	}

	public void setGridOn(boolean gridOn) {
		this.gridOn = gridOn;
	}
}
