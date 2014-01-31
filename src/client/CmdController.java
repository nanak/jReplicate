package client;

import java.text.SimpleDateFormat;
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

		return true;
	}
	
	public boolean create_selectQuery(String s) {
		
		return true;
	}
	
	public boolean create_deleteQuery(String s) {
		
		return true;
	}
}
