package server;

import java.net.InetAddress;

public class User {

	private String name;
	private int udpPort;
	private boolean loggedIn;
	private String pendingNotifications;
	private InetAddress ipAddress;
	
	public User(String userName, String userUdpPort, InetAddress clientIp) {
		name = userName;
		udpPort = Integer.parseInt(userUdpPort);
		loggedIn = false;
		pendingNotifications = "";
		ipAddress = clientIp;
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

	public void setUdpPort(String udpPort) {
		this.udpPort = Integer.parseInt(udpPort);
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

	public void storeNotification(String msg) {
		pendingNotifications += msg + "\n";
	}
	
	public String getPendingNotifications() {
		return pendingNotifications;
	}

	public void setIpAddress(InetAddress clientIp) {
		ipAddress = clientIp;
	}
	
	public InetAddress getIpAddress() {
		return ipAddress;
	}
}
