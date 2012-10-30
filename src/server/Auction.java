package server;

import java.text.DecimalFormat;
import java.util.Date;

public class Auction {

	private String owner, description, highestBidder, prevHighestBidder;
	private int duration;
	private long startTime;
	private double highestBid;
	private Date endTime;
	private int id;
	
	private static DecimalFormat bidFloatFormat;
	
	static {
		bidFloatFormat = new DecimalFormat();
		bidFloatFormat.setMaximumFractionDigits(2);
		bidFloatFormat.setMinimumFractionDigits(2);				
	}

	public Auction(int auctionId, String nameOfOwner, int durationInSec, String description) {
		id = auctionId;
		owner = nameOfOwner;
		duration = durationInSec;
		this.description = description;
		startTime = System.currentTimeMillis();
		highestBid = 0.00;
		highestBidder = "none";
		prevHighestBidder = "none";
		endTime = new Date(startTime + duration);
	}

	public Date getExpirationDate() {
		return endTime;
	}

	public String getOwner() {
		return owner;
	}

	public int getDuration() {
		return duration;
	}

	public String getDescription() {
		return description;
	}

	public String getHighestBidder() {
		return highestBidder;
	}

	public double getHighestBid() {
		return highestBid;
	}
	
	public String getHighestBidString() {
		return bidFloatFormat.format(highestBid);
	}

	public void setHighestBid(double newHighestBid, String newHighestBidder) {
		prevHighestBidder = highestBidder;
		highestBid = newHighestBid;
		highestBidder = newHighestBidder;
	}

	public String getPreviousHighestBidder() {
		return prevHighestBidder;
	}
	
	@Override
	public String toString() {

		return id + ". '" + description + "' " + owner + " " + endTime + " " + bidFloatFormat.format(highestBid) + " " + highestBidder; 
	}


}
