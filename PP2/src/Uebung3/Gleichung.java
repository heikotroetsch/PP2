package Uebung3;

public class Gleichung {

	double a;
	double b;
	double c;
	
	public Gleichung(String a, String b, String c){
		this.a = Double.parseDouble(a);
		this.b = Double.parseDouble(b);
		this.c = Double.parseDouble(c);
		System.out.println(this.a+" "+this.b+" "+this.c);
	}
	
	public Loesung berechneLoesung() throws NichtquadratischException,UnloesbarException{
		double x1;
		double x2;
		
		if(this.a == 0){
			throw new NichtquadratischException("a = 0");

		}
		
		if(Math.pow(this.b, 2)-4*this.a*this.c<0){
			throw new UnloesbarException("negative Wurzel");
		}
		
		
		x1 = (-(this.b)+Math.sqrt(Math.pow(this.b, 2)-4*this.a*this.c))/(2*this.a);
		x2 = (-(this.b)-Math.sqrt(Math.pow(this.b, 2)-4*this.a*this.c))/(2*this.a);
		
		return new Loesung(x1, x2);
	}
	
}
