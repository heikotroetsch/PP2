package database;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

/**
 * Diese Klasse wird verwendet, um die Datenbank anzulegen und mit den Eintraegen
 * aus Wohnorte.csv zu fuellen.
 */
public class CreateDatabase extends Database {
	
	private String filename = "resources/Wohnorte.csv";
	private JProgressBar processBar;
	private JPanel c;
	private JFrame frame;
	
	/**
	 * public Constructor for creating and filling a Database with two tables in the following format:<br>
	 * <pre> 
	 * +------------+---------------------+<br>
	 * |		wohnorte            |<br>
	 * +------------+---------------------+<br>
	 * | id         | INTEGER PRIMARY KEY |<br>
	 * | plz        | TEXT NOT NULL       |<br>
	 * | ort        | TEXT NOT NULL       |<br>
	 * | bundesland | TEXT NOT NULL       |<br>
	 * +------------+---------------------+<br>
	 * <br>
	 * +----------+---------------------+<br>
	 * |    	 	kontakte          |<br>
	 * +----------+---------------------+<br>
	 * | id       | INTEGER PRIMARY KEY |<br>
	 * | name     | TEXT NOT NULL       |<br>
	 * | nummer   | TEXT                |<br>
	 * | mail     | TEXT                |<br>
	 * | strasse  | TEXT                |<br>
	 * | wohnort  | INTEGER             |<br>
	 * +----------+---------------------+<br>
	 * </pre>
	 * 
	 */
	public CreateDatabase(){
		BufferedReader br = readCSV(filename);
		//Prozesslaenge ausrechnen
		int processLength = 0;
		try {
			while ((br.readLine()) != null) {
				processLength++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		makeProgressBar(processLength);
		createTables();
		insertStaticData();
	}

/**
 * This Method creates the Tables wohnorte und KONTAKTE. Diese beiden Tables sind Leer und müssen gefüllt werden. Dies geschieht normalerweise mit der Methode insertStaticData. Diese Methode füllt den Table WOHNORTE mit Wohnorten aus einer CSV Datei.
 */
	private void createTables(){
		//Update Progressbar frame with new status lable.
		c.add(new JLabel("Database deleted"));
		try {
			//Wegen schoenheit noch ein Timeout hinzugefuegt
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		frame.validate();
		c.add(new JLabel("Creating Tables"));
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		frame.validate();
	    Statement stmt = null;
	    try {
	      stmt = connection.createStatement();
	      String sql = "CREATE TABLE wohnorte " +
	                   "(id INTEGER PRIMARY KEY   AUTOINCREMENT NOT NULL," +
	                   " plz           	TEXT   	 NOT NULL, " + 
	                   " ort            TEXT     NOT NULL, " + 
	                   " bundesland     TEXT     NOT NULL)";
	      String sql2 = "CREATE TABLE kontakte " +
                   "(id INTEGER PRIMARY KEY     AUTOINCREMENT NOT NULL," +
                   " name           	TEXT   	 NOT NULL, " + 
                   " nummer           	TEXT     		 , " + 
                   " mail         	   	TEXT        	 , " + 
                   " strasse            TEXT     	  	 , " + 
                   " wohnort            INT) " ;
	      stmt.executeUpdate(sql);
	      stmt.executeUpdate(sql2);
	      stmt.close();
	      c.add(new JLabel("Tables created successfully"));
	      TimeUnit.SECONDS.sleep(1);
	      frame.validate();
	    } catch ( Exception e ) {
			e.printStackTrace();
	    }
	}
	
	/**
	 * Diese Methode füllt den Table WOHNORTE mit Wohnorten aus einer CSV Datei.
	 */
	private void insertStaticData() {
		c.add(new JLabel("Inserting Static Data"));
		frame.validate();
		Statement stmt = null;
		
		
		try {
			stmt = connection.createStatement();
			BufferedReader br = readCSV(filename);
			br = readCSV(filename);
			String line;
			int i = 0;
			if (br != null) {
				while ((line = br.readLine()) != null) {
					String[] s = line.split("\t");
					String plz = s[0];
					String ort = s[1];
					String bundesland = s[2];
					String sql = "INSERT INTO WOHNORTE " +
			                   "VALUES (?, '"+plz+"', '"+ort+"', '"+bundesland+"');"; 
					stmt.executeUpdate(sql);
					processBar.setValue(i);
					i++;
				}
			}
			c.add(new JLabel("Static Data Inserted"));
			c.add(new JLabel("Closing Window..."));
			frame.validate();
			stmt.executeUpdate("INSERT INTO WOHNORTE VALUES (0, '0', '0', '0');");
			stmt.close();
			TimeUnit.SECONDS.sleep(3);
			frame.dispose();
		} catch (Exception e) {		
		}
	}
	
	/**
	 * Hier wird ein JFrame erstellt welcher eine Progress bar hat. Diese wird mit der Prozesslänge initialisiert.
	 * @param processLength
	 */
	private void makeProgressBar(int processLength){
		   frame = new JFrame();
		   JPanel p = new JPanel();
		   c = new JPanel();
		   processBar = new JProgressBar(0, processLength);
		   p.add(processBar);
		   p.add(new JLabel("Progress..."));
		   p.setVisible(true);
		   frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		   frame.setSize(300, 160);
		   frame.setLocationRelativeTo(frame);
		   p.add(c);
		   frame.add(p);
		   c.setLayout(new BoxLayout(c, BoxLayout.PAGE_AXIS));
		   frame.setVisible(true);

	}
	
	/**
	 * Methode um eine CSV zu lesen. 
	 * @param csvFile String des ortes der Datei
	 * @return BufferedReader
	 */
	private BufferedReader readCSV(String csvFile){
		 BufferedReader br = null;
			try {
				try {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "ISO-8859-1"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	  
	       return br; 
	}
	
}
