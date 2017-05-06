package objects;

import java.awt.Graphics2D;

import game.GameFrame;
import game.GameSettings;
import game.GameState;

public class Piece extends GameObjects{

	public Piece(Shape s, GameFrame f){
		super(s,f);
		this.coords = this.pieceShape.getShapeArray();
	}
	
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
			    	 if(this.coords[j][i]==1){
			    		 graphics2d.drawImage(io.ImageLoader.get(this.coords[j][i]), pos[0]+i*GameSettings.stoneSize, pos[1]+j*GameSettings.stoneSize, GameSettings.stoneSize, GameSettings.stoneSize, null, null);
			    	 }
			     }
			}
		}
		
	}

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
		gf.getGameState().addObjToVector(this);
		gf.getGameState().setCurrent(null);
	}
	// Vererbung von GameObjects noch zu implementierenden Methoden
	//TODO

	
}
