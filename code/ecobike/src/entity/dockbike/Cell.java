package entity.dockbike;

import common.exception.EntityNotFoundException;
import entity.db.EcobikeDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Cell {
	private int dockId;

	private int no;

	private String type;

	private Bike bike;

	public static final String NORMAL_CELL = "NORMAL";
	public static final String ELECTRIC_CELL = "ELECTRIC";

	public Cell(int dockId, int no, String type) {
		this.dockId = dockId;
		this.no = no;
		this.type = type;
	}

	public static Cell getCellInDockByNo(int dockId, int cellNo) throws SQLException {
		String sql = "SELECT CELL.*, CELL_TYPE.name as type FROM CELL " +
				"INNER JOIN CELL_TYPE ON CELL.CELL_TYPEid = CELL_TYPE.id " +
				"where DOCKid = " + dockId + " and CELL.no = " + cellNo + " ;";
		Statement stm = EcobikeDB.getConnection().createStatement();
		ResultSet res = stm.executeQuery(sql);
		Cell cell = null;
		while (res.next()){
			int no = res.getInt("no");
			String type = res.getString("type");
			cell = new Cell(dockId, no, type);
		}
		return cell;
	}
	
	public static Cell getCellInDockByBike(int bikeId) throws SQLException {
		String sql = "SELECT CELL.*, CELL_TYPE.name as type FROM CELL " +
				"INNER JOIN CELL_TYPE ON CELL.CELL_TYPEid = CELL_TYPE.id " +
				"where BIKEid = " + bikeId + " ;";
		Statement stm = EcobikeDB.getConnection().createStatement();
		ResultSet res = stm.executeQuery(sql);
		Cell cell = null;
		while (res.next()){
			int no = res.getInt("no");
			String type = res.getString("type");
			int dockId = res.getInt("DOCKid");
			cell = new Cell(dockId, no, type);
		}
		return cell;
	}

	/**
	 * Get data of list empty cell in dock
	 * @throws if not found cell
	 */
	public static List<Cell> getEmptyCellInDockForBike(int dockId, String bikeType) throws EntityNotFoundException, SQLException {
		String cellType;
		if(bikeType.equals(Bike.STANDARD_BIKE) || bikeType.equals(Bike.TWIN_BIKE)){
			cellType = Cell.NORMAL_CELL;
		}
		else cellType = Cell.ELECTRIC_CELL;

		String sql = "SELECT CELL.*, CELL_TYPE.name as type FROM CELL " +
				"INNER JOIN CELL_TYPE ON CELL.CELL_TYPEid = CELL_TYPE.id " +
				"WHERE DOCKid = " + dockId + " and BIKEid IS NULL and type = '" + cellType + "';";
		Statement stm = EcobikeDB.getConnection().createStatement();
		ResultSet res = stm.executeQuery(sql);
		List<Cell> listCell = new ArrayList<Cell>();
		while (res.next()){
			int no = res.getInt("no");
			String type = res.getString("type");
			Cell cell = new Cell(dockId, no, type);
			listCell.add(cell);
		}
		return listCell;
	}

	public Bike getBike() throws SQLException {
		String sql = "SELECT CELL.BIKEid FROM CELL " +
				"where DOCKid = " + this.dockId + " and CELL.no = " + this.no + " ;";
		Statement stm = EcobikeDB.getConnection().createStatement();
		ResultSet res = stm.executeQuery(sql);
		Bike bike = null;
		if (res.next()){
			int bikeId = res.getInt("BIKEid");
			if(bikeId != 0)  bike = Bike.getBikeById(bikeId);
		}
		return bike;
	}
	
	public static void updateBikeLocation(int bikeId, int returnCellId, int returnDockId) throws SQLException {
        String sql = "UPDATE CELL SET BIKEid = ? "
                + "WHERE DOCKid = ? and no = ? " ;
        Connection conn = EcobikeDB.getConnection();
          PreparedStatement prestat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        try  {
 
            // set the corresponding param
            prestat.setInt(1, bikeId);
            prestat.setInt(2, returnDockId);
            prestat.setInt(3, returnCellId);

            // update 
            prestat.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ADD BIKE "+ bikeId + " at DOCK: "+ returnDockId + " at CELL " + returnCellId);
        }
    }

    public void removeBikeLocation() throws SQLException {
        String sql = "UPDATE CELL SET BIKEid = NULL "
                + "WHERE DOCKid = ? and no = ? " ;
        Connection conn = EcobikeDB.getConnection();
        PreparedStatement prestat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        try  {
 
            // set the corresponding param
            prestat.setInt(1, this.dockId);
            prestat.setInt(2, this.no);

            // update 
            prestat.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ADD BIKE "+ this.bike.getId() + " at DOCK: "+ this.dockId + " at CELL " + this.no);
        }
    }

	public String getType(){
		return type;
	}

	public void removeBike() {

	}

	public void setBike(Bike bike) {

	}

	public int getDockId() {
		return dockId;
	}

	public int getNo() {
		return no;
	}
}
