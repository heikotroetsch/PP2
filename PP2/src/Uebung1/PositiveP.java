package Uebung1;
public class PositiveP implements Predicate<Number> {
    public boolean test(Number n){
	return (n.doubleValue() > 0);
    }
}
