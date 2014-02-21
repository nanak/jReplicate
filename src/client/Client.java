package client;

/**
 * @author Viktor Kiss
 * Zweck: Diese Klasse ist der CLient und initalisiert einen Commandcontroller welcher wiederum <br>
 * 		  einen Socket Intialisiert einen Thread des Types Receiving Thread.
 * <br>	  Diese Klasse nimmt die Kommandos entgegen ueber das User-Terminal und leitet diese an die
 * <br>	  "checkIpnut"-Methode der Klasse weiter und gibt die Ausgabe ueber das Terminal an den User weiter.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	// Attribute
	private CmdController cmd; // Objekt des Command-Controllers
	private String name; // Name des Clients bzw. der Zweigstelle (zB "Wien", "Berlin") ...
	private int port; // Port des Servers auf den sich der Client connected
	private int reveivingPort; // Dies ist der Port auf den der Client horcht (wird vom Server benoetigt)
	private String ip; // dies ist die IP des Replikationsservers

	/**
	 * Der Konstruktor der Klasse <br>
	 * Uebergeben wird lediglich der Name des Clients bzw. der Filiale. Die restlichen Attribute bekommen default Werte zugewiesen.<br>
	 * Diese werden im Zuge der Main-Methode mit den uebergebenen Arguments ueberschrieben
	 * @param name der Name des Clients
	 */
	public Client(String name) {
		this.name = name;
		this.port = 50000;
		this.reveivingPort = 50001;
		this.ip = "127.0.0.1";
	}

	/**
	 * Dies ist die Main-Methode der Klasse.
	 * @param args die Parameter in folgender Reihenfolge: <br>
	 * --> Name des Clients <br>
	 * --> IP des Replikationsservers <br>
	 * --> Port des Replikationsservers <br> 
	 * --> Listening-Port des Clients <br>
	 */
	public static void main(String args[]) {
		Client c = null;
		try {
			c = new Client(args[0]); // Initialisierung des Clients
		} catch (ArrayIndexOutOfBoundsException e) { // Abfangen der Exception falls eine Arguments uebergeben werden
			System.out
					.println("The was an error with the parameters! Please check your input and start the program again!");
			System.exit(0);
		}

		try {
			c.port = Integer.parseInt(args[2]); // Zuweisen des Replikationsports aus den Arguments
			c.reveivingPort = Integer.parseInt(args[3]); // Zuweisen des Listeningports aus den Arguments
			c.cmd = new CmdController(c); // Initialisierung des CmdControllers
		} catch (NumberFormatException | IOException e) {
			if (e.getClass().equals(NumberFormatException.class)) {
				System.out
						.println("WARNING: The second / third argument was invailed! \n Please restart the programm!"); // Falls statt korrekten Ports irgendein Schwachsinn eingegeben wird
			} else if (e.getClass().equals(IOException.class)) {
				System.out.println("ERROR");
			}
		}
		

		try {
			c.cmd.setSocket(c.ip, c.port); // Initialisierung des Sockets des CmdController-Objekts
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Scanner sc = new Scanner(new InputStreamReader(System.in)); // Initialisierung eines Scanners (fuer das EInlesen des User-Ipnuts)
		boolean goOn = true; // "Schalter fuer die Schleife
		String line = null;
		while (goOn) {
			line = sc.nextLine(); // Einlesen der Eingabe 
			if (line.toUpperCase().equals("EXIT")) { // mit "EXIT" wird das Programm beendet
				goOn = false;
				System.out.println("Thread will be terminated!"); // Information an den CLient, dass das Programm beendet wird
			} else if (c.cmd.checkInput(line)) { // Eingabe wird an Check-Input vom Controller weite geleitet
				System.out.println("The command was executed successfully!");
			}
		}
	}

	public String getName() {
		return this.getName();
	}

	public String getIP() {
		return this.ip;
	}

	public int getPort() {
		return this.port;
	}

	public int getReiceivingPort() {
		return this.reveivingPort;
	}
}
