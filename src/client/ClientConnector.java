package client;
import java.io.*;
import java.net.*;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;


public class ClientConnector {

	private static final Logger LOG = Logger.getLogger(ClientConnector.class);
	private static final int NO_OF_ARGS = 3;

	/**
	 * Starts the client
	 * @param args String array contains server host/ip address, tcp port and udp port
	 */
	public static void main(String[] args) {

		//logger
		BasicConfigurator.configure();

		ClientConnector client = new ClientConnector();

		if(args.length != NO_OF_ARGS) {
			System.out.println("supposed no of args:" + NO_OF_ARGS);
			return;
		}
		//TODO check args types

		String serverHost = args[0];
		int tcpPort = Integer.parseInt(args[1]);
		int udpPort = Integer.parseInt(args[2]);
		client.startClient(serverHost, tcpPort, udpPort);
	}
	/**
	 * Creates a socket on client side and opens print writer and buffered reader on it;
	 * enables client to communicate with server
	 * @param serverHost servers host or ip address
	 * @param tcpPort tcp port of client and server respectively
	 * @param udpPort udp port the client is listening on
	 */
	public void startClient(String serverHost, int tcpPort, int udpPort) {
		Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			echoSocket = new Socket("localhost", 13460);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			//			Stream for listening to the servers' answer
			in = new BufferedReader(new InputStreamReader(
					echoSocket.getInputStream()));
			LOG.info("socket, pw and br created");
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host");
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for "
					+ "the connection to host.");
		}

		//		Stream to read user input
		BufferedReader stdIn = new BufferedReader(
				new InputStreamReader(System.in));

		String userInput;
		try {
			while ((userInput = stdIn.readLine()) != null) {
				LOG.info("pass user request to server");
				out.println(userInput);
				LOG.info("catch server answer:");
				System.out.println(in.readLine());
			}
		} catch (IOException e) {
			LOG.error("couldnt receive server response");
		}


		out.close();
		try {
			in.close();
			stdIn.close();
			echoSocket.close();
		} catch (IOException e) {
			LOG.error("couldnt close socket and streams properly");
		}

	}
}

