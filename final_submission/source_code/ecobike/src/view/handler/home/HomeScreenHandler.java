package view.handler.home;

import common.exception.EntityNotFoundException;
import common.exception.InvalidFormInputException;
import common.exception.NoBikeRentedException;
import controller.SearchBikeController;
import entity.dockbike.Bike;
import entity.dockbike.Dock;
import entity.rental.RentInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;
import view.handler.BaseScreenHandler;
import view.handler.bike.BikeInfoScreenHandler;
import view.handler.rent_info.RentedBikeInfoScreenHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class HomeScreenHandler extends BaseScreenHandler implements Initializable {
	@FXML
	private TextField bikeCodeTxtField;

	@FXML
	private Button searchBtn;

	@FXML
	private VBox docksVbox;

	@FXML
	private Pagination homePaging;

	@FXML
	private Button viewRentInfoBtn;
	
	@FXML
	private Button viewRentedBikeBtn;

	private List<Dock> dockList;
	private static final Integer DOCK_ROW_SIZE = 3;

	public HomeScreenHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		docksVbox.getChildren().clear();
		bController = new SearchBikeController();
		viewRentedBikeBtn.setOnMouseClicked(EventHandler -> {
			handleRentedBikeInfoScreen();
		});
		try {
			dockList = ((SearchBikeController) bController).getAllDocks();
			populateDocksToVbox(dockList, docksVbox);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleRentedBikeInfoScreen() {
		// TODO Auto-generated method stub
		try {
			RentInfo rentInfo = bController.getCurrentRentInfo();
			RentedBikeInfoScreenHandler rentedBikeInfoScreenHandler;
			rentedBikeInfoScreenHandler = new RentedBikeInfoScreenHandler(stage, Configs.RENTED_BIKE_INFO_PATH);
			rentedBikeInfoScreenHandler.show();
		} catch (NoBikeRentedException | IOException | SQLException e) {
			showDialog(e.getMessage(), "Notification");
		}
	}

	private void populateDocksToVbox(List<Dock> dockList, VBox vBox) throws IOException, SQLException {
		for (int i = 0; i < dockList.size(); i += DOCK_ROW_SIZE){
			HBox rowHbox = new HBox();
			// TODO: Cần chỉnh lại thuộc tính để đẹp hơn
			rowHbox.setAlignment(Pos.TOP_LEFT);
			rowHbox.setSpacing(10);
			rowHbox.setPadding(new Insets(0, 0, 0, 50));
			rowHbox.setPrefWidth(Region.USE_COMPUTED_SIZE);
			for(int j = i; j < i + DOCK_ROW_SIZE && j < dockList.size(); j++) {
				Dock dock = dockList.get(j);
				DockInHomeHandler dockHandler = new DockInHomeHandler(Configs.DOCK_IN_HOME_PATH, dock, this);
				rowHbox.getChildren().add(dockHandler.getContent());
			}
			vBox.getChildren().add(rowHbox);
		}
	}

	@FXML
	public void searchBike(MouseEvent event){
		try {
			String bikeCode = bikeCodeTxtField.getText();
			SearchBikeController ctrl = (SearchBikeController) bController;
			Bike bike = ctrl.getBikeById(bikeCode);
			BaseScreenHandler bikeInfoScreenHandler = new BikeInfoScreenHandler(this.getStage(), Configs.BIKE_SCREEN_PATH, bike);
			bikeInfoScreenHandler.setScreenTitle("Bike Information");
			bikeInfoScreenHandler.setbController(this.getbController());
			bikeInfoScreenHandler.setPrev(this);
			bikeInfoScreenHandler.show();
		} catch (SQLException | InvalidFormInputException | EntityNotFoundException | IOException e) {
			showDialog(e.getMessage(), "Notification");
		}
	}
}
