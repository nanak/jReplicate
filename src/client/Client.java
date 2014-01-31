package client;

import java.net.Socket;

public class Client {

	private Socket socket;
	CmdController controller;
	
	public static void main(String args[]) {
		
	}
	
	public Client() {
		this.controller = new CmdController();
		this.socket = new Socket();
	}
	
}
