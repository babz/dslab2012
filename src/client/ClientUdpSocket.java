package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.apache.log4j.Logger;

public class ClientUdpSocket {

	private static final Logger LOG = Logger.getLogger(ClientUdpSocket.class);
	
	private DatagramSocket socket;
	private DatagramPacket packet;
	private InetAddress address;
	
	public ClientUdpSocket(int udpPort) throws IOException {
		
		socket = new DatagramSocket();

		LOG.info("send request");
		byte[] buf = new byte[256];
		address = socket.getInetAddress();
		packet = new DatagramPacket(buf, buf.length, address, udpPort);
		socket.send(packet);

		LOG.info("get response");
		packet = new DatagramPacket(buf, buf.length);
		socket.receive(packet);

		LOG.info("display response");
		String received = new String(packet.getData(), 0, packet.getLength());
		System.out.println("Quote of the Moment: " + received);

		closeAll();
	}

	private void closeAll() {
		socket.close();
	}

}
