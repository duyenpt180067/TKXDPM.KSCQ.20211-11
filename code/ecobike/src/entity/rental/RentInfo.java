package entity.rental;

import entity.dockbike.Bike;

public class RentInfo {
	private int id;
	private String startTime;
	private String endTime;
	private String rentType;
	private int rentedPeriod;
	private int depopsitAmount;
	private Bike bike;
	private boolean isComplete;
	
/**
 * Save rentInfo into database 
 */
	
	public void save() {
		
	}
 
 /**
  * Get rentInfo which was not complete
  */
	public RentInfo getRentInfo() {
		return this;
	}
 /**
  * Update RentInfo
  */
 	public void update(RentInfo object) {
		
	}
}
