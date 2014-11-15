package pne.project.tsp.controler;

import pne.project.tsp.view.GraphView;

public class GraphControler {

	private GraphView gv;

	public GraphControler(GraphView gv, boolean isCreateMode) {
		this.gv = gv;
		// Faire passer le boolean dans GraphView
	}
	
	/**
	 *  Ajouter une méthode qui permet de savoir si :
	 *    - on clique sur Terminer
	 *    - on clique sur Résoudre
	 *    - on clique sur Quitter
	 */

	public GraphView getGv() {
		return gv;
	}

	public void setGv(GraphView gv) {
		this.gv = gv;
	}

}
