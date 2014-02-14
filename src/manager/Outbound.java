package manager;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Outbound implements Runnable {

	private Socket client = new Socket();
	private String serverAddress;
	private int serverPort;
	private Scanner scanner;
	private String message;
	private ReplicationManager replicate;


	public Outbound() {
		this.serverAddress = "127.0.0.1";
		this.serverPort = 50000;
		this.message = "";
	}

	public Outbound(String serverAddress, int serverPort, ReplicationManager replicate) {
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		this.message = "";
		this.replicate = replicate;
	}

	@Override
	public void run() {
		Socket client = null;
		try {
			client = new Socket(serverAddress, serverPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true){
			try {
				handleConnection(client, message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void handleConnection(Socket client, String message) throws IOException {
		PrintWriter pw = new PrintWriter(new PrintWriter(new OutputStreamWriter(client.getOutputStream())));
		pw.print(replicate.getOutString());
	}

}
