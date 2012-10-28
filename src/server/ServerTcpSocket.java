package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

/**
 * creates server sockets
 * @author Babz
 *
 */
public class ServerTcpSocket {

	private static final Logger LOG = Logger.getLogger(ServerTcpSocket.class);
	private final ExecutorService threadpool = Executors.newCachedThreadPool();

	private ServerSocket serverSocket = null;
	private int port;

	/**
	 * opens a thread for each incoming client connection request using a threadpool;
	 * handles termination of threads and closes server socket
	 * @param tcpPort
	 */
	public ServerTcpSocket(int tcpPort) {
		port = tcpPort;

		try {
			serverSocket = new ServerSocket(port);
			while (true) {				
				threadpool.execute(new ClientListener(serverSocket.accept()));
				LOG.info("client socket accepted");
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port / accept failed");
		}

		closeAll();
	}

	private void closeAll(){
		try {
			serverSocket.close();
			threadpool.shutdown();
			LOG.info("Server socket closed and threads shut down.");
		} catch (IOException e) {
			LOG.error("could not close server socket or shutdown threadpool");
		}
	}

}
