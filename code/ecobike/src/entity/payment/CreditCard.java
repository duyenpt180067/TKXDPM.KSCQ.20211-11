package entity.payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import common.exception.InvalidCardException;
import common.exception.InvalidFormInputException;
import entity.db.EcobikeDB;
import entity.dockbike.Cell;

public class CreditCard {

	private String cardCode;

	private String owner;

	private int cvvCode;
	private String issuingBank;

	private String dateExpired;
//	private String Date;
	
	public CreditCard() {
		
	}

	public CreditCard(String number,String cardHolder,String issuingBank,String Date) {
		this.cardCode = number;
		this.owner = cardHolder;
		this.issuingBank = issuingBank;
		this.dateExpired = Date;
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
	 * @throws SQLException 
	 */
	public void save() throws SQLException {
		  if(checkCardInDatabase() == false) {
			  String sql = "INSERT INTO 'CARD' (number,cardHolder, issuingBank, expiratonDate) VALUES (?, ?, ?, ?)";
		      Connection conn = EcobikeDB.getConnection();
			  PreparedStatement prestat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		      try
		      {
		    	  System.out.println("bat dau insert");
		         
		         prestat.setString(1, this.cardCode);
		         prestat.setString(2, this.owner);
		         prestat.setString(3, this.issuingBank);
		         prestat.setString(4, this.dateExpired);
		         
		         prestat.executeUpdate();
		         ResultSet resultUpdate = prestat.getGeneratedKeys();
		         System.out.println("Save Card: " + resultUpdate);

		      } catch (SQLException throwables) {
		         throwables.printStackTrace();
		      } finally {
		        
		      }
		  }
	}


	private boolean checkCardInDatabase() throws SQLException {
		
		String sql = "SELECT TOP(1) FROM CARD " +	
				"where number = " + this.cardCode + ";";	
		Statement stm = EcobikeDB.getConnection().createStatement();
		ResultSet res = stm.executeQuery(sql);
		if (res.next()){
			return true;
		}
		return false;
		
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getCvvCode() {
		return cvvCode;
	}

	public void setCvvCode(int cvvCode) {
		this.cvvCode = cvvCode;
	}

	public String getIssuingBank() {
		return issuingBank;
	}

	public void setIssuingBank(String issuingBank) {
		this.issuingBank = issuingBank;
	}

	public String getDateExpired() {
		return dateExpired;
	}

	public void setDateExpired(String dateExpired) {
		this.dateExpired = dateExpired;
	}
	
	
	/***
	 * validate card number
	 * @param cardNumber
	 * @return
	 */
	public boolean validateCardNumber(String cardNumber) {
    	if(cardNumber == null || cardNumber.trim().isEmpty() || cardNumber.length() > 50) {
    		return false;
    	}
    	else {
    		if (cardNumber.matches("([a-z0-9_-]){1,50}"))
    		    return true;
    		else
    		   return false;
    	}
    }
	
	/***
	 * validate name
	 * @param name
	 * @return
	 */
	public boolean validateName(String name) {
    	if(name == null || name.trim().isEmpty() || name.length() > 20) {
    		return false;
    	}
    	else {
    		if (name.matches("([a-z0-9_-]){1,20}"))
    		    return true;
    		else
    		   return false;
    	}
    }
	
	/***
	 * validate cvv number
	 * @param cvv
	 * @return
	 */
	public boolean validateCVVNumber(String cvv) {
		if(cvv == null || cvv.trim().isEmpty() || cvv.length() > 20) {
    		return false;
    	}
    	else {
    		if (cvv.matches("([0-9]){1,20}"))
    		    return true;
    		else
    		   return false;
    	} 
	}

	/**
	 * @param info - card information submited by user
	 * @return 1 if all fields are filled with valid input
	 * @throws InvalidFormInputException if any required field is empty or input is invalid
	 */
	public boolean validatePaymentForm(CreditCard info) throws InvalidFormInputException {
		return true;
	}
	
	/***
	 * validate date or expiration date
	 * @param date
	 * @return
	 */
	public boolean validateDate(String date, boolean isExpirationDate) {
    	if(date == null || date.trim().isEmpty() ) {
    		return false;
    	}
    	else {
    		if (date.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})"))
    			if(isExpirationDate) {
    				String[] eDate = date.split("/");
    				String pattern = "dd-MM-yyyy";
    				String now = new SimpleDateFormat(pattern).format(new Date());
    				String[] eNow = now.split("-");
					if ((eDate[2].compareTo(eNow[2]) < 0) 
							|| ((eDate[2].compareTo(eNow[2]) == 0) && (eDate[1].compareTo(eNow[1]) < 0))
							|| ((eDate[2].compareTo(eNow[2]) == 0) && (eDate[1].compareTo(eNow[1]) == 0) && (eDate[0].compareTo(eNow[0]) < 0))) {
						return false;
					}
					else {
						return true;
					}
    			}
    			else return true;
    		else
    		   return false;
    	}
    }
	
	/***
	 * Date to string
	 * @param date
	 * @return
	 * @throws InvalidCardException
	 */
	public static String getExpirationDate(String date) throws InvalidCardException {
		String[] strs = date.split("/");
		if (strs.length != 2) {
			throw new InvalidCardException();
		}

		String expirationDate = null;
		int month = -1;
		int year = -1;

		try {
			month = Integer.parseInt(strs[0]);
			year = Integer.parseInt(strs[1]);
			if (month < 1 || month > 12 || year < Calendar.getInstance().get(Calendar.YEAR) % 100 || year > 100) {
				throw new InvalidCardException();
			}
			expirationDate = strs[0] + strs[1];

		} catch (Exception ex) {
			throw new InvalidCardException();
		}

		return expirationDate;
	}

}
