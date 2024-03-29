package client;

import java.util.logging.Logger;

public class Client {

	private static final Logger LOG = Logger.getLogger(Client.class.getName());
	private static final int NO_OF_ARGS = 3;

	/**
	 * Starts the client
	 * @param args String array contains server host/ip address, tcp port and udp port
	 */
	public static void main(String[] args) {

		Client client = new Client();

		if(args.length != NO_OF_ARGS) {
			System.out.println("supposed no of args:" + NO_OF_ARGS);
			return;
		}
		//TODO check args types

		String serverHost = args[0];
		int tcpPort = Integer.parseInt(args[1]);
		int udpPort = Integer.parseInt(args[2]);
		
		new ClientTcpSocket(serverHost, tcpPort, udpPort);
	}

}

