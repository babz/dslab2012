package server;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

/**
 * manages auctions
 * @author Babz
 *
 */
public class AuctionManagement {

	private static AuctionManagement instance;
	private static final Logger LOG = Logger.getLogger(AuctionManagement.class);
	
	private Map<Integer, Auction> allActiveAuctions = Collections.synchronizedMap(new ConcurrentHashMap<Integer, Auction>());
	private static int auctionId = 0;
	private Timer timer;
	
	private AuctionManagement() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new AuctionExpirationController(), 0, 5000);
	}
	
	public static synchronized AuctionManagement getInstance() {
		if(instance == null) { 
			instance = new AuctionManagement();
			LOG.info("instance of AuctionManagement created");
		}
		return instance;
	}

	public String getAllActiveAuctions() {
		String activeAuctions = "";
		synchronized (allActiveAuctions) {
			for(Entry<Integer, Auction> entry: allActiveAuctions.entrySet()) {
				activeAuctions += entry.getValue().toString() + "\n";
			}
		}
		return activeAuctions;
	}
	
	public synchronized void checkExpiredAuctions() {
		synchronized (allActiveAuctions) {
			for(Entry<Integer, Auction> entry: allActiveAuctions.entrySet()) {
				Date now = new Date(System.currentTimeMillis());
				Date expiration = entry.getValue().getExpirationDate(); 
				if(now.compareTo(expiration) > 0) {
					allActiveAuctions.remove(entry.getKey());
				}
			}
		}
	}

	/**
	 * creates an auction
	 * @param owner
	 * @param duration
	 * @param description
	 * @return id of auction
	 */
	public synchronized int createAuction(String owner, int duration, String description) {
		Auction auctionNew = new Auction(++auctionId, owner, duration, description);
		allActiveAuctions.put(auctionId, auctionNew);
		return auctionId;
	}

	/**
	 * returns expiration date of auction
	 * @param id auction id
	 * @return expiration date
	 */
	public Date getExpiration(int id) {
		return allActiveAuctions.get(id).getExpirationDate();
	}

}
