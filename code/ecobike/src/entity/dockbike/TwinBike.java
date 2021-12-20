package entity.dockbike;

public class TwinBike extends Bike {

	public static final int PRICE = 550000;

	public static final double MULTIPLE = 1.5;

	public TwinBike(int id, String licensePlate, String type, String imagePath, int price) {
		super(id, licensePlate, type, imagePath, price);
	}
}
