package objects;

import java.awt.Graphics2D;
import game.GameController;
import game.GameFrame;
import game.GameSettings;

/**
 * Implementiert eine konkreten Spielstein in Tetris
 * @author heikotroetsch
 *
 */
public class Piece extends GameObjects{

	/**
	 * Konstruktor, welcher die Shape und Frame übergibt sowohl wie cords initialisiert und die Position auf eine Zufällige Stelle am Bildschirmrand setzt.
	 * @param s
	 * @param f
	 */
	public Piece(Shape s, GameFrame f){
		super(s,f);
		this.coords = this.pieceShape.getShapeArray();
		this.position[1] = 0;
		this.position[0] = GameSettings.stoneSize*((int)(Math.random()*(gf.getGamePanel().gamePanelArray[0].length-(s.getShapeArray()[0].length-1))));
	}
	
	/**
	 * Malt das Piece anhand eines graphics2d Objekt in das Objekt von dem das Graphics2d objekt kommt. 
	 */
	@Override
	public void draw(Graphics2D graphics2d, int[] pos) {
		if(pos==null){
			for (int i = 0; i<this.coords.length; i++){
			     for (int j = 0; j<this.coords[i].length; j++){
			    	 if(this.coords[j][i]!=0){
			    		 graphics2d.drawImage(io.ImageLoader.get(this.coords[j][i]), this.position[0]+i*GameSettings.stoneSize, this.position[1]+j*GameSettings.stoneSize, GameSettings.stoneSize, GameSettings.stoneSize, null, null);
			    	 }
			     }
			}
		}else{
			for (int i = 0; i<this.coords.length; i++){
			     for (int j = 0; j<this.coords[i].length; j++){
			    	 if(this.coords[j][i]!=0){
			    		 graphics2d.drawImage(io.ImageLoader.get(this.coords[j][i]), pos[0]+i*GameSettings.stoneSize, this.position[1]+pos[1]+j*GameSettings.stoneSize, GameSettings.stoneSize, GameSettings.stoneSize, null, null);
			    	 }
			     }
			}
		}
		
	}

	/**
	 * Hier wird ein stein einfach rotiert.
	 */
	@Override
	public void rotate() {
		if(this.coords!=null){
			int[][] rotateArray = new int[coords.length][coords[0].length];
			for (int i = 0; i<this.coords.length; i++){
			     for (int j = 0; j<this.coords[i].length; j++){
			    	 rotateArray[j][coords.length-1-i] = this.coords[i][j];
			     }
			}
			this.coords = rotateArray;
		}
	}

	/**
	 * Hier wird ein Stein eine Stelle nach rechts bewegt, falls dies möglich ist.
	 */
	@Override
	public void moveRight() throws MovementNotPossibleException {
		boolean isPossible = true;
		int[][] gparray = gf.getGamePanel().gamePanelArray;
		for (int i = 0; i<this.coords.length; i++){
		     for (int j = 0; j<this.coords[i].length; j++){
		    	 if(this.coords[i][j]!=0){
	    			try {
	    				if(gparray[((this.position[1])/GameSettings.stoneSize+i)][(((this.position[0])/GameSettings.stoneSize)+1+j)]!=0){
		    				 isPossible = false;
		    			}
	    			}
	    			catch(ArrayIndexOutOfBoundsException exception) {
	    			    isPossible=false;
	    			}
		    	 }
		    	 
		     }
		}
		if(isPossible){
			this.position[0] += GameSettings.stoneSize;
		}else{
			throw new MovementNotPossibleException("Moving right not possible");
		}
	}

	/**
	 * Hier wird ein Stein eine Stelle nach links bewegt, falls dies möglich ist.
	 */
	@Override
	public void moveLeft() throws MovementNotPossibleException {
		boolean isPossible = true;
		int[][] gparray = gf.getGamePanel().gamePanelArray;
		for (int i = 0; i<this.coords.length; i++){
		     for (int j = 0; j<this.coords[i].length; j++){
		    	 if(this.coords[i][j]!=0){
	    			try {
	    				if(gparray[((this.position[1])/GameSettings.stoneSize+i)][(((this.position[0])/GameSettings.stoneSize)-1+j)]!=0){
		    				 isPossible = false;
		    			}
	    			}
	    			catch(ArrayIndexOutOfBoundsException exception) {
	    			    isPossible=false;
	    			}
		    	 }
		    	 
		     }
		}
		if(isPossible){
			this.position[0] -= GameSettings.stoneSize;		
		}else{
			throw new MovementNotPossibleException("Moving Left not possible");
		}
	}
	
	/**
	 * Hier wird ein Stein eine Stelle nach unten bewegt, falls dies möglich ist.
	 */
	@Override
	public void moveDown() throws MovementNotPossibleException {
		boolean isPossible = true;
		int[][] gparray = gf.getGamePanel().gamePanelArray;
		for (int i = 0; i<this.coords.length; i++){
		     for (int j = 0; j<this.coords[i].length; j++){
		    	 if(this.coords[i][j]!=0){
	    			try {
	    				if(gparray[((this.position[1])/GameSettings.stoneSize+1+i)][(((this.position[0])/GameSettings.stoneSize)+j)]!=0){
		    				 isPossible = false;
		    			}
	    			}
	    			catch(ArrayIndexOutOfBoundsException exception) {
	    			    isPossible=false;
	    			}
		    	 }
		    	 
		     }
		}
		if(isPossible){
			this.position[1] += GameSettings.stoneSize;
		}else{
			this.fixPiece();
			throw new MovementNotPossibleException("Moving down not possible");
		}
	}
	
	
	@Override
	protected void move(int x, int y, boolean rotate) {
		this.position[0] = x*GameSettings.stoneSize;
		this.position[1] = y*GameSettings.stoneSize;
	}

	/**
	 * Hier wird geschaut, ob eine Rotation Möglich ist. Nur dann wird rotiert.
	 */
	@Override
	public void tryRotation() throws MovementNotPossibleException {
		int[][] rotateArray = null;
		if(this.coords!=null){
			rotateArray = new int[coords.length][coords[0].length];
			for (int i = 0; i<this.coords.length; i++){
			     for (int j = 0; j<this.coords[i].length; j++){
			    	 rotateArray[j][coords.length-1-i] = this.coords[i][j];
			     }
			}
			
		}
		
		boolean isPossible = true;
		int[][] gparray = gf.getGamePanel().gamePanelArray;
		for (int i = 0; i<rotateArray.length; i++){
		     for (int j = 0; j<rotateArray[i].length; j++){
		    	 if(rotateArray[i][j]!=0){
	    			try {
	    				if(gparray[((this.position[1])/GameSettings.stoneSize+i)][(((this.position[0])/GameSettings.stoneSize)+j)]!=0){
		    				 isPossible = false;
		    			}
	    			}
	    			catch(ArrayIndexOutOfBoundsException exception) {
	    			    isPossible=false;
	    			}
		    	 }
		    	 
		     }
		}
		if(isPossible){
			this.rotate();
		}else{
			throw new MovementNotPossibleException("Rotation not possible");
		}
	}

	/**
	 * Ein Piece wird hier fixiert indem es in das GamePanelArray und den ObjectVector eingetragen wird. 
	 */
	@Override
	protected void fixPiece() {
		int[][] gparray = gf.getGamePanel().gamePanelArray;
		for (int i = 0; i<this.coords.length; i++){
			 for (int j = 0; j<this.coords[i].length; j++){
				 if(this.coords[i][j]!=0){
					 gparray[((this.position[1])/GameSettings.stoneSize+i)][(((this.position[0])/GameSettings.stoneSize)+j)] = this.pieceShape.getColor();
				 }
			 }
		}
		boolean gameOver = false;
		for (int i = 0; i<gparray[0].length; i++){
			if(gparray[0][i]!=0){
				gameOver = true;
			}
		}
		if(gameOver){
			GameController.getInstance().endGame();
		}
		gf.getGameState().addObjToVector(this);
		gf.getGameState().setCurrent(null);
	}
}
