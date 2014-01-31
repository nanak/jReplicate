package client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class handles the connection with the database
 * 
 * Parts of this code are from
 * http://www.tutorialspoint.com/sqlite/sqlite_java.htm
 * 
 * @author nanak
 * 
 */

public class DatabaseConnection {

	/**
	 * Insert into table
	 */
	public boolean insert(String statement) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();

			stmt.executeUpdate(statement);

			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			return false;
		}
		System.out.println("Records created successfully");
		return true;
	}

	/**
	 * Select Query
	 */
	public ArrayList<String> select(String statement) {
		Connection c = null;
		Statement stmt = null;
		ArrayList<String> al = new ArrayList<String>();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(statement);

			String[] cache = statement.split("\\s+");

			if (cache[3].toLowerCase().equals("customer")) {
				while (rs.next()) {
					String email = rs.getString("email");
					String password = rs.getString("password");
					al.add(email);
					al.add(password);
				}
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			return null;
		}
		System.out.println("Operation done successfully");
		return al;
	}

	/**
	 * Update statement
	 */
	public boolean update(String statement) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			stmt.executeUpdate(statement);
			c.commit();

			stmt.close();
			c.close();
		} catch (Exception e) {
			return false;
		}
		System.out.println("Operation done successfully");
		return true;
	}

	/**
	 * Delete
	 */
	public boolean delete(String statement) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			stmt.executeUpdate(statement);
			c.commit();

			stmt.close();
			c.close();
		} catch (Exception e) {
			return false;
		}
		System.out.println("Operation done successfully");
		return true;
	}
}
