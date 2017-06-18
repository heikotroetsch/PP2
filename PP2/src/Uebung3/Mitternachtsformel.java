package Uebung3;

public class Mitternachtsformel {
  public static void main (String[] args) {
    try {
      Gleichung g = new Gleichung(args[0], args[1], args[2]);
      Loesung l = g.berechneLoesung();
      System.out.println("Loesung x1: " + l.getX1() + "     " +
                         "Loesung x2: " + l.getX2());
    }catch(ArrayIndexOutOfBoundsException aiobe) {
      System.out.println("Korrekter Aufruf: java Mitternachtsformel a b c");
    }catch(NumberFormatException nfe) {
      System.out.println("Unzulaessiges Argument: " +
                         "a, b oder c kein zulaessiger double-Wert!");
    }catch(NichtquadratischException nqe) {
      System.out.println(nqe);
    }catch(UnloesbarException ue) {
      System.out.println(ue);
    }
  }
}
