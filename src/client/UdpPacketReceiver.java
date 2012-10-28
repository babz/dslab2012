package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.apache.log4j.Logger;

public class UdpPacketReceiver implements Runnable {

	private static final Logger LOG = Logger.getLogger(UdpPacketReceiver.class);
	
	private DatagramSocket socket;
	private DatagramPacket packet;

	private byte[] buf;
	
	public UdpPacketReceiver(DatagramSocket dataSocket, byte[] buffer) {
		socket = dataSocket;
		buf = buffer;
	}

	@Override
	public void run() {
		LOG.info("get response");
		packet = new DatagramPacket(buf, buf.length);
		//blocking!
		try {
			socket.receive(packet);
		} catch (IOException e) {
			LOG.error("problems receiving udp packet from server");
		}

		LOG.info("display response");
		String received = new String(packet.getData(), 0, packet.getLength());
		System.out.println("Server response: " + received);
		
		closeAll();
	}

	private void closeAll() {
		socket.close();
	}

}
