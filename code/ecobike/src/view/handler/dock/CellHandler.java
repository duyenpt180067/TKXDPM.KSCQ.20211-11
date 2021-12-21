package view.handler.dock;

import common.exception.EntityNotFoundException;
import entity.dockbike.Bike;
import entity.dockbike.Cell;
import entity.dockbike.StandardEBike;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import utils.Configs;
import view.handler.BaseScreenHandler;
import view.handler.FXMLScreenHandler;
import view.handler.bike.BikeInfoScreenHandler;
import java.io.IOException;
import java.sql.SQLException;

public class CellHandler extends FXMLScreenHandler {

    @FXML
    private Label noLabel;

    @FXML
    private Text bikeTypeText;

    @FXML
    private Text licenseText;

    @FXML
    private Text batteryText;

    @FXML
    private Button viewBikeBtn;
    private BaseScreenHandler parentScreenHandler;

    public CellHandler(String screenPath, Cell cell, BaseScreenHandler parentScreenHandler) throws IOException, SQLException {
        super(screenPath);
        this.parentScreenHandler = parentScreenHandler;
        setCellInfo(cell);
        viewBikeBtn.setOnMouseClicked(mouseEvent -> {
            BaseScreenHandler bikeInfoScreenHandler = null;
            try {
                Bike bike = cell.getBike();
                bikeInfoScreenHandler = new BikeInfoScreenHandler(this.parentScreenHandler.getStage(), Configs.BIKE_SCREEN_PATH, bike);
                bikeInfoScreenHandler.setScreenTitle("Bike Information");
                bikeInfoScreenHandler.setbController(this.parentScreenHandler.getbController());
                bikeInfoScreenHandler.setPrev(this.parentScreenHandler);
                bikeInfoScreenHandler.show();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (EntityNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    /***
	 * set value to cell
	 * @param cell
	 * @throws SQLException if connect to db fail
	 */
    private void setCellInfo(Cell cell) throws SQLException {
        noLabel.setText(String.valueOf(cell.getNo()));
        Bike bike = cell.getBike();
        if(bike != null){
            bikeTypeText.setText(bike.getType());
            licenseText.setText(bike.getLicensePlate());
            if(bike.getType() == Bike.STANDARD_EBIKE){
                batteryText.setText(String.valueOf(StandardEBike.getRemainBatteryOfBike(bike.getId())));
            }
            else{
                batteryText.setText("");
            }
        }
        else{
            bikeTypeText.setText("Trống");
            licenseText.setText("");
            batteryText.setText("");
        }
    }
}

