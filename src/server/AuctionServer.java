package server;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Manage auctions created by clients
 * @author Babz
 *
 */
public class AuctionServer {

	private static final Logger LOG = Logger.getLogger(AuctionServer.class);
	private static final int NO_OF_ARGS = 1;

	/**
	 * Starts the server with tcp port
	 * @param args tcp port
	 */
	public static void main(String[] args) {

		//logger
		BasicConfigurator.configure();

		AuctionServer server = new AuctionServer();

		if(args.length != NO_OF_ARGS) {
			System.out.println("supposed no of args:" + NO_OF_ARGS);
			return;
		}
		//TODO check args type

		int tcpPort = Integer.parseInt(args[0]);
		LOG.info("ready to go");

		new ServerTcpSocket(tcpPort);
	}
}




