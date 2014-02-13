package client;

/**
 * @author Viktor Kiss
 * @date 31st of January
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CmdController {
	
	private DatabaseConnection dbConnection;
	
	public boolean checkInput(String s) {
		String[] cache = s.split(" ");
		if (cache.length >= 1) {
			if (cache[0].toUpperCase().equals("CREATE")) {
				this.create_insertQuery(s);
			} else if (cache[0].toUpperCase().equals("CHANGE")) {
				this.create_changeQuery(s);
			} else if (cache[0].toUpperCase().equals("DELETE")) {
				
			} else if (cache[0].toUpperCase().equals("SHOW")) {
				
			}
		}
		return true;
	}
	
	public int getBill_Size() {
		return dbConnection.select("SELECT * FROM bill;").size() / 4;
		
	}

	public boolean create_insertQuery(String s) {
		String[] cache = s.split("\\s+");
		if (cache.length >= 2) {
			if(cache[1].toUpperCase().equals("CUSTOMER")) {
				String query = "INSERT INTO customer (email,password) VALUES (\'" + cache[2] + "\',\'" + cache[3] + "\');"; 
				return dbConnection.insert(query);
			} else if (cache[1].toUpperCase().equals("BILL")) {
				String query = "INSERT INTO bill (id,date,relPath,email) VALUES (" + (this.getBill_Size() + 1) + ",\'" + (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime())) + "\',\'" + cache[3] + "\',\'" + cache[2] + "\'";
				return dbConnection.insert(query);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean create_changeQuery(String s) {
		String[] cache = s.split("\\s+");
		if (cache.length >= 2) {
			if(cache[1].toUpperCase().equals("CUSTOMER")) {
				String query = null;
				if (cache[4].toUpperCase().equals("EMAIL")) {
					query = "UPDATE OR ROLLBACK customer SET email = \'" + cache[5] + "\' WHERE email = \'" + cache[2] + "\';"; 
				} else if (cache[4].toUpperCase().equals("PASSWORD")) {
					query = "UPDATE OR ROLLBACK customer SET password = \'" + cache[5] + "\' WHERE email = \'" + cache[2] + "\';";
				}
				return dbConnection.insert(query);
			} else if (cache[1].toUpperCase().equals("BILL")) {
				String query = "UPDATE OR ROLLBACK bill SET relPath = \'" + cache[4] + "\' WHERE (email = \'" + cache[2] + "\') AND (relPath = \'" + cache[3] + "\');";
				return dbConnection.insert(query);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public String create_selectQuery(String s) {
		String[] cache = s.split("\\s+");
		if (cache.length >= 2) {
			if (cache[1].toUpperCase().equals("CUSTOMER")) {
				ArrayList<String> results = dbConnection.select("SELECT * FROM customer;");
				String resultToPrint = "";
				for (int i = 1; i <= results.size(); i++) {
					if (i % 2 == 1) {
						resultToPrint += "Mail-Adress: \t" + results.get(i-1) + "\n";
					} else {
						resultToPrint += "Password: \t" + results.get(i-1) + "\n \n";
					}
				}
				
				return resultToPrint;
			} else if (cache[1].toUpperCase().equals("BILLS")) {
				ArrayList<String> results = dbConnection.select("SELECT * FROM bill;");
				String resultToPrint = "";
				for (int i = 1; i <= results.size(); i++) {
					if (i % 4 == 1) {
						resultToPrint += "Bill_ID: \t" + results.get(i-1) + "\n";
					} else if (i % 4 == 2) {
						resultToPrint += "Bill_Date: \t" + results.get(i-1) + "\n";
					} else if (i % 4 == 3) {
						resultToPrint += "PDF_Path: \t" + results.get(i-1) + "\n";
					} else if (i % 4 == 0) {
						resultToPrint += "Mail of customer: \t" + results.get(i-1) + "\n \n";
					}
				}
				
				return resultToPrint;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public boolean create_deleteQuery(String s) {
		String[] cache = s.split("\\s+");
		if (cache.length >= 2) {
			if (cache[1].toUpperCase().equals("CUSTOMER")) {
				String query = "DELETE FROM customer WHERE email =\'" + cache[2].toLowerCase() + "\'";
				return dbConnection.delete(query);
			} else if (cache[1].toUpperCase().equals("BILL")) {
				String query = "DELETE FROM bill WHERE id =\'" + cache[2].toLowerCase() + "\'";
				return dbConnection.delete(query);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
