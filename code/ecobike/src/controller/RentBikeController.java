package controller;

import entity_rental.RentInfo;

public class RentBikeController {
	
	private RentInfo rentInfo;
/**
 * Create  rentInfo 
 */
	public void createRentInfo() {
		
	}
	
/**
 * Get RentInfo for display 
 */
	public RentInfo getRentInfo() {
		
		return this.rentInfo.getRentInfo();
	} 
	
/**
 * Get deposit for the bike
 */
	public int calculateDepositeAmount() {
		return 0;
	}
	
}
