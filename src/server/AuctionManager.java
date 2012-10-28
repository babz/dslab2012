package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import sun.security.krb5.internal.UDPClient;

/**
 * Manage auctions created by clients
 * @author Babz
 *
 */
public class AuctionManager {

	private static final Logger LOG = Logger.getLogger(AuctionManager.class);

	private static final int NO_OF_ARGS = 1;

	/**
	 * Starts the server with tcp port
	 * @param args tcp port
	 */
	public static void main(String[] args) {

		//logger
		BasicConfigurator.configure();

		AuctionManager server = new AuctionManager();

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




