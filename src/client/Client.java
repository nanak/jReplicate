package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	private CmdController cmd;
	private String name;
	private int port;
	private int reveivingPort;
	private String ip;

	public Client(String name) {
		this.name = name;
		this.port = 50000;
		this.reveivingPort = 50001;
		this.ip = "127.0.0.1";
	}

	public static void main(String args[]) {
		Client c = null;
		try {
			c = new Client(args[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out
					.println("The was an error with the parameters! Please check your input and start the program again!");
			System.exit(0);
		}

		try {
			c.port = Integer.parseInt(args[2]);
			c.reveivingPort = Integer.parseInt(args[3]);
			c.cmd = new CmdController(c);
			c.cmd.setSocket(c.ip, c.port);
		} catch (NumberFormatException | IOException e) {
			if (e.getClass().equals(NumberFormatException.class)) {
				System.out
						.println("WARNING: The second / third argument was invailed! \n Please restart the programm!");
			} else if (e.getClass().equals(IOException.class)) {
				System.out.println("ERROR");
			}
		}

		Scanner sc = new Scanner(new InputStreamReader(System.in));
		boolean goOn = true;
		String line = null;
		while (goOn) {
			line = sc.nextLine();
			if (line.toUpperCase().equals("EXIT")) {
				goOn = false;
				System.out.println("Thread will be terminated!");
			} else if (c.cmd.checkInput(line)) {
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
