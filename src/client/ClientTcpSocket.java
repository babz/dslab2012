package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

/**
 * Creates a socket on client side and opens print writer and buffered reader on it;
 * enables client to communicate with server via tcp
 * @author Babz
 */
public class ClientTcpSocket {

	private static final Logger LOG = Logger.getLogger(ClientTcpSocket.class);

	private Socket echoSocket = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private BufferedReader stdIn = null;

	private int tcp, udp;
	private String host;

	/**
	 * creates the socket
	 * @param serverHost servers host or ip address
	 * @param tcpPort tcp port of client and server respectively
	 * @param udpPort 
	 */
	public ClientTcpSocket(String serverHost, int tcpPort, int udpPort) {

		host = serverHost;
		tcp = tcpPort;
		udp = udpPort;

		try {
			echoSocket = new Socket(host, tcp);
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

		stdIn = new BufferedReader(new InputStreamReader(System.in));

		String userInput;
		try {
			while ((userInput = stdIn.readLine()) != null) {
				LOG.info("pass user request to server");
				if(userInput.startsWith("!login")) {
					out.println(userInput + " " + udpPort);
				} else {
					out.println(userInput);
				}
				LOG.info("catch server answer: ");
				System.out.println(in.readLine());
			}
		} catch (IOException e) {
			LOG.error("couldnt receive server response");
		}

		closeAll();
	}

	private void closeAll() {
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
