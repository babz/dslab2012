package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * informs the clients about autions
 * @author Babz
 *
 */
public class ServerUdpSocket implements Runnable {
	protected DatagramSocket socket = null;
	protected BufferedReader in = null;
	protected boolean alive = true;
	private int udpPort;
	private String msg;


	public ServerUdpSocket(int clientUdpPort, String message) throws SocketException {
		socket = new DatagramSocket();
		udpPort = clientUdpPort;
		msg = message;
	}

	public void run() {

		while (alive) {
			try {
				byte[] buf = new byte[256];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);

				// send the response to the client at "address" and "port"
				// TODO get ip address from tcp connection
				InetAddress address = socket.getLocalAddress();
				int port = udpPort;
				buf = msg.getBytes();
				packet = new DatagramPacket(buf, buf.length, address, port);
				socket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
				alive = false;
			}
		}
		closeAll();
	}

	private void closeAll() {
		socket.close();
		try {
			in.close();
		} catch (IOException e) {
			System.out.println("problems closing stream");
		}
	}


}
