package entity.rental;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


import entity.db.EcobikeDB;
import entity.dockbike.Bike;
import entity.dockbike.StandardBike;
import entity.dockbike.StandardEBike;
import entity.dockbike.TwinBike;
import entity.payment.CreditCard;
import utils.Configs;

public class RentInfo {
	
	public static String NOMAL_RENT_TYPE = "nomal";
	public static String ONEDAY_RENT_TYPE = "24 hours";
	
	private int id;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String rentType;
	private int rentedPeriod;
	private int depositAmount;
	private Bike bike;
	private boolean isComplete;
	private int returnDockId;
	private int returnCellId;
	private int rentAmount;
	
	
	
	
	public RentInfo() {
		super();
	}

	public RentInfo(LocalDateTime startTime, String rentType,
			Bike bike) {
		super();
		this.startTime = startTime;
		this.rentType = rentType;
		this.bike = bike;
		this.isComplete = false;
		this.depositAmount = bike.getComposit();
	}

	public RentInfo(int id, LocalDateTime startTime, String rentType, int rentedPeriod,
					int depositAmount, Bike bike, boolean isComplete, int returnDockId, int returnCellId) {
		this.id = id;
		this.startTime = startTime;
		this.rentType = rentType;
		this.rentedPeriod = rentedPeriod;
		this.depositAmount = depositAmount;
		this.bike = bike;
		this.isComplete = isComplete;
		this.returnDockId = returnDockId;
		this.returnCellId = returnCellId;
		
	}

	/**.
 * Save rentInfo into database 
 */
	
	

	public void saveInitalRentInfo(){
	      String sql = "INSERT INTO 'RENT_INFO' (startTime,rentType, depositAmount, isComplete, BIKEid) VALUES (?, ?, ?, ?,?)";
//	      Connection conn = EcobikeDB.getConnection();
//	      PreparedStatement prestat = null;
	      try(
	    		  Connection conn = EcobikeDB.getConnection();
	    		  PreparedStatement prestat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    		  )
	      {
	    	  System.out.println("bat dau insert");
	         
	         prestat.setString(1, this.startTime.toString());
	         prestat.setString(2, this.rentType);
	         prestat.setInt(3, this.depositAmount);
	         prestat.setInt(4, this.bike.getId());
	         prestat.setInt(5, 0);
	         
	         prestat.executeUpdate();
	         ResultSet resultUpdate = prestat.getGeneratedKeys();
	         System.out.println("ket qua tra ve:" + resultUpdate);

	      } catch (SQLException throwables) {
	         throwables.printStackTrace();
	      } finally {
	        
	      }
	   }
	
	public void saveFullRentInfo() throws SQLException {
        String sql = "UPDATE RENT_INFO SET endTime = ? , "
                + "rentedPeriod = ? "
                + "rentAmount = ? "
                + "isComplete = ?"
                + "returnDockId = ?"
                + "returnCellId = ?"
                + "WHERE id = ?";
        Connection conn = EcobikeDB.getConnection();
	      PreparedStatement prestat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        try  {

            // set the corresponding param
        	prestat.setString(1, this.endTime.toString());
        	prestat.setInt(2, this.rentedPeriod);
        	prestat.setInt(3, this.rentAmount);
        	prestat.setInt(4, 1);
        	prestat.setInt(5, this.returnDockId);
        	prestat.setInt(6, this.returnCellId);
        	prestat.setInt(7, this.id);
            
            // update 
        	prestat.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
			LocalDateTime startTime;
			LocalDateTime endTime;
			String rentType;
			int rentedPeriod;
			boolean isComplete;
			int returnDockId;
			int returnCellId;
			if(res.next()) {
				bikeId = res.getInt("BIKEid");
				LocalDateTime startTimeString =LocalDateTime.parse(res.getString("startTime"));
				System.out.println("ngay bat dau: lay tu CSDL "+ res.getString("startTime"));
				System.out.println("time: "+ startTimeString);
				startTime = startTimeString;
//				endTime = endTimeString;
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
				rentInfo = new RentInfo(bikeId,startTime,rentType,
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

 	
	public int getDepositAmount() {
		return depositAmount;
	}

	public Bike getBike() {
		return bike;
	}
	
	public void setReturnPos(int dockId, int cellId) {
		this.returnDockId = dockId;
		this.returnCellId = cellId;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
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
	
	
	
	public int getId() {
		return id;
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

	public int getNomalAmount(int minutes) {
		if(minutes < 10) return 0;
		long amount = 10000;
		minutes -= 30;
		while (minutes > 0 ) {
			minutes -= 15;
			amount += 3000;
		}
		return  (int) (amount*getBikeMulti());

	}
	
	public int getCurrentAmount(int minutes) {
		if(minutes < 10) return 0;
		long amount = 10000;
		minutes -= 30;
		while (minutes > 0 ) {
			minutes -= 15;
			amount += 3000;
		}
		return  (int) (amount*getBikeMulti());
	}
	
	
	public int getCurrentTime(LocalDateTime nowDateTime) {
		LocalDateTime startTime = this.startTime;
		long tmpminutes = ChronoUnit.MINUTES.between(startTime, nowDateTime);
		int minutes =(int) (this.rentedPeriod + tmpminutes);
		return minutes;
	}
	
	public void updateReturnInfo(LocalDateTime endTime, int returnDockId , int returnCellId) {
		this.endTime = endTime;
		this.returnDockId = returnDockId;
		this.returnCellId = returnCellId;
		this.rentedPeriod = getCurrentTime(endTime);
		this.rentAmount = getCurrentAmount(this.rentedPeriod);
		
	}
	
	
	public CreditCard getDepositCard() {
		CreditCard card = null;
		
		try {
			String sql = "SELECT * FROM CARD"+
					" where number = " +
					"(SELECT DISTINCT CARDnumber FROM PAYMENT_TRANSACTION where RENT_INFOid = " + this.id + ")"; 
			Statement stm = EcobikeDB.getConnection().createStatement();
			ResultSet res = stm.executeQuery(sql);
			String number;
			String cardHolder;
			String issuingBank;
			String dateExpired;
			if(res.next()) {
				number = res.getString("number");
				cardHolder = res.getString("cardHolder");
				issuingBank = res.getString("issuingBank");
				dateExpired = res.getString("expiratonDate");	
				card = new CreditCard(number,cardHolder,issuingBank,dateExpired);
				System.out.println("thong tin CreditCard: " + "number: "+ number + " cardHolder : " + cardHolder);			
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("CardHolker: null");
		}
		
		return card;
		
	}

	
	public double getBikeMulti() {
		return bike.getBikeMulti();
	}

	public int getRentAmount() {
		return rentAmount;
	}

}
