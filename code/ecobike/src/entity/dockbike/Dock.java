package entity.dockbike;

import java.util.ArrayList;
import java.util.List;

import common.exception.EntityNotFoundException;

public class Dock {
	private int id, numCell;
	private String name, address;
	private float area;
	private List<Cell> listCell;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumCell() {
		return numCell;
	}
	public void setNumCell(int numCell) {
		this.numCell = numCell;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public float getArea() {
		return area;
	}
	public void setArea(float area) {
		this.area = area;
	}
	public List<Cell> getListCell() {
		return listCell;
	}
	public void setListCell(List<Cell> listCell) {
		this.listCell = listCell;
	}
	
	/**
	 * Get data of a dock by id
	 * @param id � id of dock 
	 * @throws if not found dock
	 */
	public static Dock getDockById(int id) throws EntityNotFoundException{
		Dock dock = new Dock();
		return dock;
	}
	
	/**
	 * Get data of list dock by name
	 * @param name � name of dock
	 * @throws if not found dock
	 */
	public static List<Dock> getDockByName(String name) throws EntityNotFoundException{
		List<Dock> listDock = new ArrayList<Dock>();
		return listDock;
	}
	
	/**
	 * Get data of all dock
	 * @throws if not found dock
	 */
	public static List<Dock> getAllDock() throws EntityNotFoundException{
		List<Dock> listDock = new ArrayList<Dock>();
		return listDock;
	}
	
	/**
	 * Get data of list empty cell in dock
	 * @throws if not found cell
	 */
	public static List<Cell> getEmptyCellInDock(Dock dock) throws EntityNotFoundException{
		List<Cell> listCell = new ArrayList<Cell>();
		return listCell;
	}
	
	/**
	 * Get data of list dock not full
	 * @throws if not found dock
	 */
	public static List<Dock> getNotFullDock() throws EntityNotFoundException{
		List<Dock> listDock = new ArrayList<Dock>();
		return listDock;
	}
}
