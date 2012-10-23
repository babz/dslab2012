package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import client.ClientConnector;

/**
 * Manage auctions created by clients
 * @author Babz
 *
 */
public class AuctionManager {

	private static final Logger log = Logger.getLogger(AuctionManager.class);

	public static void main(String[] args) throws IOException {

		BasicConfigurator.configure();

		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(13460);
		} catch (IOException e) {
			System.err.println("Could not listen on port");
		}

		Socket clientSocket = null;
		try {
			clientSocket = serverSocket.accept(); 
			log.info("client socket accepted");
		} catch (IOException e) {
			System.err.println("Accept failed.");
		}

		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(
				new InputStreamReader(
						clientSocket.getInputStream()));
		log.info("socket, pw and br created");
		
		String inputLine, outputLine;
//		KnockKnockProtocol kkp = new KnockKnockProtocol();
//
//		outputLine = kkp.processInput(null);
//		out.println(outputLine);

		int tmp = 0;
		while ((inputLine = in.readLine()) != null) {
//			outputLine = kkp.processInput(inputLine);
//			out.println(outputLine);
//			if (outputLine.equals("Bye."))
//				break;
			log.info("return answer");
			out.println("Hello babz" + ++tmp);
		}
		out.close();
		in.close();
		clientSocket.close();
		serverSocket.close();
	}
}
