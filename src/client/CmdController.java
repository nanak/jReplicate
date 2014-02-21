package client;

/**
 * @author Viktor Kiss
 * @date 31st of January
 * Zweck: Dies ist das Verbindungsstueck zwischen Replikationsserver und Client und der Datenbank. <br>
 * Ueber die checkInput Methode bekommt die Methode einen Befehl (nach einer eigens definierten Syntax), bastelt daraus die Query um dem User unter die Arme zu greifen <br>
 * und schickt diese Query an die DatabaseConnection-Klasse weiter bzw. ruft dessen Methoden auf. <br>
 * Gleichzeitig hat er einen Socket um die Query an den Replikationsserver zu schicken damit diese auf allen anderen Clients ausgefuehrt wird und ein "ReceivingThread"-Objekt um ebenfalls Querys <br>
 * vom Replikationserver zu erhalten.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

public class CmdController {
	
	// Attribute
	private DatabaseConnection dbConnection; // Objekt vom Typ "DatabaseConnection"
	public Client client; // Objekt vom aufrufenden Client
	private Socket socket; // Socket um Querys an den Replikationsserver zu senden
	private boolean isRunning; // 
	private Vector<String> queryQueue; // Hier sollten die ankommenden Querys abgespeichert werden und nacheinander abgearbeitet werden
	public ReceivingThread rThread; // der ReceivingThread der die ganze Zeit auf einen Port horcht und Querys vom Replikationsserver erwartet
	
	/**
	 * Der Konstruktor der Klasse. <br>
	 * @param c das Client-Objekt des aufrufenden Clients
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public CmdController(Client c) throws UnknownHostException, IOException {
		this.client = c;
		this.isRunning = true;
		this.queryQueue = new Vector<String>();
//		socket = new Socket(c.getIP(), c.getPort());
		this.dbConnection = new DatabaseConnection();
//		this.rThread = new ReceivingThread(socket, this, this.dbConnection, this.client.getReiceivingPort());
	}
	
	/**
	 * Diese Methode initialisiert den Socket mit dem vom Client spezifizierten Ports.
	 * <br> HINWEIS: Dies wurde auf eine Methode ausgelagert, da es aufgrund eines nicht naeher bekannten Fehlers nicht moeglich war, den Socket im Konstruktor zu initialisieren.
	 * @param ip IP des Replikationsservers
	 * @param port Port des Replikationsservers
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void setSocket(String ip, int port) throws UnknownHostException, IOException {
		this.socket = new Socket(ip, port);
	}
	
	/**
	 * Dies ist der "Command-Mapper" der Klasse. <br>
	 * Er bekommt einen String (== User-Eingabe) welche einer festgelegten Sytax folgt und "bastelt daraus" das Query und ruft die dazugehoerige Methode auf, <br>
	 * um die Query an die Datenbank weiterzuleiten. Anschliessend wird ueber den vorher initialisierten Socket die Query an den Replikationsserver weiter geleitet.
	 * @param s User-Input
	 * @return true, wenn Query erfolgreich ausgefuehrt werden konnte, ansonsten im Fehler-Fall andernfalls false.
	 */
	public boolean checkInput(String s) {
		boolean rValue = false;
		PrintWriter pw;
		try {
			pw = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		String[] cache = s.split(" "); // User-Input wird aufgesplittet
		if (cache.length >= 1) {
			// die 4 Moeglichkeiten mit denen ein Befehl anfangen kann
			if (cache[0].toUpperCase().equals("CREATE")) {
				rValue = this.create_insertQuery(s);
				if (rValue)
					pw.print(s);
			} else if (cache[0].toUpperCase().equals("CHANGE")) {
				rValue =  this.create_changeQuery(s);
				if (rValue)
					pw.print(s);
			} else if (cache[0].toUpperCase().equals("DELETE")) {
				rValue =  this.create_deleteQuery(s);
				if (rValue)
					pw.print(s);
			} else if (cache[0].toUpperCase().equals("SHOW")) {
				this.create_selectQuery(s);
			} 
		}
		return false;
	}
	
	public int getBill_Size() {
		return dbConnection.select("SELECT * FROM bill;").size() / 4;
		
	}

	/**
	 * Diese bekommt die User-Eingabe uebermittelt, wenn es sich um einen insert handeln sollte und bastelt daraus die entsprechende Query lat SQLite-Syntax.
	 * @param s User-Eingabe aus der die Informationen ausgelesen werden
	 * @return true, wenn die Query erfolgreich ausgefuerht werden konnte, ansonsten false, falls irgendein Fehler auftreten sollte.
	 */
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

	/**
	 * Diese bekommt die User-Eingabe uebermittelt, wenn es sich um einen Change bzw. eine Aenderung handeln sollte und bastelt daraus die entsprechende Query lat SQLite-Syntax.
	 * @param s User-Eingabe aus der die Informationen ausgelesen werden
	 * @return true, wenn die Query erfolgreich ausgefuerht werden konnte, ansonsten false, falls irgendein Fehler auftreten sollte.
	 */
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
	
	/**
	 * Diese bekommt die User-Eingabe uebermittelt, wenn es sich um einen Select handeln sollte und bastelt daraus die entsprechende Query lat SQLite-Syntax.
	 * @param s User-Eingabe aus der die Informationen ausgelesen werden
	 * @return true, wenn die Query erfolgreich ausgefuerht werden konnte, ansonsten false, falls irgendein Fehler auftreten sollte.
	 */
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
	
	/**
	 * Diese bekommt die User-Eingabe uebermittelt, wenn es sich um einen Delete handeln sollte und bastelt daraus die entsprechende Query lat SQLite-Syntax.
	 * @param s User-Eingabe aus der die Informationen ausgelesen werden
	 * @return true, wenn die Query erfolgreich ausgefuerht werden konnte, ansonsten false, falls irgendein Fehler auftreten sollte.
	 */
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
	
	public boolean isServerRunning() {
		return this.isRunning;
	}
}
