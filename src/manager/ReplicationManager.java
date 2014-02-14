package manager;


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
	private int outboundPort1;
	private String outboundAddress1;
	private int outboundPort2;
	private String outboundAddress2;

	public ReplicationManager() {
		this.inboundPort = 50000;
		this.outboundAddress1 = "127.0.0.1";
		this.outboundPort1 = 50001;
		this.outboundAddress2 = "127.0.0.1";
		this.outboundPort2 = 50002;

	}

	public ReplicationManager(int inboundPort, String outboundAddress1,
			int outboundPort1, String outboundAddress2, int outboundPort2) {
		this.inboundPort = inboundPort;
		this.outboundAddress1 = outboundAddress1;
		this.outboundPort1 = outboundPort1;
		this.outboundAddress2 = outboundAddress2;
		this.outboundPort2 = outboundPort2;
	}

	public static void main(String[] args) {
		ReplicationManager replicate = new ReplicationManager();
		replicate.in = new Inbound(replicate.inboundPort, replicate);
		replicate.out = new Outbound(replicate.outboundAddress1,
				replicate.outboundPort1, replicate.outboundAddress2,
				replicate.outboundPort2, replicate);
		new Thread(replicate.in).start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(replicate.out).start();
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