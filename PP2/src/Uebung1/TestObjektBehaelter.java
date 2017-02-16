package Uebung1;
public class TestObjektBehaelter {
    public static void main(String[] args) {
	ObjektBehaelter<Integer> b1 = new ObjektBehaelter<Integer> (99);
	System.out.println(b1);
	ObjektBehaelter<String> b2 = new ObjektBehaelter<String> ("blubb");
	System.out.println(b2);
	Adresse adr = new Adresse("Tom Schmidt", "Seeweg" , 5 , "99999", "Wasserhausen");
	ObjektBehaelter<Adresse> b3 = new ObjektBehaelter<Adresse> (adr);
	System.out.println(b3);

	// Feld von Objekt-Behaeltern 
	// nicht generische Variante ergibt Warnung vom Compiler 
	// beim Uebersetzen mit Option -Xlint
	ObjektBehaelter<?>[] feld =  new ObjektBehaelter[] {b1,b2,b3};
	for (int i=0;i<feld.length;i++){
	    System.out.println(feld[i]);
	}
   }
}
