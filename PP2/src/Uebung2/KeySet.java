package Uebung2;

import java.util.HashSet;
import java.text.DecimalFormat;

public class KeySet {
    private static HashSet<Integer> keys = new HashSet<Integer>();
    //private final static int maxK = 1000000; // maximum key value
    private final static int maxK = 10; // maximum key value

    // create a random int value in the interval [1,k]
    private static int randomInt() {
    	return (int)(Math.random() * maxK) + 1;
    }

    //create and return key value
    public static int createKey(){
	    int possibleKey = 0;
		if (keys.size() < 0.9 * maxK) { // set less then 90% filled
		    do {
			possibleKey = randomInt();
		    } while (!keys.add(possibleKey)); // auto boxing!
		}
		return possibleKey;
    }

   public static String formatKey(int k){
		k = k % 10000000; // 10⁸
		DecimalFormat df = new DecimalFormat("0000000");
		return df.format(k);
	    }
	
	    public static void main(String[] args){
		for (int i=1;i<=9;i++){
		    System.out.println(KeySet.formatKey(createKey()));
		}
    }
    
}
