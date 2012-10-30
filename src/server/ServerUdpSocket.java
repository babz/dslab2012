package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.apache.log4j.Logger;

/**
 * informs the clients about autions
 * @author Babz
 *
 */
public class ServerUdpSocket implements Runnable {

	private static final Logger LOG = Logger.getLogger(ServerUdpSocket.class);

	private DatagramSocket socket = null;
	private boolean alive;
	private int udpPort;
	private String msg;


	public ServerUdpSocket(int clientUdpPort, String message) throws SocketException {
		socket = new DatagramSocket();
		udpPort = clientUdpPort;
		msg = message;
		alive = true;
	}

	public void run() {

		//		while (alive) {
		try {
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);

			// send the response to the client at "address" and "port"
			// TODO get ip address from tcp connection
			InetAddress address = InetAddress.getByName("localhost");
			int port = udpPort;
			LOG.info("message:" + msg);
			buf = msg.getBytes();
			packet = new DatagramPacket(buf, buf.length, address, port);
			socket.send(packet);
			alive = false;
		} catch (IOException e) {
			e.printStackTrace();
			alive = false;
		}
		//		}
		closeAll();
	}

	private void closeAll() {
		socket.close();
	}


}
