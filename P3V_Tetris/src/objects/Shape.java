package objects;

public enum Shape {
	I, J, L, O, S, T, Z;
	
	
	public int getColor(){
		switch (this) {
		case Z:
			return 1;

		case S:
			return 2;

		case I:
			return 3;

		case T:
			return 4;
		case O:
			return 5;

		case L:
			return 6;
		case J:
			return 7;
		default: 
			return 0;
		}
	}
	
	public int[][] getShapeArray (){
		int[][] i = new int[3][3];
		for(int[] p:i){
			for(int k: p){
				k = 0;
			}
		}
		int color = this.getColor();
		
		switch(this){
		case I:
			i = new int[4][4];
			i[0][0]= color;
			i[0][1]= color;
			i[0][2]= color;
			i[0][3]= color;
			return i;
		case J:
			i[0][2]= color;
			i[1][2]= color;
			i[2][2]= color;
			i[2][1]= color;
			return i;
		case L:
			//Laying down I
			i[0][0]= color;
			i[1][0]= color;
			i[2][0]= color;
			i[2][1]= color;
			return i;
		case O:
			i = new int[2][2];
			i[0][0]= color;
			i[0][1]= color;
			i[1][0]= color;
			i[1][1]= color;
			return i;
		case Z:
			//Laying down I
			i[0][0]= color;
			i[0][1]= color;
			i[1][1]= color;
			i[1][2]= color;
			return i;
		case S:
			//Laying down I
			i[0][1]= color;
			i[0][2]= color;
			i[1][0]= color;
			i[1][1]= color;
			return i;
		case T:
			//Laying down I
			i[1][0]= color;
			i[1][1]= color;
			i[1][2]= color;
			i[0][1]= color;
			return i;
		default: return null;
		}
		
	}
	
}