package entity.dockbike;

import common.exception.EntityNotFoundException;
import entity.db.EcobikeDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Bike {
	protected int id;
	protected String licensePlate;
	protected String type;
	protected String imagePath;
	protected int price;
	public static final double MULTIPLE = 0;
	public static final String STANDARD_BIKE = "STANDARD_BIKE";
	public static final String STANDARD_EBIKE = "STANDARD_EBIKE";
	public static final String TWIN_BIKE = "TWIN_BIKE";
	public static final double RATIO = 0.4;

	public Bike(int id, String licensePlate, String type, String imagePath, int price) {
		this.id = id;
		this.licensePlate = licensePlate;
		this.type = type;
		this.imagePath = imagePath;
		this.price = price;
	}

	/**
	 * Find detail information of bike when knowing its code
	 * @param bikeId - id of bike
	 * @return detail information of bike
	 */
	public static Bike getBikeById(int bikeId) throws SQLException {
		String sql = "SELECT BIKE.*, BIKE_TYPE.name as type, BIKE_TYPE.price as price FROM BIKE " +
				"INNER JOIN BIKE_TYPE ON BIKE.BIKE_TYPEid = BIKE_TYPE.id" +
				" where BIKE.id = " + bikeId + " ;";
		Statement stm = EcobikeDB.getConnection().createStatement();
		ResultSet res = stm.executeQuery(sql);
		Bike bike = null;
		if (res.next()){
			int id = res.getInt("id");
			String licensePlate = res.getString("licensePlate");
			String imagePath = res.getString("imagePath");
			String type = res.getString("type");
			int price = res.getInt("price");

			if(type.equals(STANDARD_BIKE)){
				bike = new StandardBike(id, licensePlate, type, imagePath, price);
			}
			if(type.equals(STANDARD_EBIKE)){
				int remainBattery = StandardEBike.getRemainBatteryOfBike(id);
				bike = new StandardEBike(id, licensePlate, type, imagePath, price, remainBattery);
			}
			if(type.equals(TWIN_BIKE)){
				bike = new TwinBike(id, licensePlate, type, imagePath, price);
			}
		}
		return bike;
	}

	public boolean isAvailable() throws SQLException {
		String sql = "SELECT * FROM CELL " +
				"where BIKEid = " + this.id + " ;";
		Statement stm = EcobikeDB.getConnection().createStatement();
		ResultSet res = stm.executeQuery(sql);
		if (res.next()){
			return true;
		}
		return false;
	}

	/**
	 * Get data of list dock
	 * @throws if not found dock
	 */
	public List<Dock> getNotFullDockForBike() throws EntityNotFoundException, SQLException {
		List<Dock> dockList = Dock.getAllDocks();
		List<Dock> notFullDockList = new ArrayList<Dock>();
		for (Dock dock : dockList){
			List<Cell> cellList = Cell.getEmptyCellInDockForBike(dock.getId(), this.type);
			if(cellList.size() > 0){
				notFullDockList.add(dock);
			}
		}
		return notFullDockList;
	}

	public String getName() {
		return null;
	}

	public Cell getLocation() throws SQLException {
		String sql = "SELECT CELL.*, CELL_TYPE.name as type FROM CELL " +
				"INNER JOIN CELL_TYPE ON CELL.CELL_TYPEid = CELL_TYPE.id " +
				"where BIKEid = " + this.id + " ;";
		Statement stm = EcobikeDB.getConnection().createStatement();
		ResultSet res = stm.executeQuery(sql);
		Cell cell = null;
		while (res.next()){
			int no = res.getInt("no");
			int dockId = res.getInt("DOCKid");
			String type = res.getString("type");
			cell = new Cell(dockId, no, type);
		}
		return cell;
	}
	
	public Double getBikeRentalMultiple() {
		Map<String, Double> map = new HashMap<String, Double>();
		map.put(STANDARD_BIKE, 1.0);
		map.put(STANDARD_EBIKE, 1.5);
		map.put(TWIN_BIKE, 1.5);
		return map.get(type);
	}
	
	public int getDepositAmount() {
		return  (int) (price * RATIO);
	}

	public String toString() {
		return "xe dap: "+
				"id: "+ this.id +
				"type: "+ type;
	}

	/***
	 * validate bike code
	 * @param code
	 * @return
	 */
	public static boolean validateBikeCode(String code) {
		if(code == null || code.trim().isEmpty() || code.length() > 20) {
			return false;
		}
		else {
			if (code.matches("([0-9]){1,20}"))
				return true;
			else
				return false;
		}
	}


	public String getType(){
		return type;
	}

	public String getImagePath(){
		return imagePath;
	}

	public int getId() {
		return id;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public int getPrice() {
		return price;
	}
}
