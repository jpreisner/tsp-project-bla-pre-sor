package pne.project.tsp.controler;

import pne.project.tsp.view.MainView;

public class MainControler {

	private MainView mv;

	public MainControler(MainView mv) {
		this.mv = mv;
	}

	public MainView getMv() {
		return mv;
	}

	public void setMv(MainView mv) {
		this.mv = mv;
	}
}
