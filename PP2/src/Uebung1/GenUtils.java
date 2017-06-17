package Uebung1;

public class GenUtils {

	
	public static <T> void printArray(T[] test){
		for(T t: test){
			System.out.println(t.toString());
		}
	}
	
	public static <T> void swap(T[] test, int start, int end){
		if(start>=0&&end>=0&&start<test.length&&end<test.length){
			T help = test[start];
			test[start] = test[end];
			test[end] = help;
		}
	}
	
	public static void sort(KeyHolder[] feld){
		for (int i=1;i<feld.length;i++){
		    for (int j=1;j<feld.length;j++){
			if (feld[j-1].getKey() < feld[j].getKey()){
			    GenUtils.swap(feld,j-1,j);
				}
		    }
		}
	}
	
	public static <T> int countIf(T[] feld, Predicate<T> p){
		int i = 0;
		for(T t : feld){
			if(p.test(t)){
				i++;
			}
		}
		return i;
	}
	
}
