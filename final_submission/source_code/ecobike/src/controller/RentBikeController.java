package controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
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
		rentInfo = RentInfo.getNotCompleteRentInfo().get(0);
	}

	/**
	 * Get RentInfo for display 
	 * @throws SQLException 
	 */
	public RentInfo getRentInfo() throws SQLException {
		return rentInfo;
	} 

	
	public Map<String, String> getRentedInfoMap() {
		Map<String, String> infoMap = new HashMap<String, String>();
		infoMap.put("imagePath", rentInfo.getBike().getImagePath());
		infoMap.put("name", "Xe số: " + rentInfo.getBike().getId());
		infoMap.put("type", rentInfo.getBike().getName());
		LocalDateTime nowDateTime = LocalDateTime.now();
		int minutes = rentInfo.getCurrentTime(nowDateTime);
		int hours = minutes / 60;
		int timeminutes = minutes % 60;
		String timeString = hours+ "h"+ timeminutes+"m";
		infoMap.put("time", timeString);
		String amountString = rentInfo.getCurrentAmount(minutes) + Configs.CURRENCY;
		infoMap.put("amount",amountString );
		infoMap.put("pin","Không có" );
		return infoMap;
	}
	
	public void processReturnBikeRequest() {

	}
}
