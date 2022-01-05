package view.handler.bike;

import common.exception.EntityNotFoundException;
import common.exception.NoBikeRentedException;
import entity.dockbike.Bike;
import entity.dockbike.Cell;
import entity.dockbike.Dock;
import entity.dockbike.StandardEBike;
import entity.payment.Invoice;
import entity.rental.NormalRentInfo;
import entity.rental.OneDayRentInfo;
import entity.rental.RentInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Configs;
import view.handler.BaseScreenHandler;
import view.handler.payment.PaymentRentBikeHandler;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

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
	private Text rentalMultiple;

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

	@FXML
	private ImageView bikeImage;

	private Bike bike;

	private static final Integer IMAGE_WIDTH = 400;
	private static final Integer IMAGE_HEIGHT = 230;

	public BikeInfoScreenHandler(Stage stage, String screenPath, Bike bike) throws IOException, SQLException, EntityNotFoundException {
		super(stage, screenPath);
		this.bike = bike;
		setBikeInfo(bike);
		rentBikeBtn.setOnMouseClicked(e -> {
			try {
				handleRentBikeBtnClick();
			}
			catch (Exception ex) {
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
		File file = new File(bike.getImagePath());
		Image image = new Image(file.toURI().toString(), IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
		bikeImage.setFitWidth(IMAGE_WIDTH);
		bikeImage.setFitHeight(IMAGE_HEIGHT);
		bikeImage.setImage(image);

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
		rentalMultiple.setText(String.valueOf(bike.MULTIPLE));
		if(bike.getName().equals(Bike.STANDARD_EBIKE)){
			batteryText.setText(String.valueOf(StandardEBike.getRemainBatteryOfBike(bike.getId())));
		}
		else{
			batteryText.setText("");
		}
		byHourRadioBtn.setSelected(true);
	}

	/**
	 * handle after user clicks “Rent Bike” button
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public void handleRentBikeBtnClick() throws IOException, SQLException {
			LocalDateTime today =LocalDateTime.now();
			RadioButton selectedBtn = (RadioButton) rentOptionGroup.getSelectedToggle();
			RentInfo rentInfo = null;
			if(byHourRadioBtn.isSelected()){
				rentInfo = new NormalRentInfo(today, bike);
			}
			else{
				rentInfo = new NormalRentInfo(today, bike);
			}
		if(RentInfo.getNotCompleteRentInfo().size() == 0){
			Invoice invoice = new Invoice("Thanh toan tien dat coc", rentInfo.getBike().getDepositAmount(), rentInfo);
			BaseScreenHandler paymentScreen = new PaymentRentBikeHandler(this.stage, Configs.PAYMENT_SCREEN_RENT_BIKE_PATH, invoice);
			paymentScreen.setPreviousScreen(this);
			paymentScreen.setHomeScreenHandler(homeScreenHandler);
			paymentScreen.setScreenTitle("Payment Screen");
			paymentScreen.show();
		}
		else{
			showDialog("Da co xe thue", "Notification");
		}
	}
}
