package view.handler.return_bike;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import common.exception.EntityNotFoundException;
import controller.ReturnBikeController;
import entity.dockbike.Cell;
import entity.dockbike.Dock;
import entity.payment.Invoice;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;
import view.handler.BaseScreenHandler;
import view.handler.payment.PaymentReturnBikeHandler;

/**
 * 
 * @author vanpham
 *
 */
public class ReturnBikeScreenHandler extends BaseScreenHandler {
	
	@FXML
	VBox dockList;
	
	@FXML
	VBox cellList;
	
	@FXML 
	Label dockId;
	
	@FXML 
	Label cellId;
	
	@FXML
	Button returnBikeBtn;
	
	ReturnBikeScreenHandler bController;
	/**
	 * Display info in screen
	 */
	public void display() {
		displayDockList();
		
	}
	
	public  ReturnBikeScreenHandler(Stage stage, String screenPath) throws IOException {
		super(stage,screenPath);
		
		
		this.setScreenTitle("Return Bike Screen");
		returnBikeBtn.setOnMouseClicked(e ->{
			handleReturnBikeBtnClick();
		});
	}
	
	private void displayDockList() {
		dockList.getChildren().clear();
		try {
			List<Dock> lstDock = getBController().getNotFullDock();
			for(Dock dock : lstDock ) {
				DockItemHandler dockItem = new DockItemHandler(Configs.ITEM,this);
				dockItem.setDockItem(dock);
				dockList.getChildren().add(dockItem.getContent());
			}
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void displayCellList(Dock dock) {
		cellList.getChildren().clear();
		try {
			List<Cell> lstCell = getBController().getEmptyCellIndock(dock);
			for(Cell cell : lstCell) {
				CellItemHandler cellItem = new CellItemHandler(Configs.ITEM,this);
				cellItem.setCellItem(cell);
				cellList.getChildren().add(cellItem.getContent());
			}
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void setDockId(int id) {
		dockId.setText(Integer.toString(id));
	}
	public void setCellId(int id) {
		cellId.setText(Integer.toString(id));
	}
	
	/**
	 * Process when user choose a cell
	 */
	public void handleReturnBikeBtnClick() {
//		tinh tien thue xe
//		create invoice
		
		try {
			Invoice invoice = getBController().createInvoice();
			PaymentReturnBikeHandler paymentReturnBikeHandler;
			paymentReturnBikeHandler = new PaymentReturnBikeHandler(stage, Configs.PAYMENT_SCREEN_RETURN_BIKE_PATH, invoice);
			paymentReturnBikeHandler.show();
			paymentReturnBikeHandler.display();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ReturnBikeController getBController() {
		return (ReturnBikeController) super.getBController();
	}

}
