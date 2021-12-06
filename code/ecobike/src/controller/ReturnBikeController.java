package controller;

import java.util.List;

import javax.swing.plaf.basic.BasicToolBarUI.DockingListener;

import entity.dockbike.Bike;
import entity.dockbike.Cell;
import entity.dockbike.Dock;
import entity.rental.RentInfo;

/**
 * 
 * @author vanpham
 *
 */
public class ReturnBikeController {
	public RentInfo rentInfo;
	
/**
 * Process return bike 
 * @param bike
 */
	public void returnBike(Bike bike) {

	}
	
/**
 * Get list of dock which has empty cell
 * @return 
 */
	
	public List<Dock> getNotFullDock() {
		return null;
	}
	
/**
 * Get list of empty cell to return bike
 * @param dock -dock which was chosen
 * @return
 */
	
	public List<Cell> getEmptyCellIndock(Dock dock) {
		return null;
	}
	
/**
 * Calculate rent amount
 * @return 
 */
	public int calculateRentAmount() {
		return 0;
	}
}
