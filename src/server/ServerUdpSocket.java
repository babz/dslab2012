package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Logger;

/**
 * informs the clients about autions
 * @author Babz
 *
 */
public class ServerUdpSocket implements Runnable {

	private static final Logger LOG = Logger.getLogger(ServerUdpSocket.class.getName());

	private DatagramSocket socket = null;
	private int udpPort;
	private String msg;
	private InetAddress ipAddress;

	public ServerUdpSocket(int clientUdpPort, InetAddress ipAddress, String message) throws SocketException {
		socket = new DatagramSocket();
		udpPort = clientUdpPort;
		msg = message;
		this.ipAddress = ipAddress;
	}

	public void run() {

		try {
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);

			// send the response to the client at "address" and "port"
			// InetAddress address = InetAddress.getByName("localhost");
			int port = udpPort;
			LOG.info("message:" + msg);
			buf = msg.getBytes();
			packet = new DatagramPacket(buf, buf.length, ipAddress, port);
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeAll();
	}

	private void closeAll() {
		socket.close();
	}


}
