package view.handler.dock;

import entity.dockbike.Cell;
import entity.dockbike.Dock;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Configs;
import view.handler.BaseScreenHandler;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DockViewScreenHandler extends BaseScreenHandler {
	@FXML
	private ImageView dockImage;

	@FXML
	private Text dockNameText;

	@FXML
	private Text dockAddressText;

	@FXML
	private Text dockAreaText;

	@FXML
	private Text dockNumBikeText;

	@FXML
	private Text numEmptyCellText;

	@FXML
	private VBox cellsVbox;
	private static final Integer IMAGE_WIDTH = 400;
	private static final Integer IMAGE_HEIGHT = 230;
	private static final Integer CELL_ROW_SIZE = 4;
	private List<Cell> cellList;

	public DockViewScreenHandler(Stage stage, String screenPath, Dock dock) throws IOException, SQLException {
		super(stage, screenPath);
		setDockInfo(dock);
		cellList = dock.getAllCells();
		populateCellsToVBox(cellList, cellsVbox);
	}

	/***
	 * set value to dock
	 * @param dock
	 * @throws SQLException if connect to db fail
	 */
	private void setDockInfo(Dock dock) throws SQLException {
		File file = new File(dock.getImagePath());
		Image image = new Image(file.toURI().toString(), IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
		dockImage.setFitWidth(IMAGE_WIDTH);
		dockImage.setFitHeight(IMAGE_HEIGHT);
		dockImage.setImage(image);

		dockNameText.setText(dock.getName());
		dockAddressText.setText(dock.getAddress());
		dockAreaText.setText(String.valueOf(dock.getArea()));
		int numBike = dock.getNumBike();
		int numCell = dock.getNumCell();
		dockNumBikeText.setText(String.valueOf(numBike));
		numEmptyCellText.setText(String.valueOf(numCell - numBike));
	}

	/***
	 * add cells to vbox in screen
	 * @param cellList
	 * @param vBox
	 * @throws IOException 
	 * @throws SQLException
	 */
	private void populateCellsToVBox(List<Cell> cellList, VBox vBox) throws IOException, SQLException {
		for (int i = 0; i < cellList.size(); i += CELL_ROW_SIZE){
			HBox rowHbox = new HBox();
			// TODO: Cần chỉnh lại thuộc tính để đẹp hơn
			rowHbox.setAlignment(Pos.TOP_LEFT);
			rowHbox.setSpacing(10);
			rowHbox.setPadding(new Insets(0, 0, 0, 50));
			rowHbox.setPrefWidth(Region.USE_COMPUTED_SIZE);
			for(int j = i; j < i + CELL_ROW_SIZE && j < cellList.size(); j++) {
				Cell cell = cellList.get(j);
				CellHandler cellHandler = new CellHandler(Configs.CELL_PATH, cell, this);
				rowHbox.getChildren().add(cellHandler.getContent());
			}
			vBox.getChildren().add(rowHbox);
		}
	}
}
