package view.handler;

import controller.BaseController;

public class BaseScreenHandler {

	private BaseScreenHandler prev;

	private BaseController bController;

	public BaseScreenHandler(String screenPath) {

	}

	public void setPreviousScreen(BaseScreenHandler prev) {

	}

	public BaseScreenHandler getPreviousScreen() {
		return null;
	}

	public void setScreenTitle(String title) {

	}

	public void setBController(BaseController bController) {

	}

	public BaseController getBController() {
		return null;
	}

	public void setHomeScreen(HomeScreenHandler homeScreen) {

	}

	public void show() {

	}

}
