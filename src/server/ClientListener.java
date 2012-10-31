package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientListener implements Runnable {

	private static final Logger LOG = Logger.getLogger(ClientListener.class.getName());

	private PrintWriter out;
	private BufferedReader in;

	private Socket clientSocket;
	private InetAddress ip;

	public ClientListener(Socket client) throws IOException {
		clientSocket = client;
		ip = client.getInetAddress();
	}

	@Override
	public void run() {
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
		} catch (IOException e1) {
			LOG.warning("problems with streams");
		}
		LOG.info("socket, pw and br created");

		ClientRequestParser parser = new ClientRequestParser(ip);		
		String inputLine;
		try {
			while ((inputLine = in.readLine()) != null) {
				LOG.info("get client request");
				out.println(parser.getResponse(inputLine));
			}
		} catch (IOException e) {
			LOG.warning("problems with writing an answer to client");
		}
		closeClientSocket(clientSocket);
	}

	private void closeClientSocket(Socket client){
		out.close();
		try {
			in.close();
			client.close();
		} catch (IOException e) {
			LOG.warning("problems closing client socket and streams");
		}
	}
}

