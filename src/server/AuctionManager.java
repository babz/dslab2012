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

	private static final Logger log = Logger.getLogger(AuctionManager.class);
	
	private final ExecutorService threadpool = Executors.newCachedThreadPool(); 
	
	public static void main(String[] args) throws IOException {
		//logger
		BasicConfigurator.configure();
		
		AuctionManager server1 = new AuctionManager();
		server1.startServer();
	}

	public void startServer() {		
		
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(13460);
			while (true) {				
				threadpool.execute(new ClientListener(serverSocket.accept()));
				log.info("client socket accepted");
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port / accept failed");
		}
		
		try {
			serverSocket.close();
			log.info("Server socket closed.\n");
		} catch (IOException e) {
			log.error("could not close server socket");
		}
	}
	
}




