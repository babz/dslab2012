package server;

import java.util.Date;

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
	private AuctionManagement auctionMgmt = AuctionManagement.getInstance();
	
	private String currUserName = null;
	private String response = "";

	public String getResponse(String clientRequest) {
		String[] request = clientRequest.split("\\s");

		//args: [0] = command, [1] = username, [2] = uspport
		if(clientRequest.startsWith("!login")) {
			int expectedNoOfArgs = 3;
			if(currUserName != null) {
				LOG.info("another user already logged in");
				response = "Log out first";
			} else if(request.length != expectedNoOfArgs) {
				LOG.info("wrong no of args");
				response = "expected parameter: username";
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
				response = "expected parameter: none";
			} else if(currUserName == null) {
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
		//args: [0] = command; allowed for anonymus users
		else if(clientRequest.startsWith("!list")) {
			int expectedNoOfArgs = 1;
			if(request.length != expectedNoOfArgs) {
				LOG.info("wrong no of args");
				response = "expected parameter: none";
			} else {
				response = auctionMgmt.getAllActiveAuctions();
			}
			LOG.info("client request 'list' finished");
		}
		//args: [0] = cmd, [1] = duration, [2] = description
		else if(clientRequest.startsWith("!create")) {
			int minExpectedNoOfArgs = 3;
			if(request.length < minExpectedNoOfArgs) {
				LOG.info("wrong no of args");
				response = "expected parameter: duration + description";
			} else if(!isAuthorized()) {
				response = "You have to log in first to use this request";
			} else {
				int duration = Integer.parseInt(request[1]);
				String description = clientRequest.substring(request[0].length()+request[1].length()+2);
				
				int id = auctionMgmt.createAuction(currUserName, duration, description);
				Date expiration = auctionMgmt.getExpiration(id);
				response = "An auction '" + description + "' with id " + id + "has been created and will end on " + expiration;
			}
			LOG.info("client request 'create' finished");
		}
		//args: [0] = cmd, [1] = auction-id, [2] = amount
		else if(clientRequest.startsWith("!bid")) {
			int expectedNoOfArgs = 3;
			if(request.length != expectedNoOfArgs) {
				LOG.info("wrong no of args");
				response = "expected parameter: auction-id + amount";
			} else if(!isAuthorized()) {
				response = "You have to log in first to use this request";
			} else {
				response = "TODO implement";
			}
			LOG.info("client request 'bid' finished");
		}
		//args: [0] = cmd
		else if(clientRequest.startsWith("!end")) {
			int expectedNoOfArgs = 1;
			if(request.length != expectedNoOfArgs) {
				LOG.info("wrong no of args");
				response = "expected parameter: none";
			} else if(!isAuthorized()) {
				response = "You have to log in first to use this request";
			} else {
				response = "TODO implement";
			}
			LOG.info("client request 'end' finished");
		}
		else {
			response = "request couldn't be identified";
		}
		return response;
	}
	
	private boolean isAuthorized() {
		if(currUserName == null) {
			return false;
		}
		return true;
	}

}
