package pne.project.tsp.controler;

import pne.project.tsp.view.MainView;

public class MainControler {

	private MainView mv;

	public MainControler(MainView mv) {
		this.mv = mv;
	}
	
	/**
	 * Ajouter une méthode qui permet de gérer le cas où on clique sur 
	 *     - Charger un PVC
	 *     - Quitter
	 *     (ActionListeners)
	 */

	public MainView getMv() {
		return mv;
	}

	public void setMv(MainView mv) {
		this.mv = mv;
	}
}
