package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

public class ClientListener implements Runnable {

	private static final Logger log = Logger.getLogger(ClientListener.class);

	private PrintWriter out;
	private BufferedReader in;

	private Socket clientSocket;

	public ClientListener(Socket client) throws IOException {
		clientSocket = client;
	}

	@Override
	public void run() {
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
		} catch (IOException e1) {
			log.error("problems with streams");
		}
		log.info("socket, pw and br created");

		ClientRequestParser parser = new ClientRequestParser();		
		String inputLine;
		try {
			while ((inputLine = in.readLine()) != null) {
				log.info("get client request");
				out.println(parser.getResponse(inputLine));
			}
		} catch (IOException e) {
			log.error("problems with writing an answer to client");
		}
		closeClientSocket(clientSocket);
	}

	private void closeClientSocket(Socket client){
		out.close();
		try {
			in.close();
			client.close();
		} catch (IOException e) {
			log.error("problems closing client socket and streams");
		}
	}
}

