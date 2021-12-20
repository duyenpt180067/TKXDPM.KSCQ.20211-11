package view.handler.bike;

import common.exception.EntityNotFoundException;
import entity.dockbike.Bike;
import entity.dockbike.Cell;
import entity.dockbike.Dock;
import entity.dockbike.StandardEBike;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.handler.BaseScreenHandler;

import java.io.IOException;
import java.sql.SQLException;

/**
 * define the behaviour of {@code BikeInfoScreen}
 */
public class BikeInfoScreenHandler extends BaseScreenHandler {
	@FXML
	private Text bikeTypeText;

	@FXML
	private Text licenseText;

	@FXML
	private Text bikeIdText;

	@FXML
	private Text locationText;

	@FXML
	private Text rentFeeText;

	@FXML
	private Text batteryText;

	public BikeInfoScreenHandler(Stage stage, String screenPath, Bike bike) throws IOException, SQLException, EntityNotFoundException {
		super(stage, screenPath);
		setBikeInfo(bike);
	}

	private void setBikeInfo(Bike bike) throws SQLException, EntityNotFoundException {
		bikeTypeText.setText(bike.getType());
		licenseText.setText(bike.getLicensePlate());
		bikeIdText.setText(String.valueOf(bike.getId()));
		Cell locationCell = bike.getLocation();
		if(locationCell == null){
			locationText.setText("Đang được thuê");
		}
		else{
			Dock locationDock = Dock.getDockById(locationCell.getDockId());
			locationText.setText("Bãi:" + locationDock.getName() + "; Ô số: " + locationCell.getNo());
		}
		// TODO: xem giá thuê xe
		rentFeeText.setText("");
		if(bike.getType().equals(Bike.STANDARD_EBIKE)){
			batteryText.setText(String.valueOf(StandardEBike.getRemainBatteryOfBike(bike.getId())));
		}
		else{
			batteryText.setText("");
		}
	}

	/**
	 * Display {@code BikeInfoScreen}
	 * @param bikeId
	 */
	public void display(int bikeId) {

	}

	/**
	 * Display suitable form right after user chooses rent option
	 */
	public void handleChooseRentOption() {

	}

	/**
	 * handle after user clicks “Rent Bike” button
	 */
	public void handleRentBikeBtnClick() {

	}

}
