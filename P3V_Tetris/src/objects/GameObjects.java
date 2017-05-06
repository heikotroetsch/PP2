package objects;

import java.awt.Graphics2D;
import java.awt.Image;
import game.GameFrame;

public abstract class GameObjects {
	
	/** Speichert die Form des Spielsteins */
	protected Shape pieceShape;

	/** Speichert die Form und Farbe des Spielsteins mithilfe von Koordinaten */
	protected int coords[][];

	/** Speichert aktuelle Position: pos[0] = x-Wert, pos[1] = y-Wert; */
	protected int[] position = { 0, 0 };

	/** Speichert Bild in der entsprechenden Farbe */
	protected Image img;

	/** Speichert die Instanz des GameFrames */
	protected GameFrame gf;

	public GameObjects(){}
	
	public GameObjects(Shape shape, GameFrame gf){
		this.pieceShape = shape;
		this.gf = gf;
	}
	
	/**
	 * Zeichnet Spielstein mit gegebener Position, falls diese nicht 'null' ist.
	 * Falls die uebergebene Position 'null' ist, so soll der Stein an der eigenen Position
	 * (gespeichert als Attribut) gezeichnet werden.
	 */
	public abstract void draw(Graphics2D graphics2d, int[] pos);
	
	/** Diese Methode rotiert das Object um 90 Grad nach rechts, wenn moeglich. */
	public abstract void rotate();

	/**
	 * Diese Methode bewegt den Spielstein einen Block nach rechts, wenn
	 * moeglich.
	 * @throws MovementNotPossibleException 
	 */
	public abstract void moveRight() throws MovementNotPossibleException ;

	/**
	 * Diese Methode bewegt den Spielstein einen Block nach links, wenn moeglich.
	 * @throws MovementNotPossibleException 
	 */
	public abstract void moveLeft() throws MovementNotPossibleException;

	/**
	 * Diese Methode bewegt den Spielstein einen Block nach unten, wenn moeglich.
	 * @throws MovementNotPossibleException 
	 */
	public abstract void moveDown() throws MovementNotPossibleException;

	/**
	 * Diese Methode bewegt den Spielstein nach folgender Methode:
	 * 
	 * @param x:
	 *            x-Schritte nach rechts (bzw. links bei x < 0)
	 * @param y:
	 *            y-Schritte nach unten
	 * @param rotate:
	 *            rotiert Stein nach rechts
	 * @return
	 */
	protected abstract void move(int x, int y, boolean rotate);

	/** Diese Methode rotiert den Spielstein (in der internen Darstellung 
	 *  durch ds Attribut coords 
	 * @return 
	 * @throws MovementNotPossibleException */
	protected abstract void tryRotation() throws MovementNotPossibleException;

	/**
	 * Diese Methode fixiert den aktuellen Spielstein (indem dieser auf dem Spielbrett
	 * eingetragen wird) oder beendet das Spiel.
	 * Bei Fixierung werden, wenn moeglich Zeilen geloescht und ein neuer Stein
	 * angefordert.
	 */
	protected abstract void fixPiece();

	/** Diese Methode fuegt den Spielstein in das uebergebene Array ein. 
	 * @throws MovementNotPossibleException */
	protected void addToArray(int[][] array){
			try {
			for (int i = 0; i < coords.length; i++) {
				for (int j = 0; j < coords[0].length; j++) {
					if (coords[i][j] != 0) {
						if (array[position[0] + i][position[1] + j] == 0) {
							array[position[0] + i][position[1] + j] = coords[i][j];
						} else {
							System.out.println("Hinzufuegen nicht erfolgreich");
							return;
						}
					}
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Hinzufuegen nicht erfolgreich");
			return;
		}			
	}

	protected Image getImg() {
		return img;
	}

	public void setPosition(int x, int y) {
		setPosX(x);
		setPosY(y);
	}

	protected void setPosY(int y) {
		this.position[1] = y;

	}

	protected void setPosX(int x) {
		this.position[0] = x;

	}
	protected int[] getPosition(){
		return position;
	}

	/**
	 * Diese Methode kopiert ein 2 dimensionales Array und gibt die Kopie zurueck
	 */
	protected int[][] copyArray2D(int[][] original) {
		int[][] copy = new int[original.length][original[0].length];
		for (int i = 0; i < original.length; i++) {
			for (int j = 0; j < original[0].length; j++) {
				copy[i][j] = original[i][j];
			}
		}
		return copy;
	}

	/**
	 * Diese Methode kopiert ein 1 dimensionales Array und gibt die Kopie zurueck
	 */
	protected int[] copyArray(int[] original) {
		int[] copy = new int[original.length];
		for (int i = 0; i < original.length; i++) {
			copy[i] = original[i];
		}
		return copy;
	}
	
}
