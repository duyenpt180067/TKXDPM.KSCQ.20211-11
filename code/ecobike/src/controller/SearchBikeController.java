package controller;

import java.util.ArrayList;
import java.util.List;

import entity.dockbike.Bike;
import entity.dockbike.Dock;

public class SearchBikeController {
	/**
	 * Get data of a dock by id
	 * @param id � id of dock 
	 */
	public Dock getDockById(int id) {
		Dock dock = new Dock();
		return dock;
	}
	
	/**
	 * Get data of list dock by name
	 * @param name � name of dock
	 */
	public List<Dock> getDockByName(String name) {
		List<Dock> listDock = new ArrayList<Dock>();
		return listDock;
	}
	
	/**
	 * Get data of all dock
	 */
	public List<Dock> getAllDock() {
		List<Dock> listDock = new ArrayList<Dock>();
		return listDock;
	}
	
	/**
	 * Get data of a cell in dock
	 * @param dockId � id of dock 
	 * @param cell � number of a cell in dock 
	 */
	public Bike getBikeInDock(int dockId, int cell) {
		Bike bike = new Bike();
		return bike;
	}
	
	/**
	 * Get data of a bike by id
	 * @param id � id of dock 
	 */
	public Bike getBikeById(int id) {
		Bike bike = new Bike();
		return bike;
	}
}
