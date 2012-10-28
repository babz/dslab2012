package server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Manages logged in users;
 * users have unique names
 * @author Babz
 *
 */
public class UserManagement {
	
	private static final Logger LOG = Logger.getLogger(UserManagement.class);
	private static UserManagement instance;
	
	private UserManagement() {
	}
	
	public static synchronized UserManagement getInstance() {
		if(instance == null) {
			instance = new UserManagement();
		}
		return instance;
	}
	
	private Map<String, User> loggedInUsers = Collections.synchronizedMap(new HashMap<String, User>());

	/**
	 * logs in the user if not already logged in
	 * @param userName
	 * @param userUdpPort
	 * @return true: successfully logged in
	 */
	public boolean login(String userName, String userUdpPort) {
		if(loggedInUsers.containsKey(userName)) {
			return false;
		} else {
			User userNew = new User(userName, userUdpPort);
			userNew.logIn();
			loggedInUsers.put(userName, userNew);
			return true;
		}
	}

	/**
	 * logs out the user if not already logged out
	 * @param userName 
	 * @return true: successfully logged out
	 */
	public boolean logout(String userName) {
		if(!loggedInUsers.containsKey(userName)) {
			return false;
		} else {
			loggedInUsers.get(userName).logOut();
			loggedInUsers.remove(userName);
			return true;
		}
		
	}
	
}
