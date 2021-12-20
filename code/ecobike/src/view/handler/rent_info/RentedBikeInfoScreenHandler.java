package view.handler.rent_info;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Logger;

import controller.RentBikeController;
import controller.ReturnBikeController;
import entity.dockbike.Bike;
import entity.payment.Invoice;
import entity.rental.RentInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import view.handler.BaseScreenHandler;
import view.handler.return_bike.ReturnBikeScreenHandler;

/**
 * this class to handler the screen RentedBikeInfo and process the user's react
 * @author vanpham
 *
 */
public class RentedBikeInfoScreenHandler extends BaseScreenHandler{
	
	private static Logger LOGGER = Utils.getLogger(RentedBikeInfoScreenHandler.class.getName());

	@FXML
	ImageView bikeImg;
	
	@FXML
	Text name;
	
	@FXML
	Text type;
	
	@FXML
	Text time;
	
	@FXML
	Text amount;
	
	@FXML
	Text pin;
	
	@FXML
	Button requestReturnBtn;
	
	RentBikeController bController;
	public  RentedBikeInfoScreenHandler(Stage stage, String screenPath) throws IOException, SQLException {
		super(stage,screenPath);
		this.setScreenTitle("Rented Bike Info Screen");
		this.bController = new RentBikeController();
		
		display();
		requestReturnBtn.setOnMouseClicked(e ->{
			try {
				LOGGER.info("click btn");
				handleReturnBikeBtnClick();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
	
/**
 * Display the information of the bike which was rented
 * @throws SQLException 
 */
	public void display() throws SQLException {
		Map<String, String> infoMap = bController.getScreenInfo();
		
		//set info
//		File file = new File(infoMap.get("imagePath"));
//		Image im = new Image(file.toURI().toString());
//		bikeImg.setImage(im);
		
		
		name.setText(infoMap.get("name"));
		type.setText(infoMap.get("type"));
		time.setText(infoMap.get("time"));
	
		amount.setText(infoMap.get("amount"));
		pin.setText("khong co");
		
	}
	
/**
 * Transfer toReturnBikeScreen
 * @throws IOException 
 */
	public void handleReturnBikeBtnClick() throws IOException {
		LOGGER.info("function handleReturnBikeBtnClick ");
		//Update rentInfo: time and endTime
		 bController.processReturnBikeRequest();
		try {
			ReturnBikeScreenHandler returnBikeScreenHandler = new ReturnBikeScreenHandler(stage, Configs.RETURN_BIKE_SCREEN_PATH);
			returnBikeScreenHandler.setbController(new ReturnBikeController(bController.getRentInfo()));
			returnBikeScreenHandler.show();
			returnBikeScreenHandler.display();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
/**
 * Notify Error
 * @param message -the message error
 */
	public void notifyError(String message) {
		
	}
}
