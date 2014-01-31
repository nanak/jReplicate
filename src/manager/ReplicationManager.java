package manager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class handles the replication between the clients
 * 
 * Parts of this code are from
 * http://openbook.galileocomputing.de/javainsel9/javainsel_21_007.htm
 * 
 * @author nanak
 * 
 */
public class ReplicationManager implements Runnable {
	private ArrayList<String> ip = new ArrayList<String>();
	private ServerSocket server;

	public ReplicationManager() {
		try {
			server = new ServerSocket(1234);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void handleConnection(Socket client) throws IOException {
		Scanner in = new Scanner(client.getInputStream());
		
		String input = in.nextLine();
		
	}

	@Override
	public void run() {
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
					}

			}

		}

	}
}