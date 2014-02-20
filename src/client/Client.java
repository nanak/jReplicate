package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

	private CmdController cmd;
	private String name;
	private int port;
	private int reveivingPort;
	private String ip;
	
	public Client(String name) {
		this.name = name;
	}
	
	public static void main(String args[]) {
		Client c = null;
		if (args.length >= 4) {
			c = new Client(args[0]);
		}
		
		
		c.port = 50000;
		c.reveivingPort = 50001;
		Socket socket = null;
		try {
			c.port = Integer.parseInt(args[2]);
			c.reveivingPort = Integer.parseInt(args[3]);
			socket = new Socket(args[1], c.port);
			c.cmd = new CmdController(c);
		} catch (NumberFormatException | IOException e) {
			if (e.getClass().equals(NumberFormatException.class)) {
				System.out.println("WARNING: The port is set to 50000 because the second argument was invailed! \n If you want to use another port please restart the programm!");
			} else if (e.getClass().equals(IOException.class)) {
				System.out.println();
			}
		}
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean goOn = true;
        String line = null;
        try {
            while( (line = br.readLine())!=null && goOn){
                System.out.println("StdIn: " + line); 
                if (line.toUpperCase().equals("EXIT")) {
                	goOn = false;
                	System.out.println("Thread will be terminated!");
                } else if (c.cmd.checkInput(line)) {
                	System.out.println("The command was executed successfully!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
