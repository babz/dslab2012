package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Manage auctions created by clients
 * @author Babz
 *
 */
public class AuctionManager {

	private static final Logger LOG = Logger.getLogger(AuctionManager.class);

	private static final int NO_OF_ARGS = 1;

	private final ExecutorService threadpool = Executors.newCachedThreadPool(); 

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

		server.startServer(tcpPort);
	}

	/**
	 * creates server sockets;
	 * opens a thread for each incoming client connection request using a threadpool;
	 * handles termination of threads and closes server socket
	 * @param tcpPort
	 */
	public void startServer(int tcpPort) {		


		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(tcpPort);
			while (true) {				
				threadpool.execute(new ClientListener(serverSocket.accept()));
				LOG.info("client socket accepted");
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port / accept failed");
		}

		try {
			serverSocket.close();
			threadpool.shutdown();
			LOG.info("Server socket closed.");
		} catch (IOException e) {
			LOG.error("could not close server socket");
		}
	}

}




