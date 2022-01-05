package entity.dockbike;

public class StandardBike extends Bike {
	// load tu csdl
	private static int PRICE = 400000;
	public static double MULTIPLE = 1.0;
	private static final String NAME = "Xe đạp thường đơn";

	public StandardBike(int id, String licensePlate, String type, String imagePath, int price) {
		super(id, licensePlate, type, imagePath, price);
	}

	@Override
	public String getName() {
		return NAME;
	}


}
