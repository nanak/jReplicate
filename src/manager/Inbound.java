package manager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Inbound implements Runnable{
	
	private ServerSocket server;
	private Scanner scanner;
	private int port;
	private ReplicationManager replicate;

	public Inbound(){
		this.port = 50000;
	}
	
	public Inbound(int port, ReplicationManager replicate){
		this.port = port;
		this.replicate = replicate;
	}

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
	
	public void handleConnection(Socket client) throws IOException {
		scanner = new Scanner(client.getInputStream());
		replicate.setInString(scanner.nextLine());
	}

}
