import java.io.*;
import java.net.*;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;


public class ClientConnector {
	
	static Logger log = Logger.getLogger(ClientConnector.class);
	
	public static void main(String[] args) throws IOException {
		
		BasicConfigurator.configure();
		
		Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			echoSocket = new Socket("stockholm.vitalab.tuwien.ac.at", 9000);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					echoSocket.getInputStream()));
			log.info("socket, pw and br created");
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host stockholm");
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for "
					+ "the connection to: stockholm.");
		}

		BufferedReader stdIn = new BufferedReader(
				new InputStreamReader(System.in));

		String userInput;
		while ((userInput = stdIn.readLine()) != null) {
			log.info("pass request to server");
			out.println(userInput);
			log.info("catch server answer:");
			System.out.println(in.readLine());
		}

		out.close();
		in.close();
		stdIn.close();
		echoSocket.close();
	}
}

