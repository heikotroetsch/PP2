package Uebung1;
public class TeeTest {
    public static void main(String[] args){
	Tee[] teatime = new Tee[3];
	teatime[0] = Tee.SCHWARZ;
	teatime[0].setAroma(Aroma.NONE);
	teatime[1] = Tee.ROOIBOS;
	teatime[1].setAroma(Aroma.VANILLE);
	teatime[1].setZiehzeit(5);
	teatime[2] = Tee.FRUECHTE;
	teatime[2].setAroma(Aroma.KIRSCH);

	System.out.println("Morgens eine Tasse " + teatime[0]);
	System.out.println("Mittags eine Tasse " + teatime[1]);
	System.out.println("Abends eine Tasse " + teatime[2]);

	//  Vorsicht: Konstante Objekte

	Tee t1 = Tee.SCHWARZ;
	Tee t2 = Tee.SCHWARZ;
	Tee t3 = Tee.FRUECHTE;
	
	for (Tee t : new Tee[]{t1,t2,t3}){
	    System.out.println(t);
	}
	System.out.println("-------------");
	t1.setAroma(Aroma.VANILLE);

	for (Tee t : new Tee[]{t1,t2,t3}){
	    System.out.println(t);
	}
    }
}
