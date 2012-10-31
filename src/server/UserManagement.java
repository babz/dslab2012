package server;

import java.net.InetAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages logged in users;
 * users have unique names
 * @author Babz
 *
 */
public class UserManagement {

	private static UserManagement instance;

	private UserManagement() {
	}

	public static synchronized UserManagement getInstance() {
		if(instance == null) {
			instance = new UserManagement();
		}
		return instance;
	}

	private Map<String, User> allUsers = Collections.synchronizedMap(new HashMap<String, User>());

	/**
	 * logs in the user if not already logged in
	 * @param userName
	 * @param userUdpPort
	 * @param clientIp 
	 * @return true: successfully logged in
	 */
	public boolean login(String userName, String userUdpPort, InetAddress clientIp) {
		User tmpUser = getUserByName(userName);
		if(tmpUser != null && tmpUser.isLoggedIn()) {
			return false;
		} else {
			if(tmpUser == null) {
				tmpUser = new User(userName, userUdpPort, clientIp);
				allUsers.put(userName, tmpUser);
			} else {
				tmpUser.setUdpPort(userUdpPort);
				tmpUser.setIpAddress(clientIp);
			}
			tmpUser.logIn();
			return true;
		}
	}

	/**
	 * logs out the user if not already logged out
	 * @param userName 
	 * @return true: successfully logged out
	 */
	public boolean logout(String userName) {
		User tmpUser = getUserByName(userName);
		if(!tmpUser.isLoggedIn()) {
			return false;
		} else {
			tmpUser.logOut();
			return true;
		}
	}

	public User getUserByName(String username) {
		return allUsers.get(username);
	}

}
