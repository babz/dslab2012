package server;

public class User {

	private String name;
	private int udpPort;
	private boolean loggedIn;
	
	public User(String userName, String userUdpPort) {
		name = userName;
		udpPort = Integer.parseInt(userUdpPort);
		loggedIn = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUdpPort() {
		return udpPort;
	}

	public void setUdpPort(int udpPort) {
		this.udpPort = udpPort;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void logIn() {
		loggedIn = true;
	}

	public void logOut() {
		loggedIn = false;
	}

}