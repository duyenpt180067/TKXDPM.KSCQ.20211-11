package controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import entity.rental.RentInfo;
import utils.Configs;



public class RentBikeController {
	
	private RentInfo rentInfo;
	/**
	 * Create  rentInfo 
	 */
	
	public RentBikeController() {
		rentInfo = RentInfo.getRentInfo();
		System.out.println("constructor get rent info:"+ rentInfo.getStartTime());
	}
	public void createRentInfo() {
		
	}
	
	public void setRentInfo(RentInfo rentInfo) {
		this.rentInfo = rentInfo;
	}
	
	/**
	 * Get RentInfo for display 
	 * @throws SQLException 
	 */
	public RentInfo getRentInfo() throws SQLException {
		
		return rentInfo;
	} 
	
	/**
	 * Get deposit for the bike
	 */
	public int calculateDepositeAmount() {
		return 0;
	}
	
	public Map<String, String> getScreenInfo() {
		Map<String, String> infoMap = new HashMap<String, String>();
		infoMap.put("imagePath", rentInfo.getBike().getImagePath());
		infoMap.put("name", "Xe số: " + rentInfo.getBike().getId());
		infoMap.put("type", rentInfo.getBike().getName());
		
		LocalDateTime nowDateTime = LocalDateTime.now();
		String nowString = nowDateTime.toString();	

		
		LocalDateTime startTime = LocalDateTime.parse(rentInfo.getStartTime());
		
		long tmpminutes = ChronoUnit.MINUTES.between(startTime, nowDateTime);
		
		int minutes = (int) (rentInfo.getRentedPeriod() + tmpminutes);
		int hours = minutes / 60;
		int timeminutes = minutes % 60;
		String timeString = hours+ "h"+ timeminutes+"m";
		infoMap.put("time", timeString);
		String amountString = rentInfo.getAmount(minutes) + Configs.CURRENCY;
		infoMap.put("amount",amountString );
		return infoMap;
	}
	
	public void processReturnBikeRequest() {
		LocalDateTime nowDateTime = LocalDateTime.now();
		String nowString = nowDateTime.toString();	
		
		LocalDateTime startTime = LocalDateTime.parse(rentInfo.getStartTime());
		
		long tmpminutes = ChronoUnit.MINUTES.between(startTime, nowDateTime);
		
		rentInfo.setEndTime(nowString);
		int minutes = (int) (rentInfo.getRentedPeriod() + tmpminutes);
		rentInfo.setRentedPeriod(minutes);
		
		
	}
}
