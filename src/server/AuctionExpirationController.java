package server;

import java.util.TimerTask;

public class AuctionExpirationController extends TimerTask {

	@Override
	public void run() {
		AuctionManagement.getInstance().checkExpiredAuctions();
	}

}
