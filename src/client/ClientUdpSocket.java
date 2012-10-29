package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

/**
 * sends udp packet to server without content
 * @author Babz
 *
 */
public class ClientUdpSocket {

	private static final Logger LOG = Logger.getLogger(ClientUdpSocket.class);
	private final ExecutorService threadpool = Executors.newCachedThreadPool();
	
	private DatagramSocket socket;
	private DatagramPacket packet;
	private InetAddress address;
	
	public ClientUdpSocket(int udpPort) throws IOException {
		
		socket = new DatagramSocket();

		LOG.info("send request");
		byte[] buf = new byte[256];
		address = InetAddress.getLocalHost();
		packet = new DatagramPacket(buf, buf.length, address, udpPort);
		socket.send(packet);

		threadpool.execute(new UdpPacketReceiver(socket, buf));
		
		closeAll();
	}

	private void closeAll() {
		threadpool.shutdown();
		socket.close();
	}

}
