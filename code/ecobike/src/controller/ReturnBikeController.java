package controller;

import java.sql.SQLException;
import java.util.List;

import common.exception.EntityNotFoundException;
import entity.dockbike.Bike;
import entity.dockbike.Cell;
import entity.dockbike.Dock;
import entity.payment.Invoice;
import entity.rental.RentInfo;

/**
 * 
 * @author vanpham
 *
 */
public class ReturnBikeController extends BaseController {
	public RentInfo rentInfo;
	
	public ReturnBikeController(RentInfo rentInfo) {
		this.rentInfo = rentInfo;
	}
	
	
	
	/**
	 * Update info in database when payment success, include:
	 * update cell(in database)
	 * save rentInfo (in database)
	 * save invoice (in database)
	 * @param bike
	 */
	public void returnBike(Bike bike) {
		
	}
	
	/**
	 * Get list of dock which has empty cell
	 * @return 
	 * @throws EntityNotFoundException 
	 * @throws SQLException 
	 */
	
	public List<Dock> getNotFullDock() throws EntityNotFoundException, SQLException {
		return  Dock.getAllDocks();
	}
	
	/**
	 * Get list of empty cell to return bike
	 * @param dock -dock which was chosen
	 * @return
	 * @throws EntityNotFoundException 
	 * @throws SQLException 
	 */
	
	public List<Cell> getEmptyCellIndock(Dock dock) throws EntityNotFoundException, SQLException {
		getRentInfo();
		return Cell.getEmptyCellInDockForBike(dock.getId(), rentInfo.getBike().getName());
		
	}
	
	
	public Invoice createInvoice() {
		int amount = calculateRentAmount();
		String contentString = "tra tien thue xe";
		Invoice invoice = new Invoice(contentString,amount,rentInfo);
		return invoice;
	}
	
	/**
	 * Calculate rent amount
	 * @return 
	 */
	public int calculateRentAmount() {
		int amount =  (int) (rentInfo.getAmount(rentInfo.getRentedPeriod()) );
		return amount;
	}
	
	/**
	 * Get RentInfo, get from database if rentInfo has not been initalize
	 * @return
	 * @throws SQLException
	 */
	public RentInfo getRentInfo() throws SQLException {
		
		return rentInfo;
	}

	public void setRentInfo(RentInfo rentInfo) {
		this.rentInfo = rentInfo;
	}
	
}
