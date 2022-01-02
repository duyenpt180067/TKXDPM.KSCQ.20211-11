import java.sql.SQLException;
import java.time.LocalDateTime;

import entity.dockbike.Bike;
import entity.rental.RentInfo;
import javafx.application.Application;
import javafx.stage.Stage;
import utils.Configs;
import view.handler.home.HomeScreenHandler;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
    	
    	HomeScreenHandler homeHandler = new HomeScreenHandler(primaryStage, Configs.HOME_PATH);
        homeHandler.setScreenTitle("Home Screen");
        homeHandler.show();
    }


    public static void main(String[] args) throws SQLException {
        launch(args);
        
    }
}
