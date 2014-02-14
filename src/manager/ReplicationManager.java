package manager;

import java.util.ArrayList;

/**
 * This class handles the replication between the clients
 * 
 * Parts of this code are from
 * http://openbook.galileocomputing.de/javainsel9/javainsel_21_007.htm
 * 
 * @author nanak
 * 
 */
public class ReplicationManager {
	
	private Inbound in;
	private Outbound out;
	private String inString;
	private String outString;
	private int inboundPort;
	private int outboundPort;
	private String outboundAddress;
	
	public ReplicationManager() {
		this.inboundPort = 50000;
		this.outboundAddress = "127.0.0.1";
		this.outboundPort = 50001;

	}
	
	public ReplicationManager(int inboundPort, String outboundAddress, int outboundPort){
		this.inboundPort = inboundPort;
		this.outboundAddress = outboundAddress;
		this.outboundPort = outboundPort;
	}
	
	public static void main(String[] args) {
		ReplicationManager replicate = new ReplicationManager();
		replicate.in = new Inbound(replicate.inboundPort, replicate);
		replicate.out = new Outbound(replicate.outboundAddress, replicate.outboundPort, replicate);
	}

	public String getInString() {
		return inString;
	}

	public void setInString(String inString) {
		this.inString = inString;
	}

	public String getOutString() {
		return outString;
	}

	public void setOutString(String outString) {
		this.outString = outString;
	}



	
	
	
	
}