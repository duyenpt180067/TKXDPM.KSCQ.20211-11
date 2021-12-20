package view.handler.return_bike;

import java.io.IOException;

import entity.dockbike.Cell;
import entity.dockbike.Dock;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import view.handler.FXMLScreenHandler;

public class CellItemHandler extends FXMLScreenHandler{
	@FXML
	Label name;
	
	@FXML 
	Button btn_choose;
	private ReturnBikeScreenHandler returnBikeScreen;
	
	public CellItemHandler(String screenPath, ReturnBikeScreenHandler returnBikeScreen) throws IOException {
		super(screenPath);
		btn_choose.setText("Chọn");
		this.returnBikeScreen = returnBikeScreen;
	}
	
	public void setCellItem(Cell cell) {
		name.setText("Vị trí "+ cell.getNo());
		btn_choose.setOnMouseClicked(e ->{
			returnBikeScreen.setCellId(cell.getNo());
		});
	}
}
