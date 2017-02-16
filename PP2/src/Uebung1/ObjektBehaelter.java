package Uebung1;
public class ObjektBehaelter<T> {
    private static int zaehler = 1;
    private final int key;
    private T inhalt;

    public ObjektBehaelter(T obj){
	inhalt = obj;
	key = zaehler++;
    }

    public T getInhalt(){
	return inhalt;
    }

    public void setInhalt(T obj){
	inhalt = obj;
    }

    public int getKey(){
	return this.key;
    }

    public String toString(){
	return String.valueOf("(" + key + ", " + inhalt.toString() + ")");
    }

}
