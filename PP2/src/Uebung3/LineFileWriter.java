package Uebung3;

import java.io.*;

public class LineFileWriter {
	
	public static String readLine(){
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
	
	public static void main(String[]args){
		System.out.println("Geben sie einen Dateinamen ein.");
		String fileName = LineFileWriter.readLine();
		System.out.println("Geben sie eine Zeilenzahl ein.");
		int filerow = Integer.parseInt(LineFileWriter.readLine());
		System.out.println("Geben sie eine Zeile Text ein.");
		String text = LineFileWriter.readLine();
		
		File file = new File(fileName);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			BufferedWriter br = new BufferedWriter(new FileWriter(file));
			br.write(text);
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
