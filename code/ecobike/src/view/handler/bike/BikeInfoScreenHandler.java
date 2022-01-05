package view.handler.bike;

import common.exception.EntityNotFoundException;
import entity.dockbike.Bike;
import entity.dockbike.Cell;
import entity.dockbike.Dock;
import entity.dockbike.StandardEBike;
import entity.payment.Invoice;
import entity.rental.RentInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Configs;
import view.handler.BaseScreenHandler;
import view.handler.payment.PaymentRentBikeHandler;
import view.handler.payment.ResultScreenHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
	
	@FXML
	private Button rentBikeBtn;

	private Bike bike;

	public BikeInfoScreenHandler(Stage stage, String screenPath, Bike bike) throws IOException, SQLException, EntityNotFoundException {
		super(stage, screenPath);
		this.bike = bike;
		setBikeInfo(bike);
		rentBikeBtn.setOnMouseClicked(e -> {
			try {
				handleRentBikeBtnClick();
			}
			catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		});
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
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public void handleRentBikeBtnClick() throws IOException, SQLException {
//		RentInfo renInfo =  RentInfo.getRentInfo();
//		if(renInfo != null && renInfo.isComplete() == false) {
//			return;
//		}
//		else {
			RadioButton selectedBtn = (RadioButton) rentOptionGroup.getSelectedToggle();
			String type = null;
			if(selectedBtn.getId().equals(new String("byDayRadioBtn"))){
				type = RentInfo.NOMAL_RENT_TYPE;
			}
			else{
				type = RentInfo.ONEDAY_RENT_TYPE;
			}
			LocalDateTime today =LocalDateTime.now();
			RentInfo rentInfo = new RentInfo(today, type, bike);
			Invoice invoice = new Invoice("Thanh toan tien dat coc", rentInfo.getBike().getComposit(), rentInfo);
//			Cell startCell = Cell.getCellInDockByBike(rentInfo.getBike().getId());
//			renInfo.setStartCellId(startCell.getNo());
//			renInfo.setStartDockId(startCell.getDockId());
			BaseScreenHandler paymentScreen = new PaymentRentBikeHandler(this.stage, Configs.PAYMENT_SCREEN_RENT_BIKE_PATH, invoice);
			paymentScreen.setPreviousScreen(this);
			paymentScreen.setHomeScreenHandler(homeScreenHandler);
			paymentScreen.setScreenTitle("Payment Screen");
			paymentScreen.show();
			paymentScreen.show();
		}
		
//	}
}
