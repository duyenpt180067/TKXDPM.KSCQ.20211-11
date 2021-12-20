package view.handler.return_bike;

import java.io.IOException;

import entity.dockbike.Dock;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import view.handler.FXMLScreenHandler;

public class DockItemHandler extends FXMLScreenHandler{
	
	@FXML
	Label name;
	
	@FXML 
	Button btn_choose;
	private ReturnBikeScreenHandler returnBikeScreen;
	
	public DockItemHandler(String screenPath, ReturnBikeScreenHandler returnBikeScreen) throws IOException {
		super(screenPath);
		this.returnBikeScreen = returnBikeScreen;
	}
	
	public void setDockItem(Dock dock) {
		name.setText(dock.getName());
		btn_choose.setOnMouseClicked(e ->{
			returnBikeScreen.displayCellList(dock);
			returnBikeScreen.setDockId(dock.getId());
		});
	}

}
