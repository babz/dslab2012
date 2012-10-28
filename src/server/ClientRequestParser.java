package server;

import org.apache.log4j.Logger;

/**
 * explicitely assigned to ONE client, because running in a seperate thread
 * @see ClientListener
 * @author Babz
 *
 */
public class ClientRequestParser {

	private static final Logger LOG = Logger.getLogger(ClientRequestParser.class);
	private UserManagement userMgmt = UserManagement.getInstance();

	private String currUserName = null;

	public String getResponse(String clientRequest) {
		String response = "";
		String[] request = clientRequest.split("\\s");

		//args: [0] = command, [1] = username, [2] = uspport
		if(clientRequest.startsWith("!login")) {
			int expectedNoOfArgs = 3;
			if(currUserName != null) {
				LOG.info("another user already logged in");
				response = "Log out first";
			} else if(request.length != expectedNoOfArgs) {
				LOG.info("wrong no of args");
				response = "expected no of args: " + expectedNoOfArgs;
			} else {
				String userName = request[1];
				String udpPort = request[2];
				boolean loginSuccessful = userMgmt.login(userName, udpPort);
				if(!loginSuccessful) {
					response = "Already logged in";
				} else {
					currUserName = userName;
					response = "Successfully logged in as " + currUserName;
				}
				LOG.info("client request 'login' finished");
			}
		} 
		//args: [0] = command
		else if(clientRequest.startsWith("!logout")) {
			int expectedNoOfArgs = 1;
			if(request.length != expectedNoOfArgs) {
				LOG.info("wrong no of args");
				response = "expected no of args: " + expectedNoOfArgs;
			} else {

				if(currUserName == null) {
					response = "You have to log in first";
				} else {
					boolean logoutSuccessful = userMgmt.logout(currUserName);
					if(!logoutSuccessful) {
						response = "Log in first";
					} else {
						response = "Successfully logged out as " + currUserName;
						currUserName = null;
					}
				}
				LOG.info("client request 'logout' finished");
			}
		} 
		//args: [0] = command; allowed for anonymus users
		else if(clientRequest.startsWith("!list")) {
			response = "TODO implement";
		}
		//args: [0] = cmd, [1] = duration, [2] = description
		else if(clientRequest.startsWith("!create")) {
			response = "TODO implement";
		}
		//args: [0] = cmd, [1] = auction-id, [2] = amount
		else if(clientRequest.startsWith("!bid")) {
			response = "TODO implement";
		}
		//args: [0] = cmd
		else if(clientRequest.startsWith("!end")) {
			response = "TODO implement";
		}
		else {
			response = "request couldn't be identified";
		}
		return response;
	}

}
