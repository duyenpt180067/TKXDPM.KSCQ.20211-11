package entity.dockbike;

public class Bike {

	protected int id;

	protected String licensePlate;

	protected boolean isAvailable;

	protected String type;

	/**
	 *Find detail information of bike when knowing its dockId
	 *and its No. of cell
	 *
	 * @param dockId - id of Dock
	 * @param cell - No. of cell
	 * @return detail information of bike
	 */
	public static Bike getBikeInDock(int dockId, int cell) {
		return null;
	}

	/**
	 * Find detail information of bike when knowing its code
	 * @param id - id of bike
	 * @return detail information of bike
	 */
	public static Bike getBikeById(int id) {
		return null;
	}

}
