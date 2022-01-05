package entity.payment;

import entity.db.EcobikeDB;
import entity.dockbike.Bike;
import entity.rental.RentInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class PaymentTransaction {

	private String id;

	private String content;

	private String createAt;

	private int amount;

	private RentInfo rentInfo;

	private CreditCard creditCard;
	private String errorCode;

	public PaymentTransaction(String errorCode, CreditCard creditCard, String transactionId, String transactionContent,
							  int amount, String createdAt) {
		super();
		this.errorCode = errorCode;
		this.creditCard = creditCard;
		this.id = transactionId;
		this.content = transactionContent;
		this.amount = amount;
		this.createAt = createdAt;
	}

	/**
	 * Save its object in database
	 * @throws SQLException 
	 */
	public void save() throws SQLException {
		String sql = "INSERT INTO PAYMENT_TRANSACTION ('id', createAt,content,amount,cardNum, rentInfoId) VALUES (?,?,?,?,?,?)";
	      Connection conn = EcobikeDB.getConnection();
		  PreparedStatement prestat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	      try
	      {
	    	  System.out.println("bat dau insert");
	         
	         prestat.setString(1, this.id);
	         prestat.setString(2, this.createAt);
	         prestat.setString(3, this.content);
	         prestat.setInt(4, this.amount);
	         prestat.setString(5, creditCard.getCardCode());
	         prestat.setInt(6, this.rentInfo.getId());
	         
	         prestat.executeUpdate();
	         ResultSet resultUpdate = prestat.getGeneratedKeys();
	         System.out.println("Save Payment Transaction: " + resultUpdate);
	         

	      } catch (SQLException throwables) {
	         throwables.printStackTrace();
	      } finally {
	        
	      }

	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setRentInfo(RentInfo rentInfo) {
		this.rentInfo = rentInfo;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	
}
