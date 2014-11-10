package pne.project.tsp.controler;

import pne.project.tsp.view.MainView;

public class MainControler {

	private MainView mv;

	public MainControler(MainView mv) {
		this.mv = mv;
	}
	
	/**
	 * Ajouter une méthode qui permet de gérer le cas où on clique sur 
	 *     - Créer son PVC
	 *     - Charger un PVC
	 *     - Quitter
	 */

	public MainView getMv() {
		return mv;
	}

	public void setMv(MainView mv) {
		this.mv = mv;
	}
}
