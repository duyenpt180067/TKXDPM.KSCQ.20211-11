package entity.rental;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


import entity.db.EcobikeDB;
import entity.dockbike.Bike;
import entity.payment.CreditCard;

public abstract class RentInfo {
	
	public static String NOMAL_RENT_TYPE = "nomal";
	public static String ONEDAY_RENT_TYPE = "24 hours";
	
	protected int id;
	protected LocalDateTime startTime;
	protected LocalDateTime endTime;
	protected String rentType;
	protected int rentedPeriod;
	protected int depositAmount;
	protected Bike bike;
	protected boolean isComplete;
	protected int returnDockId;
	protected int returnCellId;
	protected int rentAmount;
	protected int startCellId;
	protected int startDockId;


	public RentInfo(LocalDateTime startTime,
			Bike bike) {
		super();
		this.startTime = startTime;
		this.bike = bike;
		this.isComplete = false;
		this.depositAmount = bike.getDepositAmount();
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
	 * @throws SQLException
	 */
	public void saveInitalRentInfo() throws SQLException{
	      String sql = "INSERT INTO 'RENT_INFO' (startTime,rentType, depositAmount, isComplete, BIKEid) VALUES (?, ?, ?, ?,?)";
	      Connection conn = EcobikeDB.getConnection();
		  PreparedStatement prestat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	      try
	      {
	    	  System.out.println("bat dau insert");
	         
	         prestat.setString(1, this.startTime.toString());
	         prestat.setString(2, this.rentType);
	         prestat.setInt(3, this.depositAmount);
	         prestat.setInt(4, 0);
	         prestat.setInt(5, this.bike.getId());
	         
	         prestat.executeUpdate();
	         ResultSet resultUpdate = prestat.getGeneratedKeys();
	         System.out.println("ket qua tra ve:" + resultUpdate);

	      } catch (SQLException throwables) {
	         throwables.printStackTrace();
	      } finally {
	    	  prestat.close();
	      }
	   }


	public void saveFullRentInfo() throws SQLException {
        String sql = "UPDATE RENT_INFO SET endTime = ?, "
                + "rentedPeriod = ?, "
                + "rentAmount = ?, "
                + "isComplete = ?, "
                + "returnDockId = ?, "
                + "returnCellId = ? "
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
	public static List<RentInfo> getNotCompleteRentInfo()  {
		List<RentInfo> rentInfoList = new ArrayList<RentInfo>();
		try {
			String sql = "SELECT * FROM RENT_INFO"+
					" where isComplete = " + 0 + " ;";
			Statement stm = EcobikeDB.getConnection().createStatement();
			ResultSet res = stm.executeQuery(sql);
			while(res.next()) {
				int id = res.getInt("id");
				int bikeId = res.getInt("BIKEid");
				LocalDateTime startTimeString =LocalDateTime.parse(res.getString("startTime"));
				LocalDateTime startTime = startTimeString;
				String rentType = res.getString("rentType");
				int rentedPeriod = res.getInt("rentedPeriod");
				boolean isComplete = false;
				int returnDockId = res.getInt("returnDockId");
				int returnCellId = res.getInt("returnCellId");
				Bike bike = Bike.getBikeById(bikeId);
				int depopsitAmount = bike.getDepositAmount();
				NormalRentInfo rentInfo = new NormalRentInfo(id,startTime,
						rentedPeriod,depopsitAmount,bike,false,returnDockId,returnCellId);
				rentInfoList.add(rentInfo);
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("RentInfo: null");
		}
		return rentInfoList;
	}

 	
	public int getDepositAmount() {
		return depositAmount;
	}

	public Bike getBike() {
		return bike;
	}
	
	public void setReturnLocation(int dockId, int cellId) {
		this.returnDockId = dockId;
		this.returnCellId = cellId;
	}

	public int getCurrentAmount(int minutes) {
		return 0;
	}
	public CreditCard getDepositCard() {
		CreditCard card = null;
		try {
			String sql = "SELECT * FROM CARD"+
					" where number = " +
					"(SELECT DISTINCT cardNum FROM PAYMENT_TRANSACTION where rentInfoId = " + this.id + ")"; 
			Statement stm = EcobikeDB.getConnection().createStatement();
			ResultSet res = stm.executeQuery(sql);
			String number;
			String cardHolder;
			String issuingBank;
			String dateExpired;
			if(res.next()) {
				number = res.getString("number");
				cardHolder = res.getString("cardHolder");
				dateExpired = res.getString("expiratonDate");	
				card = new CreditCard(number,cardHolder,298,dateExpired);
				System.out.println("thong tin CreditCard: " + "number: "+ number + " cardHolder : " + cardHolder);			
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("CardHolker: null");
		}
		
		return card;
	}

	public boolean isComplete() {
		return isComplete;
	}


	public int getId() {
		return id;
	}


	public int getRentedPeriod() {
		return rentedPeriod;
	}

	public int getReturnDockId() {
		return returnDockId;
	}


	public int getReturnCellId() {
		return returnCellId;
	}

	public double getBikeMulti() {
		return bike.getBikeRentalMultiple();
	}

	public int getRentAmount() {
		return rentAmount;
	}

	public abstract int getCurrentTime(LocalDateTime nowDateTime);

	public abstract void updateReturnInfo(LocalDateTime nowDateTime, int returnDockId, int returnCellId);
}
