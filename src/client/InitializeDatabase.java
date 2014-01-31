package client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * This class initialises the database
 * 
 * Parts of this code are from
 * http://www.tutorialspoint.com/sqlite/sqlite_java.htm
 * 
 * @author nanak
 * 
 */

public class InitializeDatabase {

	/**
	 * Initialize Database
	 */
	public static void main(String[] args) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql1 = "CREATE TABLE customer "
					+ "(email      TEXT PRIMARY KEY     NOT NULL,"
					+ " password   TEXT                 NOT NULL)";
			stmt.executeUpdate(sql1);

			String sql2 = "CREATE TABLE bill "
					+ "(ID      INT PRIMARY KEY     NOT NULL,"
					+ " date    TEXT                NOT NULL,"
					+ " relPath TEXT                NOT NULL)";
			stmt.executeUpdate(sql2);
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Database was successfully created");
	}

}
