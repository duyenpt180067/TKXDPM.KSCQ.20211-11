package view.handler.bike;

import common.exception.EntityNotFoundException;
import entity.dockbike.Bike;
import entity.dockbike.Cell;
import entity.dockbike.Dock;
import entity.dockbike.StandardEBike;
import entity.rental.RentInfo;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.handler.BaseScreenHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

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

	@FXML
	private ToggleGroup rentOptionGroup;

	@FXML
	private RadioButton byHourRadioBtn;

	@FXML
	private RadioButton byComboRadioBtn;

	private Bike bike;

	public BikeInfoScreenHandler(Stage stage, String screenPath, Bike bike) throws IOException, SQLException, EntityNotFoundException {
		super(stage, screenPath);
		this.bike = bike;
		setBikeInfo(bike);
	}

	/***
	 * set value to bike
	 * @param bike
	 * @throws SQLException if connect to db fail
	 * @throws EntityNotFoundException if not found bike
	 */
	private void setBikeInfo(Bike bike) throws SQLException, EntityNotFoundException {
		bikeTypeText.setText(bike.getName());
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
		if(bike.getName().equals(Bike.STANDARD_EBIKE)){
			batteryText.setText(String.valueOf(StandardEBike.getRemainBatteryOfBike(bike.getId())));
		}
		else{
			batteryText.setText("");
		}
		byHourRadioBtn.setSelected(true);
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
	@FXML
	public void handleRentBikeBtnClick() {
		RadioButton selectedBtn = (RadioButton) rentOptionGroup.getSelectedToggle();
		String type = null;
		if(selectedBtn.getId().equals(new String("byDayRadioBtn"))){
			type = RentInfo.NOMAL_RENT_TYPE;
		}
		else{
			type = RentInfo.ONEDAY_RENT_TYPE;
		}
		Date today = new Date();
		RentInfo rentInfo = new RentInfo(today.toString(), type, bike);
	}
}
