package Uebung3;

import java.io.*;

public class Konsoleneingabe {
	
	public String readLine(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String result = "";
		try {
			result = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public String readString(){
		return this.readLine().replaceAll(" ", "");
	}
	
	public double readDouble(){
		while(true){
			try{
				return Double.parseDouble(this.readLine());
			}catch (Exception e){
			}
		}

	}
	
	public int readInt(){
		while(true){
			try{
				return Integer.parseInt(this.readLine());
			}catch (Exception e){
			}
		}

	}
	

}
