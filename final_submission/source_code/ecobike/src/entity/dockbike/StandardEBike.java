package entity.dockbike;

import entity.db.EcobikeDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StandardEBike extends Bike {

	private int remainBattery;

	public static final int PRICE = 700000;

	public static final double MULTIPLE = 1.5;

	private static final String NAME = "Xe đạp điện đơn";

	public StandardEBike(int id, String licensePlate, String type, String imagePath, int price, int remainBattery) {
		super(id, licensePlate, type, imagePath, price);
		this.remainBattery = remainBattery;
	}

	public static int getRemainBatteryOfBike(int bikeId) throws SQLException {
		String sql = "SELECT remainBattery FROM BIKE_BATTERY " +
				" where BIKEid = " + bikeId + " ;";
		Statement stm = EcobikeDB.getConnection().createStatement();
		ResultSet res = stm.executeQuery(sql);
		if (res.next()){
			int battery = res.getInt("remainBattery");
			return battery;
		}
		return 0;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
