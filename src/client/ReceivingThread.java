package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

public class ReceivingThread implements Runnable{
	public Socket socket;
	public ServerSocket replicationServerSocket;
	public CmdController cmdC;
	public DatabaseConnection dbC;
	public int socketPort;
	
	public ReceivingThread(Socket s, CmdController c, DatabaseConnection db, int port) {
		this.socket = s;
		this.cmdC = c;
		this.dbC = db;
		this.socketPort = port;
	}
	
	@Override
	public void run(){
		try {
			this.replicationServerSocket = new ServerSocket(this.socketPort);
			while(cmdC.isServerRunning()) {
				Socket cacheSocket = this.replicationServerSocket.accept();
				BufferedReader br = new BufferedReader(new InputStreamReader(cacheSocket.getInputStream()));
				PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
				String command;
				boolean rValue = false;
				while((command = br.readLine()) != null) {
					rValue = this.cmdC.checkInput(command);
				}	
				if (rValue) {
					pw.print("transaction was successful @" + Calendar.getInstance() + " | " + this.cmdC.client.getName());
				} else if (!rValue) {
					pw.print("transaction was not successful @" + Calendar.getInstance() + " | " + this.cmdC.client.getName());
				}
			}
		} catch (IOException e) {
			
		}  finally {
			try {
				this.replicationServerSocket.close();
			} catch (IOException e) {
				
			}
		}
		
	}
}
