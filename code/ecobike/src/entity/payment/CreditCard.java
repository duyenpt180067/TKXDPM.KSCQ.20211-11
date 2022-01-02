package entity.payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import entity.db.EcobikeDB;
import entity.dockbike.Cell;

public class CreditCard {

	private String cardCode;

	private String owner;

	private int cvvCode;

	private String dateExpired;
//	private String Date;
	
	public CreditCard(String number,String cardHolder,String issueBank,String Date) {
		
	}

	public CreditCard(String number, String cardHolder, int securityCode, String expirationDate) {
		// TODO Auto-generated constructor stub
		this.cardCode = number;
		this.owner = cardHolder;
		this.cvvCode = securityCode;
		this.dateExpired = expirationDate; 
	}

	/**
	 * Save unprivate into database
	 */
	public void save() {

	}

//	public String getDate() {
//		return Date;
//	}
//
//	public void setDate(String date) {
//		Date = date;
//	}
	
	public static CreditCard getCardFromTransactionId(int id) throws SQLException {
		
		String sql = "SELECT * FROM CARD " +
				
				"where number = " + 
				"(" + "SELECT CARDnumber FROM PAYMENT_TRANSACTION " +
				"where id = "+id+ ")" +
				" ;";
		Statement stm = EcobikeDB.getConnection().createStatement();
		ResultSet res = stm.executeQuery(sql);
		CreditCard creditCard = null;
		if (res.next()){
			String number = res.getString("number");
			String cardHolder = res.getString("cardHolder");
			String issuingBank = res.getString("issuingBank");
//			creditCard = new CreditCard(dockId, no, type);
		}
		return creditCard;
		
	}

}
