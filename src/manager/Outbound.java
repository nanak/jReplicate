package manager;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Outbound implements Runnable {

	private Socket client = new Socket();
	private String serverAddress1;
	private int serverPort1;
	private String serverAddress2;
	private int serverPort2;
	private Scanner scanner;
	private String message;
	private ReplicationManager replicate;


	public Outbound() {
		this.serverAddress1 = "127.0.0.1";
		this.serverPort1 = 50001;
		this.serverAddress2 = "127.0.0.1";
		this.serverPort2 = 50002;
		this.message = "";
	}

	public Outbound(String serverAddress1, int serverPort1, String serverAddress2, int serverPort2, ReplicationManager replicate) {
		this.serverAddress1 = serverAddress1;
		this.serverPort1 = serverPort1;
		this.serverAddress2 = serverAddress2;
		this.serverPort2 = serverPort2;
		this.message = "";
		this.replicate = replicate;
	}

	@Override
	public void run() {
		Socket client1 = null;
		Socket client2 = null;
		try {
			client1 = new Socket(serverAddress1, serverPort1);
			client2 = new Socket(serverAddress2, serverPort2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true){
			try {
				handleConnection(client1, message);
				handleConnection(client2, message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void handleConnection(Socket client, String message) throws IOException {
		PrintWriter pw = new PrintWriter(new PrintWriter(new OutputStreamWriter(client.getOutputStream())));
		pw.print(replicate.getInString());
	}

}
