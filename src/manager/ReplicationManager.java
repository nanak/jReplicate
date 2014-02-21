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

	/**
	 * default constructor
	 */
	public ReplicationManager() {
		this.inboundPort = 50000;
		this.outboundAddress1 = "127.0.0.1";
		this.outboundPort1 = 50001;
		this.outboundAddress2 = "127.0.0.1";
		this.outboundPort2 = 50002;

	}

	/**
	 * consturctor
	 * @param inboundPort port to listen to
	 * @param outboundAddress1 address of client 1
	 * @param outboundPort1 port of client 1
	 * @param outboundAddress2 address of client 2
	 * @param outboundPort2 port of client 2
	 */
	public ReplicationManager(int inboundPort, String outboundAddress1,
			int outboundPort1, String outboundAddress2, int outboundPort2) {
		this.inboundPort = inboundPort;
		this.outboundAddress1 = outboundAddress1;
		this.outboundPort1 = outboundPort1;
		this.outboundAddress2 = outboundAddress2;
		this.outboundPort2 = outboundPort2;
	}

	/**
	 * Main method
	 * @param args command line arguments
	 */
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

	/**
	 * gets the input string
	 * @return the input string
	 */
	public String getInString() {
		return inString;
	}

	/**
	 * set the input string
	 * @param inString the input string to set
	 */
	public void setInString(String inString) {
		this.inString = inString;
	}

	/**
	 * get the output string
	 * @return the output string
	 */
	public String getOutString() {
		return outString;
	}

	/**
	 * set the output string
	 * @param outString the output string
	 */
	public void setOutString(String outString) {
		this.outString = outString;
	}

}