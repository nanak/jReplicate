package manager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/** 
 * this class handles the outbound connection
 * @author Nanak Tattyrek
 *
 */
public class Inbound implements Runnable{
	
	private ServerSocket server;
	private Scanner scanner;
	private int port;
	private ReplicationManager replicate;

	/**
	 * default constructor
	 */
	public Inbound(){
		this.port = 50000;
	}
	
	/**
	 * constructor
	 * @param port the port to use
	 * @param replicate instance of replicationmanager
	 */
	public Inbound(int port, ReplicationManager replicate){
		this.port = port;
		this.replicate = replicate;
	}

	/**
	 * run method implements the logic for the client
	 */
	@Override
	public void run() {
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			Socket client = null;
			try {
				client = server.accept();
				handleConnection(client);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (client != null)
					try {
						client.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}

		}

	}
	
	/**
	 * handles the connection itself
	 * @param client socket instance
	 * @throws IOException if any in/out operation fails 
	 */
	public void handleConnection(Socket client) throws IOException {
		scanner = new Scanner(client.getInputStream());
		replicate.setInString(scanner.nextLine());
	}

}
