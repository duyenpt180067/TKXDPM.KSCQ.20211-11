package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.exception.EntityNotFoundException;
import entity.dockbike.Bike;
import entity.dockbike.Cell;
import entity.dockbike.Dock;

public class SearchBikeController extends BaseController{
	/**
	 * Get data of a dock by id
	 * @param dockId � id of dock
	 */
	public Dock getDockById(int dockId) throws SQLException, EntityNotFoundException {
		Dock dock = Dock.getDockById(dockId);
		return dock;
	}
	
	/**
	 * Get data of list dock by name
	 * @param dockName � name of dock
	 */
	public List<Dock> getDockByName(String dockName) throws SQLException, EntityNotFoundException {
		List<Dock> listDock = Dock.getDockByName(dockName);
		return listDock;
	}
	
	/**
	 * Get data of all dock
	 */
	public List<Dock> getAllDocks() throws SQLException, EntityNotFoundException {
		List<Dock> listDock = Dock.getAllDocks();
		return listDock;
	}
	
	/**
	 * Get data of a cell in dock
	 * @param dockId � id of dock 
	 * @param cellNo � number of a cell in dock
	 */
	public Bike getBikeInCell(int dockId, int cellNo) throws SQLException {
		Cell cell = Cell.getCellInDockByNo(dockId, cellNo);
		return cell.getBike();
	}
	
	/**
	 * Get data of a bike by id
	 * @param bikeId � id of dock
	 */
	public Bike getBikeById(int bikeId) throws SQLException {
		return Bike.getBikeById(bikeId);
	}
}
