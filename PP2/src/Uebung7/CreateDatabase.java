package Uebung7;

import java.sql.*;

public class CreateDatabase {

	public static Connection connect(){
		Connection c =null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+"src/Uebung7/test.db");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
	
	public static void main(String[]args){
		Connection c = CreateDatabase.connect();
		try {
			Statement stmt = c.createStatement();
			stmt.executeUpdate("CREATE TABLE buch (buch_id INTEGER PRIMARY KEY AUTOINCREMENT, titel TEXT, autor TEXT)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
