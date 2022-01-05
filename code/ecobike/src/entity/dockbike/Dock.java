package entity.dockbike;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import common.exception.EntityNotFoundException;
import entity.db.EcobikeDB;

public class Dock {
	private int id;
	private int numCell = 0;
	private String name, address;
	private int area;
	private List<Cell> listCell;
	private String imagePath;
	private int numBike;

	public Dock(int id, String name, String address, int area, String imagePath){
		this.id = id;
		this.name = name;
		this.address = address;
		this.area = area;
		this.imagePath = imagePath;
		this.listCell = new ArrayList<Cell>();
	}

	public Dock(){

	}

	/**
	 * Get data of a dock by id
	 * @param dockId � id of dock
	 * @throws if not found dock
	 */
	public static Dock getDockById(int dockId) throws EntityNotFoundException, SQLException {
		String sql = "SELECT * FROM DOCK WHERE id = " + dockId + " ;";
		Statement stm = EcobikeDB.getConnection().createStatement();
		ResultSet res = stm.executeQuery(sql);
		Dock dock = null;
		if (res.next()){
			 dock = new Dock(
					res.getInt("id"),
					res.getString("name"),
					res.getString("address"),
					res.getInt("area"),
					res.getString("imagePath")
			 );
		}
		return dock;
	}
	
	/**
	 * Get data of list dock by name
	 * @param name � name of dock
	 * @throws if not found dock
	 */
	public static List<Dock> getDockByName(String name) throws EntityNotFoundException, SQLException {
		String sql = "SELECT * FROM DOCK WHERE LOWER(name) = '" + name.toLowerCase() + "' ;";
		Statement stm = EcobikeDB.getConnection().createStatement();
		ResultSet res = stm.executeQuery(sql);
		List<Dock> listDock = new ArrayList<Dock>();
		while (res.next()){
			Dock dock = new Dock(
					res.getInt("id"),
					res.getString("name"),
					res.getString("address"),
					res.getInt("area"),
					res.getString("imagePath")
			);
			listDock.add(dock);
		}
		return listDock;
	}
	
	/**
	 * Get data of all dock
	 * @throws if not found dock
	 */
	public static List<Dock> getAllDocks() throws EntityNotFoundException, SQLException {
		String sql = "SELECT * FROM DOCK ;";
		Statement stm = EcobikeDB.getConnection().createStatement();
		ResultSet res = stm.executeQuery(sql);
		List<Dock> listDock = new ArrayList<Dock>();
		while (res.next()){
			Dock dock = new Dock(
					res.getInt("id"),
					res.getString("name"),
					res.getString("address"),
					res.getInt("area"),
					res.getString("imagePath")
			);
			listDock.add(dock);
		}
		return listDock;
	}

	public int getNumBike() throws SQLException {
		String sql = "SELECT COUNT(no) as num FROM CELL WHERE DOCKid = " + this.id + " and BIKEid IS NOT NULL;";
		Statement stm = EcobikeDB.getConnection().createStatement();
		ResultSet res = stm.executeQuery(sql);
		int numBike = 0;
		if (res.next()){
			numBike = res.getInt("num");
		}
		return numBike;
	}

	public int getNumCell() throws SQLException {
		if(numCell != 0) return numCell;
		String sql = "SELECT COUNT(no) as num FROM CELL WHERE DOCKid = " + this.id + " ;";
		Statement stm = EcobikeDB.getConnection().createStatement();
		ResultSet res = stm.executeQuery(sql);
		if (res.next()){
			numCell = res.getInt("num");
		}
		return numCell;
	}

	public List<Cell> getAllCells() throws SQLException {
		String sql = "SELECT CELL.*, CELL_TYPE.name as type FROM CELL " +
				"INNER JOIN CELL_TYPE ON CELL.CELL_TYPEid = CELL_TYPE.id" +
				" where DOCKid = " + this.id + " ;";
		Statement stm = EcobikeDB.getConnection().createStatement();
		ResultSet res = stm.executeQuery(sql);
		List<Cell> listCell = new ArrayList<Cell>();
		while (res.next()){
			int no = res.getInt("no");
			String type = res.getString("type");
			Cell cell = new Cell(this.id, no, type);
			listCell.add(cell);
		}
		
		System.out.println(res.toString());
		return listCell;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public String getAddress() {
		return address;
	}

	public int getArea() {
		return area;
	}

	public String getImagePath() {
		return imagePath;
	}
}
