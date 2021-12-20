package entity.rental;

import java.security.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import entity.db.EcobikeDB;
import entity.dockbike.Bike;
import entity.dockbike.StandardBike;
import entity.dockbike.StandardEBike;
import entity.dockbike.TwinBike;
import utils.Configs;

public class RentInfo {
	
	public static String NOMAL_RENT_TYPE = "nomal";
	public static String ONEDAY_RENT_TYPE = "24 hours";
	
	private int id;
	private String startTime;
	private String endTime;
	private String rentType;
	private int rentedPeriod;
	private int depopsitAmount;
	private Bike bike;
	private boolean isComplete;
	private int returnDockId;
	private int returnCellId;
	
	
	
	
	public RentInfo() {
		super();
	}

	public RentInfo(int id, String startTime, String endTime, String rentType, int rentedPeriod, int depopsitAmount,
			Bike bike, boolean isComplete, int returnDockId, int returnCellId) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.rentType = rentType;
		this.rentedPeriod = rentedPeriod;
		this.depopsitAmount = depopsitAmount;
		this.bike = bike;
		this.isComplete = isComplete;
		this.returnDockId = returnDockId;
		this.returnCellId = returnCellId;
	}

/**.
 * Save rentInfo into database 
 */
	
	public void save() {
		
	}
 
 /**
  * Get rentInfo which was not complete
 * @throws SQLException 
  */
	public static RentInfo getRentInfo()  {
		RentInfo rentInfo = null;
		try {
			
			String sql = "SELECT * FROM RENT_INFO"+
					" where isComplete = " + 0 + " ;";
			Statement stm = EcobikeDB.getConnection().createStatement();
			ResultSet res = stm.executeQuery(sql);
			int bikeId = 0;
			String startTime;
			String endTime;
			String rentType;
			int rentedPeriod;
			boolean isComplete;
			int returnDockId;
			int returnCellId;
			if(res.next()) {
				bikeId = res.getInt("BIKEid");
				String startTimeString = res.getString("startTime");
				System.out.println("ngay bat dau: lay tu CSDL "+ res.getString("startTime"));
				System.out.println("time: "+ startTimeString);
				String endTimeString = res.getString("endTime");
				startTime = startTimeString;
				endTime = endTimeString;
				rentType = res.getString("rentType");
				rentedPeriod = res.getInt("rentedPeriod");
				isComplete = false;
				returnDockId = res.getInt("returnDockId");
				returnCellId = res.getInt("returnCellId");
				
				Bike bike = null;
				
				bike = Bike.getBikeById(bikeId);
				
				System.out.println("thong tin xe dap: " + bike.toString());
//				rentInfo.setBike(bike);
				int depopsitAmount = bike.getComposit();
				rentInfo = new RentInfo(bikeId,startTime,endTime,rentType,
						rentedPeriod,depopsitAmount,bike,false,returnDockId,returnCellId);
							
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("RentInfo: null");
		}
		

		System.out.println("ham get rent info: ");
		return rentInfo;
	}
 /**
  * Update RentInfo
  */
 	public void update(RentInfo object) {
		
	}
 	
	public int getDepopsitAmount() {
		return depopsitAmount;
	}

	public void setDepopsitAmount(int depopsitAmount) {
		this.depopsitAmount = depopsitAmount;
	}

	public Bike getBike() {
		return bike;
	}

	public void setBike(Bike bike) {
		this.bike = bike;
	}
	
	public void setReturnPos(int dockId, int cellId) {
		this.returnDockId = dockId;
		this.returnCellId = cellId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	public String getRentType() {
		return rentType;
	}

	public void setRentType(String rentType) {
		this.rentType = rentType;
	}
	
	
	
	public int getRentedPeriod() {
		return rentedPeriod;
	}

	public void setRentedPeriod(int rentedPeriod) {
		this.rentedPeriod = rentedPeriod;
	}

	public int getReturnDockId() {
		return returnDockId;
	}

	public void setReturnDockId(int returnDockId) {
		this.returnDockId = returnDockId;
	}

	public int getReturnCellId() {
		return returnCellId;
	}

	public void setReturnCellId(int returnCellId) {
		this.returnCellId = returnCellId;
	}

	public long getAmount(int minutes) {
		if(this.rentType.equals(NOMAL_RENT_TYPE)) {
			return getNomalAmount(minutes);
		}
		else if(this.rentType.equals(ONEDAY_RENT_TYPE)) return getRentDayAmount(minutes);
		return 0;
	}
	
	public long getNomalAmount(int minutes) {
		if(minutes < 10) return 0;
		long amount = 10000;
		minutes -= 30;
		
		
		while (minutes > 0 ) {
			minutes -= 15;
			amount += 3000;
		}
		
		return (long) (amount*getBikeMulti());

	}
	
	public long getRentDayAmount(int minutes) {
		long amount = Configs.ONE_DAY_PASS;
		
		LocalDateTime nowDateTime1 = LocalDateTime.now();	
		
		String startTimeString = this.startTime;
		LocalDateTime startTime = LocalDateTime.parse(startTimeString.toString());

		int hours = minutes / 60;
		if(hours < 12) {
			amount = amount - 10000*(12-hours);
		}
		else if (hours >24) {
			
			minutes -=1440;
			while (minutes > 0 ) {
				minutes -= 15;
				amount += 2000;
			}
		}
		
		return (long) (amount*getBikeMulti());
	}
	
	
	public double getBikeMulti() {
		return bike.getBikeMulti();
	}
	
	
	
	
	
}
