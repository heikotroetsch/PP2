package Uebung3;

import java.util.Vector;

public class Verszinsung {
	public static void verzinsen(Vector<Double> doubles, double  z) throws AmountException{
		for(double d: doubles){
			System.out.print(d +" verzinst = ");
			d = d*(1+z);
			System.out.println(d);
			
			if(d>1000000){
				throw new AmountException(d+"is over 1.000.000");
			}
		}
	}
	
	public static void main(String[]args){
		Vector<Double> doubles = new Vector<Double>();
		for(int i = 0;i<15; i++){
			doubles.add((double)((int)(Math.random()*124567)));
		}
		try {
			Verszinsung.verzinsen(doubles, 0.053);
		} catch (AmountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
