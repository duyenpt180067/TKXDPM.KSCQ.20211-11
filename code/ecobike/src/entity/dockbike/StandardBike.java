package entity.dockbike;

public class StandardBike extends Bike {
	// load tu csdl
	private static int PRICE = 400000;
	private static double MULTIPLE = 1.0;

	public StandardBike(int id, String licensePlate, String type, String imagePath, int price) {
		super(id, licensePlate, type, imagePath, price);
	}
}
