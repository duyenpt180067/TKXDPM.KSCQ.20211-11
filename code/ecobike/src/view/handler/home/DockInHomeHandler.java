package view.handler.home;

import entity.dockbike.Dock;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import utils.Configs;
import view.handler.BaseScreenHandler;
import view.handler.FXMLScreenHandler;
import view.handler.dock.DockViewScreenHandler;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class DockInHomeHandler extends FXMLScreenHandler {
    @FXML
    private ImageView dockImage;

    @FXML
    private Text dockNameText;

    @FXML
    private Text numBikeText;

    @FXML
    private Text dockAddressText;

    @FXML
    private Button viewDockBtn;

    private Dock dock;
    private static final int IMAGE_WIDTH = 150;
    private static final int IMAGE_HEIGHT = 140;
    private BaseScreenHandler parentScreenHandler;

    public DockInHomeHandler(String screenPath, Dock dock, BaseScreenHandler parentScreenHandler) throws IOException, SQLException {
        super(screenPath);
        this.dock = dock;
        this.parentScreenHandler = parentScreenHandler;
        setDockInfo();
        viewDockBtn.setOnMouseClicked(mouseEvent -> {
            BaseScreenHandler dockViewScreenHandler = null;
            try {
                dockViewScreenHandler = new DockViewScreenHandler(this.parentScreenHandler.getStage(), Configs.DOCK_SCREEN_PATH, dock);
                dockViewScreenHandler.setScreenTitle("Dock Information");
                dockViewScreenHandler.setbController(this.parentScreenHandler.getbController());
                dockViewScreenHandler.setPrev(this.parentScreenHandler);
                dockViewScreenHandler.show();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void setDockInfo() throws SQLException {
        File file = new File(dock.getImagePath());
        Image image = new Image(file.toURI().toString(), IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
        dockImage.setImage(image);
        dockNameText.setText(dock.getName());
        dockAddressText.setText(dock.getAddress());
        numBikeText.setText(String.valueOf(dock.getNumBike()));
    }
}
