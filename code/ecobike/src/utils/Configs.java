package utils;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Configs {
	// api constants
		public static final String GET_BALANCE_URL = "https://ecopark-system-api.herokuapp.com/api/card/balance/118609_group1_2020";
		public static final String GET_VEHICLECODE_URL = "https://ecopark-system-api.herokuapp.com/api/get-vehicle-code/1rjdfasdfas";
		public static final String PROCESS_TRANSACTION_URL = "https://ecopark-system-api.herokuapp.com/api/card/processTransaction";
		public static final String RESET_URL = "https://ecopark-system-api.herokuapp.com/api/card/reset";
		
		public static final Map<String, Integer> BIKE_MULTI = new HashMap<String, Integer>();

		// demo data
		public static final String POST_DATA = "{"
				+ " \"secretKey\": \"BG27oFKhwgQ=\" ,"
				+ " \"transaction\": {"
				+ " \"command\": \"pay\" ,"
				+ " \"cardCode\": \"kscq1_group11_2021\" ,"
				+ " \"owner\": \"Group 11\" ,"
				+ " \"cvvCode\": \"298\" ,"
				+ " \"dateExpried\": \"1125\" ,"
				+ " \"transactionContent\": \"Pei debt\" ,"
				+ " \"amount\": 50000 "
				+ "}"
			+ "}";
		public static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiIxMTg2MDlfZ3JvdXAxXzIwMjAiLCJpYXQiOjE1OTkxMTk5NDl9.y81pBkM0pVn31YDPFwMGXXkQRKW5RaPIJ5WW5r9OW-Y";

		// database Configs
		public static final String DB_USERNAME = System.getenv("DB_USERNAME");
		public static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

		public static String CURRENCY = "VND";
		public static long ONE_DAY_PASS = 200000;

		// static resource
//		public static final String IMAGE_PATH = "/view/template/image";
		public static final String RENTED_BIKE_INFO_PATH = "/view/template/fxml/RentedBikeInfo.fxml";
		public static final String PAYMENT_SCREEN_RENT_BIKE_PATH = "/view/template/fxml/PaymentScreen-RentBike.fxml";
		public static final String RETURN_BIKE_SCREEN_PATH = "/view/template/fxml/ReturnBike.fxml";
		public static final String ITEM = "/view/template/fxml/item.fxml";
		public static final String PAYMENT_SCREEN_RETURN_BIKE_PATH = "/view/template/fxml/PaymentScreen-ReturnBike.fxml";
		
		
		public static final String IMAGE_PATH = "assets/images";
		
		public static final String DOCK_SCREEN_PATH = "/view/template/fxml/DockViewScreen.fxml";
		public static final String BIKE_SCREEN_PATH = "/view/template/fxml/BikeInfoScreen.fxml";
		public static final String HOME_PATH  = "/view/template/fxml/HomeScreen.fxml";
		public static final String DOCK_IN_HOME_PATH = "/view/template/fxml/DockInHome.fxml";
		public static final String CELL_PATH = "/view/template/fxml/Cell.fxml";

		public static Font REGULAR_FONT = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 24);

}
